update kern.partij
set    datovergangnaarbrp = 20000101
where  code = '036301'
;

update kern.his_partij
set    datovergangnaarbrp = 20000101
where  partij in (select id from kern.partij where code = '036301')
;
