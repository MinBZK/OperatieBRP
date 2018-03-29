Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R1687 Reden verlies moet verwijzen naar bestaand stamgegeven

Scenario:   Nationaliteit.Reden verlies is geen stamgegeven
            LT: GNNG03C10T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GNNG/GNNG03C10T10-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GNNG/GNNG03C10T10-002.xls

When voer een bijhouding uit GNNG03C10T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GNNG/expected/GNNG03C10T10.xml voor expressie /