Meta:
@auteur                 tjlee
@status                 Klaar
@sleutelwoorden         Geslaagd
@usecase                UCS-BY.HG

Narrative:
Huwelijk en geregistreerd partnerschap, voltrekking huwelijk in Nederland, actie registratieAanvangHuwelijkGeregistreerdPartnerschap

Scenario: Afgeleide bijhouding op de groep Persoon.Naamgebruik
          LT: VHNL06C20T10

Given bijhoudingsverzoek voor partij 'Gemeente Tiel'

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL/VHNL06C20-sandy.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL06C20-danny.xls

When voer een bijhouding uit VHNL06C20T10.xml namens partij 'Gemeente Tiel'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL06C20T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 156960849 wel als PARTNER betrokken bij een HUWELIJK

Then in kern heeft select tsreg, tsverval, nadereaandverval, naamgebruik, voornamennaamgebruik,
voorvoegselnaamgebruik,
geslnaamstamnaamgebruik from
kern.his_persnaamgebruik where geslnaamstamnaamgebruik = 'Maanen' de volgende gegevens:
| veld                    | waarde                |
| tsreg                   | 1983-02-05 02:00:00+01 |
| tsverval                | 1983-02-05 02:00:00+01 |
| nadereaandverval        | O                     |
| naamgebruik             | 1                     |
| voornamennaamgebruik    | Mandy                 |
| voorvoegselnaamgebruik  | Van                   |
| geslnaamstamnaamgebruik | Maanen                |

Then in kern heeft select count(*) from kern.his_persnaamgebruik where geslnaamstamnaamgebruik = 'Jong' and tsverval is not null and actieverval is not null de volgende gegevens:
| veld                    | waarde                |
| count                   | 1                     |

Then in kern heeft select count(*) from kern.his_persnaamgebruik where geslnaamstamnaamgebruik = 'Jong' and tsverval is null and actieverval is null de volgende gegevens:
| veld                    | waarde                |
| count                   | 1                     |

Then lees persoon met anummer 3814796818 uit database en vergelijk met expected VHNL06C20T10-persoon1.xml
Then lees persoon met anummer 1686982546 uit database en vergelijk met expected VHNL06C20T10-persoon2.xml
