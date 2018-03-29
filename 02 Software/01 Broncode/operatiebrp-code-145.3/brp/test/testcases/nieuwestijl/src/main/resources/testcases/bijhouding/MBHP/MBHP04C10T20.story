Meta:
@status                 Klaar
@usecase                BY.1.MP

Narrative: Scenario 4 Bijhoudingssituatie opgeschort

Scenario: Persoon is overleden EN feitdatum ligt OP datum overlijden EN geen hoofdpersoon
          LT: MBHP04C10T20



Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-MBHP/MBHP04C10T20-Sarah_kind.xls
Given enkel initiele vulling uit bestand /LO3PL-MBHP/MBHP04C10T20-Kind.xls

Given Pseudo-persoon 3049564193 is vervangen door ingeschreven persoon 6736901921

When voer een bijhouding uit MBHP04C10T20.xml namens partij 'Gemeente BRP 1'

Then is het antwoordbericht gelijk aan /testcases/bijhouding/MBHP/expected/MBHP04C10T20.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then controleer tijdstip laatste wijziging in bijhoudingsplan voor bijgehouden personen
