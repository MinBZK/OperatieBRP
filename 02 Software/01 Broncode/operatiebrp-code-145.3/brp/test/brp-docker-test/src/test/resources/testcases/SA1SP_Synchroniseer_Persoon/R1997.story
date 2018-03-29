Meta:
@status             Klaar
@usecase            SA.0.SP
@regels             R1997
@sleutelwoorden     Synchroniseer Persoon

Narrative:
Alle ingaande en uitgaande berichten worden gearchiveerd.
Het archiveren van een inkomend bericht gaat vooraf aan de XSD-validatie,
dus ook foutieve/niet in behandeling genomen berichten worden gearchiveerd.
Het archiveren van een uitgaand bericht vindt plaats nadat het bericht verzonden is,
zodat zeker is, dat de verzending heeft plaatsgevonden voordat archivering plaatsvindt.

Scenario: 1.    Bericht wordt niet gearchiveerd wanneer het bericht niet is verzonden
                LT: R1997_LT04
                Bericht = Uitgaand, XSD voldoet, Verzonden = NEE
                Toegang leveringsautorisatie afleverpunt is NULL
                Verwacht Resultaat: Het bericht is niet verzonden (omdat het afleverpunt van de autorisatie null is), dus niet gearchiveerd

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Geen npb levering obv doelbinding geenafleverpunt' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/SA1SP_Synchroniseer_Persoon/Requests/Synchoniseer_Persoon_R1997.xml


!-- Geen expected op antwoord bericht met verwerking geslaagd, want deze wordt niet geleverd omdat afleverpunt null is

When alle berichten zijn geleverd
!-- Geen synchronisatie omdat afleverpunt van de leveringsautorisatie null is
Then zijn er geen berichten ontvangen

!-- Controleer dat er geen archivering heeft plaats gevonden
Then bestaat er geen voorkomen in berpers tabel voor crossreferentie 0000000A-3000-7000-0000-000000000000 en srt lvg_synGeefSynchronisatiePersoon