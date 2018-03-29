Meta:
@status                 Klaar
@sleutelwoorden         Intaketest, Bevraging, GeefDetailsPersoon

Narrative:
Intake test voor geefDetailsPersoon service

Scenario: 1. Intake voor GeefDetailsPersoon service
            LT:
             Verwacht resultaat: Levering volgens expected

Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding
Given persoonsbeelden uit oranje:DELTAVERS02/DELTAVERS02C10T10_xls

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|258096329

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan expected_geefDetailsPersoon/expected_scenario_1.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

!-- Extra controle op versleuteling obectsleutel
!-- Controle voor objectSleutel uitgeschakel omdat dit onderhanden werk is
!-- Then is er voor xpath //brp:persoon[1][contains(@brp:objectSleutel, '+')] een node aanwezig in het antwoord bericht

Scenario: 2. Intake voor GeefDetailsPersoon service aan levsautorisatie met rol Afnemer met plaatsen afnemerindicatie
             LT:
             Verwacht resultaat: Levering volgens expected (incusief afnemerindicaties)

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit oranje:DELTAVERS02/DELTAVERS02C10T10_xls
Given afnemerindicatie op de persoon :
|bsn|leveringsautorisatieNaam|partijNaam|dienstId|tsReg
|258096329|Geen pop.bep. levering op basis van afnemerindicatie|'Gemeente Utrecht'|30|2016-07-28 T16:11:21Z

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|258096329

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan expected_geefDetailsPersoon/expected_scenario_2.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R
