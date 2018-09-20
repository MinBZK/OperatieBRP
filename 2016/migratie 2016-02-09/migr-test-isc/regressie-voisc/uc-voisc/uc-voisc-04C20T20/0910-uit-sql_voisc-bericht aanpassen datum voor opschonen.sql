update voisc.bericht set 
tijdstip_verzonden = (SELECT date_trunc('day',LOCALTIMESTAMP - interval '75 hours') - interval '1 milliseconds')
where originator = '0329010' and recipient = '3000200' and status = 'SENT_TO_ISC';