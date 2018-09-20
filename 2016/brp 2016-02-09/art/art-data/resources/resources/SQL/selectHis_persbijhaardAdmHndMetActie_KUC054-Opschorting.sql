select
srtAdm.naam, to_char(adm.tsreg,'YYYYMMDD')Adm_tsreg,
srtActie.naam, to_char(a.tsreg,'YYYYMMDD')Actie_tsreg from kern.actie a,
kern.admhnd adm, kern.srtadmhnd srtAdm, kern.srtActie srtActie
where srtActie.id=a.srt and srtAdm.id=adm.srt AND a.admhnd=adm.id and a.id in
(select actieinh from kern.his_persbijhaard where pers in
(select id from kern.pers where bsn =  ${DataSource Values#|persoon1|}))
 order by a.tsreg
