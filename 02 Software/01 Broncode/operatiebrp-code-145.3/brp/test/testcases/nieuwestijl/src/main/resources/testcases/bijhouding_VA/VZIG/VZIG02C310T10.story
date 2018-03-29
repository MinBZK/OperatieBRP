Meta:
@status                 Klaar
@usecase                UCS-BY.0.VA

Narrative: R2419 Gemeente verordening in specifieke verstrekkingsbeperking mag alleen zijn gevuld als omschrijving derde gevuld is

Scenario: Gemeenteverordening gevuld en Omschrijving derde is leeg
          LT: VZIG02C310T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-VZIG/VZIG02C310T10-001.xls

When voer een bijhouding uit VZIG02C310T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_VA/VZIG/expected/VZIG02C310T10.xml voor expressie //brp:bhg_vbaRegistreerVerhuizing_R

Then in kern heeft SELECT COUNT(id) FROM kern.admhnd de volgende gegevens:
| veld                      | waarde |
| count                     | 1      |
