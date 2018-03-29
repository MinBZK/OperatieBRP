Meta:
@auteur                 tjlee
@status                 Klaar
@sleutelwoorden         Geslaagd
@regels                 R2162
@usecase                UCS-BY.HG

Narrative:
R1861 Persoon.Predicaat naamgebruik heeft een waarde. Persoon.Adellijke titel naamgebruik heeft geen waarde

Scenario: R2162 Persoon.Predicaat naamgebruik en de Persoon.Adellijke titel naamgebruik hebben beiden een waarde
          LT: VHNL02C710T20



Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL/VHNL02C710T20-Libby.xls
Given enkel initiele vulling uit bestand /LO3PL/VHNL02C710T20-Piet.xls

When voer een bijhouding uit VHNL02C710T20.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL02C710T20.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 690020041 wel als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 373230217 wel als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 8240349473 uit database en vergelijk met expected VHNL02C710T20-persoon1.xml
Then lees persoon met anummer 9543058721 uit database en vergelijk met expected VHNL02C710T20-persoon2.xml
