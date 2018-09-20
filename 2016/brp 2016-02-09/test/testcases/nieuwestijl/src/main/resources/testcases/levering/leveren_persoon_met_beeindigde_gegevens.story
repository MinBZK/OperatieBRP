Meta:
@sprintnummer   76
@epic           Mutatielevering basis
@auteur         rarij
@jiraIssue      TEAMBRP-3004
@status         Klaar

Narrative:      Als productowner
                wil ik een verificatie van de leveringsfunctionaliteit rond beeindigde gegevens
                zodat met zekerheid weet dat het goed verwekt wordt

                Onderstaande scenario's maken gebruik van standaard DSL persoon 'Gregory'. Deze persoon is een persoon
                die schoon gecreeerd wordt eveneens als zijn ouders die tevens met elkaar getrouwd zijn en op
                hetzelfde adres woonachtig zijn.

Scenario:   1.  Afnemer met volledige autorisatie ontvangt, n.a.v. het beëindigen van het adres de hoofdpersoon, een
                mutatieBericht.
                Verwacht resultaat:
                - Melding BRLV0028: De geleverde persoon heeft de doelbindingspopulatie verlaten. Mutatielevering voor
                  deze persoonslijst is gestopt.
                - 2 'adres' groepen nl.:
                    * Een vervallen groep van het laatst, bekend, adres (B)
                    * Een gewijzigd groep van het laatst, bekend, adres  (B`)
                - Correct ingevulde DAG en DEG

Given de gehele database is gereset
Given leveringsautorisatie uit /levering_autorisaties/postcode_gebied_heemstede_2100-2129_en_afnemerindicatie, /levering_autorisaties/mutatielevering_obv_doelbinding/Mutaties_op_persoon_die_een_bijhoudingspartij_heeft_met_id=2(Minister)
Given de standaardpersoon Gregory met bsn 422316441 en anr 8615205650 met extra gebeurtenissen:
verhuizing(partij: 'Gemeente Heemstede', aanvang: 20101010, registratieDatum: 20101010) {
    naarGemeente 'Heemstede',
       straat: 'Manpadslaan', nummer: 4, postcode: '2105MA', woonplaats: "Heemstede"
}

Given verzoek voor leveringsautorisatie 'postcode gebied Heemstede 2100-2129 EN afnemerindicatie' en partij 'Gemeente Tiel'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand afnemerIndicatie_plaats_4.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Given wacht 2 seconden


Given de persoon beschrijvingen:
Gregory = Persoon.metBsn(422316441)
nieuweGebeurtenissenVoor(Gregory) {
    beeindigAdres(aanvang: 20150606) {
        op 20150606
    }
}
slaOp(Gregory)

When voor persoon 422316441 wordt de laatste handeling geleverd
And het mutatiebericht voor leveringsautorisatie postcode gebied Heemstede 2100-2129 EN afnemerindicatie is ontvangen en wordt bekeken

Then hebben de attributen in de groepen de volgende waardes:
| groep                | attribuut           | verwachteWaardes                                                                                                  |
| identificatienummers | burgerservicenummer | 422316441                                                                                                         |
| melding              | regelCode           | BRLV0027                                                                                                          |
| melding              | melding             | De geleverde persoon valt niet meer binnen de doelbindingspopulatie van de leveringsautorisatie. |

And heeft het bericht 2 groepen 'adres'

And hebben verwerkingssoorten in voorkomens de volgende waardes:
| groep | nummer | verwerkingssoort |
| adres | 1      | Wijziging        |
| adres | 2      | Verval           |

And hebben attributen in voorkomens de volgende waardes:
| groep | nummer | attribuut              | verwachteWaarde |
| adres | 1      | datumAanvangGeldigheid | 2010-10-10      |
| adres | 1      | datumEindeGeldigheid   | 2015-06-06      |
| adres | 2      | datumAanvangGeldigheid | 2010-10-10      |


Scenario:   2.  Afnemer met volledige autorisatie ontvangt, n.a.v. het beëindigen van het adres de hoofdpersoon, een
                volledigBericht.
                Verwacht resultaat:
                - 4 'adres' groepen nl.:
                    * Een gewijzigd groep van het eerste adres (A)
                    * Een vervallen groep van het eerste adres (A`)
                    * Een vervallen groep van het laatst, bekend, adres (B)
                    * Een gewijzigd groep van het laatst, bekend, adres  (B`)
                - Correct ingevulde DAG en DEG

Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand afnemerIndicatie_plaats_4.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When voor persoon 422316441 wordt de laatste handeling geleverd
And het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then hebben de attributen in de groepen de volgende waardes:
| groep                | attribuut           | verwachteWaardes                |
| identificatienummers | burgerservicenummer | 422316441, 814591139, 875271467 |

And heeft het bericht 4 groepen 'adres'
And hebben attributen in voorkomens de volgende aanwezigheid:
| groep | nummer | attribuut            | aanwezig |
| adres | 1      | datumEindeGeldigheid | ja       |
| adres | 2      | datumEindeGeldigheid | ja       |
| adres | 3      | tijdstipVerval       | ja       |
| adres | 4      | tijdstipVerval       | ja       |

And hebben attributen in voorkomens de volgende waardes:
| groep | nummer | attribuut              | verwachteWaarde |
| adres | 1      | datumAanvangGeldigheid | 2010-10-10      |
| adres | 1      | datumEindeGeldigheid   | 2015-06-06      |
| adres | 2      | datumAanvangGeldigheid | 1980-01-01      |
| adres | 2      | datumEindeGeldigheid   | 2010-10-10      |
| adres | 3      | datumAanvangGeldigheid | 2010-10-10      |
| adres | 4      | datumAanvangGeldigheid | 1980-01-01      |

Scenario:   3.  Afnemer met volledige autorisatie op attributen en materieleHistorie + nadereVerantwoording voor groep
                'adres', ontvangt, n.a.v. het plaatsen van een afnemerindicatie voor de beëindiging van het adres,
                een volledigBericht.
                Verwacht resultaat:
                - 2 'adres' groepen nl.:
                    * Een vervallen groep van het eerste adres (A`)
                    * Een gewijzigd groep van het laatst, bekend, adres  (B`)
                - Correct ingevulde DAG en DEG

Given leveringsautorisatie uit /levering_autorisaties/attendering/Attenderingen_op_pers_met_gewijzigde_postcode_EN_woonplaatsnaam_(pop.bep.=true)
Given verzoek voor leveringsautorisatie 'Attenderingen op pers met gewijzigde postcode EN woonplaatsnaam (pop.bep.=true)' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand afnemerIndicatie_plaats_4.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When voor persoon 422316441 wordt de laatste handeling geleverd
And het volledigbericht voor leveringsautorisatie Attenderingen op pers met gewijzigde postcode EN woonplaatsnaam (pop.bep.=true) is ontvangen en wordt
bekeken
Then hebben de attributen in de groepen de volgende waardes:
| groep                | attribuut           | verwachteWaardes                |
| identificatienummers | burgerservicenummer | 422316441, 814591139, 875271467 |

And heeft het bericht 2 groepen 'adres'
And hebben attributen in voorkomens de volgende aanwezigheid:
| groep | nummer | attribuut   | aanwezig |
| adres | 1      | actieInhoud | ja       |
| adres | 2      | actieInhoud | ja       |

And hebben attributen in voorkomens de volgende waardes:
| groep | nummer | attribuut              | verwachteWaarde |
| adres | 1      | datumAanvangGeldigheid | 2010-10-10      |
| adres | 1      | datumEindeGeldigheid   | 2015-06-06      |
| adres | 2      | datumAanvangGeldigheid | 1980-01-01      |
| adres | 2      | datumEindeGeldigheid   | 2010-10-10      |

Scenario:   4.  Afnemer met volledige autorisatie op attributen en materieleHistorie + nadereVerantwoording voor groep
                'adres', ontvangt, n.a.v. het plaatsen van een afnemerindicatie voor de beëindiging van het adres,
                een mutatieBericht.
                Verwacht resultaat:
                - 2 'adres' groepen nl.:
                    * Een vervallen groep van het laatst, bekend, adres (B)
                    * Een gewijzigd groep van het laatst, bekend, adres  (B`)
                - Correct ingevulde DAG en DEG

When voor persoon 422316441 wordt de laatste handeling geleverd
And het volledigbericht voor leveringsautorisatie Attenderingen op pers met gewijzigde postcode EN woonplaatsnaam (pop.bep.=true) is ontvangen en wordt
bekeken

Then hebben de attributen in de groepen de volgende waardes:
| groep                | attribuut           | verwachteWaardes |
| identificatienummers | burgerservicenummer | 422316441, 814591139, 875271467        |

And hebben attributen in voorkomens de volgende aanwezigheid:
| groep                 | nummer | attribuut | aanwezig |
| lvg_synVerwerkPersoon | 1      | meldingen | nee      |

And heeft het bericht 2 groepen 'adres'

And hebben attributen in voorkomens de volgende waardes:
| groep | nummer | attribuut              | verwachteWaarde |
| adres | 1      | datumAanvangGeldigheid | 2010-10-10      |
| adres | 1      | datumEindeGeldigheid   | 2015-06-06      |
| adres | 2      | datumAanvangGeldigheid | 1980-01-01      |

Scenario:   5.  Afnemer met volledige autorisatie op attributen en alleen nadereVerantwoording voor groep 'adres',
                ontvangt, n.a.v. het plaatsen van een afnemerindicatie voor de beëindiging van het adres,
                een mutatieBericht.
                Verwacht resultaat:
                - Melding BRLV0027: De geleverde persoon valt niet meer binnen de doelbindingspopulatie van het
                  abonnement.
                - 1 'adres' groep nl.:
                    * Een vervallen groep van het laatst, bekend, adres (B)
                - Correct ingevulde DAG en DEG
Given leveringsautorisatie uit /levering_autorisaties/mutatielevering_obv_afn_ind/Alleen_nadere_verantwoording_bij_mutaties_op_Ingezetene

Given de personen 875271467, 814591139, 422316441 zijn verwijderd
Given de standaardpersoon Gregory met bsn 422316441 en anr 8615205650 met extra gebeurtenissen:
verhuizing(partij: 'Gemeente Heemstede', aanvang: 20101010, registratieDatum: 20101010) {
    naarGemeente 'Heemstede',
       straat: 'Manpadslaan', nummer: 4, postcode: '2105MA', woonplaats: "Heemstede"
}

Given verzoek voor leveringsautorisatie 'Alleen nadere verantwoording bij mutaties op Ingezetene' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand afnemerIndicatie_plaats_4.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Given wacht 2 seconden

Given de persoon beschrijvingen:
Gregory = Persoon.metBsn(422316441)
nieuweGebeurtenissenVoor(Gregory) {
    beeindigAdres(aanvang: 20150606) {
        op 20150606
    }
}
slaOp(Gregory)

When voor persoon 422316441 wordt de laatste handeling geleverd
And het mutatiebericht voor leveringsautorisatie Alleen nadere verantwoording bij mutaties op Ingezetene is ontvangen en wordt bekeken

Then hebben de attributen in de groepen de volgende waardes:
| groep                | attribuut           | verwachteWaardes                                                                        |
| identificatienummers | burgerservicenummer | 422316441                                                                               |
| melding              | regelCode           | BRLV0027                                                                                |
| melding              | melding             | De geleverde persoon valt niet meer binnen de doelbindingspopulatie van de leveringsautorisatie. |

And heeft het bericht 2 groepen 'adres'
And hebben attributen in voorkomens de volgende aanwezigheid:
| groep | nummer | attribuut   | aanwezig |
| adres | 2      | actieVerval | ja       |

And hebben attributen in voorkomens de volgende waardes:
| groep | nummer | attribuut              | verwachteWaarde |
| adres | 1      | datumAanvangGeldigheid | 2010-10-10      |

Scenario:   6.  Afnemer met volledige autorisatie op attributen en alleen nadereVerantwoording voor groep 'adres',
                ontvangt, n.a.v. het plaatsen van een afnemerindicatie voor de beëindiging van het adres,
                een asynchroonBericht.
                Verwacht resultaat:
                - Geen voorkomen van groep adres
                - Geen adrescontainer in het bericht
Given leveringsautorisatie uit /levering_autorisaties/mutatielevering_obv_afn_ind/Alleen_nadere_verantwoording_bij_mutaties_op_Ingezetene

Given de personen 875271467, 814591139, 422316441 zijn verwijderd
Given de standaardpersoon Gregory met bsn 422316441 en anr 8615205650 met extra gebeurtenissen:
verhuizing(partij: 'Gemeente Heemstede', aanvang: 20101010, registratieDatum: 20101010) {
    naarGemeente 'Heemstede',
       straat: 'Manpadslaan', nummer: 4, postcode: '2105MA', woonplaats: "Heemstede"
}

Given verzoek voor leveringsautorisatie 'Alleen nadere verantwoording bij mutaties op Ingezetene' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand afnemerIndicatie_plaats_4.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

Given de persoon beschrijvingen:
Gregory = Persoon.metBsn(422316441)
nieuweGebeurtenissenVoor(Gregory) {
    beeindigAdres(aanvang: 20150606) {
        op 20150606
    }
}
slaOp(Gregory)

When voor persoon 422316441 wordt de laatste handeling geleverd


When het volledigbericht voor leveringsautorisatie Alleen nadere verantwoording bij mutaties op Ingezetene is ontvangen en wordt bekeken
Then hebben de attributen in de groepen de volgende waardes:
| groep                | attribuut           | verwachteWaardes                |
| identificatienummers | burgerservicenummer | 422316441, 814591139, 875271467 |

And heeft het bericht 1 groepen 'adressen'

Scenario:   7.  Afnemer met volledige autorisatie ontvangt, n.a.v. het synchroniseren van de gegevens van de hoofdpersoon,
                een synchroonBericht.
                Verwacht resultaat:
                - Melding VR00109: Expressie Populatiebeperking evalueert naar waarde NULL (onbekend)
                - Melding BRLV0023: De opgegeven persoon valt niet te synchroniseren binnen het opgegeven abonnement.

Given leveringsautorisatie uit /levering_autorisaties/postcode_gebied_heemstede_2100-2129
Given verzoek voor leveringsautorisatie 'postcode gebied Heemstede 2100-2129' en partij 'Gemeente Tiel'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand leveren_persoon_met_beeindigde_gegevens_02.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief

When het volledigbericht voor leveringsautorisatie postcode gebied Heemstede 2100-2129 is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden

And heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                                                           |
| BRLV0023 | De opgegeven persoon valt niet te synchroniseren binnen de opgegeven leveringsautorisatie. |

Scenario:   8.  Afnemer met volledige autorisatie ontvangt, n.a.v. het beëindigen van het adres de hoofdpersoon, een
                synchroonBericht.
                Verwacht resultaat:
                - Melding BRLV0014: De persoon valt niet binnen de doelgroep waarop de afnemer in dit abonnement een indicatie mag plaatsen.
                - Melding VR00109: Expressie Populatiebeperking evalueert naar waarde NULL (onbekend)

Given leveringsautorisatie uit /levering_autorisaties/postcode_gebied_heemstede_2100-2129_en_afnemerindicatie
Given verzoek voor leveringsautorisatie 'postcode gebied Heemstede 2100-2129 EN afnemerindicatie' en partij 'Gemeente Tiel'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand leveren_persoon_met_beeindigde_gegevens_03.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                                                                                             |
| BRLV0014 | De persoon valt niet binnen de populatie waarop de afnemer in deze leveringsautorisatie een indicatie mag plaatsen. |
