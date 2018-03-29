Meta:
@auteur                 fuman
@status                 Klaar
@regels                 R1569
@usecase                UCS-BY.HG


Narrative:
Geslachtsaanduiding niet in stamtabel

Scenario:   Personen Mila (Ingeschrevene-Ingezetene) en Ab (Onbekende) gaan trouwen, Geslachtsaanduiding niet in stamtabel
            LT: GHNL02C40T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-GHNL/GHNL-Mila.xls

When voer een GBA bijhouding uit GHNL02C40T10.xml namens partij 'Migratievoorziening'

Then is het foutief antwoordbericht gelijk aan /testcases/bijhouding/GHNL/expected/GHNL02C40T10.txt

Then is in de database de persoon met bsn 956803593 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 189975945 niet als PARTNER betrokken bij een HUWELIJK





