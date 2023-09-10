#!/bin/bash

./gradlew clean build

mkdir ~/.jeval

mv ./build/libs/big-decimal-expression-calculator-1.0.jar ~/.jeval/jeval-big-decimal-expression-calculator-1.0.jar

sudo cp ./jeval /usr/local/bin/

sudo mkdir /usr/local/share/man/man1/

sudo cp ./jeval-manual /usr/local/share/man/man1/jeval.1

sudo mandb

echo "jeval has been successfully installed! "
echo "Usage: jeval 'expression', for more information type man jeval"