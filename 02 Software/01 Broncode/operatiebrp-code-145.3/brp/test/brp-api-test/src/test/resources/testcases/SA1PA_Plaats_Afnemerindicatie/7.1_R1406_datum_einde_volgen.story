Meta:
@status             Klaar
@usecase            SA.0.PA
@regels             R1406
@sleutelwoorden     Plaats afnemerindicatie

Narrative: 
Een opgegeven datum einde volgen moet in de toekomst liggen.
Wanneer datum einde volgen kleiner of gelijk is aan de systeemdatum, wordt de afnemerindicatie niet geplaatst

Scenario:   1. Afnemer plaatst afnemerindicatie op een persoon, waarbij DatumEindeVolgen < de systeemdatum.
            LT: R1406_LT01
            Verwacht resultaat: 1. Afnemerindicatie kan niet geplaatst worden op persoon
                                2. Synchroon responsebericht
                                Met vulling:
                                -  Verwerking = foutief
                                -  Code = R1406
                                -  Melding = Datum einde volgen moet in de toekomst liggen.
                                3. Er wordt niet geleverd voor deze persoon

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given verzoek plaats afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|datumEindeVolgen|gisteren

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                                        |
| R1406 | Datum einde volgen moet in de toekomst liggen. |

Then is er geen synchronisatiebericht gevonden

Scenario:   2. Afnemer plaatst afnemerindicatie op een persoon, waarbij de DatumEindeVolgen = systeemdatum.
            LT: R1406_LT02
            Verwacht resultaat: 1. Afnemerindicatie kan niet geplaatst worden op persoon
                                2. Synchroon responsebericht
                                Met vulling:
                                -  Verwerking = foutief
                                -  Code = R1406
                                -  Melding = Datum einde volgen moet in de toekomst liggen.
                                3. Er wordt niet geleverd voor deze persoon

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie

Given verzoek plaats afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|datumEindeVolgen|vandaag

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                                        |
| R1406 | Datum einde volgen moet in de toekomst liggen. |

Then is er geen synchronisatiebericht gevonden