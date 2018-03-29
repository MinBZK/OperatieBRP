Meta:
@status                 Klaar
@regels                 R2161
@usecase                UCS-BY.HG

Narrative:
Combinatie scheidingsteken en voorvoegsel bestaat in de stamtabel

Scenario:   R2161 Personen Libby Thatcher (Ingeschrevene-Ingezetene, Niet NL Nat) en Piet Jansen (Ingeschrevene-Ingezetene, NL Nat) gaan trouwen, combinatie scheidingsteken en voorvoegsel geldig
            LT: VHNL02C670T10

Gemeente BRP 1


Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL/VHNL02_reg_gesl_nm-Libby.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL02_reg_gesl_nm-Piet.xls

When voer een bijhouding uit VHNL02C670T10.xml namens partij 'Gemeente Tiel'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL02C670T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 373230217 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 690020041 wel als PARTNER betrokken bij een HUWELIJK