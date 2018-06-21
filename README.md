<!-- TOC -->

- [Commands](#commands)
    - [Install load balancer](#install-load-balancer)
    - [Start load balancer](#start-load-balancer)
    - [Build](#build)
    - [Run 1st instance](#run-1st-instance)
    - [Run 2nd instance](#run-2nd-instance)
- [API](#api)
    - [Increase counter](#increase-counter)
    - [Get current counter](#get-current-counter)

<!-- /TOC -->

This project shows one of the ways of implementing distributed site hit counter using GCounter (CRDT).

## Commands

### Install load balancer

```bash
npm install -g loadbalancer
```

###  Start load balancer

```bash
loadbalancer start --config load-balancer-config/config.json 

```

### Build

```bash
./gradlew clean build
```

### Run 1st instance

```bash
java -jar build/libs/distributed-site-hit-counter-0.0.1-SNAPSHOT.jar --server.port=9091

```

### Run 2nd instance

```bash
java -jar build/libs/distributed-site-hit-counter-0.0.1-SNAPSHOT.jar --server.port=9092

```

## API

### Increase counter

```bash
curl -X GET \
  http://localhost:9000/increment 
```

### Get current counter

```bash
curl -X GET \
  http://localhost:9000/counter 
```