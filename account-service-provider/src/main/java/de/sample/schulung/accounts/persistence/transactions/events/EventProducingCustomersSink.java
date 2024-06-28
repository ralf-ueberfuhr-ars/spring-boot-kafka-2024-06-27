package de.sample.schulung.accounts.persistence.transactions.events;

import de.sample.schulung.accounts.domain.Customer;
import de.sample.schulung.accounts.persistence.CustomersSinkJpaImpl;
import de.sample.schulung.accounts.shared.interceptors.PublishEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@ConditionalOnTransactionsEnabled
@RequiredArgsConstructor
public class EventProducingCustomersSink {

  private final CustomersSinkJpaImpl sink;

  @PublishEvent(CustomerCreatedInTransactionEvent.class)
  public void createCustomerInTransaction(Customer customer) {
    sink.createCustomer(customer);
  }

  @PublishEvent(CustomerReplacedInTransactionEvent.class)
  public void replaceCustomerInTransaction(Customer customer) {
    sink.replaceCustomer(customer);
  }

  @PublishEvent(CustomerDeletedInTransactionEvent.class)
  public void deleteCustomerInTransaction(UUID uuid) {
    sink.deleteCustomer(uuid);
  }

}
