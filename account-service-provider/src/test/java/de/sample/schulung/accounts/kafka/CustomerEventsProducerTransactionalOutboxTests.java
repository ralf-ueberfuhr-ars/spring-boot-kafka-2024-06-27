package de.sample.schulung.accounts.kafka;

import de.sample.schulung.accounts.domain.AutoConfigureDisabledInitialization;
import de.sample.schulung.accounts.domain.Customer;
import de.sample.schulung.accounts.domain.CustomersService;
import de.sample.schulung.accounts.kafka.AutoConfigureEmbeddedKafka.KafkaTestContext;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.Month;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.from;

@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureEmbeddedKafka
@AutoConfigureDisabledInitialization
@AutoConfigureTransactionalOutboxEnabled
class CustomerEventsProducerTransactionalOutboxTests {

  @Autowired
  CustomersService service;
  @Autowired
  KafkaTestContext<UUID, CustomerEventRecord> context;


  @Test
  void shouldProduceCustomerEventWhenCustomerIsCreated() {
    var customer = new Customer();
    customer.setName("Tom Mayer");
    customer.setState(Customer.CustomerState.ACTIVE);
    customer.setDateOfBirth(LocalDate.of(2000, Month.DECEMBER, 20));

    service.createCustomer(customer);

    assertThat(context.poll(8, TimeUnit.SECONDS))
      .isPresent()
      .get()
      .returns(KafkaConstants.CUSTOMER_EVENTS_TOPIC, from(ConsumerRecord::topic))
      .extracting(ConsumerRecord::value)
      .returns("created", from(CustomerEventRecord::eventType))
      .returns(customer.getUuid(), from(CustomerEventRecord::uuid))
      .extracting(CustomerEventRecord::customer)
      .returns("Tom Mayer", from(CustomerRecord::name));

  }

}
