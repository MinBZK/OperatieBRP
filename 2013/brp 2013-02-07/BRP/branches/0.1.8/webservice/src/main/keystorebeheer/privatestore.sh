#!/bin/bash

# Dit script genereert een "privatestore.jks" bestand met behulp van een "server.crt" en "server.key" bestand

echo -n "kies een wachtwoord voor de private keystore : "
read -e PRIVATESTORE_PW

echo -n "kies een wachtwoord voor de server key : "
read -e SERVER_KEY_PW

rm -fv privatestore.jks tussenbestand.p12

echo p12 maken

openssl pkcs12 -export -in server.crt -inkey server.key -out tussenbestand.p12 -passout pass:$SERVER_KEY_PW

echo p12 converteren naar JKS

keytool -v -importkeystore -srckeystore tussenbestand.p12 -srcstoretype PKCS12 -destkeystore privatestore.jks -deststoretype JKS -srcstorepass $SERVER_KEY_PW -deststorepass $PRIVATESTORE_PW -noprompt

rm -fv tussenbestand.p12
