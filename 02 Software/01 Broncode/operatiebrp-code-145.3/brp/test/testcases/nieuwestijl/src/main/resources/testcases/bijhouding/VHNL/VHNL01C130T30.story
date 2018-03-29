Meta:
@auteur                 jozon
@status                 Klaar
@regels                 R1869
@sleutelwoorden         voltrekkingHuwelijkInNederland,VHNL01C130T30
@usecase                UCS-BY.HG

Narrative:
R1869 Huwelijk en geregistreerd partnerschap, voltrekking huwelijk in Nederland, actie registratieAanvangHuwelijkGeregistreerdPartnerschap

Scenario: R1869 (BRBY0417) Geen gelijktijdig ander H/GP
          LT: VHNL01C130T30

Gemeente BRP 1

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL/VHNL01C130T20-sandy.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL01C130T10-jerry.xls
And de database is aangepast met: update kern.relatie set dateinde=to_number((to_char(now() + interval '1 day', 'YYYYMMDD')), '99999999') where dateinde=20150101
And de database is aangepast met: update kern.his_relatie set dateinde=to_number((to_char(now() + interval '1 day', 'YYYYMMDD')), '99999999') where dateinde=20150101

When voer een bijhouding uit VHNL01C130T30.xml namens partij 'Gemeente Tiel'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL01C130T30.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 404941801 niet als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 7262803730 uit database en vergelijk met expected /testcases/bijhouding/VHNL/expected/VHNL01C130T30-persoon1.xml
Then lees persoon met anummer 9838793490 uit database en vergelijk met expected /testcases/bijhouding/VHNL/expected/VHNL01C130T30-persoon2.xml
