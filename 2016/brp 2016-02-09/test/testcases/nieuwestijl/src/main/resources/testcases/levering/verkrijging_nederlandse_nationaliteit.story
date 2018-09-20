Meta:
@status Klaar
@auteur anjaw
@regels BRBY0141, R1692

Narrative:
In order to de nationaliteiten in berichten in correcte volgorde te ontvangen
As a Afnemer
I want to een levering ontvangen na een handeling omtrend nationaliteiten

R1692	BRBY0141	Een te verkrijgen nationaliteit mag nog niet voorkomen

Scenario: Verkrijging Nederlandse nationaliteit

Given de gehele database is gereset
Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Alkmaar.txt
Given bijhoudingsverzoek voor partij 'Gemeente Alkmaar'
And administratieve handeling van type verkrijgingNederlandseNationaliteit , met de acties registratieNationaliteitNaam
And testdata uit bestand verkrijging_nederlandse_nationaliteit_testcase_01.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
|CODE    |MELDING                               |
|BRBY0141|De nationaliteit 0001 staat reeds bij de persoon geregistreerd.|


