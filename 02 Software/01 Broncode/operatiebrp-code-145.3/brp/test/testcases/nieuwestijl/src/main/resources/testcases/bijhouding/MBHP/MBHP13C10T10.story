Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: Afstamming met Geboorte in Nederland

Scenario: AH "Geboorte in NL" waarbij de identificatienummers ontbreken
          LT: MBHP13C10T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-MBHP/MBHP13C10T10-001.xls
Given enkel initiele vulling uit bestand /LO3PL-MBHP/MBHP13C10T10-002.xls

When voer een bijhouding uit MBHP13C10T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/MBHP/expected/MBHP13C10T10.xml voor expressie //brp:bhg_afsRegistreerGeboorte_R

Then staat er 1 notificatiebericht voor bijhouders op de queue

Then controleer tijdstip laatste wijziging in bijhoudingsplan voor bijgehouden personen
