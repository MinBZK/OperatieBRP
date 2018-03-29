Meta:
@auteur                 tjlee
@status                 Klaar
@regels                 R2167
@sleutelwoorden         voltrekkingHuwelijkInNederland,registratieNaamgebruik,TjieWah,VHNL03C90T10
@usecase                UCS-BY.HG

Narrative:
R2167 Huwelijk en geregistreerd partnerschap, voltrekking huwelijk in Nederland, actie registratieAanvangHuwelijkGeregistreerdPartnerschap, registratieNaamgebruik

Scenario: R2167 Persoon.Naamgebruik afgeleid is niet ingevuld
          LT: VHNL03C90T10

Gemeente BRP 1

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL/VHNL03C90-sandy.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL03C90-danny.xls

When voer een bijhouding uit VHNL03C90T10.xml namens partij 'Gemeente Tiel'

Then is het foutief antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL03C90T10.txt

Then lees persoon met anummer 9576485650 uit database en vergelijk met expected VHNL03C90T10-persoon1.xml
Then lees persoon met anummer 3604914962 uit database en vergelijk met expected VHNL03C90T10-persoon2.xml
