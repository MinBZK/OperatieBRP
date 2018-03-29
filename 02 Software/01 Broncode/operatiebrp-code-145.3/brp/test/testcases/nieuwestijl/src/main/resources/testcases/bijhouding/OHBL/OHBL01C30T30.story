Meta:
@auteur                 reboe
@status                 Klaar
@sleutelwoorden         ontbindingHuwelijkInBuitenland,Geslaagd
@usecase                UCS-BY.HG

Narrative: R1881 ontbinding huwelijk in Buitenland, actie registratieAanvangHuwelijkGeregistreerdPartnerschap

Scenario: Ontbinding huwelijk in het buitenland.  Einde landgebied gevuld bij Land = Verzonnenplaats (landcode 9096) eindedatum 2016-02-01
          LT: OHBL01C30T30

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL-OHBL/OHBL01C30T30-Marielle.xls

Given pas laatste relatie van soort 1 aan tussen persoon 864954313 en persoon 610097577 met relatie id 3000031 en betrokkenheid id 3000032

When voer een bijhouding uit OHBL01C30T30.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/OHBL/expected/OHBL01C30T30.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 864954313 niet als PARTNER betrokken bij een HUWELIJK
