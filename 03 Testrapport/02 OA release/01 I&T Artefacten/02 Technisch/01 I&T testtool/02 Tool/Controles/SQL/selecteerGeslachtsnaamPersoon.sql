select 
p.bsn as bsn_nummer, 
p.anr as a_nummer, 
p.vorigeanr as vorige_a_nummer,
p.volgendeanr as volgende_a_nummer,
p.srt as soort_persoon, 
hgn.voorvoegsel as historisch_voorvoegsel, 
hgn.scheidingsteken as historich_scheidingsteken, 
hgn.stam as historisch_geslachtsnaam, 
hgn.dataanvgel as ingangsdatum_geldigheid, 
hgn.dateindegel as datum_einde_geldigheid,
case when p.geslachtsaand = 1 then --hgn.predicaat||' ('||
pdc.code||' '||pdc.naammannelijk--||')' 
     else case when p.geslachtsaand = 2 then --hgn.predicaat||' ('||
pdc.code||' '||pdc.naamvrouwelijk--||')'
               else --hgn.predicaat||' ('||
pdc.code||' Onbekend'--)
          end
end as predicaat,  
case when p.geslachtsaand = 1 then --hgn.adellijketitel||' ('||
att.code||' '||att.naammannelijk--||')' 
     else case when p.geslachtsaand = 2 then --hgn.adellijketitel||' ('||
att.code||' '||att.naamvrouwelijk--||')'
               else --hgn.adellijketitel||' ('||
att.code||' Onbekend'--)

          end
end as adellijketitel,  

to_char(to_timestamp(extract(epoch from hgn.tsreg)) at time zone 'UTC','YYYY-MM-DD HH24:MI:SS') as tijdstip_registratie,
to_char(to_timestamp(extract(epoch from hgn.tsverval)) at time zone 'UTC','YYYY-MM-DD HH24:MI:SS') as tijdstip_verval,

hgn.nadereaandverval as nadere_aanduiding_verval,
case when hgn.actieverval is NULL then 'leeg'
else
case when hgn.actieverval=hgn.actieinh then 'gelijk aan actie inhoud'
else 'gevuld'
end 
end as actie_verval,
case when hgn.actieaanpgel is NULL then 'leeg' 
else 'gevuld'
end as actie_aanpassing_geldigheid,
case when hgn.actievervaltbvlevmuts is NULL then'leeg'
else 'gevuld'
end as actie_verval_levering_mutatie,
hgn.indvoorkomentbvlevmuts as indicatie_voorkomen_levering_mutatie

from kern.pers p
left join kern.persgeslnaamcomp gn on gn.pers = p.id
left join kern.his_persgeslnaamcomp hgn on hgn.persgeslnaamcomp = gn.id
left join kern.adellijketitel att on att.id = hgn.adellijketitel
left join kern.predicaat pdc on pdc.id = hgn.predicaat
where p.anr = '{anr}' AND 
p.srt =1
order by ingangsdatum_geldigheid desc, datum_einde_geldigheid desc, hgn.tsreg desc, hgn.actievervaltbvlevmuts desc, hgn.indvoorkomentbvlevmuts asc;
