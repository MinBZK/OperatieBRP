Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative: Registratie Naamgebruik Ingeschrevenen

Scenario: Registratie Partner naam voor Eigen naam voor beide Ingeschrevenen
          LT: AGBL04C40T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AGBL/AGBL04C40T10-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AGBL/AGBL04C40T10-002.xls

When voer een bijhouding uit AGBL04C40T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/AGBL/expected/AGBL04C40T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 937223177 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
Then is in de database de persoon met bsn 829172361 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP

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

Then lees persoon met anummer 2457296929 uit database en vergelijk met expected AGBL04C40T10-persoon1.xml
Then lees persoon met anummer 6768539425 uit database en vergelijk met expected AGBL04C40T10-persoon2.xml
