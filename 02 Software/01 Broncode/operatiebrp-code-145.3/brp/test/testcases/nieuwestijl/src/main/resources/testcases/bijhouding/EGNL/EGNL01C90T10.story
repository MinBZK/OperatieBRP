Meta:
@auteur                 tjlee
@status                 Klaar
@regels                 R2178
@sleutelwoorden         Foutief
@usecase                UCS-BY.HG

Narrative:
R2178 Huwelijk en geregistreerd partnerschap, beeindig Geregistreerd Partnerschap in Nederland, actie beeindigingGeregistreerdPartnerschapInNederland

Scenario: R2178 Beeindiging van een geregistreerd partnerschap. Woonplaatsnaam einde relatie verwijst niet naar een bestaand stamgegeven.
          LT: EGNL01C90T10

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL-EGNL/April.xls

Given pas laatste relatie van soort 2 aan tussen persoon 785831241 en persoon 938896969 met relatie id 2000025 en betrokkenheid id 2000026

When voer een bijhouding uit EGNL01C90T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/EGNL/expected/EGNL01C90T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 785831241 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
