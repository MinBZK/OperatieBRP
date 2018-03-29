Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative: Registratie Geslachtsnaam Ingeschrevenen

Scenario: Registratie Geslachtsnaam voor 1 partner (Ingeschrevene) met niet NL nationaliteit
          LT: AGBL04C30T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AGBL/AGBL04C30T10-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AGBL/AGBL04C30T10-002.xls

When voer een bijhouding uit AGBL04C30T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/AGBL/expected/AGBL04C30T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 881416137 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
Then is in de database de persoon met bsn 249935545 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP

Then in kern heeft select voornamen, geslnaamstam from kern.pers where bsn = '881416137' de volgende gegevens:
| veld                 | waarde |
| voornamen            | Libby  |
| geslnaamstam         | Jansen |

Then in kern heeft select stam, volgnr from kern.persgeslnaamcomp de volgende gegevens:
| veld                 | waarde |
| stam                 | Jansen |
| volgnr               | 1      |
| stam                 | Jansen |
| volgnr               | 1      |

Then lees persoon met anummer 7210876193 uit database en vergelijk met expected AGBL04C30T10-persoon1.xml
Then lees persoon met anummer 1769648545 uit database en vergelijk met expected AGBL04C30T10-persoon2.xml
