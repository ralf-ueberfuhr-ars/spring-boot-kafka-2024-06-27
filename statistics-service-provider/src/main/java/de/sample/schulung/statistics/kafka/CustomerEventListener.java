package de.sample.schulung.statistics.kafka;

import de.sample.schulung.statistics.domain.CustomersService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
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
  )
  public void handleEvent(
    @Valid @Payload CustomerEventKafkaDto event,
    @Header(KafkaHeaders.RECEIVED_PARTITION) String partition,
    @Header(KafkaHeaders.OFFSET) int offset,
    Acknowledgment acknowledgment
  ) {
    System.out.println("Received from partition " + partition + " / offset: " + offset);
    switch (event.eventType()) {
      case "CREATED":
      case "UPDATED":
        if ("active".equals(event.customer().getState())) {
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
    acknowledgment.acknowledge();
  }

}
