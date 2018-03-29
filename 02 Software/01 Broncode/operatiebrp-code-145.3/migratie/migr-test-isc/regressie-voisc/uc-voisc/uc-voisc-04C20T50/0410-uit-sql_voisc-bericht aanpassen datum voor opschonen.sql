update voisc.bericht set 
tijdstip_verzonden = LOCALTIMESTAMP - interval '75 hours 0 minutes 5 seconds' - interval '1 hour', status = 'IGNORED'
where originator = '3000210' and recipient = '0310010' and status = 'SENT_TO_MAILBOX';
