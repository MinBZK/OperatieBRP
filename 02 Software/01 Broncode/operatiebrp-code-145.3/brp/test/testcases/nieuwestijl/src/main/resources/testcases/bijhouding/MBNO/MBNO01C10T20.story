Meta:
@auteur                 tjlee
@status                 Klaar
@usecase                BY.1.MN

Narrative:
Scenario 1 Maak bijhoudingsnotificatie

Scenario: De ontvangende partij van een bijhoudingsnotificatie is een GBA-partij waarbij Partij.Datum overgang naar BRP later is dan de systeemdatum
          LT: MBNO01C10T20

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-MBNO/MBNO01C10T20-Libby.xls

When voer een bijhouding uit MBNO01C10T20.xml namens partij 'Gemeente BRP 1'

Then is het antwoordbericht gelijk aan /testcases/bijhouding/MBNO/expected/MBNO01C10T20.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then staat er 1 notificatiebericht voor bijhouders op de queue
