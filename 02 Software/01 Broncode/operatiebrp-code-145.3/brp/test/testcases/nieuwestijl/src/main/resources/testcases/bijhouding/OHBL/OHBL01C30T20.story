Meta:
@auteur                 reboe
@status                 Klaar
@sleutelwoorden         ontbindingHuwelijkInBuitenland,Foutief
@usecase                UCS-BY.HG

Narrative: R1881 ontbinding huwelijk in Buitenland, actie registratieAanvangHuwelijkGeregistreerdPartnerschap

Scenario: Ontbinding huwelijk in het buitenland.   Einde landgebied gevuld bij Land = Verzonnenplaats (landcode 9096) eindedatum 2016-01-31
          LT: OHBL01C30T20

Given alle personen zijn verwijderd

Given enkel initiele vulling uit bestand /LO3PL-OHBL/OHBL01C30T20-Marielle.xls

Given pas laatste relatie van soort 1 aan tussen persoon 406631049 en persoon 133972653 met relatie id 3000007 en betrokkenheid id 3000008

When voer een bijhouding uit OHBL01C30T20.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding/OHBL/expected/OHBL01C30T20.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 406631049 wel als PARTNER betrokken bij een HUWELIJK
