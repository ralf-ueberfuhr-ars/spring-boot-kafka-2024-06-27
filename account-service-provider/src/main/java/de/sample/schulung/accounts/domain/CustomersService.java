package de.sample.schulung.accounts.domain;

import de.sample.schulung.accounts.domain.events.CustomerCreatedEvent;
import de.sample.schulung.accounts.domain.events.CustomerDeletedEvent;
import de.sample.schulung.accounts.domain.events.CustomerReplacedEvent;
import de.sample.schulung.accounts.shared.interceptors.PublishEvent;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Validated
@Service
public class CustomersService {

  private final Map<UUID, Customer> customers = new HashMap<>();

  public Stream<Customer> getCustomers() {
    return customers
      .values()
      .stream();
  }

  public Stream<Customer> getCustomersByState(@NotNull Customer.CustomerState state) { // TODO enum?
    return this.getCustomers()
      .filter(customer -> state.equals(customer.getState()));
  }

  @PublishEvent(CustomerCreatedEvent.class)
  public void createCustomer(@Valid Customer customer) {
    var uuid = UUID.randomUUID();
    customer.setUuid(uuid);
    this.customers.put(customer.getUuid(), customer);
  }

  public Optional<Customer> findCustomerById(@NotNull UUID uuid) {
    return Optional.ofNullable(
      this.customers.get(uuid)
    );
  }

  @PublishEvent(CustomerReplacedEvent.class)
  public void replaceCustomer(@Valid Customer customer) {
    if (this.exists(customer.getUuid())) {
      this.customers.put(customer.getUuid(), customer);
    } else {
      throw new NotFoundException();
    }
  }

  @PublishEvent(CustomerDeletedEvent.class)
  public void deleteCustomer(@NotNull UUID uuid) {
    if (this.exists(uuid)) {
      this.customers.remove(uuid);
    } else {
      throw new NotFoundException();
    }
  }

  public boolean exists(UUID uuid) {
    return this.customers.containsKey(uuid);
  }

}
