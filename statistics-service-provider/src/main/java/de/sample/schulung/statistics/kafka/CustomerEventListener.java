package de.sample.schulung.statistics.kafka;

import de.sample.schulung.statistics.domain.Customer;
import de.sample.schulung.statistics.domain.CustomersService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Validated
@Component
@Slf4j
@RequiredArgsConstructor
public class CustomerEventListener {

  private final CustomersService customersService;

  // Versuch, danach 1 Versuch nach 1s, danach Versuch nach 2s, danach Versuch nach 4s
  @RetryableTopic(
    attempts = "4",
    backoff = @Backoff(
      delay = 1000L,
      multiplier = 2
    )
  )
  @KafkaListener(
    topics = KafkaConstants.CUSTOMER_EVENTS_TOPIC
  )
  public void consume(
    @Valid @Payload CustomerEventRecord record,
    @Header(KafkaHeaders.RECEIVED_PARTITION) String partition,
    @Header(KafkaHeaders.OFFSET) int offset,
    Acknowledgment  acknowledgment
  ) {
    log.info(
      "Received record {} {} (Partition: {}, Offset: {})",
      record.eventType(),
      record.uuid(),
      partition,
      offset
    );
    if(record.eventType() == null) {
      acknowledgment.acknowledge();
      return;
    }
    switch (record.eventType()) {
      case "created":
      case "updated":
        if("active".equals(record.customer().state())) {
          var customer = Customer
            .builder()
            .uuid(record.uuid())
            .dateOfBirth(record.customer().birthdate())
            .build();
          customersService.saveCustomer(customer);
        } else {
          // TODO wenn "created" / nicht "active" -> kein DB-Zugriff
          customersService.deleteCustomer(record.uuid());
        }
        break;
      case "deleted":
        customersService.deleteCustomer(record.uuid());
        break;
      default:
        throw new ValidationException();
    }
    acknowledgment.acknowledge();
  }

}
