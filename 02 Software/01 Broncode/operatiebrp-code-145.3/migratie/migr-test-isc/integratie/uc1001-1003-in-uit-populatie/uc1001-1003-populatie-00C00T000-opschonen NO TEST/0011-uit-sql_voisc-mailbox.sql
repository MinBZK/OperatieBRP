insert into voisc.lo3_mailbox(id, partijcode, mailboxnr, mailboxpwd, verzender)
select nextval('voisc.lo3_mailbox_id_sequence'), a.nr as partijcode, concat(a.nr,'0') as mailboxnr, 'password' as mailboxpwd, '3000200' as verzender
from (select '990081' as nr) AS a
where not exists (select id from voisc.lo3_mailbox where cast(partijcode as varchar) = cast(a.nr as varchar));

insert into voisc.lo3_mailbox(id, partijcode, mailboxnr, mailboxpwd, verzender)
select nextval('voisc.lo3_mailbox_id_sequence'), a.nr as partijcode, concat(a.nr,'0') as mailboxnr, 'password' as mailboxpwd, '3000200' as verzender
from (select '990082' as nr) AS a
where not exists (select id from voisc.lo3_mailbox where cast(partijcode as varchar) = cast(a.nr as varchar));

insert into voisc.lo3_mailbox(id, partijcode, mailboxnr, mailboxpwd, verzender)
select nextval('voisc.lo3_mailbox_id_sequence'), a.nr as partijcode, concat(a.nr,'0') as mailboxnr, 'password' as mailboxpwd, '3000200' as verzender
from (select '990083' as nr) AS a
where not exists (select id from voisc.lo3_mailbox where cast(partijcode as varchar) = cast(a.nr as varchar));

insert into voisc.lo3_mailbox(id, partijcode, mailboxnr, mailboxpwd, verzender)
select nextval('voisc.lo3_mailbox_id_sequence'), a.nr as partijcode, concat(a.nr,'0') as mailboxnr, 'password' as mailboxpwd, '3000200' as verzender
from (select '990084' as nr) AS a
where not exists (select id from voisc.lo3_mailbox where cast(partijcode as varchar) = cast(a.nr as varchar));

insert into voisc.lo3_mailbox(id, partijcode, mailboxnr, mailboxpwd, verzender)
select nextval('voisc.lo3_mailbox_id_sequence'), a.nr as partijcode, concat(a.nr,'0') as mailboxnr, 'password' as mailboxpwd, '3000230' as verzender
from (select '900099' as nr) AS a
where not exists (select id from voisc.lo3_mailbox where cast(partijcode as varchar) = cast(a.nr as varchar));
