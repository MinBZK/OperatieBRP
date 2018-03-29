Meta:
@status             Klaar
@usecase            LV.0.MB
@regels             R1542
@sleutelwoorden     Maak BRP Bericht

Narrative:
Een Identificerende groep is een voorkomen van een groep dat één of meer in een bericht voorkomende
objecttypen identificeert. Als er bijvoorbeeld iets gewijzigd is in de Betrokkenheid van een gerelateerde Ouder,
dan is het noodzakelijk om door te geven over welke Ouder het gaat. Hiervoor worden dan de Identificerende groepen van de betreffende Ouder in het mutatiebericht opgenomen, ook als die groepen zelf niet gewijzigd zijn.

Voor een persoon (of eventueel een voor een met 'Gereconstrueerde persoon na Administratieve handeling' (R1556)
gereconstrueerde Persoon) betreft dit het voorkomen zonder "DatumEindeGeldigheid" en zonder "Datum\Tijd verval"
van specifieke groepen.

Het gaat om voorkomens van de volgende soorten groepen:
Van de (Hoofd)persoon en gerelateerden met betrokkenheid Partner en Ouder:
Persoon.Identificatienummers
Persoon.Samengestelde naam
Persoon.Geboorte
Persoon.Geslachtsaanduiding

Van gerelateerden met betrokkenheid Kind:
Persoon.Identificatienummers
Persoon.Samengestelde naam
Persoon.Geboorte


Scenario: 1.    Bijschrijving Kind bij Hoofdpersoon
                LT: R1542_LT04
                Verwacht resultaat: Mutatielevering met betrokkenheid kind
                alleen identificerende groepen: Persoon.Identificatienummers
                                                Persoon.Samengestelde naam
                                                Persoon.Geboorte
                geen groep: Persoon.Geslachtsaanduiding, Persoon.Voornaam, Persoon.Geslachtsnaamcomponent, Persoon.Adres

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Anne_Bakker_GBA_Bijhouding_Bijschrijving_Kind_xls
Given afnemerindicatie op de persoon :
|bsn|leveringsautorisatieNaam|partijNaam|dienstId|tsReg
|595891305|Geen pop.bep. levering op basis van afnemerindicatie|'Gemeente Utrecht'|30|2016-07-28 T16:11:21Z

When voor persoon 595891305 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken

!--  check op alleen identificerende groepen betrokkenheid Kind
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep              | nummer | attribuut                   | aanwezig |
| persoon            | 2      | identificatienummers        | ja       |
| persoon            | 2      | samengesteldeNaam           | ja       |
| persoon            | 2      | geboorte                    | ja       |
| persoon            | 2      | geslachtsaanduiding         | nee      |
| persoon            | 2      | voornamen                   | nee      |
| persoon            | 2      | geslachtsnaamcomponenten    | nee      |
| persoon            | 2      | adressen                    | nee      |

!-- Controle op Identificerende groepen Kind zonder  DatumEindeGeldigheid en zonder Datum\Tijd verval R1542_LT02
Then is het synchronisatiebericht gelijk aan expecteds/R1542_expected_scenario_1.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 2 Beeinding Huwelijk tussen Anne Bakker en Jan Pietersen. Betrokkenheid partner op mutatielijst van Anne Bakker.
            LT: R1542_LT02
            Verwacht resultaat: Mutatielevering voor Anne Bakker met partner betrokkenheid Jan Pietersen
            alleen identificerende groepen: Persoon.Identificatienummers
                                            Persoon.Samengestelde naam
                                            Persoon.Geboorte
                                            Persoon.Geslachtsaanduiding
                                            geen groep: Persoon.Geslachtsnaamcomponent, Persoon.Adres

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Anne_Bakker_GBA_Bijhouding_Beeindiging_huwelijk_xls
Given afnemerindicatie op de persoon :
|bsn|leveringsautorisatieNaam|partijNaam|dienstId|tsReg
|595891305|Geen pop.bep. levering op basis van afnemerindicatie|'Gemeente Utrecht'|30|2016-07-28 T16:11:21Z

When voor persoon 595891305 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken

!--  check op alleen identificerende groepen betrokkenheid Partner
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep           | nummer | attribuut                   | aanwezig |
| persoon         | 2      | identificatienummers        | ja       |
| persoon         | 2      | samengesteldeNaam           | ja       |
| persoon         | 2      | geboorte                    | ja       |
| persoon         | 2      | geslachtsaanduiding         | ja       |
| persoon         | 2      | voornamen                   | nee      |
| persoon         | 2      | geslachtsnaamcomponenten    | nee      |
| persoon         | 2      | adressen                    | nee      |

!-- Controle op Identificerende groepen Partner zonder  DatumEindeGeldigheid en zonder Datum\Tijd verval R1542_LT02
Then is het synchronisatiebericht gelijk aan expecteds/R1542_expected_scenario_2.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 3     Geslachtsnaamwijziging partner van hoofdpersoon Anne Bakker.
                LT: R1542_LT03
                Verwacht resultaat: Mutatielevering voor Anne Bakker met betrokkenheid partner groep
                alleen identificerende groepen: Persoon.Identificatienummers
                                                Persoon.Samengestelde naam
                                                Persoon.Geboorte
                                                Persoon.Geslachtsaanduiding
                                                geen groep: Persoon.Geslachtsnaamcomponent, Persoon.Adres

Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding
Given persoonsbeelden uit specials:specials/Ouder_ANR_Toegevoegd_xls

When voor persoon 773201993 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken

!--  check op alleen identificerende groepen betrokkenheid Ouder
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep           | nummer | attribuut                   | aanwezig |
| persoon         | 2      | identificatienummers        | ja       |
| persoon         | 2      | samengesteldeNaam           | ja       |
| persoon         | 2      | geboorte                    | ja       |
| persoon         | 2      | geslachtsaanduiding         | ja       |
| persoon         | 2      | voornamen                   | nee      |
| persoon         | 2      | geslachtsnaamcomponenten    | nee      |
| persoon         | 2      | adressen                    | nee      |

!-- Controle op Identificerende groepen Ouder zonder  DatumEindeGeldigheid en zonder Datum\Tijd verval R1542_LT02
Then is het synchronisatiebericht gelijk aan expecteds/R1542_expected_scenario_3.xml voor expressie //brp:lvg_synVerwerkPersoon