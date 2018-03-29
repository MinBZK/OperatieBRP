Meta:
@sleutelwoorden     Expressiedatum
@status             Klaar

Narrative:
In deze story zijn scenario's opgenomen om de expressietaal op datum af te testen
In de scenario's wordt de evaluatie op dag getest.

!-- De eerst volgende scenario's testen de >, =, < expressie op een volledig gevulde geboortedatum

Scenario: 1.1    Geboortedatum Jan = 21-08-1960, expressie Persoon.Geboorte.Datum E> 1960/08/20
            LT:

Given leveringsautorisatie uit autorisatie/Expressietaal_op_datum_dag_groter_dan

Given persoonsbeelden uit specials:specials/Jan_xls
When voor persoon 606417801 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie nadere populatiebeperking op dag is ontvangen en wordt bekeken
Then is er 1 bericht geleverd

Scenario: 1.2    Geboortedatum Jan = 21-08-1960, expressie Persoon.Geboorte.Datum E= 1960/08/20
            LT:

Given leveringsautorisatie uit autorisatie/Expressietaal_op_datum_dag_gelijk_aan

Given persoonsbeelden uit specials:specials/Jan_xls
When voor persoon 606417801 wordt de laatste handeling geleverd

Then is er geen synchronisatiebericht gevonden

Scenario: 1.3    Geboortedatum Jan = 21-08-1960, expressie Persoon.Geboorte.Datum E>= 1960/08/20
            LT:

Given leveringsautorisatie uit autorisatie/Expressietaal_op_datum_dag_groter_of_gelijk_aan

Given persoonsbeelden uit specials:specials/Jan_xls
When voor persoon 606417801 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie nadere populatiebeperking op dag is ontvangen en wordt bekeken
Then is er 1 bericht geleverd

Scenario: 1.4    Geboortedatum Jan = 21-08-1960, expressie Persoon.Geboorte.Datum E<= 1960/08/20
            LT:

Given leveringsautorisatie uit autorisatie/Expressietaal_op_datum_dag_kleiner_of_gelijk_aan

Given persoonsbeelden uit specials:specials/Jan_xls
When voor persoon 606417801 wordt de laatste handeling geleverd

Then is er geen synchronisatiebericht gevonden

Scenario: 1.5    Geboortedatum Jan = 21-08-1960, expressie Persoon.Geboorte.Datum E<> 1960/08/20
            LT:

Given leveringsautorisatie uit autorisatie/Expressietaal_op_datum_dag_anders_dan

Given persoonsbeelden uit specials:specials/Jan_xls
When voor persoon 606417801 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie nadere populatiebeperking op dag is ontvangen en wordt bekeken
Then is er 1 bericht geleverd

Scenario: 1.6    Geboortedatum Jan = 21-08-1960, expressie Persoon.Geboorte.Datum E< 1960/08/20
            LT:

Given leveringsautorisatie uit autorisatie/Expressietaal_op_datum_dag_kleiner_dan

Given persoonsbeelden uit specials:specials/Jan_xls
When voor persoon 606417801 wordt de laatste handeling geleverd

Then is er geen synchronisatiebericht gevonden



!-- De eerst volgende scenario's testen de >, =, < expressie op een gedeeltelijk onbekende geboortedatum

Scenario: 2.1   Geboortedatum Jan = 21-08-1960, expressie Persoon.Geboorte.Datum E= 1960/08/?
            LT:

Given leveringsautorisatie uit autorisatie/Expressietaal_op_datum_dag_onbekend

Given persoonsbeelden uit specials:specials/Jan_xls
When voor persoon 606417801 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie nadere populatiebeperking op dag onbekend is ontvangen en wordt bekeken
Then is er 1 bericht geleverd

Scenario: 2.2    Geboortedatum Jan = 21-08-1960, expressie Persoon.Geboorte.Datum E> 1960/08/?
            LT:

Given leveringsautorisatie uit autorisatie/Expressietaal_op_datum_dag_onbekend_groter

Given persoonsbeelden uit specials:specials/Jan_xls
When voor persoon 606417801 wordt de laatste handeling geleverd

Then is er geen synchronisatiebericht gevonden

Scenario: 2.3    Geboortedatum Jan = 21-08-1960, expressie Persoon.Geboorte.Datum E< 1960/08/?
            LT:

Given leveringsautorisatie uit autorisatie/Expressietaal_op_datum_dag_onbekend_kleiner

Given persoonsbeelden uit specials:specials/Jan_xls
When voor persoon 606417801 wordt de laatste handeling geleverd

Then is er geen synchronisatiebericht gevonden

Scenario: 2.4   Geboortedatum Jan = 21-08-1960, expressie Persoon.Geboorte.Datum E= 1960/?/?
            LT:

Given leveringsautorisatie uit autorisatie/Expressietaal_op_datum_maand_onbekend_gelijk_aan

Given persoonsbeelden uit specials:specials/Jan_xls
When voor persoon 606417801 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie nadere populatiebeperking op dag onbekend is ontvangen en wordt bekeken
Then is er 1 bericht geleverd

Scenario: 2.5    Geboortedatum Jan = 21-08-1960, expressie Persoon.Geboorte.Datum E< 1960/?/?
            LT:

Given leveringsautorisatie uit autorisatie/Expressietaal_op_datum_maand_onbekend_kleiner_dan

Given persoonsbeelden uit specials:specials/Jan_xls
When voor persoon 606417801 wordt de laatste handeling geleverd

Then is er geen synchronisatiebericht gevonden

Scenario: 2.6    Geboortedatum Jan = 21-08-1960, expressie Persoon.Geboorte.Datum E> 1960/?/?
            LT:

Given leveringsautorisatie uit autorisatie/Expressietaal_op_datum_maand_onbekend_groter_dan

Given persoonsbeelden uit specials:specials/Jan_xls
When voor persoon 606417801 wordt de laatste handeling geleverd

Then is er geen synchronisatiebericht gevonden

Scenario: 2.7   Geboortedatum Jan = 21-08-1960, expressie Persoon.Geboorte.Datum E= ?/?/?
            LT:

Given leveringsautorisatie uit autorisatie/Expressietaal_op_datum_jaar_onbekend_gelijk_aan

Given persoonsbeelden uit specials:specials/Jan_xls
When voor persoon 606417801 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie nadere populatiebeperking op dag onbekend is ontvangen en wordt bekeken
Then is er 1 bericht geleverd

Scenario: 2.8    Geboortedatum Jan = 21-08-1960, expressie Persoon.Geboorte.Datum E< ?/?/?
            LT:

Given leveringsautorisatie uit autorisatie/Expressietaal_op_datum_jaar_onbekend_kleiner_dan

Given persoonsbeelden uit specials:specials/Jan_xls
When voor persoon 606417801 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie nadere populatiebeperking op dag onbekend is ontvangen en wordt bekeken
Then is er 1 bericht geleverd

Scenario: 2.9    Geboortedatum Jan = 21-08-1960, expressie Persoon.Geboorte.Datum E> ?/?/?
            LT:

Given leveringsautorisatie uit autorisatie/Expressietaal_op_datum_jaar_onbekend_groter_dan

Given persoonsbeelden uit specials:specials/Jan_xls
When voor persoon 606417801 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie nadere populatiebeperking op dag onbekend is ontvangen en wordt bekeken
Then is er 1 bericht geleverd


!-- De volgende scenario's testen de expressie waarbij alleen de dag is gevuld

Scenario: 3.1    Geboortedatum Jan = 21-08-1960, expressie Persoon.Geboorte.Datum E= ?/?/00
            LT:

Given leveringsautorisatie uit autorisatie/Expressietaal_op_datum_dag_is_geboortedag_00

Given persoonsbeelden uit specials:specials/Jan_xls
When voor persoon 606417801 wordt de laatste handeling geleverd

Then is er geen synchronisatiebericht gevonden

Scenario: 3.2    Geboortedatum Jan = 21-08-1960, expressie Persoon.Geboorte.Datum E= ?/?/21
            LT:

Given leveringsautorisatie uit autorisatie/Expressietaal_op_datum_dag_is_geboortedag_21

Given persoonsbeelden uit specials:specials/Jan_xls
When voor persoon 606417801 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie nadere populatiebeperking op geboortedag 21 is ontvangen en wordt bekeken
Then is er 1 bericht geleverd

Scenario: 3.3    Geboortedatum Jan = 21-08-1960, expressie Persoon.Geboorte.Datum E= 1960/?/?
            LT:

Given leveringsautorisatie uit autorisatie/Expressietaal_op_datum_dag_is_geboortejaar_1960

Given persoonsbeelden uit specials:specials/Jan_xls
When voor persoon 606417801 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie nadere populatiebeperking op geboortejaar is ontvangen en wordt bekeken
Then is er 1 bericht geleverd

!-- De volgende scenario's testen op een datum van een groep die niet gevuld is
Scenario:   4.1   Test op datum aanvang huwelijk, Jan is niet getrouwd
            LT:

Given leveringsautorisatie uit autorisatie/Expressietaal_op_datum_aanvang_huwelijk_onbekend

Given persoonsbeelden uit specials:specials/Jan_xls
When voor persoon 606417801 wordt de laatste handeling geleverd

Then is er geen synchronisatiebericht gevonden

Scenario:   4.2   Test op datum aanvang huwelijk, Jan is niet getrouwd
            LT:

Given leveringsautorisatie uit autorisatie/Expressietaal_op_datum_aanvang_huwelijk_leeg

Given persoonsbeelden uit specials:specials/Jan_xls
When voor persoon 606417801 wordt de laatste handeling geleverd

Then is er geen synchronisatiebericht gevonden

Scenario:   4.3   Test op datum aanvang huwelijk, Anne Bakker is wel getrouwd op 2010-01-01, expressie Huwelijk.DatumAanvang E= 2010/01/01
            LT:

Given leveringsautorisatie uit autorisatie/Expressietaal_op_datum_aanvang_huwelijk

Given persoonsbeelden uit specials:specials/Anne_Bakker_xls
When voor persoon 595891305 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie nadere populatiebeperking op datum aanvang huwelijk is ontvangen en wordt bekeken
Then is er 1 bericht geleverd

Scenario:   4.4   Test op datum aanvang huwelijk, Anne Bakker is wel getrouwd op 2010-01-01, expressie Huwelijk.DatumAanvang E= ?/?/?
            LT:

Given leveringsautorisatie uit autorisatie/Expressietaal_op_datum_aanvang_huwelijk_onbekend

Given persoonsbeelden uit specials:specials/Anne_Bakker_xls
When voor persoon 595891305 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie nadere populatiebeperking op datum aanvang huwelijk onbekend is ontvangen en wordt bekeken
Then is er 1 bericht geleverd

!-- De volgende scenario's testen vergelijkingen waar een berekening bij komt kijken

Scenario: 5.1 Geboortedatum Jan = 21-08-1960, expressie Persoon.Geboorte.Datum E< VANDAAG() - ^80/01/15
            LT:

Given leveringsautorisatie uit autorisatie/Expressietaal_op_datum_kleiner_dan_vandaag_min_80_jaar

Given persoonsbeelden uit specials:specials/Jan_xls
When voor persoon 606417801 wordt de laatste handeling geleverd

Then is er geen synchronisatiebericht gevonden

Scenario: 5.2 Geboortedatum Jan = 21-08-1960, expressie Persoon.Geboorte.Datum E> VANDAAG() - ^80/01/15
            LT:

Given leveringsautorisatie uit autorisatie/Expressietaal_op_datum_groter_dan_vandaag_min_80_jaar

Given persoonsbeelden uit specials:specials/Jan_xls
When voor persoon 606417801 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie nadere populatiebeperking groter dan vandaag min 80 jaar is ontvangen en wordt bekeken
Then is er 1 bericht geleverd

Scenario: 5.3 Geboortedatum Jan = 21-08-1960, expressie Persoon.Geboorte.Datum E= 1960/08/22 - ^00/00/01
            LT:

Given leveringsautorisatie uit autorisatie/Expressietaal_op_datum_gelijk_aan_berekening

Given persoonsbeelden uit specials:specials/Jan_xls
When voor persoon 606417801 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie Expressietaal op datum gelijk aan berekening is ontvangen en wordt bekeken
Then is er 1 bericht geleverd



!-- De volgende scenario's testen vergelijkingen waar een berekening bij komt kijken met gedeeltelijk onbekende datums

Scenario: 6.1 Geboortedatum Jan = 21-08-1960, expressie Persoon.Geboorte.Datum E< 1960/08/20 - ^?/?/15
            LT:

Given leveringsautorisatie uit autorisatie/Expressietaal_op_datum_kleiner_dan_vandaag_min_onbekend_aantal_jaar

Given persoonsbeelden uit specials:specials/Jan_xls
When voor persoon 606417801 wordt de laatste handeling geleverd

Then is er geen synchronisatiebericht gevonden

Scenario: 6.2 Geboortedatum Jan = 21-08-1960, expressie Persoon.Geboorte.Datum E> 1960/08/20 - ^?/?/15
            LT:

Given leveringsautorisatie uit autorisatie/Expressietaal_op_datum_groter_dan_vandaag_min_onbekend_aantal_jaar

Given persoonsbeelden uit specials:specials/Jan_xls
When voor persoon 606417801 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie nadere populatiebeperking groter dan vandaag min onbekend aantal jaar is ontvangen en wordt bekeken
Then is er 1 bericht geleverd

Scenario: 6.3 Geboortedatum Jan = 21-08-1960, expressie Persoon.Geboorte.Datum E= 1960/08/20 + ^?/?/01
            LT:

Given leveringsautorisatie uit autorisatie/Expressietaal_op_datum_gelijk_aan_vandaag_plus_onbekend_aantal_jaar

Given persoonsbeelden uit specials:specials/Jan_xls
When voor persoon 606417801 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie nadere populatiebeperking gelijk aan vandaag plus onbekend aantal jaar is ontvangen en wordt bekeken
Then is er 1 bericht geleverd

Scenario: 6.4 Geboortedatum Jan = 21-08-1960, expressie Persoon.Geboorte.Datum E= 1959/08/20 + ^1/?/01
            LT:

Given leveringsautorisatie uit autorisatie/Expressietaal_op_datum_gelijk_aan_vandaag_plus_onbekend_aantal_maanden

Given persoonsbeelden uit specials:specials/Jan_xls
When voor persoon 606417801 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie nadere populatiebeperking gelijk aan vandaag plus onbekend aantal maanden is ontvangen en wordt bekeken
Then is er 1 bericht geleverd


Scenario: 7. Geboortedatum Jan = 21-08-1960, expressie Persoon.Geboorte.Datum E=% 1959/?/?
            LT:

Given leveringsautorisatie uit autorisatie/Expressietaal_op_datum_gelijk_aan_vandaag_plus_onbekend_aantal_maanden

Given persoonsbeelden uit specials:specials/Jan_xls
When voor persoon 606417801 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie nadere populatiebeperking gelijk aan vandaag plus onbekend aantal maanden is ontvangen en wordt bekeken
Then is er 1 bericht geleverd
