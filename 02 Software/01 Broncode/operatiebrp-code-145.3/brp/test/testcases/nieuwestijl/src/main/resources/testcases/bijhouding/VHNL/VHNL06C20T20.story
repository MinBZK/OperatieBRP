Meta:
@auteur                 tjlee
@status                 Klaar
@sleutelwoorden         Geslaagd
@usecase                UCS-BY.HG

Narrative:
Huwelijk en geregistreerd partnerschap, voltrekking huwelijk in Nederland, actie registratieAanvangHuwelijkGeregistreerdPartnerschap

Scenario: Bijhouding op de groep Persoon.Naamgebruik
          LT: VHNL06C20T20


Given bijhoudingsverzoek voor partij 'Gemeente Tiel'

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL/VHNL06C20-sandy.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL06C20-danny.xls

When voer een bijhouding uit VHNL06C20T20.xml namens partij 'Gemeente Tiel'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL06C20T20.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 156960849 wel als PARTNER betrokken bij een HUWELIJK

Then in kern heeft select tsreg, tsverval from kern.his_persnaamgebruik where geslnaamstamnaamgebruik = 'Maanen' de volgende gegevens:
| veld     | waarde                 |
| tsreg    | 1983-02-05 02:00:00+01 |
| tsverval | 1983-02-05 02:00:00+01 |

Then lees persoon met anummer 3814796818 uit database en vergelijk met expected VHNL06C20T20-persoon1.xml
Then lees persoon met anummer 1686982546 uit database en vergelijk met expected VHNL06C20T20-persoon2.xml
