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
	'3000200',
	'0626010',
	'RECEIVED_FROM_ISC', 
	'BERICHTINHOUD-CIT-BERICHT-NAAR-MAILBOX-TE-VERZENDEN',
	concat('CIT', currval('voisc.bericht_id_sequence')),
	now(),
	0
);