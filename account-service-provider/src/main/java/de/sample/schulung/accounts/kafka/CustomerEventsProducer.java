package de.sample.schulung.accounts.kafka;

import de.sample.schulung.accounts.kafka.interceptor.KafkaProducer;
import de.sample.schulung.accounts.kafka.interceptor.KafkaRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CustomerEventsProducer {

  @KafkaProducer(topic = KafkaConstants.CUSTOMER_EVENTS_TOPIC)
  public KafkaRecord<UUID, CustomerEventRecord> produce(CustomerEventRecord record) {
    return new KafkaRecord<>(
      record.uuid(),
      record
    );
  }

}
