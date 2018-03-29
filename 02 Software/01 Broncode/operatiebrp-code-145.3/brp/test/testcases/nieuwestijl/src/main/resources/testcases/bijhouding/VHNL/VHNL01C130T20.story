Meta:
@auteur                 jozo
@status                 Klaar
@sleutelwoorden         Geslaagd
@regels                 R1869
@usecase                UCS-BY.HG

Narrative:
R1869 Huwelijk en geregistreerd partnerschap, voltrekking huwelijk in Nederland, actie registratieAanvangHuwelijkGeregistreerdPartnerschap

Scenario: R1869 (BRBY0417) Geen gelijktijdig ander H/GP
          LT: VHNL01C130T20

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL/VHNL01C130T20-sandy.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL01C130T10-jerry.xls
And voer enkele update uit: update kern.relatie set dateinde=to_number((to_char(now() at time zone 'UTC', 'YYYYMMDD')), '99999999') where dateinde=20150101
And voer enkele update uit: update kern.his_relatie set dateinde=to_number((to_char(now() at time zone 'UTC', 'YYYYMMDD')), '99999999') where dateinde=20150101

When voer een bijhouding uit VHNL01C130T20.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL01C130T20.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then lees persoon met anummer 7262803730 uit database en vergelijk met expected /testcases/bijhouding/VHNL/expected/VHNL01C130T20-persoon1.xml
Then lees persoon met anummer 9838793490 uit database en vergelijk met expected /testcases/bijhouding/VHNL/expected/VHNL01C130T20-persoon2.xml
