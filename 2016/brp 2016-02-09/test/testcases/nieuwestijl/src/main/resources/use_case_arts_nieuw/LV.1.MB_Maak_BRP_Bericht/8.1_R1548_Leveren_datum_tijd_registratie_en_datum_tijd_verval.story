Meta:
@auteur             kedon
@status             Klaar
@usecase            LV.1.MB
@regels             R1548
@sleutelwoorden     Maak BRP bericht

Narrative:
De attributen Datum/tijd registratie en Datum/tijd verval van een 'Inhoudelijke groep' (R1540) of van een 'Onderzoeksgroep' (R1543)
mogen alleen worden opgenomen in het te leveren resultaat als er bij de Dienst waarvoor geleverd wordt een corresponderend voorkomen
bestaat van Dienstbundel \ Groep met Dienstbundel \ Groep.Formele historie?= 'Ja'.

NB: Nadere aanduiding verval en bijhouding beëindigd? worden (indien gevuld) bij de levering van een vervallen voorkomen altijd meegeleverd.


Scenario: 1 beëindigde Inhoudelijke groep (adres) met Nadere aanduideing verval en bijhouding beeindigd gevuld en een corresponderend voorkomen van
            dienstbundel groep, met formele historie = ja
            Logisch testgeval:  R1548_01
            Verwacht Resultaat: beëindigde Inhoudelijke groep wordt opgenomen in het te leveren bericht
                - Datum tijd registratie gevuld
                - Datum tijd verval gevuld
                - Nadere aanduiding verval gevuld


Given leveringsautorisatie uit /levering_autorisaties/Populatiebeperking_levering_op_basis_van_doelbinding_Haarlem
Given de personen 299054457, 743274313, 228708977 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 228708977 en anr 2010486354 zonder extra gebeurtenissen

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Haarlem.txt
Given bijhoudingsverzoek voor partij 'Gemeente Haarlem'
Given administratieve handeling van type verhuizingIntergemeentelijk , met de acties registratieAdres
And testdata uit bestand 8.2_R1548_verhuizing_binnen_Haarlem.yml
And de database is aangepast met: update kern.his_persadres set nadereaandverval = 'O' where persadres = (select id from kern.persadres where pers = (select id from kern.pers where bsn = 228708977 ))
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het mutatiebericht voor leveringsautorisatie Populatiebeperking levering op basis van doelbinding Haarlem is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Then heeft het bericht 3 groep 'adres'
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep   | nummer | attribuut              | aanwezig  |
| adres   | 3      | tijdstipRegistratie    | ja        |
| adres   | 3      | tijdstipVerval         | ja        |
| adres   | 3      | nadereAanduidingVerval | ja        |

Scenario: 2 beëindigde Inhoudelijke groep (adres) met Nadere aanduideing verval en bijhouding beeindigd LEEG en een corresponderend voorkomen van
            dienstbundel groep, met formele historie = ja
            Logisch testgeval:  R1548_02
            Verwacht Resultaat: beëindigde Inhoudelijke groep wordt opgenomen in het te leveren bericht
                - Datum tijd registratie gevuld
                - Datum tijd verval gevuld
                - Nadere aanduiding LEEG

Given leveringsautorisatie uit /levering_autorisaties/Populatiebeperking_levering_op_basis_van_doelbinding_Haarlem
Given de personen 299054457, 743274313, 228708977 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 228708977 en anr 2010486354 zonder extra gebeurtenissen

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Haarlem.txt
Given bijhoudingsverzoek voor partij 'Gemeente Haarlem'
Given administratieve handeling van type verhuizingIntergemeentelijk , met de acties registratieAdres
And testdata uit bestand 8.2_R1548_verhuizing_binnen_Haarlem.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het mutatiebericht voor leveringsautorisatie Populatiebeperking levering op basis van doelbinding Haarlem is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Then heeft het bericht 3 groep 'adres'
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep   | nummer | attribuut                  | aanwezig  |
| adres   | 3      | tijdstipRegistratie        | ja        |
| adres   | 3      | tijdstipVerval             | ja        |
| adres   | 3      | nadereAanduidingVerval     | nee       |
| adres   | 3      | bijhoudingBeeindigd        | nee       |


Scenario: 3 beëindigde Inhoudelijke groep (adres) met Nadere aanduideing verval en bijhouding beeindigd LEEG en een corresponderend voorkomen van
            dienstbundel groep, met formele historie = Nee
            Logisch testgeval:  R1548_03
            Verwacht Resultaat: beëindigde Inhoudelijke groep wordt opgenomen in het te leveren bericht
                - Datum tijd registratie gevuld
                - Datum tijd verval gevuld
                - Nadere aanduiding LEEG

Given leveringsautorisatie uit /levering_autorisaties/Populatiebeperking_levering_op_basis_van_doelbinding_Haarlem_Geen_Formele_historie
Given de personen 299054457, 743274313, 228708977 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 228708977 en anr 2010486354 zonder extra gebeurtenissen

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Haarlem.txt
Given bijhoudingsverzoek voor partij 'Gemeente Haarlem'
Given administratieve handeling van type verhuizingIntergemeentelijk , met de acties registratieAdres
And testdata uit bestand 8.2_R1548_verhuizing_binnen_Haarlem.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het mutatiebericht voor leveringsautorisatie Popbep levering obv doelbinding Haarlem geen formele historie is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Then heeft het bericht 3 groep 'adres'
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep   | nummer | attribuut                  | aanwezig  |
| adres   | 3      | tijdstipRegistratie        | nee       |
| adres   | 3      | tijdstipVerval             | nee       |
| adres   | 3      | nadereAanduidingVerval     | nee       |
| adres   | 3      | bijhoudingBeeindigd        | nee       |

Scenario: 4.1   Onderzoeksgroep (adres) met Nadere aanduideing verval en bijhouding beeindigd gevuld en een corresponderend voorkomen van
                dienstbundel groep, met formele historie = ja
                Logisch testgeval:  R1548_04
                Verwacht Resultaat: beëindigde Inhoudelijke groep wordt opgenomen in het te leveren bericht
                    - Datum tijd registratie gevuld
                    - Datum tijd verval gevuld
                    - Nadere aanduiding verval gevuld

Given leveringsautorisatie uit /levering_autorisaties/Populatiebeperking_levering_op_basis_van_doelbinding_Haarlem, /levering_autorisaties/Populatiebeperking_levering_op_basis_van_doelbinding_Haarlem_Geen_Formele_historie
Given de personen 299054457, 743274313, 228708977 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 228708977 en anr 2010486354 zonder extra gebeurtenissen

Given de persoon beschrijvingen:
UC_Kenny = Persoon.metBsn(228708977)
nieuweGebeurtenissenVoor(UC_Kenny) {
    onderzoek(partij: 'Gemeente Haarlem', registratieDatum: 20150102) {
        gestartOp(aanvangsDatum:'2015-01-01', omschrijving:'Onderzoek is gestart op postcode', verwachteAfhandelDatum:'2015-04-01')
        gegevensInOnderzoek('Persoon.Adres.Postcode')
    }
}
slaOp(UC_Kenny)

When voor persoon 228708977 wordt de laatste handeling geleverd

When het mutatiebericht voor leveringsautorisatie Populatiebeperking levering op basis van doelbinding Haarlem is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Scenario: 4.2   Onderzoeksgroep (adres) met Nadere aanduideing verval en bijhouding beeindigd LEEG en een corresponderend voorkomen van
                dienstbundel groep, met formele historie = ja
                Logisch testgeval:  R1548_05
                Verwacht Resultaat: beëindigde Inhoudelijke groep wordt opgenomen in het te leveren bericht
                    - Datum tijd registratie gevuld
                    - Datum tijd verval gevuld
                    - Nadere aanduiding verval LEEG

Given de persoon beschrijvingen:
UC_Kenny = Persoon.metBsn(228708977)
nieuweGebeurtenissenVoor(UC_Kenny) {
    onderzoek(partij: 'Gemeente Haarlem', registratieDatum: 20150801) {
            wijzigOnderzoek(wijzigingsDatum:'2015-08-01', omschrijving:'Wijziging onderzoek verwachte afhandel datum', aanvangsDatum: '2015-08-01', verwachteAfhandelDatum: '2015-10-10')
        }
    }
    slaOp(UC_Kenny)

When voor persoon 228708977 wordt de laatste handeling geleverd

When het mutatiebericht voor leveringsautorisatie Populatiebeperking levering op basis van doelbinding Haarlem is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep         | nummer | attribuut              | aanwezig  |
| onderzoek     | 3      | tijdstipRegistratie    | ja        |
| onderzoek     | 3      | tijdstipVerval         | ja        |
| onderzoek     | 3      | nadereAanduidingVerval | nee       |

Scenario: 5   Onderzoeksgroep (adres) met Nadere aanduideing verval en bijhouding beeindigd gevuld en een corresponderend voorkomen van
                dienstbundel groep, met formele historie = ja voor leverautorisatie Populatiebeperking levering op basis van doelbinding Haarlem
                 en met formele historie = nee  voor leverautorisatie Popbep levering obv doelbinding Haarlem geen formele historie
                Logisch testgeval:  R1548_04, R1548_06
                Verwacht Resultaat: beëindigde Inhoudelijke groep wordt opgenomen in het te leveren bericht
                    - Datum tijd registratie gevuld
                    - Datum tijd verval gevuld
                    - Nadere aanduiding verval gevuld
                Bevinding: nadereAanduidingVerval wordt niet geleverd voor onderzoeksgroepen, wanneer deze wel gevuld is
                JIRA-ISSUE: TEAMBRP-4703
Meta:
@status     Onderhanden

Given de persoon beschrijvingen:
UC_Kenny = Persoon.metBsn(228708977)
nieuweGebeurtenissenVoor(UC_Kenny) {
    onderzoek(partij: 'Gemeente Haarlem', registratieDatum: 20151111) {
            wijzigOnderzoek(wijzigingsDatum:'2015-11-11', omschrijving:'Wijziging onderzoek verwachte afhandel datum', aanvangsDatum: '2015-11-11', verwachteAfhandelDatum: '2015-12-10')
        }
    }
    slaOp(UC_Kenny)

When voor persoon 228708977 wordt de laatste handeling geleverd

Given de database is aangepast met: update kern.his_onderzoek set nadereaandverval = 'O' where verwachteafhandeldat = 20151010

When het mutatiebericht voor leveringsautorisatie Populatiebeperking levering op basis van doelbinding Haarlem is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep         | nummer | attribuut              | aanwezig  |
| onderzoek     | 3      | tijdstipRegistratie    | ja        |
| onderzoek     | 3      | tijdstipVerval         | ja        |
| onderzoek     | 3      | nadereAanduidingVerval | ja        |

When het mutatiebericht voor leveringsautorisatie Popbep levering obv doelbinding Haarlem geen formele historie is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep         | nummer | attribuut              | aanwezig   |
| onderzoek     | 3      | tijdstipRegistratie    | nee        |
| onderzoek     | 3      | tijdstipVerval         | nee        |
| onderzoek     | 3      | nadereAanduidingVerval | nee        |