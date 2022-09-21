#!/bin/bash

SERVICE="control-tower_-task-service_1"

./gradlew build &&
  docker stop "$SERVICE" &&
  docker rm "$SERVICE" &&
  docker-compose up -d --build task-service
