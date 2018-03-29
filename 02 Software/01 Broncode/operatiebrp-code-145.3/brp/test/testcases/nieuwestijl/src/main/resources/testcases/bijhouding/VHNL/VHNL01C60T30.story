Meta:
@auteur                 tjlee
@status                 Klaar
@regels                 R1606
@sleutelwoorden         voltrekkingHuwelijkInNederland,TjieWah,VHNL01C60T10
@usecase                UCS-BY.HG

Narrative:
R1606 Huwelijk en geregistreerd partnerschap, voltrekking huwelijk in Nederland, actie registratieAanvangHuwelijkGeregistreerdPartnerschap

Scenario: R1606 Actie heeft een ongeldige waarde, documentsoort en adminstratieve handeling hebben een geldige waarde
          LT: VHNL01C60T30

Gemeente BRP 1

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/VHNL01C60-sandy.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL01C60-danny.xls

When voer een bijhouding uit VHNL01C60T30.xml namens partij 'Gemeente Tiel'


Then is het foutief antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL01C60T30.txt


