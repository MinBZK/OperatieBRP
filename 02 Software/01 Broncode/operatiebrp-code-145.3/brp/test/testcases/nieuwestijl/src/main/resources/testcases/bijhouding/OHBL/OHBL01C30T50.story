Meta:
@auteur                 reboe
@status                 Klaar
@sleutelwoorden         ontbindingHuwelijkInBuitenland,Foutief
@usecase                UCS-BY.HG

Narrative: R1881 ontbinding huwelijk in Buitenland, actie registratieAanvangHuwelijkGeregistreerdPartnerschap

Scenario: Ontbinding huwelijk in het buitenland.  Einde landgebied gevuld bij Land = Verzonnenplaats (landcode 9096) eindedatum 2016-10-01
          LT: OHBL01C30T50

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL-OHBL/OHBL01C30T50-Marielle.xls

Given pas laatste relatie van soort 1 aan tussen persoon 260200466 en persoon 663100418 met relatie id 3000035 en betrokkenheid id 3000036

When voer een bijhouding uit OHBL01C30T50.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/OHBL/expected/OHBL01C30T50.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 260200466 wel als PARTNER betrokken bij een HUWELIJK
