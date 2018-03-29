Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative:
Tijdzones. xxxxxxxxxxxxxxxxxxxxxxxxxxxx

Scenario:   Tijdzones. lokale tijd zonder timezone info
            LT: VHNL09C30T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/VHNL09C30T10.xls

When voer een bijhouding uit VHNL09C30T10.xml namens partij 'Gemeente BRP 1'

Then is het foutief antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL09C30T10.txt

Then is in de database de persoon met bsn 803191881 niet als PARTNER betrokken bij een HUWELIJK