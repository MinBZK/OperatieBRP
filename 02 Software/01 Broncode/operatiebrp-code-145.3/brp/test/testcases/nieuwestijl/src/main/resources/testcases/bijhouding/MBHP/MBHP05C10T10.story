Meta:
@status                 Klaar
@usecase                BY.1.MP

Narrative:
Scenario 1 Bijhoudingsituatie = "Indiener is bijhoudingspartij"

Scenario: PCT (4-13) Partij van persoon = indienende partij
          LT: MBHP05C10T10



Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-MBHP/MBHP05C10T10-Libby.xls

When voer een bijhouding uit MBHP05C10T10.xml namens partij 'Gemeente BRP 1'

Then is het antwoordbericht gelijk aan /testcases/bijhouding/MBHP/expected/MBHP05C10T10.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then staat er 0 notificatiebericht voor bijhouders op de queue

Then controleer tijdstip laatste wijziging in bijhoudingsplan voor bijgehouden personen
