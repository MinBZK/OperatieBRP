Meta:
@status             Klaar
@usecase            AL.1.VE
@regels             R1985, R1986, R1991
@sleutelwoorden     Verzend bericht

Narrative: Als beheerder van het voor zowel het BRP als het GBA stelsel kunnen leveren
De waarde van Leveringsautorisatie.Stelsel bepaald hoe het bericht verstuurd wordt.
Als Stelsel de waarde "BRP" heeft wordt het bericht via het BRP koppelvlak Levering naar de afnemer verstuurd.
Als Stelsel de waarde "GBA" heeft wordt het bericht voor de afnemer klaargezet en kan de afnemer het via het LO3 koppelvlak ophalen.

Scenario:   1.  Bericht wordt verstuurd via stelsel met waarde "GBA"
                LT: R1991_LT02, R1993_LT02
                Verwacht resultaat:
                - Levering via het LO3 koppelvlak

Given alle personen zijn verwijderd
Given personen uit bestanden:
|filenaam
|/LO3PL/GBNL04C10T10-001.xls
|/LO3PL/GBNL04C10T10-002.xls

When voer een bijhouding uit /testcases/AL1VZ_Verzenden_LO3/GBNL04C10T10.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

Then is er een LO3 levering gedaan




