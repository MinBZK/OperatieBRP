select 
case when o.id IS NULL then NULL else p.bsn end as bsn_nummer, 
case when o.id IS NULL then NULL else p.anr end as a_nummer, 
case when o.id IS NULL then NULL else p.vorigeanr end as vorige_a_nummer,
case when o.id IS NULL then NULL else p.volgendeanr end as volgende_a_nummer,
case when o.id IS NULL then NULL else p.srt end as soort_persoon,
o.dataanv as datum_aanvang_onderzoek,
o.dateinde as datum_einde_onderzoek,
o.oms as omschrijving,
sto.naam as status,

to_char(to_timestamp(extract(epoch from hpo.tsreg)) at time zone 'UTC','YYYY-MM-DD HH24:MI:SS') as tijdstip_registratie_onderzoek,
to_char(to_timestamp(extract(epoch from hpo.tsverval)) at time zone 'UTC','YYYY-MM-DD HH24:MI:SS') as tijdstip_verval_onderzoek,

hpo.nadereaandverval as nadere_aanduiding_verval

from kern.pers p
left join kern.onderzoek o on o.pers = p.id
left join kern.his_onderzoek hpo on hpo.onderzoek = o.id
left join kern.statusonderzoek sto on sto.id = o.status
where
p.anr = '{anr}' AND p.srt =1
order by tijdstip_registratie_onderzoek desc, omschrijving desc;
