package de.sample.schulung.accounts.kafka.interceptor;

/**
 * Return a KafkaRecord from a {@link KafkaProducer} method
 * to get a key and a value sent.
 */
public record KafkaRecord<K, V>(
  K key,
  V value
) {

}
