Meta:
@auteur                 tjlee
@status                 Klaar
@regels                 R1862
@sleutelwoorden         voltrekkingHuwelijkInNederland, VHNL02C320T10
@usecase                UCS-BY.HG

Narrative:
R1862 Huwelijk en geregistreerd partnerschap, voltrekking huwelijk in Nederland, actie registratieAanvangHuwelijkGeregistreerdPartnerschap

Scenario: R1862 Gemeente aanvang HGP moet registergemeente zijn
          LT: VHNL02C320T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Libby.xls

When voer een bijhouding uit VHNL02C320T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL02C320T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 422531881 niet als PARTNER betrokken bij een HUWELIJK



