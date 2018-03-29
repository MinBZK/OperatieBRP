Meta:
@status                 Klaar
@sleutelwoorden         Geslaagd
@usecase                UCS-BY.HG

Narrative:
Huwelijk en geregistreerd partnerschap, voltrekking huwelijk in Nederland, actie registratieAanvangHuwelijkGeregistreerdPartnerschap

Scenario: Test voor registratie naamgebruik. Registratie naamgebruik is niet Ja
          LT: VHNL06C10T40



Given bijhoudingsverzoek voor partij 'Gemeente BRP 1'

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL/VHNL06C10-sandy.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL06C10-danny.xls

When voer een bijhouding uit VHNL06C10T40.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL06C10T40.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 156960849 wel als PARTNER betrokken bij een HUWELIJK

Then in kern heeft select voornamennaamgebruik, voorvoegselnaamgebruik, geslnaamstamnaamgebruik, naamgebruik from kern.pers
where bsn =
'156960849' de volgende gegevens:
| veld                      | waarde |
| voornamennaamgebruik      | Mandy  |
| voorvoegselnaamgebruik    | Van    |
| geslnaamstamnaamgebruik   | Maanen |
| naamgebruik               | 2      |

Then lees persoon met anummer 3814796818 uit database en vergelijk met expected VHNL06C10T40-persoon1.xml
Then lees persoon met anummer 1686982546 uit database en vergelijk met expected VHNL06C10T40-persoon2.xml
