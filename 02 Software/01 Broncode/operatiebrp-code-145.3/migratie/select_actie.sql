select admhnd.id as admhnd_id
,      srtadmhnd.naam as admhnd_srt
,      actie.id as actie_id
,      srtactie.naam as actie_srt
,      historie.naam as historie_naam
,      historie.id as historie_id
,      historie.actieinh as historie_actieinh
,      historie.actieverval as historie_actieverval
,      historie.actieaanpgel as historie_actieaanpgel
,      historie.actievervaltbvlevmuts as historie_actievervaltbvlevmuts
from   kern.actie
join   kern.srtactie on actie.srt = srtactie.id
join   kern.admhnd on actie.admhnd = admhnd.id
join   kern.srtadmhnd on admhnd.srt = srtadmhnd.id
join ( select 'kern.his_betr' as naam, id, actieinh, actieverval, cast(null as bigint) as actieaanpgel, actievervaltbvlevmuts from kern.his_betr
       union select 'kern.his_onderzoek', id, actieinh, actieverval, null, actievervaltbvlevmuts from kern.his_onderzoek
       union select 'kern.his_ouderouderlijkgezag', id, actieinh, actieverval, actieaanpgel, actievervaltbvlevmuts from kern.his_ouderouderlijkgezag
       union select 'kern.his_ouderouderschap', id, actieinh, actieverval, actieaanpgel, actievervaltbvlevmuts from kern.his_ouderouderschap
       union select 'kern.his_persadres', id, actieinh, actieverval, actieaanpgel, actievervaltbvlevmuts from kern.his_persadres
       union select 'kern.his_persafgeleidadministrati', id, actieinh, actieverval, null, actievervaltbvlevmuts from kern.his_persafgeleidadministrati
       union select 'kern.his_persbijhouding', id, actieinh, actieverval, actieaanpgel, actievervaltbvlevmuts from kern.his_persbijhouding
       union select 'kern.his_persdeelneuverkiezingen', id, actieinh, actieverval, null, actievervaltbvlevmuts from kern.his_persdeelneuverkiezingen
       union select 'kern.his_persgeboorte', id, actieinh, actieverval, null, actievervaltbvlevmuts from kern.his_persgeboorte
       union select 'kern.his_persgeslachtsaand', id, actieinh, actieverval, actieaanpgel, actievervaltbvlevmuts from kern.his_persgeslachtsaand
       union select 'kern.his_persgeslnaamcomp', id, actieinh, actieverval, actieaanpgel, actievervaltbvlevmuts from kern.his_persgeslnaamcomp
       union select 'kern.his_persids', id, actieinh, actieverval, actieaanpgel, actievervaltbvlevmuts from kern.his_persids
       union select 'kern.his_persindicatie', id, actieinh, actieverval, actieaanpgel, actievervaltbvlevmuts from kern.his_persindicatie
       union select 'kern.his_persinschr', id, actieinh, actieverval, null, actievervaltbvlevmuts from kern.his_persinschr
       union select 'kern.his_persmigratie', id, actieinh, actieverval, actieaanpgel, actievervaltbvlevmuts from kern.his_persmigratie
       union select 'kern.his_persnaamgebruik', id, actieinh, actieverval, null, actievervaltbvlevmuts from kern.his_persnaamgebruik
       union select 'kern.his_persnation', id, actieinh, actieverval, actieaanpgel, actievervaltbvlevmuts from kern.his_persnation
       union select 'kern.his_persnrverwijzing', id, actieinh, actieverval, actieaanpgel, actievervaltbvlevmuts from kern.his_persnrverwijzing
       union select 'kern.his_persoverlijden', id, actieinh, actieverval, null, actievervaltbvlevmuts from kern.his_persoverlijden
       union select 'kern.his_perspk', id, actieinh, actieverval, null, actievervaltbvlevmuts from kern.his_perspk
       union select 'kern.his_persreisdoc', id, actieinh, actieverval, null, actievervaltbvlevmuts from kern.his_persreisdoc
       union select 'kern.his_perssamengesteldenaam', id, actieinh, actieverval, actieaanpgel, actievervaltbvlevmuts from kern.his_perssamengesteldenaam
       union select 'kern.his_persuitslkiesr', id, actieinh, actieverval, null, actievervaltbvlevmuts from kern.his_persuitslkiesr
       union select 'kern.his_persverblijfsr', id, actieinh, actieverval, null, actievervaltbvlevmuts from kern.his_persverblijfsr
       union select 'kern.his_persverificatie', id, actieinh, actieverval, null, actievervaltbvlevmuts from kern.his_persverificatie
       union select 'kern.his_persverstrbeperking', id, actieinh, actieverval, null, actievervaltbvlevmuts from kern.his_persverstrbeperking
       union select 'kern.his_persvoornaam', id, actieinh, actieverval, actieaanpgel, actievervaltbvlevmuts from kern.his_persvoornaam
       union select 'kern.his_relatie', id, actieinh, actieverval, null, actievervaltbvlevmuts from kern.his_relatie
     ) as historie on historie.actieinh = actie.id or historie.actieverval = actie.id or historie.actieaanpgel = actie.id or historie.actievervaltbvlevmuts = actie.id
where actie.id = 5215
--where admhnd.id = 477
order by admhnd.id, actie.id, historie.id
;
