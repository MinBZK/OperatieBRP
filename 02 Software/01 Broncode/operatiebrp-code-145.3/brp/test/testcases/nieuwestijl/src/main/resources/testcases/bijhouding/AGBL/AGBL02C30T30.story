Meta:
@status                 Klaar
@regels                 R2048
@usecase                UCS-BY.HG

Narrative: R2048 Omschrijving locatie bij aanvang relatie verplicht als land/gebied gelijk is aan onbekend of internationaal gebied

Scenario: Landcode = 0000  velden Relatie.Omschrijving locatie aanvang  gevuld
          LT: AGBL02C30T30

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AGBL/AGBL02C30T30-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AGBL/AGBL02C30T30-002.xls

When voer een bijhouding uit AGBL02C30T30.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/AGBL/expected/AGBL02C30T30.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 840207049 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
Then is in de database de persoon met bsn 514311241 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP






