Meta:

@status             Klaar
@usecase            SL.0.PA
@sleutelwoorden     Selectie met plaatsing afnemerindicatie

Narrative:
Peilmomenten zijn getest in Selecties met plaatsen afnemerindicatie
In deze story dat populatiebeperking van toepassing is op actuele beeld, en niet op peilmoment

Scenario: 1.    Historievorm = Geen, Peilmoment materieel resultaat is datum x (2015-12-31), Peilmoment Formeel resultaat is datum y (2015-12-31)
                LT: R2224_LT03
                Verwacht resultaat:
                - Utrecht als woonplaats op peilmoment, maar Uithoorn actueel
                - Dus voldoet aan populatiebeperking woonplaatsnaam is Uithoorn
                - Dus een afnemerindicatie geplaatst
                - Volledig bericht zonder filtering
                Uitwerking:
                - Uithoorn sinds 2016-01-02
                - Den Haag sinds 2016-01-01 (naamOpenbareRuimte = Spui)
                - Utrecht sinds  2015-12-31
                - Oosterhout sinds 2010

Given leveringsautorisatie uit aut/SelectiemetPlaatsenPopulatieBeperkingUithoorn
Given een selectierun met de volgende selectie taken:
|id |datplanning | status        |peilmommaterieelresultaat |peilmomformeelresultaat |dienstSleutel |
|1  |vandaag     | Uitvoerbaar   |20151231                  |2015-12-31T23:00:00Z   |ToegangA/DienstB |

Given persoonsbeelden uit specials:VolledigBericht/Anne_Bakker_Volledig_xls
When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar

Then is er voor persoon met bsn 595891305 en leveringautorisatie SelPopBepUithoorn en partij Gemeente Utrecht een afnemerindicatie geplaatst

!-- Volledig bericht zonder filtering
When het volledigbericht voor leveringsautorisatie SelPopBepUithoorn is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/VolledigBericht_Peilmoment.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 2.    Historievorm = Geen, Peilmoment materieel resultaat is datum x (2015-12-31), Peilmoment Formeel resultaat is datum y (2015-12-31)
                LT: R2224_LT03
                Verwacht resultaat:
                - Utrecht als woonplaats op peilmoment, maar Uithoorn actueel
                - Dus voldoet niet aan populatiebeperking woonplaatsnaam is Utrecht
                - Dus geen afnemerindicatie geplaatst
                Uitwerking:
                - Uithoorn sinds 2016-01-02
                - Den Haag sinds 2016-01-01 (naamOpenbareRuimte = Spui)
                - Utrecht sinds  2015-12-31
                - Oosterhout sinds 2010

Given leveringsautorisatie uit aut/SelectiemetPlaatsenPopulatieBeperkingUtrecht
Given een selectierun met de volgende selectie taken:
|id |datplanning | status        |peilmommaterieelresultaat |peilmomformeelresultaat |dienstSleutel |
|1  |vandaag     | Uitvoerbaar   |20151231                  |2015-12-31T23:00:00Z   |ToegangA/DienstB |

Given persoonsbeelden uit specials:VolledigBericht/Anne_Bakker_Volledig_xls
When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar

Then is er voor persoon met bsn 595891305 en leveringautorisatie SelPopBepUtrecht en partij Gemeente Utrecht geen afnemerindicatie geplaatst

Scenario: 3.    Historievorm = Geen, Peilmoment materieel resultaat is datum x (2015-12-31), Peilmoment Formeel resultaat is datum y (2015-12-31)
                LT: R2224_LT03
                Verwacht resultaat:
                - Utrecht als woonplaats op peilmoment, maar Uithoorn actueel
                - Dus voldoet aan selectiecriterium woonplaatsnaam is Uithoorn
                - Dus een afnemerindicatie geplaatst
                Uitwerking:
                - Uithoorn sinds 2016-01-02
                - Den Haag sinds 2016-01-01 (naamOpenbareRuimte = Spui)
                - Utrecht sinds  2015-12-31
                - Oosterhout sinds 2010

Given leveringsautorisatie uit aut/SelectiemetPlaatsenSelectiecriteriumUithoorn
Given een selectierun met de volgende selectie taken:
|id |datplanning | status        |peilmommaterieelresultaat |peilmomformeelresultaat |dienstSleutel |
|1  |vandaag     | Uitvoerbaar   |20151231                  |2015-12-31T23:00:00Z   |ToegangA/DienstB |

Given persoonsbeelden uit specials:VolledigBericht/Anne_Bakker_Volledig_xls
When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar

Then is er voor persoon met bsn 595891305 en leveringautorisatie SelectiecriteriumUithoorn en partij Gemeente Utrecht een afnemerindicatie geplaatst


Scenario: 4.    Historievorm = Geen, Peilmoment materieel resultaat is datum x (2015-12-31), Peilmoment Formeel resultaat is datum y (2015-12-31)
                LT: R2224_LT03
                Verwacht resultaat:
                - Utrecht als woonplaats op peilmoment, maar Uithoorn actueel
                - voldoet niet aan selectiecriterium woonplaatsnaam is Utrecht
                - Dus geen afnemerindicatie geplaatst
                Uitwerking:
                - Uithoorn sinds 2016-01-02
                - Den Haag sinds 2016-01-01 (naamOpenbareRuimte = Spui)
                - Utrecht sinds  2015-12-31
                - Oosterhout sinds 2010

Given leveringsautorisatie uit aut/SelectiemetPlaatsenSelectiecriteriumUtrecht
Given een selectierun met de volgende selectie taken:
|id |datplanning | status        |peilmommaterieelresultaat |peilmomformeelresultaat |dienstSleutel |
|1  |vandaag     | Uitvoerbaar   |20151231                  |2015-12-31T23:00:00Z   |ToegangA/DienstB |

Given persoonsbeelden uit specials:VolledigBericht/Anne_Bakker_Volledig_xls
When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar

Then is er voor persoon met bsn 595891305 en leveringautorisatie SelectiecriteriumUtrecht en partij Gemeente Utrecht geen afnemerindicatie geplaatst
