Meta:
@status                 Klaar
@usecase                UCS-BY.0.VA

Narrative: R2255 Gemeente verordening moet verwijzen naar een geldig stamgegeven

Scenario: Datum aanvang adreshouding op Partij.Datingang
            LT: VZIG02C250T20

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-VZIG/VZIG02C250T20-001.xls

When voer een bijhouding uit VZIG02C250T20.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding_VA/VZIG/expected/VZIG02C250T20.xml voor expressie //brp:bhg_vbaRegistreerVerhuizing_R

Then in kern heeft SELECT COUNT(id) FROM kern.admhnd de volgende gegevens:
| veld                      | waarde |
| count                     | 2      |