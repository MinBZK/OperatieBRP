Meta:
@auteur                 reboe
@status                 Klaar
@sleutelwoorden         ontbindingHuwelijkInBuitenland,Geslaagd
@usecase                UCS-BY.HG

Narrative: R1881 ontbinding huwelijk in Buitenland, actie registratieAanvangHuwelijkGeregistreerdPartnerschap

Scenario: Ontbinding huwelijk in het buitenland.  Einde landgebied gevuld bij Land = Verzonnenplaats (landcode 9096) eindedatum 2016-02-02
          LT: OHBL01C30T40

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL-OHBL/OHBL01C30T40-Marielle.xls

Given pas laatste relatie van soort 1 aan tussen persoon 236317657 en persoon 716587209 met relatie id 3000033 en betrokkenheid id 3000034

When voer een bijhouding uit OHBL01C30T40.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/OHBL/expected/OHBL01C30T40.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 236317657 niet als PARTNER betrokken bij een HUWELIJK
