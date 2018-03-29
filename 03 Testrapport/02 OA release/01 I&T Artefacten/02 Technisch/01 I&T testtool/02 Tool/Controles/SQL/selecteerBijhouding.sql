select
--hpb.bijhaard||' ('||
bha.code||' '||bha.naam--||')' 
as bijhoudings_aard,
--hpb.naderebijhaard||' ('||
nbha.code||' '||nbha.naam--||')' 
as nadere_bijhoudings_aard,
--par.id||' ('||
par.code||' '||par.naam--||')' 
as bijhoudings_partij,
hpb.dataanvgel as datum_aanvang_geldigheid,
hpb.dateindegel as datum_einde_geldigheid,

to_char(to_timestamp(extract(epoch from hpb.tsreg)) at time zone 'UTC','YYYY-MM-DD HH24:MI:SS') as tijdstip_registratie,
to_char(to_timestamp(extract(epoch from hpb.tsverval)) at time zone 'UTC','YYYY-MM-DD HH24:MI:SS') as tijdstip_verval,

hpb.nadereaandverval as nadere_aanduiding_verval,
case when hpb.actieverval is NULL then 'leeg'
else
case when hpb.actieverval=hpb.actieinh then 'gelijk aan actie inhoud'
else 'gevuld'
end 
end as actie_verval,
case when hpb.actieaanpgel is NULL then 'leeg' 
else 'gevuld'
end as actie_aanpassing_geldigheid,
case when hpb.actievervaltbvlevmuts is NULL then'leeg'
else 'gevuld'
end as actie_verval_levering_mutatie,
hpb.indvoorkomentbvlevmuts as indicatie_voorkomen_levering_mutatie
from
kern.his_persbijhouding hpb, kern.pers p, kern.partij par, kern.bijhaard bha, kern.naderebijhaard nbha 
where
par.id = hpb.bijhpartij
AND p.anr = '{anr}'
AND hpb.pers = p.id
and bha.id = hpb.bijhaard
and nbha.id = hpb.naderebijhaard 
order by bijhoudings_aard asc, datum_aanvang_geldigheid desc, hpb.tsreg desc, hpb.actievervaltbvlevmuts desc, nadere_bijhoudings_aard desc;
