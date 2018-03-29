Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: Geboorte in Nederland: Registratie geborene

Scenario:   Registratie geborene in Nederland met namenreeks J met voornamen meegeven
            LT: GBNL04C10T130

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL04C10T130-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL04C10T130-002.xls

When voer een bijhouding uit GBNL04C10T130.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GBNL/expected/GBNL04C10T130.xml voor expressie //brp:bhg_afsRegistreerGeboorte_R

