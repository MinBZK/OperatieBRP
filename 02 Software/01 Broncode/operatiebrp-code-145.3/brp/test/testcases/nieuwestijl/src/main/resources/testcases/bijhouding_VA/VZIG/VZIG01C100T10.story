Meta:
@status                 Klaar
@usecase                UCS-BY.0.VA

Narrative: R2770 Waarschuwing specifieke verstrekkingsbeperking vervalt als gevolg van registratie volledige verstrekkingsbeperking

Scenario:   Registratie volledige verstrekkingsbeperking bij Hoofdpersoon met een specifieke verstrekkingsbeperking
            LT: VZIG01C100T10

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-VZIG/VZIG01C100T10-001.xls

Then heeft $LIBBY_BSN$ de waarde van de volgende query: select bsn from kern.pers where voornamen = 'Libby'

!-- Specifieke verstrekkingsbeperking
When voer een bijhouding uit VZIG01C100T10a.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Geslaagd

!-- Volledige verstrekkingsbeperking
When voer een bijhouding uit VZIG01C100T10b.xml namens partij 'Gemeente BRP 2'
Then heeft het antwoordbericht verwerking Geslaagd

Then is het antwoordbericht gelijk aan /testcases/bijhouding_VA/VZIG/expected/VZIG01C100T10.xml voor expressie //brp:bhg_vbaRegistreerVerhuizing_R