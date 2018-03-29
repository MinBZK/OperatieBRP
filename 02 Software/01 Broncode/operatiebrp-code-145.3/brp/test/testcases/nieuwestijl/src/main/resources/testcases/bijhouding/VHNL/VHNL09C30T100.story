Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative:
Tijdzones. xxxxxxxxxxxxxxxxxxxxxxxxxxxx

Scenario:   Tijdzones. UTC-offset opgesteld in kwartieren
            LT: VHNL09C30T100

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/VHNL09C30T100.xls

When voer een bijhouding uit VHNL09C30T100.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL09C30T100.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 966183241 wel als PARTNER betrokken bij een HUWELIJK