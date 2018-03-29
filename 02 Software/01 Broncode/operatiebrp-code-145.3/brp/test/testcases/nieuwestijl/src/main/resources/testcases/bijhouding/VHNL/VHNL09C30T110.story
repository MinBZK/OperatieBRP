Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative:
Tijdzones. xxxxxxxxxxxxxxxxxxxxxxxxxxxx

Scenario:   Tijdzones. UTC-offset waarbij de sconden geen nul zijn
            LT: VHNL09C30T110

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/VHNL09C30T110.xls

When voer een bijhouding uit VHNL09C30T110.xml namens partij 'Gemeente BRP 1'

Then is het foutief antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL09C30T110.txt

Then is in de database de persoon met bsn 635667721 niet als PARTNER betrokken bij een HUWELIJK