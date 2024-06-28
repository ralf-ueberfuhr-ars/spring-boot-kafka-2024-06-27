package de.sample.schulung.accounts.kafka;

import org.springframework.test.context.TestPropertySource;

import java.lang.annotation.*;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@TestPropertySource(
  properties = "application.persistence.enable-transactional-events=false"
)
public @interface AutoConfigureTransactionalOutboxDisabled {
}
