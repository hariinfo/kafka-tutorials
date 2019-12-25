# Kafka Learning Path
* Code Setup - Start Kafka server and create a new topic

```
./bin/zookeeper-server-start.sh config/zookeeper.properties
./bin/kafka-server-start.sh config/server.properties
./bin/kafka-topics.sh --zookeeper localhost:2181 --create --topic events --replication-factor 1 --partitions 4

```

## Producer
## Consumer
## Kafka Stream
