Meta:
@auteur                 wipit
@status                 Onderhanden
@sleutelwoorden         Docker
@usecase                UCS-BY.HG

Narrative:
Afhandelen van Bevraging berichten via Docker

Scenario:   Bevraging BRP via BRP via Docker
            LT: AFVZ02C10T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Libby.xls
Given enkel initiele vulling uit bestand /LO3PL/Piet.xls

When voer een bevraging uit AFVZ02C10T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
