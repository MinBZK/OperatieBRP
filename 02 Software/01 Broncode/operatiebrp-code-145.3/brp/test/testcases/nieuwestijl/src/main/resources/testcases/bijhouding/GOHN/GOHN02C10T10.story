Meta:
@status                 Klaar
@usecase                UCS-BY.HG


Narrative:
Verwerking GBA ontbinding huwelijk in Nederland

Scenario:   Verwerking GBA ontbinding huwelijk in NL tussen Ingeschrevene en Pseudo
            LT: GOHN02C10T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-GOHN/GOHN-Anne.xls

Given pas laatste relatie van soort 1 aan tussen persoon 303251049 en persoon 547166473 met relatie id 1000003 en betrokkenheid id 1000004

When voer een GBA bijhouding uit GOHN02C10T10.xml namens partij 'Migratievoorziening'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/GOHN/expected/GOHN02C10T10.xml voor expressie //brp:isc_migRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 303251049 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 547166473 niet als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 3862424609 uit database en vergelijk met expected GOHN02C10T10-persoon1.xml

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
| dataanv                   | 20050808                                      |
| gemaanv                   | 7012                                          |
| wplnaamaanv               | NULL                                          |
| dateinde                  | 20160601                                      |
| gemeinde                  | 7011                                          |
| wplnaameinde              | NULL                                          |
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
| actieinhoud               | Conversie GBA                                       |
| actieverval               | Registratie einde huwelijk                          |
| dataanv                   | 20050808                                            |
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
| actieinhoud               | Conversie GBA                                         |
| aantalrelaties            | 1                                                     |
| ingeschrevenen            | 0                                                     |
| pseudos                   | 1                                                     |
| bsn1                      | NULL                                                  |
| bsn2                      | 547166473                                             |
----
| actieinhoud               | Registratie einde huwelijk                            |
| aantalrelaties            | 1                                                     |
| ingeschrevenen            | 0                                                     |
| pseudos                   | 1                                                     |
| bsn1                      | NULL                                                  |
| bsn2                      | 547166473                                             |
----
| actieinhoud               | Conversie GBA                                         |
| aantalrelaties            | 1                                                     |
| ingeschrevenen            | 1                                                     |
| pseudos                   | 0                                                     |
| bsn1                      | 303251049                                             |
| bsn2                      | NULL                                                  |
----
| actieinhoud               | Registratie einde huwelijk                            |
| aantalrelaties            | 1                                                     |
| ingeschrevenen            | 1                                                     |
| pseudos                   | 0                                                     |
| bsn1                      | 303251049                                             |
| bsn2                      | NULL                                                  |