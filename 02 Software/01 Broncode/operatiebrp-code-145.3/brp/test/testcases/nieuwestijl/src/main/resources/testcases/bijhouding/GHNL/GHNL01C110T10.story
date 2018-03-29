Meta:
@auteur                 fuman
@status                 Klaar
@regels                 R2181
@usecase                UCS-BY.HG

Narrative:
R2181 Aanwezigheid Object sleutel bepaalt de Soort Persoon als Ingeschrevene

Scenario: R2181 Objectsleutel is ingevuld en Persoon.Soort is P
          LT: GHNL01C110T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-GHNL/GHNL-Mila.xls
Given enkel initiele vulling uit bestand /LO3PL-GHNL/GHNL-Tim.xls

When voer een GBA bijhouding uit GHNL01C110T10.xml namens partij 'Migratievoorziening'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/GHNL/expected/GHNL01C110T10.xml voor expressie //brp:isc_migRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 956803593 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 189975945 niet als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 3528125729 uit database en vergelijk met expected GHNL01C110T10-persoon1.xml
