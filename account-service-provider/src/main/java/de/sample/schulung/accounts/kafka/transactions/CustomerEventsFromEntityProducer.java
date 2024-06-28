package de.sample.schulung.accounts.kafka.transactions;

import de.sample.schulung.accounts.kafka.CustomerEventRecord;
import de.sample.schulung.accounts.kafka.KafkaConstants;
import de.sample.schulung.accounts.kafka.interceptor.KafkaProducer;
import de.sample.schulung.accounts.kafka.interceptor.KafkaRecord;
import de.sample.schulung.accounts.persistence.transactions.events.ConditionalOnTransactionsEnabled;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@ConditionalOnTransactionsEnabled
@Component
@RequiredArgsConstructor
public class CustomerEventsFromEntityProducer {

  private final CustomerEventEntityToCustomerEventRecordMapper mapper;

  @KafkaProducer(topic = KafkaConstants.CUSTOMER_EVENTS_TOPIC)
  public KafkaRecord<UUID, CustomerEventRecord> sendEvent(CustomerEventEntity entity) {
    return new KafkaRecord<>(
      entity.getCustomerUuid(),
      this.mapper.map(entity)
    );
  }

}
