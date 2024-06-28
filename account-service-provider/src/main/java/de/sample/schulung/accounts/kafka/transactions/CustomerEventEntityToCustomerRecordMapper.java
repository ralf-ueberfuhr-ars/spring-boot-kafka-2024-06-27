package de.sample.schulung.accounts.kafka.transactions;

import de.sample.schulung.accounts.kafka.CustomerEventRecord;
import de.sample.schulung.accounts.kafka.CustomerEventRecordMapper;
import de.sample.schulung.accounts.kafka.CustomerRecord;
import de.sample.schulung.accounts.persistence.transactions.events.ConditionalOnTransactionsEnabled;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@ConditionalOnTransactionsEnabled
@Component
@RequiredArgsConstructor
public class CustomerEventEntityToCustomerRecordMapper {

  private final CustomerEventRecordMapper delegate;

  public CustomerRecord mapToCustomerRecord(CustomerEventEntity entity) {
    if (null == entity.getCustomerName()) {
      return null;
    }
    return new CustomerRecord(
      entity.getCustomerName(),
      entity.getCustomerBirthdate(),
      delegate.map(entity.getCustomerState())
    );
  }

  public CustomerEventRecord map(CustomerEventEntity entity) {
    return new CustomerEventRecord(
      entity.getEventType(),
      entity.getCustomerUuid(),
      this.mapToCustomerRecord(entity)
    );
  }

}
