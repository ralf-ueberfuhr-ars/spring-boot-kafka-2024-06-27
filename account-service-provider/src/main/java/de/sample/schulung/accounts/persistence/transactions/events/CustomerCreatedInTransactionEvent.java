package de.sample.schulung.accounts.persistence.transactions.events;

import de.sample.schulung.accounts.domain.Customer;

public record CustomerCreatedInTransactionEvent(
  Customer customer
) {
}
