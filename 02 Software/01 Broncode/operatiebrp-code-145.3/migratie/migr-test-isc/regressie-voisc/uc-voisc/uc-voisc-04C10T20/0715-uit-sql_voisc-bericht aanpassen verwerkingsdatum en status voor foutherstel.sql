update voisc.bericht set 
tijdstip_in_verwerking = LOCALTIMESTAMP - interval '6 hours',
status = 'SENDING_TO_ISC'
where originator = '0358010' and recipient = '3000200' and status = 'RECEIVED_FROM_MAILBOX';