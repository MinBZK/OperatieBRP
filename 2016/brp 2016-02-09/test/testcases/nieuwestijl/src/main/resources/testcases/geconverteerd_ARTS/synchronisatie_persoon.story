Synchronisatie Persoon

Meta:
 @status        Onderhanden
 @sprintnummer  70
 @jiraIssue     TEAMBRP-2422
 @auteur        rohar

Narrative:
Synchronisatie persoon testen

Scenario: Standaard flow synchronisatie persoon
Meta:
 @regels VR00050

Given leveringsautorisatie uit /levering_autorisaties/Mutaties_op_personen_ van_wie_u_de_bijhoudingspartij_heeft_id_347
Given verzoek voor leveringsautorisatie 'Mutaties op personen van wie u de bijhoudingspartij heeft id 347' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand synchronisatie_persoon_basis.yml
And extra waardes:
 | SLEUTEL                                 | WAARDE
 | zoekcriteriaPersoon.burgerservicenummer | 700137038
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
And heeft in het antwoordbericht 'zendendePartij' in 'stuurgegevens' de waardes '199903'

When het volledigbericht voor leveringsautorisatie Mutaties op personen van wie u de bijhoudingspartij heeft id 347 is ontvangen en wordt bekeken
Then hebben de attributen in de groepen de volgende waardes:
 | groep         | attribuut         | verwachteWaardes
 | stuurgegevens | ontvangendePartij | 034401


Scenario: Synchronisatie persoon - 1
Given de database wordt gereset voor de personen 210019712
Given leveringsautorisatie uit /levering_autorisaties/Mutaties_op_personen_ van_wie_u_de_bijhoudingspartij_heeft_id_347
Given verzoek voor leveringsautorisatie 'Mutaties op personen van wie u de bijhoudingspartij heeft id 347' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand synchronisatie_persoon_basis.yml
And extra waardes:
 | SLEUTEL                                 | WAARDE
 | zoekcriteriaPersoon.burgerservicenummer | 210019712
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Mutaties op personen van wie u de bijhoudingspartij heeft id 347 is ontvangen en wordt bekeken
Then hebben de attributen in de groepen de volgende waardes:
 | groep                | attribuut           | verwachteWaardes
 | stuurgegevens        | ontvangendePartij   | 034401
 | identificatienummers | burgerservicenummer | 210019712, 210015329, 210019505

Then is het synchronisatiebericht gelijk aan /testcases/geconverteerd_ARTS/synchronisatie_persoon/SyncStructuur-TC01-2-dataresponse.xml voor expressie //brp:synchronisatie


Scenario: Synchronisatie persoon - 2
Given de database wordt gereset voor de personen 210015329
Given leveringsautorisatie uit /levering_autorisaties/Mutaties_op_personen_ van_wie_u_de_bijhoudingspartij_heeft_id_347
Given verzoek voor leveringsautorisatie 'Mutaties op personen van wie u de bijhoudingspartij heeft id 347' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand synchronisatie_persoon_basis.yml
And extra waardes:
 | SLEUTEL                                 | WAARDE
 | zoekcriteriaPersoon.burgerservicenummer | 210015329
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Mutaties op personen van wie u de bijhoudingspartij heeft id 347 is ontvangen en wordt bekeken
Then hebben de attributen in de groepen de volgende waardes:
 | groep                | attribuut           | verwachteWaardes
 | stuurgegevens        | ontvangendePartij   | 034401
 | identificatienummers | burgerservicenummer | 210015329, 210018501, 210018628, 210019505, 210019712

Then is het synchronisatiebericht gelijk aan /testcases/geconverteerd_ARTS/synchronisatie_persoon/SyncStructuur-TC02-2-dataresponse.xml voor expressie //brp:synchronisatie


Scenario: Synchronisatie persoon met afnemerindicatie (beperkte historie)
Meta:
 @regels VRLV0001, BRLV0016

Given de database wordt gereset voor de personen 110015927
And de persoon beschrijvingen:
def persoon = Persoon.metBsn(110015927)
nieuweGebeurtenissenVoor(persoon) {
    afnemerindicaties {
        plaatsVoor(afnemer: 17401, abonnement: 'Geen pop.bep. levering op basis van afnemerindicatie') {
            datumAanvangMaterielePeriode gisteren()
        }
    }
}
slaOp(persoon)

Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand synchronisatie_persoon_basis.yml
And extra waardes:
 | SLEUTEL                                 | WAARDE
 | stuurgegevens.zendendePartij            | 017401
 | zoekcriteriaPersoon.burgerservicenummer | 110015927
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then hebben de attributen in de groepen de volgende waardes:
 | groep                | attribuut           | verwachteWaardes
 | stuurgegevens        | ontvangendePartij   | 017401

Then is het synchronisatiebericht gelijk aan /testcases/geconverteerd_ARTS/synchronisatie_persoon/SyncStructuur-TC03-2-dataresponse.xml voor expressie //brp:synchronisatie


Scenario: Persoon met afnemerindicatie mag worden gesynchroniseerd

Given de database is gereset voor de personen 110015927
And de persoon beschrijvingen:
persoon = Persoon.metBsn(110015927)
nieuweGebeurtenissenVoor(persoon) {
    afnemerindicaties {
        plaatsVoor(afnemer: 505005, abonnement: 'postcode gebied Hillegom 2180 - 2182') {
            datumAanvangMaterielePeriode vandaag()
        }
    }

    verhuizing() {
        naarGemeente 397,
            aanvangAdreshouding: gisteren(),
            straat: 'Plein', nummer: 4, postcode: '2100AB', woonplaats: "Heemstede"
    }

}
slaOp(persoon)

Given leveringsautorisatie uit /levering_autorisaties/mutatielevering_obv_afn_ind/postcode_gebied_Hillegom_2180-2182
Given verzoek voor leveringsautorisatie 'postcode gebied Hillegom 2180 - 2182' en partij 'SRPUC50151-5-Partij'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand synchronisatie_persoon_basis.yml
And extra waardes:
 | SLEUTEL                                 | WAARDE
 | stuurgegevens.zendendePartij            | 505005
 | zoekcriteriaPersoon.burgerservicenummer | 110015927

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie postcode gebied Hillegom 2180 - 2182 is ontvangen en wordt bekeken
Then hebben de attributen in de groepen de volgende waardes:
 | groep                | attribuut           | verwachteWaardes
 | stuurgegevens        | ontvangendePartij   | 505005


Scenario: Mag synchroniseren maar met waarschuwing
Meta:
 @regels BRLV0038

Given de database is gereset voor de personen 210015615
Given leveringsautorisatie uit /levering_autorisaties/Mutaties_op_personen_waarvan_de_bijhoudingspartij_id=347
Given verzoek voor leveringsautorisatie 'Mutaties op personen waarvan de bijhoudingspartij id = 347' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand synchronisatie_persoon_basis.yml
And extra waardes:
 | SLEUTEL                                 | WAARDE
 | zoekcriteriaPersoon.burgerservicenummer | 210015615

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
And heeft het antwoordbericht de meldingen:
 | CODE     | MELDING
 | BRLV0038 | De opgegeven persoon valt niet binnen de doelbinding van mutatielevering obv doelbinding.

And heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Waarschuwing'
And wordt er 1 bericht geleverd
