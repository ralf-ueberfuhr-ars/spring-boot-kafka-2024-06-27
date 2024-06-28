package de.sample.schulung.accounts.kafka.transactions;

import de.sample.schulung.accounts.domain.Customer;
import de.sample.schulung.accounts.persistence.transactions.events.ConditionalOnTransactionsEnabled;
import de.sample.schulung.accounts.persistence.transactions.events.CustomerCreatedInTransactionEvent;
import de.sample.schulung.accounts.persistence.transactions.events.CustomerDeletedInTransactionEvent;
import de.sample.schulung.accounts.persistence.transactions.events.CustomerReplacedInTransactionEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@ConditionalOnTransactionsEnabled
@Component
@RequiredArgsConstructor
public class CustomerEventEntityMapper {

  private void copy(Customer customer, CustomerEventEntity entity) {
    entity.setCustomerUuid(customer.getUuid());
    entity.setCustomerName(customer.getName());
    entity.setCustomerBirthdate(customer.getDateOfBirth());
    entity.setCustomerState(customer.getState());
  }

  public CustomerEventEntity map(CustomerCreatedInTransactionEvent event) {
    final var result = new CustomerEventEntity();
    result.setEventType("created");
    copy(event.customer(), result);
    return result;
  }

  public CustomerEventEntity map(CustomerReplacedInTransactionEvent event) {
    final var result = new CustomerEventEntity();
    result.setEventType("replaced");
    copy(event.customer(), result);
    return result;
  }

  public CustomerEventEntity map(CustomerDeletedInTransactionEvent event) {
    final var result = new CustomerEventEntity();
    result.setEventType("deleted");
    result.setCustomerUuid(event.uuid());
    return result;
  }

}
