Meta:
@status                 Klaar
@regels                 R1648
@usecase                UCS-BY.HG

Narrative: R1648 Bij aanvang relatie in buitenland is buitenlandse regio of buitenlandse plaats verplicht

Scenario: Landcode ongelijk aan 6030 en 0000 en 9999 veld Relatie.Buitenlandse regio aanvang niet gevuld
          LT: AGBL02C10T30

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AGBL/AGBL02C10T30-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AGBL/AGBL02C10T30-002.xls

When voer een bijhouding uit AGBL02C10T30.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/AGBL/expected/AGBL02C10T30.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 982126025 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
Then is in de database de persoon met bsn 153995397 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
