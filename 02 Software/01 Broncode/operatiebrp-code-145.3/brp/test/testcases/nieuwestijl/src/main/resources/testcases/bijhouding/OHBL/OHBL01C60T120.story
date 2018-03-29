Meta:
@auteur                 reboe
@status                 Klaar
@sleutelwoorden         ontbindingHuwelijkInBuitenland,Geslaagd
@usecase                UCS-BY.HG

Narrative: R2029 ontbinding huwelijk in Buitenland, actie registratieAanvangHuwelijkGeregistreerdPartnerschap

Scenario: Ontbinding huwelijk in het buitenland. R2029 Land/gebied "Internationaal gebied" + attributen niet gevuld
          LT: OHBL01C60T120

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL-OHBL/Marielle.xls

Given pas laatste relatie van soort 1 aan tussen persoon 720757129 en persoon 428134441 met relatie id 3000044 en betrokkenheid id 3000045

When voer een bijhouding uit OHBL01C60T120.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/OHBL/expected/OHBL01C60T120.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R


