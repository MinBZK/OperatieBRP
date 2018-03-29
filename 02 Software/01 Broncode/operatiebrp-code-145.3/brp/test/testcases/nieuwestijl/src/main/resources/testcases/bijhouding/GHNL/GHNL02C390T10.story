Meta:
@auteur                 fuman
@status                 Klaar
@regels                 R1587
@usecase                UCS-BY.HG


Narrative:
BSN is > 9 posities

Scenario:   Personen Mila (Ingeschrevene-Ingezetene, Niet NL Nat) en Ab (Onbekende) gaan trouwen, BSN is > 9 posities
            LT: GHNL02C390T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-GHNL/GHNL-Mila.xls

When voer een GBA bijhouding uit GHNL02C390T10.xml namens partij 'Migratievoorziening'

Then is het foutief antwoordbericht gelijk aan /testcases/bijhouding/GHNL/expected/GHNL02C390T10.txt

Then is in de database de persoon met bsn 956803593 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 189975945 niet als PARTNER betrokken bij een HUWELIJK
