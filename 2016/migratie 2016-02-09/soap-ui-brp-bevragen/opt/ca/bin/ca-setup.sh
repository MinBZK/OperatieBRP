#!/bin/bash
#
# Script gebaseerd op werk van Bas Huisman.
#
# Eelco Maljaars
# Mar 14, 2012
#
# t.b.v. BRP whitebox / proeftuin of andere omgevingen


function report ()
{
  logger $1
  echo $1
}

umask 077

BASEDIR=/opt/ca

if [ ! -d $BASEDIR ];
then
  report "This is all wrong, are you sure you understand $0?"
  report "Please read me and reconfigure me, I'm a simple shell script"
  exit 666;
fi


if [ "X$1" == "X" ];
then
  echo "$0 requires an argument, which can be : "
  echo " cleanall  - removes all current key material (DANGEROUS) "
  echo " server    - creates a new key/cert pair for a server if one does not exist "
  echo " ca        - creates a new certificate authority of one does not exist "
  echo " "
  exit 1;
fi

if [ $1 == "cleanall" ];
then
  report "Getting rid of all key material in $BASEDIR/var"
  ARCHIVE=$RANDOM
  cd /
  tar -czvf /tmp/OLDKEYS_$ARCHIVE.tgz $BASEDIR/var
  report "Created backup in /tmp (OLDKEYS_$ARCHIVE.tgz) "
  rm -f $BASEDIR/var/*.crt $BASEDIR/var/*.key $BASEDIR/var/*.csr $BASEDIR/var/*.p12 $BASEDIR/var/*.jks
fi

##CA
if [ $1 == "ca" ];
then
  if [ -e $BASEDIR/var/brp-ca.key ];
  then
    report "Attempt to recreate BRP CA while an old one exists"
    report "Please read the documentation or run cleanall if you are sure"
    report "you know what you are doing"
  else
    report "Creating a new certificate authority for the BRP system"
    openssl genrsa -out $BASEDIR/var/brp-ca.key 4096
    openssl req -new -x509 -days 7300 -key $BASEDIR/var/brp-ca.key -out $BASEDIR/var/brp-ca.crt -config $BASEDIR/etc/ca-ssl.conf
    keytool -v -importcert -file $BASEDIR/var/brp-ca.crt -keystore $BASEDIR/var/brp-ca.jks -storepass changedit -alias staat_der_mgba_root_ca -noprompt
  fi
fi



##KERN SOAP SERVER (ssl/tls server side, Apache)
if [ $1 == "server" ];
then
  if [ -e $BASEDIR/var/brp-server.key ];
  then
    report "Attempt to recreate BRP server key while an old one exists"
    report "Please read the documentation or run cleanall if you are sure"
    report "you know what you are doing"
  else
    report "Creating a new server key for the BRP system"
    openssl genrsa -out $BASEDIR/var/brp-server.key 2048
    openssl req -out $BASEDIR/var/brp-server.csr -key $BASEDIR/var/brp-server.key -new -config $BASEDIR/etc/server-ssl.conf
    openssl x509 -req -days 7300 -in $BASEDIR/var/brp-server.csr -CA $BASEDIR/var/brp-ca.crt -CAkey $BASEDIR/var/brp-ca.key \
    -CAcreateserial -out $BASEDIR/var/brp-server.crt -extfile $BASEDIR/etc/server-ssl.conf -extensions x509_extensions
    keytool -import -keystore $BASEDIR/var/brpserver_publicstore.jks -file $BASEDIR/var/brp-server.crt -alias server -storepass hallo123 -noprompt
  fi
fi

##KERN SOAP CLIENT (ssl/tls client side, Java(?) client)

#openssl genrsa -out kern_soap_client.key 2048
#openssl req -out kern_soap_client.csr -key kern_soap_client.key -new -config kern_soap_client.cnf
#openssl x509 -req -days 365 -in kern_soap_client.csr -CA ca.crt -CAkey ca.key -CAcreateserial -out kern_soap_client.crt -extfile kern_soap_client.cnf -extensions x509_extensions

#PKCS12 maken van de client, handig voor keytool import en browser import

#hele chain in pkcs gooien (is niet nodig)
#openssl pkcs12 -export -chain -in kern_soap_client.crt -inkey kern_soap_client.key -out kern_soap_client.p12 -CAfile ca.crt -passout pass:hallo123
#openssl pkcs12 -export -in kern_soap_client.crt -inkey kern_soap_client.key -out kern_soap_client.p12 -passout pass:hallo123
#keytool -v -importkeystore -srckeystore kern_soap_client.p12 -srcstoretype PKCS12 -destkeystore kern_soap_client.jks -deststoretype JKS -srcstorepass hallo123 -deststorepass hallo123 -noprompt

