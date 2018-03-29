Meta:
@status                 Klaar
@sleutelwoorden         Geslaagd
@usecase                UCS-BY.HG

Narrative:
Registratie Geslachtsnaam voor beide partners (Beiden Ingeschrevenen en beiden niet NL-nationaliteit)

Scenario:   Personen Libby Thatcher (Ingeschrevene-Ingezetene, niet NL Nat) en Piet Jansen (Ingeschrevene-Ingezetene, niet NL Nat) gaan trouwen, controleer relatie, betrokkenheid, afgeleid administratief,
            LT: VHNL05C10T30





Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/VHNL01C30T30-Libby.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL01C30T30-Piet.xls

When voer een bijhouding uit VHNL05C10T30.xml namens partij 'Gemeente BRP 1'


Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL05C10T30.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 546921097 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 108810100 wel als PARTNER betrokken bij een HUWELIJK

Then in kern heeft select voornamen, geslnaamstam from kern.pers where bsn = '546921097' de volgende gegevens:
| veld                 | waarde |
| voornamen            | Libby  |
| geslnaamstam         | Jansen-Thatcher |

Then in kern heeft select voornamen, geslnaamstam from kern.pers where bsn = '108810100' de volgende gegevens:
| veld                 | waarde |
| voornamen            | Piet  |
| geslnaamstam         | Jansen-Thatcher |

Then lees persoon met anummer 3102152865 uit database en vergelijk met expected VHNL05C10T30-persoon1.xml
Then lees persoon met anummer 4202603041 uit database en vergelijk met expected VHNL05C10T30-persoon2.xml
