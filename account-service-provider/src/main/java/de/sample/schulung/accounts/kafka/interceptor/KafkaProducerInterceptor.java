package de.sample.schulung.accounts.kafka.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.autoproxy.AbstractBeanFactoryAwareAdvisingPostProcessor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings("unused")
public class KafkaProducerInterceptor
  extends AbstractBeanFactoryAwareAdvisingPostProcessor
  implements InitializingBean {

  private final MethodInterceptor advice;

  public KafkaProducerInterceptor(final KafkaTemplate<Object, Object> kafkaTemplate) {
    this.advice = invocation -> {
      final var result = invocation.proceed();
      if (result == null) {
        return null;
      }
      final var annotation = AnnotationUtils.findAnnotation(invocation.getMethod(), KafkaProducer.class);
      if (annotation != null) {
        final Object key;
        final Object value;
        if (result instanceof KafkaRecord<?, ?> r) {
          key = r.key();
          value = r.value();
        } else {
          key = null;
          value = result;
        }
        //noinspection DataFlowIssue
        kafkaTemplate.send(
          annotation.topic(),
          annotation.partition() < 0 ? null : annotation.partition(),
          key,
          value
        );
      }
      return result;
    };
  }

  @Override
  public void afterPropertiesSet() {
    Pointcut pointcut = new AnnotationMatchingPointcut(null, KafkaProducer.class, true);
    this.advisor = new DefaultPointcutAdvisor(pointcut, advice);
  }


}