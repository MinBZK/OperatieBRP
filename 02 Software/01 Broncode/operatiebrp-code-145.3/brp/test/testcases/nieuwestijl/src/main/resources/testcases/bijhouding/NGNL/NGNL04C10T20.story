Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative: Nietigverklaring geregistreerd partnerschap in Nederland

Scenario:   Nietigverklaring van een symmetrisch BRP geregistreerd partnerschap Ingeschrevene Ingeschrevene
            LT: NGNL04C10T20

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-NGNL/NGNL04C10T20-001.xls
Given enkel initiele vulling uit bestand /LO3PL-NGNL/NGNL04C10T20-002.xls

When voer een bijhouding uit NGNL04C10T20a.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

Then is in de database de persoon met bsn 589297065 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
Then is in de database de persoon met bsn 580290153 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP

Given pas laatste relatie van soort 2 aan tussen persoon 589297065 en persoon 580290153 met relatie id 43000112 en betrokkenheid id 43000112

When voer een bijhouding uit NGNL04C10T20b.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/NGNL/expected/NGNL04C10T20.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 589297065 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
Then is in de database de persoon met bsn 580290153 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP

Then lees persoon met anummer 3474956321 uit database en vergelijk met expected NGNL04C10T20-persoon1.xml
Then lees persoon met anummer 5194721825 uit database en vergelijk met expected NGNL04C10T20-persoon2.xml


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
| dateinde                  | 20160510                                      |
| gemeinde                  | 7012                                          |
| wplnaameinde              | Makkum                                        |
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
| bsn1                      | 580290153                                             |
| bsn2                      | NULL                                                  |
----
| actieinhoud               | Registratie aanvang geregistreerd partnerschap        |
| aantalrelaties            | 1                                                     |
| ingeschrevenen            | 1                                                     |
| pseudos                   | 0                                                     |
| bsn1                      | 589297065                                             |
| bsn2                      | NULL                                                  |
----
| actieinhoud               | Registratie einde geregistreerd partnerschap          |
| aantalrelaties            | 1                                                     |
| ingeschrevenen            | 1                                                     |
| pseudos                   | 0                                                     |
| bsn1                      | 580290153                                             |
| bsn2                      | NULL                                                  |
----
| actieinhoud               | Registratie einde geregistreerd partnerschap          |
| aantalrelaties            | 1                                                     |
| ingeschrevenen            | 1                                                     |
| pseudos                   | 0                                                     |
| bsn1                      | 589297065                                             |
| bsn2                      | NULL                                                  |











