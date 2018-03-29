Meta:
@status                 Klaar
@regels                 R1859
@usecase                UCS-BY.HG

Narrative: R1859 Datum aanvang H/GP moet een volledig bekende datum zijn bij aanvang in Nederland

Scenario: R1859 Datum aanvang HGP moet geldige kalenderdatum zijn bij aanvang in NL, Datum aanvang is geen geldige kalenderdatum(0000) bij huwelijk, prevalidatie
          LT: VHNL02C300T40

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Libby.xls

When voer een bijhouding uit VHNL02C300T40.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
And is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL02C300T40.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 422531881 niet als PARTNER betrokken bij een HUWELIJK
