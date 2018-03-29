Meta:
@auteur                 tjlee
@status                 Klaar
@sleutelwoorden         ontbindingHuwelijkInBuitenland,Geslaagd
@usecase                UCS-BY.HG

Narrative: ontbinding huwelijk in Buitenland, actie registratieAanvangHuwelijkGeregistreerdPartnerschap

Scenario: Ontbinding huwelijk in het buitenland. Verwerk bijhoudingsvoorstel omschrijving locatie einde.
          LT: OHBL02C10T20

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL-OHBL/OHBL02C10T20-Marielle.xls

Given pas laatste relatie van soort 1 aan tussen persoon 600100777 en persoon 582986473 met relatie id 3000033 en betrokkenheid id 3000034

When voer een bijhouding uit OHBL02C10T20.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/OHBL/expected/OHBL02C10T20.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 600100777 niet als PARTNER betrokken bij een HUWELIJK

Then lees persoon met anummer 4758509074 uit database en vergelijk met expected OHBL02C10T20-persoon1.xml
