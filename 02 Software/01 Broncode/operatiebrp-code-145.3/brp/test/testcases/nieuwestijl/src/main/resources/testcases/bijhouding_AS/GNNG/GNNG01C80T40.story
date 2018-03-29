Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R1692 Een te verkrijgen nationaliteit mag nog niet voorkomen bij persoon

Scenario:   Een te verkrijgen nationaliteit komt voor bij een geborene met dezelfde DAG registratie nationaliteit
            LT: GNNG01C80T40

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GNNG/GNNG01C80T40-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GNNG/GNNG01C80T40-002.xls

When voer een bijhouding uit GNNG01C80T40.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GNNG/expected/GNNG01C80T40.xml voor expressie //brp:bhg_afsRegistreerGeboorte_R