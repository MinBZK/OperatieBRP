Meta:
@auteur                 reboe
@status                 Klaar
@sleutelwoorden         ontbindingHuwelijkInBuitenland,Geslaagd
@usecase                UCS-BY.HG

Narrative: R2049 ontbinding huwelijk in Buitenland, actie registratieAanvangHuwelijkGeregistreerdPartnerschap

Scenario: Ontbinding huwelijk in het buitenland. Relatie.Land/gebied einde = (landcode 6037) en gevulde locatie einde
          LT: OHBL01C10T50

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL-OHBL/OHBL01C10T50-Marielle.xls

Given pas laatste relatie van soort 1 aan tussen persoon 701549257 en persoon 932933129 met relatie id 3000005 en betrokkenheid id 3000006

When voer een bijhouding uit OHBL01C10T50.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/OHBL/expected/OHBL01C10T50.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R


