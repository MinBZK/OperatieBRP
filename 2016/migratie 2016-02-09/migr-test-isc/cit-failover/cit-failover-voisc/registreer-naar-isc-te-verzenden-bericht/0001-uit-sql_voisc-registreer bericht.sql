insert into voisc.bericht(
	id, 
	originator,
	recipient,
	status, 
	bericht_data,
	message_id,
	tijdstip_ontvangst, 
	version
) values(
	nextval('voisc.bericht_id_sequence'), 
	'0626010',
	'3000200',
	'RECEIVED_FROM_MAILBOX', 
	'BERICHTINHOUD-CIT-BERICHT-NAAR-ISC-TE-VERZENDEN',
	concat('CIT', currval('voisc.bericht_id_sequence')),
	now(),
	0
);