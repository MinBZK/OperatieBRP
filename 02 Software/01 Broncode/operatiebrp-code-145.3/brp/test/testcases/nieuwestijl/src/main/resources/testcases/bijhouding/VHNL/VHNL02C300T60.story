Meta:
@status                 Klaar
@regels                 R1859
@usecase                UCS-BY.HG

Narrative:
R1859 Datum aanvang H/GP moet een volledig bekende datum zijn bij aanvang in Nederland

Scenario: Datum aanvang adreshouding waarbij dd onbekend is (2016-05)
          LT: VHNL02C300T60

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL/VHNL02C300-Piet.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL02C300-Libby.xls

When voer een bijhouding uit VHNL02C300T60.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL02C300T60.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 544383849 niet als PARTNER betrokken bij een HUWELIJK
