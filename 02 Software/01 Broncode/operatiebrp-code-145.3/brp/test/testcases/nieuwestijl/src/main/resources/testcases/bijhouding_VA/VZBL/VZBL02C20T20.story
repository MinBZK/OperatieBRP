Meta:
@status                 Klaar
@usecase                UCS-BY.0.VA

Narrative: R2394 Een buitenlands adres regel migratie mag niet worden overgeslagen

Scenario: Buitenlandse Adresregel 2 en 3 zijn gevuld
          LT: VZBL02C20T20

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-VZBL/VZBL-001.xls

When voer een bijhouding uit VZBL02C20T20.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief

Then is het antwoordbericht gelijk aan /testcases/bijhouding_VA/VZBL/expected/VZBL02C20T20.xml voor expressie //brp:bhg_vbaRegistreerVerhuizing_R



