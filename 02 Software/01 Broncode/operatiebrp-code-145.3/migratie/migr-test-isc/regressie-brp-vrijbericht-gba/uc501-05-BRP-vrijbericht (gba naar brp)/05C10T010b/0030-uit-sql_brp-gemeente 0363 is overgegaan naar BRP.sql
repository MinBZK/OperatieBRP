update kern.partij
set    datingang = cast(to_char(current_date + interval '0' day, 'yyyymmdd') as int)
,      dateinde = cast(to_char(current_date + interval '1' day, 'yyyymmdd') as int)
,      datovergangnaarbrp =  cast(to_char(current_date + interval '0' day, 'yyyymmdd') as int)
,      datingangvrijber = cast(to_char(current_date + interval '0' day, 'yyyymmdd') as int)
,      dateindevrijber = cast(to_char(current_date + interval '1' day, 'yyyymmdd') as int)
,      afleverpuntvrijber='http://tools-afnemervoorbeeld:8080/afnemervoorbeeld/BrpBerichtVerwerkingService/VrijBericht'
where  code = '036301'
;

update kern.his_partij
set    datingang = cast(to_char(current_date + interval '0' day, 'yyyymmdd') as int)
,      dateinde = cast(to_char(current_date + interval '1' day, 'yyyymmdd') as int)
,      datovergangnaarbrp =  cast(to_char(current_date + interval '0' day, 'yyyymmdd') as int)
where  partij in (select id from kern.partij where code = '036301')
;

update kern.his_partijvrijber
set    datingangvrijber = cast(to_char(current_date + interval '0' day, 'yyyymmdd') as int)
,      dateindevrijber = cast(to_char(current_date + interval '1' day, 'yyyymmdd') as int)
,      afleverpuntvrijber='http://tools-afnemervoorbeeld:8080/afnemervoorbeeld/BrpBerichtVerwerkingService/VrijBericht'
where  partij in (select id from kern.partij where code = '036301')
;