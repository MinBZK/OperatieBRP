Meta:

@status             Klaar
@usecase            BV.0.GM, BV.1.GM
@sleutelwoorden     Geef Medebewoner van Persoon
@regels             R1339

Narrative:
Het resultaatbericht bevat alleen Persoon(en) waarvoor GEEN verstrekkingsbeperking geldt voor de vragende partij;
hiervan is sprake als Persoon Niet voldoet aan 'Persoon heeft een verstrekkingsbeperking voor Partij' (R1342)

Scenario: 1.    Persoon met verstrekkingsbeperking
                LT: R1339_LT10
                Verwacht resultaat:
                - Foutief; Bij deze persoon geldt een verstrekkingsbeperking waardoor deze dienst niet geleverd kan worden.

Given leveringsautorisatie uit autorisatie/GeefMedebewoners_Verstrekkingsbeperking
Given persoonsbeelden uit specials:VerstrekkingsBeperking/PersoonKrijgtVerstrekkingsBeperking_xls

Given verzoek geef medebewoners van persoon:
|key|value
|leveringsautorisatieNaam|'geefMedebewonersVerstrekkingsBeperking'
|zendendePartijNaam|'KUC033-PartijVerstrekkingsbeperking'
|burgerservicenummer|'270433417'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                                                                                          |
| R1339 | Bij deze persoon geldt een verstrekkingsbeperking waardoor deze dienst niet geleverd kan worden. |

Scenario: 2.    Persoon met vervallen verstrekkingsbeperking
                LT: R1339_LT09
                Verwacht resultaat:
                - Geslaagd

Given leveringsautorisatie uit autorisatie/GeefMedebewoners_Verstrekkingsbeperking
Given persoonsbeelden uit specials:VerstrekkingsBeperking/PersoonVerstrekkingsBeperkingVervalt_xls

Given verzoek geef medebewoners van persoon:
|key|value
|leveringsautorisatieNaam|'geefMedebewonersVerstrekkingsBeperking'
|zendendePartijNaam|'KUC033-PartijVerstrekkingsbeperking'
|burgerservicenummer|'270433417'

Then heeft het antwoordbericht verwerking Geslaagd