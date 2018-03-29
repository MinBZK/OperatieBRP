Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative: Registratie Naamgebruik Ingeschrevenen

Scenario: Registratie Partner naam voor Eigen naam voor beide Ingeschrevenen
          LT: VHBL04C40T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VHBL/VHBL04C40T10-001.xls
Given enkel initiele vulling uit bestand /LO3PL-VHBL/VHBL04C40T10-002.xls

When voer een bijhouding uit VHBL04C40T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHBL/expected/VHBL04C40T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 849545353 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 493694377 wel als PARTNER betrokken bij een HUWELIJK

Then in kern heeft
select n.geslnaamstamnaamgebruik
from kern.his_persnaamgebruik n
join kern.pers p on p.id = n.pers
where n.tsverval is null  and p.voornamen = 'Libby' de volgende gegevens:
| veld                            | waarde |
| geslnaamstamnaamgebruik         | Jansen-Thatcher |

Then in kern heeft
select n.geslnaamstamnaamgebruik
from kern.his_persnaamgebruik n
join kern.pers p on p.id = n.pers
where n.tsverval is null  and p.voornamen = 'Piet' de volgende gegevens:
| veld                            | waarde |
| geslnaamstamnaamgebruik         | Thatcher-Jansen |

Then lees persoon met anummer 5892094241 uit database en vergelijk met expected VHBL04C40T10-persoon1.xml
Then lees persoon met anummer 8608195873 uit database en vergelijk met expected VHBL04C40T10-persoon2.xml
