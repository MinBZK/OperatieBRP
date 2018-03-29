Meta:
@auteur                 tjlee
@status                 Klaar
@regels                 R1876
@sleutelwoorden         Geslaagd
@usecase                UCS-BY.HG

Narrative:
R1876 Huwelijk en geregistreerd partnerschap, beeindig Geregistreerd Partnerschap in Nederland, actie beeindigingGeregistreerdPartnerschapInNederland

Scenario: R1876 Beeindiging van een geregistreerd partnerschap. Reden einde GP is S.
          LT: EGNL01C160T50

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL-EGNL/EGNL01C160T50-April.xls

Given pas laatste relatie van soort 2 aan tussen persoon 114622930 en persoon 475056681 met relatie id 2000047 en betrokkenheid id 2000048

When voer een bijhouding uit EGNL01C160T50.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/EGNL/expected/EGNL01C160T50.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 114622930 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
