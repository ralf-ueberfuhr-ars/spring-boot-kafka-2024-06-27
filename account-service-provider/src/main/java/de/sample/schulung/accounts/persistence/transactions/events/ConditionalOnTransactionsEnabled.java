package de.sample.schulung.accounts.persistence.transactions.events;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@ConditionalOnProperty(
  name = "application.persistence.enable-transactional-events",
  havingValue = "true",
  matchIfMissing = true
)
public @interface ConditionalOnTransactionsEnabled {
}
