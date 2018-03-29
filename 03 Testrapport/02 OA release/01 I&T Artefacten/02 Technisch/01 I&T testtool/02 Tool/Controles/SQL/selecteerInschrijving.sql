select
hpi.versienr as versienummer,
hpi.datinschr as datum_inschrijving,
--p.gempk||' ('||
par.code||' '||par.naam--||')' 
as gemeente_waar_PK_zich_bevindt,
p.indpkvollediggeconv as PK_gegevens_volledig_meegeconverteerd, 
to_char(to_timestamp(extract(epoch from hpi.dattijdstempel)) at time zone 'UTC','YYYY-MM-DD HH24:MI:SS') as datum_tijd_stempel,
to_char(to_timestamp(extract(epoch from hpi.tsreg)) at time zone 'UTC','YYYY-MM-DD HH24:MI:SS') as tijdstip_registratie,
to_char(to_timestamp(extract(epoch from hpi.tsverval)) at time zone 'UTC','YYYY-MM-DD HH24:MI:SS') as tijdstip_verval,

hpi.nadereaandverval as nadere_aanduiding_verval,
case when hpi.actieverval is NULL then 
'leeg'
else
case when hpi.actieverval=hpi.actieinh then 
'gelijk aan actie inhoud'
else 
'gevuld'
end 
end as actie_verval,
case when hpi.actievervaltbvlevmuts is NULL then
'leeg'
else 'gevuld'
end as actie_verval_levering_mutatie,
hpi.indvoorkomentbvlevmuts as indicatie_voorkomen_levering_mutatie
from
kern.his_persinschr hpi, kern.pers p
left join kern.partij par on par.id = p.gempk
where
p.anr = '{anr}' AND 
hpi.pers = p.id 
order by versienummer asc, datum_tijd_stempel desc 