package de.sample.schulung.accounts.kafka;

import de.sample.schulung.accounts.domain.events.CustomerCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CustomerEventsProducer {

  private final KafkaTemplate<UUID, Object> kafkaTemplate;

  @EventListener
  public void handleCustomerCreatedEvent(CustomerCreatedEvent event) {
    kafkaTemplate.send(
      KafkaConstants.CUSTOMER_EVENTS_TOPIC,
      event.customer().getUuid(),
      event.customer() // TODO ???
    );
  }

}
