Narrative:
In order to de nationaliteiten in berichten in correcte volgorde te ontvangen
As a Afnemer
I want to een levering ontvangen na een handeling omtrend nationaliteiten

R1816	BRAL2013	Staatloos en Nationaliteit sluiten elkaar uit
R1897	BRAL2017	Vastgesteld niet Nederlander en Nederlandse nationaliteit sluiten elkaar uit
R1419	BRAL2018	Behandeld als Nederlander en Nederlandse nationaliteit sluiten elkaar uit

Scenario: Wijziging indicatie nationaliteit

Meta:
@status Klaar
@auteur anjaw
@regels BRAL2013, BRAL2017, BRAL2018


Given de gehele database is gereset
Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Alkmaar.txt
Given bijhoudingsverzoek voor partij 'Gemeente Alkmaar'
And administratieve handeling van type wijzigingIndicatieNationaliteit , met de acties registratieBehandeldAlsNederlander, registratieStaatloos, registratieVastgesteldNietNederlander
And testdata uit bestand wijziging_indicatie_nationaliteit_testcase_01.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
|CODE    |MELDING                               |
|BRAL2013|Een persoon mag niet een nationaliteit bezitten en tegelijkertijd staatloos zijn.|
|BRAL2017|Een vastgesteld niet-Nederlander mag niet de Nederlandse nationaliteit hebben.|
|BRAL2018|Een persoon met de indicatie behandeld als Nederlander mag niet tegelijkertijd de Nederlandse nationaliteit hebben.|
