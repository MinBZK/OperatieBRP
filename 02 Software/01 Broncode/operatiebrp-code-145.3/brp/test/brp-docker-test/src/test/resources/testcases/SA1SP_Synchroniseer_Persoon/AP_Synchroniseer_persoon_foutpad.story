Meta:
@status             Klaar
@usecase            AL.1.AB
@regels             R1268, R1269, R1270
@sleutelwoorden     Archiveer bericht



Narrative:
Als beheerder wil ik bij een binnenkomend bericht van soort lvg_synGeef dat deze wordt gearchiveerd
Na levering van het bericht
Wil ik dat het uitgaande bericht van soort lvg_synGeef wordt gearchiveerd


Scenario:   1. Synchroniseer persoon fout pad, check uitgaand bericht meerdere foutmeldingen juiste hoogste melding wordt gearchiveerd
            LT: R1268_LT05
            leveringsautorisatie en personen worden gebruikt uit bovenstaand scenario
            Logische testgevallen AL.1.AV Afhandelen Verzoek AL.1.AB: R1268_LT05
            Verwacht resultaat:
            1. R1268_LT05: Synchronisatie gearchiveerd Vulling voor uitgaand bericht met meerdere foutmeldingen is gearchiveerd met juiste hoogste melding
            2. Er is ook geen persoon gearchiveerd in de ber.pers tabel

Given alle personen zijn verwijderd

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van doelbinding' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/SA1SP_Synchroniseer_Persoon/Requests/4._Synchroniseer_Persoon_Story_2.1.xml

Then heeft het antwoordbericht verwerking Foutief
Then heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Fout'
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                                                                                |
| R1403 | Er is geen persoon gevonden met het opgegeven identiteitsnummer binnen uw autorisatie. |

Then is er gearchiveerd met de volgende gegevens:
| veld                  | waarde                               |
| srt                   | 65                                   |
| richting              | 2                                    |
| admhnd                | NULL                                 |
|-- data              --|-- <wordt gecheckt in aparte stap>  --|
| zendendepartij        | 2001                                 |
| zendendesysteem       | BRP                                  |
| ontvangendepartij     | 32002                                |
|-- referentienr      --|-- <wordt gecheckt in aparte stap>  --|
| crossreferentienr     | 00000000-0000-0000-0000-123456789456 |
|-- tsverzending      --|-- <wordt geccheckt in aparte stap> --|
| tsontv                | NULL                                 |
| verwerkingswijze      | NULL                                 |
| rol                   | NULL                                 |
| srtsynchronisatie     | NULL                                 |
|-- levsautorisatie   --|-- <wordt geccheckt in aparte stap> --|
| dienst                | NULL                                 |
| verwerking            | 2                                    |
| bijhouding            | NULL                                 |
| hoogstemeldingsniveau | 5                                    |

Then bestaat er een antwoordbericht voor referentie 00000000-0000-0000-0000-123456789456
Then referentienr is gelijk
Then tijdstipverzending in bericht is correct gearchiveerd
Then leveringautorisatie is gelijk in archief

Then bestaat er geen voorkomen in berpers tabel voor crossreferentie 00000000-0000-0000-0000-123456789456 en srt lvg_synGeefSynchronisatiePersoon_R


