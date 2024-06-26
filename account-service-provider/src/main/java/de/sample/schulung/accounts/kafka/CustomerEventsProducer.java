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

  private final KafkaTemplate<UUID, CustomerEventKafkaDto> kafkaTemplate;
  private final CustomerEventKafkaDtoMapper eventDtoMapper;

  @EventListener
  public void handle(CustomerCreatedEvent event) {
    final var messageKey = event.customer().getUuid();
    final var message = eventDtoMapper.map(event);
    kafkaTemplate.send(
      KafkaConstants.DEFAULT_CUSTOMER_EVENTS_TOPIC,
      messageKey,
      message
    );
  }

}
