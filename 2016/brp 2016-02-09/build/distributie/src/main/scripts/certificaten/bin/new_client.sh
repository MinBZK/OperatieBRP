#!/bin/bash
#
# Gebaseerd op werk van Bas Huisman.
#
# Eelco Maljaars
# March 14, 2012
#
# Tim Blommerde
# December 2012
# Maart 2013
#
# Script om een client key materiaal aan te maken voor de BRP

BASEDIR=/opt/ca

function report()
{
  logger $1
  echo $1
}



if [ "$1X" == "X" ];
then
  echo "This script needs a single argument : the client identifier"
  echo "Normally this would be the FQDN (hostname) of the client"
  echo "or the Modernodam username (FFLLL) "
  exit 1;
fi

if [ -e $BASEDIR/var/$1_client.key ];
then
  report "Key material for client $1 already exists, please (re)distribute it"
else
  report "Generating key material for client $1"
  openssl genrsa -out $BASEDIR/var/$1_client.key 2048
  cat $BASEDIR/etc/client-ssl.TPL | sed -e "s/NEWCLIENT/$1/g" > $BASEDIR/var/client-ssl-$1.conf
  openssl req -out $BASEDIR/var/$1_client.csr -key $BASEDIR/var/$1_client.key -new -config $BASEDIR/var/client-ssl-$1.conf
  openssl x509 -req -days 3650 -in $BASEDIR/var/$1_client.csr -CA $BASEDIR/var/brp-ca.crt -CAkey $BASEDIR/var/brp-ca.key \
  -CAcreateserial -out $BASEDIR/var/$1_client.crt -extfile $BASEDIR/var/client-ssl-$1.conf -extensions x509_extensions
  rm $BASEDIR/var/client-ssl-$1.conf $BASEDIR/var/$1_client.csr

  #PKCS12 maken van de client, handig voor keytool import en browser import
  #hele chain in pkcs gooien (is niet nodig)
  openssl pkcs12 -export -in $BASEDIR/var/$1_client.crt -inkey $BASEDIR/var/$1_client.key -out $BASEDIR/var/$1_client.p12 -passout pass:changedit

  #JKS maken van de client
  keytool -importkeystore -srckeystore $BASEDIR/var/$1_client.p12 -destkeystore $BASEDIR/var/$1_client.jks \
  -srcstoretype PKCS12 -srcstorepass changedit -deststoretype JKS -deststorepass changedit

fi

echo "Settings for the BRP server administrator : "
echo " "

CRT_SUBJECT=`openssl x509 -in $BASEDIR/var/$1_client.crt -noout -subject | sed 's/[/]/, /g' | sed -e "s/subject= , //"`
CRT_SERIAL=`openssl x509 -in $BASEDIR/var/$1_client.crt -noout -serial | sed -e "s/serial=//"`
CRT_FINGERPRINT=`openssl x509 -in $BASEDIR/var/$1_client.crt -noout -fingerprint | sed -e "s/SHA1 Fingerprint=//"`
report  "Generated key material for $1 ( $CRT_SERIAL , $CRT_FINGERPRINT ) "


DB_SIGNATURE=`openssl x509 -in $BASEDIR/var/$1_client.crt -noout -text`
DB_SIGNATURE=$(echo $DB_SIGNATURE | sed 's/\(.*\)sha1WithRSAEncryption\(.*\)/\2/' | sed 's/[: \t\r\n]*//g')
CRT_SERIAL2=`echo 'ibase=16;obase=A;'$(openssl x509 -in $BASEDIR/var/$1_client.crt -noout -serial | cut -c8-) | bc`
OIFS=$IFS
IFS=','

CRT_SUBJECT2=

for x in $CRT_SUBJECT
do
    x1=$(echo $x | sed 's/\(.*\)=\(.*\)/\1/' | tr '[a-z]' '[A-Z]' | sed 's/^ *//g')
    x2=$(echo $x | sed 's/\(.*\)=\(.*\)/\2/' | sed 's/^ *//g')

    y="$x1=$x2"
    if [ "$CRT_SUBJECT2" == "" ];
    then
        CRT_SUBJECT2="$y"
    else
        CRT_SUBJECT2="$y, $CRT_SUBJECT2"
    fi
done
IFS=$OIFS

echo " "
report "The following data is required for adding the certificate to the BRP database:"
report  "  - DB SUBJECT:   [$CRT_SUBJECT2]"
report  "  - DB SERIAL:    [$CRT_SERIAL2]"
report  "  - DB SIGNATURE: [$DB_SIGNATURE]"
echo " "

cd $BASEDIR/var/
mkdir sign
mkdir encrypt
cp $1_client.* sign
cp brpserver_publicstore.jks sign
cp $1_client.p12 encrypt
cp brp-ca.crt encrypt
cp $BASEDIR/etc/LEESMIJ .
tar -czvf $BASEDIR/distrib/$1_keymaterial.tgz LEESMIJ sign encrypt
report "Please distribute the file $BASEDIR/distrib/$1_keymaterial.tgz to the end user / tester"
rm -r sign
rm -r encrypt
rm LEESMIJ
exit 0;

