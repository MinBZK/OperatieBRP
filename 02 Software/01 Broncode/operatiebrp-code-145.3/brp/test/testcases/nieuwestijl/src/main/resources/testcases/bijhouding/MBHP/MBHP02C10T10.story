Meta:
@status                 Klaar
@usecase                BY.1.MP

Narrative:
Pad (0-16) Het controleren BZVU-personen leidt tot een foutmelding/NOK

Scenario:   Controleer BZVU-personen leidt tot foutmelding/NOK
            LT: MBHP02C10T10



Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-MBHP/MBHP02C10T10-Libby.xls
Given enkel initiele vulling uit bestand /LO3PL-MBHP/MBHP02C10T10-Jerry.xls

When voer een bijhouding uit MBHP02C10T10.xml namens partij 'Gemeente BRP 1'

Then is het antwoordbericht gelijk aan /testcases/bijhouding/MBHP/expected/MBHP02C10T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then controleer tijdstip laatste wijziging in bijhoudingsplan voor bijgehouden personen
