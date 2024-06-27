package de.sample.schulung.accounts.kafka;

import de.sample.schulung.accounts.domain.events.CustomerCreatedEvent;
import de.sample.schulung.accounts.domain.events.CustomerDeletedEvent;
import de.sample.schulung.accounts.domain.events.CustomerReplacedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerEventKafkaDtoMapper {

  private final CustomerKafkaDtoMapper customerDtoMapper;

  public CustomerEventKafkaDto map(CustomerCreatedEvent event) {
    return new CustomerEventKafkaDto(
      "CREATED",
      event.customer().getUuid(),
      customerDtoMapper.map(event.customer())
    );
  }

  public CustomerEventKafkaDto map(CustomerReplacedEvent event) {
    return new CustomerEventKafkaDto(
      "REPLACED",
      event.customer().getUuid(),
      customerDtoMapper.map(event.customer())
    );
  }

  public CustomerEventKafkaDto map(CustomerDeletedEvent event) {
    return new CustomerEventKafkaDto(
      "DELETED",
      event.uuid(),
      null
    );
  }

}
