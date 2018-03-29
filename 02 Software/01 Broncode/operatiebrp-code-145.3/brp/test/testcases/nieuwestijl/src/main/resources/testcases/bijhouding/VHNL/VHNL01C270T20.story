Meta:
@status          Klaar
@regels          R2098
@usecase         UCS-BY.HG

Narrative:
R2098 Bijhouding geblokkeerd wegens verhuizing

Scenario:   R2098 2 blokkeringen wegens een verhuizing
            LT: VHNL01C270T20

Gemeente BRP 1
Given alle personen zijn verwijderd

Given persoon met anummer 3865472018 is geblokkeerd
Given persoon met anummer 3984620818 is geblokkeerd

Given enkel initiele vulling uit bestand /LO3PL/VHNL01C270T20-Anne.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL01C270T20-Jan.xls

When voer een bijhouding uit VHNL01C270T20.xml namens partij 'Gemeente Tiel'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL01C270T20.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 620662153 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 657194761 niet als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 3865472018 uit database en vergelijk met expected VHNL01C270T20-persoon1.xml
Then lees persoon met anummer 3984620818 uit database en vergelijk met expected VHNL01C270T20-persoon2.xml
