update voisc.bericht set 
tijdstip_verzonden = LOCALTIMESTAMP - interval '74 hours 59 minutes 55 seconds'
where originator = '3000210' and recipient = '0310010' and status = 'SENT_TO_MAILBOX';
