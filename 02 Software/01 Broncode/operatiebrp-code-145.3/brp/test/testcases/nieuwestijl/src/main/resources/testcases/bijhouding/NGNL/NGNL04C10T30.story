Meta:
@status                 Klaar
@usecase                UCS-BY.HG

Narrative: Nietigverklaring geregistreerd partnerschap in Nederland

Scenario:   Nietigverklaring van een asymmetrisch BRP geregistreerd partnerschap Ingeschrevene Pseudo
            LT: NGNL04C10T30

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-NGNL/NGNL04C10T30-001.xls

When voer een bijhouding uit NGNL04C10T30a.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

Given pas laatste relatie van soort 2 aan tussen persoon 264099977 en persoon 397828329 met relatie id 43000113 en betrokkenheid id 43000113

Then is in de database de persoon met bsn 264099977 wel als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP

When voer een bijhouding uit NGNL04C10T30b.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/NGNL/expected/NGNL04C10T30.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 264099977 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP
Then is in de database de persoon met bsn 397828329 niet als PARTNER betrokken bij een GEREGISTREERD_PARTNERSCHAP

Then lees persoon met anummer 3042506913 uit database en vergelijk met expected NGNL04C10T30-persoon1.xml











