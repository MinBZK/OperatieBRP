Meta:
@sprintnummer       92
@epic               use case art
@auteur             aapos
@jiraIssue          TEAMBRP-4618
@status             Klaar
@regels             R2051

Narrative:  Als stelsebeheerder,
            wil ik dat autorisatie ivm attributen ook toegepast wordt op groepen,
            zodat de afnemer geen lege groepen krijgt over wat hij niet mag zien

Scenario:   R2051_01 Actie in verantwoording verwijst naar inhoudelijke groep (adres)
            Logisch testgeval: R2051_L01
            Verwacht Resultaat: ActieInhoud, ActieAanpassingGeldigheid en actieVerval zijn aanwezig bij de inhoudelijke groep

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given de personen 436257865, 634915113, 743274313, 205416937, 906868233, 877968585, 555163209 zijn verwijderd
Given de standaardpersoon UC_Huisman met bsn 436257865 en anr 4121946386 zonder extra gebeurtenissen

Given de persoon beschrijvingen:
UC_Huisman = Persoon.metBsn(436257865)
nieuweGebeurtenissenVoor(UC_Huisman) {
    verhuizing(partij: 'Gemeente Pijnacker', aanvang: 20160202, registratieDatum: 20160203) {
        naarGemeente 'Pijnacker',
            straat: 'Fien de la Marstraat', nummer: 50, postcode: '2642BX', woonplaats: "Pijnacker"
    }
}
slaOp(UC_Huisman)

When voor persoon 436257865 wordt de laatste handeling geleverd

When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then verantwoording acties staan in persoon

Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep             | nummer | attribuut                 | aanwezig |
| adres             | 1      | actieInhoud               | true     |
| adres             | 2      | actieAanpassingGeldigheid | true     |
| adres             | 3      | actieVerval               | true     |
| bijgehoudenActies | 1      | actie                     | true     |

Scenario:   R2051_01A Actie in verantwoording verwijst naar inhoudelijke groep (adres)
            Logisch testgeval: R2051_L0X
            Verwacht Resultaat: ActieInhoud, ActieAanpassingGeldigheid en actieVerval zijn NIET aanwezig bij de inhoudelijke groep (samengesteldeNaam)

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van doelbinding' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand R2051_synchronisatie_01.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then verantwoording acties staan in persoon

Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep                  | nummer | attribuut                 | aanwezig |
| adres                  | 1      | actieInhoud               | true     |
| adres                  | 2      | actieInhoud               | true     |
| adres                  | 2      | actieAanpassingGeldigheid | true     |
| adres                  | 3      | actieInhoud               | true     |
| adres                  | 3      | actieAanpassingGeldigheid | true     |
| adres                  | 4      | actieInhoud               | true     |
| adres                  | 4      | actieVerval               | true     |
| adres                  | 4      | actieInhoud               | true     |
| adres                  | 5      | actieInhoud               | true     |
| adres                  | 5      | actieVerval               | true     |
| voornaam               | 1      | actieInhoud               | true     |
| geslachtsnaamcomponent | 1      | actieInhoud               | true     |
| naamgebruik            | 1      | actieInhoud               | true     |
| identificatienummers   | 1      | actieInhoud               | true     |
| samengesteldeNaam      | 1      | actieInhoud               | true     |
| geboorte               | 1      | actieInhoud               | true     |
| geslachtsaanduiding    | 1      | actieInhoud               | true     |
| inschrijving           | 1      | actieInhoud               | true     |

Scenario:   R2051_02 Actie in verantwoording verwijst naar inhoudelijke groep (adres)
            Logisch testgeval: R2051_L02
            Verwacht Resultaat: ActieInhoud, ActieAanpassingGeldigheid en actieVerval zijn NIET aanwezig bij de inhoudelijke groepen <> adres (niet geraakt door de admn. handeling)

When voor persoon 436257865 wordt de laatste handeling geleverd

When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then verantwoording acties staan in persoon

Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep                | nummer | attribuut                 | aanwezig |
| identificatienummers | 1      | actieInhoud               | false    |
| samengesteldeNaam    | 1      | actieInhoud               | false    |
| geboorte             | 1      | actieInhoud               | false    |
| geslachtsaanduiding  | 1      | actieInhoud               | false    |
| identificatienummers | 1      | actieAanpassingGeldigheid | false    |
| samengesteldeNaam    | 1      | actieAanpassingGeldigheid | false    |
| geboorte             | 1      | actieAanpassingGeldigheid | false    |
| geslachtsaanduiding  | 1      | actieAanpassingGeldigheid | false    |
| identificatienummers | 1      | actieVerval               | false    |
| samengesteldeNaam    | 1      | actieVerval               | false    |
| geboorte             | 1      | actieVerval               | false    |
| geslachtsaanduiding  | 1      | actieVerval               | false    |


Scenario:   R2051_03 Actie in verantwoording verwijst naar onderzoeksgroep (onderzoek gestart, onderzoek beeindigd)
            Logisch testgeval: R2051_L03
            Verwacht Resultaat: ActieInhoud, ActieAanpassingGeldigheid en actieVerval zijn aanwezig bij de onderzoeksgroep


Given de persoon beschrijvingen:
UC_Huisman = Persoon.metBsn(436257865)
nieuweGebeurtenissenVoor(UC_Huisman) {
    onderzoekAangemaakt(partij: 34401, registratieDatum: 20160203) {
        gestartOp(aanvangsDatum:'2016-02-03', omschrijving:'Onderzoek is gestart op geboortedatum', verwachteAfhandelDatum:'2016-04-01')
                gegevensInOnderzoek('Persoon.Geboorte.Datum')
    }
}
slaOp(UC_Huisman)

When voor persoon 436257865 wordt de laatste handeling geleverd

When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then verantwoording acties staan in persoon

Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep     | nummer | attribuut                 | aanwezig |
| onderzoek | 2      | actieInhoud               | true     |
| geboorte  | 1      | actieInhoud               | false    |
| geboorte  | 1      | actieAanpassingGeldigheid | false    |
| geboorte  | 1      | actieVerval               | false    |

Scenario:   R2051_03B Wijziging onderzoek
Given de persoon beschrijvingen:
UC_Huisman = Persoon.metBsn(436257865)
nieuweGebeurtenissenVoor(UC_Huisman) {
   onderzoekGewijzigd(partij: 34401, registratieDatum: 20160206) {
        wijzigOnderzoek(wijzigingsDatum:'2016-02-06', omschrijving:'Onderzoek gewijzigd', aanvangsDatum: '2016-02-03', verwachteAfhandelDatum: '2016-03-03')
        }
   }
slaOp(UC_Huisman)

When voor persoon 436257865 wordt de laatste handeling geleverd

When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then verantwoording acties staan in persoon

Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep     | nummer | attribuut   | aanwezig |
| onderzoek | 2      | actieInhoud | true     |
| onderzoek | 3      | actieVerval | true     |
| geboorte  | 1      | actieInhoud               | false    |
| geboorte  | 1      | actieAanpassingGeldigheid | false    |
| geboorte  | 1      | actieVerval               | false    |

Scenario:   R2051_03C Beeindiging onderzoek
Given de persoon beschrijvingen:
UC_Huisman = Persoon.metBsn(436257865)
nieuweGebeurtenissenVoor(UC_Huisman) {
   onderzoekBeeindigd(partij: 34401, registratieDatum: 20160206) {
        afgeslotenOp(eindDatum:'2016-02-08')
        }
   }
slaOp(UC_Huisman)

When voor persoon 436257865 wordt de laatste handeling geleverd

When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then verantwoording acties staan in persoon

Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep     | nummer | attribuut                 | aanwezig |
| onderzoek | 2      | actieInhoud               | true     |
| onderzoek | 3      | actieVerval               | true     |
| geboorte  | 1      | actieInhoud               | false    |
| geboorte  | 1      | actieAanpassingGeldigheid | false    |
| geboorte  | 1      | actieVerval               | false    |

Scenario:   R2051_04 Actie in verantwoording verwijst naar onderzoeksgroep en naar inhoudelijke groep (het is enkel mogelijk om via de GBA sync meerdere groepen te wijzigen)
            Logisch testgeval: R2051_L04
            Verwacht Resultaat: ActieInhoud, ActieAanpassingGeldigheid en actieVerval zijn aanwezig bij de onderzoeksgroep en inhoudelijke groep(onderzoek gestart, adres wijziging)

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand R2051_wijziging_onderzoek_met_wijziging_gegeven.xls
When voor persoon 270433417 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then verantwoording acties staan in persoon

Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep     | nummer | attribuut                 | aanwezig |
| adres     | 1      | actieInhoud               | true     |
| adres     | 2      | actieInhoud               | true     |
| adres     | 2      | actieAanpassingGeldigheid | true     |
| onderzoek | 2      | actieInhoud               | true     |
| onderzoek | 4      | actieInhoud               | true     |

Given de gehele database is gereset

