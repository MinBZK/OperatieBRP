Meta:
@status                 Klaar
@regels                 R1648
@usecase                UCS-BY.HG

Narrative: R1648 Bij aanvang relatie in buitenland is buitenlandse regio of buitenlandse plaats verplicht

Scenario: Landcode ongelijk aan 6030 en 0000 en 9999 velden Relatie.Buitenlandse plaats aanvang en Relatie.Buitenlandse regio aanvang gevuld
          LT: AGBL02C10T40

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AGBL/AGBL02C10T40-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AGBL/AGBL02C10T40-002.xls

When voer een bijhouding uit AGBL02C10T40.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/AGBL/expected/AGBL02C10T40.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 644277129 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
Then is in de database de persoon met bsn 703542345 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
