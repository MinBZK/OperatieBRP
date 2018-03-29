Meta:

@status             Klaar
@usecase            BV.0.GD
@sleutelwoorden     Geef Details Persoon
@regels             R2218

Narrative:
Indien in het verzoekbericht een scope van te leveren attributen is opgegeven
Dan mag het resultaatbericht alleen groepen bevatten waarvoor geldt dat:
De groep tenminste één verplicht te leveren attribuut bevat (Element.Autorisatie = "Verplicht")
OF
De groep tenminste één attribuut bevat dat aanwezig is in de opgegeven scope
(berichtparameter 'Gevraagde attributen')

Scenario: 1.    Leveren van objecten met groep in scope of verplichte attributen
                LT: R2218_LT01, R2218_LT02, R2218_LT03
                Uitwerking:
                - Atribuut huisnummer in scope, dus groep adres leveren
                - Geboorte, verplicht attribuut bijgehoudenacties, dus leveren groep administratieve handeling
                - Geen andere groepen leveren

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|scopingElementen|Persoon.Adres.Huisnummer

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/BV0GD_Geef_Details_Persoon/expected/expected_5.1_R2218_scenario_1.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

