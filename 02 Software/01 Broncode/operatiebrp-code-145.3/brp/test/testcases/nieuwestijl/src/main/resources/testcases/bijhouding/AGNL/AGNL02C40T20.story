Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative: Registratie Naamgebruik Ingeschrevenen

Scenario: Naam partner wijzigt, afgeleide naam persoon wijzigt (naamgebruik)
          LT: AGNL02C40T20

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AGNL/AGNL02C40T20-Libby.xls
Given enkel initiele vulling uit bestand /LO3PL-AGNL/AGNL02C40T20-Piet.xls

When voer een bijhouding uit AGNL02C40T20.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/AGNL/expected/AGNL02C40T20.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 259492425 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
Then is in de database de persoon met bsn 921851145 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP

Then in kern heeft select voornamen, geslnaamstam from kern.pers where bsn = '259492425' de volgende gegevens:
| veld                 | waarde |
| voornamen            | Libby  |
| geslnaamstam         | Devries |


Then in kern heeft
select n.geslnaamstamnaamgebruik
from kern.his_persnaamgebruik n
join kern.pers p on p.id = n.pers
where n.tsverval is null  and p.voornamen = 'Piet' de volgende gegevens:
| veld                            | waarde |
| geslnaamstamnaamgebruik         | Devries |



Then lees persoon met anummer 6209041697 uit database en vergelijk met expected AGNL02C40T20-persoon1.xml
Then lees persoon met anummer 5239036961 uit database en vergelijk met expected AGNL02C40T20-persoon2.xml








