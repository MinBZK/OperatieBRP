Meta:
@status             Klaar
@usecase            LV.0.MB
@regels             R1973
@sleutelwoorden     Maak BRP bericht
@versie             7

Narrative:
Bij het opstellen van een mutatiebericht worden slechts groepen geleverd die
in de betreffende Administratieve handeling zijn gewijzigd;

De groep is gewijzigd in de Administratieve handeling als voldaan wordt aan minstens één van de volgende voorwaarden:

    (ActieInhoud hoort bij Administratieve handeling) EN (ActieAanpassingGeldigheid is leeg)
    ActieAanpassingGeldigheid hoort bij de Administratieve handeling
    'GecombineerdeActieVerval' (R2185) hoort bij Administratieve handeling

Indien de betreffende handeling niet de laatste is die bij de Persoon is doorgevoerd dan ga uit van de 'Gereconstrueerde persoon na Administratieve handeling' (R1556)

OF

zijn gemarkeerd als 'in onderzoek';

R1319 - Markeer gegevens in het persoonsdeel op basis van Onderzoek, zodat deze meegaan in een mutatiebericht

OF

die identificerend zijn.

De groep Identificerend is voor een andere groep indien

Het een Identificerende groep betreft (zie 'Identificerende groep' (R1542))

EN

De Identificerende groep duidelijk maakt onder welk Object de gewijzigde groep zich bevindt, als er meerdere Objecten van hetzelfde type bestaan.

In de praktijk is dat:

    De Identificerende groepen onder de hoofdpersoon zijn identificerend voor alle groepen (en worden dus altijd opgenomen)
    De Identificerende groepen onder een gerelateerde persoon zijn identificerend voor de groepen van de eigen betrokkenheid, de relatie, de gerelateerde betrokkenheid en de gerelateerde persoon (dus als er iets geraakt is rond een relatie of een gerelateerde, dan worden de identificerende groepen van die gerelateerde(n) opgenomen)

OF

die de identiteit van geleverde objecten beschrijven
De groep beschrijft de identiteit van een geleverd object als Het een groep [Objectnaam].Identiteit betreft

EN

Een andere groep uit dit object wordt opgenomen in het mutatiebericht

(Dit zijn bijvoorbeeld de 'Soort persoon' van een gerelateerde of het volgnummer van een Voornaam)

Identificerende groepen Partner en Ouder:

    Persoon.Identificatienummers
    Persoon.Samengestelde naam
    Persoon.Geboorte
    Persoon.Geslachtsaanduiding

Identificerende groepen Van gerelateerden met betrokkenheid Kind:

    Persoon.Identificatienummers
    Persoon.Samengestelde naam
    Persoon.Geboorte

Scenario: 1.    Administratieve handeling verhuizing wordt doorgevoerd op hoofdpersoon, in het pad tussen het rootobject in het bericht
                LT: R1973_LT01
                (de hoofdpersoon) en een objecttype dat gewijzigde groepen bevat. (huisnummer - adres)
                Verwacht resultaat: Mutatiebericht met vulling
                - identificerende groepen (R1542)
                - administratieve handeling
                - door de administratieve handeling geraakte groep (adres)

Given leveringsautorisatie uit autorisatie/levering_op_basis_van_doelbinding
Given persoonsbeelden uit specials:MaakBericht/R1973_ElisaBeth_Verhuizing_Haarlem_Beverwijk_xls
When voor persoon 270433417 wordt de laatste handeling geleverd

When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie levering op basis van doelbinding is ontvangen en wordt bekeken
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep   | nummer | attribuut                  | aanwezig |
| persoon | 1      | identificatienummers       | ja       |
| persoon | 1      | samengesteldeNaam          | ja       |
| persoon | 1      | geboorte                   | ja       |
| persoon | 1      | geslachtsaanduiding        | ja       |
| persoon | 1      | adressen                   | ja       |
| persoon | 1      | administratieveHandelingen | ja       |

!-- controle op afwezigheid andere groepen
Then heeft het bericht 0 groepen 'betrokkenheden'

Then is het synchronisatiebericht gelijk aan expecteds/R1973_expected_scenario_1.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 2.    Mutatie op gerelateerde persoon, gewijzigde groep bevindt zich op het pad van objecttype en de groep die deze identificeerd
                LT: R1973_LT02
                (naamswijziging vader)
                Verwacht resultaat: Mutatiebericht met vulling
                - identificerende groepen hoofdpersoon
                - identificerende groepen gerelateerde persoon (vader)
                - gemuteerde inhoudelijke groepen van gerelateerde persoon

Given leveringsautorisatie uit autorisatie/levering_op_basis_van_doelbinding
Given persoonsbeelden uit specials:MaakBericht/R1973_Anne_Bakker_GBA_Bijhouding_GeslachtsnaamWijzigingPartner_xls
When voor persoon 595891305 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie levering op basis van doelbinding is ontvangen en wordt bekeken
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep   | nummer | attribuut                  | aanwezig |
| persoon | 1      | identificatienummers       | ja       |
| persoon | 1      | samengesteldeNaam          | ja       |
| persoon | 1      | geboorte                   | ja       |
| persoon | 1      | geslachtsaanduiding        | ja       |
| persoon | 1      | administratieveHandelingen | ja       |
| persoon | 2      | identificatienummers       | ja       |
| persoon | 2      | samengesteldeNaam          | ja       |
| persoon | 2      | geboorte                   | ja       |
| persoon | 2      | geslachtsaanduiding        | ja       |

Then heeft het bericht 2 groepen 'betrokkenheden'
!-- controle op afwezigheid andere groepen
Then heeft het bericht 0 groepen 'adressen'

Then is het synchronisatiebericht gelijk aan expecteds/R1973_expected_scenario_2.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario:       3. Onderzoek gestart op adres hoofdpersoon
                LT: R1973_LT03
                 Verwacht resultaat: Mutatiebericht met vulling
                 - Verwerkingssoort administratieve handeling = toevoeging
                 - Verwerkingssoort bijgehouden persoon = wijziging
                 - identificerende groepen hoofdpersoon aanwezig
                 - adres heeft verwerkingssoort referentie
                 - Onderzoek aanwezig
                 - GEEN identificerende groepen betrokkenheid aanwezig
                 - GEEN andere groepen aanwezig

Given leveringsautorisatie uit autorisatie/levering_op_basis_van_doelbinding
Given persoonsbeelden uit specials:MaakBericht/R1973_Anne_Bakker_Onderzoek_Aanvang_xls

When voor persoon 595891305 wordt de laatste handeling geleverd

When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie levering op basis van doelbinding is ontvangen en wordt bekeken
Then heeft het bericht 2 groepen 'onderzoek'
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep   | nummer | attribuut            | aanwezig |
| persoon | 1      | identificatienummers | ja       |
| persoon | 1      | samengesteldeNaam    | ja       |
| persoon | 1      | geboorte             | ja       |
| persoon | 1      | geslachtsaanduiding  | ja       |
| persoon | 1      | geslachtsaanduiding  | ja       |

Then hebben verwerkingssoorten in voorkomens de volgende waardes:
| groep                | nummer | verwerkingssoort |
| adres                | 1      | Referentie       |
| synchronisatie       | 1      | Toevoeging       |
| persoon              | 1      | Wijziging        |
| identificatienummers | 1      | Identificatie    |
| samengesteldeNaam    | 1      | Identificatie    |
| geboorte             | 1      | Identificatie    |
| geslachtsaanduiding  | 1      | Identificatie    |

Then hebben attributen in voorkomens de volgende waardes:
| groep              | nummer | attribuut   | verwachteWaarde          |
| gegevenInOnderzoek | 1      | elementNaam | Persoon.Adres.Huisnummer |

!-- controle op afwezigheid andere groepen
Then heeft het bericht 0 groepen 'betrokkenheden'

Then is het synchronisatiebericht gelijk aan expecteds/R1973_expected_scenario_3.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 4.    Gerelateerde.Persoon niet in delta EN niet geautorisateerd
                LT: R1973_LT02
                Gerelateerde.Persoon.Identiteit wel geautoriseer
                (naamswijziging vader)
                Verwacht resultaat: Mutatiebericht met vulling
                - Gerelateerde structuur niet zichtbaar. Gerelateerde.Persoon.Identiteit wordt niet behouden omdat de
                    er geen andere groepen naast identeitgroep overblijven.

Given leveringsautorisatie uit autorisatie/R1973_identiteit
Given persoonsbeelden uit specials:MaakBericht/R1973_ElisaBeth_Verhuizing_Haarlem_Beverwijk_xls
When voor persoon 270433417 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie levering op basis van doelbinding is ontvangen en wordt bekeken
Then heeft het bericht 0 groepen 'betrokkenheden'

Then is het synchronisatiebericht gelijk aan expecteds/R1973_4.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 5.   Mutatiebericht nav wijziging voornamen kind (niet identificerend)
                LT: R1973_LT01, R1973_LT02, R1973_LT04
                Verwacht resultaat: Mutatiebericht met vulling
                Identificerende groepen aanwezig bij hoofdpersoon
                Identificerende groepen aanwezig bij kind
                Groepen geraakt door administratieve handeling aanweizg in bericht
                Groepen die de identiteit van de geleverde objecten beschrijven zijn aanwezig

Given leveringsautorisatie uit autorisatie/levering_op_basis_van_doelbinding
Given persoonsbeelden uit specials:MaakBericht/R1973_Anne_Bakker_GBA_Bijhouding_Kind_xls
When voor persoon 595891305 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie levering op basis van doelbinding is ontvangen en wordt bekeken

!-- Groepen geraakt door handeling aanwezig
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep   | nummer | attribuut    | aanwezig |
| persoon | 1      | inschrijving | ja       |

!-- Groepen niet geraakt door handeling (en niet identificeren) niet aanwezig
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep   | nummer | attribuut | aanwezig |
| persoon | 1      | voornamen | nee      |
| persoon | 1      | adressen  | nee      |

!-- Identificerende groepen aanwezig bij hoofdpersoon
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep   | nummer | attribuut            | aanwezig |
| persoon | 1      | identificatienummers | ja       |
| persoon | 1      | samengesteldeNaam    | ja       |
| persoon | 1      | geboorte             | ja       |
| persoon | 1      | geslachtsaanduiding  | ja       |

!-- Identificerende groepen aanwezig bij gerelateerde betrokkenheid Kind
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep   | nummer | attribuut            | aanwezig |
| persoon | 2      | identificatienummers | ja       |
| persoon | 2      | samengesteldeNaam    | ja       |
| persoon | 2      | geboorte             | ja       |
| persoon | 2      | geslachtsaanduiding  | nee      |

!-- Groepen die de identiteit van de geleverde objecten beschrijven zijn aanwezig
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep   | nummer | attribuut | aanwezig |
| persoon | 1      | soortCode | ja       |
| persoon | 2      | soortCode | ja       |

!-- NB Niet alle identiteit groepen worden geleverd bij de objecten
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep                        | nummer | attribuut | aanwezig |
| ouder                        | 1      | rolCode   | nee      |
| kind                         | 1      | rolCode   | nee      |
| familierechtelijkeBetrekking | 1      | soortCode | nee      |

Then is het synchronisatiebericht gelijk aan expecteds/R1973_expected_scenario_5.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 6.   Mutatiebericht nav wijziging voornamen ouder1 (niet identificerend)
                LT: R1973_LT01, R1973_LT02, R1973_LT04
                Verwacht resultaat: Mutatiebericht met vulling
                Identificerende groepen aanwezig bij hoofdpersoon
                Identificerende groepen aanwezig bij Ouder1
                Groepen geraakt door administratieve handeling aanweizg in bericht
                Groepen die de identiteit van de geleverde objecten beschrijven zijn aanwezig


Given leveringsautorisatie uit autorisatie/levering_op_basis_van_doelbinding
Given persoonsbeelden uit specials:MaakBericht/R1973_Anne_Bakker_GBA_Bijhouding_Ouder_xls
When voor persoon 595891305 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie levering op basis van doelbinding is ontvangen en wordt bekeken

!-- Groepen geraakt door handeling aanwezig (en niet identificerend)
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep   | nummer | attribuut    | aanwezig |
| persoon | 1      | inschrijving | ja       |

!-- Groepen niet geraakt door handeling (en niet identificeren) niet aanwezig
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep   | nummer | attribuut | aanwezig |
| persoon | 1      | voornamen | nee      |
| persoon | 1      | adressen  | nee      |

!-- Identificerende groepen aanwezig bij hoofdpersoon
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep   | nummer | attribuut            | aanwezig |
| persoon | 1      | identificatienummers | ja       |
| persoon | 1      | samengesteldeNaam    | ja       |
| persoon | 1      | geboorte             | ja       |
| persoon | 1      | geslachtsaanduiding  | ja       |

!-- Identificerende groepen aanwezig bij gerelateerde betrokkenheid van type Ouder
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep   | nummer | attribuut            | aanwezig |
| persoon | 2      | identificatienummers | ja       |
| persoon | 2      | samengesteldeNaam    | ja       |
| persoon | 2      | geboorte             | ja       |
| persoon | 2      | geslachtsaanduiding  | ja       |

!-- Groepen die de identiteit van de geleverde objecten beschrijven zijn aanwezig
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep   | nummer | attribuut | aanwezig |
| persoon | 1      | soortCode | ja       |
| persoon | 2      | soortCode | ja       |

!-- NB Niet alle identiteit groepen worden geleverd bij de objecten
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep                        | nummer | attribuut | aanwezig |
| partner                      | 1      | rolCode   | nee      |
| kind                         | 1      | rolCode   | nee      |
| familierechtelijkeBetrekking | 1      | soortCode | nee      |

Then is het synchronisatiebericht gelijk aan expecteds/R1973_expected_scenario_6.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 7.   Mutatiebericht nav wijziging voornamen partner (niet identificerend)
                LT: R1973_LT01, R1973_LT02, R1973_LT04
                Verwacht resultaat: Mutatiebericht met vulling
                Identificerende groepen aanwezig bij hoofdpersoon
                Identificerende groepen aanwezig bij Partner
                Groepen geraakt door administratieve handeling aanweizg in bericht
                Groepen die de identiteit van de geleverde objecten beschrijven zijn aanwezig


Given leveringsautorisatie uit autorisatie/levering_op_basis_van_doelbinding
Given persoonsbeelden uit specials:MaakBericht/R1973_Anne_Bakker_GBA_Bijhouding_Partner_xls
When voor persoon 595891305 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie levering op basis van doelbinding is ontvangen en wordt bekeken

!-- Groepen geraakt door handeling aanwezig (en niet identificerend)
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep   | nummer | attribuut    | aanwezig |
| persoon | 1      | inschrijving | ja       |

!-- Groepen niet geraakt door handeling (en niet identificeren) niet aanwezig
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep   | nummer | attribuut | aanwezig |
| persoon | 1      | voornamen | nee      |
| persoon | 1      | adressen  | nee      |

!-- Identificerende groepen aanwezig bij hoofdpersoon
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep   | nummer | attribuut            | aanwezig |
| persoon | 1      | identificatienummers | ja       |
| persoon | 1      | samengesteldeNaam    | ja       |
| persoon | 1      | geboorte             | ja       |
| persoon | 1      | geslachtsaanduiding  | ja       |

!-- Identificerende groepen aanwezig bij gerelateerde betrokkenheid van type Partner
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep   | nummer | attribuut            | aanwezig |
| persoon | 2      | identificatienummers | ja       |
| persoon | 2      | samengesteldeNaam    | ja       |
| persoon | 2      | geboorte             | ja       |
| persoon | 2      | geslachtsaanduiding  | ja       |

!-- Groepen die de identiteit van de geleverde objecten beschrijven zijn aanwezig
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep   | nummer | attribuut | aanwezig |
| persoon | 1      | soortCode | ja       |
| persoon | 2      | soortCode | ja       |

!-- NB Niet alle identiteit groepen worden geleverd bij de objecten
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep                        | nummer | attribuut | aanwezig |
| partner                      | 1      | rolCode   | nee      |
| kind                         | 1      | rolCode   | nee      |
| familierechtelijkeBetrekking | 1      | soortCode | nee      |

Then is het synchronisatiebericht gelijk aan expecteds/R1973_expected_scenario_7.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario:  8.   Mutatiebericht nav wijziging voornamen partner (niet identificerend), identificerende gegevens van partner niet geautoriseerd
                LT: R1973_LT05, R2260_LT02, R2260_LT01, R2260_LT04, R2260_LT05
                Verwacht resultaat: Mutatiebericht met vulling
                Identificerende groepen aanwezig bij hoofdpersoon
                Identificerende groepen aanwezig bij Partner
                Groepen geraakt door administratieve handeling aanweizg in bericht
                Groepen die de identiteit van de geleverde objecten beschrijven zijn aanwezig


Given leveringsautorisatie uit autorisatie/R1973_Geen_autorisatie_identiteitsgroepen_partner, autorisatie/R1973_Geen_autorisatie_groepen_partner, autorisatie/R1973_Geen_autorisatie_groep_Relatie
Given persoonsbeelden uit specials:MaakBericht/R1973_Anne_Bakker_GBA_Bijhouding_Partner_xls
When voor persoon 595891305 wordt de laatste handeling geleverd

!-- R1973_LT05 Controleer dat identiteitsgroep wegvalt als onderliggende voorkomens wegvallen ivm autorisatie filter
!-- R2260_LT02 Als het onderliggende object wegvalt en er bevinden zich geen gewijzigde voorkomens op het pad tussen de hoofdpersoon en de objecten, dan worden deze objecten niet getoond
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie Geen autorisatie identiteitsgroepen geregistreerd partner is ontvangen en wordt bekeken
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep    | nummer | attribuut      | aanwezig |
| persoon  | 2      | soortCode      | nee      |
| huwelijk | 1      | betrokkenheden | nee      |

Then is het synchronisatiebericht gelijk aan expecteds/R1973_expected_scenario_8_1.xml voor expressie //brp:lvg_synVerwerkPersoon

!-- R2260_LT01 Controleer dat objecten niet voorkomen als onderliggende voorkomens wegvallen ivm autorisatie filter
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie Geen autorisatie groepen partner is ontvangen en wordt bekeken
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep   | nummer | attribuut | aanwezig |
| huwelijk | 1      | betrokkenheden | nee      |

Then is het synchronisatiebericht gelijk aan expecteds/R1973_expected_scenario_8_2.xml voor expressie //brp:lvg_synVerwerkPersoon

!-- R2260_LT04 Objecten die zich op het pad bevinden
!-- R2260_LT05 Objecten die niet gewijzigd zijn, maar wel aanwezig zijn omdat ze zich op het pad bevinden
!-- Object huwelijk blijft staan, ondanks dat de relatie voorkomens niet geautoriseerd zijn, omdat het zich op het pad bevindt tussen de hoofdpersoon en de partner
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie Geen autorisatie groep Relatie is ontvangen en wordt bekeken
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep   | nummer | attribuut | aanwezig |
| huwelijk | 1      | betrokkenheden | ja      |

Then is het synchronisatiebericht gelijk aan expecteds/R1973_expected_scenario_8_3.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 9.   Mutatiebericht nav wijziging in de groep Persoon.Voornamen bij de hoofdpersoon.
                LT: R1973_LT01, R1973_LT02, R1973_LT04, R2260_LT01, R2260_LT03
                Verwacht resultaat: Mutatiebericht met vulling
                Identificerende groepen aanwezig bij hoofdpersoon
                Groepen geraakt door administratieve handeling aanweizg in bericht
                Groepen die de identiteit van de geleverde objecten beschrijven zijn aanwezig

Given leveringsautorisatie uit autorisatie/levering_op_basis_van_doelbinding
Given persoonsbeelden uit specials:MaakBericht/R1973_Anne_Bakker_GBA_Bijhouding_Niet_Identificerende_Groep_xls
When voor persoon 595891305 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie levering op basis van doelbinding is ontvangen en wordt bekeken

!-- Groepen geraakt door handeling aanwezig (en niet identificerend)
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep   | nummer | attribuut    | aanwezig |
| persoon | 1      | inschrijving | ja       |
| persoon | 1      | voornamen    | ja       |

!-- Groepen niet geraakt door handeling (en niet identificeren) niet aanwezig
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep   | nummer | attribuut | aanwezig |
| persoon | 1      | adressen  | nee      |

!-- Identificerende groepen aanwezig bij hoofdpersoon
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep   | nummer | attribuut            | aanwezig |
| persoon | 1      | identificatienummers | ja       |
| persoon | 1      | samengesteldeNaam    | ja       |
| persoon | 1      | geboorte             | ja       |
| persoon | 1      | geslachtsaanduiding  | ja       |

!-- R2260_LT01 Geen gerelateerde betrokkenheden in het bericht, want niet geraakt door de administratieve handeling
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep          | nummer | attribuut | aanwezig |
| betrokkenheden | 1      | partner   | nee      |
| betrokkenheden | 1      | kind      | nee      |
| betrokkenheden | 1      | ouder     | nee      |

!-- NB Niet alle identiteit groepen worden geleverd bij de objecten
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep                        | nummer | attribuut | aanwezig |
| partner                      | 1      | rolCode   | nee      |
| kind                         | 1      | rolCode   | nee      |
| familierechtelijkeBetrekking | 1      | soortCode | nee      |

!-- R2260_LT03 Object type PersoonVoornaam aanwezig (omdat zich in de groep een identiteitsgegeven bevindt????)
Then is er voor xpath //brp:voornaam[@brp:objecttype="PersoonVoornaam"] een node aanwezig in het levering bericht
Then is het synchronisatiebericht gelijk aan expecteds/R1973_expected_scenario_9.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 10. Mutatie bericht nav wijziging in de nationaliteit bij de persoon
              LT: R1973_LT01

Given leveringsautorisatie uit autorisatie/levering_op_basis_van_doelbinding
Given persoonsbeelden uit specials:MaakBericht/R1973_Anne_Bakker_GBA_Bijhouding_Nationaliteit_xls

When voor persoon 595891305 wordt de laatste handeling geleverd

When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie levering op basis van doelbinding is ontvangen en wordt bekeken

!-- Controleer dat de attributen van de "Persoon.Nationaliteit.Identiteit" correct meekomen in de voorkomens van Nationaliteit
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep         | nummer | attribuut         | aanwezig |
| nationaliteit | 1      | nationaliteitCode | ja       |
| nationaliteit | 2      | nationaliteitCode | ja       |
| nationaliteit | 3      | nationaliteitCode | ja       |

Then is het synchronisatiebericht gelijk aan expecteds/R1973_expected_scenario_10.xml voor expressie //brp:lvg_synVerwerkPersoon

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'levering op basis van doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|595891305

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan expecteds/R1973_expected_scenario_10_GDP.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R
