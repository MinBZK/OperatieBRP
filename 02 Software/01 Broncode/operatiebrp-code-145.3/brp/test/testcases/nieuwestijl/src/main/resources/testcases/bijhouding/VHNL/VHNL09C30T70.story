Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative:
Tijdzones. xxxxxxxxxxxxxxxxxxxxxxxxxxxx

Scenario:   Tijdzones. UTC (Zulu Time) zonder milliseconden zonder dubbele punt
            LT: VHNL09C30T70

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/VHNL09C30T70.xls

When voer een bijhouding uit VHNL09C30T70.xml namens partij 'Gemeente BRP 1'

Then is het foutief antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL09C30T70.txt

Then is in de database de persoon met bsn 536124681 niet als PARTNER betrokken bij een HUWELIJK