Meta:

@status             Klaar
@usecase            SL.0.SS
@sleutelwoorden     Selectie

Narrative:
Peilmomenten zijn getest in Standaard Selecties
In deze story dat er bij het niet voldoen op een peilmoment er ook geen afnemerindicatie geplaatst wordt

Scenario: 1.    Historievorm = Geen, Peilmoment materieel resultaat is datum x (2015-12-31), Peilmoment Formeel resultaat is datum y (2015-12-31)
                LT: R2224_LT03
                Verwacht resultaat:
                - Utrecht als woonplaats op peilmoment, maar Uithoorn actueel
                - Dus voldoet aan populatiebeperking woonplaatsnaam is Uithoorn
                - Dus Anne Bakker in bericht met adres Utrecht op peilmoment
                Uitwerking:
                - Uithoorn sinds 2016-01-02
                - Den Haag sinds 2016-01-01 (naamOpenbareRuimte = Spui)
                - Utrecht sinds  2015-12-31
                - Oosterhout sinds 2010

!-- Selectie met historievorm 'Geen'
Given leveringsautorisatie uit aut/SelectiePopulatieBeperkingUithoorn
Given een selectierun met de volgende selectie taken:
|id |datplanning | status    |peilmommaterieelresultaat |peilmomformeelresultaat |dienstSleutel |
|1  |vandaag     | Uitvoerbaar   |20151231                  |2015-12-31T23:00:00Z   |ToegangA/DienstB |

Given persoonsbeelden uit specials:specials/Anne_Bakker_xls
Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|595891305|2008-12-31 T23:59:00Z

When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar
Then resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag' met '1' personen
Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:woonplaatsnaam[text()='Utrecht']' een node aanwezig in de selectieresultaat persoonbestanden

Then zijn er de volgende controlebestanden:
| selectieTaakId | aanwezig | aantalPersonen |
| 1              | nee      | -              |

Scenario: 2.    Historievorm = Geen, Peilmoment materieel resultaat is datum x (2015-12-31), Peilmoment Formeel resultaat is datum y (2015-12-31)
                LT: R2224_LT03
                Verwacht resultaat:
                - Utrecht als woonplaats op peilmoment, maar Uithoorn actueel
                - Dus voldoet aan NIET populatiebeperking woonplaatsnaam is Utecht
                - Dus Anne Bakker in bericht met adres Utrecht op peilmoment
                Uitwerking:
                - Uithoorn sinds 2016-01-02
                - Den Haag sinds 2016-01-01 (naamOpenbareRuimte = Spui)
                - Utrecht sinds  2015-12-31
                - Oosterhout sinds 2010

!-- Selectie met historievorm 'Geen'
Given leveringsautorisatie uit aut/SelectiePopulatieBeperkingUtrecht
Given een selectierun met de volgende selectie taken:
|id |datplanning | status    |peilmommaterieelresultaat |peilmomformeelresultaat |dienstSleutel |
|1  |vandaag     | Uitvoerbaar   |20151231                  |2015-12-31T23:00:00Z   |ToegangA/DienstB |

Given persoonsbeelden uit specials:specials/Anne_Bakker_xls
Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|595891305|2008-12-31 T23:59:00Z

When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar
Then resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag' met '0' personen

