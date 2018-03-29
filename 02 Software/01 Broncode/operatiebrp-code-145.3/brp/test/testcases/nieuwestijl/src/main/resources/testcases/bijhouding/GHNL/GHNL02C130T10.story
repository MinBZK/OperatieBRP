Meta:
@status                 Onderhanden
@regels                 R1859
@usecase                UCS-BY.HG

Narrative: R1859 Datum aanvang Huwelijk moet geldige kalenderdatum zijn bij aanvang in NL

Scenario: R1859 Datum aanvang Huwelijk moet geldige kalenderdatum zijn bij aanvang in NL
          LT: GHNL02C130T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-GHNL/GHNL-Mila.xls
Given enkel initiele vulling uit bestand /LO3PL-GHNL/GHNL-Tim.xls

When voer een GBA bijhouding uit GHNL02C130T10.xml namens partij 'Migratievoorziening'

Then heeft het antwoordbericht verwerking Foutief
And is het antwoordbericht gelijk aan /testcases/bijhouding/GHNL/expected/GHNL02C130T10.xml voor expressie //brp:isc_migRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 979562697 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 956803593 niet als PARTNER betrokken bij een HUWELIJK

