Meta:
@status             Klaar
@usecase            BV.0.GM
@regels             R1266, R1268, R1269, R1270
@sleutelwoorden     Geef medebewoners

Narrative:
Testen op archiveringsregels en protocoleringsregels

Scenario: 1.    Jan wordt gezocht op adresgegevens
                LT: R1268_LT01, R1268_LT02, R1269_LT13, R1270_LT03, R1613_LT05, R1617_LT08, R1618_LT04, R1619_LT06, R1620_LT03, R1266_LT09
                Verwacht resultaat:
                - Correct gearchiveerd

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls
Given verzoek voor leveringsautorisatie 'GeefMedebewoners' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV0GM_Geef_Medebewoners/verzoek/10.2_Geef_medebewoners.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide

!-- R1266_LT09 Controle op responsebericht
Then heeft in het antwoordbericht 'zendendePartij' in 'stuurgegevens' de waarde '199903'
Then heeft in het antwoordbericht 'zendendeSysteem' in 'stuurgegevens' de waarde 'BRP'
Then is in antwoordbericht de aanwezigheid van 'referentienummer' in 'stuurgegevens' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'crossReferentienummer' in 'stuurgegevens' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'tijdstipVerzending' in 'stuurgegevens' nummer 1 ja

!-- Ingaand bericht wordt gearchiveerd R1268_LT01
Then is er gearchiveerd met de volgende gegevens:
| veld                   | waarde                               |
| srt                    | 73                                   |
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


Then tijdstipverzending in bericht is correct gearchiveerd
Then tijdstipontvangst is actueel
Then leveringautorisatie is gelijk in archief
Then dienstid is gelijk in archief

!-- Verzonden uitgaand bericht wordt gearchiveerd R1268_LT02
Then is er gearchiveerd met de volgende gegevens:
| veld                   | waarde                               |
| srt                    | 74                                   |
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

!-- aangepaste R1269_LT13 geen pers opnemen in ber.pers tabel
Then bestaat er geen voorkomen in berpers tabel voor referentie 00000000-0000-0000-0000-123456789000 en srt lvg_bvgGeefMedebewoners

!-- R1270_LT03
Then is er gearchiveerd met de volgende gegevens:
| veld     | waarde    |
| bsn      | 606417801 |
| richting | 2         |

!-- Protocolering
!-- R1613_LT05            Leveringsaantekening.Toegang leveringsautorisatie = Toegang leveringsautorisatie.ID waarvoor verstrekt wordt
!-- R1613_LT03            Leveringsaantekening.Dienst = Dienst.ID van de geleverde Dienst (volgt uit opgegeven de parameters van het verzoekbericht)
!-- R1613_LT03            Leveringsaantekening.Datum/tijd klaarzetten levering = Datum\tijd systeem van ophalen gegevens(Persoon en Autorisatie) uit de database
!-- R1613_LT03            Leveringsaantekening.Datum materieel selectie = 'leeg'
!-- R1617_LT10            Leveringsaantekening.Datum aanvang materiële periode resultaat = Peilmoment leeg, dus systeemdatum
!-- R1618_LT04            Leveringsaantekening.Datum einde materiële periode resultaat = systeemdatum + 1 dag
!-- R1619_LT06            Leveringsaantekening.Datum/tijd aanvang formele periode resultaat = 'leeg'
!-- R1620_LT03            Leveringsaantekening.Datum/tijd einde formele periode resultaat = Datum\tijd systeem van ophalen gegevens
!-- R1613_LT05            Leveringsaantekening.Administratieve handeling = 'leeg'
!-- R1613_LT05            Leveringsaantekening.Soort synchronisatie = 'leeg'
!-- R1613_LT05            Leveringsaantekening.Scope patroon = 'leeg'

!-- R1613_LT04
Then is er voor leveringsautorisatie GeefMedebewoners en partij Gemeente Standaard geprotocolleerd met de volgende gegevens:
| veld                        | waarde                        |
| bsn                         | 606417801                     |
| admhnd                      | NULL                          |
| srtsynchronisatie           | NULL                          |
| scopepatroon                | NULL                          |
| soortDienst                 | Geef medebewoners van persoon |
| dataanvmaterieleperioderes  | VANDAAG                       |
| dateindematerieleperioderes | MORGEN                        |
| tsaanvformeleperioderes     | NULL                          |
| tseindeformeleperioderes    | NU                            |

Scenario: 2.    Protocolering van geef medebewoners met peilmoment 2012-01-01
                LT: R1613_LT05, R1617_LT10 , R1618_LT05, R1619_LT06, R1620_LT03
                Verwacht resultaat:
                - zie onderstaand
!-- Protocolering
!-- R1613_LT05            Leveringsaantekening.Toegang leveringsautorisatie = Toegang leveringsautorisatie.ID waarvoor verstrekt wordt
!-- R1613_LT03            Leveringsaantekening.Dienst = Dienst.ID van de geleverde Dienst (volgt uit opgegeven de parameters van het verzoekbericht)
!-- R1613_LT03            Leveringsaantekening.Datum/tijd klaarzetten levering = Datum\tijd systeem van ophalen gegevens(Persoon en Autorisatie) uit de database
!-- R1613_LT03            Leveringsaantekening.Datum materieel selectie = 'leeg'
!-- R1617_LT10            Leveringsaantekening.Datum aanvang materiële periode resultaat = Peilmoment materieel 2012-01-01
!-- R1618_LT05            Leveringsaantekening.Datum einde materiële periode resultaat = peilmoment + 1 dag
!-- R1619_LT06            Leveringsaantekening.Datum/tijd aanvang formele periode resultaat = 'leeg'
!-- R1620_LT03            Leveringsaantekening.Datum/tijd einde formele periode resultaat = Datum\tijd systeem van ophalen gegevens
!-- R1613_LT05            Leveringsaantekening.Administratieve handeling = 'leeg'
!-- R1613_LT05            Leveringsaantekening.Soort synchronisatie = 'leeg'
!-- R1613_LT05            Leveringsaantekening.Scope patroon = 'leeg'

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'GeefMedebewoners' en partij 'Gemeente Standaard'

Given xml verzoek uit bestand /testcases/BV0GM_Geef_Medebewoners/verzoek/10.3_Geef_medebewoners.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide


!-- R1613_LT04
Then is er voor leveringsautorisatie GeefMedebewoners en partij Gemeente Standaard geprotocolleerd met de volgende gegevens:
| veld                        | waarde                        |
| bsn                         | 606417801                     |
| tsaanvformeleperioderes     | NULL                          |
| admhnd                      | NULL                          |
| srtsynchronisatie           | NULL                          |
| scopepatroon                | NULL                          |
| soortDienst                 | Geef medebewoners van persoon |
| dataanvmaterieleperioderes  | 20120101                      |
| dateindematerieleperioderes | 20120102                      |
| tseindeformeleperioderes    | NU                            |
