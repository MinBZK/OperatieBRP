Meta:
@status                 Klaar
@sleutelwoorden         voltrekkingHuwelijkInNederland VHNL02C670T30
@usecase                UCS-BY.HG

Narrative:
Alleen scheidingsteken bestaat in de stamtabel

Scenario:   R2161 Personen Libby Thatcher (Ingeschrevene-Ingezetene, Niet NL Nat) en Piet Jansen (Ingeschrevene-Ingezetene, NL Nat) gaan trouwen, alleen scheidingsteken geldig
            LT: VHNL02C670T30



Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/VHNL02_reg_gesl_nm-Libby.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL02_reg_gesl_nm-Piet.xls

When voer een bijhouding uit VHNL02C670T30.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL02C670T30.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R


Then lees persoon met anummer 8240349473 uit database en vergelijk met expected /testcases/bijhouding/VHNL/expected/VHNL02C670T30-persoon1.xml
Then lees persoon met anummer 9543058721 uit database en vergelijk met expected /testcases/bijhouding/VHNL/expected/VHNL02C670T30-persoon2.xml





