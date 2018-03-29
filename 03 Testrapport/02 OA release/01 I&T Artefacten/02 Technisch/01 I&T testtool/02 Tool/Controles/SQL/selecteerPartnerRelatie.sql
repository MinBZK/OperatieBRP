select 

relatie.srt as soort_huwelijk,
huwelijk.dataanv as datum_aanvang_huwelijk,
--huwelijk.gemaanv||' ('||
gema.code||' '||gema.naam--||')' 
as gemeente_aanvang_huwelijk, 
huwelijk.wplnaamaanv as woonplaats_aanvang_huwelijk, 
huwelijk.blplaatsaanv as buitenlandseplaats_aanvang_huwelijk, 
huwelijk.blregioaanv as buitenlandse_regio_aanvang_huwelijk, 
--huwelijk.landgebiedaanv||' ('||
lgba.code||' '||lgba.naam--||')' 
as land_gebied_aanvang_huwelijk, 
huwelijk.omslocaanv as omschrijving_locatie_aanvang_huwelijk, 
--huwelijk.rdneinde||' ('||
rer.code||' '||rer.oms--||')' 
as reden_einde_huwelijk,
huwelijk.dateinde as datum_einde_huwelijk, 
--huwelijk.gemeinde||' ('||
geme.code||' '||geme.naam--||')' 
as gemeente_einde_huwelijk, 
huwelijk.wplnaameinde as woonplaats_einde_huwelijk, 
huwelijk.blplaatseinde as buitenlandse_plaats_einde_huwelijk, 
huwelijk.blregioeinde as buitenlandseregio_einde_huwelijk, 
--huwelijk.landgebiedeinde||' ('||
lgbe.code||' '||lgbe.naam--||')' 
as land_gebied_einde_huwelijk, 
huwelijk.omsloceinde as omschrijving_locatie_einde_huwelijk,


to_char(to_timestamp(extract(epoch from huwelijk.tsreg)) at time zone 'UTC','YYYY-MM-DD HH24:MI:SS') as tijdstip_registratie_huwelijk,
to_char(to_timestamp(extract(epoch from huwelijk.tsverval)) at time zone 'UTC','YYYY-MM-DD HH24:MI:SS') as tijdstip_verval_huwelijk,
huwelijk.nadereaandverval as nadere_aanduiding_verval_huwelijk,
case when huwelijk.actieverval is NULL then 
'leeg'
else
case when huwelijk.actieverval=huwelijk.actieinh then 
'gelijk aan actie inhoud'
else 
'gevuld'
end 
end as actie_verval_huwelijk,
case when huwelijk.actievervaltbvlevmuts is NULL then
'leeg'
else 'gevuld'
end as actie_verval_levering_mutatie_huwelijk,
huwelijk.indvoorkomentbvlevmuts as indicatie_voorkomen_levering_mutatie_huwelijk,

sb.naam as soort_betrokkene, 

-- his_perssamengesteldenaam_partner
'---------------' as HIS_PERSSAMENGESTELDENAAM_PARTNER,
hsn.voornamen as hissamnaam_voornamen_partner,
case when partner.geslachtsaand = 1 then --hsn.adellijketitel||' ('||
att.code||' '||att.naammannelijk--||')' 
     else case when partner.geslachtsaand = 2 then --hsn.adellijketitel||' ('||
att.code||' '||att.naamvrouwelijk--||')'
               else --hsn.adellijketitel||' ('||
att.code||' Onbekend'
          end
end 
as hissamnaam_adellijketitel_partner,  
case when partner.geslachtsaand = 1 then --hsn.predicaat||' ('||
pdc.code||' '||pdc.naammannelijk--||')' 
     else case when partner.geslachtsaand = 2 then --hsn.predicaat||' ('||
pdc.code||' '||pdc.naamvrouwelijk--||')'
               else --hsn.predicaat||' ('||
pdc.code||' Onbekend'
          end
end 
as hissamnaam_predicaat_partner, 
hsn.voorvoegsel as hissamnaam_voorvoegsel_partner,
hsn.scheidingsteken as hissamnaam_scheidingsteken_partner,
hsn.geslnaamstam as hissamnaam_geslachtsnaamstam_partner,
hsn.dataanvgel as hissamnaam_datum_aanvang_geldigheid_partner,
hsn.dateindegel as hissamnaam_datum_einde_geldigheid_partner,
hsn.nadereaandverval as hissamnaam_nadere_aanduiding_verval_partner,
to_char(to_timestamp(extract(epoch from hsn.tsreg)) at time zone 'UTC','YYYY-MM-DD HH24:MI:SS') as hissamnaam_tijdstip_registratie_partner,
to_char(to_timestamp(extract(epoch from hsn.tsverval)) at time zone 'UTC','YYYY-MM-DD HH24:MI:SS') as hissamnaam_tijdstip_verval_partner,
case when hsn.actieverval is NULL then 'leeg'
else
case when hsn.actieverval=hsn.actieinh then 'gelijk aan actie inhoud'
else 'gevuld'
end 
end as hissamnaam_actie_verval_partner,
case when hsn.actievervaltbvlevmuts is NULL then'leeg'
else 'gevuld'
end as hissamnaam_actie_verval_levering_mutatie_partner,
hsn.indvoorkomentbvlevmuts as hissamnaam_indicatie_voorkomen_levering_mutatie_partner,

-- his_persids_partner
'---------------' as HIS_PERSIDS_PARTNER,
hpi.anr as hispersids_A_nummer_partner,
hpi.bsn as hispersids_burgerservicenummer_partner,
hpi.dataanvgel as hispersids_datum_aanvang_geldigheid_partner,
hpi.dateindegel as hispersids_datum_einde_geldigheid_partner,
hpi.nadereaandverval as hispersids_nadere_aanduiding_verval_partner,
to_char(to_timestamp(extract(epoch from hpi.tsreg)) at time zone 'UTC','YYYY-MM-DD HH24:MI:SS') as hispersids_tijdstip_registratie_partner,
to_char(to_timestamp(extract(epoch from hpi.tsverval)) at time zone 'UTC','YYYY-MM-DD HH24:MI:SS') as hispersids_tijdstip_verval_partner,
case when hpi.actieverval is NULL then 'leeg'
else
case when hpi.actieverval=hpi.actieinh then 'gelijk aan actie inhoud'
else 'gevuld'
end 
end as hispersids_actie_verval_partner,
case when hpi.actievervaltbvlevmuts is NULL then'leeg'
else 'gevuld'
end as hispersids_actie_verval_levering_mutatie_partner,
hpi.indvoorkomentbvlevmuts as hispersids_indicatie_voorkomen_levering_mutatie_partner,

-- his_betr
'---------------' as HIS_BETR,
to_char(to_timestamp(extract(epoch from hb.tsreg)) at time zone 'UTC','YYYY-MM-DD HH24:MI:SS') as hisbetr_tijdstip_registratie,
to_char(to_timestamp(extract(epoch from hb.tsverval)) at time zone 'UTC','YYYY-MM-DD HH24:MI:SS') as hisbetr_tijdstip_verval,
hb.nadereaandverval as hisbetr_nadere_aanduiding_verval,
case when hb.actieverval is NULL then 'leeg'
else
case when hb.actieverval=hb.actieinh then 'gelijk aan actie inhoud'
else 'gevuld'
end 
end as hisbetr_actie_verval,
case when hb.actievervaltbvlevmuts is NULL then'leeg'
else 'gevuld'
end as hisbetr_actie_verval_levering_mutatie,
hb.indvoorkomentbvlevmuts as hisbetr_indicatie_voorkomen_levering_mutatie,

-- his_persgeboorte_partner
'---------------' as HIS_PERSGEBOORTE_PARTNER,
hgb.datgeboorte as hisgeb_datum_geboorte_partner,
hgb.blregiogeboorte as hisgeb_buitenlandse_regio_geboorte_partner,
hgb.omslocgeboorte as hisgeb_omschrijving_locatie_geboorte_partner,
--hgb.gemgeboorte||' ('||
gemg.code||' '||gemg.naam--||')' 
as hisgeb_gemeente_geboorte_partner, 
hgb.wplnaamgeboorte as hisgeb_woonplaatsnaam_geboorte_partner, 
hgb.blplaatsgeboorte as hisgeb_buitenlandse_plaats_geboorte_partner, 
--hgb.landgebiedgeboorte||' ('||
lgbg.code||' '||lgbg.naam--||')' 
as hisgeb_landgebied_geboorte_partner,
to_char(to_timestamp(extract(epoch from hgb.tsreg)) at time zone 'UTC','YYYY-MM-DD HH24:MI:SS') as hisgeb_tijdstip_registratie_partner,
to_char(to_timestamp(extract(epoch from hgb.tsverval)) at time zone 'UTC','YYYY-MM-DD HH24:MI:SS') as hisgeb_tijdstip_verval_partner,
hgb.nadereaandverval as hisgeb_nadere_aanduiding_verval_partner,
case when hgb.actieverval is NULL then 'leeg'
else
case when hgb.actieverval=hgb.actieinh then 'gelijk aan actie inhoud'
else 'gevuld'
end 
end as hisgeb_actie_verval_partner,
case when hgb.actievervaltbvlevmuts is NULL then'leeg'
else 'gevuld'
end as hisgeb_actie_verval_levering_mutatie_partner_partner,
hgb.indvoorkomentbvlevmuts as hisgeb_indicatie_voorkomen_levering_mutatie_partner,

-- his_persgeslachtsaand_partner
'---------------' as HIS_PERSGESLACHTSAAND_PARTNER,
--hga.geslachtsaand||' ('||
gad.code||' '||gad.naam--||')' 
as hisgeslachtsaand_geslachtsaand_partner,
hga.dataanvgel as hisgeslachtsaand_datum_aanvang_geldigheid_partner,
hga.dateindegel as hisgeslachtsaand_datum_einde_geldigheid_partner,
to_char(to_timestamp(extract(epoch from hga.tsreg)) at time zone 'UTC','YYYY-MM-DD HH24:MI:SS') as hisgeslachtsaand_tijdstip_registratie_partner,
to_char(to_timestamp(extract(epoch from hga.tsverval)) at time zone 'UTC','YYYY-MM-DD HH24:MI:SS') as hisgeslachtsaand_tijdstip_verval_partner,
hga.nadereaandverval as hisgeslachtsaand_nadere_aanduiding_verval_partner,
case when hga.actieverval is NULL then 'leeg'
else
case when hga.actieverval=hgb.actieinh then 'gelijk aan actie inhoud'
else 'gevuld'
end 
end as hisgeslachtsaand_actie_verval_partner,
case when hga.actievervaltbvlevmuts is NULL then'leeg'
else 'gevuld'
end as hisgeslachtsaand_actie_verval_levering_mutatie_partner,
hga.indvoorkomentbvlevmuts as hisgeslachtsaand_indicatie_voorkomen_levering_mutatie_partner

from kern.pers subject
inner join kern.betr kb on subject.id = kb.pers
inner join kern.relatie relatie on kb.relatie = relatie.id
inner join kern.betr ob on ob.relatie = kb.relatie
inner join kern.his_relatie huwelijk on huwelijk.relatie = relatie.id
left join kern.pers partner on ob.pers = partner.id
left join kern.his_betr hb on kb.id=hb.betr
left join kern.srtbetr sb on sb.id = ob.rol
left join kern.his_perssamengesteldenaam hsn on hsn.pers = partner.id
left join kern.his_persgeboorte hgb on hgb.pers = partner.id
-- and... toegevoegd om lege hpi te vinden indien bij partner er initieel geen ids zijn; gaat dit goed indien bij partner met hsn en hpi alleen hpi wordt gewijzigd?!
left join kern.his_persids hpi on hpi.pers = partner.id 
                                  and hsn.tsreg >= hpi.tsreg
-- and... toegevoegd om lege hga te vinden indien bij partner er initieel geen hga is; gaat dit goed indien bij partner met hsn en hga alleen hga wordt gewijzigd?!
left join kern.his_persgeslachtsaand hga on hga.pers = partner.id
                                  and hsn.tsreg >= hga.tsreg
left join kern.rdneinderelatie rer on rer.id = huwelijk.rdneinde
left join kern.gem gema on gema.id = huwelijk.gemaanv
left join kern.gem geme on geme.id = huwelijk.gemeinde
left join kern.gem gemg on gemg.id = hgb.gemgeboorte
left join kern.landgebied lgba on lgba.id = huwelijk.landgebiedaanv
left join kern.landgebied lgbe on lgbe.id = huwelijk.landgebiedeinde
left join kern.landgebied lgbg on lgbg.id = hgb.landgebiedgeboorte
left join kern.geslachtsaand gad on gad.id = hga.geslachtsaand
left join kern.predicaat pdc on pdc.id = hsn.predicaat
left join kern.adellijketitel att on att.id = hsn.adellijketitel

WHERE ob.rol = 3
--and relatie.srt = 1
and kb.rol = 3
and subject.srt = 1
and partner.srt = 2
and subject.anr = '{anr}'
-- testgevallen M03
--and subject.anr = 4025890768 -- HG14007-P1
--and subject.anr = 4046454836 -- HG14007-P2
--and subject.anr = 4031827586 -- HG14008-P1
--and subject.anr = 2319767414 -- NG1245001-P2
--and subject.anr = 4091501016 -- HG14003-P1 3 recs goed, 4 fout
--and subject.anr = 4021282012 -- HG9001-P2 6 recs
--and subject.anr = 4095604592 -- HG9001-P1 6 recs(was 5 fout)
--and subject.anr = 2386217130 -- BB12001 M03 1 rec
--and subject.anr = 1401024181 -- 018-P1
--and subject.anr = 2348584154 -- BB9001-P1 1 rec beter 2
--and subject.anr = 2381032194 -- BG9001-P2
--and subject.anr = 4091501016  --HG14003-P1
--and subject.anr = 4097534296  --HG14003-P2
--and subject.anr = 4079606368  --HG15001-P1
--and subject.anr = 4024752324 --V?NA1003-P2
--and subject.anr = 1401039654  --C07-P1
--and subject.anr = 4016758913  --HG10001-P1 

-- hsn.actieinh met dezelfde admhnd hebben als huwelijk.actieinh
-- óf er is geen huwelijk wiens actieinh uit dezelfde admhnd komt als de actieinh van hsn
-- óf er is geen hsn wiens actieinh uit dezelfde admhnd komt als de actieinh van huwelijk   
and not ((select actie1.admhnd from kern.actie actie1 where actie1.id = hsn.actieinh) <> 
         (select actie2.admhnd from kern.actie actie2 where actie2.id = huwelijk.actieinh) 
     and exists (select * from kern.his_relatie trel, kern.actie tactie1, kern.actie tactie2
          where trel.actieinh = tactie1.id and tactie1.admhnd = tactie2.admhnd and tactie2.id = hsn.actieinh )
     and exists (select * from kern.his_perssamengesteldenaam thsn, kern.actie tactie1, kern.actie tactie2
        where thsn.actieinh = tactie1.id and tactie1.admhnd = tactie2.admhnd and tactie2.id = huwelijk.actieinh ))

-- dummy voorwaarde (kan weg)
and (hsn.actieinh in (select a.id from kern.actie a
                     where a.admhnd in (select b.admhnd from kern.actie b
                                        where b.id = hsn.actieinh))
    or
    not exists(select * from kern.his_perssamengesteldenaam c
    where c.actieinh in (select a.id from kern.actie a
                    where a.admhnd in (select b.admhnd from kern.actie b
                                        where b.id = hsn.actieinh))))                                   
-- hgb moet of een actieinh hebben uit dezelfde admhnd als hsn óf er is geen hgb bij de admhnd van hsn
and (hgb.actieinh in (select a.id from kern.actie a
                     where a.admhnd in (select b.admhnd from kern.actie b
                                        where b.id = hsn.actieinh))
    or
    not exists(select * from kern.his_persgeboorte c
    where c.actieinh in (select a.id from kern.actie a
                     where a.admhnd in (select b.admhnd from kern.actie b
                                        where b.id = hsn.actieinh))))   
  
-- hpi kan óf niet bestaan óf moet of een actieinh hebben uit dezelfde admhnd als samnaam óf er is geen hpi bij de admhnd van samnaam
and (hpi.id IS NULL 
     or 
     hpi.actieinh in (select a.id from kern.actie a
                     where a.admhnd in (select b.admhnd from kern.actie b
                                        where b.id = hsn.actieinh))
    or
    not exists(select * from kern.his_persids c
    where hpi.pers = c.pers and c.actieinh in (select a.id from kern.actie a
                     where a.admhnd in (select b.admhnd from kern.actie b
                                        where b.id = hsn.actieinh))))   
-- hga kan óf niet bestaan óf moet of een actieinh hebben uit dezelfde admhnd als samnaam óf er is geen hga bij de admhnd van samnaam
and (hga.id IS NULL
     or
     hga.actieinh in (select a.id from kern.actie a
                     where a.admhnd in (select b.admhnd from kern.actie b
                                        where b.id = hsn.actieinh))
     or
     not exists(select * from kern.his_persgeslachtsaand c
     where hga.pers = c.pers and c.actieinh in (select a.id from kern.actie a
                     where a.admhnd in (select b.admhnd from kern.actie b
                                        where b.id = hsn.actieinh))))  

-- .id IS NULL toegevoegd voor de blokken zonder hga of hpi
and ((hgb.actieinh in (select max(xhgb.actieinh) from kern.his_persgeboorte xhgb 
                     where xhgb.actieinh <= hsn.actieinh and xhgb.pers = hsn.pers) 
   and hga.id IS NULL or hga.actieinh in (select max(xhga.actieinh) from kern.his_persgeslachtsaand xhga
                     where xhga.actieinh <= hsn.actieinh and xhga.pers = hsn.pers)
   and huwelijk.actieinh in (select max(xhuwelijk.actieinh) from kern.his_relatie xhuwelijk  
                     where xhuwelijk.actieinh <= hsn.actieinh and xhuwelijk.relatie = relatie.id)
   and (hpi.id IS NULL or hpi.actieinh in (select max(xhpi.actieinh) from kern.his_persids xhpi
                     where xhpi.actieinh <= hsn.actieinh and xhpi.pers = hsn.pers)
                     or
                    not exists (select * from kern.his_persids xhpi
                     where xhpi.actieinh <= hsn.actieinh and xhpi.pers = hsn.pers)))
 or 
   (hgb.actieinh in (select max(xhgb.actieinh) from kern.his_persgeboorte xhgb, kern.betr xob, kern.relatie xrel 
                     where xhgb.actieinh <= huwelijk.actieinh and xhgb.pers = xob.pers and xob.id = ob.id and xob.relatie = xrel.id and xrel.id = huwelijk.relatie
                union  select xxhgb.actieinh from kern.his_persgeboorte xxhgb, kern.actie xxactie1, kern.actie xxactie2
                       where xxhgb.id = hgb.id and xxhgb.actieinh = xxactie1.id and xxactie2.id = huwelijk.actieinh and xxactie1.admhnd = xxactie2.admhnd)
  and hsn.actieinh in (select max(xhsn.actieinh) from kern.his_perssamengesteldenaam xhsn, kern.betr xob, kern.relatie xrel 
                    where xhsn.actieinh <= huwelijk.actieinh and xhsn.pers = xob.pers and xob.id = ob.id and xob.relatie = xrel.id and xrel.id = huwelijk.relatie
                union  select xxhsn.actieinh from kern.his_perssamengesteldenaam xxhsn, kern.actie xxactie1, kern.actie xxactie2
                       where xxhsn.id = hsn.id and xxhsn.actieinh = xxactie1.id and xxactie2.id = huwelijk.actieinh and xxactie1.admhnd = xxactie2.admhnd)
    and hga.actieinh in (select max(xhga.actieinh) from kern.his_persgeslachtsaand xhga, kern.betr xob, kern.relatie xrel 
                  where xhga.actieinh <= huwelijk.actieinh and xhga.pers = xob.pers and xob.id = ob.id and xob.relatie = xrel.id and xrel.id = huwelijk.relatie
                union  select xxhga.actieinh from kern.his_persgeslachtsaand xxhga, kern.actie xxactie1, kern.actie xxactie2
                       where xxhga.id = hga.id and xxhga.actieinh = xxactie1.id and xxactie2.id = huwelijk.actieinh and xxactie1.admhnd = xxactie2.admhnd)
  and (hpi.actieinh in (select max(xhpi.actieinh) from kern.his_persids xhpi, kern.betr xob, kern.relatie xrel
                       where xhpi.actieinh <= huwelijk.actieinh and xhpi.pers = xob.pers and xob.id = ob.id and xob.relatie = xrel.id and xrel.id = huwelijk.relatie
                union  select xxhpi.actieinh from kern.his_persids xxhpi, kern.actie xxactie1, kern.actie xxactie2
                       where xxhpi.id = hpi.id and xxhpi.actieinh = xxactie1.id and xxactie2.id = huwelijk.actieinh and xxactie1.admhnd = xxactie2.admhnd )                or
                   not exists (select * from kern.his_persids xhpi, kern.betr xob, kern.relatie xrel 
                                       where xhpi.actieinh <= huwelijk.actieinh and xhpi.pers = xob.pers and xob.id = ob.id and xob.relatie = xrel.id and xrel.id = huwelijk.relatie))
)
  or 
   (hsn.actieinh in (select max(xhsn.actieinh) from kern.his_perssamengesteldenaam xhsn 
                     where xhsn.actieinh <= hga.actieinh and xhsn.pers = hga.pers)
    and hgb.actieinh in (select max(xhgb.actieinh) from kern.his_persgeboorte xhgb 
                     where xhgb.actieinh <= hga.actieinh and xhgb.pers = hga.pers)
    and huwelijk.actieinh in (select max(xhuwelijk.actieinh) from kern.his_relatie xhuwelijk 
                     where xhuwelijk.actieinh <= hga.actieinh and xhuwelijk.relatie = relatie.id)
   and (hpi.id IS NULL or hpi.actieinh in (select max(xhpi.actieinh) from kern.his_persids xhpi
                     where xhpi.actieinh <= hga.actieinh and xhpi.pers = hga.pers)
                     or
                     not exists (select * from kern.his_persids xhpi
                     where xhpi.actieinh <= hga.actieinh and xhpi.pers = hga.pers)))
   or
   (hsn.actieinh in (select max(xhsn.actieinh) from kern.his_perssamengesteldenaam xhsn 
                     where xhsn.actieinh <= hgb.actieinh and xhsn.pers = hgb.pers)
    and (hga.id IS NULL or hga.actieinh in (select max(xhga.actieinh) from kern.his_persgeslachtsaand xhga 
                     where xhga.actieinh <= hgb.actieinh and xhga.pers = hgb.pers))
    and huwelijk.actieinh in (select max(xhuwelijk.actieinh) from kern.his_relatie xhuwelijk 
                     where xhuwelijk.actieinh <= hgb.actieinh and xhuwelijk.relatie = relatie.id)
 and (hpi.id IS NULL or hpi.actieinh in (select max(xhpi.actieinh) from kern.his_persids xhpi
                     where xhpi.actieinh <= hgb.actieinh and xhpi.pers = hgb.pers)
                     or
                     not exists (select * from kern.his_persids xhpi
                     where xhpi.actieinh <= hgb.actieinh and xhpi.pers = hgb.pers))) 
   or
   (hsn.actieinh in (select max(xhsn.actieinh) from kern.his_perssamengesteldenaam xhsn 
                     where xhsn.actieinh <= hpi.actieinh and xhsn.pers = hpi.pers)
   and (hga.id IS NULL or hga.actieinh in (select max(xhga.actieinh) from kern.his_persgeslachtsaand xhga 
                     where xhga.actieinh <= hpi.actieinh and xhga.pers = hpi.pers))
    and huwelijk.actieinh in (select max(xhuwelijk.actieinh) from kern.his_relatie xhuwelijk 
                     where xhuwelijk.actieinh <= hpi.actieinh and xhuwelijk.relatie = relatie.id)
 and (hgb.actieinh in (select max(xhgb.actieinh) from kern.his_persids xhgb
                     where xhgb.actieinh <= hpi.actieinh and xhgb.pers = hpi.pers)
                     or
                     not exists (select * from kern.his_persids xhgb
                     where xhgb.actieinh <= hpi.actieinh and xhgb.pers = hpi.pers))))

order by huwelijk.dataanv asc, partner.anr asc, 
tijdstip_registratie_huwelijk asc, hissamnaam_tijdstip_registratie_partner asc, hispersids_tijdstip_registratie_partner, 
hisbetr_tijdstip_registratie, hisgeb_tijdstip_registratie_partner, hisgeslachtsaand_tijdstip_registratie_partner,
tijdstip_verval_huwelijk asc, hissamnaam_tijdstip_verval_partner asc, hispersids_tijdstip_verval_partner, 
hisbetr_tijdstip_verval, hisgeb_tijdstip_verval_partner, hisgeslachtsaand_tijdstip_verval_partner, reden_einde_huwelijk, datum_einde_huwelijk, gemeente_einde_huwelijk, land_gebied_einde_huwelijk, gemeente_aanvang_huwelijk
;