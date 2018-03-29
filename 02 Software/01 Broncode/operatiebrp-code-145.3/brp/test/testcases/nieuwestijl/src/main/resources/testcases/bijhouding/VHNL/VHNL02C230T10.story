Meta:
@auteur                 jozon
@status                 Klaar
@regels                 R1810
@sleutelwoorden         voltrekkingHuwelijkInNederland, VHNL02C230T10
@usecase                UCS-BY.HG

Narrative:
R1810 Huwelijk en geregistreerd partnerschap, voltrekking huwelijk in Nederland, actie registratieAanvangHuwelijkGeregistreerdPartnerschap

Scenario: R1810 Namenreeks en Voorvoegsel sluiten elkaar uit
          LT: VHNL02C230T10




Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Libby.xls

When voer een bijhouding uit VHNL02C230T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL02C230T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 422531881 niet als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 1868196961 uit database en vergelijk met expected VHNL02C230T10-persoon1.xml





