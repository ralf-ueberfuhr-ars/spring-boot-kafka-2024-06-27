package de.sample.schulung.accounts.kafka;

import de.sample.schulung.accounts.domain.events.CustomerCreatedEvent;
import de.sample.schulung.accounts.domain.events.CustomerDeletedEvent;
import de.sample.schulung.accounts.domain.events.CustomerReplacedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerEventsListener {

  private final CustomerEventsConfiguration configuration;
  private final CustomerEventRecordMapper mapper;
  private final CustomerEventsProducer producer;

  @EventListener
  public void handleCustomerCreatedEvent(CustomerCreatedEvent event) {
    if (configuration.isEnabled()) {
      producer.produce(this.mapper.map(event));
    }
  }

  @EventListener
  public void handleCustomerReplacedEvent(CustomerReplacedEvent event) {
    if (configuration.isEnabled()) {
      producer.produce(this.mapper.map(event));
    }
  }

  @EventListener
  public void handleCustomerDeletedEvent(CustomerDeletedEvent event) {
    if (configuration.isEnabled()) {
      producer.produce(this.mapper.map(event));
    }
  }

}
