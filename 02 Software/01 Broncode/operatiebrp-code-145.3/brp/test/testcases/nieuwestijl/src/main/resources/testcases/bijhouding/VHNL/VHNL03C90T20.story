Meta:
@auteur                 tjlee
@status                 Klaar
@sleutelwoorden         Geslaagd
@regels                 R2167
@usecase                UCS-BY.HG

Narrative:
R2167 Huwelijk en geregistreerd partnerschap, voltrekking huwelijk in Nederland, actie registratieAanvangHuwelijkGeregistreerdPartnerschap, registratieNaamgebruik

Scenario: R2167 Persoon.Naamgebruik afgeleid is wel ingevuld
          LT: VHNL03C90T20

Gemeente BRP 1

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL/VHNL03C90-sandy.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL03C90-danny.xls

When voer een bijhouding uit VHNL03C90T20.xml namens partij 'Gemeente Tiel'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL03C90T20.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 717411977 wel als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 9576485650 uit database en vergelijk met expected VHNL03C90T20-persoon1.xml
Then lees persoon met anummer 3604914962 uit database en vergelijk met expected VHNL03C90T20-persoon2.xml
