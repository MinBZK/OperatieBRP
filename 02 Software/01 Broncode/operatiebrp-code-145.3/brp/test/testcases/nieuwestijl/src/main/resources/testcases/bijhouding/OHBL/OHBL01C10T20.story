Meta:
@auteur                 reboe
@status                 Klaar
@sleutelwoorden         ontbindingHuwelijkInBuitenland,Geslaagd
@usecase                UCS-BY.HG

Narrative: R2049 ontbinding huwelijk in Buitenland, actie registratieAanvangHuwelijkGeregistreerdPartnerschap

Scenario: Ontbinding huwelijk in het buitenland.  Relatie.Land/gebied einde = "Internationaal gebied" (landcode 9999) en gevulde locatie einde
          LT: OHBL01C10T20

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL-OHBL/OHBL01C10T20-Marielle.xls

Given pas laatste relatie van soort 1 aan tussen persoon 151167813 en persoon 270858489 met relatie id 2000055 en betrokkenheid id 2000056

When voer een bijhouding uit OHBL01C10T20.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/OHBL/expected/OHBL01C10T20.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 151167813 niet als PARTNER betrokken bij een HUWELIJK
