Meta:
@status             Klaar
@usecase            LV.0.MB
@regels             R1328
@sleutelwoorden     Maak BRP bericht

Narrative:
Een bericht aan een Partij met Rol = "Afnemer" mag geen voorkomens van groepen van de gerelateerde Persoon bevatten die een geldigheidsperiode hebben die geheel voor de aanvangsdatum van de Relatie met die Persoon ligt.
Dit zijn voorkomens die voldoen aan deze voorwaarde:

Datum einde geldigheid is gevuld en is kleiner of gelijk aan aanvangsdatum van de Relatie tussen de te leveren persoon en de gerelateerde;

OF

Datum/tijd verval is gevuld en het datum-deel ervan is kleiner of gelijk aan aanvangsdatum van de Relatie tussen de te leveren persoon en de gerelateerde.

Hierbij is aanvangsdatum van de Relatie als volgt gedefinieerd:
In geval van een Huwelijk of Geregistreerd partnerschap: Relatie.Datum aanvang.
In geval van een Familierechtelijke betrekking: Ouder.Ouderschap.Datum aanvang geldigheid

Toelichting:
1.Let op: Ouder.Ouderschap.Datum aanvang geldigheid zit ofwel aan de kant van de hoofdpersoon ofwel aan de kant van de gerelateerde persoon, afhankelijk van het feit of de gerelateerde persoon een kind of een ouder is)
2.Als de aanvangsdatum van de Relatie een (deels) onbekende datum is, dan dient hier bij het filteren "streng" mee om te worden gegaan. Dat wil zeggen dat alleen groepsvoorkomens van gerelateerden worden weggefilterd waarvan zeker is dat deze voor aanvang van de relatie waren beëindigd. Zie ook regel R1283 - Vergelijken (partiële) datums.
3.In geval van meerdere Relaties of Ouder.Ouderschappen tussen dezelfde betrokkenen zal in het bericht de gerelateerde meerdere keren voor komen en er per "voorkomen" van deze gerelateerde een verschillend filterresultaat ontstaan.

Scenario:   1. DEG / TijdstipVerval < datum aanvang huwelijk, voorkomen niet leveren aan afnemer
            LT: R1328_LT01, R1328_LT04, R1328_LT06, R1328_LT11
            Verwacht resultaat:
            - Groep voornaam heeft Formele Historie, dus een TijdstipVerval
            - Groep geboorte heeft Formele / Materiele historie, dus een DEG
            - Rol = Afnemer
            - DEG van groep voornaam (1992-08-08) < DAG huwelijk (2016-05-12), dus niet getoond in volledigbericht voor Partner
            - Persoon.Naamgebruik.TijdstipVerval < GerelateerdeHuwelijkspartner.TijdstipRegistratie, dus niet in volledigbericht

Given leveringsautorisatie uit autorisatie/levering_op_basis_van_doelbinding

Given persoonsbeelden uit BIJHOUDING:VHNL04C10T30/Personen_Libby_Thatcher_met_pers_histori/dbstate003

!-- opvragen van het volledig bericht van de partner
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'levering op basis van doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|rolNaam| 'Afnemer'
|bsn|159247913

!-- R1328_LT01 DEG = Leeg bij voorkomen van samengestelde naam
Then is er voor xpath //brp:betrokkenheden/brp:partner/brp:persoon/brp:samengesteldeNaam/brp:voornamen[text()='Libby'] een node aanwezig in het antwoord bericht

!-- R1328_LT04 DEG bij samengestelde naam gevuld < datum aanvang huwelijk
Then is er voor xpath //brp:betrokkenheden/brp:partner/brp:persoon/brp:samengesteldeNaam/brp:voornamen[text()='Lobby'] geen node aanwezig in het antwoord bericht

!-- R1328_LT04 DEG bij identificatienummers gevuld < datum aanvang huwelijk
Then is er voor xpath //brp:partner/brp:persoon/brp:identificatienummers[2] geen node aanwezig in het antwoord bericht

!-- R1328_LT06 tsverval gevuld bij geboorte groep < datum aanvang huwelijk, niet in bericht aan afnemer
Then is er voor xpath //brp:partner/brp:persoon/brp:geboorte[2] geen node aanwezig in het antwoord bericht


Scenario:   2.    DEG / TijdstipVerval < datum aanvang huwelijk, voorkomen leveren aan Bijhouder
                    LT: R1328_LT12
                    Verwacht resultaat:
                    - Rol = Bijhouder
                    - DEG van groep voornaam (1992-08-08) < DAG huwelijk (2016-05-12)
                    Rol = Bijhouder, dus wordt het voorkomen van voor het huwelijk wel getoond.

Given leveringsautorisatie uit autorisatie/Mutatielevering_obv_afnemerindicatie_bijhouder
Given persoonsbeelden uit BIJHOUDING:VHNL04C10T30/Personen_Libby_Thatcher_met_pers_histori/dbstate003

Given afnemerindicatie op de persoon :
| bsn       | leveringsautorisatieNaam                         | partijNaam | datumEindeVolgen | datumAanvangMaterielePeriode | tsReg                 | dienstId |
| 159247913 | 'Mutatielevering obv afnemerindicatie Bijhouder' | 'College'  |                  |                              | 2014-01-01 T00:00:00Z | 1        |

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Mutatielevering obv afnemerindicatie Bijhouder'
|zendendePartijNaam|'College'
|rolNaam| 'Bijhoudingsorgaan College'
|bsn|159247913

Then heeft het antwoordbericht verwerking Geslaagd

!-- Rol is bijhouder, dus voorkomens met DEG / tsVerval van voor datum aanvang huwelijk worden wel geleverd
Then is er voor xpath //brp:betrokkenheden/brp:partner/brp:persoon/brp:samengesteldeNaam/brp:voornamen[text()='Libby'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:betrokkenheden/brp:partner/brp:persoon/brp:samengesteldeNaam/brp:voornamen[text()='Lobby'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:partner/brp:persoon/brp:identificatienummers[2] een node aanwezig in het antwoord bericht


Then is er voor xpath //brp:partner/brp:persoon/brp:geboorte[2] een node aanwezig in het antwoord bericht

Then is het antwoordbericht gelijk aan expecteds/R1328_expected_scenario_2.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario:   3.  DEG / TijdstipVerval > datum aanvang Huwelijk, voorkomens tonen aan afnemer
                LT:R1328_LT02
                Verwacht Resultaat:
                    - Rol = Afnemer
                    - DEG van groep voornaam (20161102) > DAG huwelijk (2016-05-12)
                    - Voorkomens met DEG / TsVerval in bericht

Given leveringsautorisatie uit autorisatie/levering_op_basis_van_doelbinding

Given persoonsbeelden uit BIJHOUDING:R1328_VHNL04C10T30/Personen_Libby_Thatcher_met_pers_histori/dbstate003

!-- opvragen van het volledig bericht van de partner
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'levering op basis van doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|rolNaam| 'Afnemer'
|bsn|159247913

!-- R1328_LT01 DEG = Leeg bij voorkomen van samengestelde naam
Then is er voor xpath //brp:betrokkenheden/brp:partner/brp:persoon/brp:samengesteldeNaam/brp:voornamen[text()='Libby'] een node aanwezig in het antwoord bericht

!-- R1328_LT04 DEG bij samengestelde naam gevuld < datum aanvang huwelijk
Then is er voor xpath //brp:betrokkenheden/brp:partner/brp:persoon/brp:samengesteldeNaam/brp:voornamen[text()='Lobby'] een node aanwezig in het antwoord bericht

!-- R1328_LT04 DEG bij identificatienummers gevuld < datum aanvang huwelijk
Then is er voor xpath //brp:partner/brp:persoon/brp:identificatienummers[2] een node aanwezig in het antwoord bericht

!-- R1328_LT06 tsverval gevuld bij geboorte groep < GerelateerdeHuwelijkspartner.TijdstipRegistratie, niet in bericht aan afnemer
Then is er voor xpath //brp:partner/brp:persoon/brp:geboorte[2] geen node aanwezig in het antwoord bericht

Then is het antwoordbericht gelijk aan expecteds/R1328_expected_scenario_3.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario:   4.  DEG / TijdstipVerval = datum aanvang huwelijk, voorkomen niet tonen aan afnemer
                LT:R1328_LT03, R1328_LT05
                Verwacht resultaat:
                Voorkomens met DEG / tsverval = datum aanvang huwelijk niet leveren aan afnemer

Given leveringsautorisatie uit autorisatie/levering_op_basis_van_doelbinding

Given persoonsbeelden uit BIJHOUDING:R1328_01_VHNL04C10T30/Personen_Libby_Thatcher_met_pers_histori/dbstate003

!-- opvragen van het volledig bericht van de partner
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'levering op basis van doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|rolNaam| 'Afnemer'
|bsn|159247913

!-- R1328_LT01 DEG = Leeg bij voorkomen van samengestelde naam
Then is er voor xpath //brp:betrokkenheden/brp:partner/brp:persoon/brp:samengesteldeNaam/brp:voornamen[text()='Libby'] een node aanwezig in het antwoord bericht

!-- R1328_LT04 DEG bij samengestelde naam gevuld = datum aanvang huwelijk
Then is er voor xpath //brp:betrokkenheden/brp:partner/brp:persoon/brp:samengesteldeNaam/brp:voornamen[text()='Lobby'] geen node aanwezig in het antwoord bericht

!-- R1328_LT04 DEG bij identificatienummers gevuld = datum aanvang huwelijk
Then is er voor xpath //brp:partner/brp:persoon/brp:identificatienummers[2] geen node aanwezig in het antwoord bericht

!-- R1328_LT06 tsverval gevuld bij geboorte groep = datum aanvang huwelijk, niet in bericht aan afnemer
Then is er voor xpath //brp:partner/brp:persoon/brp:geboorte[2] geen node aanwezig in het antwoord bericht


Scenario:   5.  DEG gedeeltelijk onbekend < datum aanvang huwelijk, voorkomen niet tonen aan afnemer
                LT:R1328_LT07
                Verwacht Resultaat:
                    - Rol = Afnemer
                    - DEG van groep voornaam (20160500) < DAG huwelijk (2016-05-12)
                    - Voorkomens met DEG niet in bericht


Given leveringsautorisatie uit autorisatie/levering_op_basis_van_doelbinding
Given persoonsbeelden uit BIJHOUDING:R1328_02_VHNL04C10T30/Personen_Libby_Thatcher_met_pers_histori/dbstate003

!-- opvragen van het volledig bericht van de partner
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'levering op basis van doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|rolNaam| 'Afnemer'
|bsn|159247913

!-- R1328_LT01 DEG = Leeg bij voorkomen van samengestelde naam
Then is er voor xpath //brp:betrokkenheden/brp:partner/brp:persoon/brp:samengesteldeNaam/brp:voornamen[text()='Libby'] een node aanwezig in het antwoord bericht

!-- R1328_LT07 DEG bij samengestelde naam gevuld deels onbekend < datum aanvang huwelijk
Then is er voor xpath //brp:betrokkenheden/brp:partner/brp:persoon/brp:samengesteldeNaam/brp:voornamen[text()='Lobby'] geen node aanwezig in het antwoord bericht

!-- R1328_LT07 DEG bij identificatienummers gevuld deels onbekend < datum aanvang huwelijk
Then is er voor xpath //brp:partner/brp:persoon/brp:identificatienummers[2] geen node aanwezig in het antwoord bericht

Then is het antwoordbericht gelijk aan expecteds/R1328_expected_scenario_5.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario:   6.  DEG deels onbekend > datum aanvang Huwelijk, voorkomens tonen aan afnemer
                LT:R1328_LT09
                Verwacht Resultaat:
                    - Rol = Afnemer
                    - DEG van groep voornaam (20160600) > DAG huwelijk (2016-05-12)
                    - Voorkomens met DEG / TsVerval in bericht

Given leveringsautorisatie uit autorisatie/levering_op_basis_van_doelbinding
Given persoonsbeelden uit BIJHOUDING:R1328_03_VHNL04C10T30/Personen_Libby_Thatcher_met_pers_histori/dbstate003

!-- opvragen van het volledig bericht van de partner
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'levering op basis van doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|rolNaam| 'Afnemer'
|bsn|159247913

!-- R1328_LT09 DEG bij identificatienummers gevuld deels onbekend > datum aanvang huwelijk
Then is er voor xpath //brp:partner/brp:persoon/brp:identificatienummers[2] een node aanwezig in het antwoord bericht

Then is het antwoordbericht gelijk aan expecteds/R1328_expected_scenario_6.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario:   7.  TSverval < TsReg Persoon.Partner.TijdstipRegistratie
                LT:R1328_LT13
                Verwacht Resultaat:
                    - Rol = Afnemer
                    - DEG van groep voornaam (2016-03-01) > DAG huwelijk (2016-01-01)
                    - Voorkomens met TsVerval < Tsreg Persoon.Partner.TijdstipRegistratie NIET  in bericht

Given leveringsautorisatie uit autorisatie/levering_op_basis_van_doelbinding

Given persoonsbeelden uit BIJHOUDING:R1328_DEG_2016/Personen_Libby_Thatcher_met_pers_histori/dbstate003

!-- opvragen van het volledig bericht van de partner
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'levering op basis van doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|rolNaam| 'Afnemer'
|bsn|159247913

!-- Voorkomen van Persoon.SamengesteldeNaam met DEG 20160301 > DAG 20160101 van huwelijk relatie opnemen in bericht
Then is er voor xpath //brp:betrokkenheden/brp:partner/brp:persoon/brp:samengesteldeNaam/brp:voornamen[text()='Libby'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:betrokkenheden/brp:partner/brp:persoon/brp:samengesteldeNaam/brp:voornamen[text()='Lobby'] een node aanwezig in het antwoord bericht


!-- Voorkomen van Persoon.Geboorte met TSverval  < TsReg Persoon.Partner.TijdstipRegistratie niet in bericht
Then is er voor xpath //brp:betrokkenheden/brp:partner/brp:persoon/brp:geboorte/brp:datum[text()='1966-08-21'] geen node aanwezig in het antwoord bericht

Then is het antwoordbericht gelijk aan expecteds/R1328_expected_scenario_7.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

!-- FIXME additionele testen opnemen voor datum aanvang geldigheid Gerelateerde ouder, DAG ouderschap, DAG geregistreerd partnerschap