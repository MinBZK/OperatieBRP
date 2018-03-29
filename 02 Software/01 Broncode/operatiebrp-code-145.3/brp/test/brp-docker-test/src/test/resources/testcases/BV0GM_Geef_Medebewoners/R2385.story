Meta:
@status             Klaar
@usecase            BV.0.GM, BV.1.GM
@sleutelwoorden     Geef Medebewoner van Persoon
@regels             R2385

Narrative:
Het resultaatbericht bevat alleen gegevens van een Relatie indien
de gerelateerde ook als Persoon voorkomt in het resultaatbericht.
Toelichting: de gerelateerde kan ook een niet-ingeschrevene zijn; 'Niet-ingeschrevene' (R2272). In dit geval moet op gelijk
Persoon.Burgerservicenummer de gerelateerde als Persoon voorkomen in het resultaatbericht.

Scenario: 1.    Geef medebewoners met gerelateerde ingeschrevene
                LT: R2385_LT01
                Verwacht resultaat:
                - Libby en Piet als hoofdpersonen in het bericht
                - Libby als gerelateerde van Piet in bericht
                - Piet als gerelateerde van Libby in bericht
                - Dus 4 personen
                Let op: ROOD-2436

!-- Gemeente van inschrijving (0910) heeft hier een verkeerde code (7121 waar dit 0626 hoort te zijn)
!-- Bij testen met dienst selectie met plaatsen afnemerindicatie komt hier een foutmelding op terug
!-- Hier zou dus eigenlijk dezelfde foutmelding als in die story moeten komen
!-- ROOD-2436

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Libby.xls
Given enkel initiele vulling uit bestand /LO3PL/Piet.xls

Given verzoek voor leveringsautorisatie 'GeefMedebewoners' en partij 'Gemeente Standaard'
When voer een bijhouding uit /testcases/BRP_INTEGRATIE_TEST/IT_VHNL/VHNL04C10T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

Given verzoek voor leveringsautorisatie 'GeefMedebewoners' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV0GM_Geef_Medebewoners/xml/R2385_LT01.xml

Then heeft het antwoordbericht verwerking Geslaagd
And heeft het antwoordbericht 4 groepen 'persoon'
And is het antwoordbericht xsd-valide

Then is het antwoordbericht gelijk aan /testcases/BV0GM_Geef_Medebewoners/expected/R2385_scenario_1.xml voor expressie //brp:lvg_bvgGeefMedebewoners_R
