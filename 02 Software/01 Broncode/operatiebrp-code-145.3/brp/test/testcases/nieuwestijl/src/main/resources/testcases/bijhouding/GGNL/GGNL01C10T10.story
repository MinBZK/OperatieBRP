Meta:
@status                 Klaar
@regels                 R1848
@usecase                UCS-BY.HG

Narrative: R1848 Registersoort moet geldig zijn voor soort document

Scenario: Registersoort is niet geldig voor soort document
          LT: GGNL01C10T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-GGNL/GGNL01C10T10-Marjan.xls
Given enkel initiele vulling uit bestand /LO3PL-GGNL/GGNL01C10T10-Piet.xls

When voer een GBA bijhouding uit GGNL01C10T10.xml namens partij 'Migratievoorziening'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/GGNL/expected/GGNL01C10T10.xml voor expressie //brp:isc_migRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 988307017 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
Then is in de database de persoon met bsn 382083209 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
