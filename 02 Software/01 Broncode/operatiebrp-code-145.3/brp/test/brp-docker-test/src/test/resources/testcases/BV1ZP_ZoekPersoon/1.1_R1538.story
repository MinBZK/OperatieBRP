Meta:
@status             Klaar
@usecase            LV.1.CPI
@regels             R1538
@sleutelwoorden     Zoek Persoon


Narrative:
Het systeem levert alleen persoonslijsten van ingeschreven personen (Persoon.Soort = "Ingeschrevene" (I)).

De overige personen ('pseudo personen') komen slechts voor als gerelateerde van een ingeschreven persoon en
worden alleen geleverd als onderdeel van de persoonslijst van een ingeschreven persoon.


Scenario:   1.  Zoek persoon dienst op pseudo-persoon vader Jan
                LT: R1538_LT06, R1266_LT07
                Verwacht resultaat:
                - Geen levering
                Uitwerking:
                - Zoek persoon
                - BSN hoofdpersoon Jan = 606417801
                - BSN Pseudo persoon vader Jan = 823306185

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_R1538.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide

!-- R1266_LT07 Controle op responsebericht
Then heeft in het antwoordbericht 'zendendePartij' in 'stuurgegevens' de waarde '199903'
Then heeft in het antwoordbericht 'zendendeSysteem' in 'stuurgegevens' de waarde 'BRP'
Then is in antwoordbericht de aanwezigheid van 'referentienummer' in 'stuurgegevens' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'crossReferentienummer' in 'stuurgegevens' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'tijdstipVerzending' in 'stuurgegevens' nummer 1 ja


Then is er voor xpath //brp:naam[text()='Personen'] geen node aanwezig in het antwoord bericht
