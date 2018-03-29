insert into voisc.lo3_mailbox(id, partijcode, mailboxnr, mailboxpwd, verzender)
select nextval('voisc.lo3_mailbox_id_sequence'), a.nr as partijcode, concat(a.nr,'0') as mailboxnr, 'password' as mailboxpwd, '3000200' as verzender
from (select '999971' as nr) AS a
where not exists (select id from voisc.lo3_mailbox where cast(partijcode as varchar) = cast(a.nr as varchar));

insert into voisc.lo3_mailbox(id, partijcode, mailboxnr, mailboxpwd, verzender)
select nextval('voisc.lo3_mailbox_id_sequence'), a.nr as partijcode, concat(a.nr,'0') as mailboxnr, 'password' as mailboxpwd, '3000200' as verzender
from (select '999985' as nr) AS a
where not exists (select id from voisc.lo3_mailbox where cast(partijcode as varchar) = cast(a.nr as varchar));
