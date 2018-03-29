Meta:
@auteur                 reboe
@status                 Klaar
@sleutelwoorden         ontbindingHuwelijkInBuitenland,Geslaagd
@usecase                UCS-BY.HG

Narrative: R2020 ontbinding huwelijk in Buitenland, actie registratieAanvangHuwelijkGeregistreerdPartnerschap

Scenario: Ontbinding huwelijk in het buitenland. R2020 Land/gebied "Spanje" (landcode 6037) + attributen  Relatie.Buitenlandse plaats einde niet gevuld
          LT: OHBL01C70T40

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL-OHBL/OHBL01C70T40-Marielle.xls

Given pas laatste relatie van soort 1 aan tussen persoon 302535273 en persoon 181566305 met relatie id 3000029 en betrokkenheid id 3000030

When voer een bijhouding uit OHBL01C70T40.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/OHBL/expected/OHBL01C70T40.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R


