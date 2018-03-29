Meta:
@auteur                 fuman
@status                 Klaar
@regels                 R2044
@usecase                UCS-BY.HG

Narrative:
R2044 Gemeente aanvang relatie verplicht als Land/Gebied = Nederland

Scenario: R2044 Relatie.Land/gebied aanvang=Nederland en Relatie.Gemeente aanvang heeft geen waarde
          LT: GHNL02C280T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-GHNL/GHNL-Mila.xls

When voer een GBA bijhouding uit GHNL02C280T10.xml namens partij 'Migratievoorziening'

Then is het foutief antwoordbericht gelijk aan /testcases/bijhouding/GHNL/expected/GHNL02C280T10.txt

Then is in de database de persoon met bsn 956803593 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 189975945 niet als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 3528125729 uit database en vergelijk met expected GHNL02C280T10-persoon1.xml
