Meta:
@status                 Klaar
@sleutelwoorden         Geslaagd
@usecase                UCS-BY.HG

Narrative:
Huwelijk waarbij beide partners niet de NL nationaliteit hebben

Scenario:   Personen Libby Thatcher (Ingeschrevene-Ingezetene, niet NL Nat) en Piet Jansen (Ingeschrevene-Ingezetene, niet NL Nat) gaan trouwen, controleer relatie, betrokkenheid, afgeleid administratief,
            LT: VHNL01C30T30



Given bijhoudingsverzoek voor partij 'Gemeente BRP 1'

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/VHNL01C30T30-Libby.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL01C30T30-Piet.xls

When voer een bijhouding uit VHNL01C30T30.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then in kern heeft select voornamen, geslnaamstam from kern.pers where bsn = '546921097' de volgende gegevens:
| veld                 | waarde |
| voornamen            | Libby   |
| geslnaamstam         | Libberton |

Then in kern heeft select voornamen, geslnaamstam from kern.pers where bsn = '108810100' de volgende gegevens:
| veld                 | waarde |
| voornamen            | Piet   |
| geslnaamstam         | Pietersen |

Then lees persoon met anummer 3102152865 uit database en vergelijk met expected VHNL01C30T30-persoon1.xml
Then lees persoon met anummer 4202603041 uit database en vergelijk met expected VHNL01C30T30-persoon2.xml






