Meta:
@auteur                 tjlee
@status                 Klaar
@usecase                BY.1.MR

Narrative:
Maak bijhoudingsresultaatbericht

Scenario: 1 Testgeval die alle bijhoudinsituaties raakt (alle situaties in 1 bijhoudingsplan)
          LT: MBRB01C30T10



Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-MBRB/MBRB01C30T10-Marie_kinderen.xls
Given enkel initiele vulling uit bestand /LO3PL-MBRB/MBRB01C30T10-KindB.xls
Given enkel initiele vulling uit bestand /LO3PL-MBRB/MBRB01C30T10-KindC.xls
Given enkel initiele vulling uit bestand /LO3PL-MBRB/MBRB01C30T10-KindD.xls
Given enkel initiele vulling uit bestand /LO3PL-MBRB/MBRB01C30T10-KindE.xls
Given enkel initiele vulling uit bestand /LO3PL-MBRB/MBRB01C30T10-KindF.xls

Given Pseudo-persoon 2727971090 is vervangen door ingeschreven persoon 7658528530
Given Pseudo-persoon 4694597650 is vervangen door ingeschreven persoon 6352349458
Given Pseudo-persoon 1314509618 is vervangen door ingeschreven persoon 2407924370
Given Pseudo-persoon 4890586130 is vervangen door ingeschreven persoon 1705385234
Given Pseudo-persoon 7294540562 is vervangen door ingeschreven persoon 5456964370


When voer een bijhouding uit MBRB01C30T10.xml namens partij 'Gemeente BRP 1'

Then is het antwoordbericht gelijk aan /testcases/bijhouding/MBRB/expected/MBRB01C30T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R
