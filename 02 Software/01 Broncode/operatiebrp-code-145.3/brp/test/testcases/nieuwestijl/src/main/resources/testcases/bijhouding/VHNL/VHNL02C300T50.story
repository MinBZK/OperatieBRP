Meta:
@status                 Klaar
@regels                 R1859
@usecase                UCS-BY.HG

Narrative:
R1859 Datum aanvang H/GP moet een volledig bekende datum zijn bij aanvang in Nederland

Scenario: Datum aanvang adreshouding waarbij mm onbekend is (2005-00-01)
          LT: VHNL02C300T50


Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL/VHNL02C300-Piet.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL02C300-Libby.xls

When voer een bijhouding uit VHNL02C300T50.xml namens partij 'Gemeente BRP 1'

Then is het foutief antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL02C300T50.txt
