Meta:
@auteur                 tjlee
@status                 Klaar
@regels                 R2158
@sleutelwoorden         voltrekkingHuwelijkInNederland,registratieNaamgebruik,TjieWah,VHNL03C70T10, Geslaagd
@usecase                UCS-BY.HG

Narrative:
R2158 Huwelijk en geregistreerd partnerschap, voltrekking huwelijk in Nederland, actie registratieAanvangHuwelijkGeregistreerdPartnerschap, registratieNaamgebruik

Scenario: R2158 Persoon.Predicaat naamgebruik staat niet als stamtabel in predicaat.
          LT: VHNL03C70T10

Gemeente BRP 1

Given bijhoudingsverzoek voor partij 'Gemeente Tiel'

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL/VHNL03C70-sandy.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL03C70-danny.xls

When voer een bijhouding uit VHNL03C70T10.xml namens partij 'Gemeente Tiel'

Then is het foutief antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL03C70T10.txt

Then is in de database de persoon met bsn 156960849 niet als PARTNER betrokken bij een HUWELIJK
