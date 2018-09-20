Meta:
@sprintnummer           74
@epic                   Mutatielevering basis
@auteur                 aapos
@status                 Klaar
@regels                 VR00058h
@jiraIssue              TEAMBRP-1981
@sleutelwoorden         DatumAanvangMaterielePeriode, DatumEindeGeldigheid

Narrative:
Controleren of onbekende DatumEindeGeldigheid correct wordt geinterpreteerd bij de DatumAanvangMaterielePeriode.

Scenario: 1. DatumAanvangMaterielePeriode = 0, DatumEindeGeldigheid = 20120516: alle voorkomens adres groep wordt geleverd

Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given de database is gereset voor de personen 306867837, 306741817
Given de personen 214397282 zijn verwijderd

Given de standaardpersoon Vanilla met bsn 214397282 en anr 8167421202 met extra gebeurtenissen:
verhuizing(partij: 'Gemeente Hillegom', aanvang: 20120516, registratieDatum: 20120516) {
    naarGemeente 'Hillegom',
        straat: 'Dorpsstraat', nummer: 25, postcode: '2180AA', woonplaats: "Hillegom"
}

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand afnemerIndicatie_plaats.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

Given de database is aangepast met: update autaut.persafnemerindicatie set dataanvmaterieleperiode = 0
            where levsautorisatie = (select id from autaut.levsautorisatie where naam = 'Geen pop.bep. levering op basis van afnemerindicatie')
            and pers = (select id from kern.pers where bsn = 214397282)

Given de cache is herladen
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand datum_aanv_materiele_periode_geheel_onbekend_of_leeg_groepen_wel_geleverd_01.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then hebben attributen in voorkomens de volgende waardes:
| groep | nummer | attribuut              | verwachteWaarde |
| adres | 1      | datumAanvangGeldigheid | 2012-05-16      |
| adres | 2      | datumEindeGeldigheid   | 2012-05-16      |
| adres | 3      | datumAanvangGeldigheid | 1980-01-01      |

Scenario: 2. DatumAanvangMaterielePeriode = leeg, DatumEindeGeldigheid = 20120516: alle voorkomens adres groep wordt geleverd
Meta:
@regels                 VR00058b
Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given de database is gereset voor de personen 306867837, 306741817
Given de personen 214397282 zijn verwijderd
Given de standaardpersoon Vanilla met bsn 214397282 en anr 8167421202 met extra gebeurtenissen:
verhuizing(partij: 'Gemeente Hillegom', aanvang: 20120516, registratieDatum: 20120516) {
    naarGemeente 'Hillegom',
        straat: 'Dorpsstraat', nummer: 25, postcode: '2180AA', woonplaats: "Hillegom"
}

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand afnemerIndicatie_plaats.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand datum_aanv_materiele_periode_geheel_onbekend_of_leeg_groepen_wel_geleverd_01.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then hebben attributen in voorkomens de volgende waardes:
| groep | nummer | attribuut              | verwachteWaarde |
| adres | 1      | datumAanvangGeldigheid | 2012-05-16      |
| adres | 2      | datumEindeGeldigheid   | 2012-05-16      |
| adres | 3      | datumAanvangGeldigheid | 1980-01-01      |
