Meta:
@status                 Klaar
@regels                 R1636
@usecase                UCS-BY.HG

Narrative:
R1636 (BRAL2111) In een H/GP-relatie zijn twee partners betrokken

Scenario: In een H-relatie is 1 partner betrokken,
          LT: AGNL01C220T10



Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AGNL/Victor.xls

When voer een bijhouding uit AGNL01C220T10.xml namens partij 'Gemeente BRP 1'

Then is het foutief antwoordbericht gelijk aan /testcases/bijhouding/AGNL/expected/AGNL01C220T10.txt









