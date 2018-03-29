Meta:
@auteur                 jozon
@status                 Klaar
@regels                 R1863
@sleutelwoorden         Foutief
@usecase                UCS-BY.HG

Narrative: R1863 Gemeente einde H/GP moet gelijk zijn aan gemeente aanvang

Scenario: Gemeente einde ongelijk Gemeente aanvang
          LT: EGNL01C180T10

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL-EGNL/EGNL01C70-April.xls

Given pas laatste relatie van soort 2 aan tussen persoon 594645001 en persoon 212737065 met relatie id 4000000 en betrokkenheid id 4000000

When voer een bijhouding uit EGNL01C180T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/EGNL/expected/EGNL01C180T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 594645001 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
