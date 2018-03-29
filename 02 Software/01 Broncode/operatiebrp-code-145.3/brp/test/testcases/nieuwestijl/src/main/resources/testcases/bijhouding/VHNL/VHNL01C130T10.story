Meta:
@auteur                 tjlee
@status                 Klaar
@regels                 R1869
@sleutelwoorden         voltrekkingHuwelijkInNederland,TjieWah,VHNL01C130T10
@usecase                UCS-BY.HG

Narrative:
R1869 Huwelijk en geregistreerd partnerschap, voltrekking huwelijk in Nederland, actie registratieAanvangHuwelijkGeregistreerdPartnerschap

Scenario: R1869 (BRBY0417) Geen gelijktijdig ander H/GP
          LT: VHNL01C130T10

Gemeente BRP 1

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL/VHNL01C130T10-sandy.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL01C130T10-jerry.xls

When voer een bijhouding uit VHNL01C130T10.xml namens partij 'Gemeente Tiel'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL01C130T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 404941801 niet als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 7262803730 uit database en vergelijk met expected /testcases/bijhouding/VHNL/expected/VHNL01C130T10-persoon1.xml
Then lees persoon met anummer 9838793490 uit database en vergelijk met expected /testcases/bijhouding/VHNL/expected/VHNL01C130T10-persoon2.xml
