Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R2435 Gemeente geboorte van een ingezetene moet registergemeente zijn

Scenario:   Geboorte gemeente ongelijk aan partij van de bron
            LT: GBNL01C230T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/mama.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/papa.xls

When voer een bijhouding uit GBNL01C230T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GBNL/expected/GBNL01C230T10.xml voor expressie //brp:bhg_afsRegistreerGeboorte_R

