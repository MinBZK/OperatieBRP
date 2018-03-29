Meta:
@auteur                 tjlee
@status                 Klaar
@regels                 R1571
@sleutelwoorden         Foutief
@usecase                UCS-BY.HG

Narrative: R1571 Einde geregistreerd partnerschap in het buitenland. Er mag slechts 1 voorkomen van geslachtsnaamcomponent aanwezig zijn

Scenario: R1571 Twee voorkomens van geslachtsnaamcomponent
          LT: EGBL01C10T10

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL-EGBL/Nienke.xls

Given pas laatste relatie van soort 2 aan tussen persoon 158686421 en persoon 410082089 met relatie id 2000053 en betrokkenheid id 2000054

When voer een bijhouding uit EGBL01C10T10.xml namens partij 'Gemeente BRP 1'

Then is het foutief antwoordbericht gelijk aan /testcases/bijhouding/EGBL/expected/EGBL01C10T10.txt

Then is in de database de persoon met bsn 158686421 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
