Meta:
@status             Klaar
@usecase            LV.1.AL
@regels             R1261, R1262, R1263, R1264, R2053, R2054, R2055, R2056, R2130, R2016, R2085, R2129
@sleutelwoorden     Authorisatie levering

Narrative:
Bij het in behandeling nemen van een bevraging
(het bericht bevat de parameter Bericht.Dienst) geldt dat de opgegeven Dienst dient te bestaan.

Scenario: 1.    De opgegeven dienst bestaat niet en dient te bestaan
                LT: R2055_LT02
                Verwacht resultaat: De gevraagde dienst bestaat niet

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|dienstId|9999

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                           |
| R2343    | De opgegeven dienst bestaat niet  |

Then is er een autorisatiefout gelogd met regelcode R2055

Scenario: 2.    De opgegeven dienst (zoek persoon) bestaat niet en dient te bestaan
                LT: R2055_LT04
                Verwacht resultaat: De gevraagde dienst bestaat niet

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding_Haarlem
Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van doelbinding Haarlem'
|zendendePartijNaam|'Gemeente Haarlem'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=606417801
|dienstId|9999

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                           |
| R2343    | De opgegeven dienst bestaat niet  |

Then is er een autorisatiefout gelogd met regelcode R2055