update kern.partij
set    datovergangnaarbrp = 20200101
,      oin='abcdefgh123'
,      afleverpuntvrijber='http://tools-afnemervoorbeeld:8080/afnemervoorbeeld/BrpBerichtVerwerkingService/VrijBericht'
where  code = '060301'
;

update kern.his_partij
set    datovergangnaarbrp = 20200101
where  partij in (select id from kern.partij where code = '060301')
;

update kern.his_partijvrijber
set    afleverpuntvrijber='http://tools-afnemervoorbeeld:8080/afnemervoorbeeld/BrpBerichtVerwerkingService/VrijBericht'
where  partij in (select id from kern.partij where code = '060301')
;


update kern.partij
set    oin='abcdefgh456'
,      afleverpuntvrijber='http://tools-afnemervoorbeeld:8080/afnemervoorbeeld/BrpBerichtVerwerkingService/VrijBericht'
where  code = '001801'
;

update kern.his_partijvrijber
set    afleverpuntvrijber='http://tools-afnemervoorbeeld:8080/afnemervoorbeeld/BrpBerichtVerwerkingService/VrijBericht'
where  partij in (select id from kern.partij where code = '001801')