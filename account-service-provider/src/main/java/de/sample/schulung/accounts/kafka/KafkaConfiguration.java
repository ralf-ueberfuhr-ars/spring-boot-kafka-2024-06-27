package de.sample.schulung.accounts.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfiguration {

  @Bean
  public NewTopic customerEventsTopic() {
    return TopicBuilder
      .name(KafkaConstants.DEFAULT_CUSTOMER_EVENTS_TOPIC)
      .build();
  }

}
