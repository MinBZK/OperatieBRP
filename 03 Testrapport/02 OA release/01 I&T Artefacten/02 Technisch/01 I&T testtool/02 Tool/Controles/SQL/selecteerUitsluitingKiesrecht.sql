select 
p.bsn as bsn_nummer, 
p.anr as a_nummer, 
p.vorigeanr as vorige_a_nummer,
p.volgendeanr as volgende_a_nummer,
p.srt as soort_persoon,
hpu.induitslkiesr as uitsluiting_kiesrecht,
hpu.datvoorzeindeuitslkiesr as datum_voorzien_einde_uitsluiting_kiesrecht,

to_char(to_timestamp(extract(epoch from hpu.tsreg)) at time zone 'UTC','YYYY-MM-DD HH24:MI:SS') as tijdstip_registratie_uitsluiting_kiesrecht,
to_char(to_timestamp(extract(epoch from hpu.tsverval)) at time zone 'UTC','YYYY-MM-DD HH24:MI:SS') as tijdstip_verval_uitsluiting_kiesrecht,

hpu.nadereaandverval as nadere_aanduiding_verval_uitsluiting_kiesrecht,
case when hpu.actieverval is NULL then 
'leeg'
else
case when hpu.actieverval=hpu.actieinh then 
'gelijk aan actie inhoud'
else 
'gevuld'
end 
end as actie_verval,
case when hpu.actievervaltbvlevmuts is NULL then
'leeg'
else 'gevuld'
end as actie_verval_levering_mutatie,
hpu.indvoorkomentbvlevmuts as indicatie_voorkomen_levering_mutatie

from kern.pers p
left join kern.his_persuitslkiesr hpu on hpu.pers = p.id
where p.anr = '{anr}' AND p.srt =1
order by tijdstip_registratie_uitsluiting_kiesrecht desc;
