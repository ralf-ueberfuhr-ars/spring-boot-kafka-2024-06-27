package de.sample.schulung.accounts.domain;

import org.springframework.test.context.TestPropertySource;

import java.lang.annotation.*;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@TestPropertySource(
  properties = """
    application.customers.initialization.enabled=true
    """
)
public @interface AutoConfigureEnabledInitialization {
}
