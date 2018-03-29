Meta:
@status             Klaar
@usecase            SV.0.GS
@sleutelwoorden     Stuf bg vertaal


Narrative:
stuf e2e test voor controle goedpad in e2e ctx. Ook controle op archivering.

Scenario:   1. Stuf verzoek voor vertaling
                LT: R1268_LT12, R1268_LT13, R1270_LT09
                Verwacht resultaat:
                - Archivering volgens R1268_LT12 voor inkomende bericht
                - Archivering volgens R1268_LT13 voor uitgaande bericht
                - R1270_LT09 Ber Pers is leeg


Given verzoek voor leveringsautorisatie 'StandaardStufAutorisatie' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/SVGS_Stuf/verzoek/StufVerzoekGoedPad.xml

!-- R1268_LT12 archivering voor inkomende bericht
Then is er gearchiveerd met de volgende gegevens:
| veld                  | waarde                               |
| srt                   | 157                                  |
| richting              | 1                                    |
| admhnd                | NULL                                 |
| zendendepartij        | 32002                                |
| zendendesysteem       | Gegevensdistributiesysteem           |
| ontvangendepartij     | NULL                                 |
| referentienr          | 88409eeb-1aa5-43fc-8614-43055123a165 |
| crossreferentienr     | NULL                                 |
| verwerkingswijze      | NULL                                 |
| rol                   | NULL                                 |
| srtsynchronisatie     | NULL                                 |
| verwerking            | NULL                                 |
| bijhouding            | NULL                                 |
| hoogstemeldingsniveau | NULL                                 |

Then is het synchrone verzoek correct gearchiveerd
Then tijdstipverzending in bericht is correct gearchiveerd
Then tijdstipontvangst is actueel
Then leveringautorisatie is gelijk in archief

Then heeft het antwoordbericht verwerking Geslaagd

!-- R1268_LT13 archivering voor uitgaande bericht
Then is er gearchiveerd met de volgende gegevens:
| veld                  | waarde                               |
| srt                   | 158                                  |
| richting              | 2                                    |
| admhnd                | NULL                                 |
| zendendepartij        | 2001                                 |
| zendendesysteem       | BRP                                  |
| ontvangendepartij     | 32002                                |
| crossreferentienr     | 88409eeb-1aa5-43fc-8614-43055123a165 |
| verwerkingswijze      | NULL                                 |
| rol                   | NULL                                 |
| srtsynchronisatie     | NULL                                 |
| verwerking            | 1                                    |
| bijhouding            | NULL                                 |
| hoogstemeldingsniveau | 1                                    |
| tsontv                | NULL                                 |


Then bestaat er een antwoordbericht voor referentie 88409eeb-1aa5-43fc-8614-43055123a165
Then referentienr is gelijk
Then tijdstipverzending in bericht is correct gearchiveerd

!-- R1270_LT09
Then bestaat er geen voorkomen in berpers tabel voor crossreferentie 88409eeb-1aa5-43fc-8614-43055123a165 en srt stv_stvGeefStufbgBericht_R
