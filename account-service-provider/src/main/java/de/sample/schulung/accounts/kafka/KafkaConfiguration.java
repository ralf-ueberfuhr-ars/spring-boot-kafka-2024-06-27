package de.sample.schulung.accounts.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfiguration {

  // Topic beim Start der Anwendung erzeugen
  // -> nur für lokale Tests, NICHT für Produktion
  // siehe application.yml: spring.kafka.admin.auto-create
  @Bean
  public NewTopic customerEventsTopic() {
    return TopicBuilder
      .name(KafkaConstants.CUSTOMER_EVENTS_TOPIC)
      // .partitions(3)
      // .replicas(3)
      .build();
  }

}
