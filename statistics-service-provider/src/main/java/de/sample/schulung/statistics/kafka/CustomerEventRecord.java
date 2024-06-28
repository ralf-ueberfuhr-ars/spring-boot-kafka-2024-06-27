package de.sample.schulung.statistics.kafka;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.UUID;

public record CustomerEventRecord(
  @NotNull
  @Pattern(regexp = "created|replaced|deleted")
  String eventType,
  @NotNull
  UUID uuid,
  @Valid
  CustomerRecord customer
) {
}
