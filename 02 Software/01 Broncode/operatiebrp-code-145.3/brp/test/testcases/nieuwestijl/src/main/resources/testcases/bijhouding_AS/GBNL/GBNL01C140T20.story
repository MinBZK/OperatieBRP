Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R1733 Tussen ouders mag geen verwantschap bestaan

Scenario: kind wordt geboren met ouders die ouder-kind van elkaar zijn op DatumAanvangGeldigheid waarbij de ouder die kind is een pseudo-persoon is
          LT: GBNL01C140T20

Given alle personen zijn verwijderd

!-- vulling van OUWKIG met pseudo-persoon kind
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL01C140T20-001.xls

!-- geboorte van kind van OUWKIG en haar pseudo-persoon kind als ouders
When voer een bijhouding uit GBNL01C140T20.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GBNL/expected/GBNL01C140T20.xml voor expressie //brp:bhg_afsRegistreerGeboorte_R