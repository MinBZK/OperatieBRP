Meta:
@status             Klaar
@usecase            LV.0.MB
@regels             R1549
@sleutelwoorden     Maak BRP Bericht

Narrative:
De attributen ActieInhoud, ActieAanpassingGeldigheid en ActieVerval van een 'Inhoudelijke groep' (R1540)
of van een 'Onderzoeksgroep' (R1543) mogen alleen worden opgenomen in het te leveren resultaat:

Als er bij de Dienst waarvoor geleverd wordt een corresponderend voorkomen bestaat
van Dienstbundel \ Groep met Dienstbundel \ Groep.Verantwoording? = 'Ja'.

OF

Als de bijhouder een ABO-partij betreft, zie hiervoor R1545 - Verplicht leveren van ABO-partij en rechtsgrond..

Scenario: 1.1     Dienst waarvoor geleverd wordt heeft een corresponderend voorkomen van Dienstbundel \ Groep met Dienstbundel \ Groep.Verantwoording? = 'Ja'. voor de inhoudelijke groep
                LT: R1549_LT01
                Verwacht resultaat: mutatiebericht met vulling:
                    - Actieinhoud
                    - ActieaanpassingGeldigheid
                    - ActieVerval


Given leveringsautorisatie uit autorisatie/Abo_geen_popbep_doelbinding_Haarlem
Given persoonsbeelden uit oranje:DELTAVERS08/DELTAVERS08C10T10_xls
When voor persoon 270433417 wordt de laatste handeling geleverd

When het mutatiebericht voor leveringsautorisatie Abo geen popbep doelbinding Haarlem is ontvangen en wordt bekeken
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep                  | nummer | attribuut                 | aanwezig |
| adres                  | 1      | actieInhoud               | ja       |
| adres                  | 2      | actieAanpassingGeldigheid | ja       |
| afgeleidAdministratief | 1      | actieInhoud               | ja       |
| afgeleidAdministratief | 2      | actieVerval               | ja       |

Scenario: 1.2   Dienst waarvoor geleverd wordt heeft een corresponderend voorkomen van Dienstbundel \ Groep met Dienstbundel \ Groep.Verantwoording? = 'Ja'. voor de inhoudelijke groep
                LT: R1549_LT01
                Verwacht resultaat: volledigbericht met vulling:
                    - Actieinhoud
                    - ActieaanpassingGeldigheid
                    - ActieVerval

Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'Abo geen popbep doelbinding Haarlem'
|zendendePartijNaam|'Gemeente Haarlem'
|bsn|270433417

Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Abo geen popbep doelbinding Haarlem is ontvangen en wordt bekeken
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep                  | nummer | attribuut                 | aanwezig |
| adres                  | 1      | actieInhoud               | ja       |
| adres                  | 2      | actieAanpassingGeldigheid | ja       |
| afgeleidAdministratief | 1      | actieInhoud               | ja       |
| afgeleidAdministratief | 2      | actieVerval               | ja       |

Scenario: 2.1   Dienst waarvoor geleverd wordt heeft een corresponderend voorkomen van Dienstbundel \ Groep met Dienstbundel \ Groep.Verantwoording? = 'Nee'. voor de inhoudelijke groep
                LT: R1549_LT02, R1545_LT02

                Verwacht resultaat: mutatiebericht met vulling:
                    - GEEN Actieinhoud
                    - GEEN ActieaanpassingGeldigheid
                    - GEEN ActieVerval

Given leveringsautorisatie uit autorisatie/Levering_obv_doelbinding_Geen_Verantwoording
Given persoonsbeelden uit oranje:DELTAVERS08/DELTAVERS08C10T10_xls
When voor persoon 270433417 wordt de laatste handeling geleverd

When het mutatiebericht voor leveringsautorisatie levering obv doelbinding geen verantwoording is ontvangen en wordt bekeken
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep                  | nummer | attribuut                 | aanwezig |
| adres                  | 1      | actieInhoud               | nee      |
| adres                  | 2      | actieAanpassingGeldigheid | nee      |
| afgeleidAdministratief | 1      | actieInhoud               | nee      |
| afgeleidAdministratief | 2      | actieVerval               | nee      |

Scenario:   2.2 Dienst geefDetailsPersoon ActieInhoud, ActieAanpassingGeldigheid en ActieVerval niet leveren
            LT: R1545_LT02
            Verwacht resultaat:
            - Antwoordbericht zonder ActieInhoud, ActieAanpassingGeldigheid en ActieVerval
            Uitwerking:
            - Bijhouding is niet gedaan door Bijhoudingsorgaan Minister, in de leveringsautorisatie is geen verantwoording opgenomen

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'levering obv doelbinding geen verantwoording'
|zendendePartijNaam|'Gemeente Haarlem'
|bsn|270433417

Then heeft het antwoordbericht verwerking Geslaagd
Then hebben attributen in het antwoordbericht in voorkomens de volgende aanwezigheid:
| groep                  | nummer | attribuut                 | aanwezig |
| adres                  | 1      | actieInhoud               | nee      |
| adres                  | 2      | actieAanpassingGeldigheid | nee      |
| afgeleidAdministratief | 1      | actieInhoud               | nee      |
| afgeleidAdministratief | 2      | actieVerval               | nee      |


Scenario: 2.3   Dienst waarvoor geleverd wordt heeft een corresponderend voorkomen van Dienstbundel \ Groep met Dienstbundel \ Groep.Verantwoording? = 'Nee'. voor de inhoudelijke groep
                LT: R1549_LT02, R1545_LT02
                Verwacht resultaat: volledigbericht met vulling:
                    - GEEN Actieinhoud
                    - GEEN ActieaanpassingGeldigheid
                    - GEEN ActieVerval

Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'levering obv doelbinding geen verantwoording'
|zendendePartijNaam|'Gemeente Haarlem'
|bsn|270433417

Then heeft het antwoordbericht verwerking Geslaagd
When het volledigbericht voor leveringsautorisatie levering obv doelbinding geen verantwoording is ontvangen en wordt bekeken

Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep                  | nummer | attribuut                 | aanwezig |
| adres                  | 1      | actieInhoud               | nee      |
| adres                  | 2      | actieAanpassingGeldigheid | nee      |
| afgeleidAdministratief | 1      | actieInhoud               | nee      |
| afgeleidAdministratief | 2      | actieVerval               | nee      |

Scenario: 3.1   Dienst waarvoor geleverd wordt heeft een corresponderend voorkomen van Dienstbundel \ Groep met Dienstbundel \ Groep.Verantwoording? = 'Nee'. voor de inhoudelijke groep bij ABO-partij
                LT: R1549_LT03, R1545_LT01
                Verwacht resultaat: mutatiebericht met vulling:
                    - Actieinhoud
                    - ActieaanpassingGeldigheid
                    - ActieVerval

Given leveringsautorisatie uit autorisatie/Levering_obv_doelbinding_Geen_Verantwoording
Given persoonsbeelden uit oranje:DELTARNI/DELTARNI_INITVULLING_C10T30_xls
When voor persoon 995347529 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie levering obv doelbinding geen verantwoording is ontvangen en wordt bekeken

Given persoonsbeelden uit oranje:DELTARNI/DELTARNIC10T30_xls
When voor persoon 995347529 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie levering obv doelbinding geen verantwoording is ontvangen en wordt bekeken
!-- actualisering RNI rechtsgrondomschrijving Correctie verdrag
!-- dus geen ActieAanpassingGeldigheid

Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep                    | nummer | attribuut                 | aanwezig |
| afgeleidAdministratief   | 1      | actieInhoud               | ja       |
| afgeleidAdministratief   | 2      | actieVerval               | ja       |


Scenario: 4.    Dienst waarvoor geleverd wordt heeft een corresponderend voorkomen van Dienstbundel \ Groep met Dienstbundel \ Groep.Verantwoording? = 'Ja'. voor de onderzoeksgroep
                LT: R1549_LT04
                Verwacht resultaat: mutatiebericht met vulling:
                    - Actieinhoud
                    - ActieVerval

Given leveringsautorisatie uit autorisatie/Abo_geen_popbep_doelbinding_Haarlem
Given persoonsbeelden uit specials:specials/Anne_Bakker_xls

When voor persoon 595891305 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie Abo geen popbep doelbinding Haarlem is ontvangen en wordt bekeken

Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep         | nummer | attribuut                 | aanwezig |
| onderzoek     | 2      | actieInhoud               | ja       |


Scenario: 5.    Dienst waarvoor geleverd wordt heeft een corresponderend voorkomen van Dienstbundel \ Groep met Dienstbundel \ Groep.Verantwoording? = 'Nee'. voor de onderoeksgroep
                LT: R1549_LT05
                Blob:
                Kenny wordt geboren in de doelbinding van gemeente Haarlem
                Verwacht resultaat: mutatiebericht met vulling:
                    - GEEN Actieinhoud
                    - GEEN ActieVerval

Given leveringsautorisatie uit autorisatie/Levering_obv_doelbinding_Geen_Verantwoording
Given persoonsbeelden uit specials:specials/Anne_Bakker_xls

When voor persoon 595891305 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie levering obv doelbinding geen verantwoording is ontvangen en wordt bekeken

Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep         | nummer | attribuut                 | aanwezig  |
| onderzoek     | 2      | actieInhoud               | nee       |
| onderzoek     | 2      | actieVerval               | nee       |

Scenario: 6.1   Dienst waarvoor geleverd wordt heeft een corresponderend voorkomen van Dienstbundel \ Groep met Dienstbundel \ Groep.Verantwoording? = 'Nee'.voor de onderzoeksgroep bij ABO-partij
                LT: R1549_LT06
                Verwacht resultaat: mutatiebericht met vulling:
                    - Actieinhoud
                    - ActieVerval

!-- Indien er in de administratieve handeling minimaal 1 actie aanwezig is met een rechtsgrond verwijzing
!-- Dan wordt de verantwoording verplicht meegeleverd, ook voor de onderzoek groepen

Given leveringsautorisatie uit autorisatie/Levering_obv_doelbinding_Geen_Verantwoording_wel_onderzoek
Given persoonsbeelden uit specials:MaakBericht/R1545_RNI_deelnemer_onderzoek_xls
When voor persoon 995347529 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie levering obv doelbinding geen verantwoording wel onderzoek is ontvangen en wordt bekeken

Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep              | nummer | attribuut   | aanwezig |
| onderzoek          | 2      | actieInhoud | ja       |
| gegevenInOnderzoek | 1      | actieInhoud | ja       |

Then is het synchronisatiebericht gelijk aan expecteds/R1549_expected_scenario_6_1.xml voor expressie //lvg_synVerwerkPersoon

Scenario: 7.1   Levering tijdstip registratie / actieinhoud van betrokkenheden
                LT: R1549_LT01
                Verwacht resultaat: correcte tijdstipregistratie bij betrokkenheden
                Actieinhoud, actieVerval WEL in bericht geen ABO partij

Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding_Haarlem
Given persoonsbeelden uit oranje:DELTAVERS02/DELTAVERS02C10T10_xls
When voor persoon 258096329 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding Haarlem is ontvangen en wordt bekeken
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep                  | nummer | attribuut                 | aanwezig |
| afgeleidAdministratief | 1      | actieInhoud               | ja       |
| afgeleidAdministratief | 2      | actieVerval               | ja       |

Scenario: 7.2    Levering tijdstip registratie / actieinhoud van betrokkenheden
                LT: R1549_LT02
                Verwacht resultaat: correcte tijdstipregistratie bij betrokkenheden
                Actieinhoud, actieVerval niet in bericht geen ABO partij
Given leveringsautorisatie uit autorisatie/Levering_obv_doelbinding_Geen_Verantwoording
Given persoonsbeelden uit oranje:DELTAVERS02/DELTAVERS02C10T10_xls
When voor persoon 258096329 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie levering obv doelbinding geen verantwoording is ontvangen en wordt bekeken
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep                  | nummer | attribuut                 | aanwezig |
| afgeleidAdministratief | 1      | actieInhoud               | nee      |
| afgeleidAdministratief | 2      | actieVerval               | nee      |

Scenario: 8. Bijhoudingspartij is minister
            LT:
!-- NB partijcode bij de administratieve handeling wordt gevuld met code van Migratievoorziening.
!-- De regel gaat echter uit van een partijcode bij de administratieve handeling (AdministratieveHandeling.PartijCode)
!-- Als de bijhouding door een ABO partij is opgevoerd, maar de administratieve handeling aan de migratievoorziening is gekoppeld
!-- dan worden nu niet de administratieve handelingen getoond

Given leveringsautorisatie uit autorisatie/Levering_obv_doelbinding_Geen_Verantwoording, autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding
Given persoonsbeelden uit oranje:/DELTAVERS08/DELTAVERS08C80T10g_xls

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|270433417

!-- Controleer dat verantwoording ook in zoek persoon terugkomt
Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'levering obv doelbinding geen verantwoording'
|zendendePartijNaam|'Gemeente Haarlem'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=270433417

Then is het antwoordbericht gelijk aan expecteds/R1549_expected_scenario_8_ZP.xml voor expressie //brp:lvg_bvgZoekPersoon_R

