package de.sample.schulung.accounts.kafka.transactions;

import de.sample.schulung.accounts.persistence.transactions.events.ConditionalOnTransactionsEnabled;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@ConditionalOnTransactionsEnabled
@Repository
public interface CustomerEventEntityRepository extends JpaRepository<CustomerEventEntity, Long> {
}
