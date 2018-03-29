insert into voisc.lo3_mailbox(id, mailboxnr, verzender, partijcode, mailboxpwd)
select nextval('voisc.lo3_mailbox_id_sequence'), '9900010' as mailboxnr, '3000200' as vezender, a.nr as partijcode, 'password' as mailboxpwd
from (select '990001' as nr) AS a
where not exists (select id from voisc.lo3_mailbox where partijcode = '990001');

delete from voisc.lo3_mailbox where partijcode in ('199901');
insert into voisc.lo3_mailbox(id, mailboxnr, verzender, partijcode, mailboxpwd)
select nextval('voisc.lo3_mailbox_id_sequence'), '1999010' as mailboxnr, '3000270' as vezender, a.nr as partijcode, 'password' as mailboxpwd
from (select '199901' as nr) AS a
where not exists (select id from voisc.lo3_mailbox where partijcode = '199901');

delete from voisc.lo3_mailbox where partijcode in ('050901');
insert into voisc.lo3_mailbox(id, mailboxnr, verzender, partijcode, mailboxpwd)
select nextval('voisc.lo3_mailbox_id_sequence'), '0509010' as mailboxnr, '3000230' as vezender, a.nr as partijcode, 'password' as mailboxpwd
from (select '050901' as nr) AS a
where not exists (select id from voisc.lo3_mailbox where partijcode = '050901');

delete from voisc.lo3_mailbox where partijcode in ('056801');
insert into voisc.lo3_mailbox(id, mailboxnr, verzender, partijcode, mailboxpwd)
select nextval('voisc.lo3_mailbox_id_sequence'), '0568010' as mailboxnr, '3000230' as vezender, a.nr as partijcode, 'password' as mailboxpwd
from (select '056801' as nr) AS a
where not exists (select id from voisc.lo3_mailbox where partijcode = '056801');
