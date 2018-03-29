Meta:
@status                 Klaar
@usecase                UCS-BY.HG


Narrative: Omzetting geregistreerd partnerschap in huwelijk

Scenario:   Omzetten van een GBA Geregistreerd Partnerschap
            LT: OMGP04C10T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-OMGP/OMGP04C10T10-001.xls

Given pas laatste relatie van soort 2 aan tussen persoon 534066185 en persoon 798498249 met relatie id 31000001 en betrokkenheid id 31000001

When voer een bijhouding uit OMGP04C10T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/OMGP/expected/OMGP04C10T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 534066185 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
Then is in de database de persoon met bsn 798498249 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
Then is in de database de persoon met bsn 534066185 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 798498249 wel als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 6361452321 uit database en vergelijk met expected OMGP04C10T10-persoon1.xml

!-- Controleer de actuele relaties
Then in kern heeft select sainh.naam as actieInhoud, saav.naam as actieVerval, hr.dataanv, hr.gemaanv, hr.dateinde, hr.gemeinde, sr.naam as soortRelatie
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
| dataanv                   | 20050808                                      |
| gemaanv                   | 7012                                          |
| dateinde                  | 20160901                                      |
| gemeinde                  | 7013                                          |
| soortrelatie              | Geregistreerd partnerschap                    |
----
| actieinhoud               | Registratie einde geregistreerd partnerschap  |
| actieverval               | NULL                                          |
| dataanv                   | 20160901                                      |
| gemaanv                   | 7013                                          |
| dateinde                  | NULL                                          |
| gemeinde                  | NULL                                          |
| soortrelatie              | Huwelijk                                      |

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
| veld                      | waarde                                        |
| actieinhoud               | Conversie GBA                                 |
| actieverval               | Registratie einde geregistreerd partnerschap  |
| dataanv                   | 20050808                                      |
| gemaanv                   | 7012                                          |
| dateinde                  | NULL                                          |
| gemeinde                  | NULL                                          |
| soortrelatie              | Geregistreerd partnerschap                    |

!-- Juiste partners gekoppeld aan huwelijk aangemaakt door de omzetting
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
                   order by ingeschrevenen, actieinhoud de volgende gegevens:
| veld                      | waarde                                                |
| actieinhoud               | Conversie GBA                                         |
| aantalrelaties            | 1                                                     |
| ingeschrevenen            | 0                                                     |
| pseudos                   | 1                                                     |
| bsn1                      | NULL                                                  |
| bsn2                      | 798498249                                             |
----
| actieinhoud               | Registratie einde geregistreerd partnerschap          |
| aantalrelaties            | 1                                                     |
| ingeschrevenen            | 0                                                     |
| pseudos                   | 1                                                     |
| bsn1                      | NULL                                                  |
| bsn2                      | 798498249                                             |
----
| actieinhoud               | Registratie einde geregistreerd partnerschap          |
| aantalrelaties            | 1                                                     |
| ingeschrevenen            | 0                                                     |
| pseudos                   | 1                                                     |
| bsn1                      | NULL                                                  |
| bsn2                      | 798498249                                             |
----
| actieinhoud               | Conversie GBA                                         |
| aantalrelaties            | 1                                                     |
| ingeschrevenen            | 1                                                     |
| pseudos                   | 0                                                     |
| bsn1                      | 534066185                                             |
| bsn2                      | NULL                                                  |
----
| actieinhoud               | Registratie einde geregistreerd partnerschap          |
| aantalrelaties            | 1                                                     |
| ingeschrevenen            | 1                                                     |
| pseudos                   | 0                                                     |
| bsn1                      | 534066185                                             |
| bsn2                      | NULL                                                  |
----
| actieinhoud               | Registratie einde geregistreerd partnerschap          |
| aantalrelaties            | 1                                                     |
| ingeschrevenen            | 1                                                     |
| pseudos                   | 0                                                     |
| bsn1                      | 534066185                                             |
| bsn2                      | NULL                                                  |