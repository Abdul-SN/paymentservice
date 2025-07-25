# Payment Service CDC Setup

This project uses [Debezium](https://debezium.io/) to stream database changes to Kafka.

## Running the stack

```bash
# start containers
cd src/main/resources
docker compose up -d
```

The compose file starts PostgreSQL with logical replication, Kafka and Debezium Connect.

## Register connector

After the containers are up, register the connector:

```bash
curl -X POST -H "Content-Type: application/json" \
     --data-binary @src/main/resources/debezium/payment-connector.json \
     http://localhost:8083/connectors
```

Debezium will publish changes from tables `payment_transaction` and `refund` to topics:
`dbserver1.public.payment_transaction` and `dbserver1.public.refund`.

Applications interested in these events can subscribe to these Kafka topics.

## CDC consumer

The `payment-consumer` module contains a small Spring Boot application that reads events from the Debezium topics and logs them. Start it after the Docker stack is running:

```bash
mvn -f payment-consumer spring-boot:run
```

## Generating sample data

Set `sampledata.enabled=true` in `src/main/resources/application.yaml` to generate a few accounts and transactions automatically on startup. You can tweak the numbers with `sampledata.accounts` and `sampledata.transactions`.

