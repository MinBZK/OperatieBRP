select 
p.bsn as bsn_nummer, 
p.anr as a_nummer, 
p.vorigeanr as vorige_a_nummer,
p.volgendeanr as volgende_a_nummer,
p.srt as soort_persoon, 
--hpg.geslachtsaand||' ('||
gad.code||' '||gad.naam--||')' 
as geslacht,
hpg.dataanvgel as datum_aanvang_geldigheid_geslacht,
hpg.dateindegel as datum_einde_geldigheid_geslacht,

to_char(to_timestamp(extract(epoch from hpg.tsreg)) at time zone 'UTC','YYYY-MM-DD HH24:MI:SS') as tijdstip_registratie_geslacht,
to_char(to_timestamp(extract(epoch from hpg.tsverval)) at time zone 'UTC','YYYY-MM-DD HH24:MI:SS') as tijdstip_verval_geslacht,

hpg.nadereaandverval as nadere_aanduiding_verval_naamgebruik,
case when hpg.actieverval is NULL then 'leeg'
else
case when hpg.actieverval=hpg.actieinh then 'gelijk aan actie inhoud'
else 'gevuld'
end 
end as actie_verval,
case when hpg.actieaanpgel is NULL then 'leeg' 
else 'gevuld'
end as actie_aanpassing_geldigheid,
case when hpg.actievervaltbvlevmuts is NULL then'leeg'
else 'gevuld'
end as actie_verval_levering_mutatie,
hpg.indvoorkomentbvlevmuts as indicatie_voorkomen_levering_mutatie

from kern.pers p
left join kern.his_persgeslachtsaand hpg on hpg.pers = p.id
left join kern.geslachtsaand gad on gad.id = hpg.geslachtsaand
where
p.anr = '{anr}' AND 
p.srt =1
order by datum_aanvang_geldigheid_geslacht desc,  datum_einde_geldigheid_geslacht desc, hpg.actievervaltbvlevmuts desc, indicatie_voorkomen_levering_mutatie desc, tijdstip_registratie_geslacht desc, nadere_aanduiding_verval_naamgebruik desc;
