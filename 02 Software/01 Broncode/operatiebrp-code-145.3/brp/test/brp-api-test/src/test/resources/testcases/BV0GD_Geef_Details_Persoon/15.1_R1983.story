Meta:
@status             Klaar
@usecase            BV.0.GD
@sleutelwoorden     Geef Details Persoon
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

Scenario: 1.    Geef details persoon voor een partij met volledige verstrekkingsbeperking
                LT: R1983_LT08
                Verwacht resultaat:
                - Geen bericht

Given leveringsautorisatie uit /levering_autorisaties_nieuw/R1983/Geef_details_persoon_volledige_verstrekkingbeperking
Given persoonsbeelden uit oranje:DELTAVERS05/DELTAVERS05C10T10_xls

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geef details persoon volledige verstrekkingbeperking'
|zendendePartijNaam|'KUC033-PartijVerstrekkingsbeperking'
|bsn|434587977

Then heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Fout'
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                                                                            |
| R1339    | Bij deze persoon geldt een verstrekkingsbeperking waardoor deze dienst niet geleverd kan worden.   |

Then is het aantal ontvangen berichten 0


Scenario: 2.    Geef details persoon met partij dan verstrekkingsbeperking aan toegewezen is
                LT:  R1983_LT09, R1342_LT02
                Verwacht resultaat: GEEN bericht, want verstrekkingsbeperking op partij

Given leveringsautorisatie uit autorisatie/GDP_verstrekkingsbeperking_op_partij

Given persoonsbeelden uit BIJHOUDING:VZIG04C20T20/Specifieke_verstrekkingsbeperking_Partij/dbstate002

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geef Details Persoon Afnemer'
|zendendePartijNaam|'Stichting Interkerkelijke Ledenadministratie'
|bsn|771168585

Then heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Fout'
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                                                                            |
| R1339    | Bij deze persoon geldt een verstrekkingsbeperking waardoor deze dienst niet geleverd kan worden.   |

Then is het aantal ontvangen berichten 0


Scenario: 3.    Geef details persoon met andere partij dan verstrekkingsbeperking aan toegewezen is
                LT: R1983_LT07
                Verwacht resultaat:
                - bericht, want verstrekkingsbeperking op partij is voor andere partij

Given leveringsautorisatie uit autorisatie/GDP_verstrekkingsbeperking_op_andere_partij

Given persoonsbeelden uit BIJHOUDING:VZIG04C20T20/Specifieke_verstrekkingsbeperking_Partij/dbstate002

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geef Details Persoon Afnemer'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|771168585

Then heeft het antwoordbericht verwerking Geslaagd