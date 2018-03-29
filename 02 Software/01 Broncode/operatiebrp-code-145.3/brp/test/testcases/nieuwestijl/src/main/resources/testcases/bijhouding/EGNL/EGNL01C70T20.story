Meta:
@auteur                 tjlee
@status                 Klaar
@regels                 R2040
@sleutelwoorden         Geslaagd
@usecase                UCS-BY.HG

Narrative:
R2040 Huwelijk en geregistreerd partnerschap, beeindig Geregistreerd Partnerschap in Nederland, actie beeindigingGeregistreerdPartnerschapInNederland

Scenario: R2040 Beeindiging van een geregistreerd partnerschap. Woonplaatsnaam relatie einde is niet ingevuld.
          LT: EGNL01C70T20

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL-EGNL/EGNL01C70-April.xls

Given pas laatste relatie van soort 2 aan tussen persoon 594645001 en persoon 212737065 met relatie id 2000019 en betrokkenheid id 2000020

When voer een bijhouding uit EGNL01C70T20.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/EGNL/expected/EGNL01C70T20.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 594645001 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
