Meta:
@auteur                 tjlee
@status                 Klaar
@sleutelwoorden         Geslaagd
@regels                 R2158
@usecase                UCS-BY.HG

Narrative:
R2158 Huwelijk en geregistreerd partnerschap, voltrekking huwelijk in Nederland, actie registratieAanvangHuwelijkGeregistreerdPartnerschap, registratieNaamgebruik

Scenario: R2158 Persoon.Predicaat naamgebruik staat als stamtabel in predicaat.
          LT: VHNL03C70T20

Gemeente BRP 1


Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL/VHNL03C70-sandy.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL03C70-danny.xls

When voer een bijhouding uit VHNL03C70T20.xml namens partij 'Gemeente Tiel'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL03C70T20.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 156960849 wel als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 3814796818 uit database en vergelijk met expected VHNL03C70T20-persoon1.xml
Then lees persoon met anummer 1686982546 uit database en vergelijk met expected VHNL03C70T20-persoon2.xml
