Meta:
@status                 Klaar
@regels                 R1860
@usecase                UCS-BY.HG

Narrative:
R1860 Datum einde H/GP moet geldige kalenderdatum zijn bij einde in NL

Scenario: Datum einde waarbij mm onbekend is (2005-00-01)
          LT: EGNL01C20T20

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-EGNL/EGNL01C70-April.xls

Given pas laatste relatie van soort 2 aan tussen persoon 594645001 en persoon 212737065 met relatie id 2000003 en betrokkenheid id 2000004

When voer een bijhouding uit EGNL01C20T20.xml namens partij 'Gemeente BRP 1'

Then is het foutief antwoordbericht gelijk aan /testcases/bijhouding/EGNL/expected/EGNL01C20T20.txt