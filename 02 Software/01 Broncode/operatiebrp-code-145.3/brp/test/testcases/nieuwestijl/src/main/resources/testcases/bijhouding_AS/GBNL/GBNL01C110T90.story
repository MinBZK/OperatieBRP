Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R1726 Geslachtsnaam Nederlands kind moet overeenkomen met ouder

Scenario: Voorvoegsel, scheidingsteken en geslachtsnaam kind komen niet overeen met een ouder maar het kind is geen Nederlander
          LT: GBNL01C110T90

Given alle personen zijn verwijderd

!-- De ouders
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL01C110T90-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL01C110T90-002.xls

When voer een bijhouding uit GBNL01C110T90.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GBNL/expected/GBNL01C110T90.xml voor expressie //brp:bhg_afsRegistreerGeboorte_R
