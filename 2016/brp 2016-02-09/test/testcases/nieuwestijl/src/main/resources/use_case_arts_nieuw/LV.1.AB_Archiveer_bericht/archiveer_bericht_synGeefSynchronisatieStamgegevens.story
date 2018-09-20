Meta:
@sprintnummer           91
@epic                   Archiveer bericht
@auteur                 miuij
@status                 Klaar
@regels                 R1269, R1270

Narrative:
Als beheerder wil ik bij een bericht van soort lvg_synGeefSynchronisatieStamgegeven dat deze wordt gearchiveerd
Na levering van het bericht
Wil ik dat het uitgaande bericht van soort lvg_synGeef wordt gearchiveerd en dat er geen record wordt aangemaakt in de berpers tabel


Scenario:       Synchroniseer Stamgegevens, inkomend en uitgaand bericht geen persoonreferentie in berpers
                Logische testgevallen LV.1.AV Afhandelen Verzoek LV.1.AB:   R1269_L05, R1270_L07
                verwacht resultaat:
                1. Geen persoonsreferentie in berpers voor inkomend bericht
                2. Geen persoonsreferentie in berpers voor uitgaand bericht

Given leveringsautorisatie uit /levering_autorisaties/synchronisatie_stamgegeven
Given verzoek voor leveringsautorisatie 'SynchronisatieStamgegeven' en partij 'Gemeente Alkmaar'
Given verzoek van bericht lvg_synGeefSynchronisatieStamgegeven
And testdata uit bestand 2.1_synchronisatie_stamgegeven_basis.yml
And extra waardes:
| SLEUTEL                        | WAARDE
| parameters.stamgegeven         | ElementTabel

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
And heeft het antwoordbericht 1101 groepen 'element'

Then bestaat er geen voorkomen in berpers tabel voor referentie 0000000A-3000-7000-FFFF-000000000041 en srt 66
Then bestaat er geen voorkomen in berpers tabel voor crossreferentie 0000000A-3000-7000-FFFF-000000000041 en srt 67

