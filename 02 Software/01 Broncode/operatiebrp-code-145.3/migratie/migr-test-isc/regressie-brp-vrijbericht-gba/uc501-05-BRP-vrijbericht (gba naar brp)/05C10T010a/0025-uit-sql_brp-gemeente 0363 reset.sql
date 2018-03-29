update kern.partij
set    datingang = 19000101
,      dateinde = NULL
,      ondertekenaarvrijber = NULL
,      transporteurvrijber = NULL
,      datingangvrijber = 19000101
,      dateindevrijber = NULL
,      datovergangnaarbrp = NULL
,      afleverpuntvrijber = NULL
,      indblokvrijber = NULL
,      indagvrijber = TRUE
where  code = '036301'
;

update kern.his_partij
set    datingang = 19000101
,      dateinde = NULL
,      datovergangnaarbrp = NULL
where  partij in (select id from kern.partij where code = '036301')
;

update kern.his_partijvrijber
set    tsverval = NULL
,      ondertekenaarvrijber = NULL
,      transporteurvrijber = NULL
,      datingangvrijber = 19000101
,      dateindevrijber = NULL
,      afleverpuntvrijber = NULL
,      indblokvrijber = NULL
where  partij in (select id from kern.partij where code = '036301')
;
