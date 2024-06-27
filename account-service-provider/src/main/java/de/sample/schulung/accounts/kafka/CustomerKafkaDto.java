package de.sample.schulung.accounts.kafka;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CustomerKafkaDto {

  private String name;
  private LocalDate dateOfBirth;
  private String state;

}
