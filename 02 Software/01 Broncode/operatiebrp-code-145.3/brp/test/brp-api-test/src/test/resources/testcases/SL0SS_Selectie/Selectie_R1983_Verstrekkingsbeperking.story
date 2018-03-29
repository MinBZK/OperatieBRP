Meta:

@status             Klaar
@usecase            SL.0.US
@regels             R1983
@sleutelwoorden     Selectie

Narrative:
Indien er sprake is van een 'verstrekkingsbeperking bij de Persoon voor ontvangende partij dan mag die Persoon in principe niet geleverd worden als zoekresultaat, bevragingsresultaat, selectieresultaat of in een spontaan bericht van het systeem.

Hiervan is sprake als Persoon Niet voldoet aan 'Persoon heeft een verstrekkingsbeperking voor Partij' (R1342).

In de volgende situaties is er echter sprake van een uitzondering en mag de Dienst wel worden geleverd:
Mutatielevering op basis van afnemerindicatie (hier dient de afnemer vooralsnog zelf zijn indicatie te verwijderen; bovendien moet hij op de hoogte worden gebracht van het instellen van de verstrekkingsbeperking)
Diensten die de afnemerindicatie verwijderen (daarbij wordt ook geen / minimale persoonsinformatie geleverd): Verwijdering afnemerindicatie en 'Selectie met verwijdering afnemerindicatie'.

Noot: De dienst Mutatielevering op basis van doelbinding ondersteunen we niet voor afnemers waarbij een verstrekkingsbeperking van toepassing kan zijn. Ook bij deze dienst hoeft deze melding dus niet af te gaan.

Scenario: 1.    Selectie voor persoon met volledige verstrekkingsbeperking
                LT: R1342_LT01, R1983_LT20
                Verwacht resultaat:
                - Selectie uitgevoerd
                - 0 personen gevonden
                Uitwerking:
                - Op hoofdpersoon Elisa staat een verstrekkingsbeperking, zij wordt dus niet geselecteerd


Given leveringsautorisatie uit aut/SelectieAutWaarVerstrekkingsbeperking
Given een selectierun met de volgende selectie taken:
|id |datplanning | status               | dienstSleutel                                                 |
|1  |vandaag      | Uitvoerbaar            | SelectieAutWaarVerstrekkingsbeperking |

Given persoonsbeelden uit specials:VerstrekkingsBeperking/PersoonKrijgtVerstrekkingsBeperking_xls
When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar
Then resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag' met '0' personen

Scenario: 2.    Selectie voor persoon met verstrekkingsbeperking op partij aanwezig
                LT: R1342_LT02, R1983_LT21
                Verwacht resultaat:
                - Selectie uitgevoerd
                - 0 personen gevonden


Given leveringsautorisatie uit aut/SelectieVerstrekkingsbeperkingOpPartijMogelijk
Given een selectierun met de volgende selectie taken:
|id |datplanning | status               | dienstSleutel                                                 |
|1  |vandaag      | Uitvoerbaar            | SelectieVerstrekkingsbeperkingOpPartijMogelijk |

Given persoonsbeelden uit BIJHOUDING:VZIG04C20T20/Specifieke_verstrekkingsbeperking_Partij/dbstate002
When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar
Then resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag' met '0' personen

Scenario: 3.    Selectie voor persoon met volledige verstrekkingsbeperking, maar Partij.Verstrekkingsbeperking mogelijk? = Nee
                LT: R1342_LT03
                Verwacht resultaat:
                - 1 persoon gevonden


Given leveringsautorisatie uit aut/SelectieAutWaarVerstrekkingsbeperkingNietMogelijk
Given een selectierun met de volgende selectie taken:
|id |datplanning | status               | dienstSleutel                                                 |
|1  |vandaag      | Uitvoerbaar            | SelectieAutWaarVerstrekkingsbeperkingNietMogelijk |

Given persoonsbeelden uit specials:VerstrekkingsBeperking/PersoonKrijgtVerstrekkingsBeperking_xls
When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar
Then resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag' met '1' personen

Scenario: 4.    Selectie voor persoon zonder verstrekkingsbeperking
                LT: R1342_LT04, R1983_LT19,
                Verwacht resultaat:
                - Selectie uitgevoerd
                - 1 personen gevonden
                Uitwerking:
                - Op hoofdpersoon Elisa staat geen verstrekkingsbeperking, zij wordt dus geselecteerd

Given leveringsautorisatie uit aut/SelectieAutWaarVerstrekkingsbeperking
Given een selectierun met de volgende selectie taken:
|id |datplanning | status               | dienstSleutel                                                 |
|1  |vandaag      | Uitvoerbaar            | SelectieAutWaarVerstrekkingsbeperking |

Given persoonsbeelden uit specials:VerstrekkingsBeperking/PersoonVerstrekkingsBeperkingVervalt_xls
When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar
Then resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag' met '1' personen
