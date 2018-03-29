Meta:
@auteur                 jozon
@status                 Klaar
@usecase                BY.1.MP

Narrative:
Scenario 3 Testgeval 1

Scenario: 2 hoofdpersonen in AH, 1 hoofdpersoon in BZVU
          LT: MBHP03C10T10



Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-MBHP/MBHP03C10T10-Karin.xls
Given enkel initiele vulling uit bestand /LO3PL-MBHP/MBHP03C10T10-Karel.xls

When voer een bijhouding uit MBHP03C10T10.xml namens partij 'Gemeente BRP 1'

Then is het antwoordbericht gelijk aan /testcases/bijhouding/MBHP/expected/MBHP03C10T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then staat er 2 notificatiebericht voor bijhouders op de queue

Then controleer tijdstip laatste wijziging in bijhoudingsplan voor bijgehouden personen
