Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative:
Tijdzones. xxxxxxxxxxxxxxxxxxxxxxxxxxxx

Scenario:   Tijdzones. negatieve offset waarbij de seconden in de offset geen nul zijn
            LT: VHNL09C30T190

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/VHNL09C30T190.xls

When voer een bijhouding uit VHNL09C30T190.xml namens partij 'Gemeente BRP 1'

Then is het foutief antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL09C30T190.txt

Then is in de database de persoon met bsn 500654505 niet als PARTNER betrokken bij een HUWELIJK