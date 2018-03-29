Meta:
@auteur                 tjlee
@status                 Klaar
@sleutelwoorden         Geslaagd
@regels                 R2036
@usecase                UCS-BY.HG

Narrative:
R2036 Huwelijk en geregistreerd partnerschap, voltrekking huwelijk in Nederland, actie registratieAanvangHuwelijkGeregistreerdPartnerschap

Scenario: R2036 Woonplaatsnaam geboorte mag alleen gevuld zijn als ook Gemeente gevuld is
          LT: VHNL02C580T20

Gemeente BRP 1

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL/VHNL02C580T20-sandy.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL02C580T20-danny.xls

When voer een bijhouding uit VHNL02C580T20.xml namens partij 'Gemeente Tiel'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL02C580T20.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 842423369 wel als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 7218585874 uit database en vergelijk met expected /testcases/bijhouding/VHNL/expected/VHNL02C580T20-persoon1.xml
Then lees persoon met anummer 6060876050 uit database en vergelijk met expected /testcases/bijhouding/VHNL/expected/VHNL02C580T20-persoon2.xml
