Meta:
@status             Klaar
@usecase            BV.0.ZP
@sleutelwoorden     Zoek Persoon
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

Scenario: 1.    Zoek persoon voor een partij met volledige verstrekkingsbeperking
                LT: R1983_LT11
                Verwacht resultaat:
                - Jan Vermeer niet gevonden
                Uitwerking:
                - 4 personen
                - Jan Vermeer (met volledige verstrekkingsbeperking)
                - Adèle Kleinman
                - Elizabeth Harvey
                - Harmen Vermeer

Given leveringsautorisatie uit /levering_autorisaties_nieuw/R1983/Zoek_persoon_volledige_verstrekkingbeperking
Given persoonsbeelden uit oranje:DELTAVERS05/DELTAVERS05C10T10_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek persoon volledige verstrekkingbeperking'
|zendendePartijNaam|'KUC033-PartijVerstrekkingsbeperking'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=434587977

Then heeft het antwoordbericht verwerking Geslaagd

!-- Het verwachte resultaat lijkt in deze API-test een beetje raar. Dit komt omdat er niet echt gezocht wordt op de zoekcriteria.
!-- Je krijgt alle personen terug die aangemaakt worden.
!-- Echter vindt de filtering op verstrekkingsbeperking wel goed plaats.
!-- Allen persoon Jan Vermeer heeft een volledige verstrekkingsbeperking.
!-- Dit is ook precies de persoon die weggefilterd wordt
!-- Vandaar dat er verder geen volledige expected in de test staat, Maar alleen een controle dat Jan niet geleverd wordt.

!-- Check dat Jan (bsn: 434587977)niet wordt geleverd
Then is er voor xpath //brp:voornamen[text()='Jan'] geen node aanwezig in het antwoord bericht
Then is er voor xpath //brp:burgerservicenummer[text()='434587977'] geen node aanwezig in het antwoord bericht


Scenario: 2.    Zoek persoon met partij dan verstrekkingsbeperking aan toegewezen is
                LT:  R1983_LT12, R1342_LT02
                Verwacht resultaat: GEEN persoon gevonden, want verstrekkingsbeperking op partij

Given leveringsautorisatie uit autorisatie/Zoek_Persoon_verstrekkingsbeperking_op_partij

Given persoonsbeelden uit BIJHOUDING:VZIG04C20T20/Specifieke_verstrekkingsbeperking_Partij/dbstate002

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon Afnemer'
|zendendePartijNaam|'Stichting Interkerkelijke Ledenadministratie'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=771168585

Then heeft het antwoordbericht verwerking Geslaagd
Then is er voor xpath //brp:burgerservicenummer[text()='771168585'] geen node aanwezig in het antwoord bericht

Scenario: 3.    Zoek persoon met andere partij dan verstrekkingsbeperking aan toegewezen is
                LT: R1343_LT03
                Verwacht resultaat:
                - Persoon gevonden, want verstrekkingsbeperking op partij is voor andere partij

Given leveringsautorisatie uit autorisatie/Zoek_Persoon_verstrekkingsbeperking_op_andere_partij

Given persoonsbeelden uit BIJHOUDING:VZIG04C20T20/Specifieke_verstrekkingsbeperking_Partij/dbstate002

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon Afnemer'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=771168585

Then heeft het antwoordbericht verwerking Geslaagd
Then is er voor xpath //brp:burgerservicenummer[text()='771168585'] een node aanwezig in het antwoord bericht