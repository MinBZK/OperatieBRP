select 
case when hpm.id IS NULL then NULL else p.bsn end as bsn_nummer, 
case when hpm.id IS NULL then NULL else p.anr end as a_nummer, 
case when hpm.id IS NULL then NULL else p.vorigeanr end as vorige_a_nummer,
case when hpm.id IS NULL then NULL else p.volgendeanr end as volgende_a_nummer,
case when hpm.id IS NULL then NULL else p.srt end as soort_persoon, 
--hpm.srtmigratie||' ('||
m.code||' '||m.naam--||')' 
as soort_migratie,
hpm.dataanvgel as datum_aanvang_geldigheid_migratie,
hpm.dateindegel as datum_einde_geldigheid_migratie,
--hpm.rdnwijzmigratie||' ('||
rwv.code||' '||rwv.naam--||')' 
as reden_wijziging_migratie,
--hpm.aangmigratie||' ('||
ag.code||' '||ag.naam--||')' 
as aangever_migratie,
--hpm.landgebiedmigratie||' ('||
lgb.code||' '||lgb.naam--||')' 
as land_gebied_migratie,
hpm.bladresregel1migratie as adresregel1,
hpm.bladresregel2migratie as adresregel2,
hpm.bladresregel3migratie as adresregel3,
hpm.bladresregel4migratie as adresregel4,
hpm.bladresregel5migratie as adresregel5,
hpm.bladresregel6migratie as adresregel6,

to_char(to_timestamp(extract(epoch from hpm.tsreg)) at time zone 'UTC','YYYY-MM-DD HH24:MI:SS') as tijdstip_registratie_migratie,
to_char(to_timestamp(extract(epoch from hpm.tsverval)) at time zone 'UTC','YYYY-MM-DD HH24:MI:SS') as tijdstip_verval_migratie,

hpm.nadereaandverval as nadere_aanduiding_verval_migratie,
case when hpm.actieverval is NULL then 'leeg'
else
case when hpm.actieverval=hpm.actieinh then 'gelijk aan actie inhoud'
else 'gevuld'
end 
end as actie_verval,
case when hpm.actieaanpgel is NULL then 'leeg' 
else 'gevuld'
end as actie_aanpassing_geldigheid,
case when hpm.actievervaltbvlevmuts is NULL then'leeg'
else 'gevuld'
end as actie_verval_levering_mutatie,
hpm.indvoorkomentbvlevmuts as indicatie_voorkomen_levering_mutatie

from kern.pers p
left join kern.his_persmigratie hpm on hpm.pers = p.id
left join kern.srtmigratie m on m.id = hpm.srtmigratie
left join kern.rdnwijzverblijf rwv on rwv.id = hpm.rdnwijzmigratie
left join kern.aang ag on ag.id = hpm.aangmigratie
left join kern.landgebied lgb on lgb.id = hpm.landgebiedmigratie
where
p.anr = '{anr}' AND p.srt =1
order by hpm.tsreg desc, actie_verval_levering_mutatie asc, indicatie_voorkomen_levering_mutatie asc;
