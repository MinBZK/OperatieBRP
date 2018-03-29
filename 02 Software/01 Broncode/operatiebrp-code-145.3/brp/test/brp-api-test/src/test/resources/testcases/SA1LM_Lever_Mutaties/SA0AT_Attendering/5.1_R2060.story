Meta:
@status             Klaar
@usecase            SA.0.AT
@regels             R1334, R1983, R1991, R1993, R1994, R2016, R2057, R2060, R2062, R2129
@sleutelwoorden     Attendering, Lever mutaties

Narrative:
Spontane berichten (het initiatief voor levering ligt bij de BRP) voor een bepaalde partij worden alleen geleverd
via de Toegang leveringsautorisatie van deze partij waarbij een Toegang leveringsautorisatie.Afleverpunt is opgenomen.
Opmerking: Dit speelt als er voor een Leveringsautorisatie van een bepaalde partij meerdere Toegang leveringsautorisatie bestaan.
Van deze Toegang leveringsautorisatie mag er maar één een Toegang leveringsautorisatie.Afleverpunt hebben.


Scenario: 1     Toegang leveringsautorisatie heeft geen afleverpunt
                LT: R2060_LT02
                Persoon wordt geboren en komt in doelbinding van de Leverautorisatie terecht en wordt afnemerindicatie geplaatst.
                Toegang Leveringsautorisaties is = 1
                Toegang leveringsautorisatie afleverpunt is NULL
                Verwacht Resultaat: Geen levering, afleverpunt ontbreekt.

Given leveringsautorisatie uit autorisatie/geen_afleverpunt
Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC20T280_xls
When voor persoon 854820425 wordt de laatste handeling geleverd
Then is er geen synchronisatiebericht gevonden
