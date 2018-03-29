select 
sb.naam as soort_betrokkene, 
kind.bsn as pers_bsn_nr, 
kind.anr as pers_anr,

--kind.datgeboorte as pers_geboorte_datum, 
case when kind.datgeboorte = 0 then trim(to_char(kind.datgeboorte, '99999999'))
when {placeholder}=kind.datgeboorte then 'gelijk aan placeholder'
else trim(to_char(kind.datgeboorte, '99999999'))
end 
as pers_geboorte_datum, 
kind.voornamen as pers_voornamen, 
kind.voorvoegsel as pers_voorvoegsel, 
kind.scheidingsteken as pers_scheidingsteken, 
kind.geslnaamstam as pers_geslachtsnaam_stam, 
kind.geslachtsaand as pers_geslachts_aanduiding, 
kind.srt as pers_soort_persoon,

--his_ouderouderschap
'---------------' as HIS_OUDEROUDERSCHAP,
ho.indouderuitwiekindisgeboren as hisouderschap_ind_uit_wie_kind_is_geboren,
ho.dataanvgel as hisouderschap_datum_aanvang, 
ho.dateindegel as hisouderschap_datum_einde,
ho.nadereaandverval as hisouderschap_nadere_aanduiding_verval,
to_char(to_timestamp(extract(epoch from ho.tsreg)) at time zone 'UTC','YYYY-MM-DD HH24:MI:SS') as hisouderschap_tijdstip_registratie,
to_char(to_timestamp(extract(epoch from ho.tsverval)) at time zone 'UTC','YYYY-MM-DD HH24:MI:SS') as hisouderschap_tijdstip_verval,
case when ho.actieverval is NULL then 'leeg'
else
case when ho.actieverval=ho.actieinh then 'gelijk aan actie inhoud'
else 'gevuld'
end 
end as hisouderschap_actie_verval,
case when ho.actievervaltbvlevmuts is NULL then'leeg'
else 'gevuld'
end as hisouderschap_actie_verval_levering_mutatie,
ho.indvoorkomentbvlevmuts as hisouderschap_indicatie_voorkomen_levering_mutatie,

-- his_ouderouderlijkgezag
'---------------' as HIS_OUDEROUDERLIJKGEZAG,
hog.indouderheeftgezag as hisouderlijkgezag_ind_ouder_heeft_gezag,
hog.dataanvgel as hisouderlijkgezag_datum_aanvang, 
hog.dateindegel as hisouderlijkgezag_datum_einde,
hog.nadereaandverval as hisouderlijkgezag_nadere_aanduiding_verval,
to_char(to_timestamp(extract(epoch from hog.tsreg)) at time zone 'UTC','YYYY-MM-DD HH24:MI:SS') as hisouderlijkgezag_tijdstip_registratie,
to_char(to_timestamp(extract(epoch from hog.tsverval)) at time zone 'UTC','YYYY-MM-DD HH24:MI:SS') as hisouderlijkgezag_tijdstip_verval,
case when hog.actieverval is NULL then 'leeg'
else
case when hog.actieverval=hog.actieinh then 'gelijk aan actie inhoud'
else 'gevuld'
end 
end as hisouderlijkgezag_actie_verval,
case when hog.actievervaltbvlevmuts is NULL then'leeg'
else 'gevuld'
end as hisouderlijkgezag_actie_verval_levering_mutatie,
hog.indvoorkomentbvlevmuts as hisouderlijkgezag_indicatie_voorkomen_levering_mutatie,

-- his_perssamengesteldenaam
'---------------' as HIS_PERSSAMENGESTELDENAAM,
hsn.voornamen as hissamnaam_voornamen,
case when hga.geslachtsaand = 1 then --hsn.adellijketitel||' ('||
att.code||' '||att.naammannelijk--||')' 
     else case when hga.geslachtsaand = 2 then --hsn.adellijketitel||' ('||
att.code||' '||att.naamvrouwelijk--||')'
               else --hsn.adellijketitel||' ('||
att.code||' Onbekend'
          end
end 
as hissamnaam_adellijketitel,  
case when hga.geslachtsaand = 1 then --hsn.predicaat||' ('||
pdc.code||' '||pdc.naammannelijk--||')' 
     else case when hga.geslachtsaand = 2 then --hsn.predicaat||' ('||
pdc.code||' '||pdc.naamvrouwelijk--||')'
               else --hsn.predicaat||' ('||
pdc.code||' Onbekend'
          end
end 
as hissamnaam_predicaat,    
hsn.voorvoegsel as hissamnaam_voorvoegsel,
hsn.scheidingsteken as hissamnaam_scheidingsteken,
hsn.geslnaamstam as hissamnaam_geslachtsnaamstam,
hsn.dataanvgel as hissamnaam_datum_aanvang_geldigheid,
hsn.dateindegel as hissamnaam_datum_einde_geldigheid,
hsn.nadereaandverval as hissamnaam_nadere_aanduiding_verval,
to_char(to_timestamp(extract(epoch from hsn.tsreg)) at time zone 'UTC','YYYY-MM-DD HH24:MI:SS') as hissamnaam_tijdstip_registratie,
to_char(to_timestamp(extract(epoch from hsn.tsverval)) at time zone 'UTC','YYYY-MM-DD HH24:MI:SS') as hissamnaam_tijdstip_verval,
case when hsn.actieverval is NULL then 'leeg'
else
case when hsn.actieverval=hsn.actieinh then 'gelijk aan actie inhoud'
else 'gevuld'
end 
end as hissamnaam_actie_verval,
case when hsn.actievervaltbvlevmuts is NULL then'leeg'
else 'gevuld'
end as hissamnaam_actie_verval_levering_mutatie,
hsn.indvoorkomentbvlevmuts as hissamnaam_indicatie_voorkomen_levering_mutatie,

-- his_persids
'---------------' as HIS_PERSIDS,
hpi.anr as hispersids_A_nummer,
hpi.bsn as hispersids_burgerservicenummer,
hpi.dataanvgel as hispersids_datum_aanvang_geldigheid,
hpi.dateindegel as hispersids_datum_einde_geldigheid,
hpi.nadereaandverval as hispersids_nadere_aanduiding_verval,
to_char(to_timestamp(extract(epoch from hpi.tsreg)) at time zone 'UTC','YYYY-MM-DD HH24:MI:SS') as hispersids_tijdstip_registratie,
to_char(to_timestamp(extract(epoch from hpi.tsverval)) at time zone 'UTC','YYYY-MM-DD HH24:MI:SS') as hispersids_tijdstip_verval,
case when hpi.actieverval is NULL then 'leeg'
else
case when hpi.actieverval=hpi.actieinh then 'gelijk aan actie inhoud'
else 'gevuld'
end 
end as hispersids_actie_verval,
case when hpi.actievervaltbvlevmuts is NULL then'leeg'
else 'gevuld'
end as hispersids_actie_verval_levering_mutatie,
hpi.indvoorkomentbvlevmuts as hispersids_indicatie_voorkomen_levering_mutatie,

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

-- his_persgeboorte
'---------------' as HIS_PERSGEBOORTE,
case when hgb.datgeboorte = 0 then trim(to_char(hgb.datgeboorte, '99999999'))
when {placeholder}=hgb.datgeboorte then 'gelijk aan placeholder'
else trim(to_char(hgb.datgeboorte, '99999999'))
end 
as hisgeb_datum_geboorte, 

hgb.blregiogeboorte as hisgeb_buitenlandse_regio_geboorte,
hgb.omslocgeboorte as hisgeb_omschrijving_locatie_geboorte,
--hgb.gemgeboorte||' ('||
gem.code||' '||gem.naam--||')' 
as hisgeb_gemeente_geboorte, 
hgb.wplnaamgeboorte as hisgeb_woonplaatsnaam_geboorte, 
hgb.blplaatsgeboorte as hisgeb_buitenlandse_plaats_geboorte, 
--hgb.landgebiedgeboorte||' ('||
lgb.code||' '||lgb.naam--||')' 
as hisgeb_landgebied_geboorte,
to_char(to_timestamp(extract(epoch from hgb.tsreg)) at time zone 'UTC','YYYY-MM-DD HH24:MI:SS') as hisgeb_tijdstip_registratie,
to_char(to_timestamp(extract(epoch from hgb.tsverval)) at time zone 'UTC','YYYY-MM-DD HH24:MI:SS') as hisgeb_tijdstip_verval,
hgb.nadereaandverval as hisgeb_nadere_aanduiding_verval,
case when hgb.actieverval is NULL then 'leeg'
else
case when hgb.actieverval=hgb.actieinh then 'gelijk aan actie inhoud'
else 'gevuld'
end 
end as hisgeb_actie_verval,
case when hgb.actievervaltbvlevmuts is NULL then'leeg'
else 'gevuld'
end as hisgeb_actie_verval_levering_mutatie,
hgb.indvoorkomentbvlevmuts as hisgeb_indicatie_voorkomen_levering_mutatie,

-- his_persgeslachtsaand
'---------------' as HIS_PERSGESLACHTSAAND,
--hga.geslachtsaand||' ('||
gad.code||' '||gad.naam--||')'  
as hisgeslachtsaand_geslachtsaand,
hga.dataanvgel as hisgeslachtsaand_datum_aanvang_geldigheid,
hga.dateindegel as hisgeslachtsaand_datum_einde_geldigheid,
to_char(to_timestamp(extract(epoch from hga.tsreg)) at time zone 'UTC','YYYY-MM-DD HH24:MI:SS') as hisgeslachtsaand_tijdstip_registratie,
to_char(to_timestamp(extract(epoch from hga.tsverval)) at time zone 'UTC','YYYY-MM-DD HH24:MI:SS') as hisgeslachtsaand_tijdstip_verval,
hga.nadereaandverval as hisgeslachtsaand_nadere_aanduiding_verval,
case when hga.actieverval is NULL then 'leeg'
else
case when hga.actieverval=hgb.actieinh then 'gelijk aan actie inhoud'
else 'gevuld'
end 
end as hisgeslachtsaand_actie_verval,
case when hga.actievervaltbvlevmuts is NULL then'leeg'
else 'gevuld'
end as hisgeslachtsaand_actie_verval_levering_mutatie,
hga.indvoorkomentbvlevmuts as hisgeslachtsaand_indicatie_voorkomen_levering_mutatie

from kern.pers p
inner join kern.betr ob on p.id = ob.pers
inner join kern.betr kb on ob.relatie = kb.relatie
inner join kern.pers kind on kb.pers = kind.id
left join kern.his_betr hb on kb.id=hb.betr
left join kern.srtbetr sb on sb.id = kb.rol
left join kern.his_perssamengesteldenaam hsn on hsn.pers = kind.id
left join kern.his_ouderouderschap ho on ob.id = ho.betr
-- and... toegevoegd om lege hgb te vinden indien bij betrokkene(ouder/kind) er initieel geen hgb is; 
left join kern.his_persgeboorte hgb on hgb.pers = kind.id
                                       and hsn.tsreg >= hgb.tsreg
left join kern.his_ouderouderlijkgezag hog on ob.id = hog.betr
-- and... toegevoegd om lege hpi te vinden indien bij betrokkene(ouder/kind) er initieel geen hpi is; 
left join kern.his_persids hpi on hpi.pers = kind.id
                                       and hsn.tsreg >= hpi.tsreg
-- and... toegevoegd om lege hga te vinden indien bij betrokkene(ouder/kind) er initieel geen hga is;
left join kern.his_persgeslachtsaand hga on hga.pers = kind.id
                                       and hsn.tsreg >= hga.tsreg
left join kern.geslachtsaand gad on gad.id = hga.geslachtsaand 
left join kern.landgebied lgb on lgb.id = hgb.landgebiedgeboorte
left join kern.gem gem on gem.id = hgb.gemgeboorte
left join kern.predicaat pdc on pdc.id = hsn.predicaat
left join kern.adellijketitel att on att.id = hsn.adellijketitel

where ob.rol = 2
and kb.rol = 1
and p.srt = 1
--and p.anr = 1401023515  --004-P1
--and p.anr = 1401034603  --203-P1
--and p.anr = 1401043027  --G01-P1
--and p.anr = 1839305202  --G02-P1
--and p.anr = 6167217392 --AF-8001-K1
and p.anr = '{anr}'

--and (((hgb.id IS NULL or hgb.actieinh in (select max(xhgb.actieinh) from kern.his_persgeboorte xhgb 
--                     where xhgb.actieinh <= hsn.actieinh and xhgb.pers = hsn.pers))
--   and (hga.id IS NULL or hga.actieinh in (select max(xhga.actieinh) from kern.his_persgeslachtsaand xhga
--                     where xhga.actieinh <= hsn.actieinh and xhga.pers = hsn.pers)
--     or not exists (select * from kern.his_persgeslachtsaand xhga
--                   where xhga.actieinh <= hsn.actieinh and xhga.pers = hsn.pers))
--   and (hpi.id IS NULL or hpi.actieinh in (select max(xhpi.actieinh) from kern.his_persids xhpi 
--                     where xhpi.actieinh <= hsn.actieinh and xhpi.pers = hsn.pers)
--     or not exists (select * from kern.his_persids xhpi
--                   where xhpi.actieinh <= hsn.actieinh and xhpi.pers = hsn.pers)))
--  or
--     (hsn.actieinh in (select max(xhsn.actieinh) from kern.his_perssamengesteldenaam xhsn 
--                     where xhsn.actieinh <= hgb.actieinh and xhsn.pers = hgb.pers)
--   and (hga.id IS NULL or hga.actieinh in (select max(xhga.actieinh) from kern.his_persgeslachtsaand xhga 
--                     where xhga.actieinh <= hgb.actieinh and xhga.pers = hgb.pers)
--     or not exists (select * from kern.his_persgeslachtsaand xhga
--                   where xhga.actieinh <= hgb.actieinh and xhga.pers = hgb.pers))
--   and (hpi.id IS NULL or hpi.actieinh in (select max(xhpi.actieinh) from kern.his_persids xhpi 
--                     where xhpi.actieinh <= hgb.actieinh and xhpi.pers = hgb.pers)
--     or not exists (select * from kern.his_persids xhpi
--                   where xhpi.actieinh <= hgb.actieinh and xhpi.pers = hgb.pers)))
--  or
--     (hsn.actieinh in (select max(xhsn.actieinh) from kern.his_perssamengesteldenaam xhsn 
--                     where xhsn.actieinh <= hga.actieinh and xhsn.pers = hga.pers)
--  and (hgb.id IS NULL or hgb.actieinh in (select max(xhgb.actieinh) from kern.his_persgeboorte xhgb
--                     where xhgb.actieinh <= hga.actieinh and xhgb.pers = hga.pers))
--   and (hpi.id IS NULL or hpi.actieinh in (select max(xhpi.actieinh) from kern.his_persids xhpi 
--                     where xhpi.actieinh <= hga.actieinh and xhpi.pers = hga.pers)
--     or not exists (select * from kern.his_persids xhpi
--                   where xhpi.actieinh <= hga.actieinh and xhpi.pers = hga.pers)))
--  or
--     (hsn.actieinh in (select max(xhsn.actieinh) from kern.his_perssamengesteldenaam xhsn 
--                     where xhsn.actieinh <= hpi.actieinh and xhsn.pers = hpi.pers)
--  and (hga.id IS NULL or hga.actieinh in (select max(xhga.actieinh) from kern.his_persgeslachtsaand xhga
--                     where xhga.actieinh <= hpi.actieinh and xhga.pers = hpi.pers)
--     or not exists (select * from kern.his_persgeslachtsaand xhga
--                   where xhga.actieinh <= hpi.actieinh and xhga.pers = hpi.pers))
--   and (hgb.id IS NULL or hgb.actieinh in (select max(xhgb.actieinh) from kern.his_persids xhgb 
--                     where xhgb.actieinh <= hpi.actieinh and xhgb.pers = hpi.pers))))

and (kind.id IS NULL or (((hgb.id IS NULL or hgb.actieinh in (select max(xhgb.actieinh) from kern.his_persgeboorte xhgb 
                    where xhgb.actieinh <= hsn.actieinh and xhgb.pers = hsn.pers))
    and (hga.id IS NULL or hga.actieinh in (select max(xhga.actieinh) from kern.his_persgeslachtsaand xhga
                    where xhga.actieinh <= hsn.actieinh and xhga.pers = hsn.pers))
    and ho.actieinh in (select max(xho.actieinh) from kern.his_ouderouderschap xho, kern.betr xkb, kern.betr xob 
                    where xho.actieinh <= hsn.actieinh and xho.betr = xob.id and xob.pers = p.id and xob.relatie = xkb.relatie and xkb.pers = hsn.pers)
    and (hog.actieinh in (select max(xhog.actieinh) from kern.his_ouderouderlijkgezag xhog, kern.betr xkb, kern.betr xob 
                    where xhog.actieinh <= hsn.actieinh and xhog.betr = xob.id and xob.pers = p.id and xob.relatie = xkb.relatie and xkb.pers = hsn.pers)
                    or
                    not exists (select * from kern.his_ouderouderlijkgezag xhog, kern.betr xkb, kern.betr xob 
                    where xhog.actieinh <= hsn.actieinh and xhog.betr = xob.id and xob.pers = p.id and xob.relatie = xkb.relatie and xkb.pers = hsn.pers))
    and (hpi.id IS NULL or hpi.actieinh in (select max(xhpi.actieinh) from kern.his_persids xhpi
                    where xhpi.actieinh <= hsn.actieinh and xhpi.pers = hsn.pers)
                    or
                    not exists (select * from kern.his_persids xhpi
                    where xhpi.actieinh <= hsn.actieinh and xhpi.pers = hsn.pers)))
 or 
   ((hgb.id IS NULL or hgb.actieinh in (select max(xhgb.actieinh) from kern.his_persgeboorte xhgb, kern.betr xkb, kern.betr xob 
                     where xhgb.actieinh <= ho.actieinh and xhgb.pers = xkb.pers and xkb.relatie = xob.relatie and xob.id = ho.betr))
    and hsn.actieinh in (select max(xhsn.actieinh) from kern.his_perssamengesteldenaam xhsn, kern.betr xkb, kern.betr xob 
                    where xhsn.actieinh <= ho.actieinh and xhsn.pers = xkb.pers and xkb.relatie = xob.relatie and xob.id = ho.betr)
    and (hga.id IS NULL or hga.actieinh in (select max(xhga.actieinh) from kern.his_persgeslachtsaand xhga, kern.betr xkb, kern.betr xob 
                    where xhga.actieinh <= ho.actieinh and xhga.pers = xkb.pers and xkb.id = xob.relatie and xob.id = ho.betr))
   and (hog.actieinh in (select max(xhog.actieinh) from kern.his_ouderouderlijkgezag xhog 
                    where xhog.actieinh <= ho.actieinh and xhog.betr = ho.betr)
                    or
                    not exists (select * from kern.his_ouderouderlijkgezag xhog 
                    where xhog.actieinh <= ho.actieinh and xhog.betr = ho.betr))
    and (hpi.id IS NULL or hpi.actieinh in (select max(xhpi.actieinh) from kern.his_persids xhpi, kern.betr xkb, kern.betr xob 
                     where xhpi.actieinh <= ho.actieinh and xhpi.pers = xkb.pers and xkb.relatie = xob.relatie and xob.id = ho.betr)
                     or
                     not exists (select * from kern.his_persids xhpi, kern.betr xkb, kern.betr xob 
                    where xhpi.actieinh <= ho.actieinh and xhpi.pers = xkb.pers and xkb.relatie = xob.relatie and xob.id = ho.betr)))
   or 
      (hog.actieinh IS NOT NULL and (((hgb.id IS NULL or hgb.actieinh in (select max(xhgb.actieinh) from kern.his_persgeboorte xhgb, kern.betr xkb, kern.betr xob 
                      where xhgb.actieinh <= hog.actieinh and xhgb.pers = xkb.pers and xkb.relatie = xob.relatie and xob.id = hog.betr)
                      or not exists (select * from kern.his_persgeboorte xhgb, kern.betr xkb, kern.betr xob 
                      where xhgb.actieinh <= hog.actieinh and xhgb.pers = xkb.pers and xkb.relatie = xob.relatie and xob.id = hog.betr))
     and (hsn.actieinh in (select max(xhsn.actieinh) from kern.his_perssamengesteldenaam xhsn, kern.betr xkb, kern.betr xob 
                     where xhsn.actieinh <= hog.actieinh and xhsn.pers = xkb.pers and xkb.relatie = xob.relatie and xob.id = hog.betr) 
                     or not exists (select * from kern.his_perssamengesteldenaam xhsn, kern.betr xkb, kern.betr xob 
                     where xhsn.actieinh <= hog.actieinh and xhsn.pers = xkb.pers and xkb.relatie = xob.relatie and xob.id = hog.betr ))
     and (hga.id IS NULL or hga.actieinh in (select max(xhga.actieinh) from kern.his_persgeslachtsaand xhga, kern.betr xkb, kern.betr xob 
                     where xhga.actieinh <= hog.actieinh and xhga.pers = xkb.pers and xkb.relatie = xob.relatie and xob.id = hog.betr) 
                     or not exists(select * from kern.his_persgeslachtsaand xhga, kern.betr xkb, kern.betr xob 
                     where xhga.actieinh <= hog.actieinh and xhga.pers = xkb.pers and xkb.relatie = xob.relatie and xob.id = hog.betr))
    and (ho.actieinh in (select max(xho.actieinh) from kern.his_ouderouderschap xho 
                     where xho.actieinh <= hog.actieinh and xho.betr = hog.betr) 
                     or not exists(select * from kern.his_ouderouderschap xho where xho.actieinh <= hog.actieinh and xho.betr = hog.betr)))
   and (hpi.id IS NULL or hpi.actieinh in (select max(xhpi.actieinh) from kern.his_persids xhpi, kern.betr xkb, kern.betr xob 
                     where xhpi.actieinh <= hog.actieinh and xhpi.pers = xkb.pers and xkb.relatie = xob.relatie and xob.id = hog.betr)
                     or
                     not exists (select * from kern.his_persids xhpi, kern.betr xkb, kern.betr xob 
                     where xhpi.actieinh <= hog.actieinh and xhpi.pers = xkb.pers and xkb.relatie = xob.relatie and xob.id = hog.betr))))
  or
    (hsn.actieinh in (select max(xhsn.actieinh) from kern.his_perssamengesteldenaam xhsn 
                      where xhsn.actieinh <= hga.actieinh and xhsn.pers = hga.pers)
     and (hgb.id IS NULL or hgb.actieinh in (select max(xhgb.actieinh) from kern.his_persgeboorte xhgb 
                      where xhgb.actieinh <= hga.actieinh and xhgb.pers = hga.pers))
     and ho.actieinh in (select max(xho.actieinh) from kern.his_ouderouderschap xho, kern.betr xkb, kern.betr xob 
                      where xho.actieinh <= hga.actieinh and xho.betr = xob.id and xob.relatie = xkb.relatie and xkb.pers = hga.pers)
    and (hog.actieinh in (select max(xhog.actieinh) from kern.his_ouderouderlijkgezag xhog, kern.betr xkb, kern.betr xob 
                      where xhog.actieinh <= hga.actieinh and xhog.betr = xob.id and xob.relatie = xkb.relatie and xkb.pers = hga.pers)
                      or
                      not exists (select * from kern.his_ouderouderlijkgezag xhog, kern.betr xkb, kern.betr xob 
                      where xhog.actieinh <= hga.actieinh and xhog.betr = xob.id and xob.relatie = xkb.relatie and xkb.pers = hga.pers))   
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
    and ho.actieinh in (select max(xho.actieinh) from kern.his_ouderouderschap xho, kern.betr xkb, kern.betr xob 
                     where xho.actieinh <= hgb.actieinh and xho.betr = xob.id and xob.relatie = xkb.relatie and xkb.pers = hgb.pers)
   and (hog.actieinh in (select max(xhog.actieinh) from kern.his_ouderouderlijkgezag xhog, kern.betr xkb, kern.betr xob 
                     where xhog.actieinh <= hgb.actieinh and xhog.betr = xob.id and xob.relatie = xkb.relatie and xkb.pers = hgb.pers)
                     or
                     not exists (select * from kern.his_ouderouderlijkgezag xhog, kern.betr xkb, kern.betr xob 
                     where xhog.actieinh <= hgb.actieinh and xhog.betr = xob.id and xob.relatie = xkb.relatie and xkb.pers = hgb.pers))   
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
    and ho.actieinh in (select max(xho.actieinh) from kern.his_ouderouderschap xho, kern.betr xkb, kern.betr xob 
                     where xho.actieinh <= hpi.actieinh and xho.betr = xob.id and xob.relatie = xkb.relatie and xkb.id = hpi.pers)
   and (hog.actieinh in (select max(xhog.actieinh) from kern.his_ouderouderlijkgezag xhog, kern.betr xkb, kern.betr xob 
                     where xhog.actieinh <= hpi.actieinh and xhog.betr = xob.id and xob.relatie = xkb.relatie and xkb.pers = hpi.pers)
                     or
                     not exists (select * from kern.his_ouderouderlijkgezag xhog, kern.betr xkb, kern.betr xob 
                     where xhog.actieinh <= hpi.actieinh and xhog.betr = xob.id and xob.relatie = xkb.relatie and xkb.pers = hpi.pers))   
 and (hgb.id IS NULL or hgb.actieinh in (select max(xhgb.actieinh) from kern.his_persids xhgb
                     where xhgb.actieinh <= hpi.actieinh and xhgb.pers = hpi.pers)
                     or
                     not exists (select * from kern.his_persids xhgb
                      where xhgb.actieinh <= hpi.actieinh and xhgb.pers = hpi.pers))))
)
union all

select 
sb.naam as soort_betrokkene, 
ouder.bsn as pers_bsn_nr, 
ouder.anr as pers_anr,

case when ouder.datgeboorte = 0 then trim(to_char(ouder.datgeboorte, '99999999'))
when {placeholder}=ouder.datgeboorte then 'gelijk aan placeholder'
else trim(to_char(ouder.datgeboorte, '99999999'))
end 
as pers_geboorte_datum, 

ouder.voornamen as pers_voornamen, 
ouder.voorvoegsel as pers_voorvoegsel, 
ouder.scheidingsteken as pers_scheidingsteken, 
ouder.geslnaamstam as pers_geslachtsnaam_stam, 
ouder.geslachtsaand as pers_geslachts_aanduiding, 
ouder.srt as pers_soort_persoon,

-- his_ouderouderschap
'---------------' as HIS_OUDEROUDERSCHAP,
ho.indouderuitwiekindisgeboren as hisouderschap_ind_uit_wie_kind_is_geboren,
ho.dataanvgel as hisouderschap_datum_aanvang, 
ho.dateindegel as hisouderschap_datum_einde,
ho.nadereaandverval as nadere_aanduiding_verval_ouderschap,
to_char(to_timestamp(extract(epoch from ho.tsreg)) at time zone 'UTC','YYYY-MM-DD HH24:MI:SS') as hisouderschap_tijdstip_registratie,
to_char(to_timestamp(extract(epoch from ho.tsverval)) at time zone 'UTC','YYYY-MM-DD HH24:MI:SS') as hisouderschap_tijdstip_verval,
case when ho.actieverval is NULL then 'leeg'
else
case when ho.actieverval=ho.actieinh then 'gelijk aan actie inhoud'
else 'gevuld'
end 
end as hisouderschap_actie_verval,
case when ho.actievervaltbvlevmuts is NULL then'leeg'
else 'gevuld'
end as hisouderschap_actie_verval_levering_mutatie,
ho.indvoorkomentbvlevmuts as hisouderschap_indicatie_voorkomen_levering_mutatie,

-- his_ouderouderlijkgezag
'---------------' as HIS_OUDEROUDERLIJKGEZAG,
hog.indouderheeftgezag as hisouderlijkgezag_ind_ouder_heeft_gezag,
hog.dataanvgel as hisouderlijkgezag_datum_aanvang, 
hog.dateindegel as hisouderlijkgezag_datum_einde,
hog.nadereaandverval as nadere_aanduiding_verval_ouderlijkgezag,
to_char(to_timestamp(extract(epoch from hog.tsreg)) at time zone 'UTC','YYYY-MM-DD HH24:MI:SS') as hisouderlijkgezag_tijdstip_registratie,
to_char(to_timestamp(extract(epoch from hog.tsverval)) at time zone 'UTC','YYYY-MM-DD HH24:MI:SS') as hisouderlijkgezag_tijdstip_verval,
case when hog.actieverval is NULL then 'leeg'
else
case when hog.actieverval=hog.actieinh then 'gelijk aan actie inhoud'
else 'gevuld'
end 
end as hisouderlijkgezag_actie_verval,
case when hog.actievervaltbvlevmuts is NULL then'leeg'
else 'gevuld'
end as hisouderlijkgezag_actie_verval_levering_mutatie,
hog.indvoorkomentbvlevmuts as hisouderlijkgezag_indicatie_voorkomen_levering_mutatie,

-- his_perssamengesteldenaam
'---------------' as HIS_PERSSAMENGESTELDENAAM,
hsn.voornamen as hissamnaam_voornamen,
case when hga.geslachtsaand = 1 then --hsn.adellijketitel||' ('||
att.code||' '||att.naammannelijk--||')' 
     else case when hga.geslachtsaand = 2 then --hsn.adellijketitel||' ('||
att.code||' '||att.naamvrouwelijk--||')'
               else --hsn.adellijketitel||' ('||
att.code||' Onbekend'
          end
end 
as hissamnaam_adellijketitel,  
case when hga.geslachtsaand = 1 then --hsn.predicaat||' ('||
pdc.code||' '||pdc.naammannelijk--||')' 
     else case when hga.geslachtsaand = 2 then --hsn.predicaat||' ('||
pdc.code||' '||pdc.naamvrouwelijk--||')'
               else --hsn.predicaat||' ('||
pdc.code||' Onbekend'
          end
end 
as hissamnaam_predicaat, 
hsn.voorvoegsel as hissamnaam_voorvoegsel,
hsn.scheidingsteken as hissamnaam_scheidingsteken,
hsn.geslnaamstam as hissamnaam_geslachtsnaamstam,
hsn.dataanvgel as hissamnaam_datum_aanvang_geldigheid,
hsn.dateindegel as hissamnaam_datum_einde_geldigheid,
hsn.nadereaandverval as hissamnaam_nadere_aanduiding_verval,
to_char(to_timestamp(extract(epoch from hsn.tsreg)) at time zone 'UTC','YYYY-MM-DD HH24:MI:SS') as hissamnaam_tijdstip_registratie,
to_char(to_timestamp(extract(epoch from hsn.tsverval)) at time zone 'UTC','YYYY-MM-DD HH24:MI:SS') as hissamnaam_tijdstip_verval,
case when hsn.actieverval is NULL then 'leeg'
else
case when hsn.actieverval=hsn.actieinh then 'gelijk aan actie inhoud'
else 'gevuld'
end 
end as hissamnaam_actie_verval,
case when hsn.actievervaltbvlevmuts is NULL then'leeg'
else 'gevuld'
end as hissamnaam_actie_verval_levering_mutatie,
hsn.indvoorkomentbvlevmuts as hissamnaam_indicatie_voorkomen_levering_mutatie,

-- his_persids
'---------------' as HIS_PERSIDS,
hpi.anr as hispersids_A_nummer,
hpi.bsn as hispersids_burgerservicenummer,
hpi.dataanvgel as hispersids_datum_aanvang_geldigheid,
hpi.dateindegel as hispersids_datum_einde_geldigheid,
hpi.nadereaandverval as hispersids_nadere_aanduiding_verval,
to_char(to_timestamp(extract(epoch from hpi.tsreg)) at time zone 'UTC','YYYY-MM-DD HH24:MI:SS') as hispersids_tijdstip_registratie,
to_char(to_timestamp(extract(epoch from hpi.tsverval)) at time zone 'UTC','YYYY-MM-DD HH24:MI:SS') as hispersids_tijdstip_verval,
case when hpi.actieverval is NULL then 'leeg'
else
case when hpi.actieverval=hpi.actieinh then 'gelijk aan actie inhoud'
else 'gevuld'
end 
end as hispersids_actie_verval,
case when hpi.actievervaltbvlevmuts is NULL then'leeg'
else 'gevuld'
end as hispersids_actie_verval_levering_mutatie,
hpi.indvoorkomentbvlevmuts as hispersids_indicatie_voorkomen_levering_mutatie,

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

-- his_persgeboorte
'---------------' as HIS_PERSGEBOORTE,

case when hgb.datgeboorte = 0 then trim(to_char(hgb.datgeboorte, '99999999'))
when {placeholder}=hgb.datgeboorte then 'gelijk aan placeholder'
else trim(to_char(hgb.datgeboorte, '99999999'))
end 
as hisgeb_datum_geboorte,  

hgb.blregiogeboorte as hisgeb_buitenlandse_regio_geboorte,
hgb.omslocgeboorte as hisgeb_omschrijving_locatie_geboorte,
--hgb.gemgeboorte||' ('||
gem.code||' '||gem.naam--||')' 
as hisgeb_gemeente_geboorte, 
hgb.wplnaamgeboorte as hisgeb_woonplaatsnaam_geboorte, 
hgb.blplaatsgeboorte as hisgeb_buitenlandse_plaats_geboorte, 
--hgb.landgebiedgeboorte||' ('||
lgb.code||' '||lgb.naam--||')' 
as hisgeb_landgebied_geboorte,
to_char(to_timestamp(extract(epoch from hgb.tsreg)) at time zone 'UTC','YYYY-MM-DD HH24:MI:SS') as hisgeb_tijdstip_registratie,
to_char(to_timestamp(extract(epoch from hgb.tsverval)) at time zone 'UTC','YYYY-MM-DD HH24:MI:SS') as hisgeb_tijdstip_verval,
hgb.nadereaandverval as hisgeb_nadere_aanduiding_verval,
case when hgb.actieverval is NULL then 'leeg'
else
case when hgb.actieverval=hgb.actieinh then 'gelijk aan actie inhoud'
else 'gevuld'
end 
end as hisgeb_actie_verval,
case when hgb.actievervaltbvlevmuts is NULL then'leeg'
else 'gevuld'
end as hisgeb_actie_verval_levering_mutatie,
hgb.indvoorkomentbvlevmuts as hisgeb_indicatie_voorkomen_levering_mutatie,

-- -- his_persgeslachtsaand
'---------------' as HIS_PERSGESLACHTSAAND,
--hga.geslachtsaand||' ('||
gad.code||' '||gad.naam--||')'  
as hisgeslachtsaand_geslachtsaand,
hga.dataanvgel as hisgeslachtsaand_datum_aanvang_geldigheid,
hga.dateindegel as hisgeslachtsaand_datum_einde_geldigheid,
to_char(to_timestamp(extract(epoch from hga.tsreg)) at time zone 'UTC','YYYY-MM-DD HH24:MI:SS') as hisgeslachtsaand_tijdstip_registratie,
to_char(to_timestamp(extract(epoch from hga.tsverval)) at time zone 'UTC','YYYY-MM-DD HH24:MI:SS') as hisgeslachtsaand_tijdstip_verval,
hga.nadereaandverval as hisgeslachtsaand_nadere_aanduiding_verval,
case when hga.actieverval is NULL then 'leeg'
else
case when hga.actieverval=hgb.actieinh then 'gelijk aan actie inhoud'
else 'gevuld'
end 
end as hisgeslachtsaand_actie_verval,
case when hga.actievervaltbvlevmuts is NULL then'leeg'
else 'gevuld'
end as hisgeslachtsaand_actie_verval_levering_mutatie,
hga.indvoorkomentbvlevmuts as hisgeslachtsaand_indicatie_voorkomen_levering_mutatie

from kern.pers p
inner join kern.betr kb on p.id = kb.pers
inner join kern.betr ob on ob.relatie = kb.relatie
left join kern.pers ouder on ob.pers = ouder.id
left join kern.his_betr hb on ob.id=hb.betr
left join kern.srtbetr sb on sb.id = ob.rol
left join kern.his_perssamengesteldenaam hsn on hsn.pers = ouder.id
left join kern.his_ouderouderschap ho on ob.id = ho.betr
-- and... toegevoegd om lege hgb te vinden indien bij betrokkene(ouder/kind) er initieel geen hgb is;
left join kern.his_persgeboorte hgb on hgb.pers = ouder.id
                                       and hsn.tsreg >= hgb.tsreg
left join kern.his_ouderouderlijkgezag hog on ob.id = hog.betr
-- and... toegevoegd om lege hpi te vinden indien bij betrokkene(ouder/kind) er initieel geen hpi is;
left join kern.his_persids hpi on hpi.pers = ouder.id
                                       and hsn.tsreg >= hpi.tsreg
-- and... toegevoegd om lege hga te vinden indien bij betrokkene(ouder/kind) er initieel geen hga is;
left join kern.his_persgeslachtsaand hga on hga.pers = ouder.id
                                       and hsn.tsreg >= hga.tsreg
left join kern.geslachtsaand gad on gad.id = hga.geslachtsaand 
left join kern.landgebied lgb on lgb.id = hgb.landgebiedgeboorte
left join kern.gem gem on gem.id = hgb.gemgeboorte
left join kern.predicaat pdc on pdc.id = hsn.predicaat
left join kern.adellijketitel att on att.id = hsn.adellijketitel
where ob.rol = 2
and kb.rol = 1
and p.srt = 1
--and p.anr = 1401023515  --004-P1
--and p.anr = 1401034603  --203-P1
--and p.anr = 1401043027  --G01-P1
--and p.anr = 1839305202  --G02-P1
--and p.anr = 6167217392 --AF-8001
and p.anr = '{anr}'

and (ouder.id IS NULL or ((hgb.id IS NULL or hgb.actieinh in (select max(xhgb.actieinh) from kern.his_persgeboorte xhgb 
                    where xhgb.actieinh <= hsn.actieinh and xhgb.pers = hsn.pers))
    and (hga.id IS NULL or hga.actieinh in (select max(xhga.actieinh) from kern.his_persgeslachtsaand xhga
                    where xhga.actieinh <= hsn.actieinh and xhga.pers = hsn.pers))
    and ho.actieinh in (select max(xho.actieinh) from kern.his_ouderouderschap xho, kern.pers xouder, kern.betr xob 
                    where xho.actieinh <= hsn.actieinh and xho.betr = xob.id and xob.pers = xouder.id and xouder.id = hsn.pers)
    and (hog.actieinh in (select max(xhog.actieinh) from kern.his_ouderouderlijkgezag xhog, kern.pers xouder, kern.betr xob 
                    where xhog.actieinh <= hsn.actieinh and xhog.betr = xob.id and xob.pers = xouder.id and xouder.id = hsn.pers)
                    or
                    not exists (select * from kern.his_ouderouderlijkgezag xhog, kern.pers xouder, kern.betr xob 
                    where xhog.actieinh <= hsn.actieinh and xhog.betr = xob.id and xob.pers = xouder.id and xouder.id = hsn.pers))
    and (hpi.id IS NULL or hpi.actieinh in (select max(xhpi.actieinh) from kern.his_persids xhpi
                    where xhpi.actieinh <= hsn.actieinh and xhpi.pers = hsn.pers)
                    or
                    not exists (select * from kern.his_persids xhpi
                    where xhpi.actieinh <= hsn.actieinh and xhpi.pers = hsn.pers))
 or 
   ((hgb.id IS NULL or hgb.actieinh in (select max(xhgb.actieinh) from kern.his_persgeboorte xhgb, kern.pers xouder, kern.betr xob 
                     where xhgb.actieinh <= ho.actieinh and xhgb.pers = xouder.id and xouder.id = xob.pers and xob.id = ho.betr))
    and hsn.actieinh in (select max(xhsn.actieinh) from kern.his_perssamengesteldenaam xhsn, kern.pers xouder, kern.betr xob 
                    where xhsn.actieinh <= ho.actieinh and xhsn.pers = xouder.id and xouder.id = xob.pers and xob.id = ho.betr)
    and (hga.id IS NULL or hga.actieinh in (select max(xhga.actieinh) from kern.his_persgeslachtsaand xhga, kern.pers xouder, kern.betr xob 
                    where xhga.actieinh <= ho.actieinh and xhga.pers = xouder.id and xouder.id = xob.pers and xob.id = ho.betr))
   and (hog.actieinh in (select max(xhog.actieinh) from kern.his_ouderouderlijkgezag xhog 
                    where xhog.actieinh <= ho.actieinh and xhog.betr = ho.betr)
                    or
                    not exists (select * from kern.his_ouderouderlijkgezag xhog 
                    where xhog.actieinh <= ho.actieinh and xhog.betr = ho.betr))
    and (hpi.id IS NULL or hpi.actieinh in (select max(xhpi.actieinh) from kern.his_persids xhpi, kern.pers xouder, kern.betr xob 
                     where xhpi.actieinh <= ho.actieinh and xhpi.pers = xouder.id and xouder.id = xob.pers and xob.id = ho.betr)
                     or
                     not exists (select * from kern.his_persids xhpi, kern.pers xouder, kern.betr xob 
                    where xhpi.actieinh <= ho.actieinh and xhpi.pers = xouder.id and xouder.id = xob.pers and xob.id = ho.betr)))
   or 
     (hog.actieinh IS NOT NULL and  (((hgb.id IS NULL or hgb.actieinh in (select max(xhgb.actieinh) from kern.his_persgeboorte xhgb, kern.pers xouder, kern.betr xob 
                      where xhgb.actieinh <= hog.actieinh and xhgb.pers = xouder.id and xouder.id = xob.pers and xob.id = hog.betr)
                      or not exists (select * from kern.his_persgeboorte xhgb, kern.pers xouder, kern.betr xob 
                      where xhgb.actieinh <= hog.actieinh and xhgb.pers = xouder.id and xouder.id = xob.pers and xob.id = hog.betr))
     and (hsn.actieinh in (select max(xhsn.actieinh) from kern.his_perssamengesteldenaam xhsn, kern.pers xouder, kern.betr xob 
                     where xhsn.actieinh <= hog.actieinh and xhsn.pers = xouder.id and xouder.id = xob.pers and xob.id = hog.betr) 
                     or not exists (select * from kern.his_perssamengesteldenaam xhsn, kern.pers xouder, kern.betr xob 
                     where xhsn.actieinh <= hog.actieinh and xhsn.pers = xouder.id and xouder.id = xob.pers and xob.id = hog.betr ))
     and (hga.id IS NULL or hga.actieinh in (select max(xhga.actieinh) from kern.his_persgeslachtsaand xhga, kern.pers xouder, kern.betr xob 
                     where xhga.actieinh <= hog.actieinh and xhga.pers = xouder.id and xouder.id = xob.pers and xob.id = hog.betr) 
                     or not exists(select * from kern.his_persgeslachtsaand xhga, kern.pers xouder, kern.betr xob 
                     where xhga.actieinh <= hog.actieinh and xhga.pers = xouder.id and xouder.id = xob.pers and xob.id = hog.betr))
    and (ho.actieinh in (select max(xho.actieinh) from kern.his_ouderouderschap xho 
                     where xho.actieinh <= hog.actieinh and xho.betr = hog.betr) 
                     or not exists(select * from kern.his_ouderouderschap xho where xho.actieinh <= hog.actieinh and xho.betr = hog.betr)))
   and (hpi.id IS NULL or hpi.actieinh in (select max(xhpi.actieinh) from kern.his_persids xhpi, kern.pers xouder, kern.betr xob 
                     where xhpi.actieinh <= hog.actieinh and xhpi.pers = xouder.id and xouder.id = xob.pers and xob.id = hog.betr)
                     or
                     not exists (select * from kern.his_persids xhpi, kern.pers xouder, kern.betr xob 
                     where xhpi.actieinh <= hog.actieinh and xhpi.pers = xouder.id and xouder.id = xob.pers and xob.id = hog.betr))))
  or
    (hsn.actieinh in (select max(xhsn.actieinh) from kern.his_perssamengesteldenaam xhsn 
                      where xhsn.actieinh <= hga.actieinh and xhsn.pers = hga.pers)
     and (hgb.id IS NULL or hgb.actieinh in (select max(xhgb.actieinh) from kern.his_persgeboorte xhgb 
                      where xhgb.actieinh <= hga.actieinh and xhgb.pers = hga.pers))
     and ho.actieinh in (select max(xho.actieinh) from kern.his_ouderouderschap xho, kern.pers xouder, kern.betr xob 
                      where xho.actieinh <= hga.actieinh and xho.betr = xob.id and xob.pers = xouder.id and xouder.id = hga.pers)
    and (hog.actieinh in (select max(xhog.actieinh) from kern.his_ouderouderlijkgezag xhog, kern.pers xouder, kern.betr xob 
                      where xhog.actieinh <= hga.actieinh and xhog.betr = xob.id and xob.pers = xouder.id and xouder.id = hga.pers)
                      or
                      not exists (select * from kern.his_ouderouderlijkgezag xhog, kern.pers xouder, kern.betr xob 
                      where xhog.actieinh <= hga.actieinh and xhog.betr = xob.id and xob.pers = xouder.id and xouder.id = hga.pers))   
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
    and ho.actieinh in (select max(xho.actieinh) from kern.his_ouderouderschap xho, kern.pers xouder, kern.betr xob 
                     where xho.actieinh <= hgb.actieinh and xho.betr = xob.id and xob.pers = xouder.id and xouder.id = hgb.pers)
   and (hog.actieinh in (select max(xhog.actieinh) from kern.his_ouderouderlijkgezag xhog, kern.pers xouder, kern.betr xob 
                     where xhog.actieinh <= hgb.actieinh and xhog.betr = xob.id and xob.pers = xouder.id and xouder.id = hgb.pers)
                     or
                     not exists (select * from kern.his_ouderouderlijkgezag xhog, kern.pers xouder, kern.betr xob 
                     where xhog.actieinh <= hgb.actieinh and xhog.betr = xob.id and xob.pers = xouder.id and xouder.id = hgb.pers))   
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
    and ho.actieinh in (select max(xho.actieinh) from kern.his_ouderouderschap xho, kern.pers xouder, kern.betr xob 
                     where xho.actieinh <= hpi.actieinh and xho.betr = xob.id and xob.pers = xouder.id and xouder.id = hpi.pers)
   and (hog.actieinh in (select max(xhog.actieinh) from kern.his_ouderouderlijkgezag xhog, kern.pers xouder, kern.betr xob 
                     where xhog.actieinh <= hpi.actieinh and xhog.betr = xob.id and xob.pers = xouder.id and xouder.id = hpi.pers)
                     or
                     not exists (select * from kern.his_ouderouderlijkgezag xhog, kern.pers xouder, kern.betr xob 
                     where xhog.actieinh <= hpi.actieinh and xhog.betr = xob.id and xob.pers = xouder.id and xouder.id = hpi.pers))   
 and (hgb.id IS NULL or hgb.actieinh in (select max(xhgb.actieinh) from kern.his_persids xhgb
                     where xhgb.actieinh <= hpi.actieinh and xhgb.pers = hpi.pers)
                     or
                     not exists (select * from kern.his_persids xhgb
                      where xhgb.actieinh <= hpi.actieinh and xhgb.pers = hpi.pers))))
)
                 

order by soort_betrokkene desc, pers_geboorte_datum asc, hisouderschap_tijdstip_registratie, hisouderlijkgezag_tijdstip_registratie, hissamnaam_tijdstip_registratie, hispersids_tijdstip_registratie, hisbetr_tijdstip_registratie, hisgeb_tijdstip_registratie asc, hisgeslachtsaand_tijdstip_registratie asc, hisouderschap_tijdstip_verval asc, hisouderlijkgezag_tijdstip_verval asc, hissamnaam_tijdstip_verval asc, hispersids_tijdstip_verval asc, hisbetr_tijdstip_verval asc, hisgeb_tijdstip_verval asc, hisgeslachtsaand_tijdstip_verval asc, hisouderschap_datum_einde asc, hisouderlijkgezag_datum_einde asc, hissamnaam_datum_einde_geldigheid asc, hispersids_datum_einde_geldigheid asc, hisgeslachtsaand_datum_einde_geldigheid asc, pers_bsn_nr, pers_voornamen, pers_anr, hissamnaam_voornamen, hisouderlijkgezag_datum_aanvang, hisouderlijkgezag_datum_einde
, pers_anr asc, pers_geslachts_aanduiding asc;