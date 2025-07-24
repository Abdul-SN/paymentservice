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
     --data @debezium/payment-connector.json \
     http://localhost:8083/connectors
```

Debezium will publish changes from tables `payment_transaction` and `refund` to topics:
`dbserver1.public.payment_transaction` and `dbserver1.public.refund`.

Applications interested in these events can subscribe to these Kafka topics.
