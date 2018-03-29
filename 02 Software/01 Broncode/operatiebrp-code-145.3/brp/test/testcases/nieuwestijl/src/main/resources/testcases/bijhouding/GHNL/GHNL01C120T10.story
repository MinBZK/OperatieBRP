Meta:
@auteur                 fuman
@status                 Klaar
@regels                 R2098
@usecase                UCS-BY.HG

Narrative:
R2098 Bijhouding geblokkeerd wegens verhuizing

Scenario: R2098 1 blokkering door verhuizing binnnen Nederland
          LT: GHNL01C120T10

Given alle personen zijn verwijderd
Given persoon met anummer 3528125729 is geblokkeerd

Given enkel initiele vulling uit bestand /LO3PL-GHNL/GHNL-Mila.xls

When voer een GBA bijhouding uit GHNL01C120T10.xml namens partij 'Migratievoorziening'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/GHNL/expected/GHNL01C120T10.xml voor expressie //brp:isc_migRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 956803593 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 189975945 niet als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 3528125729 uit database en vergelijk met expected GHNL01C120T10-persoon1.xml
