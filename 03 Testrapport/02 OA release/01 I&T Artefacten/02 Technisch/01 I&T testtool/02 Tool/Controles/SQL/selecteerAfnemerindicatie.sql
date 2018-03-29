select 
p.anr as a_nummer, 
--a.afnemer||' ('||
pa.code||' '||pa.naam--||')' 
as afnemer,
--a.levsautorisatie||' ('||
l.naam --||')' 
as leveringsautorisatie,
ha.dataanvmaterieleperiode as dat_aanv_materiele_periode,
ha.dateindevolgen as dat_einde_volgen,

case when ha.dienstinh is NULL then 'leeg'
else 'gevuld'
end as dienst_inh,

case when ha.dienstverval is NULL then 'leeg'
else 'gevuld'
end as dienst_verval,

case when ha.tsreg is not null then 'gevuld'
else 'leeg' 
end as tijdstip_registratie_afnemerindicatie,
case when ha.tsverval is not NULL then 'gevuld'
else 'leeg' 
end as tijdstip_verval_afnemerindicatie

from kern.pers p
left join autaut.persafnemerindicatie a on a.pers = p.id
left join kern.partij pa on pa.id = a.partij
left join autaut.levsautorisatie l on l.id = a.levsautorisatie
left join autaut.his_persafnemerindicatie ha on ha.persafnemerindicatie = a.id


where
p.anr = '{anr}' AND p.srt =1
order by tijdstip_registratie_afnemerindicatie, tijdstip_verval_afnemerindicatie desc, afnemer, leveringsautorisatie;