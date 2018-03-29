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
                LT: R2258_LT01
                Usecase Afhandelen verzoek AL1AV
                Verwacht resultaat: Geslaagd

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Anne_met_Historie.xls
Given verzoek voor leveringsautorisatie 'indicatie npb volledig geconverteerd isnull' en partij 'Gemeente Standaard'

!-- R2258_LT01
Given xml verzoek uit bestand /testcases/BV0GD_Geef_Details_Persoon/verzoek/R2258_Nadere_popbep_volledig_geconverteerd_GeefDetails.xml

Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht verwerking Geslaagd


Scenario: 2.    Dienstbundel.Nadere populatiebeperking = false
                LT: R2258_LT07
                Verwacht resultaat: Fout situatie
                -Fout: De dienstenbundel heeft een onvolledig geconverteerde expressie.


Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Anne_met_Historie.xls

Given verzoek voor leveringsautorisatie 'indicatie npb volledig geconverteerd isfalse' en partij 'Gemeente Standaard'
!-- R2258_LT07
Given xml verzoek uit bestand /testcases/BV0GD_Geef_Details_Persoon/verzoek/R2258_Nadere_popbep_volledig_geconverteerd_GeefDetails.xml
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht verwerking Foutief


Scenario: 3.    Dienstbundel.Nadere populatiebeperking = false & Dienstbundel.Nadere populatiebeperking = false
                LT: R2258_LT10
                Verwacht resultaat:
                - Fout situatie voor diensten in dienstbundel waarvan Dienstbundel.Nadere populatiebeperking = false (synchroniseerpersooon)
                - Goed situatie voor diensten in dienstbundel waarvan Dienstbundel.Nadere populatiebeperking = null (geef details persoon)


!-- testgevallen om te valideren dat andere dienstbundels wel geldig zijn voor de leveringsautorisatie
!-- wanneer 1 van de dienstbundels een Dienstbundel.Nadere populatiebeperking = false heeft

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Anne_met_Historie.xls
Given verzoek voor leveringsautorisatie 'indicatie npb volledig geconverteerd isfalse en isnull' en partij 'Gemeente Standaard'

!-- R2258_LT10 - Geslaagd want in dienstbundel waarvoor geldt Dienstbundel.Nadere populatiebeperking = null
Given xml verzoek uit bestand /testcases/BV0GD_Geef_Details_Persoon/verzoek/R2258_Nadere_popbep_volledig_geconverteerd_GeefDetails.xml
Then is het antwoordbericht xsd-valide
Then heeft het antwoordbericht verwerking Geslaagd
