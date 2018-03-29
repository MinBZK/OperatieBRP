Meta:
@status             Klaar
@usecase            SA.0.PA
@regels             R1339
@sleutelwoorden     Plaats afnemerindicatie
Narrative:
Wanneer een persoon een verstrekkingsbeperking op een partij heeft geplaatst,
dan is het niet mogelijk voor de partij in kwestie om een afnemerindicatie te plaatsen

Scenario:   1. Afnemer plaatst afnemerindicatie op een persoon, deze persoon heeft een volledige verstrekkingsbeperking.
            LT: R1339_LT04, R1983_LT35
            Verwacht resultaat: 1. Er wordt aan het verzoek tot het plaatsen van een afnemerindicatie, GEEN gevolg gegeven en krijgt de verzoekende Partij
                                een foutmelding retour. Persoon met volledige verstrekkingsbeperking wordt derhalve niet geleverd.
                                2. Synchroon responsebericht
                                Met vulling:
                                -  Verwerking = foutief
                                -  Hoogste meldings niveau = fout
                                -  Melding = De persoon heeft een verstrekkingsbeperking waardoor deze dienst niet geleverd kan worden.
                                -  Afnemer = De Partij waarvoor de Dienst wordt geleverd
                                3. Er wordt niet geleverd voor deze persoon

Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC20T140_xls
Given leveringsautorisatie uit autorisatie/Mutaties_op_specifieke_personen_voor_afnemer_is_502707
Given verzoek plaats afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'Mutaties op specifieke personen voor afnemer is 502707'
|zendendePartijNaam|'KUC033-PartijVerstrekkingsbeperking'
|bsn|254534521

Then heeft het antwoordbericht verwerking Foutief
And heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Fout'
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                                                                                          |
| R1339 | Bij deze persoon geldt een verstrekkingsbeperking waardoor deze dienst niet geleverd kan worden. |
And heeft in het antwoordbericht 'partijCode' in 'plaatsingAfnemerindicatie' de waarde '502707'

Then is er geen synchronisatiebericht gevonden

Scenario:   2. Afnemer plaatst afnemerindicatie op een persoon, deze persoon heeft een verstrekkingsbeperking op partij.
            LT: R1983_LT36
            Verwacht resultaat:
            - Foutmelding R1339

Given persoonsbeelden uit BIJHOUDING:VZIG04C20T20/Specifieke_verstrekkingsbeperking_Partij/dbstate002
Given leveringsautorisatie uit autorisatie/Plaats_Afnemerindicatie_verstrekkingsbeperking_op_partij
Given verzoek plaats afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'Plaats afnemerindicatie'
|zendendePartijNaam|'Stichting Interkerkelijke Ledenadministratie'
|bsn|771168585

Then heeft het antwoordbericht verwerking Foutief
And heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Fout'
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                                                                                          |
| R1339 | Bij deze persoon geldt een verstrekkingsbeperking waardoor deze dienst niet geleverd kan worden. |

Then is er geen synchronisatiebericht gevonden