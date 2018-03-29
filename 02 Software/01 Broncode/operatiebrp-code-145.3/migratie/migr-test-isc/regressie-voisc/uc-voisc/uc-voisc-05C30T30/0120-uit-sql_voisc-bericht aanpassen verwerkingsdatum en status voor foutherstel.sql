update voisc.bericht set 
tijdstip_verzonden = (SELECT date_trunc('day',LOCALTIMESTAMP - interval '75 hours') - interval '1 milliseconds'),
status = 'ERROR'
where originator = '3000210' and recipient = '0402010' and status = 'RECEIVED_FROM_ISC';