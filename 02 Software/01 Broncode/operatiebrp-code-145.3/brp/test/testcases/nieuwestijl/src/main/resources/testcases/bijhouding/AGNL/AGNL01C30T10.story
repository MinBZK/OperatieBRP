Meta:
@status                 Klaar
@regels                 R1850
@sleutelwoorden         AGNL01C30T10
@usecase                UCS-BY.HG

Narrative:
Omschrijving niet gevuld bij Nederlandse registerakten

Scenario: Omschrijving gevuld bij Nederlandse akte
          LT: AGNL01C30T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AGNL/Marjan.xls

When voer een bijhouding uit AGNL01C30T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/AGNL/expected/AGNL01C30T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 221087849 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP





