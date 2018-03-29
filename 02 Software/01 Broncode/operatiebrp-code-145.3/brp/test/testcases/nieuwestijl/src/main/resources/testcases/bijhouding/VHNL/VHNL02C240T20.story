Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative: R1836 Nevenactie mag alleen betrekking hebben op een hoofdpersoon

Scenario: Nevenactie waarbij 1 partner die geen betrekking heeft op de hoofdactie
          LT: VHNL02C240T20

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL/VHNL02C240T20-sandy.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL02C240T20-danny.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL02C240T20-jerry.xls

When voer een bijhouding uit VHNL02C240T20.xml namens partij 'Gemeente Tiel'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL02C240T20.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 619885385 niet als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 3174746386 uit database en vergelijk met expected VHNL02C240T20-persoon1.xml
Then lees persoon met anummer 1502805746 uit database en vergelijk met expected VHNL02C240T20-persoon2.xml
