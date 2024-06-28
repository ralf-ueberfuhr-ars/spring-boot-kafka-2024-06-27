package de.sample.schulung.accounts.persistence.transactions.events;

import de.sample.schulung.accounts.domain.Customer;
import de.sample.schulung.accounts.domain.Customer.CustomerState;
import de.sample.schulung.accounts.domain.sink.CustomersSink;
import de.sample.schulung.accounts.persistence.CustomersSinkJpaImpl;
import de.sample.schulung.accounts.shared.interceptors.PublishEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Primary
@Component
@ConditionalOnTransactionsEnabled
@RequiredArgsConstructor
public class CustomersSinkWithEventsInTransactionsJpaImpl implements CustomersSink {

  private final CustomersSinkJpaImpl delegate;
  private final TransactionTemplate transactionTemplate;

  @Override
  public Stream<Customer> getCustomers() {
    return delegate.getCustomers();
  }

  @Override
  public Stream<Customer> getCustomersByState(CustomerState state) {
    return delegate.getCustomersByState(state);
  }

  @PublishEvent(CustomerCreatedInTransactionEvent.class)
  void createCustomerInTransaction(Customer customer) {
    delegate.createCustomer(customer);
  }

  @Override
  public void createCustomer(Customer customer) {
    transactionTemplate.executeWithoutResult(
      __ -> this.createCustomerInTransaction(customer)
    );
  }

  @Override
  public Optional<Customer> findCustomerById(UUID uuid) {
    return delegate.findCustomerById(uuid);
  }

  @PublishEvent(CustomerReplacedInTransactionEvent.class)
  void replaceCustomerInTransaction(Customer customer) {
    delegate.replaceCustomer(customer);
  }

  @Override
  public void replaceCustomer(Customer customer) {
    transactionTemplate.executeWithoutResult(
      __ -> this.replaceCustomerInTransaction(customer)
    );
  }

  @PublishEvent(CustomerDeletedInTransactionEvent.class)
  void deleteCustomerInTransaction(UUID uuid) {
    delegate.deleteCustomer(uuid);
  }

  @Override
  public void deleteCustomer(UUID uuid) {
    transactionTemplate.executeWithoutResult(
      __ -> this.deleteCustomerInTransaction(uuid)
    );
  }

  @Override
  public boolean exists(UUID uuid) {
    return delegate.exists(uuid);
  }

  @Override
  public long count() {
    return delegate.count();
  }
}
