package de.sample.schulung.statistics.kafka;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;

import java.util.UUID;

/*
 * This is the Kafka API.
 */
public record CustomerEventKafkaDto(
  @Pattern(regexp = "x|y")
  String eventType,
  UUID uuid,
  @Valid
  CustomerKafkaDto customer
) {

}
