Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R1727 Geslachtsnaam Nederlands kind moet overeenkomen met die van eerder kind uit dezelfde relatie

Scenario: NL kind verschilt in geslachtsnaam met 1 van de 2 siblings
          LT: GBNL01C120T30

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL01C120T30-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL01C120T30-002.xls

!-- Geboorte toekomstige broer met dezelfde geslachtsnaam als kind
When voer een bijhouding uit GBNL01C120T30a.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Geslaagd

!-- Geboorte toekomstige zus met andere geslachtsnaam dan haar broer. De melding wordt gedeblokkeerd.
When voer een bijhouding uit GBNL01C120T30b.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Geslaagd

!-- Geboorte kind met dezelfde geslachtsnaam als broer en andere geslachtsnaam dan zus
When voer een bijhouding uit GBNL01C120T30c.xml namens partij 'Gemeente BRP 1'

!-- kind verschilt van zus maar niet van broer
Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GBNL/expected/GBNL01C120T30.xml voor expressie //brp:bhg_afsRegistreerGeboorte_R