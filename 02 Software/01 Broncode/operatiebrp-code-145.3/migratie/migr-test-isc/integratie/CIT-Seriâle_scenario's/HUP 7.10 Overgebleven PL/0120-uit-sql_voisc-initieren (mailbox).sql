insert into voisc.lo3_mailbox(id, mailboxnr, verzender, partijcode, mailboxpwd)
select nextval('voisc.lo3_mailbox_id_sequence'), '9900010' as mailboxnr, '3000200' as vezender, a.nr as partijcode, 'password' as mailboxpwd
from (select '990001' as nr) AS a
where not exists (select id from voisc.lo3_mailbox where partijcode = '990001');

insert into voisc.lo3_mailbox(id, mailboxnr, verzender, partijcode, mailboxpwd)
select nextval('voisc.lo3_mailbox_id_sequence'), '9900110' as mailboxnr, '3000210' as vezender, a.nr as partijcode, 'password' as mailboxpwd
from (select '990011' as nr) AS a
where not exists (select id from voisc.lo3_mailbox where partijcode = '990011');

insert into voisc.lo3_mailbox(id, mailboxnr, verzender, partijcode, mailboxpwd)
select nextval('voisc.lo3_mailbox_id_sequence'), '9900210' as mailboxnr, '3000220' as vezender, a.nr as partijcode, 'password' as mailboxpwd
from (select '990021' as nr) AS a
where not exists (select id from voisc.lo3_mailbox where partijcode = '990021');

delete from voisc.lo3_mailbox where partijcode in ('062601');
insert into voisc.lo3_mailbox(id, mailboxnr, verzender, partijcode, mailboxpwd)
select nextval('voisc.lo3_mailbox_id_sequence'), '0626010' as mailboxnr, '3000230' as vezender, a.nr as partijcode, 'password' as mailboxpwd
from (select '062601' as nr) AS a
where not exists (select id from voisc.lo3_mailbox where partijcode = '062601');