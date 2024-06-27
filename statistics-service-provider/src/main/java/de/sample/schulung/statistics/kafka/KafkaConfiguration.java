package de.sample.schulung.statistics.kafka;

import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
@Slf4j
public class KafkaConfiguration {

  // Übung 2
  @Bean
  public DefaultErrorHandler errorHandler() {
    final var backoff = new FixedBackOff(5000, 3);
    final var result = new DefaultErrorHandler(
      (consumerRecord, exception) -> log.error(
        "Couldn't process message: {}; {}",
        consumerRecord.value(),
        exception.toString()
      ),
      backoff
    );

    result.addNotRetryableExceptions(ValidationException.class);
    return result;
  }

}
