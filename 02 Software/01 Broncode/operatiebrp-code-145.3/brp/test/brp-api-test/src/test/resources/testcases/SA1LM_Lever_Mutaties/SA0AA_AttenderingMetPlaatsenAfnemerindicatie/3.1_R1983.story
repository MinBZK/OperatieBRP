Meta:
@status             Klaar
@usecase            SA.0.AA
@sleutelwoorden     Attendering met plaatsen afnemerindicatie, Lever mutaties

@regels             R1983, R1343

Narrative:

R1983:
Dienst verstrekt alleen gegevens indien geen sprake is van een verstrekkingsbeperking voor de afnemer


Indien er sprake is van een 'verstrekkingsbeperking bij de Persoon voor ontvangende partij dan mag die Persoon in principe niet geleverd worden
als zoekresultaat, bevragingsresultaat, selectieresultaat of in een spontaan bericht van het systeem.

Hiervan is sprake als Persoon Niet voldoet aan 'Persoon heeft een verstrekkingsbeperking voor Partij' (R1342).

In de volgende situaties is er echter sprake van een uitzondering en mag de Dienst wel worden geleverd:
Mutatielevering op basis van afnemerindicatie (hier dient de afnemer vooralsnog zelf zijn indicatie te verwijderen;
bovendien moet hij op de hoogte worden gebracht van het instellen van de verstrekkingsbeperking)
Diensten die de afnemerindicatie verwijderen (daarbij wordt ook geen / minimale persoonsinformatie geleverd):
Verwijdering afnemerindicatie en 'Selectie met verwijdering afnemerindicatie'.

Noot: De dienst Mutatielevering op basis van doelbinding ondersteunen we niet voor afnemers waarbij een verstrekkingsbeperking van toepassing
kan zijn. Ook bij deze dienst hoeft deze melding dus niet af te gaan.


Scenario: 1.   Mutatie waardoor persoon binnen populatiebeperking en attenderingscriterium komt te vallen
                LT: R1983_LT05, R1342_LT01
                Verwacht resultaat:
                - GEEN bericht, want volledige verstrekkingsbeperking
                - Geen afnemerindicatie geplaatst

Given leveringsautorisatie uit autorisatie/attendering_geboortedatum_verstrekkingsbeperking
Given persoonsbeelden uit oranje:DELTAVERS05/DELTAVERS05C10T10_xls
When voor persoon 434587977 wordt de laatste handeling geleverd
Then is er geen synchronisatiebericht gevonden
Then is er voor persoon met bsn 434587977 en leveringautorisatie Attendering geboortedatum verstrekkingsbeperking en partij KUC033-PartijVerstrekkingsbeperking geen afnemerindicatie geplaatst


Scenario: 2.    Mutatie waardoor persoon binnen populatiebeperking en attenderingscriterium komt te vallen
                LT:  R1983_LT06, R1342_LT02
                Verwacht resultaat:
                - GEEN bericht, want verstrekkingsbeperking op partij
                - Geen afnemerindicatie geplaatst

Given leveringsautorisatie uit autorisatie/attendering_geboortedatum_verstrekkingsbeperking_op_partij
!-- Startsituatie opgegeven
Given persoonsbeelden uit BIJHOUDING:VZIG04C20T20/Specifieke_verstrekkingsbeperking_Partij/dbstate001

!-- Wijziging na mutatie zorgt dat attenderingcriterium afgaat
Given persoonsbeelden uit BIJHOUDING:VZIG04C20T20/Specifieke_verstrekkingsbeperking_Partij/dbstate002
When voor persoon 771168585 wordt de laatste handeling geleverd
Then is er geen synchronisatiebericht gevonden
Then is er voor persoon met bsn 771168585 en leveringautorisatie Attendering geboortedatum verstrekkingsbeperking en partij Stichting Interkerkelijke Ledenadministratie geen afnemerindicatie geplaatst

Scenario: 3.   Mutatie waardoor persoon binnen populatiebeperking en attenderingscriterium komt te vallen
                LT: R1343_LT03
                Verwacht resultaat:
                - bericht, want verstrekkingsbeperking op partij is voor andere partij
                - Afnemerindicatie geplaatst

Given leveringsautorisatie uit autorisatie/attendering_geboortedatum_verstrekkingsbeperking_op_andere_partij
!-- Startsituatie opgegeven
Given persoonsbeelden uit BIJHOUDING:VZIG04C20T20/Specifieke_verstrekkingsbeperking_Partij/dbstate001

!-- Wijziging na mutatie zorgt dat attenderingcriterium afgaat
Given persoonsbeelden uit BIJHOUDING:VZIG04C20T20/Specifieke_verstrekkingsbeperking_Partij/dbstate002
When voor persoon 771168585 wordt de laatste handeling geleverd
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering geboortedatum verstrekkingsbeperking is ontvangen en wordt bekeken
Then is er 1 bericht geleverd
Then is er voor persoon met bsn 771168585 en leveringautorisatie Attendering geboortedatum verstrekkingsbeperking en partij Gemeente Utrecht een afnemerindicatie geplaatst
