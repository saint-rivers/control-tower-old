#!/bin/bash

SERVICE="control-tower_task-service_1"

./gradlew build &&
  docker stop "$SERVICE" &&
  docker rm "$SERVICE" &&
  docker-compose up -d --build task-service

SERVICE="control-tower_user-service_1"

docker stop "$SERVICE" &&
  docker rm "$SERVICE" &&
  docker-compose up -d --build user-service
