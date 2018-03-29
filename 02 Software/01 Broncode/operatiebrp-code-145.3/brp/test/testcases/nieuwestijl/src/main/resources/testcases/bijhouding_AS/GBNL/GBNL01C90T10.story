Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R1721 NOUWKIG verplicht als daarvoor kandidaat is

Scenario: NOUWKIG (huidig partner van OUWKIG) aanwezig als kandidaat
          LT: GBNL01C90T10

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL01C90T10-001.xls

When voer een bijhouding uit GBNL01C90T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GBNL/expected/GBNL01C90T10.xml voor expressie //brp:bhg_afsRegistreerGeboorte_R
