Meta:
@status                 Klaar
@sleutelwoorden         Geslaagd
@usecase                UCS-BY.HG

Narrative:
Test om blobs te genereren die in de API testen worden herbruikt

Scenario:   Personen Libby Thatcher met pers historie (Ingeschrevene-Ingezetene) en Piet Jansen (Ingeschrevene-Ingezetene) gaan trouwen, controleer relatie, betrokkenheid, afgeleid administratief,
            LT: VHNL04C10T30

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Libby_met_his_pers_is_datumAanvang_huwelijk.xls
Given enkel initiele vulling uit bestand /LO3PL/Piet.xls

When voer een bijhouding uit PRE_VHNL04C10T30_deblokkeerbaar.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd





