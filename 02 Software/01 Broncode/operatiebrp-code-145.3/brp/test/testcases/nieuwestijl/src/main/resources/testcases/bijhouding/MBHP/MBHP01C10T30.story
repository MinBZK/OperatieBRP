Meta:
@status                 Klaar
@usecase                BY.1.MP

Narrative:
Scenario 1 Hoofdscenario

Scenario: Persoon is WEL overleden EN feitdatum ligt VOOR datum overlijden EN GEEN hoofdpersoon
          LT: MBHP01C10T30



Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-MBHP/MBHP01C10T30-Cindy_kind.xls
Given enkel initiele vulling uit bestand /LO3PL-MBHP/MBHP01C10T30-Kind.xls

Given Pseudo-persoon 7876832545 is vervangen door ingeschreven persoon 1527323073

When voer een bijhouding uit MBHP01C10T30.xml namens partij 'Gemeente BRP 1'

Then is het antwoordbericht gelijk aan /testcases/bijhouding/MBHP/expected/MBHP01C10T30.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then controleer tijdstip laatste wijziging in bijhoudingsplan voor bijgehouden personen
