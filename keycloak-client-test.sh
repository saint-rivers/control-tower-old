#!/bin/bash

sudo ./gradlew build &&
  docker stop control-tower_keycloak-client_1 &&
  docker rm control-tower_keycloak-client_1 &&
  docker-compose up -d --build keycloak-clien
