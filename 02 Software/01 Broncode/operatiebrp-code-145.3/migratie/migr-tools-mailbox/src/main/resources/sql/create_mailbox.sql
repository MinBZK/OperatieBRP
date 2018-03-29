drop schema if exists mailbox cascade;

create schema mailbox;

create sequence mailbox.ms_sequence_nr;

create table mailbox.mailbox (
	mailboxnr VARCHAR(7) NOT NULL, 
	status INTEGER, 
	password VARCHAR(8)
);

alter table mailbox.mailbox add constraint mailbox_pk primary key (mailboxnr);

create table mailbox.entry (
	originator_or_recipient VARCHAR(7), 
	ms_sequence_id BIGINT NOT NULL, 
	mesg VARCHAR(20000), 
	status INTEGER, message_id VARCHAR(12), 
	cross_reference VARCHAR(12), 
	notification_request INTEGER, 
	mailboxnr VARCHAR(7) NOT NULL,
	CONSTRAINT pk_mailbox_entry PRIMARY KEY (ms_sequence_id, mailboxnr)
);

create index mailbox_mailbox_status on mailbox.entry(status);