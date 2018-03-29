Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R2455 Er moet precies één OUWKIG geregistreerd worden

Scenario: Er wordt geen OUWKIG geregistreerd
          LT: GBNL02C130T10

Given alle personen zijn verwijderd

!-- Geboorte van het kind zonder OUWKIG
When voer een bijhouding uit GBNL02C130T10.xml namens partij 'Gemeente BRP 1'

!-- XSD-error
Then is het foutief antwoordbericht gelijk aan /testcases/bijhouding_AS/GBNL/expected/GBNL02C130T10.txt