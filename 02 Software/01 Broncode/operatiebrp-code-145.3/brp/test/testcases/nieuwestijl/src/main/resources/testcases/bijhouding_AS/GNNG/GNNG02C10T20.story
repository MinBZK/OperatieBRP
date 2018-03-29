Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R1689 Reden verplicht bij verlies van Nederlandse nationaliteit

Scenario:   Reden verlies nationaliteit aanwezig bij verlies NL nationaliteit
            LT: GNNG02C10T20

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GNNG/GNNG02C10T20-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GNNG/GNNG02C10T20-002.xls

When voer een bijhouding uit GNNG02C10T20.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GNNG/expected/GNNG02C10T20.xml voor expressie //brp:bhg_afsRegistreerGeboorte_R