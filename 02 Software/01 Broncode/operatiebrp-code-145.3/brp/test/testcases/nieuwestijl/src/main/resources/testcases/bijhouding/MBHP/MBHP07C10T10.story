Meta:
@auteur                 tjlee
@status                 Klaar
@usecase                BY.1.MP

Narrative:
Scenario 1 Controleer automatische fiat instelling

Scenario: Automatische fiat instelling = Nee (Handmatig)
          LT: MBHP07C10T10



Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-MBHP/MBHP07C10T10-Libby.xls

When voer een bijhouding uit MBHP07C10T10.xml namens partij 'Gemeente BRP 1'

Then is het antwoordbericht gelijk aan /testcases/bijhouding/MBHP/expected/MBHP07C10T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then controleer tijdstip laatste wijziging in bijhoudingsplan voor bijgehouden personen
