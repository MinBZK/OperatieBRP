Meta:
@auteur                 tjlee
@status                 Klaar
@regels                 R1861
@usecase                UCS-BY.HG

Narrative:
R1861 GBA Voltrekking Huwelijk mag alleen door betrokken gemeente worden geregistreerd

Scenario: R1861 Huwelijk wordt niet door een betrokken gemeente geregistreerd
          LT: GHNL01C10T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-GHNL/GHNL-Mila.xls
Given enkel initiele vulling uit bestand /LO3PL-GHNL/GHNL-Tim.xls

When voer een GBA bijhouding uit GHNL01C10T10.xml namens partij 'Migratievoorziening'

Then heeft het antwoordbericht verwerking Foutief
And is het antwoordbericht gelijk aan /testcases/bijhouding/GHNL/expected/GHNL01C10T10.xml voor expressie //brp:isc_migRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 956803593 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 979562697 niet als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 3528125729 uit database en vergelijk met expected GHNL01C10T10-persoon1.xml
Then lees persoon met anummer 7403085089 uit database en vergelijk met expected GHNL01C10T10-persoon2.xml
