#!/bin/bash
rm -rf lib/*
mkdir -p lib
wget -O lib/jackson-databind-2.9.4.jar http://repo1.maven.org/maven2/com/fasterxml/jackson/core/jackson-databind/2.9.4/jackson-databind-2.9.4.jar
wget -O lib/junit-4.12.jar http://repo1.maven.org/maven2/junit/junit/4.12/junit-4.12.jar
