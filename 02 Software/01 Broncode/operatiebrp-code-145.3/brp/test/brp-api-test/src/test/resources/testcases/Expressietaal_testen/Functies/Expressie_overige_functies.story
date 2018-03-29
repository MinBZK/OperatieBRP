Meta:
@status             Klaar
@sleutelwoorden     Expressietaal

Narrative:
Testen voor het valideren van de verschillende functies in de expressie taal.
De functies die getest worden:
- Overige functies (IS_NULL, KV, KNV, )

Scenario: 01 Functie IS_NULL WAAR
             LT:
             Expressie: IS_NULL(AANTAL(NULL))

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/functie_IS_NULL_WAAR

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Expressietaal'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 02 Functie IS_NULL ONWAAR
             LT:
             Expressie: IS_NULL(Persoon.Identificatienummers.Burgerservicenummer)=ONWAAR

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/functie_IS_NULL_ONWAAR

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Expressietaal'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801

Then heeft het antwoordbericht verwerking Geslaagd



Scenario: 03 Jan is niet getrouwd, expressie op huwelijk komt voor KV(GerelateerdeHuwelijkspartner.Persoon.SoortCode)
             LT:

Given leveringsautorisatie uit autorisatie/Expressietaal_op_aanwezigheid_huwelijk

Given persoonsbeelden uit specials:specials/Jan_xls
When voor persoon 606417801 wordt de laatste handeling geleverd
Then is er geen synchronisatiebericht gevonden

Scenario: 04 Jan is niet getrouwd, expressie op huwelijk komt NIET voor KNV(GerelateerdeHuwelijkspartner.Persoon.SoortCode)
             LT:

Given leveringsautorisatie uit autorisatie/Expressietaal_op_afwezigheid_huwelijk

Given persoonsbeelden uit specials:specials/Jan_xls
When voor persoon 606417801 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie nadere populatiebeperking op afwezigheid huwelijk is ontvangen en wordt bekeken
Then is er 1 bericht geleverd

Scenario: 05 Anne Bakker is WEL getrouwd, expressie op huwelijk komt voor KV(GerelateerdeHuwelijkspartner.Persoon.SoortCode)
             LT:

Given leveringsautorisatie uit autorisatie/Expressietaal_op_aanwezigheid_huwelijk

Given persoonsbeelden uit specials:specials/Anne_Bakker_xls
When voor persoon 595891305 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie nadere populatiebeperking op aanwezigheid huwelijk is ontvangen en wordt bekeken
Then is er 1 bericht geleverd

Scenario: 06 Anne Bakker is WEL getrouwd, expressie op huwelijk komt NIET voor KNV(GerelateerdeHuwelijkspartner.Persoon.SoortCode)
             LT:

Given leveringsautorisatie uit autorisatie/Expressietaal_op_afwezigheid_huwelijk

Given persoonsbeelden uit specials:specials/Anne_Bakker_xls
When voor persoon 595891305 wordt de laatste handeling geleverd

Then is er geen synchronisatiebericht gevonden

Scenario: 07 Expressie op postcode komt voor KV(persoon.adres.postcode)
             LT:

Given leveringsautorisatie uit autorisatie/Expressietaal_op_aanwezigheid_postcode

Given persoonsbeelden uit specials:specials/Jan_xls
When voor persoon 606417801 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie nadere populatiebeperking op aanwezigheid postcode is ontvangen en wordt bekeken
Then is er 1 bericht geleverd

Scenario: 08 Expressie op postcode komt NIET voor KNV(persoon.adres.postcode)
             LT:

Given leveringsautorisatie uit autorisatie/Expressietaal_op_afwezigheid_postcode

Given persoonsbeelden uit specials:specials/Jan_xls
When voor persoon 606417801 wordt de laatste handeling geleverd

Then is er geen synchronisatiebericht gevonden

Scenario: 09 Expressie op bijhoudingspartij komt voor KV(persoon.bijhouding.bijhoudingspartij)
             LT:

Given leveringsautorisatie uit autorisatie/Expressietaal_op_aanwezigheid_bijhoudingspartij

Given persoonsbeelden uit specials:specials/Jan_xls
When voor persoon 606417801 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie nadere populatiebeperking op aanwezigheid bijhoudingspartij is ontvangen en wordt bekeken
Then is er 1 bericht geleverd

Scenario: 10 Expressie op bijhoudingspartij komt NIET voor KNV(persoon.bijhouding.bijhoudingspartij)
             LT:

Given leveringsautorisatie uit autorisatie/Expressietaal_op_afwezigheid_bijhoudingspartij

Given persoonsbeelden uit specials:specials/Jan_xls
When voor persoon 606417801 wordt de laatste handeling geleverd

Then is er geen synchronisatiebericht gevonden

Scenario: 11 Expressie met closure
             LT:
             Expressie: Persoon.Bijhouding.PartijCode EIN x WAARBIJ x={62601}

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/Expressietaal_Closure

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Expressietaal'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801

Then heeft het antwoordbericht verwerking Geslaagd



