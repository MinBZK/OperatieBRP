select 
p.bsn as bsn_nummer, 
p.anr as a_nummer,
p.vorigeanr as vorige_a_nummer,
p.volgendeanr as volgende_a_nummer,
p.srt as soort_persoon, 

--hpb.datgeboorte as datum_geboorte_persoon,
case when {placeholder}=hpb.datgeboorte then
'gelijk aan placeholder'
else trim(to_char(hpb.datgeboorte, '99999999'))
end 
as datum_geboorte_persoon, 
hpb.indvoorkomentbvlevmuts as indicatie_voorkomen_levering_mutatie,  
doc.aktenr as akte_nummer, 
doc.oms as beschrijving_document, 
--doc.partij||' ('||
par.code||' '||par.naam--||')' 
as partij_document, 
--hpb.gemgeboorte||' ('||
g.code||' '||g.naam--||')' 
as gemeente_geboorte, 
hpb.wplnaamgeboorte as woonplaats_geboorte, 
hpb.blplaatsgeboorte as buitenlandse_plaats_geboorte, 
hpb.blregiogeboorte as buitenlandse_regio_geboorte,
hpb.omslocgeboorte as locatie_geboorte, 
--hpb.landgebiedgeboorte||' ('||
lgb.code||' '||lgb.naam--||')' 
as land_gebied_geboorte,
to_char(to_timestamp(extract(epoch from hpb.tsreg)) at time zone 'UTC','YYYY-MM-DD HH24:MI:SS') as tsreg_geboorte_persoon,
to_char(to_timestamp(extract(epoch from hpb.tsverval)) at time zone 'UTC','YYYY-MM-DD HH24:MI:SS') as tijdstip_verval_geboorte,

hpb.nadereaandverval as nadere_aanduiding_verval_persoon,
case when hpb.actieverval is NULL then 'leeg'
else
case when hpb.actieverval=hpb.actieinh then 'gelijk aan actie inhoud'
else 'gevuld'
end 
end as actie_verval,
case when hpb.actievervaltbvlevmuts is NULL then'leeg'
else 'gevuld'
end as actie_verval_levering_mutatie

from kern.pers p
left join kern.his_persgeboorte hpb on hpb.pers = p.id
left join kern.gem g on g.id = hpb.gemgeboorte
left join kern.actie actie on actie.id = hpb.actieinh
left join kern.actiebron actieb on actieb.actie = actie.id
left join kern.doc doc on doc.id= actieb.doc 
left join kern.partij par on par.id = doc.partij
left join kern.landgebied lgb on lgb.id = hpb.landgebiedgeboorte
where p.anr = '{anr}' AND 
p.srt =1
order by tsreg_geboorte_persoon asc, tijdstip_verval_geboorte asc, actie_verval_levering_mutatie asc;
