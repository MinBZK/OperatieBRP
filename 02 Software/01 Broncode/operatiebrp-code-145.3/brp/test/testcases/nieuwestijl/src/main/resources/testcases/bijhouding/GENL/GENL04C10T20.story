Meta:
@status                 Klaar
@sleutelwoorden         Geslaagd
@usecase                UCS-BY.HG

Narrative: GBA beeindiging Geregistreerd Partnerschap in Nederland

Scenario: Verwerking Ingeschrevene beindigt relatie met Ingeschrevene
          LT: GENL04C10T20

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL-GENL/GENL04C10T20-April.xls
Given enkel initiele vulling uit bestand /LO3PL-GENL/GENL04C10T20-May.xls

When voer een bijhouding uit GENL04C10T20a.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/GENL/expected/GENL04C10T20a.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 265424665 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP

Given pas laatste relatie van soort 2 aan tussen persoon 265424665 en persoon 563546505 met relatie id 4000075 en betrokkenheid id 4000076

When voer een GBA bijhouding uit GENL04C10T20b.xml namens partij 'Migratievoorziening'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/GENL/expected/GENL04C10T20b.xml voor expressie //brp:isc_migRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 265424665 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
Then is in de database de persoon met bsn 563546505 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP

Then lees persoon met anummer 6259267873 uit database en vergelijk met expected GENL04C10T20-persoon1.xml
Then lees persoon met anummer 7973205281 uit database en vergelijk met expected GENL04C10T20-persoon2.xml

!-- Controleer de actuele relatie(s)
Then in kern heeft select sainh.naam as actieInhoud, saav.naam as actieVerval, hr.dataanv, hr.gemaanv, hr.wplnaamaanv, hr.dateinde,
                   hr.gemeinde, hr.wplnaameinde, sr.naam as soortRelatie, hr.blplaatseinde, hr.blregioeinde, hr.omsloceinde,
                   hr.landgebiedeinde
                   from kern.his_relatie hr
                   join kern.actie ainh on ainh.id = hr.actieinh
                   left join kern.actie av on av.id = hr.actieverval
                   left join kern.relatie r on r.id = hr.relatie
                   left join kern.srtactie sainh on ainh.srt = sainh.id
                   left join kern.srtactie saav on av.srt = saav.id
                   left join kern.srtrelatie sr on r.srt = sr.id
                   where sainh.naam ='Registratie einde geregistreerd partnerschap'
                   and hr.tsreg is not null
                   order by actieinhoud, dataanv de volgende gegevens:
| veld                      | waarde                                        |
| actieinhoud               | Registratie einde geregistreerd partnerschap  |
| actieverval               | NULL                                          |
| dataanv                   | 20160510                                      |
| gemaanv                   | 7012                                          |
| wplnaamaanv               | NULL                                          |
| dateinde                  | 20160901                                      |
| gemeinde                  | 7012                                          |
| wplnaameinde              | NULL                                          |
| soortrelatie              | Geregistreerd partnerschap                    |
| blplaatseinde             | NULL                                          |
| blregioeinde              | NULL                                          |
| omsloceinde               | NULL                                          |
| landgebiedeinde           | 229                                           |

!-- Controleer de vervallen relatie
Then in kern heeft select sainh.naam as actieInhoud, saav.naam as actieVerval, hr.dataanv, hr.gemaanv, hr.dateinde, hr.gemeinde, sr.naam as soortRelatie
                   from kern.his_relatie hr
                   join kern.actie ainh on ainh.id = hr.actieinh
                   left join kern.actie av on av.id = hr.actieverval
                   left join kern.relatie r on r.id = hr.relatie
                   left join kern.srtactie sainh on ainh.srt = sainh.id
                   left join kern.srtactie saav on av.srt = saav.id
                   left join kern.srtrelatie sr on r.srt = sr.id
                   where saav.naam ='Registratie einde geregistreerd partnerschap'
                   and hr.tsverval is not null
                   order by actieinhoud, dataanv de volgende gegevens:
| veld                      | waarde                                              |
| actieinhoud               | Registratie aanvang geregistreerd partnerschap      |
| actieverval               | Registratie einde geregistreerd partnerschap        |
| dataanv                   | 20160510                                            |
| gemaanv                   | 7012                                                |
| dateinde                  | NULL                                                |
| gemeinde                  | NULL                                                |
| soortrelatie              | Geregistreerd partnerschap                          |

!-- Juiste partners gekoppeld aan huwelijk aangemaakt door de ontbinding
Then in kern heeft select sainh.naam as actieInhoud, count(hr.relatie) as aantalRelaties, count(p1.id) ingeschrevenen, count(p2.id) pseudos, p1.bsn as bsn1, p2.bsn as bsn2
                   from kern.betr b
                   join kern.relatie r on r.id = b.relatie
                   join kern.his_relatie hr on r.id = hr.relatie
                   left outer join kern.pers p1 on p1.id = b.pers and p1.srt = 1
                   left outer join kern.pers p2 on p2.id = b.pers and p2.srt = 2
                   left join kern.actie av on av.id = hr.actieverval
                   left join kern.actie ainh on ainh.id = hr.actieinh
                   left join kern.srtactie sainh on ainh.srt = sainh.id
                   left join kern.srtactie saav on av.srt = saav.id
                   where b.rol=3
                   group by actieInhoud, hr.relatie, p1.bsn, p2.bsn
                   order by ingeschrevenen, actieinhoud, bsn1 de volgende gegevens:
| veld                      | waarde                                                |
| actieinhoud               | Registratie aanvang geregistreerd partnerschap        |
| aantalrelaties            | 1                                                     |
| ingeschrevenen            | 1                                                     |
| pseudos                   | 0                                                     |
| bsn1                      | 265424665                                             |
| bsn2                      | NULL                                                  |
----
| actieinhoud               | Registratie aanvang geregistreerd partnerschap          |
| aantalrelaties            | 1                                                     |
| ingeschrevenen            | 1                                                     |
| pseudos                   | 0                                                     |
| bsn1                      | 563546505                                             |
| bsn2                      | NULL                                                  |
----
| actieinhoud               | Registratie einde geregistreerd partnerschap        |
| aantalrelaties            | 1                                                     |
| ingeschrevenen            | 1                                                     |
| pseudos                   | 0                                                     |
| bsn1                      | 265424665                                             |
| bsn2                      | NULL                                                  |
----
| actieinhoud               | Registratie einde geregistreerd partnerschap          |
| aantalrelaties            | 1                                                     |
| ingeschrevenen            | 1                                                     |
| pseudos                   | 0                                                     |
| bsn1                      | 563546505                                             |
| bsn2                      | NULL                                                  |

