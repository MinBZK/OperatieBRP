Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative: Nietigverklaring huwelijk in Nederland

Scenario:   Nietigverklaring van een asymmetrisch BRP huwelijk Ingeschrevene Pseudo
            LT: NHNL02C10T30

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-NHNL/NHNL02C10T30-001.xls

When voer een bijhouding uit NHNL02C10T30a.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

Given pas laatste relatie van soort 1 aan tussen persoon 319306057 en persoon 793558025 met relatie id 43000103 en betrokkenheid id 43000103

Then is in de database de persoon met bsn 319306057 wel als PARTNER betrokken bij een HUWELIJK

When voer een bijhouding uit NHNL02C10T30b.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/NHNL/expected/NHNL02C10T30.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 319306057 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 793558025 niet als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 3042506913 uit database en vergelijk met expected NHNL02C10T30-persoon1.xml











