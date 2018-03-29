Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative: GBA verwerking van een Geregistreerd Partnerschap

Scenario: GBA Geregistreerd Partnerschap tussen een ingeschrevene en pseudo-persoon
          LT: GGNL02C10T20

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-GGNL/GGNL02C10T20-Marjan.xls

When voer een GBA bijhouding uit GGNL02C10T20.xml namens partij 'Migratievoorziening'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/GGNL/expected/GGNL02C10T20.xml voor expressie //brp:isc_migRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 776878025 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
Then is in de database de persoon met bsn 755302217 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
