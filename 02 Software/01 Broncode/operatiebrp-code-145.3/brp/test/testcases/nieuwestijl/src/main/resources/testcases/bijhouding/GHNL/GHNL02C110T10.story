Meta:
@auteur                 fuman
@status                 Klaar
@regels                 R1854
@usecase                UCS-BY.HG

Narrative:
R1854 Datum aanvang moet een waarde hebben bij Huwelijk of Geregistreerd partnerschap

Scenario: R1854 Datum aanvang is niet gevuld bij huwelijk
          LT: GHNL02C110T10



Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-GHNL/GHNL-Mila.xls

When voer een GBA bijhouding uit GHNL02C110T10.xml namens partij 'Migratievoorziening'

Then is het foutief antwoordbericht gelijk aan /testcases/bijhouding/GHNL/expected/GHNL02C110T10.txt

Then is in de database de persoon met bsn 956803593 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 189975945 niet als PARTNER betrokken bij een HUWELIJK
