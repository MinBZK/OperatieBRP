Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R1733 Tussen ouders mag geen verwantschap bestaan

Scenario: kind wordt geboren met ouders die ouder-kind van elkaar zijn op DatumAanvangGeldigheid
          LT: GBNL01C140T10

Given alle personen zijn verwijderd

!-- vulling van OUWKIG
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL01C140T10-001.xls

!-- geboorte van kind van OUWKIG
When voer een bijhouding uit GBNL01C140T10a.xml namens partij 'Gemeente BRP 1'

!-- geboorte van kind van OUWKIG en haar kind als ouders
When voer een bijhouding uit GBNL01C140T10b.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GBNL/expected/GBNL01C140T10.xml voor expressie //brp:bhg_afsRegistreerGeboorte_R