Meta:
@auteur                 fuman
@status                 Klaar
@sleutelwoorden         Geslaagd
@usecase                UCS-BY.HG

Narrative: Registratie Naamgebruik Ingeschrevenen

Scenario: Registratie Partner naam voor Eigen naam met voorvoegsel en scheidingsteken voor beide Ingeschrevenen
          LT: AGNL02C40T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AGNL/AGNL02C40T10-Libby.xls
Given enkel initiele vulling uit bestand /LO3PL-AGNL/AGNL02C40T10-Piet.xls

When voer een bijhouding uit AGNL02C40T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/AGNL/expected/AGNL02C40T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 722310857 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
Then is in de database de persoon met bsn 999025673 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP

Then in kern heeft
select n.geslnaamstamnaamgebruik
from kern.his_persnaamgebruik n
join kern.pers p on p.id = n.pers
where n.tsverval is null  and p.voornamen = 'Libby' de volgende gegevens:
| veld                            | waarde |
| geslnaamstamnaamgebruik         | Jansen-l'Thatcher |

Then in kern heeft
select n.geslnaamstamnaamgebruik
from kern.his_persnaamgebruik n
join kern.pers p on p.id = n.pers
where n.tsverval is null  and p.voornamen = 'Piet' de volgende gegevens:
| veld                            | waarde |
| geslnaamstamnaamgebruik         | Thatcher-l'Jansen |


Then lees persoon met anummer 9523189025 uit database en vergelijk met expected AGNL02C40T10-persoon1.xml
Then lees persoon met anummer 2137201953 uit database en vergelijk met expected AGNL02C40T10-persoon2.xml








