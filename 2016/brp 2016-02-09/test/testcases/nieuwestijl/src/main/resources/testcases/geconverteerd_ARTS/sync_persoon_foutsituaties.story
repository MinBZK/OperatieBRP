Synchronisatie Persoon - Fout situaties

Meta:
 @status        Klaar
 @auteur        sasme
 @sprintnummer  70
 @jiraIssue     TEAMBRP-2422

Narrative:
Synchronisatie persoon, situaties waarin geen synchronisatie gedaan mag worden

Scenario: Niet ingeschreven persoon mag niet worden gesynchroniseerd
Meta:
 @regels BRLV0006

Given leveringsautorisatie uit /levering_autorisaties/Mutaties_op_personen_ van_wie_u_de_bijhoudingspartij_heeft_id_347
Given verzoek voor leveringsautorisatie 'Mutaties op personen van wie u de bijhoudingspartij heeft id 347' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand synchronisatie_persoon_basis.yml
And extra waardes:
 | SLEUTEL                                 | WAARDE
 | zoekcriteriaPersoon.burgerservicenummer | 700158431

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
 | CODE     | MELDING
 | BRLV0006 | De opgegeven persoon is een niet-ingeschreven persoon. Levering niet mogelijk.


Scenario: Persoonslijst die is afgevoerd mag niet worden gesynchroniseerd
Meta:
 @regels BRLV0022

Given leveringsautorisatie uit /levering_autorisaties/Mutaties_op_personen_ van_wie_u_de_bijhoudingspartij_heeft_id_347
Given verzoek voor leveringsautorisatie 'Mutaties op personen van wie u de bijhoudingspartij heeft id 347' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand synchronisatie_persoon_basis.yml
And extra waardes:
 | SLEUTEL                                 | WAARDE
 | zoekcriteriaPersoon.burgerservicenummer | 617617545

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
 | CODE     | MELDING
 | BRLV0022 | Blokkerend: Er is geen geldige persoon met het opgegeven burgerservicenummer.


Scenario: Een persoon met verstrekkingsbeperking mag niet worden gesynchroniseerd
Meta:
 @regels BRLV0031

Given de database wordt gereset voor de personen 695544457
And de persoon beschrijvingen:
persoon = Persoon.metBsn(695544457)
nieuweGebeurtenissenVoor(persoon) {
    verstrekkingsbeperking(partij: 502707) {
        registratieBeperkingen( [partij: 502707] )
    }
}
slaOp(persoon)

Given leveringsautorisatie uit /levering_autorisaties/mutatielevering_obv_afn_ind/Mutaties_op_specifieke_personen_voor_afnemer_is_505002
Given verzoek voor leveringsautorisatie 'Mutaties op specifieke personen voor afnemer is 505002' en partij 'KUC033-PartijVerstrekkingsbeperking'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand synchronisatie_persoon_basis.yml
And extra waardes:
 | SLEUTEL                                 | WAARDE
 | stuurgegevens.zendendePartij            | 502707
 | zoekcriteriaPersoon.burgerservicenummer | 695544457

When het bericht wordt verstuurd
Then heeft het antwoordbericht de meldingen:
 | CODE     | MELDING
 | BRLV0031 | De persoon heeft een verstrekkingsbeperking waardoor deze dienst niet geleverd kan worden.

Then wacht tot alle berichten zijn ontvangen
And is het aantal ontvangen berichten 0


Scenario: Persoon buiten het abonnement kan niet worden gesynchroniseerd
Meta:
 @regels BRLV0023

Given de database is gereset voor de personen 210019712
Given leveringsautorisatie uit /levering_autorisaties/Mutaties_op_personen_van_wie_u_de_bijhoudingspartij_heeft_id_356
Given verzoek voor leveringsautorisatie 'Mutaties op personen van wie u de bijhoudingspartij heeft id 356' en partij 'SRPUC50151-2-Partij'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand synchronisatie_persoon_basis.yml
And extra waardes:
 | SLEUTEL                                 | WAARDE
 | stuurgegevens.zendendePartij            | 505002
 | zoekcriteriaPersoon.burgerservicenummer | 210019712

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
 | CODE     | MELDING
 | BRLV0023 | De opgegeven persoon valt niet te synchroniseren binnen de opgegeven leveringsautorisatie.



Scenario: Persoon zonder afnemerindicatie kan niet worden gesynchroniseerd
Meta:
 @regels BRLV0040

Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand synchronisatie_persoon_basis.yml
And extra waardes:
 | SLEUTEL                                 | WAARDE
 | stuurgegevens.zendendePartij            | 017401
 | zoekcriteriaPersoon.burgerservicenummer | 210015688

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
 | CODE     | MELDING
 | BRLV0040 | De opgegeven persoon valt niet te synchroniseren binnen de opgegeven leveringsautorisatie.

Then wacht tot alle berichten zijn ontvangen
And zijn er 0 berichten geleverd
