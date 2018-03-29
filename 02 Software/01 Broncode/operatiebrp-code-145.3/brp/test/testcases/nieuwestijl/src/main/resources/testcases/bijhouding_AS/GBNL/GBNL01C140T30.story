Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R1733 Tussen ouders mag geen verwantschap bestaan

Scenario: kind wordt geboren met ouders die siblings van elkaar zijn op DatumAanvangGeldigheid
          LT: GBNL01C140T30

Given alle personen zijn verwijderd

!-- De grootouder
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL01C140T30-001.xls

!-- Grootouder krijgt twee kinderen
When voer een bijhouding uit GBNL01C140T30a.xml namens partij 'Gemeente BRP 1'
When voer een bijhouding uit GBNL01C140T30b.xml namens partij 'Gemeente BRP 1'

!-- De twee kinderen van de grootouder krijgen een kind
When voer een bijhouding uit GBNL01C140T30c.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
!-- Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GBNL/expected/GBNL01C140T30.xml voor expressie //brp:bhg_afsRegistreerGeboorte_R