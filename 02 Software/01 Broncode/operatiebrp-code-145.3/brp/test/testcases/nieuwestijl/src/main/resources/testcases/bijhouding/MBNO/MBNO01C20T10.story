Meta:
@auteur                 tjlee
@status                 Klaar
@usecase                BY.1.MN

Narrative:
Scenario 1 Maak bijhoudingsnotificatie

Scenario: Maak bijhoudingsnotificaties waarbij er 3 personen voorkomen
          LT: MBNO01C20T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-MBNO/MBNO01C20T10-Nicolette.xls
Given enkel initiele vulling uit bestand /LO3PL-MBNO/MBNO01C20T10-Nico.xls
Given enkel initiele vulling uit bestand /LO3PL-MBNO/MBNO01C20T10-Ed.xls

Given Pseudo-persoon 9349479186 is vervangen door ingeschreven persoon 2830345874
Given Pseudo-persoon 6284593426 is vervangen door ingeschreven persoon 2830345874

When voer een bijhouding uit MBNO01C20T10.xml namens partij 'Gemeente BRP 1'

Then is het antwoordbericht gelijk aan /testcases/bijhouding/MBNO/expected/MBNO01C20T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then staan er 3 notificatieberichten voor bijhouders op de queue
