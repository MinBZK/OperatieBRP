Meta:
@status                 Klaar
@sleutelwoorden         Geslaagd
@usecase                UCS-BY.HG

Narrative:
Registratie aanvang huwelijk in NL tussen I-I en I-I

Scenario:   Personen Libby Thatcher met pers historie (Ingeschrevene-Ingezetene) en Piet Jansen (Ingeschrevene-Ingezetene) gaan trouwen, controleer relatie, betrokkenheid, afgeleid administratief,
            LT: VHNL04C10T30

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Libby_met_his_pers_2016.xls
Given enkel initiele vulling uit bestand /LO3PL/Piet.xls

When voer een bijhouding uit PRE_VHNL04C10T30_20160101.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Geslaagd