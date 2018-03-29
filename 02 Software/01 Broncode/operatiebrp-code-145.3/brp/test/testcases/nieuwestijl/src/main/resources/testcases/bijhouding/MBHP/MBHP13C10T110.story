Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: Afstamming met Geboorte in Nederland

Scenario: AH "Geboorte in NL" waarbij de NOUWKIG is overleden op de geboorte datum
          LT: MBHP13C10T110

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-MBHP/MBHP13C10T100-001.xls
Given enkel initiele vulling uit bestand /LO3PL-MBHP/MBHP13C10T100-002.xls

When voer een bijhouding uit MBHP13C10T110.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/MBHP/expected/MBHP13C10T110.xml voor expressie //brp:bhg_afsRegistreerGeboorte_R

Then staat er 0 notificatiebericht voor bijhouders op de queue

Then controleer tijdstip laatste wijziging in bijhoudingsplan voor bijgehouden personen
