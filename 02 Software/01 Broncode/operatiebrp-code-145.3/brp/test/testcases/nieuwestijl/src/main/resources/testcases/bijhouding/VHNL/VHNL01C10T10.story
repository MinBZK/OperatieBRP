Meta:
@status                 Klaar
@regels                 R1861
@sleutelwoorden         voltrekkingHuwelijkInNederland,TjieWah,VHNL01C10T10
@usecase                UCS-BY.HG

Narrative:
R1861 Huwelijk en geregistreerd partnerschap, voltrekking huwelijk in Nederland, actie registratieAanvangHuwelijkGeregistreerdPartnerschap

Scenario: R1861 (BRAL2104) H/GP mag alleen door betrokken gemeente worden geregistreerd
          LT: VHNL01C10T10



Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL/VHNL01C10T10-sandy.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL01C10T10-danny.xls

When voer een bijhouding uit VHNL01C10T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL01C10T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 366472057 niet als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 9158630674 uit database en vergelijk met expected VHNL01C10T10-persoon1.xml
Then lees persoon met anummer 3218483474 uit database en vergelijk met expected VHNL01C10T10-persoon2.xml

Then staat er 0 notificatiebericht voor bijhouders op de queue


