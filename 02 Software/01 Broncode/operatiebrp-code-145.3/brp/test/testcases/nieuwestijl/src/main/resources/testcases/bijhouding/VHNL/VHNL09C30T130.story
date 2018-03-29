Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative:
Tijdzones. xxxxxxxxxxxxxxxxxxxxxxxxxxxx

Scenario:   Tijdzones. zero UTC-offset in uren en minuten zonder dubbele punt
            LT: VHNL09C30T130

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/VHNL09C30T130.xls

When voer een bijhouding uit VHNL09C30T130.xml namens partij 'Gemeente BRP 1'

Then is het foutief antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL09C30T130.txt

Then is in de database de persoon met bsn 650000201 niet als PARTNER betrokken bij een HUWELIJK