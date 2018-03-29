Meta:
@auteur                 fuman
@status                 Klaar
@usecase                BY.1.MP

Narrative:
Scenario 1 Hoofdscenario

Scenario: Persoon kan maar 1 keer betrokken zijn huwelijk zonder naamswijziging
          LT: MBHP01C10T70



Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-MBHP/MBHP01C10T70-Karin.xls
Given enkel initiele vulling uit bestand /LO3PL-MBHP/MBHP01C10T70-Karel.xls
Given enkel initiele vulling uit bestand /LO3PL-MBHP/MBHP01C10T70-Tom.xls

Given Pseudo-persoon 5932312865 is vervangen door ingeschreven persoon 6827429650
Given Pseudo-persoon 7531041042 is vervangen door ingeschreven persoon 6827429650

When voer een bijhouding uit MBHP01C10T70.xml namens partij 'Gemeente BRP 1'

Then is het antwoordbericht gelijk aan /testcases/bijhouding/MBHP/expected/MBHP01C10T70.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then controleer tijdstip laatste wijziging in bijhoudingsplan voor bijgehouden personen
