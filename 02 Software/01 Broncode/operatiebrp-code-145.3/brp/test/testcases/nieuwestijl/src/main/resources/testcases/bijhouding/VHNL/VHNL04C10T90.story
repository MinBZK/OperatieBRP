Meta:
@status                 Klaar
@regels                 R1812
@sleutelwoorden         voltrekkingHuwelijkInNederland
@usecase                UCS-BY.HG

Narrative:
Verwerken Groep Samengestelde naam zonder namenreeks

Scenario:   Personen Libby Thatcher (Ingeschrevene-Ingezetene, Niet NL Nat) en Piet Jansen (Onbekende) gaan trouwen, Verwerken Groep Samengestelde naam zonder namenreeks
            LT: VHNL04C10T90

Gemeente BRP 1

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/VHNL02_reg_gesl_nm-Libby.xls

When voer een bijhouding uit VHNL04C10T90.xml namens partij 'Gemeente Tiel'

Then is het foutief antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL04C10T90.txt

Then is in de database de persoon met bsn 690020041 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 793558025 niet als PARTNER betrokken bij een HUWELIJK








