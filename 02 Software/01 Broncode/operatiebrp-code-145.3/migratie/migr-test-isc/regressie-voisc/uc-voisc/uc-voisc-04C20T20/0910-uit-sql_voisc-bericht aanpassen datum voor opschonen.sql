update voisc.bericht set 
tijdstip_verzonden = LOCALTIMESTAMP - interval '75 hours 0 minutes 5 seconds' - interval '1 hour'
where originator = '0329010' and recipient = '3000200' and status = 'SENT_TO_ISC';
