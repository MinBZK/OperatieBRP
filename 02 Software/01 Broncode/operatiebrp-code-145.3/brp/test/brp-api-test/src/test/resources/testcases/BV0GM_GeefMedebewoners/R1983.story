Meta:
@status             Klaar
@usecase            BV.0.GM, BV.1.GM
@sleutelwoorden     Geef Medebewoner van Persoon
@regels             R1983

Narrative:
Indien er sprake is van een 'verstrekkingsbeperking bij de Persoon voor de Bericht.Ontvangende partij'
dan mag die Persoon in principe niet geleverd worden als zoekresultaat, bevragingsresultaat,
selectieresultaat of in een spontaan bericht van het systeem.
In de volgende situaties is er echter sprake van een uitzondering en mag de Dienst wel worden geleverd:
Mutatielevering op basis van afnemerindicatie (hier dient de afnemer vooralsnog zelf zijn indicatie
te verwijderen om de levering te stoppen; bovendien moet hij op de hoogte worden gebracht van het
instellen van de verstrekkingsbeperking)
Diensten die de afnemerindicatie verwijderen (daarbij wordt ook geen / minimale persoonsinformatie geleverd):
Verwijderen afnemerindicatie en Selectie met plaatsen afnemerindicatie

Scenario: 1.    Geef medebewoners met partij dan verstrekkingsbeperking aan toegewezen is
                LT:  R1983_LT18, R1342_LT02
                Verwacht resultaat: GEEN persoon gevonden, want verstrekkingsbeperking op partij

Given leveringsautorisatie uit autorisatie/Geef_Medebewoners_verstrekkingsbeperking_op_partij

Given persoonsbeelden uit BIJHOUDING:VZIG04C20T20/Specifieke_verstrekkingsbeperking_Partij/dbstate002

Given verzoek geef medebewoners van persoon:
|key|value
|leveringsautorisatieNaam|'Geef medebewoners'
|zendendePartijNaam|'Stichting Interkerkelijke Ledenadministratie'
|burgerservicenummer|'771168585'

Then heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Fout'
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                                                                            |
| R1339    | Bij deze persoon geldt een verstrekkingsbeperking waardoor deze dienst niet geleverd kan worden.   |

Then is het aantal ontvangen berichten 0

Scenario: 2.    Geef medebewoners met andere partij dan verstrekkingsbeperking aan toegewezen is
                LT: R1343_LT03
                Verwacht resultaat:
                - Persoon gevonden, want verstrekkingsbeperking op partij is voor andere partij

Given leveringsautorisatie uit autorisatie/Geef_Medebewoners_verstrekkingsbeperking_op_andere_partij

Given persoonsbeelden uit BIJHOUDING:VZIG04C20T20/Specifieke_verstrekkingsbeperking_Partij/dbstate002

Given verzoek geef medebewoners van persoon:
|key|value
|leveringsautorisatieNaam|'Geef medebewoners'
|zendendePartijNaam|'Gemeente Utrecht'
|burgerservicenummer|'771168585'

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                                                         |
| R2383    | De opgegeven identificatiecriteria zijn niet herleidbaar tot een uniek adres.   |

!-- Komt foutief uit, omdat dedze persoon geen BAG code heeft op zn adres
!-- Doel van de test is vast te stellen dat de verstrekkingsbeperking alleen voor een andere partij geld
!-- Vandaar dat deze test okay is