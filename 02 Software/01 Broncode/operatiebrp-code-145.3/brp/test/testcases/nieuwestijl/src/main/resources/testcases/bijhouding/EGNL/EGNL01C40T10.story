Meta:
@auteur                 tjlee
@status                 Klaar
@regels                 R1880
@sleutelwoorden         Foutief
@usecase                UCS-BY.HG

Narrative:
R1880 Huwelijk en geregistreerd partnerschap, beeindig Geregistreerd Partnerschap in Nederland, actie beeindigingGeregistreerdPartnerschapInNederland

Scenario: R1880 Beeindiging van een geregistreerd partnerschap. Gemeente einde is niet geldig op datum einde.
          LT: EGNL01C40T10

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL-EGNL/EGNL01C40T10-April.xls

Given pas laatste relatie van soort 2 aan tussen persoon 298461481 en persoon 430618633 met relatie id 2000007 en betrokkenheid id 2000008

When voer een bijhouding uit EGNL01C40T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/EGNL/expected/EGNL01C40T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 298461481 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
