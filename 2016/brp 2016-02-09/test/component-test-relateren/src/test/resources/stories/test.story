Meta:
@auteur         jaree
@epic           BRP Relateren
@jiraIssue      ORANJE-3947
@sleutelwoorden basis test
@status         development
@componenten    database, relateren

Narrative:
Test zodat de unit-test/regressie tests voor het relateren component goed werken.

Scenario: 1. Relateren van 2 personen met BRP personen
Meta:
@scenarioNaam   basisHuwelijk
Given het persoon beschrijving bestand: dsl/huwelijk.groovy
When persoon 8019523858 wordt gerelateerd
Then vergelijk persoon 8019523858 met expected
