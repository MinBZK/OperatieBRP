Meta:
@auteur                 aapos
@regels                 R2063,R2065,R1319,R1562,R2051,R1543,R1973
@epic                   Levering onderzoek
@sleutelwoorden         onderzoek,casus6
@status                 Klaar

Narrative: Casus 6. Een administratieve handeling die een groep in onderzoek plaatst,
           waarvoor de afnemer slechts voor een deel (attributen binnen die groep) is geautoriseerd.

Scenario: 1. Er wordt een onderzoek gestart op huisnummer en huisnummer toevoeging, vervolgens wordt het mutatie bericht bekeken met een abonnement waarvoor
             geen autorisatie aanwezig is op het attribuut huisnummer, verwacht is dat er een mutatie bericht geleverd wordt met het adres voorkomen, maar zonder huisnummer

Given leveringsautorisatie uit /levering_autorisaties/Abo_onderzoek_met_autorisatie_op_att_binnen_groep
Given de personen 627129705, 304953337, 533679849 zijn verwijderd
Given de standaardpersoon Olivia met bsn 533679849 en anr 4201576978 zonder extra gebeurtenissen
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(533679849)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 59401, registratieDatum: 20150101) {
        gestartOp(aanvangsDatum:'2015-01-01', omschrijving:'Casus 6. Onderzoek is gestart op huisnummer en toevoeging', verwachteAfhandelDatum:'2015-08-01')
        gegevensInOnderzoek('Persoon.Adres.Huisnummer')
        gegevensInOnderzoek('Persoon.Adres.Postcode')
    }

}
slaOp(persoon)

When voor persoon 533679849 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Abo onderzoek met autorisatie op att binnen groep is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: R1973_A Start onderzoek, controleer dat de groep waar naar het gegeven in onderzoek verwijst voorkomt in het mutatie bericht

Given leveringsautorisatie uit /levering_autorisaties/Abo_met_alleen_verantwoordingsinfo_True
Given de personen 627129705, 304953337, 285279385 zijn verwijderd
Given de standaardpersoon Olivia met bsn 285279385 en anr 6806784786 zonder extra gebeurtenissen

Given de persoon beschrijvingen:
persoon = Persoon.metBsn(285279385)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 59401, registratieDatum: 20150102) {
        gestartOp(aanvangsDatum:'2015-01-01', omschrijving:'Onderzoek is gestart op postcode', verwachteAfhandelDatum:'2015-04-01')
        gegevensInOnderzoek('Persoon.Adres.Postcode')
    }
}
slaOp(persoon)

When voor persoon 285279385 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Abo met alleen verantwoordingsinfo True is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep       | nummer | attribuut | aanwezig |
| onderzoeken | 1      | onderzoek | ja       |
| adressen    | 1      | adres     | ja       |


Then is er voor xpath //brp:adres[@brp:voorkomenSleutel=//brp:voorkomenSleutelGegeven[text()]] een node aanwezig in het levering bericht

Scenario: R1973_B. Onderzoek wijzigen, controleer dat de groep waar naar het gegeven in onderzoek verwijst voorkomt in het mutatie bericht

Given leveringsautorisatie uit /levering_autorisaties/Abo_met_alleen_verantwoordingsinfo_True
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(285279385)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 34401, registratieDatum: 20150802) {
            wijzigOnderzoek(wijzigingsDatum:'2015-08-01', omschrijving:'Wijziging onderzoek verwachte afhandel datum', aanvangsDatum: '2015-01-01', verwachteAfhandelDatum: '2015-10-10')
        }
    }
    slaOp(persoon)

When voor persoon 285279385 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Abo met alleen verantwoordingsinfo True is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep       | nummer | attribuut | aanwezig |
| onderzoeken | 1      | onderzoek | ja       |
| adressen    | 1      | adres     | ja       |

Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep              | nummer | attribuut           | aanwezig |
| onderzoek          | 1      | tijdstipVerval      | nee      |
| onderzoek          | 1      | tijdstipRegistratie | nee      |
| onderzoek          | 2      | tijdstipVerval      | nee      |
| onderzoek          | 2      | tijdstipRegistratie | nee      |

Then is er voor xpath //brp:adres[@brp:voorkomenSleutel=//brp:voorkomenSleutelGegeven[text()]] een node aanwezig in het levering bericht

Scenario: R1973_C. Onderzoek beeindigen, controleer dat de groep waar naar het gegeven in onderzoek verwijst voorkomt in het mutatie bericht

Given leveringsautorisatie uit /levering_autorisaties/Abo_met_alleen_verantwoordingsinfo_True
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(285279385)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 59401, registratieDatum: 20151010) {
        afgeslotenOp(eindDatum:'2015-10-09')
    }
}
slaOp(persoon)

When voor persoon 285279385 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Abo met alleen verantwoordingsinfo True is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep       | nummer | attribuut | aanwezig |
| onderzoeken | 1      | onderzoek | ja       |
| adressen    | 1      | adres     | ja       |

Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep              | nummer | attribuut           | aanwezig |
| onderzoek          | 1      | tijdstipVerval      | nee      |
| onderzoek          | 1      | tijdstipRegistratie | nee      |
| onderzoek          | 2      | tijdstipVerval      | nee      |
| onderzoek          | 2      | tijdstipRegistratie | nee      |

Then is er voor xpath //brp:adres[@brp:voorkomenSleutel=//brp:voorkomenSleutelGegeven[text()]] een node aanwezig in het levering bericht

Scenario: R1973_D. Onderzoek starten via GBA Synchronisatie, controleer dat de groep waar naar het gegeven in onderzoek verwijst voorkomt in het mutatie
 bericht

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand start_onderzoek_reisdocument.xls
When voor persoon 303899177 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep          | nummer | attribuut    | aanwezig |
| onderzoeken    | 1      | onderzoek    | ja       |
| reisdocumenten | 1      | reisdocument | ja       |

Then is er voor xpath //brp:reisdocument[@brp:voorkomenSleutel=//brp:voorkomenSleutelGegeven[text()]] een node aanwezig in het levering bericht

