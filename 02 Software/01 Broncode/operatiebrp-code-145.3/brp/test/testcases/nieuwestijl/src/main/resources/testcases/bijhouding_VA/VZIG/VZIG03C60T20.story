Meta:
@status                 Klaar
@regels                 R2257
@usecase                UCS-BY.0.VA

Narrative: R2257 Gemeente verordening moet verwijzen naar bestaand stamgegeven

Scenario: Persoon Verstrekkingsbeperking.Gemeente verordening verwijst naar een stamgegeven in Partij waarbij Soort ongelijk aan 'Gemeente'
          LT: VZIG03C60T20

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-VZIG/VZIG-001.xls

When voer een bijhouding uit VZIG03C60T20.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_VA/VZIG/expected/VZIG03C60T20.xml voor expressie //brp:bhg_vbaRegistreerVerhuizing_R

Then in kern heeft SELECT COUNT(id) FROM kern.admhnd de volgende gegevens:
| veld                      | waarde |
| count                     | 1      |