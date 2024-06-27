package de.sample.schulung.accounts.kafka;

import de.sample.schulung.accounts.domain.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerKafkaDtoMapper {

  CustomerKafkaDto map(Customer source);

  default String mapState(Customer.CustomerState source) {
    return switch (source) {
      case ACTIVE -> "active";
      case LOCKED -> "locked";
      case DISABLED -> "disabled";
    };
  }

}
