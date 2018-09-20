update voisc.bericht set 
tijdstip_in_verwerking = LOCALTIMESTAMP - interval '6 hours'
where recipient = '0344010' and status = 'SENDING_TO_MAILBOX';