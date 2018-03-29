Meta:
@auteur                 tjlee
@status                 Klaar
@regels                 R1854
@sleutelwoorden         voltrekkingHuwelijkInNederland,TjieWah,VHNL02C280T10
@usecase                UCS-BY.HG

Narrative:
R1854 Huwelijk en geregistreerd partnerschap, voltrekking huwelijk in Nederland, actie registratieAanvangHuwelijkGeregistreerdPartnerschap

Scenario: R1854 De Datum aanvang is niet gevuld bij huwelijk
          LT: VHNL02C280T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Piet.xls

When voer een bijhouding uit VHNL02C280T10.xml namens partij 'Gemeente BRP 1'

Then is het foutief antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL02C280T10.txt

Then lees persoon met anummer 5398948626 uit database en vergelijk met expected VHNL02C280T10-persoon1.xml
