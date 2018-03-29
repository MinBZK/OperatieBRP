Meta:
@status             Klaar
@usecase            SA.0.PA, SA.1.PA.CA, SA.1.PA.CI
@sleutelwoorden     Plaats afnemerindicatie, Archivering + Protocollering + Verzenden
@regels             PLAFNIND

Narrative:
Indien berichtarchivering wordt vastgelegd van een ingaand bericht,
dan wordt per in het bericht gerefereerde Persoon een voorkomen van Bericht \ Persoon vastgelegd:

R1269:
Ook als er in een ingaand bericht wordt gerefereerd aan een Persoon die niet bekend is in de BRP,
dan wordt er geen voorkomen van Bericht\Persoon aangemaakt
(de foutieve BSN zelf is nog wel terug te vinden in de vastgelegde berichtinhoud).

Scenario: 1.    Plaatsen afnemeridicatie met onbekende BSN
                LT: R1269_LT11, R1270_LT10, R2236_LT05
                Verwacht resultaat:
                1. Geen persoon vastgelegd in de BERPERS tabel
                2. Berichten wel gearchiveerd
                3. BSN terug te vinden in de vastgeledge berichtinhoud (Verzoek/antwoordbericht)

Given alle personen zijn verwijderd
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/Plaatsen_en_Verwijderen_AfnemerIndicatie/Requests/7._Plaats_Afnemerindicatie_Story_4.4.xml

Then heeft het antwoordbericht verwerking Foutief

And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                                |
| R1403     | Er is geen persoon gevonden met het opgegeven identiteitsnummer binnen uw autorisatie. |

When alle berichten zijn geleverd
Then zijn er geen berichten ontvangen

!-- R1269_LT11
Then heeft in het antwoordbericht 'burgerservicenummer' in 'identificatienummers' de waarde '606417801'
Then bestaat er geen voorkomen in berpers tabel voor referentie 00000000-0000-0000-0000-999999991240 en srt lvg_synRegistreerAfnemerindicatie

!-- R1270_LT10
Then is er gearchiveerd met de volgende gegevens:
| veld                  | waarde                               |
| srt                   | 41                                   |
| richting              | 2                                    |
| admhnd                | NULL                                 |
|-- data              --|-- <wordt gecheckt in aparte stap>  --|
| zendendepartij        | 2001                                 |
| zendendesysteem       | BRP                                  |
| ontvangendepartij     | 32002                                |
|-- referentienr      --|-- <wordt gecheckt in aparte stap>  --|
| crossreferentienr     | 00000000-0000-0000-0000-999999991240 |
|-- tsverzending      --|-- <wordt gecheckt in aparte stap>  --|
|-- tsontv            --|-- <wordt gecheckt in aparte stap>  --|
| verwerkingswijze      | NULL                                 |
| rol                   | NULL                                 |
| srtsynchronisatie     | NULL                                 |
|-- levsautorisatie   --|-- <wordt gecheckt in aparte stap>  --|
| dienst                | NULL                                 |
| verwerking            | 2                                    |
| bijhouding            | NULL                                 |
| hoogstemeldingsniveau | 5                                    |

Then is het synchrone verzoek correct gearchiveerd
Then tijdstipverzending in bericht is correct gearchiveerd
Then tijdstipontvangst is actueel
Then leveringautorisatie is gelijk in archief

Then bestaat er geen voorkomen in berpers tabel voor crossreferentie 00000000-0000-0000-0000-999999991240 en srt lvg_synRegistreerAfnemerindicatie_R

Then is er niet geprotocolleerd voor persoon 606417801

