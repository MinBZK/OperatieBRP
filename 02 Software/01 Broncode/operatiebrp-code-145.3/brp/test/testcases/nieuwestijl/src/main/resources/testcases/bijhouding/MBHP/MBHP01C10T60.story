Meta:
@status                 Klaar
@usecase                BY.1.MP

Narrative:
Scenario 1 Hoofdscenario

Scenario: Persoon is WEL overleden EN feitdatum VOOR datum overlijden EN WEL hoofdpersoon
          LT: MBHP01C10T60



Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-MBHP/MBHP01C10T60-Kim-overleden.xls

When voer een bijhouding uit MBHP01C10T60.xml namens partij 'Gemeente BRP 1'

Then is het antwoordbericht gelijk aan /testcases/bijhouding/MBHP/expected/MBHP01C10T60.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then controleer tijdstip laatste wijziging in bijhoudingsplan voor bijgehouden personen
