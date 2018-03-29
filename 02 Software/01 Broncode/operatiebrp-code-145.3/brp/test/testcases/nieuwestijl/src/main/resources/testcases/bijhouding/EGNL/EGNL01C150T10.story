Meta:
@status                 Klaar
@regels                 R1874
@sleutelwoorden         Foutief
@usecase                UCS-BY.HG

Narrative:
R1874 Huwelijk en geregistreerd partnerschap, beeindig Geregistreerd Partnerschap in Nederland, actie beeindigingGeregistreerdPartnerschapInNederland

Scenario: R1874 Beeindiging van een geregistreerd partnerschap. Een verzoek in de bijhouding om een reeds ontbonden relatie te ontbinden.
          LT: EGNL01C150T10

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL-EGNL/April-HGP-ontbonden.xls

Given pas laatste relatie van soort 2 aan tussen persoon 594134377 en persoon 218046601 met relatie id 2000037 en betrokkenheid id 2000038

When voer een bijhouding uit EGNL01C150T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/EGNL/expected/EGNL01C150T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 594134377 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
