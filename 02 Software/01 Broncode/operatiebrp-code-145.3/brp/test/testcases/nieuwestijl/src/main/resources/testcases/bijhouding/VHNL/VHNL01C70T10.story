Meta:
@auteur                 tjlee
@status                 Klaar
@regels                 R1625
@sleutelwoorden         voltrekkingHuwelijkInNederland,TjieWah,VHNL01C70T10
@usecase                UCS-BY.HG

Narrative:
R1625 Huwelijk en geregistreerd partnerschap, voltrekking huwelijk in Nederland, actie registratieAanvangHuwelijkGeregistreerdPartnerschap

Scenario: R1625 De Geslachtsaanduiding is niet ingevuld
          LT: VHNL01C70T10



Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/VHNL01C70-sandy.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL01C70-danny.xls

When voer een bijhouding uit VHNL01C70T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
And is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL01C70T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 558376617 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 156960849 niet als PARTNER betrokken bij een HUWELIJK

