Meta:
@status                 Klaar
@sleutelwoorden         voltrekkingHuwelijkInNederland VHNL02C670T20
@usecase                UCS-BY.HG

Narrative:
Combinatie scheidingsteken en voorvoegsel bestaat niet in de stamtabel

Scenario:   R2161 Personen Libby Thatcher (Ingeschrevene-Ingezetene, Niet NL Nat) en Piet Jansen (Ingeschrevene-Ingezetene, NL Nat) gaan trouwen, combinatie scheidingsteken en voorvoegsel ongeldig
            LT: VHNL02C670T20



Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/VHNL02_reg_gesl_nm-Libby.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL02_reg_gesl_nm-Piet-btl-nat.xls

When voer een bijhouding uit VHNL02C670T20.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL02C670T20.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R


Then lees persoon met anummer 8240349473 uit database en vergelijk met expected /testcases/bijhouding/VHNL/expected/VHNL02C670T20-persoon1.xml
Then lees persoon met anummer 1514974049 uit database en vergelijk met expected /testcases/bijhouding/VHNL/expected/VHNL02C670T20-persoon2.xml







