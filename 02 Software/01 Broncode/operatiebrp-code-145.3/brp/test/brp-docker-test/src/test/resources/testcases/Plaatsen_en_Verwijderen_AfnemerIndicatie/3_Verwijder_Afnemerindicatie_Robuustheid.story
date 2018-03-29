Meta:
@status             Klaar
@usecase            SA.0.PA, SA.0.VA
@sleutelwoorden     requestberichten

Narrative:
Robuustheid testen voor request berichten van verwijderen afnemerindicatie


Scenario:   1.1 Verwijder afnemerindicatie 3 verkeerde partijcodes
                Verwacht resultaat:
                - Foutief: omdat partijcode niet voldoet aan xsd restrictie


Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Libby.xls

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/Plaatsen_en_Verwijderen_AfnemerIndicatie/Requests/3_Verwijder_Afnemerindicatie_Robuustheid_scenario_1.xml

Then is het antwoordbericht een soapfault