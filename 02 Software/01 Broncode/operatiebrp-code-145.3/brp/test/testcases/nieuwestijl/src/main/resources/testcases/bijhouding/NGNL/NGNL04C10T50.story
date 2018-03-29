Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative: Nietigverklaring geregistreerd partnerschap in Nederland

Scenario:   Nevenactie Registratie geslachtsnaam Eigen naam voor naam partner "E"
            LT: NGNL04C10T50

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-NGNL/NGNL04C10T50-001.xls
Given enkel initiele vulling uit bestand /LO3PL-NGNL/NGNL04C10T50-002.xls

When voer een bijhouding uit NGNL04C10T50a.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

Given pas laatste relatie van soort 2 aan tussen persoon 444093801 en persoon 403428233 met relatie id 43000115 en betrokkenheid id 43000115


When voer een bijhouding uit NGNL04C10T50b.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/NGNL/expected/NGNL04C10T50.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 444093801 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
Then is in de database de persoon met bsn 403428233 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP


Then in kern heeft select voornamen, geslnaamstam from kern.pers where bsn = '444093801' de volgende gegevens:
| veld                 | waarde |
| voornamen            | Libby  |
| geslnaamstam         | Thatcher |


Then lees persoon met anummer 2438128673 uit database en vergelijk met expected NGNL04C10T50-persoon1.xml













