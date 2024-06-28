package de.sample.schulung.accounts.kafka.transactions;

import de.sample.schulung.accounts.persistence.transactions.events.ConditionalOnTransactionsEnabled;
import de.sample.schulung.accounts.persistence.transactions.events.CustomerCreatedInTransactionEvent;
import de.sample.schulung.accounts.persistence.transactions.events.CustomerDeletedInTransactionEvent;
import de.sample.schulung.accounts.persistence.transactions.events.CustomerReplacedInTransactionEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Save the event in the database.
 */
@Component
@ConditionalOnTransactionsEnabled
@RequiredArgsConstructor
public class CustomersSinkTransactionalEventHandler {

  private final CustomerEventEntityRepository repo;
  private final CustomerEventEntityMapper mapper;

  @EventListener
  void handleCustomerCreatedEvent(CustomerCreatedInTransactionEvent event) {
    repo.save(mapper.map(event));
  }

  @EventListener
  void handleCustomerReplacedEvent(CustomerReplacedInTransactionEvent event) {
    repo.save(mapper.map(event));
  }

  @EventListener
  void handleCustomerDeletedEvent(CustomerDeletedInTransactionEvent event) {
    repo.save(mapper.map(event));
  }

}
