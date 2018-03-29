Meta:
@status                 Klaar
@sleutelwoorden         Geslaagd
@usecase                UCS-BY.HG

Narrative:
Huwelijk waarbij partner 2 niet de NL nationaliteit heeft

Scenario:   Personen Libby Thatcher (Ingeschrevene-Ingezetene, NL Nat) en Piet Jansen (Ingeschrevene-Ingezetene, niet NL Nat) gaan trouwen, controleer relatie, betrokkenheid, afgeleid administratief,
            LT: VHNL01C30T20



Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/VHNL01C30T20-Libby.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL01C30T20-Piet.xls

When voer een bijhouding uit VHNL01C30T20.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
And is er een blob gemaakt voor administratienummer 7026242849
And is er een blob aangepast voor administratienummer 3403146273

Then in kern heeft select voornamen, geslnaamstam from kern.pers where bsn = '376846793' de volgende gegevens:
| veld                 | waarde |
| voornamen            | Piet   |
| geslnaamstam         | Thatcher |

Then lees persoon met anummer 7026242849 uit database en vergelijk met expected VHNL01C30T20-persoon1.xml
Then lees persoon met anummer 3403146273 uit database en vergelijk met expected VHNL01C30T20-persoon2.xml









