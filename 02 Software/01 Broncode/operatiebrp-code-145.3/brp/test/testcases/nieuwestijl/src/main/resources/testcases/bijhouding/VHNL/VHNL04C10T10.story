Meta:
@status                 Klaar
@sleutelwoorden         Geslaagd
@usecase                UCS-BY.HG

Narrative:
Registratie aanvang huwelijk in NL tussen I-I en I-I (Happy Flow)

Scenario:   Personen Libby Thatcher (Ingeschrevene-Ingezetene) en Piet Jansen (Ingeschrevene-Ingezetene) gaan trouwen, controleer relatie, betrokkenheid, afgeleid administratief,
            LT: VHNL04C10T10



Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Libby.xls
Given enkel initiele vulling uit bestand /LO3PL/Piet.xls

When voer een bijhouding uit VHNL04C10T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL04C10T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 159247913 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 422531881 wel als PARTNER betrokken bij een HUWELIJK

Then in kern heeft select statuslev from kern.admhnd where partij = 27012 and tslev is null de volgende gegevens:
| veld                      | waarde |
| statuslev                 | 1      |

Then in kern heeft select toelichtingontlening from kern.admhnd where toelichtingontlening is not null de volgende gegevens:
| veld                      | waarde                |
| toelichtingontlening      | test toelichting      |


Then lees persoon met anummer 1868196961 uit database en vergelijk met expected VHNL04C10T10-persoon1.xml
Then lees persoon met anummer 5398948626 uit database en vergelijk met expected VHNL04C10T10-persoon2.xml
