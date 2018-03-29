select 
case when pr.id IS NULL then NULL else p.bsn end as bsn_nummer, 
case when pr.id IS NULL then NULL else p.anr end as a_nummer, 
case when pr.id IS NULL then NULL else p.vorigeanr end as vorige_a_nummer,
case when pr.id IS NULL then NULL else p.volgendeanr end as volgende_a_nummer,
case when pr.id IS NULL then NULL else p.srt end as soort_persoon, 
--pr.srt||' ('||
srd.code||' '||srd.oms--||')' 
as soort_reisdocument,
hpr.nr as nummer_reisdocument,
hpr.autvanafgifte as autoriteit_afgifte_reisdocument,
hpr.datingangdoc as datum_ingang_reisdocument,
hpr.dateindedoc as datum_einde_reisdocument,
hpr.datuitgifte as datum_uitgifte_reisdocument,
hpr.datinhingvermissing as datum_ingang_inhouding_vermissing,
--hpr.aandinhingvermissing||' ('||
avr.code||' '||avr.naam--||')' 
as aanduiding_inhouding_vermissing,

to_char(to_timestamp(extract(epoch from hpr.tsreg)) at time zone 'UTC','YYYY-MM-DD HH24:MI:SS') as tijdstip_registratie_reisdocument,
to_char(to_timestamp(extract(epoch from hpr.tsverval)) at time zone 'UTC','YYYY-MM-DD HH24:MI:SS') as tijdstip_verval_reisdocument,

hpr.nadereaandverval as nadere_aanduiding_verval_reisdocument,
case when hpr.actieverval is NULL then 'leeg'
else
case when hpr.actieverval=hpr.actieinh then 'gelijk aan actie inhoud'
else 'gevuld'
end 
end as actie_verval,
case when hpr.actievervaltbvlevmuts is NULL then'leeg'
else 'gevuld'
end as actie_verval_levering_mutatie,
hpr.indvoorkomentbvlevmuts as indicatie_voorkomen_levering_mutatie

from kern.pers p
left join kern.persreisdoc pr on pr.pers = p.id
left join kern.his_persreisdoc hpr on hpr.persreisdoc = pr.id
left join kern.srtnlreisdoc srd on srd.id = pr.srt
left join kern.aandinhingvermissingreisdoc avr on avr.id = hpr.aandinhingvermissing
where
p.anr = '{anr}' AND p.srt =1
order by nummer_reisdocument desc, datum_ingang_reisdocument desc,  datum_einde_reisdocument desc;
