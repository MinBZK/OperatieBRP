Meta:
@status                 Klaar
@usecase                BY.1.MP

Narrative:
Scenario 1 Hoofdscenario

Scenario: BZVU-personen WEL gevuld EN persoon komt er WEL in voor
          LT: MBHP01C10T20



Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-MBHP/MBHP01C10T20-Martina.xls

When voer een bijhouding uit MBHP01C10T20.xml namens partij 'Gemeente BRP 1'

Then is het antwoordbericht gelijk aan /testcases/bijhouding/MBHP/expected/MBHP01C10T20.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then controleer tijdstip laatste wijziging in bijhoudingsplan voor bijgehouden personen
