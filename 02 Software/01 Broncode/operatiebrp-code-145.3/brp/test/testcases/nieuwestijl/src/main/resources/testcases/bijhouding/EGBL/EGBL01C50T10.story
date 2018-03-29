Meta:
@status                 Klaar
@regels                 R2167
@sleutelwoorden         Foutief
@usecase                UCS-BY.HG

Narrative: R2167 Einde geregistreerd partnerschap in het buitenland. Naamgebruik afgeleid in Naamgebruik is verplicht

Scenario: R2167 Naamgebruik afgeleid is niet aanwezig in Naamgebruik
          LT: EGBL01C50T10

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL-EGBL/Nienke.xls

Given pas laatste relatie van soort 2 aan tussen persoon 158686421 en persoon 410082089 met relatie id 2000061 en betrokkenheid id 2000062

When voer een bijhouding uit EGBL01C50T10.xml namens partij 'Gemeente BRP 1'

Then is het foutief antwoordbericht gelijk aan /testcases/bijhouding/EGBL/expected/EGBL01C50T10.txt

Then is in de database de persoon met bsn 158686421 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
