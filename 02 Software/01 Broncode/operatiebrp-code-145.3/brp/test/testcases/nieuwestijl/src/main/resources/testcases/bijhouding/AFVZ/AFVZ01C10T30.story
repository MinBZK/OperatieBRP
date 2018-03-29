Meta:
@auteur                 fuman
@status                 Klaar
@sleutelwoorden         Docker
@usecase                UCS-BY.HG

Narrative:
Afhandelen van GBA en BRP berichten via de verschillende kanalen

Scenario:   Verzoek BRP via ISC via Docker
            LT: AFVZ01C10T30



Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Libby.xls
Given enkel initiele vulling uit bestand /LO3PL/Piet.xls

When voer een GBA bijhouding uit AFVZ01C10T30.xml namens partij 'Gemeente BRP 1'

Then is het foutief antwoordbericht gelijk aan /testcases/bijhouding/AFVZ/expected/AFVZ01C10T30.txt
