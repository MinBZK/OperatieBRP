Meta:
@status Klaar

Narrative:
De story test op expressies op een boolean afnemerindicatie

Scenario: 1.1 Expressie Persoon.Indicatie.OnderCuratele E=WAAR
            LT:

Given leveringsautorisatie uit autorisatie/Expressietaal_op_boolean_waar

Given persoonsbeelden uit specials:specials/Jan_xls
When voor persoon 606417801 wordt de laatste handeling geleverd

Then is er geen synchronisatiebericht gevonden

Scenario: 1.2 Expressie Persoon.Indicatie.OnderCuratele E=WAAR
            LT:

Given leveringsautorisatie uit autorisatie/Expressietaal_op_boolean_waar

Given persoonsbeelden uit specials:specials/Jan_R1805_xls
When voor persoon 606417801 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie nadere populatiebeperking op boolean waar is ontvangen en wordt bekeken
Then is er 1 bericht geleverd


Scenario: 2.1 Expressie Persoon.Indicatie.OnderCuratele E=ONWAAR
            LT:

Given leveringsautorisatie uit autorisatie/Expressietaal_op_boolean_onwaar

Given persoonsbeelden uit specials:specials/Jan_R1805_xls
When voor persoon 606417801 wordt de laatste handeling geleverd

Then is er geen synchronisatiebericht gevonden

!-- Expressie op KNV omdat de boolean niet gevuld wordt
Scenario: 2.2 Expressie KNV(Persoon.Indicatie.OnderCuratele.Waarde)
            LT:

Given leveringsautorisatie uit autorisatie/Expressietaal_op_boolean_afwezig

Given persoonsbeelden uit specials:specials/Jan_xls
When voor persoon 606417801 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie nadere populatiebeperking op boolean afwezig is ontvangen en wordt bekeken
Then is er 1 bericht geleverd

!-- Test om te laten zien dat expressie onwaar een ander resultaat geeft dan KNV
Scenario: 2.3 Expressie Persoon.Indicatie.OnderCuratele E=ONWAAR
            LT:

Given leveringsautorisatie uit autorisatie/Expressietaal_op_boolean_onwaar

Given persoonsbeelden uit specials:specials/Jan_xls
When voor persoon 606417801 wordt de laatste handeling geleverd

Then is er geen synchronisatiebericht gevonden


Scenario: 3. Expressie Persoon.Indicatie.OnderCuratele E>=ONWAAR
            LT:
            Groter dan vergelijk op boolean

Given leveringsautorisatie uit autorisatie/Expressietaal_op_boolean_groter_dan

Given persoonsbeelden uit specials:specials/Jan_R1805_xls
When voor persoon 606417801 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie nadere populatiebeperking op boolean groter dan is ontvangen en wordt bekeken
Then is er 1 bericht geleverd


Scenario: 4. Expressie Persoon.Indicatie.OnderCuratele E>=WAAR
            LT:
            Groter of gelijk aan vergelijk op boolean

Given leveringsautorisatie uit autorisatie/Expressietaal_op_boolean_groter_of_gelijk

Given persoonsbeelden uit specials:specials/Jan_R1805_xls
When voor persoon 606417801 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie nadere populatiebeperking op boolean groter of gelijk is ontvangen en wordt bekeken
Then is er 1 bericht geleverd

Scenario: 5. Expressie Persoon.Indicatie.OnderCuratele E<=WAAR
            LT:
            kleiner of gelijk aan vergelijk op boolean

Given leveringsautorisatie uit autorisatie/Expressietaal_op_boolean_kleiner_of_gelijk

Given persoonsbeelden uit specials:specials/Jan_R1805_xls
When voor persoon 606417801 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie nadere populatiebeperking op boolean kleiner of gelijk is ontvangen en wordt bekeken
Then is er 1 bericht geleverd

Scenario: 6. Expressie Persoon.Indicatie.OnderCuratele E<WAAR
            LT:
            kleiner dan vergelijk op boolean

Given leveringsautorisatie uit autorisatie/Expressietaal_op_boolean_kleiner_dan

Given persoonsbeelden uit specials:specials/Jan_R1805_xls
When voor persoon 606417801 wordt de laatste handeling geleverd
Then is er geen synchronisatiebericht gevonden


Scenario: 7. Expressie Persoon.Indicatie.OnderCuratele E<>ONWAAR
            LT:
            Ongelijk aan vergelijk op boolean

Given leveringsautorisatie uit autorisatie/Expressietaal_op_boolean_ongelijk

Given persoonsbeelden uit specials:specials/Jan_R1805_xls
When voor persoon 606417801 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie nadere populatiebeperking op boolean ongelijk is ontvangen en wordt bekeken
Then is er 1 bericht geleverd


