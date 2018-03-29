Meta:
@status                 Klaar
@sleutelwoorden         Geslaagd
@usecase                UCS-BY.HG

Narrative:
Huwelijk waarbij beide partners geen nationaliteit hebben

Scenario:   Personen Libby Thatcher (Ingeschrevene-Ingezetene, geen Nat) en Piet Jansen (Ingeschrevene-Ingezetene, geen Nat) gaan trouwen, controleer relatie, betrokkenheid, afgeleid administratief,
            LT: VHNL01C30T50



Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/VHNL01C30T50-Libby.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL01C30T50-Piet.xls

When voer een bijhouding uit VHNL01C30T50.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL01C30T50.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 410985673 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 725042953 wel als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 4014026529 uit database en vergelijk met expected VHNL01C30T50-persoon1.xml
Then lees persoon met anummer 6524763425 uit database en vergelijk met expected VHNL01C30T50-persoon2.xml




