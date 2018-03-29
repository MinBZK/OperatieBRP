delete from voisc.lo3_mailbox where partijcode in ('900050', '900051', '900052');

insert into voisc.lo3_mailbox(id, verzender, partijcode, mailboxnr, mailboxpwd) values(nextval('voisc.lo3_mailbox_id_sequence'), '3000200', '900050', '9000500', 'password');
insert into voisc.lo3_mailbox(id, verzender, partijcode, mailboxnr, mailboxpwd) values(nextval('voisc.lo3_mailbox_id_sequence'), '3000200', '900051', '9000510', 'password');
insert into voisc.lo3_mailbox(id, verzender, partijcode, mailboxnr, mailboxpwd) values(nextval('voisc.lo3_mailbox_id_sequence'), '3000200', '900052', '9000520', 'password');
