Meta:
@auteur                 tjlee
@status                 Klaar
@regels                 R2098
@usecase                UCS-BY.HG

Narrative:
R2098 Bijhouding geblokkeerd wegens verhuizing xxxxxxxxxxxxxxxxxxxxxxxx

Scenario: R2098 1 blokkering door verhuizing xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
          LT: AGNL01C130T10

Gemeente BRP 1

Given alle personen zijn verwijderd

Given persoon met anummer 9495973153 is geblokkeerd

Given enkel initiele vulling uit bestand /LO3PL-AGNL/Marjan.xls
Given enkel initiele vulling uit bestand /LO3PL-AGNL/Victor.xls

When voer een bijhouding uit AGNL01C130T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/AGNL/expected/AGNL01C130T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 221087849 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
Then is in de database de persoon met bsn 110477200 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
