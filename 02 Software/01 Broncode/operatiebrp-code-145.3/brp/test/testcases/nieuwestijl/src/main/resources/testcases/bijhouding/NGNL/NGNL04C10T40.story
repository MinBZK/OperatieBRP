Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative: Nietigverklaring geregistreerd partnerschap in Nederland

Scenario:   Registratie Geslachtsnaam voor 1 partner (Ingeschrevene) met niet NL nationaliteit
            LT: NGNL04C10T40

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-NGNL/NGNL04C10T40-001.xls
Given enkel initiele vulling uit bestand /LO3PL-NGNL/NGNL04C10T40-002.xls

When voer een bijhouding uit NGNL04C10T40a.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

Given pas laatste relatie van soort 2 aan tussen persoon 262978921 en persoon 724307977 met relatie id 43000114 en betrokkenheid id 43000114


When voer een bijhouding uit NGNL04C10T40b.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/NGNL/expected/NGNL04C10T40.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 262978921 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
Then is in de database de persoon met bsn 724307977 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP


Then in kern heeft select voornamen, geslnaamstam from kern.pers where bsn = '262978921' de volgende gegevens:
| veld                 | waarde |
| voornamen            | Libby  |
| geslnaamstam         | Pietersen |

Then in kern heeft select stam, volgnr from kern.persgeslnaamcomp de volgende gegevens:
| veld                 | waarde |
| stam                 | Jansen |
| volgnr               | 1      |

Then lees persoon met anummer 4038121761 uit database en vergelijk met expected NGNL04C10T40-persoon1.xml













