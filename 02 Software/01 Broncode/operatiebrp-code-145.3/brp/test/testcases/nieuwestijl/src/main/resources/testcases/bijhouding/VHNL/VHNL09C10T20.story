Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative:
Een schrikkeljaar in de toekomst met de laatste dag van februari

Scenario:   Een schrikkeljaar in het verleden met de laatste dag van februari
            LT: VHNL09C10T20

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Libby.xls

When voer een bijhouding uit VHNL09C10T20.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL09C10T20.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 422531881 niet als PARTNER betrokken bij een HUWELIJK
