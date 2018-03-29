insert into voisc.lo3_mailbox(id, instantietype, instantiecode, mailboxnr, mailboxpwd)
select nextval('voisc.lo3_mailbox_id_sequence'), 'G' as instantietype, a.nr as instantiecode, '0989010' as mailboxnr, 'password' as mailboxpwd
from (select 989 as nr) AS a
where not exists (select id from voisc.lo3_mailbox where instantiecode = a.nr);