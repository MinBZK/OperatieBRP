Meta:
@status                 Klaar
@sleutelwoorden         Docker
@usecase                UCS-BY.HG

Narrative:
Afhandelen van GBA en BRP berichten via de verschillende kanalen

Scenario: Verzoek ISC via BRP Docker versie
          LT: AFVZ01C10T20

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AFVZ/AFVZ01C10T10-Andrea.xls

When voer een bijhouding uit AFVZ01C10T20.xml namens partij 'Migratievoorziening'

Then is het foutief antwoordbericht gelijk aan /testcases/bijhouding/AFVZ/expected/AFVZ01C10T20.xml