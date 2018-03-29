Meta:
@status                 Klaar
@regels                 R1648
@usecase                UCS-BY.HG

Narrative: R1648 Bij aanvang relatie in buitenland is buitenlandse regio of buitenlandse plaats verplicht

Scenario: Landcode ongelijk aan 6030 en 0000 en 9999 veld Relatie.Buitenlandse plaats aanvang niet gevuld
          LT: AGBL02C10T20

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AGBL/AGBL02C10T20-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AGBL/AGBL02C10T20-002.xls

When voer een bijhouding uit AGBL02C10T20.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/AGBL/expected/AGBL02C10T20.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 173056465 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
Then is in de database de persoon met bsn 955392329 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP






