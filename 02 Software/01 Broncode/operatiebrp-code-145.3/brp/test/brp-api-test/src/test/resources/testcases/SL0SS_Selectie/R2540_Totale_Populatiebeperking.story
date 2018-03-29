Meta:
@status             Klaar
@sleutelwoorden     Selectie
@regels             R2540

Narrative:
Selecteer de Persoon(en) die voldoen aan de Totale populatiebeperking (R2059) en de Dienst.Nadere selectiecriterium

Jan
- Geboortedatum 1960-08-21
- Postcode      2252EB

Scenario:   1.      Selectie, Jan voldoet aan totale populatiebeperking
                    LT: R2540_LT01
                    Verwacht resultaat:
                    - Jan in het bericht

Given leveringsautorisatie uit aut/Selectie_Totale_Populatiebeperking_WAAR
Given een selectierun met de volgende selectie taken:
|id |datplanning 	|status      | dienstSleutel
|1  |vandaag 	    |Uitvoerbaar     | Selectie_Totale_Populatiebeperking_WAAR

Given persoonsbeelden uit specials:specials/Jan_xls
When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar
Then resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag' met '1' personen

Scenario:   2.      Selectie, nadere selectiecriterium onwaar
                    LT: R2540_LT02
                    Verwacht resultaat:
                    - Jan NIET in het bericht
!-- NadereSelcriterium                  : Persoon.Geboorte.Datum E= 1959/08/21
Given leveringsautorisatie uit aut/Selectie_Nadere_selectiecriterium_ONWAAR
Given een selectierun met de volgende selectie taken:
|id |datplanning 	|status      | dienstSleutel
|1  |vandaag 	    |Uitvoerbaar     | Selectie_Nadere_selectiecriterium_ONWAAR

Given persoonsbeelden uit specials:specials/Jan_xls
When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar
Then resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag' met '0' personen

Scenario:   3.      Selectie, dienstbundel nadere populatiebeperking onwaar
                    LT: R2540_LT03
                    Verwacht resultaat:
                    - Jan NIET in het bericht
!-- Nadere Populatie Beperking	        : Persoon.Geboorte.Datum > 1965/12/10
Given leveringsautorisatie uit aut/Selectie_Dienstbundel_Nadere_Populatiebeperking_ONWAAR
Given een selectierun met de volgende selectie taken:
|id |datplanning 	|status      | dienstSleutel
|1  |vandaag 	    |Uitvoerbaar     | Selectie_Dienstbundel_Nadere_Populatiebeperking_ONWAAR

Given persoonsbeelden uit specials:specials/Jan_xls
When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar
Then resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag' met '0' personen

Scenario:   4.      Selectie, Toegang leveringsautorisatie nadere populatiebeperking onwaar
                    LT: R2540_LT04
                    Verwacht resultaat:
                    - Jan NIET in het bericht
!-- Nadere Populatie Beperking	        : Persoon.Geboorte.Datum > 1961/12/10
Given leveringsautorisatie uit aut/Selectie_Toegang_Leveringsautorisatie_Bop_Bep_Onwaar
Given een selectierun met de volgende selectie taken:
|id |datplanning 	|status      | dienstSleutel
|1  |vandaag 	        |Uitvoerbaar     | Selectie_Toegang_Leveringsautorisatie_Bop_Bep_Onwaar

Given persoonsbeelden uit specials:specials/Jan_xls
When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar
Then resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag' met '0' personen

Scenario:   5.      Selectie, leveringsautorisatie populatiebeperking onwaar
                    LT: R2540_LT05
                    Verwacht resultaat:
                    - Jan NIET in het bericht
!-- Populatie Beperking			        : AANTAL(FILTER(Adressen, a, a.Postcode >= "2270AA" EN a.Postcode <= "2280ZZ")) > 0
Given leveringsautorisatie uit aut/Selectie_Leveringsautorisatie_pop_bep_Onwaar
Given een selectierun met de volgende selectie taken:
|id |datplanning 	|status      | dienstSleutel
|1  |vandaag 	        |Uitvoerbaar     | Selectie_Leveringsautorisatie_pop_bep_Onwaar

Given persoonsbeelden uit specials:specials/Jan_xls
When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar
Then resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag' met '0' personen
