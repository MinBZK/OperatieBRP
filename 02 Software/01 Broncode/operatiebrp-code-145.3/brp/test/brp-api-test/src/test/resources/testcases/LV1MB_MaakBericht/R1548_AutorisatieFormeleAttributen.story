Meta:

@status             Klaar
@usecase            LV.0.MB
@regels             R1548
@sleutelwoorden     Maak BRP bericht

Narrative:
De attributen Datum/tijd registratie en Datum/tijd verval van een 'Inhoudelijke groep' (R1540) of van een 'Onderzoeksgroep' (R1543)
mogen alleen worden opgenomen in het te leveren resultaat als er bij de Dienst waarvoor geleverd wordt een corresponderend voorkomen
bestaat van Dienstbundel \ Groep met Dienstbundel \ Groep.Formele historie?= 'Ja'.

NB: Nadere aanduiding verval en bijhouding beëindigd? worden (indien gevuld) bij de levering van een vervallen voorkomen altijd meegeleverd.

Scenario: 1. Autorisatie met formele historie op inhoudelijke groep waarbij Nadere aanduiding verval en Bijhouding beeindigd gevuld zijn
             LT:  R1548_LT01, R1548_LT03
             Verwacht Resultaat: beëindigde Inhoudelijke groep wordt opgenomen in het te leveren bericht
                - Datum tijd registratie gevuld
                - Datum tijd verval gevuld
                - Nadere aanduiding verval gevuld
                - Bijhouding Beeindigd gevuld

Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding_Haarlem,  autorisatie/Geen_FormeleHistorie
Given persoonsbeelden uit specials:MaakBericht/R1548_Persoon_Voorkomen_met_onbekende_DEG_xls

When voor persoon 963363529 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding Haarlem is ontvangen en wordt bekeken
Then heeft het bericht 3 groep 'nationaliteit'

!-- LT: R1548_01
!-- Controleer dat attributen nadereAanduidingVerval en indicatieBijhoudingBeeindigd (beide gevuld) aanwezig zijn in mutatielevering
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep         | nummer | attribuut                    | aanwezig |
| nationaliteit | 2      | tijdstipRegistratie          | ja       |
| nationaliteit | 2      | tijdstipVerval               | ja       |
| nationaliteit | 2      | nadereAanduidingVerval       | ja       |
| nationaliteit | 2      | indicatieBijhoudingBeeindigd | ja       |

When het volledigbericht voor leveringsautorisatie Geen FormeleHistorie is ontvangen en wordt bekeken
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep         | nummer | attribuut                    | aanwezig  |
| nationaliteit | 2      | tijdstipRegistratie          | nee       |
| nationaliteit | 2      | tijdstipVerval               | nee       |
| nationaliteit | 2      | nadereAanduidingVerval       | nee       |
| nationaliteit | 2      | indicatieBijhoudingBeeindigd | nee       |

Scenario: 2. beëindigde Inhoudelijke groep (adres) met Nadere aanduiding verval en bijhouding beeindigd LEEG en een corresponderend voorkomen van dienstbundel groep, met formele historie = ja
            LT:  R1548_LT02
            Verwacht Resultaat: beëindigde Inhoudelijke groep wordt opgenomen in het te leveren bericht
                - Datum tijd registratie gevuld
                - Datum tijd verval gevuld
                - Nadere aanduiding LEEG


Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding_Haarlem
Given persoonsbeelden uit oranje:DELTAVERS08/DELTAVERS08C10T10_xls
When voor persoon 270433417 wordt de laatste handeling geleverd

When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding Haarlem is ontvangen en wordt bekeken
Then heeft het bericht 3 groep 'adres'

Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep   | nummer | attribuut                  | aanwezig  |
| adres   | 3      | tijdstipRegistratie        | ja        |
| adres   | 3      | tijdstipVerval             | ja        |
| adres   | 3      | nadereAanduidingVerval     | nee       |
| adres   | 3      | bijhoudingBeeindigd        | nee       |


Scenario: 4a.    Onderzoeksgroep (adres) met Nadere aanduiding verval en bijhouding beeindigd LEEG en een corresponderend voorkomen van, dienstbundel groep, met formele historie = ja
                LT:  R1548_LT05, R1548_LT06
                Verwacht Resultaat: beëindigde Inhoudelijke groep wordt opgenomen in het te leveren bericht
                    - Datum tijd registratie gevuld
                    - Datum tijd verval gevuld
                    - Nadere aanduiding verval LEEG

Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding_Haarlem
Given persoonsbeelden uit specials:MaakBericht/R1548_ElisaBeth_vervallen_onderzoek_xls

When voor persoon 270433417 wordt de laatste handeling geleverd

!-- Controle op R1548_LT05
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding Haarlem is ontvangen en wordt bekeken
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep         | nummer | attribuut              | aanwezig  |
| onderzoek     | 2      | tijdstipRegistratie    | ja        |
| onderzoek     | 2      | tijdstipVerval         | ja        |
| onderzoek     | 2      | nadereAanduidingVerval | nee       |
!-- Controle op R1548_LT06

Then is het synchronisatiebericht gelijk aan expecteds/R1548_expected_scenario_4a.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 4b.    Onderzoeksgroep (adres) met Nadere aanduiding verval en bijhouding beeindigd LEEG en een corresponderend voorkomen van, dienstbundel groep, met formele historie = ja
                LT:  R1548_LT05, R1548_LT06
                Verwacht Resultaat: beëindigde Inhoudelijke groep wordt opgenomen in het te leveren bericht
                    - Datum tijd registratie gevuld
                    - Datum tijd verval gevuld
                    - Nadere aanduiding verval LEEG

Given leveringsautorisatie uit autorisatie/Geen_FormeleHistorie
Given persoonsbeelden uit specials:MaakBericht/R1548_ElisaBeth_vervallen_onderzoek_xls

When voor persoon 270433417 wordt de laatste handeling geleverd

When het mutatiebericht voor leveringsautorisatie Geen FormeleHistorie is ontvangen en wordt bekeken
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep         | nummer | attribuut              | aanwezig  |
| onderzoek     | 2      | tijdstipRegistratie    | nee       |
| onderzoek     | 2      | tijdstipVerval         | nee       |
| onderzoek     | 2      | nadereAanduidingVerval | nee       |

Then is het synchronisatiebericht gelijk aan expecteds/R1548_expected_scenario_4b.xml voor expressie //brp:lvg_synVerwerkPersoon