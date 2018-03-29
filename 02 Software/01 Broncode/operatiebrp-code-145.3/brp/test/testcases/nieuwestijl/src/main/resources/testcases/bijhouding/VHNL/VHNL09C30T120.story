Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative:
Tijdzones. xxxxxxxxxxxxxxxxxxxxxxxxxxxx

Scenario:   Tijdzones. zero UTC-offset in uren en minuten
            LT: VHNL09C30T120

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/VHNL09C30T120.xls

When voer een bijhouding uit VHNL09C30T120.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL09C30T120.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 712497481 wel als PARTNER betrokken bij een HUWELIJK