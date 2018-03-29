Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative: Nietigverklaring huwelijk in Nederland

Scenario:   Nietigverklaring van een symmetrisch BRP huwelijk Ingeschrevene Ingeschrevene
            LT: NHNL02C10T20

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-NHNL/NHNL02C10T20-001.xls
Given enkel initiele vulling uit bestand /LO3PL-NHNL/NHNL02C10T20-002.xls

When voer een bijhouding uit NHNL02C10T20a.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

Given pas laatste relatie van soort 1 aan tussen persoon 193035273 en persoon 701445385 met relatie id 43000102 en betrokkenheid id 43000102

Then is in de database de persoon met bsn 193035273 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 701445385 wel als PARTNER betrokken bij een HUWELIJK

When voer een bijhouding uit NHNL02C10T20b.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/NHNL/expected/NHNL02C10T20.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 193035273 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 701445385 niet als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 8261315873 uit database en vergelijk met expected NHNL02C10T20-persoon1.xml
Then lees persoon met anummer 8718134561 uit database en vergelijk met expected NHNL02C10T20-persoon2.xml

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
                   where sainh.naam ='Registratie einde huwelijk'
                   and hr.tsreg is not null
                   order by actieinhoud, dataanv de volgende gegevens:
| veld                      | waarde                                        |
| actieinhoud               | Registratie einde huwelijk                    |
| actieverval               | NULL                                          |
| dataanv                   | 20160510                                      |
| gemaanv                   | 7012                                          |
| wplnaamaanv               | NULL                                          |
| dateinde                  | 20160510                                      |
| gemeinde                  | 7012                                          |
| wplnaameinde              | Makkum                                        |
| soortrelatie              | Huwelijk                                      |
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
                   where saav.naam ='Registratie einde huwelijk'
                   and hr.tsverval is not null
                   order by actieinhoud, dataanv de volgende gegevens:
| veld                      | waarde                                              |
| actieinhoud               | Registratie aanvang huwelijk                        |
| actieverval               | Registratie einde huwelijk                          |
| dataanv                   | 20160510                                            |
| gemaanv                   | 7012                                                |
| dateinde                  | NULL                                                |
| gemeinde                  | NULL                                                |
| soortrelatie              | Huwelijk                                            |

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
| actieinhoud               | Registratie aanvang huwelijk                          |
| aantalrelaties            | 1                                                     |
| ingeschrevenen            | 1                                                     |
| pseudos                   | 0                                                     |
| bsn1                      | 193035273                                             |
| bsn2                      | NULL                                                  |
----
| actieinhoud               | Registratie aanvang huwelijk                          |
| aantalrelaties            | 1                                                     |
| ingeschrevenen            | 1                                                     |
| pseudos                   | 0                                                     |
| bsn1                      | 701445385                                             |
| bsn2                      | NULL                                                  |
----
| actieinhoud               | Registratie einde huwelijk                            |
| aantalrelaties            | 1                                                     |
| ingeschrevenen            | 1                                                     |
| pseudos                   | 0                                                     |
| bsn1                      | 193035273                                             |
| bsn2                      | NULL                                                  |
----
| actieinhoud               | Registratie einde huwelijk                            |
| aantalrelaties            | 1                                                     |
| ingeschrevenen            | 1                                                     |
| pseudos                   | 0                                                     |
| bsn1                      | 701445385                                             |
| bsn2                      | NULL                                                  |










