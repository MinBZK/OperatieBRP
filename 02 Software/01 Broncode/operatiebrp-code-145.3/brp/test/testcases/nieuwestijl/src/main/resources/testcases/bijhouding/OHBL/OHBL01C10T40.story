Meta:
@auteur                 reboe
@status                 Klaar
@sleutelwoorden         ontbindingHuwelijkInBuitenland,Geslaagd
@usecase                UCS-BY.HG

Narrative: R2049 ontbinding huwelijk in Buitenland, actie registratieAanvangHuwelijkGeregistreerdPartnerschap

Scenario: Ontbinding huwelijk in het buitenland. Relatie.Land/gebied einde = "Onbekend" (landcode 9999) en NIET gevulde locatie einde
          LT: OHBL01C10T40

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL-OHBL/OHBL01C10T40-Marielle.xls

Given pas laatste relatie van soort 1 aan tussen persoon 583322633 en persoon 420486537 met relatie id 3000003 en betrokkenheid id 3000004

When voer een bijhouding uit OHBL01C10T40.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/OHBL/expected/OHBL01C10T40.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R


