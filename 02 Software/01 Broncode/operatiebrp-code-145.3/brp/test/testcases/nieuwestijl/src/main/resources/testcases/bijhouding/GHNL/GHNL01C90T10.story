Meta:
@auteur                 tjlee
@status                 Klaar
@regels                 R2145
@usecase                UCS-BY.HG

Narrative:
R2145 Geslachtsnaamstam in Samengestelde naam is verplicht

Scenario: R2145 Geslachtsnaamstam leeg bij Onbekend Persoon
          LT: GHNL01C90T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-GHNL/GHNL-Mila.xls

When voer een GBA bijhouding uit GHNL01C90T10.xml namens partij 'Migratievoorziening'

Then is het foutief antwoordbericht gelijk aan /testcases/bijhouding/GHNL/expected/GHNL01C90T10.txt

Then lees persoon met anummer 3528125729 uit database en vergelijk met expected GHNL01C90T10-persoon1.xml
