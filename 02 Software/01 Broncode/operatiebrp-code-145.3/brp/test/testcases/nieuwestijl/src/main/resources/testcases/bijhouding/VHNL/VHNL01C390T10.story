Meta:
@status                 Klaar
@regels                 R2348
@usecase                UCS-BY.HG

Narrative:
R2348 Partij van de Administratieve handeling moet gelijk zijn aan de Zendende partij

Scenario:   Partij van de administratieve handeling is ongelijk aan de Zendende partij
            LT: VHNL01C390T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/VHNL01C390T10.xls

When voer een bijhouding uit VHNL01C390T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL01C390T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 422531881 niet als PARTNER betrokken bij een HUWELIJK