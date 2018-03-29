select 
case when hov.id IS NULL then NULL else p.bsn end as bsn_nummer, 
case when hov.id IS NULL then NULL else p.anr end as a_nummer,
case when hov.id IS NULL then NULL else p.vorigeanr end as vorige_a_nummer,
case when hov.id IS NULL then NULL else p.volgendeanr end as volgende_a_nummer, 
case when hov.id IS NULL then NULL else p.srt end as soort_persoon, 

hov.datoverlijden as datum_overlijden,
--hov.gemoverlijden||'( '||
gem.code||' '||gem.naam--||')' 
as gemeente_overlijden,
hov.wplnaamoverlijden as woonplaats_overlijden,
hov.blplaatsoverlijden as buitenlandse_plaats_overlijden,
hov.blregiooverlijden as buitenlandse_regio_overlijden,
hov.omslocoverlijden as omschrijving_locatie_overlijden,
--hov.landgebiedoverlijden||' ('||
lgb.code||' '||lgb.naam--||')' 
as land_gebied_overlijden,
--doc.oms as beschrijving_document, 
--par.code as gemeente_document, 
--doc.aktenr as akte_nummer,

to_char(to_timestamp(extract(epoch from hov.tsreg)) at time zone 'UTC','YYYY-MM-DD HH24:MI:SS') as tijdstip_registratie_overlijden,
to_char(to_timestamp(extract(epoch from hov.tsverval)) at time zone 'UTC','YYYY-MM-DD HH24:MI:SS') as tijdstip_verval_overlijden,

hov.nadereaandverval as nadere_aanduiding_verval_overlijden,
case when hov.actieverval is NULL then 
'leeg'
else
case when hov.actieverval=hov.actieinh then 
'gelijk aan actie inhoud'
else 
'gevuld'
end 
end as actie_verval,
case when hov.actievervaltbvlevmuts is NULL then
'leeg'
else 'gevuld'
end as actie_verval_levering_mutatie,
hov.indvoorkomentbvlevmuts as indicatie_voorkomen_levering_mutatie


from kern.pers p
left join kern.his_persoverlijden hov on hov.pers = p.id
--left join kern.actie actie on actie.id = hov.actieinh
--left join kern.actiebron actieb on actieb.actie = actie.id
--left join kern.doc doc on doc.id= actieb.doc 
--left join kern.partij par on par.id = doc.partij
left join kern.gem gem on gem.id = hov.gemoverlijden
left join kern.landgebied lgb on lgb.id = hov.landgebiedoverlijden
where
p.anr = '{anr}' AND p.srt =1
order by tijdstip_registratie_overlijden desc;
