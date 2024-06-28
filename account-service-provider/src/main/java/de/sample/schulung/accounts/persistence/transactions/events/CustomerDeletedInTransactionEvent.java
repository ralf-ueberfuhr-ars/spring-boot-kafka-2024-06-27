package de.sample.schulung.accounts.persistence.transactions.events;

import java.util.UUID;

public record CustomerDeletedInTransactionEvent(
  UUID uuid
) {
}
