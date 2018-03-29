Meta:
@auteur                 jozon
@status                 Klaar
@regels                 R1864
@sleutelwoorden         Geslaagd
@usecase                UCS-BY.HG

Narrative: R1864 Datum einde relatie moet op of na datum aanvang relatie liggen

Scenario: Datum einde relatie gelijk aan Datum aanvang relatie
          LT: EGNL01C190T20

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL-EGNL/EGNL01C190-April.xls

Given pas laatste relatie van soort 2 aan tussen persoon 983074057 en persoon 212737065 met relatie id 4000006 en betrokkenheid id 4000006

When voer een bijhouding uit EGNL01C190T20.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/EGNL/expected/EGNL01C190T20.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 983074057 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
