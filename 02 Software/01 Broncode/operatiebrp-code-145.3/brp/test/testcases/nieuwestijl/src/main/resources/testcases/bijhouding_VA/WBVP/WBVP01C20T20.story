Meta:
@status                 Klaar
@regels                 R2501
@usecase                UCS-BY.0.VA

Narrative: R2501 Verwerking Wijziging Bijzondere Verblijfsrechtelijke Positie R2501 geen voorkomens

Scenario: R2501 Simpele bijhouding Wijziging Bijzondere Verblijfsrechtelijke Positiean niet-Nederlander zonder indicatie voorkomens
          LT: WBVP01C20T20

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-WBVP/WBVP-001.xls

When voer een bijhouding uit WBVP01C20T10.xml namens partij 'Gemeente BRP 1'

Then is het foutief antwoordbericht gelijk aan /testcases/bijhouding_VA/WBVP/expected/WBVP01C20T10.xml
