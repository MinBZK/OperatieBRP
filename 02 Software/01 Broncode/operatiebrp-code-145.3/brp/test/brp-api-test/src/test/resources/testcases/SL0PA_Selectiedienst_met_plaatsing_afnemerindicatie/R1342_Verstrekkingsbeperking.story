Meta:

@status             Klaar
@usecase            SL.0.US
@regels             R1983
@sleutelwoorden     Selectie met afnemerindicatie

Narrative:


Een Persoon 'heeft een verstrekkingsbeperking voor een Partij' als een verstrekkingsbeperking voor die Partij mogelijk is en (er sprake is van een volledige verstrekkingsbeperking of een specifieke verstrekkingsbeperking voor die Partij):

Partij.Verstrekkingsbeperking mogelijk? = 'Ja'
EN
(
Persoon.Volledige verstrekkingsbeperking? = 'Ja'
OF
er bestaat bij de Persoon een voorkomen van Persoon \ Verstrekkingsbeperking met Partij gelijk aan de betreffende Partij

Scenario: 1.    Selectie voor persoon met volledige verstrekkingsbeperking
                LT: R1342_LT01, R1983_LT23
                Verwacht resultaat:
                - Selectie uitgevoerd
                - 0 personen gevonden
                Uitwerking:
                - Op hoofdpersoon Elisa staat een verstrekkingsbeperking, zij wordt dus niet geselecteerd


Given leveringsautorisatie uit aut/SelectieAfnemerindicatieVerstrekkingsbeperking
Given een selectierun met de volgende selectie taken:
|id |datplanning | status               | dienstSleutel                                                 |
|1  |vandaag      | Uitvoerbaar            | SelectieAfnemerindicatieVerstrekkingsbeperking |

Given persoonsbeelden uit specials:VerstrekkingsBeperking/PersoonKrijgtVerstrekkingsBeperking_xls
When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar
Then is er voor persoon met bsn 270433417 en leveringautorisatie SelectieLeveringautorisatieVerstrekkingsbeperking en partij KUC033-PartijVerstrekkingsbeperking geen afnemerindicatie geplaatst


Scenario: 2.    Selectie voor persoon met verstrekkingsbeperking op partij aanwezig
                LT: R1342_LT02, R1983_LT24
                Verwacht resultaat:
                - Selectie uitgevoerd
                - 0 personen gevonden

Given leveringsautorisatie uit aut/SelectieAfnemerindicatieVerstrekkingsbeperkingOpPartijMogelijk
Given een selectierun met de volgende selectie taken:
|id |datplanning | status               | dienstSleutel                                                 |
|1  |vandaag      | Uitvoerbaar            | SelectieAfnemerindicatieVerstrekkingsbeperkingOpPartijMogelijk |

Given persoonsbeelden uit BIJHOUDING:VZIG04C20T20/Specifieke_verstrekkingsbeperking_Partij/dbstate002
When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar
Then is er voor persoon met bsn 771168585 en leveringautorisatie SelectieVerstrekkingsbeperkingOpPartijMogelijk en partij Stichting Interkerkelijke Ledenadministratie geen afnemerindicatie geplaatst

Scenario: 3.    Selectie voor persoon met volledige verstrekkingsbeperking, maar Partij.Verstrekkingsbeperking mogelijk? = Nee
                LT: R1342_LT03
                Verwacht resultaat:
                - 1 persoon gevonden


Given leveringsautorisatie uit aut/SelectieAfnemerindicatieVerstrekkingsbeperkingNietMogelijk
Given een selectierun met de volgende selectie taken:
|id |datplanning | status               | dienstSleutel                                                 |
|1  |vandaag      | Uitvoerbaar            | SelectieAfnemerindicatieVerstrekkingsbeperkingNietMogelijk |

Given persoonsbeelden uit specials:VerstrekkingsBeperking/PersoonKrijgtVerstrekkingsBeperking_xls
When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar
Then is er voor persoon met bsn 270433417 en leveringautorisatie SelectieLeveringautorisatieVerstrekkingsbeperkingNietMogelijk en partij Gemeente Utrecht een afnemerindicatie geplaatst
When het volledigbericht voor leveringsautorisatie SelectieLeveringautorisatieVerstrekkingsbeperkingNietMogelijk is ontvangen en wordt bekeken

Scenario:   4. Volledig bericht na succesvol plaatsing afnemerindicatie, Geen verstrekkingsbeperking
            LT: R1342_LT04, R1983_LT22
            Succesvol plaatsen van een afnemerindicatie bij een ingeschreven persoon, psuedo personen geen afnemerindicatie geplaatst
            Resultaat:
            Voor Anne Bakker wordt succesvol een afnemerindicatie geplaatst en er wordt een volledig bericht verstuurd.


Given leveringsautorisatie uit aut/SelectieAutPlaatsAfnemerindicatie
Given een selectierun met de volgende selectie taken:
| id | datplanning | status  | dienstSleutel                                                 |
| 1  | vandaag     | Uitvoerbaar | SelectieAutPlaatsAfnemerindicatie |

Given persoonsbeelden uit specials:VolledigBericht/Anne_Bakker_Volledig_xls
When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar

Then is het totalenbestand voor selectietaak '1' en datumplanning 'vandaag' gelijk aan 'expecteds/expected_R1342_scenario4_resultaatset_totalen.xml'
And is er voor persoon met bsn 595891305 en leveringautorisatie SelectieLeveringautorisatie1 en partij Gemeente Utrecht een afnemerindicatie geplaatst
