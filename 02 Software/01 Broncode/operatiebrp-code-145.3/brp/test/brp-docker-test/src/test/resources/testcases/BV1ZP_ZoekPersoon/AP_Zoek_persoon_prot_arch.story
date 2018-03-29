Meta:
@status             Klaar
@usecase            BV.0.ZP
@regels             R1268, R1269, R1270, R1613, R1614, R1615, R1617, R1618, R1619, R1620, R2236
@sleutelwoorden     Zoek Persoon

Narrative:
Testen op archiverings en protocoleringsregels

Scenario: 1.    Zoek persoon gearchiveerd en geprotocoleerd
                LT: R1268_LT01, R1268_LT02, R1269_LT09, R1270_LT04, R1613_LT03, R1617_LT08, R1618_LT03, R1619_LT06, R1620_LT03, R2236_LT01
                Verwacht resultaat: Correct gearchiveerd en Correct geprotocoleerd

!-- R1613_LT03            Leveringsaantekening.Toegang leveringsautorisatie = Toegang leveringsautorisatie.ID waarvoor verstrekt wordt
!-- R1613_LT03            Leveringsaantekening.Dienst = Dienst.ID van de geleverde Dienst (volgt uit opgegeven de parameters van het verzoekbericht)
!-- R1613_LT03            Leveringsaantekening.Datum/tijd klaarzetten levering = Datum\tijd systeem van ophalen gegevens(Persoon en Autorisatie) uit de database
!-- R1613_LT03            Leveringsaantekening.Datum materieel selectie = 'leeg'
!-- R1617_LT08            Leveringsaantekening.Datum aanvang materiële periode resultaat = Leeg
!-- R1618_LT03            Leveringsaantekening.Datum einde materiële periode resultaat = Leeg
!-- R1619_LT06            Leveringsaantekening.Datum/tijd aanvang formele periode resultaat = 'leeg'
!-- R1620_LT03            Leveringsaantekening.Datum/tijd einde formele periode resultaat = Datum\tijd systeem van ophalen gegevens
!-- R1613_LT03            Leveringsaantekening.Administratieve handeling = 'leeg'
!-- R1613_LT03            Leveringsaantekening.Soort synchronisatie = 'leeg'
!-- R1613_LT03            Leveringsaantekening.Scope patroon = 'leeg'

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'ZoekPersoon dienstbundel zoekopadres' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/10._Zoek_Persoon_Story_9.1.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide

!-- Ingaand bericht wordt gearchiveerd R1268_LT01
Then is er gearchiveerd met de volgende gegevens:
| veld                   | waarde                               |
| srt                    | 75                                   |
| richting               | 1                                    |
| admhnd                 | NULL                                 |
|-- data              -- | -- <wordt gecheckt in aparte stap> --|
| zendendepartij         | 32002                                |
| zendendesysteem        | AFNEMERSYSTEEM                       |
| ontvangendepartij      | NULL                                 |
| referentienr           | 00000000-0000-0000-0000-123456789000 |
| crossreferentienr      | NULL                                 |
|-- tsverzending      -- | -- <wordt gecheckt in aparte stap> --|
|-- tsontv            -- | -- <wordt gecheckt in aparte stap> --|
| verwerkingswijze       | NULL                                 |
| rol                    | 1                                    |
| srtsynchronisatie      | NULL                                 |
|-- levsautorisatie   -- | -- <wordt gecheckt in aparte stap> --|
|-- dienst            -- | -- <wordt gecheckt in aparte stap> --|
| verwerking             | NULL                                 |
| bijhouding             | NULL                                 |
| hoogstemeldingsniveau  | NULL                                 |

Then is het synchrone verzoek correct gearchiveerd
Then tijdstipverzending in bericht is correct gearchiveerd
Then tijdstipontvangst is actueel
Then leveringautorisatie is gelijk in archief
Then dienstid is gelijk in archief

!-- Verzonden uitgaand bericht wordt gearchiveerd R1268_LT02
Then is er gearchiveerd met de volgende gegevens:
| veld                   | waarde                               |
| srt                    | 76                                   |
| richting               | 2                                    |
| admhnd                 | NULL                                 |
|-- data              -- | -- <wordt gecheckt in aparte stap> --|
| zendendepartij         | 2001                                 |
| zendendesysteem        | BRP                                  |
| ontvangendepartij      | 32002                                |
|-- referentienr      -- | -- <wordt gecheckt in aparte stap> --|
| crossreferentienr      | 00000000-0000-0000-0000-123456789000 |
|-- tsverzending      -- | -- <wordt gecheckt in aparte stap> --|
|-- tsontv            -- | -- <wordt gecheckt in aparte stap> --|
| verwerkingswijze       | NULL                                 |
| rol                    | NULL                                 |
| srtsynchronisatie      | NULL                                 |
|-- levsautorisatie   -- | -- <wordt gecheckt in aparte stap> --|
| dienst                 | NULL                                 |
| verwerking             | 1                                    |
| bijhouding             | NULL                                 |
| hoogstemeldingsniveau  | 1                                    |

Then bestaat er een antwoordbericht voor referentie 00000000-0000-0000-0000-123456789000
Then referentienr is gelijk
Then tijdstipverzending in bericht is correct gearchiveerd
Then tijdstipontvangst is actueel

!-- aangepaste R1269_LT10 geen pers opnemen in ber.pers tabel
Then bestaat er geen voorkomen in berpers tabel voor referentie 00000000-0000-0000-0000-123456789000 en srt lvg_bvgZoekPersoon

!-- R1270_LT05
Then is er gearchiveerd met de volgende gegevens:
| veld     | waarde    |
| bsn      | 606417801 |
| richting | 2         |

!-- R1613_LT04
Then is er voor leveringsautorisatie ZoekPersoon dienstbundel zoekopadres en partij Gemeente Standaard geprotocolleerd met de volgende gegevens:
| veld                        | waarde       |
| bsn                         | 606417801    |
| dataanvmaterieleperioderes  | NULL         |
| dateindematerieleperioderes | NULL         |
| tsaanvformeleperioderes     | NULL         |
| admhnd                      | NULL         |
| srtsynchronisatie           | NULL         |
| scopepatroon                | NULL         |
| tseindeformeleperioderes    | NU           |
| soortDienst                 | Zoek persoon |

!-- Bevraging goed, maar geen personen, dus geen protocollering
Scenario: 2.    Bevraging geslaagd, zoek persoon waar niemand gevonden wordt
                LT: R2236_LT04
                Verwacht resultaat:
                - Anne wordt NIET gevonden
                - Dus niet geprotocolleerd

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Anne_met_Historie.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_Niemand_Gevonden.xml
Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht 0 groepen 'persoon'

Then is er niet geprotocolleerd

!-- Bevraging fout, dus geen personen, dus geen protocollering
Scenario: 3.    Zoek Persoon met meer tussenresultaten dan toegestaan.
                LT: R2236_LT04
                Uitwerking: Er worden 12 personen gevonden, het maximum aantal tussenresultaten = 12,
                door populatiebeperking wordt 1 persoon uit de resultaten gefilterd, waarmee het aantal zoekresultaten op 11 uitkomt.
                Dit is meer dan de default van max 10.
                Verwacht resultaat:
                - Foutief
                - dus geen protocollering

!-- Inladen van een leveringsautorisatie zonder Dienst.maximum aantal zoek resultaten. Default = 10
!-- Inladen van 12 testpersonen, max aantal tussenresultaten is geconfigureerd op 12
Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kim.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kanye.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Khloe.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kourtney.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kylie.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kylie2.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kylie3.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kylie4.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kylie5.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kylie6.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kylie7.xls
Given enkel initiele vulling uit bestand /LO3PL/R2286/Kylie8.xls

!-- 1 Persoon valt af door Populatie beperking (Kanye), waarmee het aantal zoekresultaten op 11 komt
!-- R2289_LT04
Given verzoek voor leveringsautorisatie 'Zoek Persoon totale popbep gevuld' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_Foutief.xml

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                   |
| R2289     | De zoekopdracht heeft teveel resultaten opgeleverd        |

Then is er niet geprotocolleerd