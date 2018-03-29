select 
case when pi.id is NULL then NULL else p.bsn end as bsn_nummer, 
case when pi.id is NULL then NULL else p.anr end as a_nummer, 
case when pi.id is NULL then NULL else p.vorigeanr end as vorige_a_nummer,
case when pi.id is NULL then NULL else p.volgendeanr end as volgende_a_nummer,
case when pi.id is NULL then NULL else p.srt end as soort_persoon, 
--pi.srt||' ('||
sid.naam--||')' 
as soort_indicatie,
hpi.waarde as waarde,
hpi.dataanvgel as datum_aanvang_geldigheid_indicatie,
hpi.dateindegel as datum_einde_geldigheid_indicatie,
hpi.indvoorkomentbvlevmuts as indicatie_voorkomen_levering_mutatie,
hpi.migrrdnopnamenation as migratie_reden_opname,
hpi.migrrdnbeeindigennation as migratie_reden_beeindiging,

to_char(to_timestamp(extract(epoch from hpi.tsreg)) at time zone 'UTC','YYYY-MM-DD HH24:MI:SS') as tijdstip_registratie_indicatie,
to_char(to_timestamp(extract(epoch from hpi.tsverval)) at time zone 'UTC','YYYY-MM-DD HH24:MI:SS') as tijdstip_verval_indicatie,

hpi.nadereaandverval as nadere_aanduiding_verval_indicatie,
case when hpi.actieverval is NULL then 'leeg'
else
case when hpi.actieverval=hpi.actieinh then 'gelijk aan actie inhoud'
else 'gevuld'
end 
end as actie_verval,
case when hpi.actieaanpgel is NULL then 'leeg' 
else 'gevuld'
end as actie_aanpassing_geldigheid,
case when hpi.actievervaltbvlevmuts is NULL then'leeg'
else 'gevuld'
end as actie_verval_levering_mutatie


from kern.pers p
left join kern.persindicatie pi on pi.pers = p.id
left join kern.his_persindicatie hpi on hpi.persindicatie = pi.id
left join kern.srtindicatie sid on sid.id = pi.srt
where p.anr = '{anr}' 
AND p.srt =1
order by hpi.tsreg desc, datum_aanvang_geldigheid_indicatie desc,  datum_einde_geldigheid_indicatie desc;
