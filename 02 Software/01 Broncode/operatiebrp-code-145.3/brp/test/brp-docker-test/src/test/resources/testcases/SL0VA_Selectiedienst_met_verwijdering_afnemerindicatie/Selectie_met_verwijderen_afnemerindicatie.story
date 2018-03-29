Meta:
@status             Klaar
@sleutelwoorden     Selectie met verwijdering afnemerindicatie

Narrative:

Indien bij de Dienst waarvoor geldt dat Dienst.Soort selectie is gelijk aan 'Selectiedienst met verwijdering afnemerindicatie'
met versturen van een volledig bericht
dan wordt de afnemerindicatie van personen binnen de selectie verwijderd en vervolgens een volledig bericht verstuurd.

Test overzicht:
| nr | indverzvolber? | Tot.pop bep | Afn. ind. aanwezig? | Nadere bijh.aard actueel? | Verstrekkingsbeperking? | Verwacht resultaat                        | Opgenomen in                                         |
| 1  | JA             | WAAR        | JA                  | JA                        | NEE                     | Afn. ind. verwijderd, bericht aan afnemer | Selectie_met_verwijderen_afnemerindicatie.story      |
| 2  | NEE            | WAAR        | JA                  | JA                        | NEE                     | Afn. ind. verwijderd, geen bericht        | 1.1_Selectie_Verwijder_Afnemerindicatie_R2591.story  |
| 3  | JA             | ONWAAR      | JA                  | JA                        | NEE                     | Afn. ind. niet verwijderd, geen bericht   | 2.1_Selectie_Verwijder_AfnInd_Pop_Bep.story          |
| 4  | JA             | WAAR        | NEE                 | JA                        | NEE                     | Afn. ind. niet verwijderd, geen bericht   | Verwijderen_afn_ind_bij_vervallen_afn_ind.story      |
| 5  | JA             | WAAR        | JA                  | NEE                       | NEE                     | Afn. ind. niet verwijderd, geen bericht   | Selectie_met_verwijderen_naderebijhoudingsaard.story |
| 6  | JA             | WAAR        | JA                  | JA                        | JA                      | Afn. ind. verwijderd, geen bericht        | 2.1_Selectie_Verwijder_AfnInd_Pop_Bep.story          |

Aanvullend scenario: afnemer indicatie met datum einde volgen in het verleden, in dit geval wordt er wel een volledigbericht aan de afnemer verwacht

Scenario:   1. Selectie met verwijdering afnemerindicatie goedpad
            LT: R2591_LT01, R2667_LT04, R2692_LT03
            Uitwerking:
            Persoon heeft al een afnmerindicatie op partij en leveringsautorisatie
            Persoon wordt wel opgenomen in resultaatset totalen
            Afnemerindicatie wordt verwijderd - check op vervallen afnemerindicatie
            Volledig bericht wordt verstuurd
            Status is aangepast van uitvoerbaar naar selectie uitgevoerd
            Protocolleren
            Archiveren

Given alle selectie personen zijn verwijderd
And selectiepersonen uit bestand /LO3PL/R2286/Kim.xls

!-- afnemerindicatie plaatsen voor Kim met gelijke partij en leveringsautoristatie
Given verzoek voor leveringsautorisatie 'SelectieMetVerwijderingAfnInd' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/SL0VA_Selectiedienst_met_verwijdering_afnemerindicatie/Requests/3_Plaats_Afnemerindicatie_Kim_Gemeente_Standaard.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide

Given een selectierun met de volgende selectie taken:
| datplanning | status      | dienstSleutel                           |
| vandaag     | Uitvoerbaar | selectiemetverwijderingafnemerindicatie |

When start selectie run
And wacht tot selectie run gestart
And wacht maximaal 5 minuten tot selectie run klaar

Then zijn de volgende resultaat files aanwezig voor selectietaak met dienstsleutel 'selectiemetverwijderingafnemerindicatie' en datumuitvoer 'vandaag':
| type                 | aantal |
| Resultaatset totalen | ==1    |

When alle berichten zijn geleverd
Then is er een volledigbericht ontvangen voor leveringsautorisatie SelectieMetVerwijderingAfnInd

Then heeft de actuele status rij de volgende waarden:
| dienstSleutel                           | status              | gewijzigddoor |
| selectiemetverwijderingafnemerindicatie | SELECTIE_UITGEVOERD | Systeem       |

!-- Voor gemeente Standaard is geen actueel en geldig voorkomen van persoon.afnemerindicatie
Then in autaut heeft select indag,dataanvmaterieleperiode,dateindevolgen from autaut.persafnemerindicatie where pers=(select id from kern.pers where bsn='606417801') AND levsautorisatie=(select id from autaut.levsautorisatie where naam='SelectieMetVerwijderingAfnInd') AND partij=32002 de volgende gegevens:
| veld  | waarde |
| indag | false  |

!-- check archivering van asynchrone bericht voor plaatsen door selectie
Then is er gearchiveerd met de volgende gegevens:
| veld                   | waarde                               |
| srt                    | 23                                   |
| richting               | 2                                    |
| admhnd                 | NULL                                 |
|-- data              -- | -- <wordt gecheckt in aparte stap> --|
| zendendepartij         | 2001                                 |
| zendendesysteem        | BRP                                  |
| ontvangendepartij      | 32002                                |
|-- referentienr      -- | -- <wordt gecheckt in aparte stap> --|
| crossreferentienr      | NULL                                 |
| tsontv                 | NULL                                 |
| verwerkingswijze       | NULL                                 |
| rol                    | 1                                    |
| srtsynchronisatie      | 2                                    |
|-- levsautorisatie   -- | -- <wordt gecheckt in aparte stap> --|
|-- levsautorisatie   -- | -- <wordt gecheckt in aparte stap> --|
| verwerking             | NULL                                 |
| bijhouding             | NULL                                 |
| hoogstemeldingsniveau  | NULL                                 |

!-- Then controleer dat asynchroon ontvangen berichten vanuit selectierun correct gearchiveerd zijn
Then referentienr is gelijk
Then tijdstipverzending in bericht is correct gearchiveerd
Then dienstid is gelijk in archief
Then leveringautorisatie is gelijk in archief


!-- check protocollering
Then is er voor leveringsautorisatie SelectieMetVerwijderingAfnInd en partij Gemeente Standaard geprotocolleerd met de volgende gegevens:
| veld                        | waarde          |
| bsn                         | 606417801       |
| soortDienst                 | Selectie        |
| soortSynchronisatie         | Volledigbericht |
| dataanvmaterieleperioderes  | NULL            |
| dateindematerieleperioderes | NULL            |
| tsaanvformeleperioderes     | NULL            |
| tseindeformeleperioderes    | NU              |
