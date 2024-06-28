package de.sample.schulung.accounts.kafka.transactions;

import de.sample.schulung.accounts.kafka.CustomerEventsProducer;
import de.sample.schulung.accounts.persistence.transactions.events.ConditionalOnTransactionsEnabled;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Sends the saved entities to Kafka and deletes the entities.
 */
@ConditionalOnTransactionsEnabled
@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerEventEntityOutbox {

  private final CustomerEventsProducer producer;
  private final CustomerEventEntityRepository repo;
  private final CustomerEventEntityToCustomerRecordMapper mapper;

  // TODO if sending lasts more than 5 seconds, we should be aware that we could read the data twice!
  @Scheduled(fixedRate = 5000)
  public void execute() {
    log.info("Searching for customer events in outbox...");
    repo.findAll()
      .forEach(this::send);
  }

  private void send(CustomerEventEntity entity) {
    log.info(
      "Sending customer event: {} {} (ID: {})",
      entity.getEventType(),
      entity.getCustomerUuid(),
      entity.getId()
    );
    this.producer.produce(mapper.map(entity));
    this.repo.delete(entity);
  }


}
