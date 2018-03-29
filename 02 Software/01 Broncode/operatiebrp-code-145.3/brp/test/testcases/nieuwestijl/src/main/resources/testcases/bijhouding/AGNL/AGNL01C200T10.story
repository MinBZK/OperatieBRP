Meta:
@auteur                 fuman
@status                 Klaar
@regels                 R1585
@sleutelwoorden         AGNL01C200T10, Geslaagd
@usecase                UCS-BY.HG


Narrative:
Anummer voldoet niet aan 11-proof

Scenario:   Personen Libby Thatcher (Ingeschrevene-Ingezetene, Niet NL Nat) en Piet Jansen (Onbekende) gaan trouwen, Anummer voldoet niet aan 11-proof
            LT: AGNL01C200T10



Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AGNL/AGNL_reg_gesl_nm-Libby.xls

When voer een bijhouding uit AGNL01C200T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/AGNL/expected/AGNL01C200T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 690020041 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP









