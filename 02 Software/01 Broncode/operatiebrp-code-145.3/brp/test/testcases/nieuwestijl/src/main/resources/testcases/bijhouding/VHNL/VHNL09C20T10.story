Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative:
Datumformaten. Punt als scheidingsteken van de datumcomponenten

Scenario:   Punt als scheidingsteken van de datumcomponenten
            LT: VHNL09C20T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Libby.xls

When voer een bijhouding uit VHNL09C20T10.xml namens partij 'Gemeente BRP 1'

Then is het foutief antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL09C20T10.txt

Then is in de database de persoon met bsn 422531881 niet als PARTNER betrokken bij een HUWELIJK