Meta:
@auteur                 tjlee
@status                 Klaar
@regels                 R1625
@usecase                UCS-BY.HG

Narrative:
R1625 De groepen Geslachtsaanduiding, Geboorte en 'Samengestelde naam' zijn verplicht voor de Soort Persoon Niet-Ingeschrevene of Onbekend

Scenario: R1625 Soort N en Geslachtsaanduiding niet ingevuld
          LT: GHNL01C40T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-GHNL/GHNL-Mila.xls

When voer een GBA bijhouding uit GHNL01C40T10.xml namens partij 'Migratievoorziening'

Then heeft het antwoordbericht verwerking Foutief
And is het antwoordbericht gelijk aan /testcases/bijhouding/GHNL/expected/GHNL01C40T10.xml voor expressie //brp:isc_migRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 956803593 niet als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 3528125729 uit database en vergelijk met expected GHNL01C40T10-persoon1.xml
