Meta:
@status                 Klaar
@regels                 R1845
@usecase                UCS-BY.HG

Narrative:
R1845 Huwelijk en geregistreerd partnerschap, beeindig Geregistreerd Partnerschap in Nederland, actie beeindigingGeregistreerdPartnerschapInNederland

Scenario: R1845 Beeindiging van een geregistreerd partnerschap. Een objectsleutel verwijst niet naar een gerelateerd object in BRP.
          LT: EGNL01C140T10

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL-EGNL/EGNL01C140T10-April.xls

Given pas laatste relatie van soort 2 aan tussen persoon 714801033 en persoon 986557961 met relatie id 2000051 en betrokkenheid id 2000052

When voer een bijhouding uit EGNL01C140T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/EGNL/expected/EGNL01C140T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 986557961 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
