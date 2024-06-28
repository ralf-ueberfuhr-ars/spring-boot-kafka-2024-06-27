package de.sample.schulung.accounts.kafka;

import de.sample.schulung.accounts.domain.events.CustomerCreatedEvent;
import de.sample.schulung.accounts.domain.events.CustomerDeletedEvent;
import de.sample.schulung.accounts.domain.events.CustomerReplacedEvent;
import de.sample.schulung.accounts.kafka.interceptor.KafkaProducer;
import de.sample.schulung.accounts.kafka.interceptor.KafkaRecord;
import de.sample.schulung.accounts.persistence.transactions.events.ConditionalOnTransactionsDisabled;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@ConditionalOnTransactionsDisabled
public class CustomerEventsProducer {

  private final CustomerEventRecordMapper mapper;

  @EventListener
  @KafkaProducer(topic = KafkaConstants.CUSTOMER_EVENTS_TOPIC)
  public KafkaRecord<UUID, CustomerEventRecord> handleCustomerCreatedEvent(CustomerCreatedEvent event) {
    return new KafkaRecord<>(
      event.customer().getUuid(),
      this.mapper.map(event)
    );
  }

  @EventListener
  @KafkaProducer(topic = KafkaConstants.CUSTOMER_EVENTS_TOPIC)
  public KafkaRecord<UUID, CustomerEventRecord> handleCustomerReplacedEvent(CustomerReplacedEvent event) {
    return new KafkaRecord<>(
      event.customer().getUuid(),
      this.mapper.map(event)
    );
  }

  @EventListener
  @KafkaProducer(topic = KafkaConstants.CUSTOMER_EVENTS_TOPIC)
  public KafkaRecord<UUID, CustomerEventRecord> handleCustomerDeletedEvent(CustomerDeletedEvent event) {
    return new KafkaRecord<>(
      event.uuid(),
      this.mapper.map(event)
    );
  }

}
