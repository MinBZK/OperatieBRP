Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R1586 Burgerservicenummer mag niet reeds voorkomen in de BRP

Scenario: Kind heeft het burgerservicenummer van een van de ouders
          LT: GBNL01C10T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL01C10T10-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL01C10T10-002.xls

When voer een bijhouding uit GBNL01C10T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GBNL/expected/GBNL01C10T10.xml voor expressie //brp:bhg_afsRegistreerGeboorte_R