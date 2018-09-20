Meta:
@auteur             kedon
@status             Klaar
@usecase            LV.1.MB
@regels             R1549
@sleutelwoorden     Maak BRP bericht

Narrative:
De attributen ActieInhoud, ActieAanpassingGeldigheid en ActieVerval van een' Inhoudelijke groep' (R1540) of van een 'Onderzoeksgroep' (R1543) mogen alleen worden opgenomen in het te leveren resultaat:
Als er bij de Dienst waarvoor geleverd wordt een corresponderend voorkomen bestaat van Dienstbundel \ Groep met Dienstbundel \ Groep.Verantwoording? = 'Ja'.
OF
Als de bijhouder een ABO-partij betreft, zie hiervoor R1545 - Verplicht leveren van ABO-partij en rechtsgrond..


Scenario: 1 Dienst waarvoor geleverd wordt heeft een corresponderend voorkomen van Dienstbundel \ Groep met Dienstbundel \ Groep.Verantwoording? = 'Ja'.
            voor zowel de inhoudelijke groep als de onderzoeksgroep.
            Logisch testgeval: R1549_01
            Verwacht resultaat: mutatiebericht met vulling:
                - Actieinhoud
                - ActieaanpassingGeldigheid
                - ActieVerval
            Bevinding: JIRA ISSUE TEAMBRP-4707 ActieaanpassingGeldigheid wordt niet geleverd
Meta:
@status Bug

Given leveringsautorisatie uit /levering_autorisaties/Abo_geen_popbep_doelbinding_Haarlem
Given verzoek voor leveringsautorisatie 'Abo geen popbep doelbinding Haarlem' en partij 'Gemeente Haarlem'

Given de personen 299054457, 743274313, 228708977 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 228708977 en anr 2010486354 zonder extra gebeurtenissen

Given de persoon beschrijvingen:
UC_Kenny = Persoon.metBsn(228708977)
nieuweGebeurtenissenVoor(UC_Kenny) {
    verhuizing(partij: 'Gemeente Hillegom', aanvang: 20120514, registratieDatum: 20150514) {
                naarGemeente 'Hillegom',
                    straat: 'Dorpsstraat', nummer: 25, postcode: '2180AA', woonplaats: "Hillegom"
        }
}
slaOp(UC_Kenny)

Given de persoon beschrijvingen:
UC_Kenny = Persoon.metBsn(228708977)
nieuweGebeurtenissenVoor(UC_Kenny) {
    naamswijziging(partij: 'Gemeente Haarlem', aanvang: 20101010, toelichting:'rijmelarij', registratieDatum: 20101010) {
        geslachtsnaam([stam:'McCormick']).wordt([stam:'Southpark', voorvoegsel:'van'])
    }
}
slaOp(UC_Kenny)

Given de persoon beschrijvingen:
UC_Kenny = Persoon.metBsn(228708977)
nieuweGebeurtenissenVoor(UC_Kenny) {
    onderzoek(partij: 'Gemeente Haarlem', registratieDatum: 20111231) {
        gestartOp(aanvangsDatum:'20111231', omschrijving:'Onderzoek is gestart op huisnummer', verwachteAfhandelDatum:'2015-08-01')
        gegevensInOnderzoek('Persoon.Adres.Huisnummer')
    }
}
slaOp(UC_Kenny)

Given de persoon beschrijvingen:
UC_Kenny = Persoon.metBsn(228708977)
nieuweGebeurtenissenVoor(UC_Kenny) {
    onderzoek(partij: 'Gemeente Haarlem', registratieDatum: 20150202) {
            wijzigOnderzoek(wijzigingsDatum:'2015-02-02', omschrijving:'Casus Onderzoek gegevens gewijzigd', aanvangsDatum: '2015-01-01', verwachteAfhandelDatum: '2015-10-10')
        }
}
slaOp(UC_Kenny)

Given de persoon beschrijvingen:
UC_Kenny = Persoon.metBsn(228708977)
nieuweGebeurtenissenVoor(UC_Kenny) {
    onderzoek(partij: 'Gemeente Haarlem') {
        afgeslotenOp(eindDatum:'2016-01-01')
    }
}
slaOp(UC_Kenny)

When voor persoon 228708977 wordt de laatste handeling geleverd

Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand 9.1_R1549_Synchroniseer_Persoon.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Abo geen popbep doelbinding Haarlem is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep     | nummer | attribuut                 | aanwezig |
| adres     | 1      | actieInhoud               | ja       |
| adres     | 2      | actieInhoud               | ja       |
| adres     | 3      | actieInhoud               | ja       |
| adres     | 3      | actieVerval               | ja       |
| adres     | 2      | actieAanpassingGeldigheid | ja       |
| onderzoek | 2      | actieInhoud               | ja       |
| onderzoek | 3      | actieVerval               | ja       |

Scenario: 2 Dienst waarvoor geleverd wordt heeft een corresponderend voorkomen van Dienstbundel \ Groep met Dienstbundel \ Groep.Verantwoording? = 'Nee'.
            voor zowel de inhoudelijke groep als de onderzoeksgroep.
            Logisch testgeval: R1549_02
            Verwacht resultaat: mutatiebericht met vulling:
                - Actieinhoud NIET in bericht
                - ActieaanpassingGeldigheid NIET in bericht
                - ActieVerval NIET in bericht

Given leveringsautorisatie uit /levering_autorisaties/Levering_obv_doelbinding_Geen_Verantwoording
Given de personen 299054457, 743274313, 572309065 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 572309065 en anr 1707568082 zonder extra gebeurtenissen

Given de persoon beschrijvingen:
UC_Kenny = Persoon.metBsn(572309065)
nieuweGebeurtenissenVoor(UC_Kenny) {
    verhuizing(partij: 'Gemeente Hillegom', aanvang: 20120514, registratieDatum: 20120514) {
                naarGemeente 'Hillegom',
                    straat: 'Dorpsstraat', nummer: 25, postcode: '2180AA', woonplaats: "Hillegom"
        }
}
slaOp(UC_Kenny)

Given de persoon beschrijvingen:
UC_Kenny = Persoon.metBsn(572309065)
nieuweGebeurtenissenVoor(UC_Kenny) {
    naamswijziging(partij: 'Gemeente Haarlem', aanvang: 20101011, toelichting:'rijmelarij', registratieDatum: 20101011) {
        geslachtsnaam([stam:'McCormick']).wordt([stam:'Southpark', voorvoegsel:'van'])
    }
}
slaOp(UC_Kenny)

Given de persoon beschrijvingen:
UC_Kenny = Persoon.metBsn(572309065)
nieuweGebeurtenissenVoor(UC_Kenny) {
    onderzoek(partij: 'Gemeente Haarlem') {
        gestartOp(aanvangsDatum:'20111231', omschrijving:'Onderzoek is gestart op huisnummer', verwachteAfhandelDatum:'2015-08-01')
        gegevensInOnderzoek('Persoon.Adres.Huisnummer')
    }
}
slaOp(UC_Kenny)

Given de persoon beschrijvingen:
UC_Kenny = Persoon.metBsn(572309065)
nieuweGebeurtenissenVoor(UC_Kenny) {
    onderzoek(partij: 'Gemeente Haarlem', registratieDatum: 20111231) {
        gestartOp(aanvangsDatum:'20111231', omschrijving:'Onderzoek is gestart op postcode', verwachteAfhandelDatum:'2015-08-01')
        gegevensInOnderzoek('Persoon.Adres.Postcode')
    }
}
slaOp(UC_Kenny)

When voor persoon 572309065 wordt de laatste handeling geleverd

When het mutatiebericht voor leveringsautorisatie levering obv doelbinding geen verantwoording is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Given verzoek voor leveringsautorisatie 'levering obv doelbinding geen verantwoording' en partij 'Gemeente Haarlem'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand 9.2_R1549_Synchroniseer_Persoon.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
When het volledigbericht voor leveringsautorisatie levering obv doelbinding geen verantwoording is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep     | nummer | attribuut                 | aanwezig |
| adres     | 3      | actieInhoud               | nee      |
| adres     | 3      | actieVerval               | nee      |
| adres     | 3      | actieAanpassingGeldigheid | nee      |
| onderzoek | 3      | actieInhoud               | nee      |
| onderzoek | 3      | actieVerval               | nee      |
| onderzoek | 3      | actieAanpassingGeldigheid | nee      |

Scenario: 3 Dienst waarvoor geleverd wordt heeft een corresponderend voorkomen van Dienstbundel \ Groep met Dienstbundel \ Groep.Verantwoording? = 'nee'.
            voor zowel de inhoudelijke groep als de onderzoeksgroep, maar bijhouder is ABO-partij (partijrol=3)
            Logisch testgeval: R1549_03
            Verwacht resultaat: mutatiebericht met vulling:
                - Actieinhoud
                - ActieaanpassingGeldigheid
                - ActieVerval

Given leveringsautorisatie uit /levering_autorisaties/Levering_obv_doelbinding_Geen_Verantwoording


Given de personen 299054457, 743274313, 245745609 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 245745609 en anr 2809602962 zonder extra gebeurtenissen

Given de persoon beschrijvingen:
UC_Kenny = Persoon.metBsn(245745609)
nieuweGebeurtenissenVoor(UC_Kenny) {
    verhuizing(partij: 'Minister', aanvang: 20120514, registratieDatum: 20120514) {
                naarGemeente 'Hillegom',
                    straat: 'Dorpsstraat', nummer: 25, postcode: '2180AA', woonplaats: "Hillegom"
        }
}
slaOp(UC_Kenny)

Given de persoon beschrijvingen:
UC_Kenny = Persoon.metBsn(245745609)
nieuweGebeurtenissenVoor(UC_Kenny) {
    naamswijziging(partij: 'Minister', aanvang: 20101010, toelichting:'rijmelarij', registratieDatum: 20101012) {
        geslachtsnaam([stam:'McCormick']).wordt([stam:'Southpark', voorvoegsel:'van'])
    }
}
slaOp(UC_Kenny)

Given de persoon beschrijvingen:
UC_Kenny = Persoon.metBsn(245745609)
nieuweGebeurtenissenVoor(UC_Kenny) {
    onderzoek(partij: 'Minister', registratieDatum: 20111231) {
        gestartOp(aanvangsDatum:'20111231', omschrijving:'Onderzoek is gestart op huisnummer', verwachteAfhandelDatum:'2015-08-01')
        gegevensInOnderzoek('Persoon.Adres.Huisnummer')
    }
}
slaOp(UC_Kenny)

Given de persoon beschrijvingen:
UC_Kenny = Persoon.metBsn(245745609)
nieuweGebeurtenissenVoor(UC_Kenny) {
    onderzoek(partij: 'Minister', registratieDatum: 20150202) {
            wijzigOnderzoek(wijzigingsDatum:'2015-02-02', omschrijving:'Casus Onderzoek gegevens gewijzigd', aanvangsDatum: '2015-01-01', verwachteAfhandelDatum: '2015-10-10')
        }
}
slaOp(UC_Kenny)

Given de persoon beschrijvingen:
UC_Kenny = Persoon.metBsn(245745609)
nieuweGebeurtenissenVoor(UC_Kenny) {
    onderzoek(partij: 'Minister') {
        afgeslotenOp(eindDatum:'2016-01-01')
    }
}
slaOp(UC_Kenny)

When voor persoon 245745609 wordt de laatste handeling geleverd


Given verzoek voor leveringsautorisatie 'levering obv doelbinding geen verantwoording' en partij 'Gemeente Haarlem'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand 9.3_R1549_Synchroniseer_Persoon.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie levering obv doelbinding geen verantwoording is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep     | nummer | attribuut                 | aanwezig |
| adres     | 1      | actieInhoud               | ja       |
| adres     | 3      | actieVerval               | ja       |
| adres     | 2      | actieAanpassingGeldigheid | ja       |
| onderzoek | 2      | actieInhoud               | ja       |
| onderzoek | 4      | actieVerval               | ja       |
