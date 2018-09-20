[ req ] 
distinguished_name     = req_distinguished_name 
prompt                 = no

[ req_distinguished_name ] 
O = Programma mGBA
OU = Modernodam
emailAddress = support@modernodam.nl
C = NL
ST = Zuid-Holland
L = Den Haag
CN = NEWCLIENT


[ x509_extensions ]
basicConstraints 	= critical,CA:FALSE
keyUsage=digitalSignature,keyEncipherment,dataEncipherment,keyAgreement
extendedKeyUsage=serverAuth,clientAuth

