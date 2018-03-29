select 
case when hpeu.id IS NULL then NULL else p.bsn end as bsn_nummer, 
case when hpeu.id IS NULL then NULL else p.anr end as a_nummer,
case when hpeu.id IS NULL then NULL else p.vorigeanr end as vorige_a_nummer,
case when hpeu.id IS NULL then NULL else p.volgendeanr end as volgende_a_nummer, 
case when hpeu.id IS NULL then NULL else p.srt end as soort_persoon,
hpeu.inddeelneuverkiezingen as indicatie_deelname_euverkiezing,
hpeu.dataanlaanpdeelneuverkiezing as datum_deelname_euverkiezing,
hpeu.datvoorzeindeuitsleuverkiezi as datum_voorziening_einde_uitsluiting_euverkiezing,

to_char(to_timestamp(extract(epoch from hpeu.tsreg)) at time zone 'UTC','YYYY-MM-DD HH24:MI:SS') as tijdstip_registratie_deelname_euverkiezing,
to_char(to_timestamp(extract(epoch from hpeu.tsverval)) at time zone 'UTC','YYYY-MM-DD HH24:MI:SS') as tijdstip_verval_deelname_euverkiezing,

hpeu.nadereaandverval as nadere_aanduiding_verval_deelname_euverkiezing,
case when hpeu.actieverval is NULL then 
'leeg'
else
case when hpeu.actieverval=hpeu.actieinh then 
'gelijk aan actie inhoud'
else 
'gevuld'
end 
end as actie_verval,
case when hpeu.actievervaltbvlevmuts is NULL then
'leeg'
else 'gevuld'
end as actie_verval_levering_mutatie,
hpeu.indvoorkomentbvlevmuts as indicatie_voorkomen_levering_mutatie

from kern.pers p
left join kern.his_persdeelneuverkiezingen hpeu on hpeu.pers = p.id
where
p.anr = '{anr}' AND p.srt =1
order by tijdstip_registratie_deelname_euverkiezing desc;
