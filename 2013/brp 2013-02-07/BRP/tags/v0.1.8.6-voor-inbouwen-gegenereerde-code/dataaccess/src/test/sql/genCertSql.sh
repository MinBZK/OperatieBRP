for i in 1 2 3 4 5  ; do

echo -n INSERT INTO autaut.certificaat \(subject, serial, signature\) VALUES \(\'
echo -n $(openssl x509 -in certificate_$i.crt -subject -noout) | sed 's/.*CN=/CN=/g'
echo -n \', x\'
echo -n $(openssl x509 -in certificate_$i.crt -serial -noout) | sed 's/serial=//g'
echo \'::bigint, \'ff\'\)\;

done
