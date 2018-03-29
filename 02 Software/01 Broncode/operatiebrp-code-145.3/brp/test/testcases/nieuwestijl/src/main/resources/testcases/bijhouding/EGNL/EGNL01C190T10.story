Meta:
@auteur                 jozon
@status                 Klaar
@regels                 R1864
@sleutelwoorden         Foutief
@usecase                UCS-BY.HG

Narrative: R1864 Datum einde relatie moet op of na datum aanvang relatie liggen

Scenario: Datum einde relatie kleienr dan Datum aanvang relatie
          LT: EGNL01C190T10

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL-EGNL/EGNL01C190-April.xls

Given pas laatste relatie van soort 2 aan tussen persoon 983074057 en persoon 212737065 met relatie id 4000005 en betrokkenheid id 4000005

When voer een bijhouding uit EGNL01C190T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/EGNL/expected/EGNL01C190T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 983074057 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
