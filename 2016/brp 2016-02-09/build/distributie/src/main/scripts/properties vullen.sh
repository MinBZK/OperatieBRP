#!/bin/bash

# util scriptje om snel alle properties te find/replacen

PROPERTIES_DIR=/home/dennis/project/brpcode/build/distributie/target/brpprops


cd $PROPERTIES_DIR



sed -i 's/<database hostname>/localhost/g' *.properties
sed -i 's/<database port>/5432/g' *.properties
sed -i 's/<database username>/brp/g' *.properties
sed -i 's/<database password>/brp/g' *.properties

sed -i 's/<brp routering centrale host>/localhost/g' *.properties
sed -i 's/<brp routering centrale poort>/61616/g' *.properties

sed -i 's/<ISC host>/localhost/g' *.properties
sed -i 's/<directory 1>/\/tmp\/activemq\/data/g' *.properties
sed -i 's/<directory 2>/\/tmp\/activemq\/scheduler/g' *.properties


sed -i 's/<publicstore 1>/abc/g' *.properties
sed -i 's/<publicstore password 1>/abcd/g' *.properties
sed -i 's/<privatestore 1>/abcde/g' *.properties
sed -i 's/<privatestore password 1>/abcdef/g' *.properties
sed -i 's/<privatestore keypair alias 1>/abcdef/g' *.properties
sed -i 's/<privatestore key password 1>/abcdefg/g' *.properties