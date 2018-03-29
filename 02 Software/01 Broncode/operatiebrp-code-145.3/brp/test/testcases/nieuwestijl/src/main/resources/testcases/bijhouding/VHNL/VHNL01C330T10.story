Meta:
@auteur                 fuman
@status                 Klaar
@regels                 R1835
@usecase                UCS-BY.HG

Narrative:
R1835 Object sleutel in bijhouding moet verwijzen naar bestaand object in BRP van juiste type

Scenario:   Objectsleutel komt niet overeen, Objectsleutel komt niet overeen
            LT: VHNL01C330T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Libby.xls
Given enkel initiele vulling uit bestand /LO3PL/Piet.xls

When voer een bijhouding uit VHNL01C330T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL01C330T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 159247913 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 422531881 niet als PARTNER betrokken bij een HUWELIJK
