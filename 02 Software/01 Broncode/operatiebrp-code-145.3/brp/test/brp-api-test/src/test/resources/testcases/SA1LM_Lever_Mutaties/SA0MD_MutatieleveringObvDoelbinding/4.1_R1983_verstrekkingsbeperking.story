Meta:
@status             Klaar
@usecase            SA.0.MD
@regels             R1983
@sleutelwoorden     Mutatielevering o.b.v. doelbinding, Lever Mutaties

Narrative:

Indien er sprake is van een 'verstrekkingsbeperking bij de Persoon voor ontvangende partij dan mag die Persoon in principe niet geleverd worden als zoekresultaat, bevragingsresultaat, selectieresultaat of in een spontaan bericht van het systeem.

Hiervan is sprake als Persoon Niet voldoet aan 'Persoon heeft een verstrekkingsbeperking voor Partij' (R1342).

In de volgende situaties is er echter sprake van een uitzondering en mag de Dienst wel worden geleverd:
Mutatielevering op basis van afnemerindicatie (hier dient de afnemer vooralsnog zelf zijn indicatie te verwijderen; bovendien moet hij op de hoogte worden gebracht van het instellen van de verstrekkingsbeperking)
Diensten die de afnemerindicatie verwijderen (daarbij wordt ook geen / minimale persoonsinformatie geleverd): Verwijdering afnemerindicatie en 'Selectie met verwijdering afnemerindicatie'.

Noot: De dienst Mutatielevering op basis van doelbinding ondersteunen we niet voor afnemers waarbij een verstrekkingsbeperking van toepassing kan zijn. Ook bij deze dienst hoeft deze melding dus niet af te gaan.

RESULTATEN ZIJN DUS EIGENLIJK ALTIJD GOED

Scenario:   1.  Doelbinding met volledige verstrekkingsbeperking
                LT: R1983_LT26
                 Verwacht resultaat:
                 - Levering geen melding

Given leveringsautorisatie uit autorisatie/volledige_verstrekkingsbeperking
Given persoonsbeelden uit oranje:DELTAVERS05/DELTAVERS05C10T10_xls
When voor persoon 434587977 wordt de laatste handeling geleverd
When het volledigbericht voor partij KUC033-PartijVerstrekkingsbeperking en leveringsautorisatie volledige verstrekkingsbeperking is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan Expecteds/Verstrekkingsbeperking_scenario_2.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 2.    Doelbinding met verstrekkingsbeperking op partij
                LT: R1983_LT27
                Verwacht resultaat:
                - Levering geen melding


Given leveringsautorisatie uit autorisatie/Mutatielevering_doelbinding_verstrekkingsbeperking_op_partij

Given persoonsbeelden uit BIJHOUDING:VZIG04C20T20/Specifieke_verstrekkingsbeperking_Partij/dbstate002
When voor persoon 771168585 wordt de laatste handeling geleverd

When het mutatiebericht voor partij Stichting Interkerkelijke Ledenadministratie en leveringsautorisatie Doelbinding Afnemer is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan Expecteds/Verstrekkingsbeperking_scenario_1.xml voor expressie //brp:lvg_synVerwerkPersoon