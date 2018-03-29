update kern.partij
set    datovergangnaarbrp = 20000101
,      afleverpuntvrijber='http://tools-afnemervoorbeeld:8080/afnemervoorbeeld/BrpBerichtVerwerkingService/VrijBericht'
,      dateindevrijber = cast(to_char(current_date + interval '0' day, 'yyyymmdd') as int)
where  code = '036301'
;

update kern.his_partij
set    datovergangnaarbrp = 20000101
where  partij in (select id from kern.partij where code = '036301')
;

update kern.his_partijvrijber
set    afleverpuntvrijber='http://tools-afnemervoorbeeld:8080/afnemervoorbeeld/BrpBerichtVerwerkingService/VrijBericht'
,      dateindevrijber = cast(to_char(current_date + interval '0' day, 'yyyymmdd') as int)
where  partij in (select id from kern.partij where code = '036301')
;