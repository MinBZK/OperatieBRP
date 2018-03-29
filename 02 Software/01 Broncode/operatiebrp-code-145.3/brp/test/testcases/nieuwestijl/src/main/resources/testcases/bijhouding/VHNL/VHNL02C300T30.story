Meta:
@status                 Klaar
@regels                 R1859
@usecase                UCS-BY.HG

Narrative:
R1859 Datum aanvang H/GP moet een volledig bekende datum zijn bij aanvang in Nederland

Scenario: Datum aanvang is een volledig onbekende datum (0000)
          LT: VHNL02C300T30



Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Libby.xls

When voer een bijhouding uit VHNL02C300T30.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
And is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL02C300T30.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 422531881 niet als PARTNER betrokken bij een HUWELIJK


