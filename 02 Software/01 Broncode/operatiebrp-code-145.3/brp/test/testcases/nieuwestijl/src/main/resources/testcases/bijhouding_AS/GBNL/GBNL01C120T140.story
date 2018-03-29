Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R1727 Geslachtsnaam Nederlands kind moet overeenkomen met die van eerder kind uit dezelfde relatie

Scenario: Een ouder zonder partner heeft twee kinderen die van geslachtsnaam verschillen
          LT: GBNL01C120T140

Given alle personen zijn verwijderd

!-- Gemeenschappelijke OUWKIG van het kind en sibling
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL01C120T140-001.xls

!-- NOUWKIG van de sibling
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL01C120T140-002.xls

!-- Geboorte toekomstige sibling
When voer een bijhouding uit GBNL01C120T140a.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

!-- Geboorte kind
When voer een bijhouding uit GBNL01C120T140b.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GBNL/expected/GBNL01C120T140.xml voor expressie //brp:bhg_afsRegistreerGeboorte_R