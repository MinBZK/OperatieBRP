Meta:
@sprintnummer           74
@epic                   Mutatielevering basis
@auteur                 dihoe
@status                 Klaar
@regels                 VR00058
@jiraIssue              TEAMBRP-1981
@sleutelwoorden         DatumEindeGeldigheid, DatumAanvangMaterielePeriode

Narrative:
Controleren of onbekende DatumEindeGeldigheid correct wordt geinterpreteerd bij de DatumAanvangMaterielePeriode.

Scenario: 1. DatumAanvangMaterielePeriode = 2012/05/15, DatumEindeGeldigheid = 20120515: niet alle voorkomens adres groep wordt geleverd

Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given de database is gereset voor de personen 306867837, 306741817
Given de personen 431607229 zijn verwijderd
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
        identificatienummers bsn: 431607229, anummer: 8173289746
    }
    verhuizing(partij: 'Gemeente Hillegom', aanvang: 20120515, registratieDatum: 20120515) {
                naarGemeente 'Hillegom',
                    straat: 'Dorpsstraat', nummer: 25, postcode: '2180AA', woonplaats: "Hillegom"
        }

}
slaOp(testpersoon)

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand afnemerIndicatie_plaats_4.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd


Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand datum_einde_geldigheid_gelijk_of_kleiner_dan_datum_materiele_periode_01.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then hebben attributen in voorkomens de volgende waardes:
| groep | nummer | attribuut              | verwachteWaarde |
| adres | 1      | datumAanvangGeldigheid | 2012-05-15      |
| adres | 2      | datumAanvangGeldigheid | 1993-09-16      |

And hebben attributen in voorkomens de volgende aanwezigheid:
| groep | nummer | attribuut            | aanwezig |
| adres | 2      | datumEindeGeldigheid | nee      |

Scenario: 2. DatumAanvangMaterielePeriode = 2012/05/15, DatumEindeGeldigheid = 20120514: niet alle voorkomens adres groep wordt geleverd

Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given de database is gereset voor de personen 306867837, 306741817
Given de personen 431607229 zijn verwijderd
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
        identificatienummers bsn: 431607229, anummer: 8173289746
    }
    verhuizing(partij: 'Gemeente Hillegom', aanvang: 20120514, registratieDatum: 20120514) {
                naarGemeente 'Hillegom',
                    straat: 'Dorpsstraat', nummer: 25, postcode: '2180AA', woonplaats: "Hillegom"
        }
}

slaOp(testpersoon)
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand afnemerIndicatie_plaats_4.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand datum_einde_geldigheid_gelijk_of_kleiner_dan_datum_materiele_periode_02.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then hebben attributen in voorkomens de volgende waardes:
| groep | nummer | attribuut              | verwachteWaarde |
| adres | 1      | datumAanvangGeldigheid | 2012-05-14      |
| adres | 2      | datumAanvangGeldigheid | 1993-09-16      |

And hebben attributen in voorkomens de volgende aanwezigheid:
| groep | nummer | attribuut            | aanwezig |
| adres | 2      | datumEindeGeldigheid | nee      |
