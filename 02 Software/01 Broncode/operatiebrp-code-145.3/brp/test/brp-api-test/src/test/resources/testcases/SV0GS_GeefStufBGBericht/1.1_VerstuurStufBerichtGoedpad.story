Meta:
@status             Klaar
@usecase            SV.0.GS
@sleutelwoorden     Stuf bg vertaal

Narrative:
Verstuur stuf vertaal verzoek:
Verstuur verzoek en ontvang vertaling

Scenario:   1. StandaardStufAutorisatie verstuurt een stuf vertaal verzoek
            LT: R2439_LT01, R2440_LT01, R2441_LT01, R2442_LT01, R1257_LT01, R1258_LT03, R1261_LT01, R1262_LT27, R1263_LT01, R1264_LT23, R2052_LT01, R2053_LT01, R2056_LT23, R2120_LT01, R2121_LT01, R2122_LT01, R2130_LT23, R2239_LT01, R2242_LT01, R2243_LT01, R2244_LT01, R2245_LT01, R2524_LT04, R2585_LT01, R2443_LT01, R2444_LT01, R1266_LT10
            Verwacht resultaat:
            - Geslaagd


Given leveringsautorisatie uit autorisatie/StandaardStufAutorisatie
Given verzoek verstuur stuf bericht:
|key|value
|leveringsautorisatieNaam|'StandaardStufAutorisatie'
|zendendePartijNaam|'Gemeente Utrecht'
|versieNr|0310
|soortBericht|Mutatiebericht
|soortSynchronisatie|Mutatiebericht

Then heeft het antwoordbericht verwerking Geslaagd

!-- R1266_LT10
Then heeft in het antwoordbericht 'zendendePartij' in 'stuurgegevens' de waarde '199903'
Then heeft in het antwoordbericht 'zendendeSysteem' in 'stuurgegevens' de waarde 'BRP'
Then is in antwoordbericht de aanwezigheid van 'referentienummer' in 'stuurgegevens' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'crossReferentienummer' in 'stuurgegevens' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'tijdstipVerzending' in 'stuurgegevens' nummer 1 ja
