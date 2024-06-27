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
    // map event to record
    var customer = event.customer();
    var payload = new CustomerEventRecord(
      "created",
      customer.getUuid(),
      new CustomerRecord(
        customer.getName(),
        customer.getDateOfBirth(),
        switch(customer.getState()) {
          case ACTIVE -> "active";
          case LOCKED -> "locked";
          case DISABLED -> "disabled";
        }
      )
    );
    // send message
    kafkaTemplate.send(
      KafkaConstants.CUSTOMER_EVENTS_TOPIC,
      event.customer().getUuid(),
      payload
    );
  }

}
