package de.sample.schulung.accounts.kafka.transactions;

import de.sample.schulung.accounts.kafka.CustomerEventsConfiguration;
import de.sample.schulung.accounts.persistence.transactions.events.ConditionalOnTransactionsEnabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@ConditionalOnTransactionsEnabled
@EnableScheduling
public class CustomerEventTransactionalOutboxConfiguration {

  @Autowired
  void disable(CustomerEventsConfiguration customerEventsConfiguration) {
    customerEventsConfiguration.setEnabled(false);
  }

}
