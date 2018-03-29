delete from kern.his_partijbijhouding
where  partij = (select id from kern.partij where code='062601')
;

insert into kern.his_partijbijhouding(id, indautofiat, partij, tsreg)
values(nextval('kern.seq_his_partijbijhouding'), true, (select id from kern.partij where code='062601'), now())
;

update kern.partij
set    datovergangnaarbrp = 20160101
,      indautofiat = true
,      indagbijhouding = true
where  code = '062601'
;
