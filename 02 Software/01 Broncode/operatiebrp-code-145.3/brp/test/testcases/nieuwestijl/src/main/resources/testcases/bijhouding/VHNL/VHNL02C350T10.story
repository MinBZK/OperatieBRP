Meta:
@status                 Klaar
@regels                 R1865
@sleutelwoorden         voltrekkingHuwelijkInNederland, VHNL02C350T10
@usecase                UCS-BY.HG

Narrative:
R1865 Huwelijk en geregistreerd partnerschap, voltrekking huwelijk in Nederland, actie registratieAanvangHuwelijkGeregistreerdPartnerschap

Scenario: R1865 Leeftijd van de partners bij voltrekking HGP is jonger dan 18 jaar
          LT: VHNL02C350T10

Gemeente BRP 1

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Libby-jonger-18.xls

When voer een bijhouding uit VHNL02C350T10.xml namens partij 'Gemeente Tiel'

Then heeft het antwoordbericht verwerking Foutief
And is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL02C350T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 422531881 niet als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 1868196961 uit database en vergelijk met expected VHNL02C350T10-persoon1.xml
