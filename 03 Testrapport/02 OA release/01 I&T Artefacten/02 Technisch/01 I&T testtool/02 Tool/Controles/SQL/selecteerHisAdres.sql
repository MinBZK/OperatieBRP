select  

p.bsn as bsn,
p.anr as anr,
p.vorigeanr as vorige_anr,
p.volgendeanr as volgende_anr,

hpa.dataanvadresh as datum_aanvang_adres,  
hpa.dataanvgel as datum_aanvang_geldigheid_adres, 
hpa.dateindegel as datum_einde_geldigheid_adres,

hpa.postcode as postcode,
hpa.nor as naam_openbare_ruimte, 
hpa.afgekortenor as afgekorte_naam_openbare_ruimte, 
hpa.huisnr as huisnr, 
hpa.huisletter as huisletter, 
hpa.huisnrtoevoeging as huisnummer_toevoeging, 
hpa.wplnaam as naam_woonplaats, 
hpa.loctenopzichtevanadres as loc_tov_adres,
hpa.locoms as locatie_omschrijving,
hpa.identcodeadresseerbaarobject as identificatiecode_adresseerbaar_object, 
hpa.identcodenraand as identificatiecode_nummer_aanduiding,
--hpa.srt||' ('||
sad.code||' '||sad.naam--||')' 
as soort_adres, 
hpa.bladresregel1 as buitenlandse_adresregel1, 
hpa.bladresregel2 as buitenlandse_adresregel2, 
hpa.bladresregel3 as buitenlandse_adresregel3, 
--hpa.landgebied||' ('||
l.code||' '||l.naam--||')' 
as land_adres, 

--hpa.aangadresh||' ('||
aah.code||' '||aah.naam--||')' 
as aangever_adres_huishouding, 
--hpa.gem||' ('||
g.code||' '||g.naam--||')' 
as gemeente_adres, 
hpa.gemdeel as gemeente_deel_adres,
--hpa.rdnwijz||' ('||
rwv.code||' '||rwv.naam--||')' 
as reden_wijz_verblijf,

to_char(to_timestamp(extract(epoch from hpa.tsreg)) at time zone 'UTC','YYYY-MM-DD HH24:MI:SS') as datum_opneming_adres,
to_char(to_timestamp(extract(epoch from hpa.tsverval)) at time zone 'UTC','YYYY-MM-DD HH24:MI:SS') as tijdstip_verval_adres,

hpa.nadereaandverval as nadere_aanduiding_verval_adres,
case when hpa.actieverval is NULL then 'leeg'
else
case when hpa.actieverval=hpa.actieinh then 'gelijk aan actie inhoud'
else 'gevuld'
end 
end as actie_verval,
case when hpa.actieaanpgel is NULL then 'leeg' 
else 'gevuld'
end as actie_aanpassing_geldigheid,
case when hpa.actievervaltbvlevmuts is NULL then'leeg'
else 'gevuld'
end as actie_verval_levering_mutatie,
hpa.indvoorkomentbvlevmuts as indicatie_voorkomen_levering_mutatie 

from   
kern.pers p
join kern.persadres pa on pa.pers in (select id from kern.pers where anr = p.anr)
join kern.his_persadres hpa on hpa.persadres = pa.id
left join kern.landgebied l on l.id = hpa.landgebied
left join kern.gem g on g.id = hpa.gem
left join kern.rdnwijzverblijf rwv on rwv.id = hpa.rdnwijz
left join kern.aang aah on aah.id = hpa.aangadresh
left join kern.srtadres sad on sad.id = hpa.srt 
where p.anr = '{anr}' 
AND p.srt=1
order by datum_aanvang_geldigheid_adres asc, datum_einde_geldigheid_adres desc, datum_opneming_adres asc, indicatie_voorkomen_levering_mutatie asc;