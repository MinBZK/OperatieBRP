Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative:
Een schrikkeljaar in het verleden met de laatste dag van februari

Scenario:   Een schrikkeljaar in het verleden met de laatste dag van februari
            LT: VHNL09C10T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/VHNL09C10T10.xls

When voer een bijhouding uit VHNL09C10T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL09C10T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 832503241 wel als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 5609765650 uit database en vergelijk met expected VHNL09C10T10-persoon1.xml
