package de.sample.schulung.statistics.kafka;

import de.sample.schulung.statistics.domain.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerEventKafkaDtoMapper {

  public Customer map(CustomerEventKafkaDto source) {
    return Customer.builder()
      .uuid(source.uuid())
      .dateOfBirth(source.customer() != null ? source.customer().getDateOfBirth() : null)
      .build();
  }

}
