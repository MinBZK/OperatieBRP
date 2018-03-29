Meta:
@status     Klaar

Narrative:
In deze story worden de wildcard expressies getest
postcode Jan = 2252EB
huisnummer Jan = 16
woonplaatsnaam = Voorschoten

Scenario: 2.1 Persoon.Adres.Postcode E=% {"2252*"}
              LT:

Given leveringsautorisatie uit autorisatie/Expressietaal_wildcard_string_postcode_1

Given persoonsbeelden uit specials:specials/Jan_xls
When voor persoon 606417801 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie nadere populatiebeperking op postcode is ontvangen en wordt bekeken
Then is er 1 bericht geleverd

Scenario: 2.2 Persoon.Adres.Postcode E=% {"2251*"}
              LT:

Given leveringsautorisatie uit autorisatie/Expressietaal_wildcard_string_postcode_2

Given persoonsbeelden uit specials:specials/Jan_xls
When voor persoon 606417801 wordt de laatste handeling geleverd

Then is er geen synchronisatiebericht gevonden

Scenario: 3.1 Persoon.Adres.Postcode E=% {"2252??"}
              LT:

Given leveringsautorisatie uit autorisatie/Expressietaal_wildcard_string_postcode_3

Given persoonsbeelden uit specials:specials/Jan_xls
When voor persoon 606417801 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie nadere populatiebeperking op postcode is ontvangen en wordt bekeken
Then is er 1 bericht geleverd

Scenario: 3.2 Persoon.Adres.Postcode E=% {"2252C?"}
              LT:

Given leveringsautorisatie uit autorisatie/Expressietaal_wildcard_string_postcode_4

Given persoonsbeelden uit specials:specials/Jan_xls
When voor persoon 606417801 wordt de laatste handeling geleverd

Then is er geen synchronisatiebericht gevonden


Scenario:   4.1    Persoon.Adres.Postcode
            LT:
!-- Nadere populatie beperking	: Persoon.Adres.Postcode EIN% {"2252*", "2253*"}

Given leveringsautorisatie uit autorisatie/Expressietaal_wildcard_string_postcode_5

Given persoonsbeelden uit specials:specials/Jan_xls
When voor persoon 606417801 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie nadere populatiebeperking op postcode is ontvangen en wordt bekeken
Then is er 1 bericht geleverd

Scenario:   4.2    Persoon.Adres.Postcode
            LT:
!-- Nadere populatie beperking	: Persoon.Adres.Postcode AIN% {"2252*", "2253*"}

Given leveringsautorisatie uit autorisatie/Expressietaal_wildcard_string_postcode_6

Given persoonsbeelden uit specials:specials/Jan_xls
When voor persoon 606417801 wordt de laatste handeling geleverd

Then is er 1 bericht geleverd

Scenario:   5.1    Persoon.Adres.Postcode
            LT:
!-- Nadere populatie beperking	: Persoon.Adres.Postcode EIN% {"2?52??", "2352??"}

Given leveringsautorisatie uit autorisatie/Expressietaal_wildcard_string_postcode_7

Given persoonsbeelden uit specials:specials/Jan_xls
When voor persoon 606417801 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie nadere populatiebeperking op postcode is ontvangen en wordt bekeken
Then is er 1 bericht geleverd

Scenario:   5.2    Persoon.Adres.Postcode
            LT:
!-- Nadere populatie beperking	: Persoon.Adres.Postcode AIN% {"2?52??", "2352??"}

Given leveringsautorisatie uit autorisatie/Expressietaal_wildcard_string_postcode_8

Given persoonsbeelden uit specials:specials/Jan_xls
When voor persoon 606417801 wordt de laatste handeling geleverd

Then is er 1 bericht geleverd

Scenario:   6. Persoon.Adres.Postcode
            LT:

!-- Nadere populatie beperking	: Persoon.Adres.Postcode EIN% {"2?52?", "2352??"}

Given leveringsautorisatie uit autorisatie/Expressietaal_wildcard_string_postcode_9

Given persoonsbeelden uit specials:specials/Jan_xls
When voor persoon 606417801 wordt de laatste handeling geleverd

Then is er geen synchronisatiebericht gevonden

Scenario: 7.1  Persoon.Adres.Woonplaatsnaam
        LT:

!-- Expressie Persoon.Adres.Woonplaatsnaam E=% {"V*"}

Given leveringsautorisatie uit autorisatie/Expressietaal_wildcard_string_woonplaatsnaam_1

Given persoonsbeelden uit specials:specials/Jan_xls
When voor persoon 606417801 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie nadere populatiebeperking op woonplaatsnaam is ontvangen en wordt bekeken
Then is er 1 bericht geleverd

Scenario: 7.2  Persoon.Adres.Woonplaatsnaam
        LT:
!-- Nadere populatie beperking	: Persoon.Adres.Woonplaatsnaam E=% {"Voors?hoten"}

Given leveringsautorisatie uit autorisatie/Expressietaal_wildcard_string_woonplaatsnaam_2

Given persoonsbeelden uit specials:specials/Jan_xls
When voor persoon 606417801 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie nadere populatiebeperking op woonplaatsnaam is ontvangen en wordt bekeken
Then is er 1 bericht geleverd

Scenario: 7.3  Persoon.Adres.Woonplaatsnaam
        LT:

!-- Nadere populatie beperking	: Persoon.Adres.Woonplaatsnaam E=% {"Voorschoten*"}

Given leveringsautorisatie uit autorisatie/Expressietaal_wildcard_string_woonplaatsnaam_3

Given persoonsbeelden uit specials:specials/Jan_xls
When voor persoon 606417801 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie nadere populatiebeperking op woonplaatsnaam is ontvangen en wordt bekeken
Then is er 1 bericht geleverd

Scenario: 7.4  Persoon.Adres.Woonplaatsnaam
            LT:
!-- Nadere populatie beperking	: Persoon.Adres.Woonplaatsnaam E=% {"*Voorschoten"}

Given leveringsautorisatie uit autorisatie/Expressietaal_wildcard_string_woonplaatsnaam_4

Given persoonsbeelden uit specials:specials/Jan_xls
When voor persoon 606417801 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie nadere populatiebeperking op woonplaatsnaam is ontvangen en wordt bekeken
Then is er 1 bericht geleverd