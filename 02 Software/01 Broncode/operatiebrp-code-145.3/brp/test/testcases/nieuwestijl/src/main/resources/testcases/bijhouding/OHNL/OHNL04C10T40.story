Meta:
@status                 Klaar
@usecase                UCS-BY.HG


Narrative:
Ontbinding huwelijk in Nederland

Scenario:   Registratie Geslachtsnaam voor 1 partner (Ingeschrevene) met niet NL nationaliteit
            LT: OHNL04C10T40

Gemeente BRP 1

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-OHNL/OHNL04C10T40-001.xls
Given enkel initiele vulling uit bestand /LO3PL-OHNL/OHNL04C10T40-002.xls

When voer een bijhouding uit OHNL04C10T40a.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/OHNL/expected/OHNL04C10T40a.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Given pas laatste relatie van soort 1 aan tussen persoon 640235657 en persoon 334977289 met relatie id 30010003 en betrokkenheid id 30010003

Then is in de database de persoon met bsn 640235657 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 334977289 wel als PARTNER betrokken bij een HUWELIJK

When voer een bijhouding uit OHNL04C10T40b.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/OHNL/expected/OHNL04C10T40b.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 640235657 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 334977289 niet als PARTNER betrokken bij een HUWELIJK

Then in kern heeft select voornamen, geslnaamstam from kern.pers where bsn = '334977289' de volgende gegevens:
| veld                 | waarde |
| voornamen            | Libby  |
| geslnaamstam         | Jansen |

Then in kern heeft select stam, volgnr from kern.persgeslnaamcomp de volgende gegevens:
| veld                 | waarde |
| stam                 | Jansen |
| volgnr               | 1      |
| stam                 | Jansen |
| volgnr               | 1      |

Then lees persoon met anummer 5128720161 uit database en vergelijk met expected OHNL04C10T40-persoon1.xml













