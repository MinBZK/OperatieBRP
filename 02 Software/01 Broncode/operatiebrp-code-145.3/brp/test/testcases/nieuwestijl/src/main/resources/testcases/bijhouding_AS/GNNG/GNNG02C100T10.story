Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R2545 Reden mag alleen ingevuld zijn bij verlies van Nederlandse nationaliteit

Scenario:   Reden verlies einde nationaliteit is ingevuld bij verlies van een buitenlandse nationaliteit
            LT: GNNG02C100T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GNNG/GNNG02C100-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GNNG/GNNG02C100-002.xls

When voer een bijhouding uit GNNG02C100T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GNNG/expected/GNNG02C100T10.xml voor expressie //brp:bhg_afsRegistreerGeboorte_R