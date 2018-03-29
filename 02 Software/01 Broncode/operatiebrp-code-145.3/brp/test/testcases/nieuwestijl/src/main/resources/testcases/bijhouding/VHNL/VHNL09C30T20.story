Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative:
Tijdzones. xxxxxxxxxxxxxxxxxxxxxxxxxxxx

Scenario:   Tijdzones. lokale tijd zonder timezone info en zonder milliseconden
            LT: VHNL09C30T20

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/VHNL09C30T20.xls

When voer een bijhouding uit VHNL09C30T20.xml namens partij 'Gemeente BRP 1'

Then is het foutief antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL09C30T20.txt

Then is in de database de persoon met bsn 831998921 niet als PARTNER betrokken bij een HUWELIJK