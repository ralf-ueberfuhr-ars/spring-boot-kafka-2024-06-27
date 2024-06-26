package de.sample.schulung.accounts.kafka;

import java.util.UUID;

/*
 * This is the Kafka API.
 */
public record CustomerEventKafkaDto(
  String eventType,
  UUID uuid,
  CustomerKafkaDto customer
) {

}
