Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative:
Tijdzones. xxxxxxxxxxxxxxxxxxxxxxxxxxxx

Scenario:   Tijdzones. UTC (Zulu Time) met milliseconden
            LT: VHNL09C30T30

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/VHNL09C30T30.xls

When voer een bijhouding uit VHNL09C30T30.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL09C30T30.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 233051521 wel als PARTNER betrokken bij een HUWELIJK