Meta:
@status             Klaar
@sleutelwoorden     Expressietaal

Narrative:
In deze story zijn scenario's opgenomen om de expressietaal af te testen mbt EN functie, AANTAL en OF functie.
Aantal wordt in PDT allen gebruikt in de adressen.soortcode dit zou niet voor kunnen komen dat dit 0 is, geen testgeval voor gemaakt.


Scenario:   1. Expressietaal testcases/Expressie_Taal_testen/autorisaties/Autorisatie_EN_OF_AANTAL
            LT:
            Expressie:
            bijhouding.bijhoudingspartij = 62601 EN
            (ER_IS(MAP(adressen, x, x.soort), v, NIET(v = B)) OF AANTAL(MAP(adressen, x, x.soort))= 0)
            EN bijhouding.nadere_bijhoudingsaard = 'A'
            Jan heeft partijcode 62601 en een soortcode ongelijk aan B nadere bijhoudingsaard = A.
            Verwacht resultaat: levering volledig bericht.

Given leveringsautorisatie uit autorisatie/Autorisatie_EN_OF_AANTAL
Given persoonsbeelden uit specials:specials/Jan_xls
When voor persoon 606417801 wordt de laatste handeling geleverd
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Autorisatie_EN_OF_AANTAL is ontvangen en wordt bekeken


Scenario:   2. Expressietaal testcases/Expressie_Taal_testen/autorisaties/Autorisatie_EN_OF_AANTAL
            LT:
            Expressie:
            bijhouding.bijhoudingspartij = 62601 EN
            (ER_IS(MAP(adressen, x, x.soort), v, NIET(v = B)) OF AANTAL(MAP(adressen, x, x.soort))= 0)
            EN bijhouding.nadere_bijhoudingsaard = 'A'
            Jan_nadere_bijhoudingsaard_Emigratie_xls heeft partijcode 62601 en een soortcode ongelijk aan B nadere bijhoudingsaard <> A.
            Verwacht resultaat: geen levering naderebijhoudingsaard = E

Given leveringsautorisatie uit autorisatie/Autorisatie_EN_OF_AANTAL
Given persoonsbeelden uit specials:specials/Jan_nadere_bijhoudingsaard_Emigratie_xls
When voor persoon 606417801 wordt de laatste handeling geleverd
Then is er geen synchronisatiebericht voor leveringsautorisatie Autorisatie_EN_OF_AANTAL


Scenario:   3. Expressietaal testcases/Expressie_Taal_testen/autorisaties/Autorisatie_EN_OF_AANTAL
            LT:
            Expressie:
            bijhouding.bijhoudingspartij = 62601 EN
            (ER_IS(MAP(adressen, x, x.soort), v, NIET(v = B)) OF AANTAL(MAP(adressen, x, x.soort))= 0)
            EN bijhouding.nadere_bijhoudingsaard = 'A'
            Jan heeft partijcode 62601 en een soortcode is gelijk aan B nadere bijhoudingsaard = A.
            Verwacht resultaat: geen levering soortcode = B

Given leveringsautorisatie uit autorisatie/Autorisatie_EN_OF_AANTAL
Given persoonsbeelden uit specials:specials/Jan_adres_soort_code_b_xls
When voor persoon 606417801 wordt de laatste handeling geleverd
Then is er geen synchronisatiebericht voor leveringsautorisatie Autorisatie_EN_OF_AANTAL


Scenario:   5. Expressietaal testcases/Expressie_Taal_testen/autorisaties/Autorisatie_EN_OF_AANTAL
            LT:
            Expressie:
            bijhouding.bijhoudingspartij = 62601 EN
            (ER_IS(MAP(adressen, x, x.soort), v, NIET(v = B)) OF AANTAL(MAP(adressen, x, x.soort))= 0)
            EN bijhouding.nadere_bijhoudingsaard = 'A'
            Jan heeft partijcode 34401 en een soortcode is ongelijk aan B  aantal adressen is 0 nadere bijhoudingsaard = A.
            Verwacht resultaat: geen levering bijhoudingspartij 34401

Given leveringsautorisatie uit autorisatie/Autorisatie_EN_OF_AANTAL
Given persoonsbeelden uit specials:specials/Jan_bijhoudingspartij_348_xls
When voor persoon 606417801 wordt de laatste handeling geleverd
Then is er geen synchronisatiebericht voor leveringsautorisatie Autorisatie_EN_OF_AANTAL

Scenario:  6. Lijst van voornamen van de kinderen van de persoon
           LT:
           Expressie: MAP(GerelateerdeKind.Persoon, k, k.SamengesteldeNaam.Voornamen) EIN {"Co","Sanne"}
           Verwacht resultaat: WAAR
Given persoonsbeelden uit specials:VolledigBericht/Anne_Bakker_Volledig_xls
Given leveringsautorisatie uit autorisatie/functie_MAP

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Expressietaal'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|595891305

Then heeft het antwoordbericht verwerking Geslaagd