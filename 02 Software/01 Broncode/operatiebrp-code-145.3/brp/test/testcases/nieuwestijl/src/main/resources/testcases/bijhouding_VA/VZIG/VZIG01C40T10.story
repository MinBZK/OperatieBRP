Meta:
@status                 Klaar
@usecase                UCS-BY.0.VA
@regels                 R1911

Narrative: R1911 Verstrekkingsbeperking moet mogelijk zijn voor specieke partij

Scenario:   Specifieke verstrekkingsbeperking Partij gevuld
            LT: VZIG01C40T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-VZIG/VZIG-001.xls

When voer een bijhouding uit VZIG01C40T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_VA/VZIG/expected/VZIG01C40T10.xml voor expressie //brp:bhg_vbaRegistreerVerhuizing_R

Then in kern heeft SELECT COUNT(id) FROM kern.admhnd de volgende gegevens:
| veld                      | waarde |
| count                     | 1      |


