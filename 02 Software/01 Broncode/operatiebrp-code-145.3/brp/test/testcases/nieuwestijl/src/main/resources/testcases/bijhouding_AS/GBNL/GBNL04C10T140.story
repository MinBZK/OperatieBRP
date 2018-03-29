Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: Geboorte in Nederland: Registratie geborene

Scenario:   Registratie geborene in Nederland met namenreeks J
            LT: GBNL04C10T140

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL04C10T130-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL04C10T130-002.xls

When voer een bijhouding uit GBNL04C10T140.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

!-- Controleer persgeslachtsnaamcomponent
Then in kern heeft select vn.naam, vn.volgnr, hvn.dataanvgel
                   from kern.persvoornaam vn
                   left outer join kern.his_persvoornaam hvn on hvn.persvoornaam = vn.id
                   left outer join kern.actie a on a.id = hvn.actieinh
                   left outer join kern.srtactie sa on sa.id = a.srt
                   left outer join kern.pers pe on pe.id = vn.pers de volgende gegevens:
| veld                      | waarde                                        |
| naam                      | Mama                                          |
| volgnr                    | 1                                             |
| dataanvgel                | 19920808                                      |
----
| naam                      | Papa                                          |
| volgnr                    | 1                                             |
| dataanvgel                | 19920808                                      |

!-- Controleer pers samengesteldenaam
Then in kern heeft select sg.geslnaamstam, sg.voornamen
                   from kern.his_perssamengesteldenaam sg
                   left outer join kern.actie a on a.id = sg.actieinh
                   left outer join kern.srtactie sa on sa.id = a.srt
                   where sa.naam ='Registratie geborene' de volgende gegevens:
| veld                      | waarde                                        |
| geslnaamstam              | Vader                                         |
| voornamen                 | NULL                                          |
