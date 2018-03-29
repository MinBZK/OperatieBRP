select 
p.bsn as bsn_nummer, 
p.anr as a_nummer, 
p.vorigeanr as vorige_a_nummer,
p.volgendeanr as volgende_a_nummer,
p.srt as soort_persoon, 
--hng.naamgebruik||' ('||
ngb.code||' '||ngb.naam--||')' 
as naamgebruik,
hng.indnaamgebruikafgeleid as indicatie_naamgebruik_afgeleid,
hng.voornamennaamgebruik as gebruik_voornamen,
case when p.geslachtsaand = 1 then --hng.adellijketitelnaamgebruik||' ('||
att.code||' '||att.naammannelijk--||')' 
     else case when p.geslachtsaand = 2 then --hng.adellijketitelnaamgebruik||' ('||
att.code||' '||att.naamvrouwelijk--||')'
               else --hng.adellijketitelnaamgebruik||' ('||
att.code||' Onbekend'
          end
end 
as gebruik_adellijketitel,  
case when p.geslachtsaand = 1 then --hng.predicaatnaamgebruik||' ('||
pdc.code||' '||pdc.naammannelijk--||')' 
     else case when p.geslachtsaand = 2 then --hng.predicaatnaamgebruik||' ('||
pdc.code||' '||pdc.naamvrouwelijk--||')'
               else --hng.predicaatnaamgebruik||' ('||
pdc.code||' Onbekend'
          end
end 
as gebruik_predicaat, 
hng.voorvoegselnaamgebruik as gebruik_voorvoegsel,
hng.scheidingstekennaamgebruik as gebruik_scheidingsteken,
hng.geslnaamstamnaamgebruik as gebruik_geslachtsnaam,

to_char(to_timestamp(extract(epoch from hng.tsreg)) at time zone 'UTC','YYYY-MM-DD HH24:MI:SS') as tijdstip_registratie_naamgebruik,
to_char(to_timestamp(extract(epoch from hng.tsverval)) at time zone 'UTC','YYYY-MM-DD HH24:MI:SS') as tijdstip_verval_naamgebruik,

hng.nadereaandverval as nadere_aanduiding_verval_naamgebruik,
case when hng.actieverval is NULL then 
'leeg'
else
case when hng.actieverval=hng.actieinh then 
'gelijk aan actie inhoud'
else 
'gevuld'
end 
end as actie_verval,
case when hng.actievervaltbvlevmuts is NULL then
'leeg'
else 'gevuld'
end as actie_verval_levering_mutatie,
hng.indvoorkomentbvlevmuts as indicatie_voorkomen_levering_mutatie

from kern.pers p
left join kern.his_persnaamgebruik hng on hng.pers = p.id
left join kern.naamgebruik ngb on ngb.id = hng.naamgebruik
left join kern.predicaat pdc on pdc.id = hng.predicaatnaamgebruik
left join kern.adellijketitel att on att.id = hng.adellijketitelnaamgebruik
where
p.anr = '{anr}' AND p.srt =1
order by hng.tsreg desc, hng.actievervaltbvlevmuts desc;
