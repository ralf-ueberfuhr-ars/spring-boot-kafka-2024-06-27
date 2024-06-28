package de.sample.schulung.accounts.kafka.transactions;

import de.sample.schulung.accounts.domain.Customer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity(name = "CustomerEvent")
@Table(name = "CUSTOMER_EVENTS")
@Getter
@Setter
public class CustomerEventEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @Column(nullable = false)
  private String eventType;
  @Column(nullable = false)
  private UUID customerUuid;
  private String customerName;
  private LocalDate customerBirthdate;
  private Customer.CustomerState customerState;

}
