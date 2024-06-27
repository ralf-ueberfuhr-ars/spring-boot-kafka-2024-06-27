package de.sample.schulung.accounts.kafka;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.support.JacksonUtils;

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

  @EventListener(ContextRefreshedEvent.class)
  public void configureJsonSerializer() {
    JacksonUtils
      .enhancedObjectMapper()
      .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
  }

}
