Meta:
@status             Klaar
@usecase            BV.0.ZA
@sleutelwoorden     Zoek Persoon op adresgegevens
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

Scenario: 1.    Zoek persoon op adres met partij dan verstrekkingsbeperking aan toegewezen is
                LT:  R1983_LT15, R1342_LT02
                Verwacht resultaat: GEEN persoon gevonden, want verstrekkingsbeperking op partij

Given leveringsautorisatie uit autorisatie/Zoek_Persoon_adres_verstrekkingsbeperking_op_partij

Given persoonsbeelden uit BIJHOUDING:VZIG04C20T20/Specifieke_verstrekkingsbeperking_Partij/dbstate002

Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon Adres Afnemer'
|zendendePartijNaam|'Stichting Interkerkelijke Ledenadministratie'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Postcode,Waarde=2252EB

Then heeft het antwoordbericht verwerking Geslaagd
Then is er voor xpath //brp:burgerservicenummer[text()='771168585'] geen node aanwezig in het antwoord bericht

Scenario: 2.    Zoek persoon op adres met andere partij dan verstrekkingsbeperking aan toegewezen is
                LT: R1343_LT03
                Verwacht resultaat:
                - Persoon gevonden, want verstrekkingsbeperking op partij is voor andere partij

Given leveringsautorisatie uit autorisatie/Zoek_Persoon_adres_verstrekkingsbeperking_op_andere_partij

Given persoonsbeelden uit BIJHOUDING:VZIG04C20T20/Specifieke_verstrekkingsbeperking_Partij/dbstate002

Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon Adres Afnemer'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Postcode,Waarde=2252EB

Then heeft het antwoordbericht verwerking Geslaagd
Then is er voor xpath //brp:burgerservicenummer[text()='771168585'] een node aanwezig in het antwoord bericht