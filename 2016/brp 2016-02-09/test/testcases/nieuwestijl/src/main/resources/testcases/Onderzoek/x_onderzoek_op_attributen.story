Meta:
@auteur                 dihoe
@epic                   Levering onderzoek
@jiraIssue              TEAMBRP-2571
@sleutelwoorden         onderzoek
@status                 Uitgeschakeld
@regels                 VR00116,R2063,R2065,R1563,R1962,R1973


Narrative: Stel het onderzoeksdeel samen voor een bericht

Scenario: 1. start onderzoek gelijktijdig met wijziging van het gegeven dat in onderzoek wordt geplaatst, er wordt geleverd
Casus 1

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given de personen 627129705, 304953337, 826569353 zijn verwijderd
Given de standaardpersoon Olivia met bsn 826569353 en anr 2084945042 zonder extra gebeurtenissen
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(826569353)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 59401, registratieDatum: 20150101) {
        gestartOp(aanvangsDatum:'2015-01-01', omschrijving:'Onderzoek is gestart op geboortedatum', verwachteAfhandelDatum:'2015-04-01')
        gegevensInOnderzoek('Persoon.Geboorte.Datum')
    }
}
slaOp(persoon)

When voor persoon 826569353 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then hebben attributen in voorkomens de volgende waardes:
| groep              | nummer | attribuut              | verwachteWaarde                       |
| synchronisatie     | 1      | naam                   | Aanvang onderzoek                     |
| synchronisatie     | 1      | partijCode             | 059401                                |
| onderzoek          | 2      | datumAanvang           | 2015-01-01                            |
| onderzoek          | 2      | verwachteAfhandeldatum | 2015-04-01                            |
| onderzoek          | 2      | omschrijving           | Onderzoek is gestart op geboortedatum |
| gegevenInOnderzoek | 1      | elementNaam            | Persoon.Geboorte.Datum                |
| actie              | 1      | soortNaam              | Registratie onderzoek                 |

Scenario: 2. er is een lopend onderzoek en er vindt een mutatie plaats op het gegeven in onderzoek, leveren zonder onderzoek
Casus 19

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given de personen 627129705, 304953337, 826569353 zijn verwijderd
Given de standaardpersoon Olivia met bsn 826569353 en anr 2084945042 zonder extra gebeurtenissen
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(826569353)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 59401, registratieDatum: 20150301) {
        gestartOp(aanvangsDatum:'2015-03-01', omschrijving:'Onderzoek is gestart op samengestelde naam', verwachteAfhandelDatum:'2015-12-01')
        gegevensInOnderzoek('Persoon.SamengesteldeNaam.Geslachtsnaamstam')
    }
}
slaOp(persoon)

Persoon.nieuweGebeurtenissenVoor(persoon) {
    naamswijziging(aanvang:20150601, registratieDatum: 20150601) {
            geslachtsnaam(stam:'Burton').wordt(stam:'Dalen', voorvoegsel:'van')
    }
}
slaOp(persoon)

When voor persoon 826569353 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep              | nummer | attribuut              | aanwezig |
| onderzoek          | 2      | datumAanvang           | nee      |
| onderzoek          | 2      | verwachteAfhandeldatum | nee      |
| onderzoek          | 2      | omschrijving           | nee      |
| gegevenInOnderzoek | 1      | elementNaam            | nee      |

Scenario: 3. Volledigbericht met een onderzoek op een actueel gegeven, een beëindigd gegeven en een vervallen gegeven in de persoonslijst.
Casus 44

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given de personen 627129705, 304953337, 407124585 zijn verwijderd
Given de standaardpersoon Olivia met bsn 407124585 en anr 7052794130 zonder extra gebeurtenissen
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(407124585)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 50301, registratieDatum: 20090101 ) {
        gestartOp(aanvangsDatum:'2009-01-01', omschrijving:'Onderzoek is gestart op postcode', verwachteAfhandelDatum:'2015-06-01')
        gegevensInOnderzoek('Persoon.Adres.Postcode')
    }
}
slaOp(persoon)
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(407124585)
nieuweGebeurtenissenVoor(persoon) {
    verhuizing(partij: 'Gemeente Pijnacker', aanvang: 20100312, registratieDatum: 20100312) {
        naarGemeente 'Pijnacker',
            straat: 'Fien de la Marstraat', nummer: 50, postcode: '2642BX', woonplaats: "Pijnacker"
    }
}
slaOp(persoon)
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(407124585)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 50301, registratieDatum: 20120501) {
        gestartOp(aanvangsDatum:'2012-05-01', omschrijving:'Onderzoek is gestart op huisnummer', verwachteAfhandelDatum:'2015-06-01')
        gegevensInOnderzoek('Persoon.Adres.Huisnummer')
    }
}
slaOp(persoon)
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(407124585)
nieuweGebeurtenissenVoor(persoon) {
    verhuizing(partij: 'Gemeente Delft', aanvang: 20120510, registratieDatum: 20120510) {
        naarGemeente 'Delft',
            straat: 'Aalscholverring', nummer: 30, postcode: '2623PD', woonplaats: "Delft"
    }
}
slaOp(persoon)
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(407124585)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 50301, registratieDatum: 20150101) {
        gestartOp(aanvangsDatum:'2015-01-01', omschrijving:'Onderzoek is gestart op NaamOpenbareRuimte', verwachteAfhandelDatum:'2015-06-01')
        gegevensInOnderzoek('Persoon.Adres.NaamOpenbareRuimte')
    }
}
slaOp(persoon)

Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And extra waardes:
| SLEUTEL                                 | WAARDE                                          |
| stuurgegevens.zendendePartij            | 034401                                          |
| parameters.abonnementNaam               | Geen pop.bep. levering op basis van doelbinding |
| zoekcriteriaPersoon.burgerservicenummer | 407124585                                       |

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When voor persoon 407124585 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then hebben attributen in voorkomens de volgende waardes:
| groep              | nummer | attribuut   | verwachteWaarde                  |
| gegevenInOnderzoek | 1      | elementNaam | Persoon.Adres.Postcode           |
| gegevenInOnderzoek | 2      | elementNaam | Persoon.Adres.Huisnummer         |
| gegevenInOnderzoek | 3      | elementNaam | Persoon.Adres.NaamOpenbareRuimte |

Scenario: 4. Wanneer alle attributen van een groepen in onderzoek gezet worden, dan zijn in de mutatielevering ook alle gegevens in onderzoek geleverd

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand Alle_att_binnen_groep_onderzoek.xls
When voor persoon 651919113 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then heeft het bericht 7 groepen 'gegevenInOnderzoek'

Scenario: R1973_A Start onderzoek, controleer dat de groep waar naar het gegeven in onderzoek verwijst voorkomt in het mutatie bericht
Meta:
@regels                 R1973
@jiraIssue              TEAMBRP-2573

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
Meta:
@regels                 R1973
@jiraIssue              TEAMBRP-2573

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
Meta:
@regels                 R1973
@jiraIssue              TEAMBRP-2573

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

Scenario: R1973_D. Onderzoek starten via GBA Synchornisatie, controleer dat de groep waar naar het gegeven in onderzoek verwijst voorkomt in het mutatie bericht
Meta:
@regels                 R1973
@jiraIssue              TEAMBRP-2573

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


Scenario: Casus 7. leveren, wijziging op onderzoek gegevens zelf gelijktijding met wijziging  op gegevens in onderzoek

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given alle personen zijn verwijderd
Given een sync uit bestand wijziging_onderzoek_met_wijziging_gegeven.xls
When voor persoon 270433417 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then hebben attributen in voorkomens de volgende waardes:
| groep              | nummer | attribuut              | verwachteWaarde                           |
| onderzoek          | 2      | datumAanvang           | 2011-05-01                                |
| onderzoek          | 2      | verwachteAfhandeldatum | 0000                                      |
| onderzoek          | 2      | datumEinde             | 2012-03-16                                |
| onderzoek          | 2      | omschrijving           | Conversie GBA: 081110                     |
| onderzoek          | 2      | statusNaam             | Afgesloten                                |
| gegevenInOnderzoek | 1      | elementNaam            | Persoon.Adres.AfgekorteNaamOpenbareRuimte |
| onderzoek          | 4      | datumAanvang           | 2011-05-01                                |
| onderzoek          | 4      | verwachteAfhandeldatum | 0000                                      |
| onderzoek          | 4      | omschrijving           | Conversie GBA: 081120                     |
| onderzoek          | 4      | statusNaam             | In uitvoering                             |
| gegevenInOnderzoek | 2      | elementNaam            | Persoon.Adres.Huisnummer                  |

Scenario: Casus 20a. Er is een lopend onderzoek naar een ontbrekend gegeven, Dit gegeven wordt middels een AH opgevoerd

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given de personen 627129705, 304953337, 987281161, 516503625, 875271467, 814591139 zijn verwijderd
Given de standaardpersoon Olivia met bsn 987281161 en anr 9610531602 zonder extra gebeurtenissen
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(987281161)
nieuweGebeurtenissenVoor(persoon) {
    onderzoek(partij: 34401, registratieDatum: 20150101) {
        gestartOp(aanvangsDatum:'2015-01-01', omschrijving:'Onderzoek is gestart op datum aanvang huwelijk/partnerschap en geboortedatum', verwachteAfhandelDatum:'2015-04-01')
        gegevensInOnderzoek('HuwelijkGeregistreerdPartnerschap.DatumAanvang')
    }
}
slaOp(persoon)

When voor persoon 987281161 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden

Scenario: Casus 20b. Er is een lopend onderzoek naar een ontbrekend gegeven, Dit gegeven wordt middels een AH opgevoerd, het mutatie bericht bevat geen onderzoek

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given de standaardpersoon Gregory met bsn 516503625 en anr 2372318354 zonder extra gebeurtenissen
Given de persoon beschrijvingen:
persoon = Persoon.metBsn(987281161)
def Johnny      = uitDatabase bsn: 516503625


nieuweGebeurtenissenVoor(persoon) {
    huwelijk() {
                  op 20150501 te 'Delft' gemeente 'Delft'
                  met Johnny
        }
}
slaOp(persoon)

When voor persoon 987281161 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep          | nummer | attribuut    | aanwezig |
| onderzoeken    | 1      | onderzoek    | nee      |


