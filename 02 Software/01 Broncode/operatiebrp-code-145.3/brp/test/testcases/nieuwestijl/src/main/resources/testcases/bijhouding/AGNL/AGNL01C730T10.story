Meta:
@auteur                 tjlee
@status                 Klaar
@sleutelwoorden         Geslaagd
@regels                 R1571
@usecase                UCS-BY.HG

Narrative:
R1571 Er mag slechts 1 voorkomen van geslachtsnaamcomponent aanwezig zijn

Scenario: 2 voorkomens van geslachtsnaamcomponent, 2 voorkomens van geslachtsnaamcomponent
          LT: AGNL01C730T10



Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/VHNL05C20T10-Libby.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL01C30T10-Piet.xls

When voer een bijhouding uit AGNL01C730T10.xml namens partij 'Gemeente BRP 1'

Then is het foutief antwoordbericht gelijk aan /testcases/bijhouding/AGNL/expected/AGNL01C730T10.txt
