Meta:
@auteur                 tjlee
@status                 Klaar
@regels                 R2161
@sleutelwoorden         Geslaagd, AGNL01C740T10
@usecase                UCS-BY.HG

Narrative:
R2161 Combinatie scheidingsteken en voorvoegsel bestaat niet in de stamtabel

Scenario:   R2161 Personen Marjan Victor gaan trouwen, combinatie scheidingsteken en voorvoegsel ongeldig
            LT: AGNL01C740T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AGNL/Marjan-reg_geslnaam.xls
Given enkel initiele vulling uit bestand /LO3PL-AGNL/Victor.xls

When voer een bijhouding uit AGNL01C740T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/AGNL/expected/AGNL01C740T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R
