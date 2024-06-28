package de.sample.schulung.accounts.kafka.transactions;

import de.sample.schulung.accounts.persistence.transactions.events.ConditionalOnTransactionsEnabled;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@ConditionalOnTransactionsEnabled
@EnableScheduling
public class CustomerEventTransactionalOutboxConfiguration {
}
