#!/bin/bash

# Dit script genereert een "client.p12" en "client.jks" bestand met behulp van een "client.key", "client.crt" en "server.crt" bestand
# Daarna wordt het client certificaat toegevoegd aan een bestaand of nieuw "publicstore.jks" bestand
# Tot slot wordt achtereenvolgens de signature, het serial number en het subject afgedrukt die nodig zijn voor het database record

echo -n "kies een wachtwoord voor de client keystore : "
read -e CLIENT_KEYSTORE_PW

echo -n "kies een wachtwoord voor de client key : "
read -e CLIENT_KEY_PW

echo -n "wachtwoord van de (bestaande of nieuwe) publicstore.jks : "
read -e PUBLICSTORE_PW

rm -fv client.jks client.p12

echo p12 maken

openssl pkcs12 -export -in client.crt -inkey client.key -out client.p12 -passout pass:$CLIENT_KEY_PW

echo p12 converteren naar JKS

keytool -v -importkeystore -srckeystore client.p12 -srcstoretype PKCS12 -destkeystore client.jks -deststoretype JKS -srcstorepass $CLIENT_KEY_PW -deststorepass $CLIENT_KEYSTORE_PW -noprompt

echo server certificaat toevoegen aan client jks

keytool -importcert -file server.crt -keystore client.jks -alias server -storepass $CLIENT_KEYSTORE_PW

echo client certificaat toevoegen aan publicstore.jks \(als deze store nog niet bestaat dan wordt hij aangemaakt\)

ALIAS=$(date "+%Y%m%d%H%M%S")
keytool -importcert -file client.crt -keystore publicstore.jks -alias $ALIAS -storepass $PUBLICSTORE_PW

echo gegevens van certificaat afdrukken voor database

openssl x509 -in client.crt -text -noout
openssl x509 -in client.crt -serial -noout
openssl x509 -in client.crt -subject -noout
