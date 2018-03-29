Meta:
@auteur                 tjlee
@status                 Klaar
@regels                 R1572
@sleutelwoorden         Geslaagd
@usecase                BY.1.MR

Narrative:
Maak bijhoudingsresultaatbericht

Scenario: Melding soortNaam Waarschuwing. R1572 Ouder en wijzigen Geslachtsnaamcomponent.stam en Ouder.Ouderschap.DAG overlapt met Geslachtsnaamcomponent.Standaard.DAG
          LT: MBRB01C20T10



Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-MBRB/MBRB01C20T10-sandy.xls
Given enkel initiele vulling uit bestand /LO3PL-MBRB/MBRB01C20T10-danny.xls

When voer een bijhouding uit MBRB01C20T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
And is het antwoordbericht gelijk aan /testcases/bijhouding/MBRB/expected/MBRB01C20T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

