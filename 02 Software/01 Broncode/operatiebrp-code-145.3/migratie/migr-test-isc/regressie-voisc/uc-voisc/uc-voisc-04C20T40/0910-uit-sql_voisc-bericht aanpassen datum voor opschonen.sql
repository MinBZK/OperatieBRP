update voisc.bericht set 
tijdstip_verzonden = LOCALTIMESTAMP - interval '74 hours 59 minutes 55 seconds'
where originator = '0329010' and recipient = '3000210' and status = 'SENT_TO_ISC';
