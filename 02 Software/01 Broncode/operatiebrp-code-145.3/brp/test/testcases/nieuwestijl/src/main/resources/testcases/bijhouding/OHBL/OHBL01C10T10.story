Meta:
@auteur                 reboe
@status                 Klaar
@sleutelwoorden         ontbindingHuwelijkInBuitenland,Geslaagd
@usecase                UCS-BY.HG

Narrative: R2049 ontbinding huwelijk in Buitenland, actie registratieAanvangHuwelijkGeregistreerdPartnerschap

Scenario: Ontbinding huwelijk in het buitenland. Relatie.Land/gebied einde = "Onbekend" (landcode 0000) en gevulde locatie einde
          LT: OHBL01C10T10

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL-OHBL/OHBL01C10T10-Marielle.xls

Given pas laatste relatie van soort 1 aan tussen persoon 672613001 en persoon 157295321 met relatie id 2000053 en betrokkenheid id 2000054

When voer een bijhouding uit OHBL01C10T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/OHBL/expected/OHBL01C10T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 672613001 niet als PARTNER betrokken bij een HUWELIJK
