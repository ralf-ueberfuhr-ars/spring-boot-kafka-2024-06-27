package de.sample.schulung.statistics.kafka;

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
