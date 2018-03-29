Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R2487 Waarschuwing als een persoon een Nederlands adres heeft in de buitenlandse adresregels

Scenario: Landgebied Nederland en geen buitenlands adres regel
          LT: GBNL01C250T40

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL01C250T40-001.xls

When voer een bijhouding uit GBNL01C250T40.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GBNL/expected/GBNL01C250T40.xml voor expressie //brp:bhg_afsRegistreerGeboorte_R