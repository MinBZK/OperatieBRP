select 
case when hpv.id IS NULL then NULL else p.bsn end as bsn_nummer, 
case when hpv.id IS NULL then NULL else p.anr end as a_nummer,
case when hpv.id IS NULL then NULL else p.vorigeanr end as vorige_a_nummer,
case when hpv.id IS NULL then NULL else p.volgendeanr end as volgende_a_nummer, 
case when hpv.id IS NULL then NULL else p.srt end as soort_persoon, 
--hpv.aandverblijfsr||' ('||
avr.code||' '||avr.oms--||')' 
as aanduiding_verblijfsrecht,
hpv.datmededelingverblijfsr as datum_mededeling_verblijfsrecht,
hpv.datvoorzeindeverblijfsr as datum_voorziening_einde_verblijfsrecht,
hpv.dataanvverblijfsr as datum_aanvang_verblijfsrecht,

to_char(to_timestamp(extract(epoch from hpv.tsreg)) at time zone 'UTC','YYYY-MM-DD HH24:MI:SS') as tijdstip_registratie_verblijfsrecht,
to_char(to_timestamp(extract(epoch from hpv.tsverval)) at time zone 'UTC','YYYY-MM-DD HH24:MI:SS') as tijdstip_verval_verblijfsrecht,

hpv.nadereaandverval as nadere_aanduiding_verval_verblijfsrecht,
case when hpv.actieverval is NULL then 'leeg'
else
case when hpv.actieverval=hpv.actieinh then 'gelijk aan actie inhoud'
else 'gevuld'
end 
end as actie_verval,

case when hpv.actievervaltbvlevmuts is NULL then'leeg'
else 'gevuld'
end as actie_verval_levering_mutatie,
hpv.indvoorkomentbvlevmuts as indicatie_voorkomen_levering_mutatie

from kern.pers p
left join kern.his_persverblijfsr hpv on hpv.pers = p.id
left join kern.aandverblijfsr avr on avr.id = hpv.aandverblijfsr

where
p.anr = '{anr}' 
AND p.srt = 1
order by hpv.tsreg desc;