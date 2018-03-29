Meta:
@status             Klaar
@usecase            SA.0.PA
@regels             R1402
@sleutelwoorden     Plaats afnemerindicatie

Narrative:
Binnen abonnement hoogstens één afnemersindicatie per persoon,
wanneer er al een afnemerindicatie op de betreffendepersoon aanwezig is, kan er geen nieuwe afnemerindicatie geplaatst worden


Scenario:   1.  Afnemer plaatst afnemerindicatie op een persoon, waarop al een afnemerindicatie is voor de desbetreffende afnemer.
                LT: R1402_LT02, R1410_LT02
                Verwacht resultaat: 1a. Bij het werderom plaatsen van een afnemerindicatie op deze persoon voor dezelfde abonnement, dient dit niet mogelijk te zijn daar
                                er reeds een afnemerindicatie is geplaatst op deze persoon
                                1b. Synchroon responsebericht
                                Met vulling:
                                -  Verwerking = foutief
                                -  Melding = Er bestaat al een afnemersindicatie voor de opgegeven persoon binnen het abonnement.
                                2. Er wordt niet geleverd voor deze persoon

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls
Given afnemerindicatie op de persoon :
|bsn|leveringsautorisatieNaam|partijNaam|dienstId|tsReg|datumEindeVolgen
|606417801|Geen pop.bep. levering op basis van afnemerindicatie|'Gemeente Utrecht'|1|2016-07-28 T16:11:21Z|20150805
Given verzoek plaats afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                                                    |
| R1402     | Er bestaat al een afnemerindicatie voor de opgegeven persoon bij deze partij en deze leveringsautorisatie. |

Then is er geen synchronisatiebericht gevonden