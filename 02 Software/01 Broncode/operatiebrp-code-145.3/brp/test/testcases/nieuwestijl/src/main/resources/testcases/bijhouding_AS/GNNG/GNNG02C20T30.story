Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R1706 Reden verlies moet verwijzen naar geldig stamgegeven

Scenario:   Reden verlies verwijst op Datum einde geldigheid naar een stamgegeven dat niet meer geldig is
            LT: GNNG02C20T30

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GNNG/GNNG02C20T30-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GNNG/GNNG02C20T30-002.xls

When voer een bijhouding uit GNNG02C20T30.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GNNG/expected/GNNG02C20T30.xml voor expressie //brp:bhg_afsRegistreerGeboorte_R