insert into voisc.lo3_mailbox(id, partijcode, mailboxnr, mailboxpwd, verzender)
select nextval('voisc.lo3_mailbox_id_sequence'), a.nr as partijcode, concat(a.nr,'0') as mailboxnr, 'password' as mailboxpwd, '3000200' as verzender
from (select '999971' as nr) AS a
where not exists (select id from voisc.lo3_mailbox where cast(partijcode as varchar) = cast(a.nr as varchar));

insert into voisc.lo3_mailbox(id, partijcode, mailboxnr, mailboxpwd, verzender)
select nextval('voisc.lo3_mailbox_id_sequence'), a.nr as partijcode, concat(a.nr,'0') as mailboxnr, 'password' as mailboxpwd, '3000210' as verzender
from (select '062602' as nr) AS a
where not exists (select id from voisc.lo3_mailbox where cast(partijcode as varchar) = cast(a.nr as varchar));

insert into voisc.lo3_mailbox(id, partijcode, mailboxnr, mailboxpwd, verzender)
select nextval('voisc.lo3_mailbox_id_sequence'), a.nr as partijcode, concat(a.nr,'0') as mailboxnr, 'password' as mailboxpwd, '3000210' as verzender
from (select '062701' as nr) AS a
where not exists (select id from voisc.lo3_mailbox where cast(partijcode as varchar) = cast(a.nr as varchar));

insert into voisc.lo3_mailbox(id, partijcode, mailboxnr, mailboxpwd, verzender)
select nextval('voisc.lo3_mailbox_id_sequence'), a.nr as partijcode, concat(a.nr,'0') as mailboxnr, 'password' as mailboxpwd, '3000210' as verzender
from (select '066601' as nr) AS a
where not exists (select id from voisc.lo3_mailbox where cast(partijcode as varchar) = cast(a.nr as varchar));

insert into voisc.lo3_mailbox(id, partijcode, mailboxnr, mailboxpwd, verzender)
select nextval('voisc.lo3_mailbox_id_sequence'), a.nr as partijcode, concat(a.nr,'0') as mailboxnr, 'password' as mailboxpwd, '3000210' as verzender
from (select '076501' as nr) AS a
where not exists (select id from voisc.lo3_mailbox where cast(partijcode as varchar) = cast(a.nr as varchar));

insert into voisc.lo3_mailbox(id, partijcode, mailboxnr, mailboxpwd, verzender)
select nextval('voisc.lo3_mailbox_id_sequence'), a.nr as partijcode, concat(a.nr,'0') as mailboxnr, 'password' as mailboxpwd, '3000210' as verzender
from (select '076601' as nr) AS a
where not exists (select id from voisc.lo3_mailbox where cast(partijcode as varchar) = cast(a.nr as varchar));