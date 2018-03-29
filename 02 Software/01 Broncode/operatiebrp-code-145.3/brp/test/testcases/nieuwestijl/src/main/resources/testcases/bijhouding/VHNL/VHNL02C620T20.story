Meta:
@auteur                 tjlee
@status                 Klaar
@sleutelwoorden         Geslaagd
@regels                 R2044
@usecase                UCS-BY.HG

Narrative:
R2044 Huwelijk en geregistreerd partnerschap, voltrekking huwelijk in Nederland, actie registratieAanvangHuwelijkGeregistreerdPartnerschap

Scenario: R2044 Gemeente aanvang relatie verplicht als Land/Gebied = Nederland
          LT: VHNL02C620T20

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL/VHNL02C620T20-sandy.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL02C620T20-danny.xls

When voer een bijhouding uit VHNL02C620T20.xml namens partij 'Gemeente Tiel'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL02C620T20.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 494249225 wel als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 7486831378 uit database en vergelijk met expected /testcases/bijhouding/VHNL/expected/VHNL02C620T20-persoon1.xml
Then lees persoon met anummer 5606489362 uit database en vergelijk met expected /testcases/bijhouding/VHNL/expected/VHNL02C620T20-persoon2.xml

