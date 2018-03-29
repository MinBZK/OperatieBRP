Meta:
@status                 Klaar
@regels                 R2501
@usecase                UCS-BY.0.VA

Narrative: R2501 Verwerking Wijziging Bijzondere Verblijfsrechtelijke Positie R2501 Vervallen voorkomen

Scenario: R2501 Simpele bijhouding Wijziging Bijzondere Verblijfsrechtelijke Positie niet-Nederlander met vervallen voorkomen
          LT: WBVP01C20T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-WBVP/WBVP-001-01C20T10.xls
Given de database is aangepast met: update kern.his_persindicatie set tsverval = now(), actieverval = actieinh

When voer een bijhouding uit WBVP01C20T10.xml namens partij 'Gemeente BRP 1'

Then is het foutief antwoordbericht gelijk aan /testcases/bijhouding_VA/WBVP/expected/WBVP01C20T10.xml
