Meta:
@status             Klaar
@usecase            BV.0.ZP
@sleutelwoorden     Zoek Persoon
@regels             R2293

Narrative:
Indien een zoekcriterium de optie 'Vanaf' bevat, dat dient er een match te zijn met het tekstdeel van de opgegeven waarde na de omzetting van hoofdletters en diakritische tekens naar de bijbehorende kleine letters. Deze optie is alleen mogelijk bij tekstvelden:
De geconverteerde inhoud van de het opgegeven deel dient gelijk te zijn (dus na omzetting van hoofdletters en diakritische tekens). De aanwezige tekst mag dus langer zijn dan de opgegeven zoektekst. Het 'overblijvende deel' wordt niet beschouwd in de vergelijking.

Scenario:   1. Zoeken met optie Vanaf exact met textattribuut zoeken op achternaam ßlußmann
            LT: R2281_LT05, R2734_LT01, R2734_LT02
            Doel: Vaststellen dat de zoekpersoon dienst correcte resultaten levert indien gezocht wordt
            met de optie 'Vanaf exact' voor alfanummerieke waarden en datum velden
            1 persoon wordt ingeladen met achternamen diakritische tekens bevat.
            1 persoon wordt ingeladen met achternaam slussman
            Verwacht resultaat:
            1 Persoon met achternaam ßlußmann wordt gevonden

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/BV1ZP_Slusmann.xls
Given enkel initiele vulling uit bestand /LO3PL/BV1ZP_SSlussmann_zonder_diakrieten.xls
Given enkel initiele vulling uit bestand /LO3PL/BV1ZP_Slusmann_zonder_diakrieten.xls

!-- Geslachtsnaam ßlußmann wordt volgens de conversie van diakritische tekens sslussmann
!-- Zoeken met optie 'Vanaf exact' met waarde 'ßluß' op geslachtsnaam zou dus de persoon op moeten leveren.
!-- Persoon SSlussman zonder diakrieten wordt niet gevonden
!-- Persoon Slusman zonder diakrieten wordt niet gevonden
Given verzoek voor leveringsautorisatie 'Zoek Persoon' en partij 'Gemeente Standaard'
Given xml verzoek uit bestand /testcases/BV1ZP_ZoekPersoon/Requests/Zoek_Persoon_R2281_1.xml

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht xsd-valide

Then heeft het antwoordbericht 1 groepen 'persoon'
!-- check of de diakritische tekens correct worden geleverd.
Then is er voor xpath //brp:geslachtsnaamstam[text()='ßlußmann'] een node aanwezig in het antwoord bericht

