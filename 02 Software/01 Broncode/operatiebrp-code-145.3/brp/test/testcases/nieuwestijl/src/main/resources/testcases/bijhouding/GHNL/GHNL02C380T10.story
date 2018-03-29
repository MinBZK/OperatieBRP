Meta:
@auteur                 fuman
@status                 Klaar
@regels                 R1585
@usecase                UCS-BY.HG


Narrative:
R1585 Anummer is > 10 posities

Scenario:   R1585 Personen Mila (Ingeschrevene-Ingezetene, Niet NL Nat) en Ab Adriaan (Onbekende) gaan trouwen, Anummer is > 10 posities
            LT: GHNL02C380T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-GHNL/GHNL-Mila.xls

When voer een GBA bijhouding uit GHNL02C380T10.xml namens partij 'Migratievoorziening'

Then is het foutief antwoordbericht gelijk aan /testcases/bijhouding/GHNL/expected/GHNL02C380T10.txt

Then is in de database de persoon met bsn 956803593 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 189975945 niet als PARTNER betrokken bij een HUWELIJK
