Meta:
@auteur                 tjlee
@status                 Klaar
@sleutelwoorden         Foutief
@regels                 R2156
@usecase                UCS-BY.HG

Narrative:
R2156 Personen Libby Thatcher (Ingeschrevene-Ingezetene, Niet NL Nat) en Piet Jansen (Ingeschrevene-Ingezetene, NL Nat) gaan trouwen, Adelijke titel niet in stamtabel

Scenario:   Prevalidatie. Adelijke titel niet in stamtabel
            LT: VHNL03C10T30



Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/VHNL02_reg_gesl_nm-Libby.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL02_reg_gesl_nm-Piet.xls

When voer een bijhouding uit VHNL03C10T30.xml namens partij 'Gemeente BRP 1'

Then is het foutief antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL03C10T30.txt

Then is in de database de persoon met bsn 690020041 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 373230217 niet als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 8240349473 uit database en vergelijk met expected VHNL03C10T30-persoon1.xml
Then lees persoon met anummer 9543058721 uit database en vergelijk met expected VHNL03C10T30-persoon2.xml
