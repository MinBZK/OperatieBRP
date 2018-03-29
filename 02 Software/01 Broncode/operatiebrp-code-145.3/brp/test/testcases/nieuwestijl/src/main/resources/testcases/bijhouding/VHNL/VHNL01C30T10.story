Meta:
@status                 Klaar
@sleutelwoorden         Geslaagd
@usecase                UCS-BY.HG

Narrative:
Huwelijk waarbij partner 1 niet de NL nationaliteit heeft

Scenario:   Personen Libby Thatcher (Ingeschrevene-Ingezetene, Niet NL Nat) en Piet Jansen (Ingeschrevene-Ingezetene, NL Nat) gaan trouwen, controleer relatie, betrokkenheid, afgeleid administratief,
            LT: VHNL01C30T10



Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/VHNL01C30T10-Libby.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL01C30T10-Piet.xls

When voer een bijhouding uit VHNL01C30T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL01C30T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then in kern heeft select voornamen, geslnaamstam from kern.pers where bsn = '422531881' de volgende gegevens:
| veld                 | waarde |
| voornamen            | Libby  |
| geslnaamstam         | Jansen |

Then lees persoon met anummer 1868196961 uit database en vergelijk met expected VHNL01C30T10-persoon1.xml
Then lees persoon met anummer 5398948626 uit database en vergelijk met expected VHNL01C30T10-persoon2.xml







