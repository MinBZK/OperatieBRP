Meta:
@status             Klaar
@usecase            BV.0.ZA
@regels             R1268, R1269, R1270
@sleutelwoorden     Zoek Persoon op adresgegevens

Narrative:
Testen op archiveringsregels

Scenario: 1.    Jan wordt gezocht op adresgegevens
                LT: R1268_LT01, R1268_LT02, R1269_LT10, R1266_LT08
                Verwacht resultaat:
                - Correct gearchiveerd

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'ZoekPersoonAdres' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV0ZA_Zoek_persoon_op_adresgegevens/Requests/9._Zoek_Persoon_op_Adres_Story_8.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide

!-- R1266_LT08 Controle op responsebericht
Then heeft in het antwoordbericht 'zendendePartij' in 'stuurgegevens' de waarde '199903'
Then heeft in het antwoordbericht 'zendendeSysteem' in 'stuurgegevens' de waarde 'BRP'
Then is in antwoordbericht de aanwezigheid van 'referentienummer' in 'stuurgegevens' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'crossReferentienummer' in 'stuurgegevens' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'tijdstipVerzending' in 'stuurgegevens' nummer 1 ja

!-- Ingaand bericht wordt gearchiveerd R1268_LT01
Then is er gearchiveerd met de volgende gegevens:
| veld                  | waarde                               |
| srt                   | 151                                  |
| richting              | 1                                    |
| admhnd                | NULL                                 |
|-- data              --|-- <wordt gecheckt in aparte stap>  --|
| zendendepartij        | 32002                                  |
| zendendesysteem       | AFNEMERSYSTEEM                       |
| ontvangendepartij     | NULL                                 |
| referentienr          | 00000000-0000-0000-0000-123456789000 |
| crossreferentienr     | NULL                                 |
|-- tsverzending      --|-- <wordt gecheckt in aparte stap>  --|
|-- tsontv            --|-- <wordt gecheckt in aparte stap>  --|
| verwerkingswijze      | NULL                                 |
| rol                   | 1                                    |
| srtsynchronisatie     | NULL                                 |
|-- levsautorisatie   --|-- <wordt gecheckt in aparte stap>  --|
|-- dienst            --|-- <wordt gecheckt in aparte stap>  --|
| verwerking            | NULL                                 |
| bijhouding            | NULL                                 |
| hoogstemeldingsniveau | NULL                                 |

Then is het synchrone verzoek correct gearchiveerd
Then tijdstipverzending in bericht is correct gearchiveerd
Then tijdstipontvangst is actueel
Then leveringautorisatie is gelijk in archief
Then dienstid is gelijk in archief

!-- Verzonden uitgaand bericht wordt gearchiveerd R1268_LT02
Then is er gearchiveerd met de volgende gegevens:
| veld                  | waarde                               |
| srt                   | 152                                   |
| richting              | 2                                    |
| admhnd                | NULL                                 |
|-- data              --|-- <wordt gecheckt in aparte stap>  --|
| zendendepartij        | 2001                                 |
| zendendesysteem       | BRP                                  |
| ontvangendepartij     | 32002                                  |
|-- referentienr      --|-- <wordt gecheckt in aparte stap>  --|
| crossreferentienr     | 00000000-0000-0000-0000-123456789000 |
|-- tsverzending      --|-- <wordt gecheckt in aparte stap>  --|
|-- tsontv            --|-- <wordt gecheckt in aparte stap>  --|
| verwerkingswijze      | NULL                                 |
| rol                   | NULL                                 |
| srtsynchronisatie     | NULL                                 |
|-- levsautorisatie   --|-- <wordt gecheckt in aparte stap>  --|
| dienst                | NULL                                 |
| verwerking            | 1                                    |
| bijhouding            | NULL                                 |
| hoogstemeldingsniveau | 1                                    |

Then bestaat er een antwoordbericht voor referentie 00000000-0000-0000-0000-123456789000
Then referentienr is gelijk
Then tijdstipverzending in bericht is correct gearchiveerd
Then tijdstipontvangst is actueel

!-- aangepaste R1269_LT10 geen pers opnemen in ber.pers tabel
Then bestaat er geen voorkomen in berpers tabel voor referentie 00000000-0000-0000-0000-123456789000 en srt lvg_bvgZoekPersoonOpAdres

!-- R1270_LT05
Then is er gearchiveerd met de volgende gegevens:
| veld      | waarde    |
| bsn       | 606417801 |
| richting       | 2 |
