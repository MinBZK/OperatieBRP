Meta:
@auteur                 jaree
@status                 Onderhanden

Narrative:
Test zodat de unit-test/regressie tests voor het relateren component goed werken.

Scenario: 1. Relateren van 2 personen met BRP personen
Given alle personen zijn verwijderd
Given de persoonsbeschrijvingen in /testcases/Relateren/testPersonen.groovy
When persoon 283215161 wordt gerelateerd
Then persoon 283215161 is niet veranderd muv de volgende groepen Relatie
And onbekende persoon 555627433 is vervallen
And persoon 283215161 heeft een huwelijk met datum aanvang 20060101 en ingeschreven persoon 555627433
And persoon 283215161 heeft een vervallen huwelijk met datum aanvang 20060101 en onbekende persoon 555627433
