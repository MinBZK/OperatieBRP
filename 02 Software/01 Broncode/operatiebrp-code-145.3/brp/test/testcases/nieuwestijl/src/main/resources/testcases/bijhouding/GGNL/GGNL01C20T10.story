Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative: R2348 Partij van de Administratieve handeling moet gelijk zijn aan de Zendende partij

Scenario: Zendende partij en de Partij van de Administratieve handeling zijn ongelijk
          LT: GGNL01C20T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-GGNL/GGNL01C20T10-001.xls
Given enkel initiele vulling uit bestand /LO3PL-GGNL/GGNL01C20T10-002.xls

When voer een GBA bijhouding uit GGNL01C20T10.xml namens partij 'Migratievoorziening'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/GGNL/expected/GGNL01C20T10.xml voor expressie //brp:isc_migRegistreerHuwelijkGeregistreerdPartnerschap_R
