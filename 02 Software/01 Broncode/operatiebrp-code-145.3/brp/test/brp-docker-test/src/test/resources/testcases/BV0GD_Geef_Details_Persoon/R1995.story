Meta:
@status             Klaar
@usecase            AL.1.VZ
@regels             R1266, R1995, R1997
@sleutelwoorden     Verzenden

Narrative:
Ieder uitgaand bericht, waarin persoonsgegevens zijn opgenomen en waarbij geldt dat:

Het protcolleringsniveau van de Leveringsautorisatie niet geheim is (Leveringsautorisatie.Protocolleringsniveau heeft niet de waarde "Geheim")
De Rol van de Toegang leveringsautorisatie (Via Partij \ Rol) gelijk is aan "Afnemer"

dient geprotocolleerd te worden. De wijze waarop geprotocolleerd dient te worden is afhankelijk van de Soort dienst en is uitgewerkt is aparte verwerkingslogica.

(Toelichting: Leveringen aan bijhouders dienen dus NIET geprotocolleerd te worden. Leveringen aan afnemers dienen wel geprotcolleerd te worden, maar NIET als de leveringsautorisatie protocolleringsniveau "Geheim" heeft)

Scenario: 1.    Het protcolleringsniveau van de Leveringsautorisatie = geheim EN De Rol van de Toegang leveringsautorisatie = "Afnemer"
                LT: R1995_LT02
                Verwacht resultaat: Er wordt niet geprotocolleerd

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Bewerker autorisatie protniveau2' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV0GD_Geef_Details_Persoon/verzoek/Autorisatie_Geef_Details_Persoon_1.xml

Then heeft het antwoordbericht verwerking Geslaagd

!-- R1995_LT02: Er wordt niet geprotocolleeerd
Then is er niet geprotocolleerd

Scenario: 2.    Het protcolleringsniveau van de Leveringsautorisatie <> geheim EN De Rol van de Toegang leveringsautorisatie <> "Afnemer"
                LT: R1995_LT03, R1266_LT06
                Verwacht resultaat: Er wordt niet geprotocolleerd
                Dienst: geef details Persoon

!-- Partij met rol ongelijk aan afnemer
Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

!-- Omdat er een andere rol in de leveringsautorisatie gebruikt is dan afnemer, moet dit ook in de parameters van het request goed staan
Given verzoek voor leveringsautorisatie 'Partijrol Bijhoudingsorgaan Minister' en partij 'BRP Minister Test'
Given xml verzoek uit bestand /testcases/BV0GD_Geef_Details_Persoon/verzoek/Autorisatie_Geef_Details_Persoon_2.xml

Then heeft het antwoordbericht verwerking Geslaagd

!-- R1266_LT06 Controle op responsebericht
Then heeft in het antwoordbericht 'zendendePartij' in 'stuurgegevens' de waarde '199903'
Then heeft in het antwoordbericht 'zendendeSysteem' in 'stuurgegevens' de waarde 'BRP'
Then is in antwoordbericht de aanwezigheid van 'referentienummer' in 'stuurgegevens' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'crossReferentienummer' in 'stuurgegevens' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'tijdstipVerzending' in 'stuurgegevens' nummer 1 ja


!-- R1995_LT03: Er wordt niet geprotocolleeerd omdat de rol <> afnemer, de in de levsautorisatie gebruikte partij heeft rol bijhoudingsorgaan minister
Then is er niet geprotocolleerd voor persoon 606417801

Scenario: 3.    Het protcolleringsniveau van de Leveringsautorisatie <> geheim EN De Rol van de Toegang leveringsautorisatie <> "Afnemer" (bijhoudingsorgaan college)
                LT: R1995_LT03
                Verwacht resultaat: Er wordt niet geprotocolleerd
                Dienst: geef details persoon

Given verzoek voor leveringsautorisatie 'geefDetailsPersoon' en partij 'Gemeente BRP 1'
Given xml verzoek uit bestand /testcases/BV0GD_Geef_Details_Persoon/verzoek/Autorisatie_Geef_Details_Persoon_3.xml

Then heeft het antwoordbericht verwerking Geslaagd

!-- R1995_LT03: Er wordt niet geprotocolleeerd omdat de rol <> afnemer, de in de levsautorisatie gebruikte partij heeft rol bijhoudingsorgaan college
Then is er niet geprotocolleerd
