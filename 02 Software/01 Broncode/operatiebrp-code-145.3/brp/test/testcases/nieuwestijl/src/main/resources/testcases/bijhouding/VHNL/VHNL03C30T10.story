Meta:
@status                 Klaar
@sleutelwoorden         voltrekkingHuwelijkInNederland VHNL03C30T10
@usecase                UCS-BY.HG

Narrative:
Stam niet aanwezig

Scenario:   Personen Libby Thatcher (Ingeschrevene-Ingezetene, Niet NL Nat) en Piet Jansen (Ingeschrevene-Ingezetene, NL Nat) gaan trouwen, stam niet aanwezig
            LT: VHNL03C30T10




Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/VHNL02_reg_gesl_nm-Libby.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL02_reg_gesl_nm-Piet.xls

When voer een bijhouding uit VHNL03C30T10.xml namens partij 'Gemeente BRP 1'

Then is het foutief antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL03C30T10.txt

Then lees persoon met anummer 8240349473 uit database en vergelijk met expected VHNL03C30T10-persoon1.xml
Then lees persoon met anummer 9543058721 uit database en vergelijk met expected VHNL03C30T10-persoon2.xml





