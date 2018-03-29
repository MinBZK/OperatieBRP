Meta:
@status                 Klaar
@sleutelwoorden         ontbindingHuwelijkInBuitenland,Geslaagd
@usecase                UCS-BY.HG

Narrative: R2020 ontbinding huwelijk in Buitenland, actie registratieAanvangHuwelijkGeregistreerdPartnerschap

Scenario: Ontbinding huwelijk in het buitenland. R2020 Land/gebied "Spanje" (landcode 6037) + attributen  Relatie.Buitenlandse regio einde niet  gevuld
          LT: OHBL01C70T30

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL-OHBL/OHBL01C70T30-Marielle.xls

Given pas laatste relatie van soort 1 aan tussen persoon 330380569 en persoon 345908313 met relatie id 3000027 en betrokkenheid id 3000028

When voer een bijhouding uit OHBL01C70T30.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/OHBL/expected/OHBL01C70T30.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R


