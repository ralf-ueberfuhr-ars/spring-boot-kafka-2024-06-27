package de.sample.schulung.statistics.kafka;

import de.sample.schulung.statistics.domain.CustomersService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Validated
@Component
@RequiredArgsConstructor
public class CustomerEventListener {

  private final CustomersService customersService;
  private final CustomerEventKafkaDtoMapper mapper;

  @KafkaListener(
    topics = KafkaConstants.DEFAULT_CUSTOMER_EVENTS_TOPIC
    // TODO how to aknowledge only after database access
  )
  public void handleEvent(@Valid @Payload CustomerEventKafkaDto event) {
    switch (event.eventType()) {
      case "CREATED":
      case "UPDATED":
        if("active".equals(event.customer().getState())) {
          var customer = this.mapper.map(event);
          customersService.saveCustomer(customer);
        } else {
          customersService.deleteCustomer(event.uuid());
        }
        break;
      case "DELETED":
        customersService.deleteCustomer(event.uuid());
        break;
    }
  }

}
