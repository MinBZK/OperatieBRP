Meta:
@auteur                 reboe
@status                 Klaar
@sleutelwoorden         ontbindingHuwelijkInBuitenland,Geslaagd
@usecase                UCS-BY.HG

Narrative: R1877 ontbinding huwelijk in Buitenland, actie registratieAanvangHuwelijkGeregistreerdPartnerschap

Scenario: Ontbinding huwelijk in het buitenland. Relatie.Land/gebied einde = (landcode 6037)
          LT: OHBL01C40T20

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL-OHBL/OHBL01C40T20-Marielle.xls

Given pas laatste relatie van soort 1 aan tussen persoon 296291353 en persoon 405786281 met relatie id 3000011 en betrokkenheid id 3000012

When voer een bijhouding uit OHBL01C40T20.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/OHBL/expected/OHBL01C40T20.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R


