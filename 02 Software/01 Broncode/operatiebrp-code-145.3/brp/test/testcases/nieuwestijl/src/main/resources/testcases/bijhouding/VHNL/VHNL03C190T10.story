Meta:
@auteur                 tjlee
@status                 Klaar
@regels                 R2172
@sleutelwoorden         voltrekkingHuwelijkInNederland,TjieWah,VHNL03C190T10
@usecase                UCS-BY.HG

Narrative:
R2172 Huwelijk en geregistreerd partnerschap, voltrekking huwelijk in Nederland, actie registratieAanvangHuwelijkGeregistreerdPartnerschap

Scenario: R2172 Geslachtsaanduiding in Geslachtsaanduiding is ingevuld
          LT: VHNL03C190T10



Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/VHNL03C190-sandy.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL03C190-danny.xls

When voer een bijhouding uit VHNL03C190T10.xml namens partij 'Gemeente BRP 1'

Then is het foutief antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL03C190T10.txt

Then lees persoon met anummer 3814796818 uit database en vergelijk met expected VHNL03C190T10-persoon1.xml
Then lees persoon met anummer 1686982546 uit database en vergelijk met expected VHNL03C190T10-persoon2.xml
