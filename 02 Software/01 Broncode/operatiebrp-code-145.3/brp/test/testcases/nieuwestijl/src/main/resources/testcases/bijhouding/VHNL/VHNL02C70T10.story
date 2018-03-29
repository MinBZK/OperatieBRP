Meta:
@status                 Klaar
@regels                 R1598
@usecase                UCS-BY.HG

Narrative: R1598 Rol (betrokkenheid in de relatie) moet verwijzen naar geldig stamgegeven

Scenario:   Rol Onbekend is geen geldig stamgegeven voor betrokkenheid
            LT: VHNL02C70T10


Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Libby.xls

When voer een bijhouding uit VHNL02C70T10.xml namens partij 'Gemeente BRP 1'

Then is het foutief antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL02C70T10.txt

Then is in de database de persoon met bsn 422531881 niet als PARTNER betrokken bij een HUWELIJK






