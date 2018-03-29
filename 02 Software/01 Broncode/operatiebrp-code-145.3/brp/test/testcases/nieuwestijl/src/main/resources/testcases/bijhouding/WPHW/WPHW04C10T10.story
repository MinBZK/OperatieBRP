Meta:
@status                 Klaar
@sleutelwoorden         Geslaagd
@usecase                UCS-BY.HG

Narrative:
Wijzigen partnergegevens huwelijk identificatienummers, samengestelde naam, geboorte en geslachtsaanduiding

Scenario:   de partner is een ingeschrevene en identificatienummers, samengestelde naam, geboorte en geslachtsaanduiding worden gewijzigd
            LT: WPHW04C10T10


Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-WPHW/WPHW04C10T10-001.xls
Given enkel initiele vulling uit bestand /LO3PL-WPHW/WPHW04C10T10-002.xls

When voer een bijhouding uit WPHW04C10T10a.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

Then is in de database de persoon met bsn 563420777 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 366970409 wel als PARTNER betrokken bij een HUWELIJK

Given pas laatste relatie van soort 1 aan tussen persoon 563420777 en persoon 366970409 met relatie id 50000001 en betrokkenheid id 50000001
Given pas laatste relatie van soort 1 aan tussen persoon 366970409 en persoon 563420777 met relatie id 50000002 en betrokkenheid id 50000002

When voer een bijhouding uit WPHW04C10T10b.xml namens partij 'Gemeente BRP 1'

Then is het antwoordbericht gelijk aan /testcases/bijhouding/WPHW/expected/WPHW04C10T10.xml voor expressie //brp:bhg_hgpActualiseerHuwelijkGeregistreerdPartnerschap_R

!-- Controleer dat ontrelateren is getriggerd
Then in kern heeft select count(1)
                   from kern.his_betr hr join kern.actie ainh on ainh.id = hr.actieinh LEFT join kern.actie av on av.id = hr.actieverval left join kern.srtactie sainh on ainh.srt = sainh.id left join kern.srtactie saav on av.srt = saav.id
                   where sainh.naam ='Ontrelateren' and av.srt is NULL de volgende gegevens:
| veld                      | waarde |
| count                     | 4      |

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
Then in kern heeft select sg.voornamen, id.anr,id.bsn, sainh.naam as actieinhoud, saav.naam as actieVerval, id.dateindegel, CASE WHEN id.tsverval IS NULL THEN 'Leeg' ELSE 'Gevuld' END AS tsverval
from kern.his_persids id
left join kern.pers p on p.id = id.pers
left join kern.his_perssamengesteldenaam sg on sg.pers = id.pers
left join kern.actie ainh on ainh.id = id.actieinh
left join kern.srtactie sainh on ainh.srt = sainh.id
left join kern.actie av on av.id = id.actieverval
left join kern.srtactie saav on av.srt = saav.id
where sg.voornamen = 'Karel' and p.srt = '2' order by id.anr, id.dateindegel de volgende gegevens:
| veld              | waarde                                       |
| voornamen         | Karel                                        |
| anr               | 5238902546                                   |
| bsn               | 131445261                                    |
| actieinhoud       | Registratie identificatienummers gerelateerde|
| actieverval       | NULL                                         |
| dateindegel       | NULL                                         |
| tsverval          | Leeg                                         |
----
| voornamen         | Karel                                        |
| anr               | 5398948626                                   |
| bsn               | 366970409                                    |
| actieinhoud       | Conversie GBA                                |
| actieverval       | NULL                                         |
| dateindegel       | 20170101                                     |
| tsverval          | Leeg                                         |
----
| voornamen         | Karel                                        |
| anr               | 5398948626                                   |
| bsn               | 366970409                                    |
| actieinhoud       | Conversie GBA                                |
| actieverval       | Registratie identificatienummers gerelateerde|
| dateindegel       | NULL                                         |
| tsverval          | Gevuld                                       |

!-- Controleer kern.his_perssamengesteldenaam, in deze groep wordt bij de partner (pseudo) een nieuwe rij aangemaakt en de oude vervalt materieel.
Then in kern heeft select id.voornamen, id.geslnaamstam, id.indnreeks, id.predicaat, id.voorvoegsel, id.scheidingsteken, sainh.naam as actieinhoud, saav.naam as actieVerval, id.dateindegel, CASE WHEN tsverval IS NULL THEN 'Leeg' ELSE 'Gevuld' END AS tsverval
                   from kern.his_perssamengesteldenaam id
                   left join kern.pers p on p.id = id.pers
                   left join kern.actie ainh on ainh.id = id.actieinh
                   left join kern.srtactie sainh on ainh.srt = sainh.id
                   left join kern.actie av on av.id = id.actieverval
                   left join kern.srtactie saav on av.srt = saav.id
                   where p.voornamen = 'Karel' and p.srt = '2' order by id.voornamen, id.dateindegel
de volgende gegevens:
| veld              | waarde                                     |
| voornamen         | Karel                                      |
| geslnaamstam      | Puffelen                                   |
| indnreeks         | false                                      |
| predicaat         | 1                                          |
| voorvoegsel       | l                                          |
| scheidingsteken   | '                                          |
| actieinhoud       | Registratie samengestelde naam gerelateerde|
| actieverval       | NULL                                       |
| dateindegel       | NULL                                       |
| tsverval          | Leeg                                       |
----
| voornamen         | Piet                                       |
| geslnaamstam      | Jansen                                     |
| indnreeks         | false                                      |
| predicaat         | NULL                                       |
| voorvoegsel       | NULL                                       |
| scheidingsteken   | NULL                                       |
| actieinhoud       | Conversie GBA                              |
| actieverval       | NULL                                       |
| dateindegel       | 20170101                                   |
| tsverval          | Leeg                                       |
----
| voornamen         | Piet                                       |
| geslnaamstam      | Jansen                                     |
| indnreeks         | false                                      |
| predicaat         | NULL                                       |
| voorvoegsel       | NULL                                       |
| scheidingsteken   | NULL                                       |
| actieinhoud       | Conversie GBA                              |
| actieverval       | Registratie samengestelde naam gerelateerde|
| dateindegel       | NULL                                       |
| tsverval          | Gevuld                                     |



!-- Controleer kern.his_persgeboorte, in deze groep wordt bij de partner (pseudo) een nieuwe rij aangemaakt en de oude vervalt formeel.
Then in kern heeft select p.voornamen, id.datgeboorte, id.blplaatsgeboorte, id.blregiogeboorte, id.omslocgeboorte, lg.naam, sainh.naam as actieinhoud, saav.naam as actieVerval, id.nadereaandverval, CASE WHEN tsverval IS NULL THEN 'Leeg' ELSE 'Gevuld' END AS tsverval
                   from kern.his_persgeboorte id
                   left join kern.pers p on p.id = id.pers
                   left join kern.actie ainh on ainh.id = id.actieinh
                   left join kern.srtactie sainh on ainh.srt = sainh.id
                   left join kern.actie av on av.id = id.actieverval
                   left join kern.srtactie saav on av.srt = saav.id
                   left join kern.landgebied lg on lg.id = id.landgebiedgeboorte
                   where p.voornamen = 'Karel' and p.srt = '2' order by lg.naam de volgende gegevens:
| veld              | waarde                           |
| voornamen         | Karel                            |
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
| voornamen         | Karel                            |
| datgeboorte       | 19600821                         |
| blplaatsgeboorte  | NULL                             |
| blregiogeboorte   | NULL                             |
| omslocgeboorte    | NULL                             |
| naam              | Nederland                        |
| actieinhoud       | Conversie GBA                    |
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
                   where p.voornamen = 'Karel' and p.srt = '2' order by id.geslachtsaand, id.dateindegel de volgende gegevens:
| veld              | waarde                                      |
| voornamen         | Karel                                       |
| geslachtsaand     | 1                                           |
| actieinhoud       | Registratie geslachtsaanduiding gerelateerde|
| actieverval       | NULL                                        |
| dateindegel       | NULL                                        |
| tsverval          | Leeg                                        |
----
| voornamen         | Karel                                       |
| geslachtsaand     | 2                                           |
| actieinhoud       | Conversie GBA                               |
| actieverval       | NULL                                        |
| dateindegel       | 20170101                                    |
| tsverval          | Leeg                                        |
----
| voornamen         | Karel                                       |
| geslachtsaand     | 2                                           |
| actieinhoud       | Conversie GBA                               |
| actieverval       | Registratie geslachtsaanduiding gerelateerde|
| dateindegel       | NULL                                        |
| tsverval          | Gevuld                                      |


