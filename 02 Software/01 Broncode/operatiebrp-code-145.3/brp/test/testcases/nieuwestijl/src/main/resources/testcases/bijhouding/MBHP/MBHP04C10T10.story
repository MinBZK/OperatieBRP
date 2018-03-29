Meta:
@status                 Klaar
@usecase                BY.1.MP

Narrative:
Scenario 4 Bijhoudingssituatie opgeschort

Scenario: Persoon is overleden EN feitdatum ligt NA datum overlijden EN geen hoofdpersoon
          LT: MBHP04C10T10



Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-MBHP/MBHP04C10T10-Karin_kind.xls
Given enkel initiele vulling uit bestand /LO3PL-MBHP/MBHP04C10T10-Kind.xls

Given Pseudo-persoon 4318183457 is vervangen door ingeschreven persoon 6848125217

When voer een bijhouding uit MBHP04C10T10.xml namens partij 'Gemeente BRP 1'

Then is het antwoordbericht gelijk aan /testcases/bijhouding/MBHP/expected/MBHP04C10T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then controleer tijdstip laatste wijziging in bijhoudingsplan voor bijgehouden personen
