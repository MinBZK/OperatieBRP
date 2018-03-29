Meta:
@status             Klaar
@usecase            AL.1.VZ
@regels             R1997
@sleutelwoorden     Verzenden

Narrative:
Alle ingaande en uitgaande berichten worden gearchiveerd.
Het archiveren van een inkomend bericht gaat vooraf aan de XSD-validatie,
dus ook foutieve/niet in behandeling genomen berichten worden gearchiveerd.
Het archiveren van een uitgaand bericht vindt plaats nadat het bericht verzonden is,
zodat zeker is, dat de verzending heeft plaatsgevonden voordat archivering plaatsvindt.

Scenario: 1.    Er wordt een afnemerindicatie geplaatst via een niet XSD valide bericht.
                LT: R1997_LT02
                Verwacht resultaat:
                - GEEN bericht geleverd, en dus NIET gearchiveerd

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/Plaatsen_en_Verwijderen_AfnemerIndicatie/Requests/Plaats_Niet_XSD_Valide_Afnemerindicatie.xml

Then bestaat er geen voorkomen in berpers tabel voor referentie 00000000-0000-0000-0000-999999991234 en srt lvg_synRegistreerAfnemerindicatie
