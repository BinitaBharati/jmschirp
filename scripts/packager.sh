#!/usr/bin/env bash
cd ..
lein clean
lein uberjar
mkdir -p target/jmschirp-0.1.0
cp target/jmschirp-0.1.0-standalone.jar target/jmschirp-0.1.0
cp scripts/README.txt target/jmschirp-0.1.0
cp scripts/log4j.properties target/jmschirp-0.1.0
cd target
tar -czvf jmschirp-0.1.0.tar.gz jmschirp-0.1.0/ 
zip -r jmschirp-0.1.0.zip jmschirp-0.1.0/
