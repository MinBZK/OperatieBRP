Meta:
@status                 Klaar
@regels                 R1580
@usecase                UCS-BY.HG

Narrative:
R1580 Een persoon mag niet worden bijgehouden als de nadere bijhoudingsaard 'Fout' is

Scenario:   Persoon heeft bijhoudingsaard 'Fout', Persoon heeft bijhoudingsaard 'Fout'
            LT: AGNL01C80T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Libby_bijh_F.xls
Given enkel initiele vulling uit bestand /LO3PL/Piet.xls

When voer een bijhouding uit AGNL01C80T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/AGNL/expected/AGNL01C80T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 159247913 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
Then is in de database de persoon met bsn 814518217 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP




