Meta:
@status                 Klaar
@sleutelwoorden         voltrekkingHuwelijkInNederland,TjieWah,VHNL06C40T10
@usecase                UCS-BY.HG

Narrative: Naamgebruik bij meerdere huwelijken

Scenario: Sandy trouwt 2e keer en Naamgebruik op 'N' (eigen naam - achternaamnaam van Jerry)
          LT: VHNL06C40T10

Gemeente BRP 1

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL/VHNL06C40T10-sandy.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL06C40T10-jerry.xls

When voer een bijhouding uit VHNL06C40T10.xml namens partij 'Gemeente Tiel'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL06C40T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 404941801 wel als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 7262803730 uit database en vergelijk met expected /testcases/bijhouding/VHNL/expected/VHNL06C40T10-persoon1.xml
Then lees persoon met anummer 9838793490 uit database en vergelijk met expected /testcases/bijhouding/VHNL/expected/VHNL06C40T10-persoon2.xml

Then in kern heeft select code from kern.regel r ,kern.admhndgedeblokkeerderegel a where r.id = a.regel de volgende gegevens:
| veld                 | waarde |
| code                 | R1869  |

