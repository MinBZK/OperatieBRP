Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R2487 Waarschuwing als een persoon een Nederlands adres heeft in de buitenlandse adresregels

Scenario: Landgebied Canada en buitenlands adres regel 1
          LT: GBNL01C250T50

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL01C250T50-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL01C250T50-002.xls

When voer een bijhouding uit GBNL01C250T50.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GBNL/expected/GBNL01C250T50.xml voor expressie //brp:bhg_afsRegistreerGeboorte_R