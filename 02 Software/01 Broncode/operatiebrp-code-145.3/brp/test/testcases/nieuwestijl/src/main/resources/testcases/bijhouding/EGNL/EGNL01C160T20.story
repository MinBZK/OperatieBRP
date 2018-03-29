Meta:
@auteur                 tjlee
@status                 Klaar
@regels                 R1876
@sleutelwoorden         Geslaagd
@usecase                UCS-BY.HG

Narrative:
R1876 Huwelijk en geregistreerd partnerschap, beeindig Geregistreerd Partnerschap in Nederland, actie beeindigingGeregistreerdPartnerschapInNederland

Scenario: R1876 Beeindiging van een geregistreerd partnerschap. Reden einde GP is A.
          LT: EGNL01C160T20

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL-EGNL/EGNL01C160T20-April.xls

Given pas laatste relatie van soort 2 aan tussen persoon 709774217 en persoon 130898843 met relatie id 2000041 en betrokkenheid id 2000042

When voer een bijhouding uit EGNL01C160T20.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/EGNL/expected/EGNL01C160T20.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 709774217 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP

Then lees persoon met anummer 5392867090 uit database en vergelijk met expected EGNL01C160T20-persoon1.xml
