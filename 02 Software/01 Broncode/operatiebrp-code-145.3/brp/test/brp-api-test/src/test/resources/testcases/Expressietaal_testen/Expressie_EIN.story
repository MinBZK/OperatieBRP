Meta:
@status             Klaar
@sleutelwoorden     Expressietaal

Narrative:
In deze story zijn scenario's opgenomen om de expressietaal af te testen mbt EN functie, AANTAL en OF functie.
Aantal wordt in PDT allen gebruikt in de adressen.soortcode dit zou niet voor kunnen komen dat dit 0 is, geen testgeval voor gemaakt.


Scenario:   1. Expressietaal EIN bijhoudingspartij en geboortedatum > 19
            LT:
            Expressie:
            Jan heeft partijcode 62601 zit in de autorisatie en nadere bijhoudingsaard = A en geboortedatum 1960.
            Verwacht resultaat: levering volledig bericht.

Given leveringsautorisatie uit autorisatie/Expressietaal_EIN
Given persoonsbeelden uit specials:specials/Jan_xls
When voor persoon 606417801 wordt de laatste handeling geleverd
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Expressietaal_EIN is ontvangen en wordt bekeken


Scenario:   2. Expressietaal EIN bijhoudingspartij en geboortedatum > 19
            LT:
            Expressie:
            Jan heeft partijcode 34401 zit NIET in de autorisatie en nadere bijhoudingsaard = A en geboortedatum 1960.
            Verwacht resultaat: geen levering volledig bericht.

Given leveringsautorisatie uit autorisatie/Expressietaal_EIN
Given persoonsbeelden uit specials:specials/Jan_bijhoudingspartij_348_xls
When voor persoon 606417801 wordt de laatste handeling geleverd
Then is er geen synchronisatiebericht gevonden

