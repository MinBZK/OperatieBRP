Meta:
@auteur                 tjlee
@status                 Klaar
@regels                 R2163
@sleutelwoorden         Geslaagd, AGNL01C750T10
@usecase                UCS-BY.HG

Narrative:
R2163 Personen Marjan en Victor gaan trouwen, adelijke en predicaat gevuld

Scenario:   R2163 Predicaat en Adelijke titel beiden gevuld
            LT: AGNL01C750T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AGNL/Marjan-reg_geslnaam.xls
Given enkel initiele vulling uit bestand /LO3PL-AGNL/Victor.xls

When voer een bijhouding uit AGNL01C750T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/AGNL/expected/AGNL01C750T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 830053001 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
Then is in de database de persoon met bsn 110477200 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
