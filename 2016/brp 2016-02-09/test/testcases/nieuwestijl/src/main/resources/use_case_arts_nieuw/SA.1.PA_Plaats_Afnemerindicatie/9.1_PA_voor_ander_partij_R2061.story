Meta:
@auteur             aapos
@status             Klaar
@usecase            SA.0.PA,SA.1.PA,SA.1.PA.AL,SA.1.PA.CI,LV.1.PA,LV.1.VA,LV.0.AV,LV.1.AV,LV.1.AB,LV.1.XV,LV.1.AU,LV.1.AL,LV.1.MR,LV.1.VB,LV1.AB
@regels             R1336,R1339,R1350,R1402,R1403,R1404,R1405,R1406,R1407,R1408,R1538,R1539,R1984,R2016,R2061
@sleutelwoorden     Plaats afnemerindicatie, Controleer inhoud, Verwijder afnemerindicatie, Afhandeling verzoek, Afhandelen verzoek, Archiveer bericht, XSD validatie, Authenticatie, Autorisatie levering, Maak responsebericht, Verstuur bericht, Archiveer bericht
Narrative:
Plaatsen afnemerindicatie alleen mogelijk indien persoon tot de doelgroep van het abonnement behoort.
Wanneer de expressie van de populatiebeperking niet op waar evalueert kan er geen afnemerindicatie geplaatst worden

Scenario:16 Partij probeert een afnemerindicatie te plaatsen voor een andere partij, Persoon afnemerindicatie.afnemer <>Bericht zendende partij
                    Logische testgevallen Plaats Afnemerindicatie SA.1.PA: R2061_02
                    Verwacht Resultaat: Foutmelding: Een afnemer mag alleen voor zichzelf een indicatie onderhouden.
                    Bevinding: Er is een onbekende fout opgetreden probeer later opnieuw

Meta:
@status             Bug

Given leveringsautorisatie uit /levering_autorisaties/modelAutorisaties/model_autorisatie_levering_obv_afn_ind
Given de personen 299054457, 743274313, 606417801 zijn verwijderd
And de standaardpersoon UC_Kenny met bsn 606417801 en anr 1383746930 zonder extra gebeurtenissen

Given de persoon beschrijvingen:
persoon = Persoon.metBsn(606417801)
nieuweGebeurtenissenVoor(persoon) {
    verhuizing(partij: 'Gemeente Delft', aanvang: 20100212, registratieDatum: 20100212) {
            naarGemeente 'Delft',
                straat: 'Markt', nummer: 87, postcode: '2611GW', woonplaats: "Delft"
        }
}
slaOp(persoon)

When voor persoon 606417801 wordt de laatste handeling geleverd

Given verzoek voor leveringsautorisatie 'model autorisatie obv afnemerindicatie' en partij 'Gemeente Delft' met ondertekenaar 00000001001005650000 en transporteur 00000001001005650000
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie

And testdata uit bestand 9.2_Plaats_Afnemerindicatie_14.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd


Given verzoek voor leveringsautorisatie 'model autorisatie obv afnemerindicatie' en partij 'Gemeente Alkmaar' met ondertekenaar 00000001001005650000 en transporteur 00000001001005650000
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie

And testdata uit bestand 9.2_Plaats_Afnemerindicatie_11.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief

And heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Fout'
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                                        |
| R2061    | Een afnemer mag alleen voor zichzelf een indicatie onderhouden |


Given verzoek voor leveringsautorisatie 'model autorisatie obv afnemerindicatie' en partij 'Gemeente Alkmaar' met ondertekenaar 00000001001005650000 en transporteur 00000001001005650000
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie

And testdata uit bestand 9.2_Plaats_Afnemerindicatie_12.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief

And heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Fout'
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                                        |
| R2061    | Een afnemer mag alleen voor zichzelf een indicatie onderhouden |


Given verzoek voor leveringsautorisatie 'model autorisatie obv afnemerindicatie' en partij 'Gemeente Alkmaar' met ondertekenaar 00000001001005650000 en transporteur 00000001001005650000
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie

And testdata uit bestand 9.2_Plaats_Afnemerindicatie_13.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief

And heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Fout'
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                                        |
| R2061    | Een afnemer mag alleen voor zichzelf een indicatie onderhouden |

Given verzoek voor leveringsautorisatie 'model autorisatie obv afnemerindicatie' en partij 'Gemeente Alkmaar' met ondertekenaar 00000001001005650000 en transporteur 00000001001005650000
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie

And testdata uit bestand 9.2_Plaats_Afnemerindicatie_10.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief

And heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Fout'
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                                        |
| R2061    | Een afnemer mag alleen voor zichzelf een indicatie onderhouden |

