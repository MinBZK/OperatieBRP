Meta:
@auteur                 tjlee
@status                 Klaar
@regels                 R2040
@sleutelwoorden         Foutief
@usecase                UCS-BY.HG

Narrative:
R2040 Huwelijk en geregistreerd partnerschap, beeindig Geregistreerd Partnerschap in Nederland, actie beeindigingGeregistreerdPartnerschapInNederland

Scenario: R2040 Beeindiging van een geregistreerd partnerschap. Gemeente is niet ingevuld.
          LT: EGNL01C70T10

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL-EGNL/EGNL01C70-April.xls

Given pas laatste relatie van soort 2 aan tussen persoon 594645001 en persoon 212737065 met relatie id 2000017 en betrokkenheid id 2000018

When voer een bijhouding uit EGNL01C70T10.xml namens partij 'Gemeente BRP 1'

Then is het foutief antwoordbericht gelijk aan /testcases/bijhouding/EGNL/expected/EGNL01C70T10.txt

Then is in de database de persoon met bsn 594645001 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
