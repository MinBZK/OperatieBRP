Meta:
@auteur                 tjlee
@status                 Klaar
@regels                 R2254
@sleutelwoorden         Geslaagd
@usecase                UCS-BY.HG

Narrative:
R2254 Huwelijk en geregistreerd partnerschap, beeindig Geregistreerd Partnerschap in Nederland, actie beeindigingGeregistreerdPartnerschapInNederland

Scenario: R2254 Beeindiging van een geregistreerd partnerschap. Samengestelde naam komt voor bij een niet-ingeschrevene.
          LT: EGNL01C170T20

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL-EGNL/EGNL01C170T20-April.xls

Given pas laatste relatie van soort 2 aan tussen persoon 986557961 en persoon 714801033 met relatie id 2000051 en betrokkenheid id 2000052

When voer een bijhouding uit EGNL01C170T20.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/EGNL/expected/EGNL01C170T20.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 986557961 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
