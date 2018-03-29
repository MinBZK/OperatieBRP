Meta:
@auteur                 tjlee
@status                 Klaar
@regels                 R2045
@sleutelwoorden         Foutief
@usecase                UCS-BY.HG

Narrative:
R2045 Huwelijk en geregistreerd partnerschap, beeindig Geregistreerd Partnerschap in Nederland, actie beeindigingGeregistreerdPartnerschapInNederland

Scenario: R2045 Beeindiging van een geregistreerd partnerschap. Gemeente einde relatie is niet ingevuld.
          LT: EGNL01C80T10

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL-EGNL/April.xls

Given pas laatste relatie van soort 2 aan tussen persoon 785831241 en persoon 938896969 met relatie id 2000023 en betrokkenheid id 2000024

When voer een bijhouding uit EGNL01C80T10.xml namens partij 'Gemeente BRP 1'

Then is het foutief antwoordbericht gelijk aan /testcases/bijhouding/EGNL/expected/EGNL01C80T10.txt

Then is in de database de persoon met bsn 785831241 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
