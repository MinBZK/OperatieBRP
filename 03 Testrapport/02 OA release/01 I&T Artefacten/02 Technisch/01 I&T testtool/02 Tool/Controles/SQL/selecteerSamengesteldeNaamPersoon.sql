select 
p.bsn as bsn_nummer, 
p.anr as a_nummer, 
p.vorigeanr as vorige_a_nummer,
p.volgendeanr as volgende_a_nummer,
p.srt as soort_persoon, 
hsn.indafgeleid as indafgeleid,
hsn.indnreeks as indnreeks,
case when p.geslachtsaand = 1 then --hsn.predicaat||' ('||
pdc.code||' '||pdc.naammannelijk--||')' 
     else case when p.geslachtsaand = 2 then --hsn.predicaat||' ('||
pdc.code||' '||pdc.naamvrouwelijk--||')'
               else --hsn.predicaat||' ('||
pdc.code||' Onbekend'
          end
end as predicaat, 
hsn.voornamen as voornamen,
case when p.geslachtsaand = 1 then --hsn.adellijketitel||' ('||
att.code||' '||att.naammannelijk--||')' 
     else case when p.geslachtsaand = 2 then --hsn.adellijketitel||' ('||
att.code||' '||att.naamvrouwelijk--||')'
               else --hsn.adellijketitel||' ('||
att.code||' Onbekend'
          end
end as adellijketitel, 
hsn.voorvoegsel as voorvoegsel,
hsn.scheidingsteken as scheidingsteken,
hsn.geslnaamstam as geslachtsnaamstam,  
hsn.dataanvgel as ingangsdatum_geldigheid, 
hsn.dateindegel as datum_einde_geldigheid,

to_char(to_timestamp(extract(epoch from hsn.tsreg)) at time zone 'UTC','YYYY-MM-DD HH24:MI:SS') as tijdstip_registratie,
to_char(to_timestamp(extract(epoch from hsn.tsverval)) at time zone 'UTC','YYYY-MM-DD HH24:MI:SS') as tijdstip_verval,

hsn.nadereaandverval as nadere_aanduiding_verval,
case when hsn.actieverval is NULL then 'leeg'
else
case when hsn.actieverval=hsn.actieinh then 'gelijk aan actie inhoud'
else 'gevuld'
end 
end as actie_verval,
case when hsn.actieaanpgel is NULL then 'leeg' 
else 'gevuld'
end as actie_aanpassing_geldigheid,
case when hsn.actievervaltbvlevmuts is NULL then'leeg'
else 'gevuld'
end as actie_verval_levering_mutatie,
hsn.indvoorkomentbvlevmuts as indicatie_voorkomen_levering_mutatie

from kern.pers p
left join kern.his_perssamengesteldenaam hsn on hsn.pers = p.id
left join kern.adellijketitel att on att.id = hsn.adellijketitel
left join kern.predicaat pdc on pdc.id = hsn.predicaat
where p.anr = '{anr}' AND 
p.srt =1
order by ingangsdatum_geldigheid desc, datum_einde_geldigheid desc, hsn.tsreg desc, hsn.actievervaltbvlevmuts desc, hsn.indvoorkomentbvlevmuts asc;