Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R1727 Geslachtsnaam Nederlands kind moet overeenkomen met die van eerder kind uit dezelfde relatie

Scenario: NL kind met dezelfde ouders als ingezeten ingeschreven sibling met pseudo-ouder verschilt in geslachtsnaam
          LT: GBNL01C120T20

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL01C120T20-001.xls

!-- Geboorte toekomstige sibling met ouwkig en soort pseudo nouwkig
When voer een bijhouding uit GBNL01C120T20a.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

!-- Geboorte kind met ouwkig en soort pseudo nouwkig
When voer een bijhouding uit GBNL01C120T20b.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GBNL/expected/GBNL01C120T20.xml voor expressie //brp:bhg_afsRegistreerGeboorte_R