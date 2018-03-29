Meta:
@status                 Klaar
@regels                 R2048
@usecase                UCS-BY.HG

Narrative: R2048 Omschrijving locatie bij aanvang relatie verplicht als land/gebied gelijk is aan onbekend of internationaal gebied

Scenario: Landcode = 9999  velden Relatie.Omschrijving locatie aanvang  gevuld
          LT: AGBL02C30T40

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AGBL/AGBL02C30T40-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AGBL/AGBL02C30T40-002.xls

When voer een bijhouding uit AGBL02C30T40.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/AGBL/expected/AGBL02C30T40.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 970387337 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
Then is in de database de persoon met bsn 110665223 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP






