Meta:
@status             Klaar
@usecase            BV.0.ZP
@sleutelwoorden     Zoek Persoon
@regels             R1983

Narrative:
R1983:

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
                - Geen persoon gevonden

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/R1983/Rick_volledige_verstrekkingsbeperking.xls

Given verzoek voor leveringsautorisatie 'ZoekPersoonVerstrekkingsBeperking' en partij 'Gemeente VerstrekkingsbepMogelijk'

Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_R1983.xml

Then heeft het antwoordbericht verwerking Geslaagd
And heeft het antwoordbericht 0 groepen 'persoon'
And is het antwoordbericht xsd-valide





