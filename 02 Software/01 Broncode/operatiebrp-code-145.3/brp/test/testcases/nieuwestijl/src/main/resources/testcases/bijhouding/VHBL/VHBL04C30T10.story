Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative: Registratie Geslachtsnaam Ingeschrevenen

Scenario: Registratie Geslachtsnaam voor 1 partner (Ingeschrevene) met niet NL nationaliteit
          LT: VHBL04C30T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VHBL/VHBL04C30T10-001.xls
Given enkel initiele vulling uit bestand /LO3PL-VHBL/VHBL04C30T10-002.xls

When voer een bijhouding uit VHBL04C30T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHBL/expected/VHBL04C30T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 803182089 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 127319219 wel als PARTNER betrokken bij een HUWELIJK

Then in kern heeft select voornamen, geslnaamstam from kern.pers where bsn = '803182089' de volgende gegevens:
| veld                 | waarde |
| voornamen            | Libby  |
| geslnaamstam         | Jansen |

Then in kern heeft select stam, volgnr from kern.persgeslnaamcomp de volgende gegevens:
| veld                 | waarde |
| stam                 | Jansen |
| volgnr               | 1      |
| stam                 | Jansen |
| volgnr               | 1      |

Then lees persoon met anummer 4937125409 uit database en vergelijk met expected VHBL04C30T10-persoon1.xml
Then lees persoon met anummer 6417083681 uit database en vergelijk met expected VHBL04C30T10-persoon2.xml
