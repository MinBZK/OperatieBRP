Meta:

@status             Klaar
@usecase            BV.0.GD
@sleutelwoorden     Geef Details Persoon
@regels             R2273

Narrative:
Indien in het verzoekbericht een scope van te leveren attributen is opgegeven
Dan mag het resultaatbericht alleen objecten bevatten waarvoor geldt dat:
Het object na toepassing van de scoping (R2218 - Bij scoping mag het bericht alleen groepen bevatten die
verplichte attributen of attributen uit de scope bevatten) nog groepen bevat
OF
Het object zich bevindt in het pad tussen het rootobject in het bericht (de hoofdpersoon) en
een object dat na toepassing van de scoping nog groepen bevat.

Scenario: 1.    Leveren van groepen met attributen in scope of verplichte attributen
                LT: R2273_LT01, R2273_LT02, R2273_LT03, R2273_LT04
                Uitwerking:
                - Atribuut huisnummer in scope, dus groep adres leveren, dus object adressen leveren
                - Object persoon in pad naar peronen
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

Then is het antwoordbericht gelijk aan /testcases/BV0GD_Geef_Details_Persoon/expected/expected_12.1_R2273_scenario_1.xml voor expressie
//brp:lvg_bvgGeefDetailsPersoon_R

!-- R2273_LT01 verplicht attribuut bijhoudingsaardCode, in groep administratieveHandeling, dus object administratieveHandelingen leveren
!-- R2273_LT03 Het object bevindt zich in het pad tussen het rootobject in het bericht en en een object dat na toepassing van de scoping nog groepen bevat.
!-- R2273_LT01 attribuut huisnummer in scope, dus groep adres leveren, dus object adressen leveren
!-- R2273_LT02, R2273_LT04 Geen andere objecten leveren
