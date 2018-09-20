Meta:

@auteur         jaree
@epic           BRP Relateren
@jiraIssue      ORANJE-3947
@sleutelwoorden basis test
@status         development
@componenten    database, relateren
@scenarionaam   basisHuwelijk

Narrative:
Test zodat de unit-test/regressie tests voor het relateren component goed werken.

Scenario: 1. Relateren van 2 personen met BRP personen
Given het persoon beschrijving bestand: dsl/huwelijk.groovy
When persoon 6068609810 wordt gerelateerd
Then vergelijk persoon 6068609810 met expected
