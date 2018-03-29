Meta:
@status                 Klaar
@regels                 R2048
@usecase                UCS-BY.HG

Narrative: R2048 Omschrijving locatie bij aanvang relatie verplicht als land/gebied gelijk is aan onbekend of internationaal gebied

Scenario: Landcode = 0000  velden Relatie.Omschrijving locatie aanvang niet gevuld
          LT: AGBL02C30T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AGBL/AGBL-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AGBL/AGBL-002.xls

When voer een bijhouding uit AGBL02C30T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/AGBL/expected/AGBL02C30T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 480112745 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
Then is in de database de persoon met bsn 195517441 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP






