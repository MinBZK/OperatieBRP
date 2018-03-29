Meta:
@auteur                 tjlee
@status                 Klaar
@regels                 R2039
@sleutelwoorden         voltrekkingHuwelijkInNederland,TjieWah,VHNL02C590T10
@usecase                UCS-BY.HG

Narrative:
R2039 Huwelijk en geregistreerd partnerschap, voltrekking huwelijk in Nederland, actie registratieAanvangHuwelijkGeregistreerdPartnerschap

Scenario: R2039 Woonplaatsnaam aanvang relatie mag alleen gevuld zijn als ook Gemeente gevuld is
          LT: VHNL02C590T10



Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Piet.xls
Given enkel initiele vulling uit bestand /LO3PL/Libby.xls

When voer een bijhouding uit VHNL02C590T10.xml namens partij 'Gemeente BRP 1'

Then is het foutief antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL02C590T10.txt

Then lees persoon met anummer 5398948626 uit database en vergelijk met expected VHNL02C590T10-persoon1.xml
Then lees persoon met anummer 1868196961 uit database en vergelijk met expected VHNL02C590T10-persoon2.xml
