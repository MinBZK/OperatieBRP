Meta:
@status                 Klaar
@sleutelwoorden         Geslaagd
@usecase                UCS-BY.HG

Narrative:
Wijzigen partnergegevens huwelijk identificatienummers, samengestelde naam, geboorte en geslachtsaanduiding

Scenario:   de partner is een pseudo-persoon (onbekend in BRP) en identificatienummers, samengestelde naam, geboorte en geslachtsaanduiding worden gewijzigd
            LT: WPHW04C10T20


Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-WPHW/WPHW04C10T20-001.xls

When voer een bijhouding uit WPHW04C10T20a.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

Then is in de database de persoon met bsn 800148873 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 956729289 wel als PARTNER betrokken bij een HUWELIJK

Given pas laatste relatie van soort 1 aan tussen persoon 800148873 en persoon 956729289 met relatie id 50000001 en betrokkenheid id 50000001
Given pas laatste relatie van soort 1 aan tussen persoon 956729289 en persoon 800148873 met relatie id 50000002 en betrokkenheid id 50000002

When voer een bijhouding uit WPHW04C10T20b.xml namens partij 'Gemeente BRP 1'

!-- Controleer dat ontrelateren niet is getriggerd
Then in kern heeft select count(1)
                   from kern.his_betr hr join kern.actie ainh on ainh.id = hr.actieinh LEFT join kern.actie av on av.id = hr.actieverval left join kern.srtactie sainh on ainh.srt = sainh.id left join kern.srtactie saav on av.srt = saav.id
                   where sainh.naam ='Ontrelateren' and av.srt is NULL de volgende gegevens:
| veld                      | waarde |
| count                     | 0      |

!-- Controleer verantwoordingsgegevens van de ActieBron
Then in kern heeft select count(1), sdoc.naam
                   from kern.actiebron ab
                   join kern.actie ainh on ainh.id = ab.actie
                   left join kern.srtactie sainh on ainh.srt = sainh.id
                   left join kern.doc doc on doc.id = ab.doc
                   left join kern.srtdoc sdoc on sdoc.id = doc.srt
                   where sainh.naam in ('Registratie identificatienummers gerelateerde') group by sdoc.naam de volgende gegevens:
| veld   | waarde                                  |
| count  | 1                                       |
| naam   | Overig                                  |


!-- Controleer kern.his_persids, in deze groep wordt bij de partner (pseudo) een nieuwe rij aangemaakt en de oude vervalt materieel.
Then in kern heeft select p.voornamen, id.anr,id.bsn, sainh.naam as actieinhoud, saav.naam as actieVerval, id.dateindegel, CASE WHEN id.tsverval IS NULL THEN 'Leeg' ELSE 'Gevuld' END AS tsverval
                   from kern.his_persids id
                   left join kern.pers p on p.id = id.pers
                   left join kern.actie ainh on ainh.id = id.actieinh
                   left join kern.srtactie sainh on ainh.srt = sainh.id
                   left join kern.actie av on av.id = id.actieverval
                   left join kern.srtactie saav on av.srt = saav.id
                   where p.voornamen is null and p.srt = '2' order by id.anr, id.dateindegel de volgende gegevens:
| veld              | waarde                                       |
| voornamen         | NULL                                         |
| anr               | 1082678674                                   |
| bsn               | 956729289                                    |
| actieinhoud       | Registratie aanvang huwelijk                 |
| actieverval       | NULL                                         |
| dateindegel       | 20170101                                     |
| tsverval          | Leeg                                         |
----
| voornamen         | NULL                                         |
| anr               | 1082678674                                   |
| bsn               | 956729289                                    |
| actieinhoud       | Registratie aanvang huwelijk                 |
| actieverval       | Registratie identificatienummers gerelateerde|
| dateindegel       | NULL                                         |
| tsverval          | Gevuld                                       |
----
| voornamen         | NULL                                         |
| anr               | 5238902546                                   |
| bsn               | 131445261                                    |
| actieinhoud       | Registratie identificatienummers gerelateerde|
| actieverval       | NULL                                         |
| dateindegel       | NULL                                         |
| tsverval          | Leeg                                         |

!-- Controleer kern.his_perssamengesteldenaam, in deze groep wordt bij de partner (pseudo) een nieuwe rij aangemaakt en de oude vervalt materieel.
Then in kern heeft select id.voornamen, id.geslnaamstam, id.indnreeks, id.adellijketitel, sainh.naam as actieinhoud, saav.naam as actieVerval, id.dateindegel, CASE WHEN tsverval IS NULL THEN 'Leeg' ELSE 'Gevuld' END AS tsverval
                   from kern.his_perssamengesteldenaam id
                   left join kern.pers p on p.id = id.pers
                   left join kern.actie ainh on ainh.id = id.actieinh
                   left join kern.srtactie sainh on ainh.srt = sainh.id
                   left join kern.actie av on av.id = id.actieverval
                   left join kern.srtactie saav on av.srt = saav.id
                   where id.geslnaamstam = 'Jansen' or id.geslnaamstam = 'Puffelen' and p.srt = '2' order by id.voornamen, id.dateindegel
de volgende gegevens:
| veld              | waarde                                     |
| voornamen         | Karel                                      |
| geslnaamstam      | Jansen                                     |
| indnreeks         | false                                      |
| adellijketitel    | NULL                                       |
| actieinhoud       | Registratie aanvang huwelijk               |
| actieverval       | NULL                                       |
| dateindegel       | 20170101                                   |
| tsverval          | Leeg                                       |
----
| voornamen         | Karel                                      |
| geslnaamstam      | Jansen                                     |
| indnreeks         | false                                      |
| adellijketitel    | NULL                                       |
| actieinhoud       | Registratie aanvang huwelijk               |
| actieverval       | Registratie samengestelde naam gerelateerde|
| dateindegel       | NULL                                       |
| tsverval          | Gevuld                                     |
----
| voornamen         | NULL                                       |
| geslnaamstam      | Puffelen                                   |
| indnreeks         | true                                       |
| adellijketitel    | 1                                          |
| actieinhoud       | Registratie samengestelde naam gerelateerde|
| actieverval       | NULL                                       |
| dateindegel       | NULL                                       |
| tsverval          | Leeg                                       |


!-- Controleer kern.his_persgeboorte, in deze groep wordt bij de partner (pseudo) een nieuwe rij aangemaakt en de oude vervalt formeel.
Then in kern heeft select p.voornamen, id.datgeboorte, id.blplaatsgeboorte, id.blregiogeboorte, id.omslocgeboorte, lg.naam, sainh.naam as actieinhoud, saav.naam as actieVerval, id.nadereaandverval, CASE WHEN tsverval IS NULL THEN 'Leeg' ELSE 'Gevuld' END AS tsverval
                   from kern.his_persgeboorte id
                   left join kern.pers p on p.id = id.pers
                   left join kern.actie ainh on ainh.id = id.actieinh
                   left join kern.srtactie sainh on ainh.srt = sainh.id
                   left join kern.actie av on av.id = id.actieverval
                   left join kern.srtactie saav on av.srt = saav.id
                   left join kern.landgebied lg on lg.id = id.landgebiedgeboorte
                   where p.voornamen is null and p.srt = '2' order by lg.naam de volgende gegevens:
| veld              | waarde                           |
| voornamen         | NULL                             |
| datgeboorte       | 19610821                         |
| blplaatsgeboorte  | Almeria                          |
| blregiogeboorte   | Andalusie                        |
| omslocgeboorte    | Zuid Spanje                      |
| naam              | Canada                           |
| actieinhoud       | Registratie geboorte gerelateerde|
| actieverval       | NULL                             |
| nadereaandverval  | NULL                             |
| tsverval          | Leeg                             |
----
| voornamen         | NULL                             |
| datgeboorte       | 19800101                         |
| blplaatsgeboorte  | NULL                             |
| blregiogeboorte   | NULL                             |
| omslocgeboorte    | NULL                             |
| naam              | Nederland                        |
| actieinhoud       | Registratie aanvang huwelijk     |
| actieverval       | Registratie geboorte gerelateerde|
| nadereaandverval  | NULL                             |
| tsverval          | Gevuld                           |

!-- Controleer kern.his_persgeslachtsaand, in deze groep wordt bij de partner (pseudo) een nieuwe rij aangemaakt en de oude vervalt materieel.
Then in kern heeft select p.voornamen, id.geslachtsaand, sainh.naam as actieinhoud, saav.naam as actieVerval, id.dateindegel, CASE WHEN tsverval IS NULL THEN 'Leeg' ELSE 'Gevuld' END AS tsverval
                   from kern.his_persgeslachtsaand id
                   left join kern.pers p on p.id = id.pers
                   left join kern.actie ainh on ainh.id = id.actieinh
                   left join kern.srtactie sainh on ainh.srt = sainh.id
                   left join kern.actie av on av.id = id.actieverval
                   left join kern.srtactie saav on av.srt = saav.id
                   where p.voornamen is null and p.srt = '2' order by id.geslachtsaand, id.dateindegel de volgende gegevens:
| veld              | waarde                                      |
| voornamen         | NULL                                        |
| geslachtsaand     | 1                                           |
| actieinhoud       | Registratie geslachtsaanduiding gerelateerde|
| actieverval       | NULL                                        |
| dateindegel       | NULL                                        |
| tsverval          | Leeg                                        |
----
| voornamen         | NULL                                        |
| geslachtsaand     | 2                                           |
| actieinhoud       | Registratie aanvang huwelijk                |
| actieverval       | NULL                                        |
| dateindegel       | 20170101                                    |
| tsverval          | Leeg                                        |
----
| voornamen         | NULL                                        |
| geslachtsaand     | 2                                           |
| actieinhoud       | Registratie aanvang huwelijk                |
| actieverval       | Registratie geslachtsaanduiding gerelateerde|
| dateindegel       | NULL                                        |
| tsverval          | Gevuld                                      |


