Meta:
@auteur                 tjlee
@status                 Klaar
@regels                 R2169
@sleutelwoorden         Foutief
@usecase                UCS-BY.HG

Narrative: R2169 Einde geregistreerd partnerschap in het buitenland. Namenreeks in Samengestelde naam is verplicht

Scenario: R2169 Namenreeks is niet aanwezig in Samengestelde naam
          LT: EGBL01C60T10

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL-EGBL/Nienke.xls

Given pas laatste relatie van soort 2 aan tussen persoon 158686421 en persoon 410082089 met relatie id 2000063 en betrokkenheid id 2000064

When voer een bijhouding uit EGBL01C60T10.xml namens partij 'Gemeente BRP 1'

Then is het foutief antwoordbericht gelijk aan /testcases/bijhouding/EGBL/expected/EGBL01C60T10.txt

Then is in de database de persoon met bsn 158686421 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
