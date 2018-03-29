Meta:
@status                 Klaar
@sleutelwoorden         voltrekkingHuwelijkInNederland
@usecase                UCS-BY.HG

Narrative:
R1636 (BRAL2111) In een H/GP-relatie zijn twee partners betrokken

Scenario: In een H-relatie is 1 partner betrokken,
          LT: VHNL01C100T10



Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Libby.xls

When voer een bijhouding uit VHNL01C100T10.xml namens partij 'Gemeente BRP 1'

Then is het foutief antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL01C100T10.txt

Then lees persoon met anummer 1868196961 uit database en vergelijk met expected VHNL01C100T10-persoon1.xml









