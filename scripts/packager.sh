#!/usr/bin/env bash

VERSION=0.1.1
PWD=`pwd | awk -F/ '{ print $NF; }'`

if [ $PWD !=  'jmschirp' ]; then 
  echo "You must run the script from the project root directory"
  exit 1
fi

lein clean
lein uberjar
mkdir -p target/jmschirp-$VERSION
cp target/jmschirp-$VERSION-standalone.jar target/jmschirp-$VERSION
cp scripts/README.txt target/jmschirp-$VERSION
cp scripts/log4j.properties target/jmschirp-$VERSION
cd target
tar -czvf jmschirp-$VERSION.tar.gz jmschirp-$VERSION/ 
zip -r jmschirp-$VERSION.zip jmschirp-$VERSION/

