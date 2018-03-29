select 
case when hpbpn.id IS NULL then NULL else p.bsn end as bsn_nummer, 
case when hpbpn.id IS NULL then NULL else p.anr end as a_nummer, 
case when hpbpn.id IS NULL then NULL else p.vorigeanr end as vorige_a_nummer,
case when hpbpn.id IS NULL then NULL else p.volgendeanr end as volgende_a_nummer,
case when hpbpn.id IS NULL then NULL else p.srt end as soort_persoon,
pbpn.nr as buitenlands_persoonsnummer,
--pbpn.autvanafgifte||' ('||
ava.code--||')' 
as autoriteit_van_afgifte,
--lg.id||' ('||
lg.code||' '||lg.naam--||')' 
as landgebied_afgifte,
--n.id||' ('||
n.code||' '||n.naam--||')' 
as nationaliteit_code,

to_char(to_timestamp(extract(epoch from hpbpn.tsreg)) at time zone 'UTC','YYYY-MM-DD HH24:MI:SS') as tijdstip_registratie_buitenlands_persoonsnummer,
to_char(to_timestamp(extract(epoch from hpbpn.tsverval)) at time zone 'UTC','YYYY-MM-DD HH24:MI:SS') as tijdstip_verval_buitenlands_persoonsnummer,

hpbpn.nadereaandverval as nadere_aanduiding_verval_buitenlands_persoonsnummer,

case when hpbpn.actieverval is NULL then 'leeg'
else
case when hpbpn.actieverval=hpbpn.actieinh then 'gelijk aan actie inhoud'
else 'gevuld'
end 
end as actie_verval,
case when hpbpn.actievervaltbvlevmuts is NULL then'leeg'
else 'gevuld'
end as actie_verval_levering_mutatie,
hpbpn.indvoorkomentbvlevmuts as indicatie_voorkomen_levering_mutatie


from kern.pers p
left join kern.persblpersnr pbpn on pbpn.pers = p.id
left join kern.his_persblpersnr hpbpn on hpbpn.persblpersnr = pbpn.id
left join kern.autvanafgifteblpersnr ava on ava.id = pbpn.autvanafgifte
left join kern.landgebied lg on lg.id = ava.landgebied
left join kern.nation n on n.id = ava.nation

where
p.anr = '{anr}' AND p.srt =1
order by tijdstip_registratie_buitenlands_persoonsnummer desc;