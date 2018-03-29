Meta:
@status                 Klaar
@regels                 R1863
@sleutelwoorden         Geslaagd
@usecase                UCS-BY.HG

Narrative: R1863 Gemeente einde H/GP moet gelijk zijn aan gemeente aanvang

Scenario: Gemeente einde ongelijk Gemeente aanvang
          LT: EGNL01C180T20

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL-EGNL/EGNL01C180T20-May.xls

Given pas laatste relatie van soort 2 aan tussen persoon 589473001 en persoon 212737065 met relatie id 4000001 en betrokkenheid id 4000001

When voer een bijhouding uit EGNL01C180T20.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/EGNL/expected/EGNL01C180T20.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 589473001 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
