select 
p.bsn as bsn_nummer, 
p.anr as a_nummer, 
p.vorigeanr as vorige_a_nummer,
p.volgendeanr as volgende_a_nummer,
p.srt as soort_persoon, 
--pn.nation||' ('||
n.code||' '||n.naam  --||')' 
as nationaliteit,

hpn.dataanvgel as datum_aanvang_geldigheid_nationaliteit,
hpn.dateindegel as datum_einde_geldigheid_nationaliteit,
--hpn.rdnverk||' ('||
rvknn.code||' '||rvknn.oms  --||')' 
as reden_verkrijgen_nationalieit,
--hpn.rdnverlies||' ('||
rvlnn.code||' '||rvlnn.oms --||')' 
as reden_verlies_nationaliteit,
hpn.indbijhoudingbeeindigd as bijhouding_beeindigd,
hpn.migrrdnopnamenation as migratie_reden_opname,
hpn.migrrdnbeeindigennation as migratie_reden_beeindiging,
hpn.migrdateindebijhouding as migratie_dat_einde_bijhouding,

to_char(to_timestamp(extract(epoch from hpn.tsreg)) at time zone 'UTC','YYYY-MM-DD HH24:MI:SS') as tijdstip_registratie_nationaliteit,
to_char(to_timestamp(extract(epoch from hpn.tsverval)) at time zone 'UTC','YYYY-MM-DD HH24:MI:SS') as tijdstip_verval_nationaliteit,

hpn.nadereaandverval as nadere_aanduiding_verval_nationaliteit,
case when hpn.actieverval is NULL then 'leeg'
else
case when hpn.actieverval=hpn.actieinh then 'gelijk aan actie inhoud'
else 'gevuld'
end 
end as actie_verval,
case when hpn.actieaanpgel is NULL then 'leeg' 
else 'gevuld'
end as actie_aanpassing_geldigheid,
case when hpn.actievervaltbvlevmuts is NULL then'leeg'
else 'gevuld'
end as actie_verval_levering_mutatie,
hpn.indvoorkomentbvlevmuts as indicatie_voorkomen_levering_mutatie


from kern.pers p
left join kern.persnation pn on pn.pers = p.id
left join kern.his_persnation hpn on hpn.persnation = pn.id
left join kern.nation n on n.id = pn.nation
left join kern.rdnverknlnation rvknn on rvknn.id = hpn.rdnverk
left join kern.rdnverliesnlnation rvlnn on rvlnn.id = hpn.rdnverlies
--left join kern.actie actie on actie.id = hpn.actieinh
--left join kern.actiebron actieb on actieb.actie = actie.id
--left join kern.doc doc on doc.id= actieb.doc 
where 
p.anr = '{anr}' 
AND p.srt =1
order by hpn.tsreg desc, datum_aanvang_geldigheid_nationaliteit desc,  datum_einde_geldigheid_nationaliteit desc, actie_verval asc, indicatie_voorkomen_levering_mutatie desc, nadere_aanduiding_verval_nationaliteit desc;
