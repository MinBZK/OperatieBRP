Meta:
@status             Klaar
@usecase            SA.0.PA, SA.0.VA
@sleutelwoorden     requestberichten

Narrative:
Robuustheid testen voor request berichten van plaatsen afnemerindicatie


Scenario:   1.1 Plaats afnemerindicatie 1 verkeerde partijcode (overige 2 zijn goed)
                Verwacht resultaat:
                - Foutief: xsd fout omdat partijcode niet voldoet


Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Libby.xls

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/Plaatsen_en_Verwijderen_AfnemerIndicatie/Requests/2_Plaats_Afnemerindicatie_Robuustheid_scenario_1.xml

Then is het antwoordbericht een soapfault

Scenario:   1.2 Plaats afnemerindicatie 3 verkeerde partijcode
                Verwacht resultaat:
                - Foutief: xsd fout omdat partijcode niet voldoet


Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Libby.xls

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/Plaatsen_en_Verwijderen_AfnemerIndicatie/Requests/2_Plaats_Afnemerindicatie_Robuustheid_scenario_1.2.xml

Then is het antwoordbericht een soapfault

Scenario:   2.1 Plaats afnemerindicatie datumAanvangMaterielePeriode (optioneel) gevuld op juiste wijze
                Verwacht resultaat:
                - Geslaagd

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Libby.xls

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/Plaatsen_en_Verwijderen_AfnemerIndicatie/Requests/2_Plaats_Afnemerindicatie_Robuustheid_scenario_2.1.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide

Scenario:   2.2 Plaats afnemerindicatie datumAanvangMaterielePeriode (optioneel) gevuld op onjuiste wijze (letters)
                Verwacht resultaat:
                - Foutief

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Libby.xls

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/Plaatsen_en_Verwijderen_AfnemerIndicatie/Requests/2_Plaats_Afnemerindicatie_Robuustheid_scenario_2.2.xml

Then is het antwoordbericht een soapfault

Scenario:   2.3 Plaats afnemerindicatie datumAanvangMaterielePeriode (optioneel) gevuld op onjuiste wijze (ongeldige datum)
                Verwacht resultaat:
                - Foutief

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Libby.xls

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/Plaatsen_en_Verwijderen_AfnemerIndicatie/Requests/2_Plaats_Afnemerindicatie_Robuustheid_scenario_2.3.xml

Then is het antwoordbericht een soapfault

Scenario:   2.4 Plaats afnemerindicatie datumAanvangMaterielePeriode (optioneel) leeg datumEindevolgen gevuld
                Verwacht resultaat:
                - ??

!-- datum aanvang leeg, maar datum einde gevuld gaat goed
!-- Bij andere diensten mag datum aanvang niet leeg zijn
!-- Hoort dit zo, of zou datum ingang bij plaatsen afnemerindicatie eigenlijk ook niet leeg moeten mogen zijn?

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Libby.xls

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/Plaatsen_en_Verwijderen_AfnemerIndicatie/Requests/2_Plaats_Afnemerindicatie_Robuustheid_scenario_2.4.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide


Scenario:   3.1 Plaats afnemerindicatie verplicht veld verwijderd uit request
                Verwacht resultaat:
                - Foutief

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Libby.xls

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/Plaatsen_en_Verwijderen_AfnemerIndicatie/Requests/2_Plaats_Afnemerindicatie_Robuustheid_scenario_3.1.xml

Then is het antwoordbericht een soapfault