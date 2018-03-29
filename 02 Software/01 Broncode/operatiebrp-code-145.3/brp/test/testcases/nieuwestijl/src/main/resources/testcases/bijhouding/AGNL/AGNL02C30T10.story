Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative: Registratie Geslachtsnaam Ingeschrevenen

Scenario: Registratie Geslachtsnaam voor 1 partner (Ingeschrevene) met niet NL nationaliteit
          LT: AGNL02C30T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AGNL/AGNL02C30T10-Libby.xls
Given enkel initiele vulling uit bestand /LO3PL-AGNL/AGNL02C30T10-Piet.xls

When voer een bijhouding uit AGNL02C30T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/AGNL/expected/AGNL02C30T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 103449437 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
Then is in de database de persoon met bsn 991810697 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP

Then in kern heeft select voornamen, geslnaamstam from kern.pers where bsn = '103449437' de volgende gegevens:
| veld                 | waarde |
| voornamen            | Libby  |
| geslnaamstam         | Jansen |

Then in kern heeft select stam, volgnr from kern.persgeslnaamcomp de volgende gegevens:
| veld                 | waarde |
| stam                 | Jansen |
| volgnr               | 1      |
| stam                 | Jansen |
| volgnr               | 1      |

Then lees persoon met anummer 9348489505 uit database en vergelijk met expected AGNL02C30T10-persoon1.xml
Then lees persoon met anummer 8964123425 uit database en vergelijk met expected AGNL02C30T10-persoon2.xml








