Meta:
@status                 Klaar
@sleutelwoorden         INTEST-2850

Narrative:
Beschrijving testgeval: In de IV is een persoon in de database gezet, deze persoon woont in het buitenland.
Vervolgens wordt zijn geslacht gewijzigd, waarbij ook de voornamen worden gewijzigd.
Er staat een afnemerindicatie op deze persoon.

Verwacht resultaat: wijzigingen zijn verwerkt in de database en er komt een mutatielevering met daarin de wijzigingen.

Actueel resultaat: wijzigingen zijn verwerkt in de database, maar er komt geen mutatielevering. In de logs is een foutmelding te zien, zie bijlage.

Scenario: 1.1   Bevragin via geef details

Given leveringsautorisatie uit autorisatie/Levering_obv_doelbinding
Given persoonsbeelden uit specials:IENT/INTEST-2850_xls

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Levering obv doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|108685408

Then is het antwoordbericht gelijk aan  /testcases/IENT/INTEST_2850/expected/INTEST2850_expected_geefdetpers.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario:   1.2. Afnemerindicatie op persoon, lever mutatie.

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:IENT/INTEST-2850_xls

Given afnemerindicatie op de persoon :
|bsn|leveringsautorisatieNaam|partijNaam|dienstId|tsReg|
|108685408 |Geen pop.bep. levering op basis van afnemerindicatie|'Gemeente Utrecht'|1|2016-07-28 T16:11:21Z

When voor persoon 108685408 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan  /testcases/IENT/INTEST_2850/expected/INTEST2850_expected_mutlev.xml voor expressie //brp:lvg_synVerwerkPersoon