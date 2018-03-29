Meta:
@auteur                 jozon
@status                 Klaar
@regels                 R1579
@sleutelwoorden         AGNL01C70T10
@usecase                UCS-BY.HG

Narrative:
R1579 (BRAL9003) Geen bijhouden gegevens na overlijden persoon

Scenario: 1. In een Huwelijk-Geregistreerd Partnerschaprelatie is er een overleden partner.
            LT : AGNL01C70T10



Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AGNL/AGNL01C70T10-piet.xls
Given enkel initiele vulling uit bestand /LO3PL-AGNL/Marjan.xls

When voer een bijhouding uit AGNL01C70T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
And is het antwoordbericht gelijk aan /testcases/bijhouding/AGNL/expected/AGNL01C70T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 221087849 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
Then is in de database de persoon met bsn 558376617 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
