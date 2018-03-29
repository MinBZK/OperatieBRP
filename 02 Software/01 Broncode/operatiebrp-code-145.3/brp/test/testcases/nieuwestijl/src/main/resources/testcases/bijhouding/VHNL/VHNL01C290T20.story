Meta:
@status                 Klaar
@regels                 R1580
@usecase                UCS-BY.HG

Narrative:
R1580 Een persoon mag niet worden bijgehouden als de nadere bijhoudingsaard 'Gewist' is

Scenario: Persoon heeft bijhoudingsaard 'Gewist'
          LT: VHNL01C290T20

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Libby_bijh_F.xls
Given enkel initiele vulling uit bestand /LO3PL/Piet.xls

And de database is aangepast met: UPDATE kern.his_persbijhouding set naderebijhaard=(select id from kern.naderebijhaard where code = 'W') where  naderebijhaard = (select id from kern.naderebijhaard where code = 'F')

When voer een bijhouding uit VHNL01C290T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL01C290T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 159247913 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 814518217 niet als PARTNER betrokken bij een HUWELIJK




