Meta:
@status                 Klaar
@regels                 R1631
@usecase                UCS-BY.HG

Narrative: R1631 Soort relatie moet verwijzen naar een geldig stamgegeven

Scenario:   RRol Onbekend is geen geldig stamgegeven voor relatie
            LT: VHNL02C100T10


Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Libby.xls

When voer een bijhouding uit VHNL02C100T10.xml namens partij 'Gemeente BRP 1'

Then is het foutief antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL02C100T10.txt

Then is in de database de persoon met bsn 422531881 niet als PARTNER betrokken bij een HUWELIJK






