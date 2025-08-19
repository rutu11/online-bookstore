package com.onlinebookstore.book_service.service;

import com.onlinebookstore.base_domain.events.OrderEvents;
import com.onlinebookstore.book_service.dto.InventoryDTO;
import com.onlinebookstore.book_service.entity.Book;
import com.onlinebookstore.book_service.entity.Inventory;
import com.onlinebookstore.book_service.exceptions.BookNotFoundException;
import com.onlinebookstore.book_service.mapper.InventoryMapper;
import com.onlinebookstore.book_service.repository.BookMgmtRepo;
import com.onlinebookstore.book_service.repository.InventoryRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {

    private final InventoryRepo inventoryRepo;
    private final BookMgmtRepo bookMgmtRepo;
    private final KafkaTemplate<String, OrderEvents> kafkaTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryService.class);

    @Value("${spring.kafka.topic.inventory-response}")
    private String inventoryRespTopic;

    public InventoryService(InventoryRepo inventoryRepo, BookMgmtRepo bookMgmtRepo, KafkaTemplate<String, OrderEvents> kafkaTemplate) {
        this.inventoryRepo = inventoryRepo;
        this.bookMgmtRepo = bookMgmtRepo;
        this.kafkaTemplate = kafkaTemplate;
    }

    public Inventory addStock(int bookId, long stock){

        Book bookExists = bookMgmtRepo.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Unable to add Stock.. Book with ID: "+bookId+" not found"));

        Inventory stockToAdd = Inventory.builder()
                .bookDetails(bookExists)
                .stock(stock)
                .build();

        LOGGER.info("Book Id: {} found, adding stock", bookId);
        return inventoryRepo.save(stockToAdd);
    }

    public InventoryDTO fetchStockOfABook(int bookId){
        Inventory inventory = inventoryRepo.findByBookDetails_BookId(bookId).orElseThrow(() -> new RuntimeException("Inventory not found.."));
        return InventoryMapper.toDTO(inventory);
//        Optional<Inventory> bookDetailsBookId = inventoryRepo.findByBookDetails_BookId(bookId);
//        return bookDetailsBookId.orElseThrow(() -> new RuntimeException("Inventory not found.."));
    }

    public List<InventoryDTO> fetchInventory(){
        List<Inventory> allStock = inventoryRepo.findAll();
        return InventoryMapper.toDTOList(allStock);
    }

    public Inventory updateStockForABook(int bookId, long newStock){
        Inventory dataFound = inventoryRepo.findByBookDetails_BookId(bookId).orElseThrow(() -> new RuntimeException("Inventory not found.."));
        dataFound.setStock(newStock);

        LOGGER.info("Book Id: {} found, updating stock", bookId);
        return inventoryRepo.save(dataFound);
    }

    @KafkaListener(topics = "${spring.kafka.topic.order-events}", groupId = "inventory-group")
    public void handleOrderEvent(OrderEvents orderEvent){
        Inventory bookStock = inventoryRepo.findByBookDetails_BookId(orderEvent.getBookId()).orElse(null);

        if(bookStock != null && bookStock.getStock() >= orderEvent.getQuantity()){
            long updatedStock = bookStock.getStock() - orderEvent.getQuantity();
            bookStock.setStock(updatedStock);

            Inventory updatedInventory = inventoryRepo.save(bookStock);
            LOGGER.info("Inventory updated => {}", updatedInventory.toString());
            orderEvent.setEventType("INVENTORY-CONFIRMED");
        }
        else {
            LOGGER.info("Failed to confirm inventory for id: {}", orderEvent.getOrderId());
            orderEvent.setEventType("INVENTORY-FAILED");
        }

        LOGGER.info("Sending InventoryEvent response => {}", orderEvent.toString());
        kafkaTemplate.send(inventoryRespTopic, orderEvent)
                .whenComplete((result, ex) -> {
                    if(ex == null){
                        LOGGER.info("OK inventory updated Status => {}",  result.getRecordMetadata());
                    }
                    else {
                        ex.printStackTrace();
                        Throwable root = org.apache.commons.lang3.exception.ExceptionUtils.getRootCause(ex);
                        LOGGER.error(String.valueOf(root));
                    }
                });

    }
}
