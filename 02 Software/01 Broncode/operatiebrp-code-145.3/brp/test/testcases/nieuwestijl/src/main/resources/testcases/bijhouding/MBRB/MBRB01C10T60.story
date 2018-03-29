Meta:
@auteur                 fuman
@status                 Klaar
@usecase                BY.1.MR

Narrative:
Maak bijhoudingsresultaatbericht

Scenario: Bericht met Resultaat Bijhouding 'Verwerkt' (Uitgesloten gerelateerde)
          LT: MBRB01C10T60



Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-MBRB/MBRB01C10T60-Karin.xls
Given enkel initiele vulling uit bestand /LO3PL-MBRB/MBRB01C10T60-Karel.xls

When voer een bijhouding uit MBRB01C10T60.xml namens partij 'Gemeente BRP 1'

Then is het antwoordbericht gelijk aan /testcases/bijhouding/MBRB/expected/MBRB01C10T60.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R
