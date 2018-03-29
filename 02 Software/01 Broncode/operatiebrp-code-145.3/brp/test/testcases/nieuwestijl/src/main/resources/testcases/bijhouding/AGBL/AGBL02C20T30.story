Meta:
@status                 Klaar
@regels                 R2028
@usecase                UCS-BY.HG

Narrative: R2028 Bij aanvang relatie met Land/gebied = Nederland zijn geen buitenlandse locatiegegevens toegestaan

Scenario: Landcode = 9999  velden Relatie.Buitenlandse plaats aanvang en Relatie.Buitenlandse regio aanvang  gevuld
          LT: AGBL02C20T30

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AGBL/AGBL-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AGBL/AGBL-002.xls

When voer een bijhouding uit AGBL02C20T30.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/AGBL/expected/AGBL02C20T30.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 480112745 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
Then is in de database de persoon met bsn 195517441 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP






