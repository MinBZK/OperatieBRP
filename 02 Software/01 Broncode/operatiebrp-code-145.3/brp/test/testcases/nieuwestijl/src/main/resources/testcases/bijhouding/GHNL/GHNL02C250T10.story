Meta:
@auteur                 tjlee
@status                 Klaar
@regels                 R2034
@usecase                UCS-BY.HG

Narrative:
R2034 Gemeente geboorte en/of Woonplaatsnaam geboorte mogen uitsluitend gevuld zijn indien de Persoon geboren is in Nederland

Scenario: R2034 Land/gebied = 0000 Persoon.Gemeente geboorte gevuld Persoon.Woonplaatsnaam geboorte leeg
          LT: GHNL02C250T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-GHNL/GHNL-Mila.xls

When voer een GBA bijhouding uit GHNL02C250T10.xml namens partij 'Migratievoorziening'

Then heeft het antwoordbericht verwerking Foutief
And is het antwoordbericht gelijk aan /testcases/bijhouding/GHNL/expected/GHNL02C250T10.xml voor expressie //brp:isc_migRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 956803593 niet als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 3528125729 uit database en vergelijk met expected GHNL02C250T10-persoon1.xml
