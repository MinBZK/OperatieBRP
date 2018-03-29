Meta:
@auteur                 tjlee
@status                 Klaar
@regels                 R2176
@sleutelwoorden         voltrekkingHuwelijkInNederland,registratieNaamgebruik,TjieWah,VHNL03C100T10
@usecase                UCS-BY.HG

Narrative:
R2176 Huwelijk en geregistreerd partnerschap, voltrekking huwelijk in Nederland, actie registratieAanvangHuwelijkGeregistreerdPartnerschap, registratieNaamgebruik

Scenario: R2176 Persoon.Naamgebruik is niet ingevuld.
          LT: VHNL03C100T10

Gemeente BRP 1

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL/VHNL03C100-sandy.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL03C100-danny.xls

When voer een bijhouding uit VHNL03C100T10.xml namens partij 'Gemeente Tiel'

Then is het foutief antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL03C100T10.txt

Then lees persoon met anummer 3814796818 uit database en vergelijk met expected VHNL03C100T10-persoon1.xml
Then lees persoon met anummer 1686982546 uit database en vergelijk met expected VHNL03C100T10-persoon2.xml
