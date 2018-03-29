Meta:
@status                 Klaar
@regels                 R1860
@usecase                UCS-BY.HG

Narrative:
R1860 Datum einde H/GP moet geldige kalenderdatum zijn bij einde in NL

Scenario: Datum einde waarbij dd onbekend is (2016-01)
          LT: EGNL01C20T40

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-EGNL/EGNL01C70-April.xls

Given pas laatste relatie van soort 2 aan tussen persoon 594645001 en persoon 212737065 met relatie id 2000005 en betrokkenheid id 2000006

When voer een bijhouding uit EGNL01C20T40.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
And is het antwoordbericht gelijk aan /testcases/bijhouding/EGNL/expected/EGNL01C20T40.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 594645001 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP


