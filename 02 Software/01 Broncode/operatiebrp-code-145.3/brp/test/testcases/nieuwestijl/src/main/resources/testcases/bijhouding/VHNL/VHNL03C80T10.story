Meta:
@auteur                 tjlee
@status                 Klaar
@regels                 R2159
@sleutelwoorden         voltrekkingHuwelijkInNederland,registratieNaamgebruik,TjieWah,VHNL03C80T10
@usecase                UCS-BY.HG

Narrative:
R2159 Huwelijk en geregistreerd partnerschap, voltrekking huwelijk in Nederland, actie registratieAanvangHuwelijkGeregistreerdPartnerschap, registratieNaamgebruik

Scenario: R2159 Persoon.Adellijke titel naamgebruik verwijst niet naar een stamgegeven in Adellijke titel.
          LT: VHNL03C80T10

Gemeente BRP 1


Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL/VHNL03C80-sandy.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL03C80-danny.xls

When voer een bijhouding uit VHNL03C80T10.xml namens partij 'Gemeente Tiel'

Then is het foutief antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL03C80T10.txt

Then is in de database de persoon met bsn 156960849 niet als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 3814796818 uit database en vergelijk met expected VHNL03C80T10-persoon1.xml
Then lees persoon met anummer 1686982546 uit database en vergelijk met expected VHNL03C80T10-persoon2.xml
