Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative: Registratie Geslachtsnaam Ingeschrevenen

Scenario: Registratie Geslachtsnaam voor beide partners (Beiden Ingeschrevenen en beiden niet NL-nationaliteit)
          LT: AGNL02C30T20

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AGNL/AGNL02C30T20-Libby.xls
Given enkel initiele vulling uit bestand /LO3PL-AGNL/AGNL02C30T20-Piet.xls

When voer een bijhouding uit AGNL02C30T20.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/AGNL/expected/AGNL02C30T20.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 694547785 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
Then is in de database de persoon met bsn 515621481 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP

Then in kern heeft select voornamen, geslnaamstam from kern.pers where bsn = '694547785' de volgende gegevens:
| veld                 | waarde |
| voornamen            | Libby  |
| geslnaamstam         | Jansen-Thatcher |

Then in kern heeft select voornamen, geslnaamstam from kern.pers where bsn = '515621481' de volgende gegevens:
| veld                 | waarde |
| voornamen            | Piet  |
| geslnaamstam         | Jansen-Thatcher |

Then lees persoon met anummer 4760974369 uit database en vergelijk met expected AGNL02C30T20-persoon1.xml
Then lees persoon met anummer 9763132193 uit database en vergelijk met expected AGNL02C30T20-persoon2.xml








