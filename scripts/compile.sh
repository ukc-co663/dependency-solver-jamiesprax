#!/bin/bash
chmod 0777 scripts/compile.sh scripts/install_deps.sh scripts/run_tests.sh scripts/solve
CLASSPATH=classes:$(ls lib/* | sed 's/ /:/')
JAVAS=$(find src -name '*.java')
mkdir -p classes
CLASSPATH=$(echo $CLASSPATH | tr ' ' ':')
javac -cp $CLASSPATH -sourcepath src -d classes $JAVAS