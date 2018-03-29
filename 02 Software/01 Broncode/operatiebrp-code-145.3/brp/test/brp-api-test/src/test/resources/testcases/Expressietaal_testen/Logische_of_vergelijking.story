Meta:
@status Klaar

Narrative:
De testen zijn gemaakt om te controleren dat de expressie van logische of vergelijking goed gaat.

!-- De volgende scenario's testen de of vergelijking middels expressie: persoon.nationaliteit.code EIN {1, 2}

Scenario: 1. Anne Bakker nationaliteit.code 1 en nationaliteit.code 67, expressie Persoon.Nationaliteit.NationaliteitCode EIN {0001,0067}
             LT:

Given leveringsautorisatie uit autorisatie/Expressietaal_of_vergelijking_nationaliteit_beide_correct

Given persoonsbeelden uit specials:specials/Anne_Bakker_xls
When voor persoon 595891305 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie nadere populatiebeperking op nationaliteit beide correct is ontvangen en wordt bekeken
Then is er 1 bericht geleverd

Scenario: 2. Anne Bakker nationaliteit.code 1 en nationaliteit.code 67, expressie Persoon.Nationaliteit.NationaliteitCode EIN {0001,0068}
             LT:

Given leveringsautorisatie uit autorisatie/Expressietaal_of_vergelijking_nationaliteit_1_correct

Given persoonsbeelden uit specials:specials/Anne_Bakker_xls
When voor persoon 595891305 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie nadere populatiebeperking op nationaliteit 1 correct is ontvangen en wordt bekeken
Then is er 1 bericht geleverd

Scenario: 3. Anne Bakker nationaliteit.code 1 en nationaliteit.code 67, expressie Persoon.Nationaliteit.NationaliteitCode EIN {0002,0068}
             LT:

Given leveringsautorisatie uit autorisatie/Expressietaal_of_vergelijking_nationaliteit_niet_correct

Given persoonsbeelden uit specials:specials/Anne_Bakker_xls
When voor persoon 595891305 wordt de laatste handeling geleverd

Then is er geen synchronisatiebericht gevonden

!-- De volgende scenario's testen de EN vergelijking middels expressie (persoon.nationaliteit.code E= 1) EN (persoon.nationaliteit.code E= 2)

Scenario: 4. Anne Bakker nationaliteit.code 1 en nationaliteit.code 67, expressie (Persoon.Nationaliteit.NationaliteitCode E= 0001) EN (Persoon.Nationaliteit.NationaliteitCode E= 0067)
             LT:

Given leveringsautorisatie uit autorisatie/Expressietaal_EN_vergelijking_nationaliteit_beide_correct

Given persoonsbeelden uit specials:specials/Anne_Bakker_xls
When voor persoon 595891305 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie nadere populatiebeperking op nationaliteit beide correct is ontvangen en wordt bekeken
Then is er 1 bericht geleverd

Scenario: 5. Anne Bakker nationaliteit.code 1 en nationaliteit.code 67, expressie (Persoon.Nationaliteit.NationaliteitCode E= 0001) EN (Persoon.Nationaliteit.NationaliteitCode E= 0068)
             LT:

Given leveringsautorisatie uit autorisatie/Expressietaal_EN_vergelijking_nationaliteit_1_correct

Given persoonsbeelden uit specials:specials/Anne_Bakker_xls
When voor persoon 595891305 wordt de laatste handeling geleverd

Then is er geen synchronisatiebericht gevonden

Scenario: 6. Anne Bakker nationaliteit.code 1 en nationaliteit.code 67, expressie (Persoon.Nationaliteit.NationaliteitCode E= 0002) EN (Persoon.Nationaliteit.NationaliteitCode E= 0068)
             LT:

Given leveringsautorisatie uit autorisatie/Expressietaal_EN_vergelijking_nationaliteit_niet_correct

Given persoonsbeelden uit specials:specials/Anne_Bakker_xls
When voor persoon 595891305 wordt de laatste handeling geleverd

Then is er geen synchronisatiebericht gevonden

!-- De volgende scenario's testen de EN vergelijking middels expressie: persoon.nationaliteit.code AIN {1, 2}

Scenario: 7. Anne Bakker nationaliteit.code 1 en nationaliteit.code 67, expressie Persoon.Nationaliteit.NationaliteitCode AIN {0001,0067}
             LT:

Given leveringsautorisatie uit autorisatie/Expressietaal_EN_vergelijking_nationaliteit_beide_correct_AIN

Given persoonsbeelden uit specials:specials/Anne_Bakker_xls
When voor persoon 595891305 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie nadere populatiebeperking op nationaliteit beide correct AIN is ontvangen en wordt bekeken
Then is er 1 bericht geleverd

Scenario: 8. Anne Bakker nationaliteit.code 1 en nationaliteit.code 67, expressie Persoon.Nationaliteit.NationaliteitCode AIN {0001,0068}
             LT:

Given leveringsautorisatie uit autorisatie/Expressietaal_EN_vergelijking_nationaliteit_1_correct_AIN

Given persoonsbeelden uit specials:specials/Anne_Bakker_xls
When voor persoon 595891305 wordt de laatste handeling geleverd

Then is er geen synchronisatiebericht gevonden

Scenario: 9. Anne Bakker nationaliteit.code 1 en nationaliteit.code 67, expressie Persoon.Nationaliteit.NationaliteitCode AIN {0002,0068}
             LT:

Given leveringsautorisatie uit autorisatie/Expressietaal_EN_vergelijking_nationaliteit_niet_correct_AIN

Given persoonsbeelden uit specials:specials/Anne_Bakker_xls
When voor persoon 595891305 wordt de laatste handeling geleverd

Then is er geen synchronisatiebericht gevonden