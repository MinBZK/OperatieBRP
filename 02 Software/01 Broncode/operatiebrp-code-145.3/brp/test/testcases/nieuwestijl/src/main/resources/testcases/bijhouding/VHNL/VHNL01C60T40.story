Meta:
@status                 Klaar
@regels                 R1606
@usecase                UCS-BY.HG

Narrative: R1606 Huwelijk en geregistreerd partnerschap, voltrekking huwelijk in Nederland, actie registratieAanvangHuwelijkGeregistreerdPartnerschap

Scenario: R1606 Administratieve handeling heeft een geldige waarde, actie en documentsoort hebben een ongeldige waarde
          LT: VHNL01C60T40

Gemeente BRP 1

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/VHNL01C60-sandy.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL01C60-danny.xls

When voer een bijhouding uit VHNL01C60T40.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
And is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL01C60T40.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R


Then is in de database de persoon met bsn 558376617 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 156960849 niet als PARTNER betrokken bij een HUWELIJK

