insert into voisc.lo3_mailbox(id, mailboxnr, verzender, partijcode, mailboxpwd)
select nextval('voisc.lo3_mailbox_id_sequence'), concat(a.nr,'0') as mailboxnr, '3000200' as vezender, a.nr as partijcode, 'password' as mailboxpwd
from (select '999971' as nr) AS a
where not exists (select id from voisc.lo3_mailbox where partijcode = '999971');