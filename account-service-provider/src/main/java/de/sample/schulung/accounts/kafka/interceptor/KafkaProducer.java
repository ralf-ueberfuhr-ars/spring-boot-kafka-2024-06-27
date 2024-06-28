package de.sample.schulung.accounts.kafka.interceptor;

import java.lang.annotation.*;

/**
 * Annotate a method to get the return value
 * sent to a Kafka topic. The method can return a simple object that is then sent as a value
 * without a key, or it can return an instance of {@link KafkaRecord}.
 * If the method returns <tt>null</tt> (or has a void return type), no message is produced.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface KafkaProducer {

  /**
   * The name of the topic.
   *
   * @return the name of the topic
   */
  String topic();

  /**
   * The partition. Leave empty, if the Partitioner should do the job.
   *
   * @return the partition
   */
  int partition() default -1;

}