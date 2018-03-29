delete from voisc.lo3_mailbox where instantiecode in (900008, 900009, 900010);

insert into voisc.lo3_mailbox(id, instantietype, instantiecode, mailboxnr, mailboxpwd) values(nextval('voisc.lo3_mailbox_id_sequence'), 'A', 900008, 9000080, 'password');
insert into voisc.lo3_mailbox(id, instantietype, instantiecode, mailboxnr, mailboxpwd) values(nextval('voisc.lo3_mailbox_id_sequence'), 'A', 900009, 9000090, 'password');
insert into voisc.lo3_mailbox(id, instantietype, instantiecode, mailboxnr, mailboxpwd) values(nextval('voisc.lo3_mailbox_id_sequence'), 'A', 900010, 9000100, 'password');
