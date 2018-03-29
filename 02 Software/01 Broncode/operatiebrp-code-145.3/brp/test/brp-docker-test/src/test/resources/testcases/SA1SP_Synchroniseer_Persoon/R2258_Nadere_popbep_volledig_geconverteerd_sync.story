Meta:
@status             Klaar
@usecase            LV.1.AL
@regels             R2258
@sleutelwoorden     Autorisatie levering

Narrative:
Dienstbundels waarbij Dienstbundel.Nadere populatiebeperking volledig geconverteerd? gelijk is aan
"Nee" dienen buiten beschouwing gelaten te worden.
LET OP
In de E2E getest omdat de API testen de Dienstbundel.Nadere populatiebeperking volledig geconverteerd gelijk aan NEE,
niet buiten beschouwing laat

Scenario: 1.    Dienstbundel.Nadere populatiebeperking = null
                LT: R2258_LT02, R2258_LT03
                Verwacht resultaat: Geslaagd

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Anne_met_Historie.xls
Given verzoek voor leveringsautorisatie 'indicatie npb volledig geconverteerd isnull' en partij 'Gemeente Standaard'
!-- R2258_LT02
Given xml verzoek uit bestand /testcases/SA1SP_Synchroniseer_Persoon/Requests/R2258_Nadere_popbep_volledig_geconverteerd_Synchronisatie.xml
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht verwerking Geslaagd

!-- R2258_LT03
!-- When voor persoon 590984809 wordt de laatste handeling geleverd
When alle berichten zijn geleverd
Then is er een volledigbericht ontvangen voor leveringsautorisatie indicatie npb volledig geconverteerd isnull

Scenario: 2.    Dienstbundel.Nadere populatiebeperking = false
                LT: R2258_LT08, R2258_LT09
                Verwacht resultaat: Fout situatie
                -Fout: De dienstenbundel heeft een onvolledig geconverteerde expressie.
                - Persoon uit scenario 1 wordt herbruikt

Given verzoek voor leveringsautorisatie 'indicatie npb volledig geconverteerd isfalse' en partij 'Gemeente Standaard'
!-- R2258_LT08
Given xml verzoek uit bestand /testcases/SA1SP_Synchroniseer_Persoon/Requests/R2258_Nadere_popbep_volledig_geconverteerd_Synchronisatie.xml
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht verwerking Foutief

!-- R2258_LT09
When alle berichten zijn geleverd
Then zijn er geen berichten ontvangen

Scenario: 3.    Dienstbundel.Nadere populatiebeperking = false & Dienstbundel.Nadere populatiebeperking = false
                LT: R2258_LT11, R2258_LT12
                Uitwerking:
                testgevallen om te valideren dat andere dienstbundels wel geldig zijn voor de leveringsautorisatie
                wanneer 1 van de dienstbundels een Dienstbundel.Nadere populatiebeperking = false heeft
                Verwacht resultaat:
                - Fout situatie voor diensten in dienstbundel waarvan Dienstbundel.Nadere populatiebeperking = false (synchroniseerpersooon)
                - Goed situatie voor diensten in dienstbundel waarvan Dienstbundel.Nadere populatiebeperking = null (geef details persoon)
                - Persoon uit scenario 1 wordt herbruikt

Given verzoek voor leveringsautorisatie 'indicatie npb volledig geconverteerd isfalse en isnull' en partij 'Gemeente Standaard'
!-- R2258_LT11 - Foutief want in dienstbundel waarvoor geldt Dienstbundel.Nadere populatiebeperking = false
Given xml verzoek uit bestand /testcases/SA1SP_Synchroniseer_Persoon/Requests/R2258_Nadere_popbep_volledig_geconverteerd_Synchronisatie.xml
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht verwerking Foutief

!-- R2258_LT12 - Geen mutatie bericht want dienst mut lev in dienstbundel waarvoor geldt Dienstbundel.Nadere populatiebeperking = false
When alle berichten zijn geleverd
Then zijn er geen berichten ontvangen