Meta:
@status                 Klaar
@usecase                UCS-BY.0.VA

Narrative: R2371 Migrant moet zelf partner zijn als aangever partner is

Scenario: Aangever migratie = P en de persoon heeft geen Huwelijk of Geregistreerd partnerschap
          LT: VZBL01C50T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-VZBL/VZBL01C50T10.xls

When voer een bijhouding uit VZBL01C50T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief

Then is het antwoordbericht gelijk aan /testcases/bijhouding_VA/VZBL/expected/VZBL01C50T10.xml voor expressie  //brp:bhg_vbaRegistreerVerhuizing_R
