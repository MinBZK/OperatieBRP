Meta:
@status             Klaar
@sleutelwoorden     Selectie met plaatsen afnemerindicatie

Narrative:
Ga na of er al een geldig voorkomen van Persoon \ Afnemerindicatie bestaat voor de combinatie van deze Persoon, deze Leveringsautorisatie en deze Partij.
Indien dat niet het geval is, vul dan de volgende rubrieken ter plaatsing van de afnemerindicatie:
Persoon \ Afnemerindicatie.Persoon = De betreffende Persoon
Persoon \ Afnemerindicatie.Partij = De Partij waarvoor de Dienst (in combinatie met de Toegang leveringsautorisatie) wordt geleverd
Persoon \ Afnemerindicatie.Leveringsautorisatie = De Leveringsautorisatie waarbinnen de Dienst wordt geleverd
Persoon \ Afnemerindicatie.Datum aanvang materiële periode = 'leeg'
Persoon \ Afnemerindicatie.Datum einde volgen= 'leeg'}

Scenario:   1. Selectie goedpad met plaatsing afnemerindicatie op persoon partij en leveringsautorisatie
            LT: R2588_LT02, R2692_LT02, R2667_LT01
            Uitwerking:
            Modelautorisatie inladen, check of de afnemerindicatie op de juiste partij wordt geplaatst
            check vulling van db op:
            Persoon \ Afnemerindicatie.Persoon = De betreffende Persoon
            Persoon \ Afnemerindicatie.Partij = De Partij waarvoor de Dienst (in combinatie met de Toegang leveringsautorisatie) wordt geleverd
            Persoon \ Afnemerindicatie.Leveringsautorisatie = De Leveringsautorisatie waarbinnen de Dienst wordt geleverd
            Persoon \ Afnemerindicatie.Datum aanvang materiële periode = 'leeg'
            Persoon \ Afnemerindicatie.Datum einde volgen= 'leeg'


Given alle selectie personen zijn verwijderd
And selectiepersonen uit bestand /LO3PL/R2286/Kim.xls

!-- preconditie afnemerindicatie Kim, inladen met andere leveringsautorisatie en andere partij, dus er kan voor andere autorisatie geplaatst worden
Given verzoek voor leveringsautorisatie 'SelectieMetPlaatsenAfnemerindicatieModelAut' en partij 'Gemeente Standaard2'
Given xml verzoek uit bestand /testcases/SL0PA_Selectiedienst_met_plaatsing_afnemerindicatie/Requests/3_Plaats_Afnemerindicatie_Kim_Gemeente_Standaard2.xml
Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide

!-- preconditie afnemerinindicatie Kim, zelfde partij andere leveringsautorisatie... als bovenstaande slaagt.

Given een selectierun met de volgende selectie taken:
|datplanning 	|status      | dienstSleutel
|vandaag       |Uitvoerbaar | ToegangSelectieMetPlaatsenVolledigBericht/DienstSelectieMetPlaatsenVolledigBericht

When start selectie run
And wacht tot selectie run gestart
And wacht maximaal 2 minuten tot selectie run klaar

Then zijn de volgende resultaat files aanwezig voor selectietaak met dienstsleutel 'ToegangSelectieMetPlaatsenVolledigBericht/DienstSelectieMetPlaatsenVolledigBericht' en datumuitvoer 'vandaag':
|type                         |aantal
|Resultaatset totalen         |==1

Then is er voor persoon met bsn 606417801 en leveringautorisatie SelectieMetPlaatsingAfnemerindicatieEnVolledigBericht en partij Gemeente Standaard en soortDienst SELECTIE een afnemerindicatie geplaatst

When alle berichten zijn geleverd

Then is er een volledigbericht ontvangen voor leveringsautorisatie SelectieMetPlaatsingAfnemerindicatieEnVolledigBericht

!-- Voor gemeente Standaard is een actueel en geldig voorkomen van persoon.afnemerindicatie
Then in autaut heeft select indag,dataanvmaterieleperiode,dateindevolgen from autaut.persafnemerindicatie where pers=(select id from kern.pers where bsn='606417801') AND levsautorisatie=(select id from autaut.levsautorisatie where naam='SelectieMetPlaatsingAfnemerindicatieEnVolledigBericht') AND partij=32002 de volgende gegevens:
| veld                      | waarde |
| indag                     | true   |
| dataanvmaterieleperiode   | NULL   |
| dateindevolgen            | NULL   |

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
Then is er voor leveringsautorisatie SelectieMetPlaatsingAfnemerindicatieEnVolledigBericht en partij Gemeente Standaard geprotocolleerd met de volgende gegevens:
| veld                        | waarde                     |
| bsn                         | 606417801                  |
| soortDienst                 | Selectie                   |
| soortSynchronisatie         | Volledigbericht            |
| dataanvmaterieleperioderes  | NULL                       |
| dateindematerieleperioderes | NULL                       |
| tsaanvformeleperioderes     | NULL                       |
| tseindeformeleperioderes    | NU                         |

!-- Controle op status wijziging R2692_LT02, controle op vullen TsREG voor nieuwe status en TsVerval voor oude status handmatig uitgevoerd
Then heeft de actuele status rij de volgende waarden:
|dienstSleutel                                                                      |status             |gewijzigddoor
|ToegangSelectieMetPlaatsenVolledigBericht/DienstSelectieMetPlaatsenVolledigBericht |SELECTIE_UITGEVOERD|Systeem
