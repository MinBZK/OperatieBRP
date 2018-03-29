Meta:
@auteur                 fuman
@status                 Klaar
@regels                 R2235
@usecase                UCS-BY.HG

Narrative:
R2235 Partij in Document moet verwijzen naar bestaande Partij

Scenario: R2235 De partij verwijst niet naar een bestaande partij, partij 999901
          LT: GHNL02C420T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-GHNL/GHNL02C420T10-Anja.xls
Given enkel initiele vulling uit bestand /LO3PL-GHNL/GHNL02C420T10-Peter.xls

When voer een GBA bijhouding uit GHNL02C420T10.xml namens partij 'Migratievoorziening'

Then heeft het antwoordbericht verwerking Foutief
And is het antwoordbericht gelijk aan /testcases/bijhouding/GHNL/expected/GHNL02C420T10.xml voor expressie //brp:isc_migRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 587019657 niet als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 2416471314 uit database en vergelijk met expected GHNL02C420T10-persoon1.xml
Then lees persoon met anummer 8389649170 uit database en vergelijk met expected GHNL02C420T10-persoon2.xml
