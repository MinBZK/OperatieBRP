Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative:
Tijdzones. xxxxxxxxxxxxxxxxxxxxxxxxxxxx

Scenario:   Tijdzones. UTC (Zulu Time) zonder milliseconden
            LT: VHNL09C30T40

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/VHNL09C30T40.xls

When voer een bijhouding uit VHNL09C30T40.xml namens partij 'Gemeente BRP 1'

Then is het foutief antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL09C30T40.txt

Then is in de database de persoon met bsn 718055561 niet als PARTNER betrokken bij een HUWELIJK