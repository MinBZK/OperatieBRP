Meta:
@auteur                 tjlee
@status                 Klaar
@regels                 R2169
@sleutelwoorden         Foutief
@usecase                UCS-BY.HG

Narrative:
R2169 Huwelijk en geregistreerd partnerschap, beeindig Geregistreerd Partnerschap in Nederland, actie beeindigingGeregistreerdPartnerschapInNederland

Scenario: R2169 Beeindiging van een geregistreerd partnerschap. Namenreeks in Samengestelde naam is niet aanwezig.
          LT: EGNL01C120T10

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL-EGNL/April.xls

Given pas laatste relatie van soort 2 aan tussen persoon 785831241 en persoon 938896969 met relatie id 2000031 en betrokkenheid id 2000032

When voer een bijhouding uit EGNL01C120T10.xml namens partij 'Gemeente BRP 1'

Then is het foutief antwoordbericht gelijk aan /testcases/bijhouding/EGNL/expected/EGNL01C120T10.txt

Then is in de database de persoon met bsn 785831241 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
