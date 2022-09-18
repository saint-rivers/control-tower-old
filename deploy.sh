#!/bin/bash

docker-compose down
./gradlew build -x test
docker-compose up --scale task-service=2 --scale user-service=2 -d --build