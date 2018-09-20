Meta:
@sprintnummer           74
@epic                   Mutatielevering basis
@auteur                 dihoe
@status                 Klaar
@regels                 VR00058
@jiraIssue              TEAMBRP-1981
@sleutelwoorden         DatumAanvangMaterielePeriode, DatumEindeGeldigheid

Narrative:
Controleren of onbekende DatumEindeGeldigheid correct wordt geinterpreteerd bij de DatumAanvangMaterielePeriode.

Scenario: 1. DatumAanvangMaterielePeriode = 2012/05/15, DatumEindeGeldigheid = 20120516: wel een volledigbericht

Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given de database is gereset voor de personen 306867837, 306741817
Given de personen 614492312 zijn verwijderd
Given de persoon beschrijvingen:
def vader_testpersoon    =   Persoon.uitDatabase(bsn: 306867837)
def moeder_testpersoon   =   Persoon.uitDatabase(bsn: 306741817)

testpersoon = uitGebeurtenissen {
    geboorte(partij: 17401, aanvang: 19930916, toelichting: '1e kind', registratieDatum: 19930916) {
        op '1993/09/16' te 'Delft' gemeente 503
        geslacht 'VROUW'
        namen {
            voornamen 'Charlotte'
            geslachtsnaam 'Hendriks'
        }
        ouders moeder: moeder_testpersoon, vader: vader_testpersoon
        identificatienummers bsn: 614492312, anummer: 5753210130
    }
    verhuizing(partij: 'Gemeente Hillegom', aanvang: 20120516, registratieDatum: 20120516) {
            naarGemeente 'Hillegom',
                straat: 'Dorpsstraat', nummer: 25, postcode: '2180AA', woonplaats: "Hillegom"
    }

}
slaOp(testpersoon)

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand afnemerIndicatie_plaats_3.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand datum_einde_geldigheid_onbekend_groepen_wel_geleverd_01.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then hebben attributen in voorkomens de volgende waardes:
| groep | nummer | attribuut              | verwachteWaarde |
| adres | 1      | datumAanvangGeldigheid | 2012-05-16      |
| adres | 2      | datumEindeGeldigheid   | 2012-05-16      |
| adres | 3      | datumAanvangGeldigheid | 1993-09-16      |

Scenario: 2. DatumAanvangMaterielePeriode = 2012/05/15, DatumEindeGeldigheid = 20120500: wel een volledigbericht

Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given de database is gereset voor de personen 306867837, 306741817
Given de personen 614492312 zijn verwijderd
Given de persoon beschrijvingen:
def vader_testpersoon    =   Persoon.uitDatabase(bsn: 306867837)
def moeder_testpersoon   =   Persoon.uitDatabase(bsn: 306741817)

testpersoon = uitGebeurtenissen {
    geboorte(partij: 17401, aanvang: 19930916, toelichting: '1e kind', registratieDatum: 19930916) {
        op '1993/09/16' te 'Delft' gemeente 503
        geslacht 'VROUW'
        namen {
            voornamen 'Charlotte'
            geslachtsnaam 'Hendriks'
        }
        ouders moeder: moeder_testpersoon, vader: vader_testpersoon
        identificatienummers bsn: 614492312, anummer: 5753210130
    }
    verhuizing(partij: 'Gemeente Hillegom', aanvang: 20120500, registratieDatum: 20120500) {
            naarGemeente 'Hillegom',
                straat: 'Dorpsstraat', nummer: 25, postcode: '2180AA', woonplaats: "Hillegom"
    }

}
slaOp(testpersoon)

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand afnemerIndicatie_plaats_3.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand datum_einde_geldigheid_onbekend_groepen_wel_geleverd_01.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then hebben attributen in voorkomens de volgende waardes:
| groep | nummer | attribuut              | verwachteWaarde |
| adres | 1      | datumAanvangGeldigheid | 2012-05         |
| adres | 2      | datumEindeGeldigheid   | 2012-05         |
| adres | 3      | datumAanvangGeldigheid | 1993-09-16      |

Scenario: 3. DatumAanvangMaterielePeriode = 2012/05/15, DatumEindeGeldigheid = 20120000: wel een volledigbericht

Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given de database is gereset voor de personen 306867837, 306741817
Given de personen 614492312 zijn verwijderd
Given de persoon beschrijvingen:
def vader_testpersoon    =   Persoon.uitDatabase(bsn: 306867837)
def moeder_testpersoon   =   Persoon.uitDatabase(bsn: 306741817)

testpersoon = uitGebeurtenissen {
    geboorte(partij: 17401, aanvang: 19930916, toelichting: '1e kind', registratieDatum: 19930916) {
        op '1993/09/16' te 'Delft' gemeente 503
        geslacht 'VROUW'
        namen {
            voornamen 'Charlotte'
            geslachtsnaam 'Hendriks'
        }
        ouders moeder: moeder_testpersoon, vader: vader_testpersoon
        identificatienummers bsn: 614492312, anummer: 5753210130
    }
    verhuizing(partij: 'Gemeente Hillegom', aanvang: 20120000, registratieDatum: 20121212) {
            naarGemeente 'Hillegom',
                straat: 'Dorpsstraat', nummer: 25, postcode: '2180AA', woonplaats: "Hillegom"
    }

}
slaOp(testpersoon)

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand afnemerIndicatie_plaats_3.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand datum_einde_geldigheid_onbekend_groepen_wel_geleverd_01.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then hebben attributen in voorkomens de volgende waardes:
| groep | nummer | attribuut              | verwachteWaarde |
| adres | 1      | datumAanvangGeldigheid | 2012            |
| adres | 2      | datumEindeGeldigheid   | 2012            |
| adres | 3      | datumAanvangGeldigheid | 1993-09-16      |

Scenario: 4. DatumAanvangMaterielePeriode = 2012/05/15, DatumEindeGeldigheid = 00000000: wel een volledigbericht

Meta:
@regels dianatest

Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given de database is gereset voor de personen 306867837, 306741817
Given de personen 614492312 zijn verwijderd
Given de persoon beschrijvingen:
def vader_testpersoon    =   Persoon.uitDatabase(bsn: 306867837)
def moeder_testpersoon   =   Persoon.uitDatabase(bsn: 306741817)

testpersoon = uitGebeurtenissen {
    geboorte(partij: 17401, aanvang: 19930916, toelichting: '1e kind', registratieDatum: 19930916) {
        op '1993/09/16' te 'Delft' gemeente 503
        geslacht 'VROUW'
        namen {
            voornamen 'Charlotte'
            geslachtsnaam 'Hendriks'
        }
        ouders moeder: moeder_testpersoon, vader: vader_testpersoon
        identificatienummers bsn: 614492312, anummer: 5753210130
    }
    verhuizing(partij: 'Gemeente Hillegom', aanvang: 20120516, registratieDatum: 20120516) {
            naarGemeente 'Hillegom',
                straat: 'Dorpsstraat', nummer: 25, postcode: '2180AA', woonplaats: "Hillegom"
    }

}
slaOp(testpersoon)

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand afnemerIndicatie_plaats_3.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

Given de database is aangepast met: update kern.his_persadres set dateindegel = 0
    where (dataanvgel = 19930916 and dateindegel = 20120516)
    and persadres = (select id from kern.persadres where pers = (select id from kern.pers where bsn = 614492312))
Given de database is aangepast met: update kern.his_persadres set dataanvgel = 0
    where dataanvgel = 20120516
    and persadres = (select id from kern.persadres where pers = (select id from kern.pers where bsn = 614492312))

Given de cache is herladen

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand datum_einde_geldigheid_onbekend_groepen_wel_geleverd_01.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht met dienst Synchronisatie persoon voor partij 017401 en leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then hebben attributen in voorkomens de volgende waardes:
| groep | nummer | attribuut              | verwachteWaarde |
| adres | 1      | datumEindeGeldigheid   | 0000            |
| adres | 2      | datumAanvangGeldigheid | 0000            |
| adres | 3      | datumAanvangGeldigheid | 1993-09-16      |
