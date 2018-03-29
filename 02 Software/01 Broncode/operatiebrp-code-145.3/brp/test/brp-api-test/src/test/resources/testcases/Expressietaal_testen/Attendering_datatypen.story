Meta:
@status             Klaar
@sleutelwoorden     Expressietaal

Narrative:
In deze story zijn scenario's opgenomen om de expressietaal af te testen mbt EN functie, AANTAL en OF functie.
Aantal wordt in PDT allen gebruikt in de adressen.soortcode dit zou niet voor kunnen komen dat dit 0 is, geen testgeval voor gemaakt.


Scenario:   1. Attendering datatype datum
            LT:
            Expressie:
            GEWIJZIGD(oud, nieuw, [Huwelijk.DatumAanvang])
            DELTAVERS05C10T150_xls heeft als laatste handeling bijschrijving huwelijk
            Verwacht resultaat: levering volledigbericht.

Given leveringsautorisatie uit autorisatie/Expressietaal_Attendering_datum
Given persoonsbeelden uit oranje:DELTAVERS05/DELTAVERS05C10T150_xls
When voor persoon 434587977 wordt de laatste handeling geleverd
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Expressietaal_Attendering_datum is ontvangen en wordt bekeken

Scenario:   2. Attendering datatype datum
            LT:
            Expressie:
            GEWIJZIGD(oud, nieuw, [Huwelijk.DatumAanvang])
            Anne_Bakker_GBA_Bijhouding_Bijschrijving_Kind_xls heeft wel een huwelijk maar is niet de laatste handeling
            Verwacht resultaat: GEEN levering mutatie bericht.

Given leveringsautorisatie uit autorisatie/Expressietaal_Attendering_datum
Given persoonsbeelden uit specials:specials/Anne_Bakker_GBA_Bijhouding_Bijschrijving_Kind_xls
When voor persoon 595891305 wordt de laatste handeling geleverd
Then is er geen synchronisatiebericht voor leveringsautorisatie Expressietaal_Attendering_datum


Scenario:   3. Attendering datatype datum
            LT:
            Expressie:
            GEWIJZIGD(oud, nieuw, [Huwelijk.DatumAanvang])
            Jan_xls heeft geen huwelijk
            Verwacht resultaat: GEEN levering mutatie bericht. (geen Object huwelijk)

Given leveringsautorisatie uit autorisatie/Expressietaal_Attendering_datum
Given persoonsbeelden uit specials:specials/Jan_xls
When voor persoon 606417801 wordt de laatste handeling geleverd
Then is er geen synchronisatiebericht voor leveringsautorisatie Expressietaal_Attendering_datum


Scenario:   4. Attendering datatype string
            LT:
            Expressie:
            GEWIJZIGD(oud, nieuw, [Persoon.SamengesteldeNaam.AdellijkeTitelCode]) OF GEWIJZIGD(oud, nieuw, [Persoon.SamengesteldeNaam.PredicaatCode])
            DELTAVERS01aC10T20_xls AdellijkeTitelCode gewijzigd
            Verwacht resultaat: Volledigbericht geleverd


Given leveringsautorisatie uit autorisatie/Expressietaal_Attendering_string
Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC10T20_xls
When voor persoon 822062793 wordt de laatste handeling geleverd
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Expressietaal_Attendering_string is ontvangen en wordt bekeken


Scenario:   5. Attendering datatype string
            LT:
            Expressie:
            GEWIJZIGD(oud, nieuw, [Persoon.SamengesteldeNaam.AdellijkeTitelCode]) OF GEWIJZIGD(oud, nieuw, [Persoon.SamengesteldeNaam.PredicaatCode])
             Jan_xls heeft geen AdellijkeTitelCode of PredicaatCode
            Verwacht resultaat: Geen Volledigbericht geleverd


Given leveringsautorisatie uit autorisatie/Expressietaal_Attendering_string
Given persoonsbeelden uit specials:specials/Jan_xls
When voor persoon 606417801 wordt de laatste handeling geleverd
Then is er geen synchronisatiebericht voor leveringsautorisatie Expressietaal_Attendering_datum

Scenario:   6. Attendering datatype boolean
            LT:
            Expressie:
            Verwacht resultaat: Volledigbericht geleverd


Given leveringsautorisatie uit autorisatie/Expressietaal_Attendering_boolean
Given persoonsbeelden uit specials:specials/Jan_Staatloos_xls
When voor persoon 606417801 wordt de laatste handeling geleverd
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Expressietaal_Attendering_boolean is ontvangen en wordt bekeken

Scenario:   7. Attendering datatype boolean
            LT:
            Expressie:
            Verwacht resultaat: Geen Volledigbericht geleverd

Given leveringsautorisatie uit autorisatie/Expressietaal_Attendering_boolean
Given persoonsbeelden uit specials:specials/Jan_xls
When voor persoon 606417801 wordt de laatste handeling geleverd
Then is er geen synchronisatiebericht voor leveringsautorisatie Expressietaal_Attendering_datum


Scenario:   8. Attendering datatype getal
            LT:
            Expressie:
            GEWIJZIGD(oud, nieuw, [Persoon.Adres.GemeenteCode])

            Verwacht resultaat:Volledigbericht geleverd

Given leveringsautorisatie uit autorisatie/Expressietaal_Attendering_getal
Given persoonsbeelden uit specials:specials/ElisaBeth_Haarlem_Beverwijk_xls
When voor persoon 270433417 wordt de laatste handeling geleverd
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Expressietaal_Attendering_getal is ontvangen en wordt bekeken


Scenario:   9. Attendering datatype getal
            LT:
            Expressie:
            GEWIJZIGD(oud, nieuw, [Persoon.Adres.GemeenteCode])
            Jan_xls heeft geen wijziging in gemeentecode
            Verwacht resultaat: GEEN Volledigbericht geleverd

Given leveringsautorisatie uit autorisatie/Expressietaal_Attendering_getal
Given persoonsbeelden uit specials:specials/Jan_xls
When voor persoon 606417801 wordt de laatste handeling geleverd
Then is er geen synchronisatiebericht voor leveringsautorisatie Expressietaal_Attendering_datum
