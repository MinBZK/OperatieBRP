Meta:
@status             Klaar
@regels             Requestberichten

Narrative:
Robuustheid testen voor request berichten van Synchroniseer Persoon

Scenario:   1.  Synchroniseer Persoon 1 verkeerde partijcode (overige 2 zijn goed)
                Verwacht resultaat:
                - Foutmelding <> ALG0001

Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van doelbinding' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/SA1SP_Synchroniseer_Persoon/Requests/Synchroniseer_Persoon_Scenario_1.xml

Then is het antwoordbericht een soapfault

Scenario:   2.      Synchroniseer Persoon verplicht veld verwijderd uit request (zendendePartij)
                    Verwacht resultaat:
                    - Niet xsd valide, dus soapfault

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van doelbinding' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/SA1SP_Synchroniseer_Persoon/Requests/Synchroniseer_Persoon_Scenario_2.xml

Then is het antwoordbericht een soapfault
!-- And is het ontvangen bericht voor expressie //faultstring gelijk aan /testcases/SA1SP_Synchroniseer_Persoon/expected/expected_scenario_2.xml


Scenario:   3.      Synchroniseer Persoon verplicht veld verwijderd uit request (Parameters)
                    Verwacht resultaat:
                    - Niet xsd valide, dus soapfault

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van doelbinding' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/SA1SP_Synchroniseer_Persoon/Requests/Synchroniseer_Persoon_Scenario_3.xml

Then is het antwoordbericht een soapfault

Scenario:   4.      Synchroniseer Persoon verplicht veld verwijderd uit request (referentienummer)
                    Verwacht resultaat:
                    - Niet xsd valide, dus soapfault

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van doelbinding' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/SA1SP_Synchroniseer_Persoon/Requests/Synchroniseer_Persoon_Scenario_4.xml

Then is het antwoordbericht een soapfault

Scenario:   5.      Synchroniseer Persoon verplicht veld verkeerd gevuld uit request (referentienummer)
                    Verwacht resultaat:
                    - Niet xsd valide, dus soapfault

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van doelbinding' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/SA1SP_Synchroniseer_Persoon/Requests/Synchroniseer_Persoon_Scenario_5.xml

Then is het antwoordbericht een soapfault

Scenario:   6.      Synchroniseer Persoon extra veld toegevoegd (peilmoment)
                    Verwacht resultaat:
                    - Niet xsd valide, dus soapfault

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van doelbinding' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/SA1SP_Synchroniseer_Persoon/Requests/Synchroniseer_Persoon_Scenario_6.xml

Then is het antwoordbericht een soapfault

Scenario:   7.      Synchroniseer Persoon elementen in de verkeerde volgorde
                    Verwacht resultaat:
                    - Niet xsd valide, dus soapfault

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van doelbinding' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/SA1SP_Synchroniseer_Persoon/Requests/Synchroniseer_Persoon_Scenario_6.xml

Then is het antwoordbericht een soapfault

Scenario:   8.      Synchroniseer Persoon elementen leeg gelaten
                    Verwacht resultaat:
                    - Niet xsd valide, dus soapfault

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van doelbinding' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/SA1SP_Synchroniseer_Persoon/Requests/Synchroniseer_Persoon_Scenario_8.xml

Then is het antwoordbericht een soapfault