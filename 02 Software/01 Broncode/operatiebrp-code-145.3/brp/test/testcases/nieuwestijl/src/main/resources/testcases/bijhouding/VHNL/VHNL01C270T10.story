Meta:
@auteur                 tjlee
@status                 Klaar
@regels                 R2098
@usecase                UCS-BY.HG

Narrative:
R2098 Bijhouding geblokkeerd wegens verhuizing xxxxxxxxxxxxxxxxxxxxxxxx

Scenario: R2098 1 blokkering door verhuizing xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
          LT: VHNL01C270T10

Gemeente BRP 1

Given alle personen zijn verwijderd

Given persoon met anummer 2907879058 is geblokkeerd

Given enkel initiele vulling uit bestand /LO3PL/VHNL01C270T10-Anne.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL01C270T10-Jan.xls

When voer een bijhouding uit VHNL01C270T10.xml namens partij 'Gemeente Tiel'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL01C270T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 960148425 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 822795401 niet als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 2907879058 uit database en vergelijk met expected VHNL01C270T10-persoon1.xml
Then lees persoon met anummer 1939836050 uit database en vergelijk met expected VHNL01C270T10-persoon2.xml
