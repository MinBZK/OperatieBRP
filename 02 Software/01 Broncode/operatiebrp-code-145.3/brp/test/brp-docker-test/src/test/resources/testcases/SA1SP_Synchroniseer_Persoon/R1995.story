Meta:
@status             Klaar
@usecase            AL.1.VZ
@regels             R1995, R1266
@sleutelwoorden     Verzenden

Narrative:
Ieder uitgaand bericht, waarin persoonsgegevens zijn opgenomen en waarbij geldt dat:

Het protcolleringsniveau van de Leveringsautorisatie niet geheim is (Leveringsautorisatie.Protocolleringsniveau heeft niet de waarde "Geheim")
De Rol van de Toegang leveringsautorisatie (Via Partij \ Rol) gelijk is aan "Afnemer"

dient geprotocolleerd te worden. De wijze waarop geprotocolleerd dient te worden is afhankelijk van de Soort dienst en is uitgewerkt is aparte verwerkingslogica.

(Toelichting: Leveringen aan bijhouders dienen dus NIET geprotocolleerd te worden. Leveringen aan afnemers dienen wel geprotcolleerd te worden, maar NIET als de leveringsautorisatie protocolleringsniveau "Geheim" heeft)

Scenario: 1.    Het protcolleringsniveau van de Leveringsautorisatie <> geheim EN De Rol van de Toegang leveringsautorisatie <> "Afnemer"
                LT: R1995_LT03, R1266_LT04
                Verwacht resultaat: Er wordt niet geprotocolleerd
                Dienst: Synchroniseer Persoon

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Partijrol Bijhoudingsorgaan Minister' en partij 'BRP Minister Test'
Given xml verzoek uit bestand /testcases/SA1SP_Synchroniseer_Persoon/Requests/Synchroniseer_Persoon_R1995.xml

Then heeft het antwoordbericht verwerking Geslaagd

!-- R1266_LT04 Controle op responsebericht
Then heeft in het antwoordbericht 'zendendePartij' in 'stuurgegevens' de waarde '199903'
Then heeft in het antwoordbericht 'zendendeSysteem' in 'stuurgegevens' de waarde 'BRP'
Then is in antwoordbericht de aanwezigheid van 'referentienummer' in 'stuurgegevens' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'crossReferentienummer' in 'stuurgegevens' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'tijdstipVerzending' in 'stuurgegevens' nummer 1 ja

When alle berichten zijn geleverd

!-- R1995_LT03: Er wordt niet geprotocolleeerd omdat de rol <> afnemer, de in de levsautorisatie gebruikte partij heeft rol bijhoudingsorgaan minister
Then is er niet geprotocolleerd
