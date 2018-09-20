select admhnd.id as admhnd_id
,      srtadmhnd.naam as admhnd_srt
,      actie.id as actie_id
,      srtactie.naam as actie_srt
,      historie.naam as historie_naam
,      historie.id as historie_id
,      historie.actieinh as historie_actieinh
,      historie.actieverval as historie_actieverval
,      historie.actieaanpgel as historie_actieaanpgel
from   kern.actie
join   kern.srtactie on actie.srt = srtactie.id
join   kern.admhnd on actie.admhnd = admhnd.id
join   kern.srtadmhnd on admhnd.srt = srtadmhnd.id
join ( select 'kern.his_betr' as naam, id, actieinh, actieverval, cast(null as bigint) as actieaanpgel from kern.his_betr
		   union select 'kern.his_doc' as naam, id, actieinh, actieverval, null as actieaanpgel from kern.his_doc
       union select 'kern.his_onderzoek', id, actieinh, actieverval, null from kern.his_onderzoek
       union select 'kern.his_onderzoekafgeleidadminis', id, actieinh, actieverval, null from kern.his_onderzoekafgeleidadminis
       union select 'kern.his_ouderouderlijkgezag', id, actieinh, actieverval, actieaanpgel from kern.his_ouderouderlijkgezag
       union select 'kern.his_ouderouderschap', id, actieinh, actieverval, actieaanpgel from kern.his_ouderouderschap
       union select 'kern.his_partij', id, actieinh, actieverval, null from kern.his_partij
       union select 'kern.his_partijbijhouding', id, actieinh, actieverval, null from kern.his_partijbijhouding
       union select 'kern.his_partijonderzoek', id, actieinh, actieverval, null from kern.his_partijonderzoek
       union select 'kern.his_partijrol', id, actieinh, actieverval, null from kern.his_partijrol
       union select 'kern.his_persadres', id, actieinh, actieverval, actieaanpgel from kern.his_persadres
       union select 'kern.his_persafgeleidadministrati', id, actieinh, actieverval, null from kern.his_persafgeleidadministrati
       union select 'kern.his_persbijhouding', id, actieinh, actieverval, actieaanpgel from kern.his_persbijhouding
       union select 'kern.his_persdeelneuverkiezingen', id, actieinh, actieverval, null from kern.his_persdeelneuverkiezingen
       union select 'kern.his_persgeboorte', id, actieinh, actieverval, null from kern.his_persgeboorte
       union select 'kern.his_persgeslachtsaand', id, actieinh, actieverval, actieaanpgel from kern.his_persgeslachtsaand
       union select 'kern.his_persgeslnaamcomp', id, actieinh, actieverval, actieaanpgel from kern.his_persgeslnaamcomp
       union select 'kern.his_persids', id, actieinh, actieverval, actieaanpgel from kern.his_persids
       union select 'kern.his_persindicatie', id, actieinh, actieverval, actieaanpgel from kern.his_persindicatie
       union select 'kern.his_persinschr', id, actieinh, actieverval, null from kern.his_persinschr
       union select 'kern.his_persmigratie', id, actieinh, actieverval, actieaanpgel from kern.his_persmigratie
       union select 'kern.his_persnaamgebruik', id, actieinh, actieverval, null from kern.his_persnaamgebruik
       union select 'kern.his_persnation', id, actieinh, actieverval, actieaanpgel from kern.his_persnation
       union select 'kern.his_persnrverwijzing', id, actieinh, actieverval, actieaanpgel from kern.his_persnrverwijzing
       union select 'kern.his_personderzoek', id, actieinh, actieverval, null from kern.his_personderzoek
       union select 'kern.his_persoverlijden', id, actieinh, actieverval, null from kern.his_persoverlijden
       union select 'kern.his_perspk', id, actieinh, actieverval, null from kern.his_perspk
       union select 'kern.his_persreisdoc', id, actieinh, actieverval, null from kern.his_persreisdoc
       union select 'kern.his_perssamengesteldenaam', id, actieinh, actieverval, actieaanpgel from kern.his_perssamengesteldenaam
       union select 'kern.his_persuitslkiesr', id, actieinh, actieverval, null from kern.his_persuitslkiesr
       union select 'kern.his_persverblijfsr', id, actieinh, actieverval, null from kern.his_persverblijfsr
       union select 'kern.his_persverificatie', id, actieinh, actieverval, null from kern.his_persverificatie
       union select 'kern.his_persverstrbeperking', id, actieinh, actieverval, null from kern.his_persverstrbeperking
       union select 'kern.his_persvoornaam', id, actieinh, actieverval, actieaanpgel from kern.his_persvoornaam
       union select 'kern.his_relatie', id, actieinh, actieverval, null from kern.his_relatie
       union select 'kern.his_terugmelding', id, actieinh, actieverval, null from kern.his_terugmelding
       union select 'kern.his_terugmeldingcontactpers', id, actieinh, actieverval, null from kern.his_terugmeldingcontactpers
       union select 'autaut.his_dienst', id, actieinh, actieverval, null from autaut.his_dienst
       union select 'autaut.his_dienstattendering', id, actieinh, actieverval, null from autaut.his_dienstattendering
       union select 'autaut.his_dienstbundel', id, actieinh, actieverval, null from autaut.his_dienstbundel
       union select 'autaut.his_dienstbundelgroep', id, actieinh, actieverval, null from autaut.his_dienstbundelgroep
       union select 'autaut.his_dienstbundelgroepattr', id, actieinh, actieverval, null from autaut.his_dienstbundelgroepattr
       union select 'autaut.his_dienstbundello3rubriek', id, actieinh, actieverval, null from autaut.his_dienstbundello3rubriek
       union select 'autaut.his_dienstselectie', id, actieinh, actieverval, null from autaut.his_dienstselectie
       union select 'autaut.his_levsautorisatie', id, actieinh, actieverval, null from autaut.his_levsautorisatie
       union select 'autaut.his_partijfiatuitz', id, actieinh, actieverval, null from autaut.his_partijfiatuitz
       union select 'autaut.his_toegangbijhautorisatie', id, actieinh, actieverval, null from autaut.his_toegangbijhautorisatie
       union select 'autaut.his_toeganglevsautorisatie', id, actieinh, actieverval, null from autaut.his_toeganglevsautorisatie
     ) as historie on historie.actieinh = actie.id or historie.actieverval = actie.id or historie.actieaanpgel = actie.id
where actie.id = 278
--where admhnd.id = 477
order by admhnd.id, actie.id, historie.id
;
