Meta:
@status                 Klaar
@sleutelwoorden         Intaketest, Synchronisatie

Narrative: Intake test synchronisatie

Scenario:   1. Synchroniseer persoon, controleer volledigbericht met een leveringsautorisatie waar alles getoond wordt
            LT:

Given persoonsbeelden uit oranje:DELTAVERS05/DELTAVERS05C10T10_xls
Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding
Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|434587977

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/intake/expected_synchronisatie_berichten/expected_antwoordbericht_1.xml voor expressie //brp:lvg_synGeefSynchronisatiePersoon_R
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected_synchronisatie_berichten/expected_scenario_1.xml voor expressie //brp:lvg_synVerwerkPersoon
Then is er 1 bericht geleverd


Scenario:   2. Synchroniseer stamgegeven, controleer op alleen een antwoordbericht met vulling element
            LT:

Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding
Given verzoek synchroniseer stamgegeven:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|stamgegeven|ElementTabel

Then heeft het antwoordbericht verwerking Geslaagd

