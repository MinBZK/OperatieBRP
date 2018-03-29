Meta:
@auteur                 fuman
@status                 Klaar
@regels                 R1587
@sleutelwoorden         voltrekkingHuwelijkInNederland
@usecase                UCS-BY.HG


Narrative:
BSN voldoet niet aan 11-proof

Scenario:   Personen Libby Thatcher (Ingeschrevene-Ingezetene, Niet NL Nat) en Piet Jansen (Onbekende) gaan trouwen, BSN voldoet niet aan 11-proof
            LT: VHNL02C820T30



Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/VHNL02_reg_gesl_nm-Libby.xls

When voer een bijhouding uit VHNL02C820T30.xml namens partij 'Gemeente BRP 1'

Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL02C820T30.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 690020041 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 373230217 niet als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 8240349473 uit database en vergelijk met expected VHNL02C820T30-persoon1.xml












