Meta:
@status             Klaar
@usecase            SA.0.PA
@regels             R1405
@sleutelwoorden     Plaats afnemerindicatie

Narrative:
Een opgegeven datum aanvang materiële periode mag niet in de toekomst liggen.
Wanneer dit wel het geval is kan er geen afnemerindicatie geplaatst worden

Scenario:   1. Afnemer plaatst afnemerindicatie op een persoon, waarbij de datum materiele periode in de toekomst ligt.
            LT: R1405_LT03
            Verwacht resultaat: 1. Afnemerindicatie kan niet geplaatst worden op persoon
                                2. Synchroon responsebericht
                                Met vulling:
                                -  Verwerking = foutief
                                -  Code = R1405
                                -  Melding = Datum aanvang materiele periode mag niet in de toekomst liggen.
                                3. Er wordt niet geleverd voor deze persoon

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given verzoek plaats afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|datumAanvangMaterielePeriode|morgen

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                                                         |
| R1405 | Datum aanvang materiele periode mag niet in de toekomst liggen. |

Then is er geen synchronisatiebericht gevonden