package de.sample.schulung.statistics.kafka;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record CustomerRecord(
  @NotNull
  LocalDate birthdate,
  @NotNull
  @Pattern(regexp = "active|locked|disabled")
  String state
) {
}
