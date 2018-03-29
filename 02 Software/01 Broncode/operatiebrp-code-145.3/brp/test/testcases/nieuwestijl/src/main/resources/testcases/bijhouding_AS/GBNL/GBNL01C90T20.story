Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R1721 NOUWKIG verplicht als daarvoor kandidaat is

Scenario: NOUWKIG (overleden partner van OUWKIG  306 dagen geleden) aanwezig als kandidaat
          LT: GBNL01C90T20

Given alle personen zijn verwijderd

!-- De ouders
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL01C90T20-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL01C90T20-002.xls

!-- Huwelijk
When voer een bijhouding uit GBNL01C90T20a.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

Given pas laatste relatie van soort 1 aan tussen persoon 551498729 en persoon 406396905 met relatie id 30020001 en betrokkenheid id 30020001

!-- Ontbinding wegens Overlijden
When voer een bijhouding uit GBNL01C90T20b.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

!-- Geboorte
When voer een bijhouding uit GBNL01C90T20c.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief