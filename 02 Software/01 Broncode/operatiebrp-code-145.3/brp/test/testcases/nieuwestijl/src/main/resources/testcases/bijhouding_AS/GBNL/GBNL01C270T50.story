Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R2523 Ouder mag geen kinderen hebben met identieke burgerservicenummers en of administratienummers

Scenario:   OUWKIG heeft op Datum aanvang geldigheid geen kind (Pseudo) met hetzelfde Persoon.Burgerservicenummer en Persoon.Administratienummer (wel later)
            LT: GBNL01C270T50

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL01C270T50-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL01C270T10-002.xls

When voer een bijhouding uit GBNL01C270T50.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GBNL/expected/GBNL01C270T50.xml voor expressie //brp:bhg_afsRegistreerGeboorte_R