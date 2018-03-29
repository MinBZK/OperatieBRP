Meta:
@status                 Klaar
@sleutelwoorden         Docker
@usecase                UCS-BY.HG

Narrative:
Afhandelen van GBA en BRP berichten via de verschillende kanalen

Scenario: Verzoek ISC via ISC Docker versie
          LT: AFVZ01C10T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AFVZ/AFVZ01C10T10-Andrea.xls

When voer een GBA bijhouding uit AFVZ01C10T10.xml namens partij 'Migratievoorziening'

Then heeft het antwoordbericht verwerking Geslaagd
And is het antwoordbericht gelijk aan /testcases/bijhouding/AFVZ/expected/AFVZ01C10T10.xml voor expressie //brp:isc_migRegistreerHuwelijkGeregistreerdPartnerschap_R

