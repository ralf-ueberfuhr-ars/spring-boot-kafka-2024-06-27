package de.sample.schulung.accounts.kafka;

import de.sample.schulung.accounts.domain.events.CustomerCreatedEvent;
import de.sample.schulung.accounts.kafka.interceptor.KafkaProducer;
import de.sample.schulung.accounts.kafka.interceptor.KafkaRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CustomerEventsProducer {

  private final CustomerEventKafkaDtoMapper eventDtoMapper;

  @EventListener
  @KafkaProducer(topic = KafkaConstants.DEFAULT_CUSTOMER_EVENTS_TOPIC)
  public KafkaRecord<UUID, CustomerEventKafkaDto> handle(CustomerCreatedEvent event) {
    return new KafkaRecord<>(
      event.customer().getUuid(),
      eventDtoMapper.map(event)
    );
  }

}
