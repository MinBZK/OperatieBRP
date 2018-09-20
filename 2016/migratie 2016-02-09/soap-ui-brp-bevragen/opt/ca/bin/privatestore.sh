#!/bin/bash

# Dit script genereert een "privatestore.jks" bestand met behulp van een "brp-server.crt" en "brp-server.key" bestand

umask 077

BASEDIR=/opt/ca


if [ ! -d $BASEDIR ];
then
  report "This is all wrong, are you sure you understand $0?"
  report "Please read me and reconfigure me, I'm a simple shell script"
  exit 666;
fi


echo -n "kies een wachtwoord voor de private keystore : "
read -e PRIVATESTORE_PW

echo -n "kies een wachtwoord voor de server key : "
read -e SERVER_KEY_PW

rm -rv $BASEDIR/var/privatestore.jks $BASEDIR/var/tussenbestand.p12

echo p12 maken

openssl pkcs12 -export -in $BASEDIR/var/brp-server.crt -inkey $BASEDIR/var/brp-server.key -out $BASEDIR/var/tussenbestand.p12 -passout pass:$SERVER_KEY_PW

echo p12 converteren naar JKS

keytool -v -importkeystore -srckeystore $BASEDIR/var/tussenbestand.p12 -srcstoretype PKCS12 -destkeystore $BASEDIR/var/privatestore.jks -deststoretype JKS -srcstorepass $SERVER_KEY_PW -deststorepass $PRIVATESTORE_PW -noprompt

rm -fv $BASEDIR/var/tussenbestand.p12
