Meta:
@auteur                 tjlee
@status                 Klaar
@sleutelwoorden         Geslaagd
@regels                 R1865
@usecase                UCS-BY.HG

Narrative:
R1865 Minimumleeftijd van de partners bij GBA voltrekking Huwelijk is 18 jaar

Scenario: R1865 1 partner(LO3) is jonger dan 18 jaar
          LT: GHNL02C150T10



Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-GHNL/GHNL-jonger-18.xls

When voer een GBA bijhouding uit GHNL02C150T10.xml namens partij 'Migratievoorziening'

Then heeft het antwoordbericht verwerking Geslaagd
And is het antwoordbericht gelijk aan /testcases/bijhouding/GHNL/expected/GHNL02C150T10.xml voor expressie //brp:isc_migRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 701415241 wel als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 8496047890 uit database en vergelijk met expected GHNL02C150T10-persoon1.xml
