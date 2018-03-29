Meta:
@auteur                 tjlee
@status                 Klaar
@sleutelwoorden         Geslaagd
@usecase                UCS-BY.HG

Narrative:
Huwelijk en geregistreerd partnerschap, voltrekking huwelijk in Nederland, actie registratieAanvangHuwelijkGeregistreerdPartnerschap

Scenario: De actuele Persoon.Samengestelde naam wordt (afgeleid) geregistreerd terwijl Persoon.Naamgebruik afgeleid de waarde Ja heeft
          LT: VHNL06C10T50

Given bijhoudingsverzoek voor partij 'Gemeente Tiel'

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL/VHNL06C10T50-sandy.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL06C10T50-danny.xls

When voer een bijhouding uit VHNL06C10T50.xml namens partij 'Gemeente Tiel'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL06C10T50.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 156960849 wel als PARTNER betrokken bij een HUWELIJK

Then in kern heeft select  voornamennaamgebruik,predicaatnaamgebruik,voorvoegselnaamgebruik,geslnaamstam from kern.pers where bsn = '156960849' de volgende gegevens:
| veld                      | waarde |
| voornamennaamgebruik      | Sandy  |
| predicaatnaamgebruik      | 3      |
| voorvoegselnaamgebruik    | de     |
| geslnaamstam              | Jong   |

Then lees persoon met anummer 3814796818 uit database en vergelijk met expected VHNL06C10T50-persoon1.xml
Then lees persoon met anummer 1686982546 uit database en vergelijk met expected VHNL06C10T50-persoon2.xml
