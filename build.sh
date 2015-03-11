#!/bin/bash

M2_EXTRA_OPTS=-Dmaven.test.skip=true
JAVA_HOME=/usr/lib/jvm/java-7-openjdk-amd64

# Builds mlo-client
mvn clean package $M2_EXTRA_OPTS -f mlo-client/pom.xml

