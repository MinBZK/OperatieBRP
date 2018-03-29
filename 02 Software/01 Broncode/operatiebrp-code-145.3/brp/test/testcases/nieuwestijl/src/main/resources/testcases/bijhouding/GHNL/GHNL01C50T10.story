Meta:
@auteur                 fuman
@status                 Klaar
@regels                 R1636
@usecase                UCS-BY.HG

Narrative:
R1636 (BRAL2111) In een H/GP-relatie zijn twee partners betrokken

Scenario: In een H-relatie is 1 partner betrokken,
          LT: GHNL01C50T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-GHNL/GHNL-Mila.xls

When voer een GBA bijhouding uit GHNL01C50T10.xml namens partij 'Migratievoorziening'

Then is het foutief antwoordbericht gelijk aan /testcases/bijhouding/GHNL/expected/GHNL01C50T10.txt

Then lees persoon met anummer 3528125729 uit database en vergelijk met expected GHNL01C50T10-persoon1.xml









