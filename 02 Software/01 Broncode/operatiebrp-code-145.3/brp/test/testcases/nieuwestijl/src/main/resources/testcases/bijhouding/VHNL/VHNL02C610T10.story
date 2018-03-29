Meta:
@status                 Klaar
@regels                 R2041
@sleutelwoorden         voltrekkingHuwelijkInNederland,TjieWah,VHNL02C610T10
@usecase                UCS-BY.HG

Narrative:
R2044 Huwelijk en geregistreerd partnerschap, voltrekking huwelijk in Nederland, actie registratieAanvangHuwelijkGeregistreerdPartnerschap

Scenario: R2041 Gemeente geboorte verplicht als Land/Gebied = Nederland
          LT: VHNL02C610T10

Gemeente BRP 1

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL/VHNL02C610T10-sandy.xls

When voer een bijhouding uit VHNL02C610T10.xml namens partij 'Gemeente Tiel'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL02C610T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 649253577 niet als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 6862819090 uit database en vergelijk met expected /testcases/bijhouding/VHNL/expected/VHNL02C610T10-persoon1.xml

