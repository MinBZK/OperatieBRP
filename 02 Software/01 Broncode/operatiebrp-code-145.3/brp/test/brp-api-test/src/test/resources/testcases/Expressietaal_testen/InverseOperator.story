Meta:
@status Klaar

Narrative:
De testen zijn gemaakt om te controleren dat de expressie van logische of Numerieke inverse goed gaat.

!-- De volgende scenario's testen de of vergelijking middels expressie: NIET (Persoon.Nationaliteit.NationaliteitCode E= 0002)

Scenario: 1. Anne Bakker nationaliteit.code 1 en nationaliteit.code 67, expressie NIET (Persoon.Nationaliteit.NationaliteitCode E= 0002)
             LT:

Given leveringsautorisatie uit autorisatie/Expressietaal_logische_Inverse
Given persoonsbeelden uit specials:specials/Anne_Bakker_xls
When voor persoon 595891305 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie nadere populatiebeperking op niet nationaliteitscode is ontvangen en wordt bekeken
Then is er 1 bericht geleverd

Scenario: 2. Anne Bakker niet geleverd omkering van expressie scenario1
             LT:
            Verwachting dat er niet geleverd wordt.

Given leveringsautorisatie uit autorisatie/Expressietaal_logische_Inverse2
Given persoonsbeelden uit specials:specials/Anne_Bakker_xls
When voor persoon 595891305 wordt de laatste handeling geleverd
Then is er geen synchronisatiebericht voor leveringsautorisatie nadere populatiebeperking op niet nationaliteitscode2


Scenario: 3. Anne Bakker nationaliteit.code 1 en nationaliteit.code 67, expressie NIET (Persoon.Adres.Woonplaatsnaam E= ("Voorschoten")
             LT:

Given leveringsautorisatie uit autorisatie/Expressietaal_logische_Inverse_String
Given persoonsbeelden uit specials:specials/Anne_Bakker_xls
When voor persoon 595891305 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie nadere populatiebeperking op niet woonplaats is ontvangen en wordt bekeken
Then is er 1 bericht geleverd


Scenario: 4. Anne Bakker Numerieke inverse
             LT:

Given leveringsautorisatie uit autorisatie/Expressietaal_numerieke_Inverse
Given persoonsbeelden uit specials:specials/Anne_Bakker_xls
When voor persoon 595891305 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie nadere populatiebeperking op numerieke inverse is ontvangen en wordt bekeken
Then is er 1 bericht geleverd








