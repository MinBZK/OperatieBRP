select count(*) as aantal from voisc.bericht where status not in ('SENT_TO_MAILBOX', 'SENT_TO_ISC', 'IGNORED', 'PROCESSED_IMMEDIATELY', 'ERROR');
