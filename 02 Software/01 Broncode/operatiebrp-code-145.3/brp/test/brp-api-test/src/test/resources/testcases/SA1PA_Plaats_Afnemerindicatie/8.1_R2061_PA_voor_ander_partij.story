Meta:
@status             Klaar
@usecase            SA.0.PA
@regels             R2061
@sleutelwoorden     Plaats afnemerindicatie

Narrative:
Als een Partij een verzoek stuurt om een afnemerindicatie te plaatsen of te verwjderen
dan dient in het verzoekbericht Persoon \ Afnemerindicatie.Afnemer gelijk zijn aan Bericht.Zendende partij

Scenario:   1.   Partij probeert een afnemerindicatie te plaatsen voor een andere partij, Persoon afnemerindicatie.afnemer <>Bericht zendende partij
                 LT: R2061_LT02
                 Verwacht resultaat:    1. Afnemerindicatie kan niet geplaatst worden op persoon
                                        2. Synchroon responsebericht
                                        Met vulling:
                                        -  Verwerking = foutief
                                        -  Code = R2061
                                        -  Melding = Een afnemer mag alleen voor zichzelf een indicatie onderhouden.
                                        3. Er wordt niet geleverd voor deze persoon

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given verzoek plaats afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|testAfnemerindicatiePartij|'Gemeente Haarlem'

Then heeft het antwoordbericht verwerking Foutief
And heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Fout'
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                                                |
| R2061    | Een afnemer mag alleen voor zichzelf een afnemerindicatie onderhouden. |

