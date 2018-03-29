Meta:
@auteur                 fuman
@status                 Klaar
@sleutelwoorden         Docker
@usecase                UCS-BY.HG

Narrative:
Afhandelen van GBA en BRP berichten via de verschillende kanalen

Scenario:   Verzoek BRP via BRP via Docker
            LT: AFVZ01C10T40



Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Libby.xls
Given enkel initiele vulling uit bestand /LO3PL/Piet.xls

When voer een bijhouding uit AFVZ01C10T40.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/AFVZ/expected/AFVZ01C10T40.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R


