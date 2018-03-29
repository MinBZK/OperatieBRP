Meta:
@status             Klaar
@sleutelwoorden     Expressietaal

Narrative:
In deze story zijn scenario's opgenomen om de expressietaal af te testen mbt EN functie, AANTAL en OF functie.
Aantal wordt in PDT allen gebruikt in de adressen.soortcode dit zou niet voor kunnen komen dat dit 0 is, geen testgeval voor gemaakt.


Scenario:   1. Expressietaal OF nationaliteitscode
            LT:
            Expressie:
            Jan heeft partijcode 62601 zit in de autorisatie en nadere bijhoudingsaard = A en geboortedatum 1960.
            Verwacht resultaat: levering volledig bericht.


Given leveringsautorisatie uit autorisatie/Expressietaal_OF
Given persoonsbeelden uit specials:specials/Anne_Bakker_xls
When voor persoon 595891305 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie nadere populatiebeperking op nationaliteit of is ontvangen en wordt bekeken
Then is er 1 bericht geleverd

