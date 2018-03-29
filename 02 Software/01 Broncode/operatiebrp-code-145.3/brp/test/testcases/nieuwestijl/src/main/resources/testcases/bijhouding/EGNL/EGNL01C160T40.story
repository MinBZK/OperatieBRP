Meta:
@auteur                 tjlee
@status                 Klaar
@regels                 R1876
@sleutelwoorden         Geslaagd
@usecase                UCS-BY.HG

Narrative:
R1876 Huwelijk en geregistreerd partnerschap, beeindig Geregistreerd Partnerschap in Nederland, actie beeindigingGeregistreerdPartnerschapInNederland

Scenario: R1876 Beeindiging van een geregistreerd partnerschap. Reden einde is R.
          LT: EGNL01C160T40

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL-EGNL/EGNL01C160T40-April.xls

Given pas laatste relatie van soort 2 aan tussen persoon 703544329 en persoon 629954185 met relatie id 2000045 en betrokkenheid id 2000046

When voer een bijhouding uit EGNL01C160T40.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/EGNL/expected/EGNL01C160T40.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 703544329 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
