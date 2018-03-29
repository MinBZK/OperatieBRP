Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R1807 Lengte van de voornamen mag bij elkaar opgeteld niet langer dan 200 karakters zijn

Scenario:   Een voornaam van 200 characters xxxxxxxxx
            LT: GBNL02C70T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL02C70.xls

When voer een bijhouding uit GBNL02C70T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GBNL/expected/GBNL02C70T10.xml voor expressie //brp:bhg_afsRegistreerGeboorte_R

