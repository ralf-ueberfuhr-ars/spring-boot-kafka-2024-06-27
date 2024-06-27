package de.sample.schulung.accounts.kafka;

import de.sample.schulung.accounts.domain.events.CustomerCreatedEvent;
import de.sample.schulung.accounts.domain.events.CustomerDeletedEvent;
import de.sample.schulung.accounts.domain.events.CustomerReplacedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CustomerEventsProducer {

  private final KafkaTemplate<UUID, Object> kafkaTemplate;
  private final CustomerEventRecordMapper mapper;

  @EventListener
  public void handleCustomerCreatedEvent(CustomerCreatedEvent event) {
    // map event to record
    var payload = this.mapper.map(event);
    // send message
    kafkaTemplate.send(
      KafkaConstants.CUSTOMER_EVENTS_TOPIC,
      event.customer().getUuid(),
      payload
    );
  }

  @EventListener
  public void handleCustomerReplacedEvent(CustomerReplacedEvent event) {
    // map event to record
    var payload = this.mapper.map(event);
    // send message
    kafkaTemplate.send(
      KafkaConstants.CUSTOMER_EVENTS_TOPIC,
      event.customer().getUuid(),
      payload
    );
  }

  @EventListener
  public void handleCustomerDeletedEvent(CustomerDeletedEvent event) {
    // map event to record
    var payload = this.mapper.map(event);
    // send message
    kafkaTemplate.send(
      KafkaConstants.CUSTOMER_EVENTS_TOPIC,
      event.uuid(),
      payload
    );
  }

}
