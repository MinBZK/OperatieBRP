Meta:
@status                 Klaar
@regels                 R1809
@usecase                UCS-BY.HG

Narrative:
R1809 Huwelijk en geregistreerd partnerschap, voltrekking huwelijk in Nederland, actie registratieAanvangHuwelijkGeregistreerdPartnerschap

Scenario: Ingeschrevene trouwt met Niet-Ingeschrevene waarbij voornaam = gevuld en Namenreeks = Ja
          LT: VHNL02C220T10

Gemeente BRP 1

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Libby.xls

When voer een bijhouding uit VHNL02C220T10.xml namens partij 'Gemeente Tiel'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL02C220T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 422531881 niet als PARTNER betrokken bij een HUWELIJK





