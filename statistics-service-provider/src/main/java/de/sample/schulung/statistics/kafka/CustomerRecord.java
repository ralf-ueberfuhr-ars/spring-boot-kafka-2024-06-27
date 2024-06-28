package de.sample.schulung.statistics.kafka;

import java.time.LocalDate;

public record CustomerRecord(
  LocalDate birthdate,
  String state
) {
}
