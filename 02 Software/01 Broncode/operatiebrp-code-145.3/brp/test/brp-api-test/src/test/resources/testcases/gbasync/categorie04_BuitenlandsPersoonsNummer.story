Meta:
@status                 Klaar
@sleutelwoorden         GeconverteerdeDataTest

Narrative: Leveren categorie 4 (Nationaliteit) met buitenlandspersoonsnumer

Scenario: 1 NATI08C10T10_xls Initiele vulling persoon met buitenlandspersoonsnummer
             LT:
            Uitwerking: Initiele vulling persoonsgegevens met buitenlandsnummer gevuld
            Verwacht resultaat: Buitenlandspersoonsnummer voorkomen in volledigbericht

Given leveringsautorisatie uit autorisatie/autorisatiealles_bijhouder
Given persoonsbeelden uit specials:BuitenlandsPersoonsNummer/NATI08C10T10_xls

Given verzoek geef details persoon:
| key                      | value                        |
| leveringsautorisatieNaam | 'autorisatiealles bijhouder' |
| zendendePartijNaam       | 'College'                    |
| rolNaam                  | 'Bijhoudingsorgaan College'  |
| bsn                      | 749001161                    |

Then heeft het antwoordbericht verwerking Geslaagd

Then hebben attributen in het antwoordbericht in voorkomens de volgende aanwezigheid:
| groep                     | nummer | attribuut | aanwezig |
| buitenlandsPersoonsnummer | 1      | nummer    | ja       |

Then is het antwoordbericht gelijk aan expected/categorie04_BuitenlandsPersoonsnummer/01.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R


Scenario: 1a NATI08C10T10_xls Buitenlandspersoonsnummer en autorisatie zonder verantwoording en form his
             LT:
             Verwacht resultaat:
             - Geen voorkomen van Buitenlandspersoonsnummer in volledigbericht aan afnemer (attribuut autorisatie = bijhouder)
             - Geen verantwoording / historie bij voorkomen van buitenlandspersoonsnummer
             Omdat alle attributen bij het voorkomen weggefiltert worden door de autorisatie filter, geen voorkomen van Buitenlandspersoonsnummer

Given leveringsautorisatie uit autorisatie/autorisatiealles_GEEN_FV
Given persoonsbeelden uit specials:BuitenlandsPersoonsNummer/NATI08C10T10_xls

Given verzoek geef details persoon:
| key                      | value                                        |
| leveringsautorisatieNaam | 'autorisatiealles geen verant geen form his' |
| zendendePartijNaam       | 'Gemeente Utrecht'                           |
| bsn                      | 749001161                                    |

Then heeft het antwoordbericht verwerking Geslaagd

Then hebben attributen in het antwoordbericht in voorkomens de volgende aanwezigheid:
| groep   | nummer | attribuut                  | aanwezig |
| persoon | 1      | buitenlandsPersoonsnummer  | nee      |

Then is het antwoordbericht gelijk aan expected/categorie04_BuitenlandsPersoonsnummer/01A.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario: 1b NATI08C10T10_xls Buitenlandspersoonsnummer en autorisatie zonder verantwoording en form his Rol = Bijhouder
             LT:
             Verwacht resultaat:
             - Attributen met autorisatie 'Bijhouder' getoond bij voorkomen van buitenlandspersoonsnummer
             - Geen attributen bij voorkomen van buitenlandspersoonsnummer die betrekking hebben op verantwoording of formele historie

Given leveringsautorisatie uit autorisatie/autorisatiealles_GEEN_FV_BIJH
Given persoonsbeelden uit specials:BuitenlandsPersoonsNummer/NATI08C10T10_xls

Given verzoek geef details persoon:
| key                      | value                        |
| leveringsautorisatieNaam | 'autorisatiealles bijhouder geen FV' |
| zendendePartijNaam       | 'College'                    |
| rolNaam                  | 'Bijhoudingsorgaan College'  |
| bsn                      | 749001161                    |

Then heeft het antwoordbericht verwerking Geslaagd

Then hebben attributen in het antwoordbericht in voorkomens de volgende aanwezigheid:
| groep                     | nummer | attribuut | aanwezig |
| buitenlandsPersoonsnummer | 1      | nummer    | ja       |

Then is het antwoordbericht gelijk aan expected/categorie04_BuitenlandsPersoonsnummer/01B.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R


Scenario: 2 NATI08C10T10_xls Synchroniseer persoon met buitenlandspersoonsnummer
             LT:
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit specials:BuitenlandsPersoonsNummer/NATI08C10T10_xls
Given verzoek synchroniseer persoon:
| key                      | value              |
| leveringsautorisatieNaam | 'autorisatiealles' |
| zendendePartijNaam       | 'Gemeente Utrecht' |
| bsn                      | 749001161          |

Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken

Then is het synchronisatiebericht gelijk aan expected/categorie04_BuitenlandsPersoonsnummer/02.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 3 NATI08C10T10_xls Zoek persoon met buitenlandspersoonsnummer
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit specials:BuitenlandsPersoonsNummer/NATI08C10T10_xls
Given verzoek zoek persoon:
| key                      | value                                                                                         |
| leveringsautorisatieNaam | 'autorisatiealles'                                                                            |
| zendendePartijNaam       | 'Gemeente Utrecht'                                                                            |
| zoekcriteria             | ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=749001161 |

Then heeft het antwoordbericht verwerking Geslaagd

Then is het antwoordbericht gelijk aan expected/categorie04_BuitenlandsPersoonsnummer/03.xml voor expressie //brp:lvg_bvgZoekPersoon_R

Scenario: 3a NATI08C10T10_xls Zoek persoon op buitenlandspersoonsnummer als bijhouder
Given leveringsautorisatie uit autorisatie/autorisatiealles_bijhouder
Given persoonsbeelden uit specials:BuitenlandsPersoonsNummer/NATI08C10T10_xls
Given verzoek zoek persoon:
| key                      | value                                                                                     |
| leveringsautorisatieNaam | 'autorisatiealles bijhouder'                                                              |
| zendendePartijNaam       | 'College'                                                                                 |
| rolNaam                  | 'Bijhoudingsorgaan College'                                                               |
| zoekcriteria             | ZoekOptie=Exact,ElementNaam=Persoon.BuitenlandsPersoonsnummer.Nummer,Waarde=0001A00033333 |

Then heeft het antwoordbericht verwerking Geslaagd

Then is het antwoordbericht gelijk aan expected/categorie04_BuitenlandsPersoonsnummer/03A.xml voor expressie //brp:lvg_bvgZoekPersoon_R


Scenario: 4 NATI08C10T10_xls Geef medebewoners persoon met buitenlandspersoonsnummer
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit specials:BuitenlandsPersoonsNummer/NATI08C10T10_xls
Given verzoek geef medebewoners van persoon:
| key                      | value              |
| leveringsautorisatieNaam | 'autorisatiealles' |
| zendendePartijNaam       | 'Gemeente Utrecht' |
| burgerservicenummer      | '749001161'        |

Then heeft het antwoordbericht verwerking Geslaagd

Then is het antwoordbericht gelijk aan expected/categorie04_BuitenlandsPersoonsnummer/04.xml voor expressie //brp:lvg_bvgGeefMedebewoners_R


Scenario: 5 NATI08C10T10_xls Zoek persoon op adres met buitenlandspersoonsnummer
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit specials:BuitenlandsPersoonsNummer/NATI08C10T10_xls
Given verzoek zoek persoon op adres:
| key                      | value                                                            |
| leveringsautorisatieNaam | 'autorisatiealles'                                               |
| zendendePartijNaam       | 'Gemeente Utrecht'                                               |
| zoekcriteria             | ZoekOptie=Exact,ElementNaam=Persoon.Adres.Postcode,Waarde=1422RZ |

Then heeft het antwoordbericht verwerking Geslaagd

Then is het antwoordbericht gelijk aan expected/categorie04_BuitenlandsPersoonsnummer/05.xml voor expressie //brp:lvg_bvgZoekPersoonOpAdres_R

Scenario: 6 NATI08C20T30_xls Initiele vulling persoon met buitenlandspersoonsnummer
             LT:
            Verwacht resultaat:
            - Initiele vulling van persoon met vervallen voorkomen van buitenlandspersoonsnummer
            - Geautoriseerd voor groep met verantwoording en historie, dus vervallen en actueel voorkomen in volledigbericht

Given leveringsautorisatie uit autorisatie/autorisatiealles_bijhouder
Given persoonsbeelden uit specials:BuitenlandsPersoonsNummer/NATI08C20T30_xls

Given verzoek geef details persoon:
| key                      | value                        |
| leveringsautorisatieNaam | 'autorisatiealles bijhouder' |
| zendendePartijNaam       | 'College'                    |
| rolNaam                  | 'Bijhoudingsorgaan College'  |
| bsn                      | 613532041                    |

Then heeft het antwoordbericht verwerking Geslaagd

Then is het antwoordbericht gelijk aan expected/categorie04_BuitenlandsPersoonsnummer/06.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario: 7 NATI08C20T40_xls Initiele vulling persoon met buitenlandspersoonsnummer
             LT:
            Verwacht resultaat:
            - 1 actueel voorkomen van nationaliteit
            - 2 actuele voorkomens van buitenlandspersoonsnummer in bericht

Given leveringsautorisatie uit autorisatie/autorisatiealles_bijhouder
Given persoonsbeelden uit specials:BuitenlandsPersoonsNummer/NATI08C20T40_xls


Given verzoek geef details persoon:
| key                      | value                        |
| leveringsautorisatieNaam | 'autorisatiealles bijhouder' |
| zendendePartijNaam       | 'College'                    |
| rolNaam                  | 'Bijhoudingsorgaan College'  |
| bsn                      | 590636649                    |

Then heeft het antwoordbericht verwerking Geslaagd

Then is het antwoordbericht gelijk aan expected/categorie04_BuitenlandsPersoonsnummer/07.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R


Scenario: 8 NATI08C20T50_xls Initiele vulling persoon met buitenlandspersoonsnummer
             LT:
            Verwacht resultaat:
            - 2 actuele voorkomens van nationaliteit
            - 2 actuele voorkomens van buitenlandspersoonsnummer

Given leveringsautorisatie uit autorisatie/autorisatiealles_bijhouder
Given persoonsbeelden uit specials:BuitenlandsPersoonsNummer/NATI08C20T50_xls


Given verzoek geef details persoon:
| key                      | value                        |
| leveringsautorisatieNaam | 'autorisatiealles bijhouder' |
| zendendePartijNaam       | 'College'                    |
| rolNaam                  | 'Bijhoudingsorgaan College'  |
| bsn                      | 207369641                    |

Then heeft het antwoordbericht verwerking Geslaagd

Then is het antwoordbericht gelijk aan expected/categorie04_BuitenlandsPersoonsnummer/08.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario: 9 NATI08C30T10_xls Initiele vulling persoon met buitenlandspersoonsnummer
             LT:
            Uitwerking: Meerdere vervallen voorkomens van buitenlandspersoonsnummer waarvan 1 met indicatie 'Onjuist'
            Verwacht resultaat: Nadere aanduiding verval gevuld met 'O' bij voorkomen met indicatie Onjuist

Given leveringsautorisatie uit autorisatie/autorisatiealles_bijhouder
Given persoonsbeelden uit specials:BuitenlandsPersoonsNummer/NATI08C30T10_xls

Given verzoek geef details persoon:
| key                      | value                        |
| leveringsautorisatieNaam | 'autorisatiealles bijhouder' |
| zendendePartijNaam       | 'College'                    |
| rolNaam                  | 'Bijhoudingsorgaan College'  |
| bsn                      | 663483785                    |

Then heeft het antwoordbericht verwerking Geslaagd

Then is het antwoordbericht gelijk aan expected/categorie04_BuitenlandsPersoonsnummer/09.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario: 9a Vervallen voorkomen buitenlandspersoonsnummer niet in bericht
             LT:
                Uitwerking: Er is geen autorisatie voor formele historie
                Verwacht resultaat:
                - Vervallen voorkomens van buitenlandspersoonsnummer niet in bericht
                - Actuele voorkomens van buitenlandspersoonsnummer wel in bericht

Given leveringsautorisatie uit autorisatie/autorisatiealles_GEEN_FV_BIJH
Given persoonsbeelden uit specials:BuitenlandsPersoonsNummer/NATI08C30T10_xls

Given verzoek geef details persoon:
| key                      | value                        |
| leveringsautorisatieNaam | 'autorisatiealles bijhouder geen FV' |
| zendendePartijNaam       | 'College'                    |
| rolNaam                  | 'Bijhoudingsorgaan College'  |
| bsn                      | 663483785                    |

Then heeft het antwoordbericht verwerking Geslaagd

Then is het antwoordbericht gelijk aan expected/categorie04_BuitenlandsPersoonsnummer/09A.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R


Scenario: 10 NATI08C40T10_xls Initiele vulling persoon met buitenlandspersoonsnummer
             LT:
            Uitwerking: Initiele vulling waarbij Buitenlandspersoonsnummer heeft een alphanummerieke waarde van 40 karakters
            Verwachting: Buitenlandspersoonsnummer aanwezig in volledigbericht

Given leveringsautorisatie uit autorisatie/autorisatiealles_bijhouder
Given persoonsbeelden uit specials:BuitenlandsPersoonsNummer/NATI08C40T10_xls


Given verzoek geef details persoon:
| key                      | value                        |
| leveringsautorisatieNaam | 'autorisatiealles bijhouder' |
| zendendePartijNaam       | 'College'                    |
| rolNaam                  | 'Bijhoudingsorgaan College'  |
| bsn                      | 890179785                    |

Then heeft het antwoordbericht verwerking Geslaagd

Then is het antwoordbericht gelijk aan expected/categorie04_BuitenlandsPersoonsnummer/10.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R


Scenario: 11 GBA_BIJH_Toevoeging_Buitenlands_Persoonsnummer_xls
             LT:
            Uitwerking: Mutatie bericht als gevolg van toevoeging van buitenlandspersoonsnummer bij persoon
            Verwacht resultaat:
            - Object buitenlandspersoonsnummer aanwezig met verwerkingssoort 'toevoeging'
            - Attributen van voorkomen van buitenlandspersoonsnummer aanwezig (autoriteitVanAfgifteCode, nummer) incl historie en verantwoording attributen

Given leveringsautorisatie uit autorisatie/autorisatiealles_bijhouder
Given persoonsbeelden uit specials:BuitenlandsPersoonsNummer/GBA_BIJH_Toevoeging_Buitenlands_Persoonsnummer_xls

When voor persoon 207369641 wordt de laatste handeling geleverd

When het mutatiebericht voor leveringsautorisatie autorisatiealles bijhouder is ontvangen en wordt bekeken

Then is het synchronisatiebericht gelijk aan expected/categorie04_BuitenlandsPersoonsnummer/11.xml voor expressie //brp:lvg_synVerwerkPersoon


Given verzoek geef details persoon:
| key                      | value                        |
| leveringsautorisatieNaam | 'autorisatiealles bijhouder' |
| zendendePartijNaam       | 'College'                    |
| rolNaam                  | 'Bijhoudingsorgaan College'  |
| bsn                      | 207369641                    |

Then heeft het antwoordbericht verwerking Geslaagd

Then is het antwoordbericht gelijk aan expected/categorie04_BuitenlandsPersoonsnummer/11A.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R


Scenario: 12 GBA_BIJH_Actualisering_Buitenlands_Persoonsnummer_xls
             LT:
            Verwacht resultaat:
            - 3 voorkomens nationaliteit met verwerkingssoort vervallen, wijziging en toevoeging (ivm historie patroon F+M)
            - 1 voorkomen buitenlandspersoonsnummer met verwerkingssoort toevoeging
            - Het andere voorkomen van buitenlandspersoonsnummer blijft ongewijzigd (actueel) en komt dus niet mee in mutatie bericht

Given leveringsautorisatie uit autorisatie/autorisatiealles_bijhouder
Given persoonsbeelden uit specials:BuitenlandsPersoonsNummer/GBA_BIJH_Actualisering_Buitenlands_Persoonsnummer_xls

When voor persoon 207369641 wordt de laatste handeling geleverd

When het mutatiebericht voor leveringsautorisatie autorisatiealles bijhouder is ontvangen en wordt bekeken

Then is het synchronisatiebericht gelijk aan expected/categorie04_BuitenlandsPersoonsnummer/12.xml voor expressie //brp:lvg_synVerwerkPersoon

Given verzoek geef details persoon:
| key                      | value                        |
| leveringsautorisatieNaam | 'autorisatiealles bijhouder' |
| zendendePartijNaam       | 'College'                    |
| rolNaam                  | 'Bijhoudingsorgaan College'  |
| bsn                      | 207369641                    |

Then heeft het antwoordbericht verwerking Geslaagd

Then is het antwoordbericht gelijk aan expected/categorie04_BuitenlandsPersoonsnummer/12A.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R
