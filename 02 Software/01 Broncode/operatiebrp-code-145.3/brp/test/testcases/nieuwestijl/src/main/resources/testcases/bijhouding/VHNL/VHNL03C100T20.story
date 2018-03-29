Meta:
@auteur                 tjlee
@status                 Klaar
@sleutelwoorden         Geslaagd
@regels                 R2176
@usecase                UCS-BY.HG

Narrative:
R2176 Huwelijk en geregistreerd partnerschap, voltrekking huwelijk in Nederland, actie registratieAanvangHuwelijkGeregistreerdPartnerschap, registratieNaamgebruik

Scenario: R2176 Persoon.Naamgebruik is ingevuld R2176 Persoon.Naamgebruik is ingevuld
          LT: VHNL03C100T20


Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL/VHNL03C100T20-sandy.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL03C100T20-danny.xls

When voer een bijhouding uit VHNL03C100T20.xml namens partij 'Gemeente Tiel'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL03C100T20.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 156960849 wel als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 3814796818 uit database en vergelijk met expected VHNL03C100T20-persoon1.xml
Then lees persoon met anummer 1686982546 uit database en vergelijk met expected VHNL03C100T20-persoon2.xml
