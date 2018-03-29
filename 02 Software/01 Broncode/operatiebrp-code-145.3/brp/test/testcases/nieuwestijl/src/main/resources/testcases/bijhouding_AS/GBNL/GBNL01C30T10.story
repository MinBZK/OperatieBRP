Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R1690 Kind moet Nederlander worden als het een 3e generatie kind betreft

Scenario: Kind is geen Nederlander terwijl de ouders en grootouders Ingezetenen zijn
          LT: GBNL01C30T10

Given alle personen zijn verwijderd

!-- Initiële vulling: Ingezeten grootouders
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL01C30T10-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL01C30T10-002.xls

!-- Initiële vulling: Ingezeten vader
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL01C30T10-003.xls

!-- Geboorte: Ingezeten moeder met BSNs van grootouders
When voer een bijhouding uit GBNL01C30T10a.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Geslaagd

!-- Geboorte niet-Nederlands kind met BSNs van moeder en vader
When voer een bijhouding uit GBNL01C30T10b.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GBNL/expected/GBNL01C30T10.xml voor expressie //brp:bhg_afsRegistreerGeboorte_R
