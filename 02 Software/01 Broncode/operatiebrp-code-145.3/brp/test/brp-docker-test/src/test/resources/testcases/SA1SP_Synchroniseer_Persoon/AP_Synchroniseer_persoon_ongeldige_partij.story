Meta:
@status             Klaar
@usecase            AL.1.AB
@regels             R1268, R1269, R1270
@sleutelwoorden     Archiveer bericht



Narrative:
Als beheerder wil ik bij een binnenkomend bericht van soort lvg_synGeef dat deze wordt gearchiveerd
Na levering van het bericht
Wil ik dat het uitgaande bericht van soort lvg_synGeef wordt gearchiveerd

Scenario:   1.  Synchoniseer persoon met ongeldige partij
                LT: R1268_LT03
                Verwacht resultaat:
                - Bericht verzendende partij niet gevuld

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Jan.xls
!-- R1262_LT03

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van doelbinding gemeente ongeldig' en partij 'Gemeente Ongeldig'
Given xml verzoek uit bestand /testcases/SA1SP_Synchroniseer_Persoon/Requests/Synchroniseer_persoon_gemeente_ongeldig.xml

Then heeft het antwoordbericht verwerking Foutief

Then is er gearchiveerd met de volgende gegevens:
| veld                  | waarde                               |
| zendendepartij        | NULL                                 |