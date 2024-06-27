# Spring Boot - Kafka

[![CI Build](https://github.com/ralf-ueberfuhr-ars/spring-boot-kafka-2024-06-27/actions/workflows/ci.yml/badge.svg)](https://github.com/ralf-ueberfuhr-ars/spring-boot-kafka-2024-06-27/actions/workflows/ci.yml)

## Inhalte:

In diesem Projekt findest Du folgende Projekte:

- [Account Service Provider](account-service-provider) - REST-Service für die Verwaltung von Kundendaten
- [Statistics Service Provider](statistics-service-provider) - REST-Service für die Ausgabe von Statistiken zu den
  Kundendaten

Beide Projekte verwenden
- Spring MVC Controller
- Bean Validation
- Spring Data JPA mit einer H2-Datenbank

## Bauen und Starten

Du kannst die jeweiligen Projekte bauen und starten mit folgenden Befehlen:

```bash
# Ins jeweilige Projekt wechseln
cd <ordner>
# Bauen
mvn package
# Starten
java -jar target/<file>.jar
# oder
mvn spring-boot:run
```
