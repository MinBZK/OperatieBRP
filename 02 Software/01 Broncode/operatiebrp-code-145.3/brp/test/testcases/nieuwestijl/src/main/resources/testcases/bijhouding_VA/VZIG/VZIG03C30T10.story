Meta:
@status                 Klaar
@usecase                UCS-BY.0.VA

Narrative: R2316 Woonplaatsnaam moet verwijzen naar een bestaand stamgegeven

Scenario: Woonplaatsnaam is geen bestaand stamgegeven
          LT: VZIG03C30T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-VZIG/VZIG-001.xls

When voer een bijhouding uit VZIG03C30T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_VA/VZIG/expected/VZIG03C30T10.xml voor expressie //brp:bhg_vbaRegistreerVerhuizing_R

Then in kern heeft SELECT COUNT(id) FROM kern.admhnd de volgende gegevens:
| veld                      | waarde |
| count                     | 1      |