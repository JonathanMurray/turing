#!/usr/bin/env bash

cd src/main
echo "Current directory: $PWD"

echo "Compiling Java source code..."
javac turing/*.java

echo "Running main class..."
java turing/Main
