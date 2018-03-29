select 
p.bsn as bsn_nummer, 
p.anr as a_nummer, 
p.vorigeanr as vorige_a_nummer,
p.volgendeanr as volgende_a_nummer,
p.srt as soort_persoon, 
pv.volgnr as volgnummer_voornaam,
hpv.naam as voornaam,
hpv.dataanvgel as datum_aanvang_geldigheid_voornaam,
hpv.dateindegel as datum_einde_geldigheid_voornaam,

to_char(to_timestamp(extract(epoch from hpv.tsreg)) at time zone 'UTC','YYYY-MM-DD HH24:MI:SS') as tijdstip_registratie_voornaam,
to_char(to_timestamp(extract(epoch from hpv.tsverval)) at time zone 'UTC','YYYY-MM-DD HH24:MI:SS') as tijdstip_verval_voornaam,

hpv.nadereaandverval as nadere_aanduiding_verval,
case when hpv.actieverval is NULL then 'leeg'
else
case when hpv.actieverval=hpv.actieinh then 'gelijk aan actie inhoud'
else 'gevuld'
end 
end as actie_verval,
case when hpv.actieaanpgel is NULL then 'leeg' 
else 'gevuld'
end as actie_aanpassing_geldigheid,
case when hpv.actievervaltbvlevmuts is NULL then'leeg'
else 'gevuld'
end as actie_verval_levering_mutatie,
hpv.indvoorkomentbvlevmuts as indicatie_voorkomen_levering_mutatie

from kern.pers p
left join kern.persvoornaam pv on pv.pers = p.id
left join kern.his_persvoornaam hpv on hpv.persvoornaam = pv.id

where
p.anr = '{anr}' AND p.srt =1
order by datum_aanvang_geldigheid_voornaam desc,  datum_einde_geldigheid_voornaam desc, volgnummer_voornaam asc, hpv.tsverval asc, hpv.actievervaltbvlevmuts desc, hpv.indvoorkomentbvlevmuts asc;
