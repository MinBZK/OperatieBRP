Meta:

@status             Klaar
@usecase            BV.0.GD
@sleutelwoorden     Bevraging GeefDetailsPersoon
@regels             R2224
@regelversie        6

Narrative:
Indien in het bericht

Bericht.Historievorm de waarde "Geen" heeft

dan worden alleen de gegevens die Geldig (R2129) waren op Bericht.Peilmoment materieel resultaat
zoals in het systeem bekend op Bericht.Peilmoment formeel resultaat geleverd.

Indien één of beide peilmomenten leeg zijn (of niet voorkomen in het bericht) dan wordt 'Systeemdatum' (R2016) als peilmoment(en) gebruikt.

Indien er sprake is van Datum (deels) onbekend (R1273) dan moet de R1283 - Vergelijken (partiële) datums uitgevoerd worden.

Scenario: 1.   Historievorm = Leeg, Peilmoment materieel resultaat is LEEG, Peilmoment Formeel resultaat is LEEG
                LT: R2401_LT01, R2224_LT10
                Verwacht Resultaat
                - Oosterhout in het bericht
                - Utrecht in het bericht
                - 's-Gravenhage in het bericht
                - Uithoorn in bericht
                Uitleg waarom:
                - Oosterhout materieel in resultaat doordat voorkomen ooit geldig is geweest voor Peilmoment materieel resultaat en systeemdatum
                - Oosterhout formeel in resultaat doordat tijdstipregistratie voor peilmoment formeel resultaat ligt
                - Utrecht materieel in resultaat doordat voorkomen geldig is voor systeemdatum
                - Utrecht formeel in resultaat doordat tijdstipregistratie voor systeemdatum ligt
                - 's-Gravenhage materieel in resultaat doordat voorkomen geldig is voor systeemdatum
                - 's-Gravenhage formeel in resultaat doordat tijdstipregistratie voor systeemdatum ligt
                - Uithoorn materieel in resultaat doordat voorkomen geldig is voor systeemdatum

| WOONPLAATS    | DAG      | DEG      | TSREG                    |
| Oosterhout    | 20101231 | 20160000 | 2010-12-31T01:00:00.000Z |
| Utrecht       | 20160000 | 20160100 | 2016-01-01T01:00:00.000Z |
| 's-Gravenhage | 20160100 | 20160200 | 2016-01-01T01:00:00.000Z |
| Uithoorn      | 20160200 | NULL     | 2016-02-01T01:00:00.000Z |

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Anne_met_Historie2_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|986096969

Then heeft het antwoordbericht verwerking Geslaagd

Then heeft het antwoordbericht 4 groepen 'adres'
Then is er voor xpath //brp:adres[brp:woonplaatsnaam = 'Oosterhout'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:adres[brp:woonplaatsnaam = 'Utrecht'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:adres/brp:woonplaatsnaam[contains(text(),'Gravenhage')] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:adres[brp:woonplaatsnaam = 'Uithoorn'] een node aanwezig in het antwoord bericht

Then is het antwoordbericht gelijk aan Expected_R2226_Deel1/10.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario:   2. GeefDetailsPersoon service met historie vorm Leeg en peilmomentMaterieelResultaat gevuld
            LT: R2224_LT11
            Historievorm: Leeg
            peilmomentMaterieelResultaat: 2010-01-01
            peilmomentFormeelResultaat: Leeg (dus enkel voorkomens geldig op systeemdatum)
            Verwacht resultaat:
            - Alle voorkomens waarvoor geldt dat (DAG <= 2010-01-01 EN DEG > 2010-01-01)
                    EN
              (tijdstipregistratie < systeemdatum EN tijdstip verval = leeg)

!-- | WOONPLAATS    | DAG      | DEG      |                                   |
!-- | Utrecht       | 19760401 | 20100101 |                                   |
!-- | 's-Gravenhage | 20100101 | 20150101 |                                   |
!-- | Uithoorn      | 20150101 | NULL     | <-- Huidig adres want DEG is NULL |

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Anne_met_Historie_xls
!-- Bevraging persoonsgegevens met historievorm Geen en peilmomentMaterieelResultaat gevuld
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|590984809
|peilmomentMaterieelResultaat|2010-01-01

Then heeft het antwoordbericht verwerking Geslaagd

!-- 1 groep adres, want adres voor Utrecht is beeindigd per 2010-01-01, adres voor Den Haag is ingegaan per 2010-01-01
Then heeft het antwoordbericht 1 groep 'adres'
Then heeft in het antwoordbericht 'woonplaatsnaam' in 'adres' de waarde ''s-Gravenhage'

!-- Geboorte kind1 geregistreerd op 2010-01-01, Geboorte kind2 op 2015-01-01
Then heeft het antwoordbericht 3 groepen 'kind'

!-- Bij 1ste kind zijn alle 'normale' groepen aanwezig
Then is er voor xpath (//brp:kind)[2]/brp:persoon/brp:identificatienummers een node aanwezig in het antwoord bericht
Then is er voor xpath (//brp:kind)[2]/brp:persoon/brp:samengesteldeNaam een node aanwezig in het antwoord bericht
Then is er voor xpath (//brp:kind)[2]/brp:persoon/brp:geboorte een node aanwezig in het antwoord bericht

!-- Bij 2de kind zijn slechts groepen met historiepatroon 'geen' aanwezig
Then is er voor xpath (//brp:kind)[3]/brp:persoon/brp:geboorte een node aanwezig in het antwoord bericht
Then is er voor xpath (//brp:kind)[3]/brp:persoon/brp:identificatienummers geen node aanwezig in het antwoord bericht
Then is er voor xpath (//brp:kind)[3]/brp:persoon/brp:samengesteldeNaam geen node aanwezig in het antwoord bericht

!-- Huwelijk ingegaan op 2010-01-01, ontbonden op 2015-01-01
Then heeft het antwoordbericht 2 groep 'partner'

!-- Onderzoek geldig op formele historie as, maar wijst naar een actueel gegeven, die niet voorkomt in het bericht en wordt daarom niet getoond
Then is er voor xpath //brp:onderzoek geen node aanwezig in het antwoord bericht

Then is het antwoordbericht gelijk aan expected_R2224/02.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario: 3.    Historievorm = Leeg, Peilmoment materieel resultaat is datum x (2016-01-01), Peilmoment Formeel resultaat is datum y (2016-01-01)
                LT: R2224_LT12
                Verwacht resultaat:
                - Den Haag in bericht
                Uitwerking:
                - Uithoorn sinds 2016-01-02
                - Den Haag sinds 2016-01-01 (naamOpenbareRuimte = Spui)
                - Utrecht sinds  2015-21-31
                - Oosterhout sinds 2010

Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding_Haarlem

Given persoonsbeelden uit specials:specials/Anne_Bakker_xls

Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|595891305|'1900-12-31 T23:59:00Z'

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van doelbinding Haarlem'
|zendendePartijNaam|'Gemeente Haarlem'
|bsn|595891305
|peilmomentMaterieelResultaat|'2016-01-01'
|peilmomentFormeelResultaat|2016-01-01T23:59:56.223Z

Then heeft het antwoordbericht verwerking Geslaagd

Then heeft het antwoordbericht 1 groepen 'adres'

Then is er voor xpath //brp:naamOpenbareRuimte[text()='Spui'] een node aanwezig in het antwoord bericht

Then is het antwoordbericht gelijk aan expected_R2224/03.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario: 4.    Historievorm = Leeg, Peilmoment materieel resultaat is datum x (2015-12-31), Peilmoment Formeel resultaat is datum y (2015-12-31)
                LT: R2224_LT13
                Verwacht resultaat:
                - Utrecht in bericht
                Uitwerking:
                - Uithoorn sinds 2016-01-02
                - Den Haag sinds 2016-01-01
                - Utrecht sinds  2015-21-31
                - Oosterhout sinds 2010

Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding_Haarlem

Given persoonsbeelden uit specials:specials/Anne_Bakker_xls

Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|595891305|'1900-12-31 T23:59:00Z'

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van doelbinding Haarlem'
|zendendePartijNaam|'Gemeente Haarlem'
|bsn|595891305
|peilmomentMaterieelResultaat|'2015-12-31'
|peilmomentFormeelResultaat|2015-12-31T23:59:56.223Z

Then heeft het antwoordbericht verwerking Geslaagd

!-- R2225_LT01
Then heeft het antwoordbericht 1 groepen 'adres'

Then is er voor xpath //brp:woonplaatsnaam[text()='Utrecht'] een node aanwezig in het antwoord bericht

Then is het antwoordbericht gelijk aan expected_R2224/04.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario:   5. GeefDetailsPersoon service met historie vorm Leeg en peilmomentMaterieelResultaat gevuld  / peilmomentFormeelResultaat  = LEEG, PL bevat deels onbekende datum
            LT: R2224_LT14
            Historievorm: Leeg
            peilmomentMaterieelResultaat: 2016-01-31
            peilmomentFormeelResultaat: Leeg
            Verwacht resultaat:
            - Bartoklaan en Spui in het bericht
!-- Geldig voorkomens van adres op peilmoment materieel resultaat 2016-01-31
!-- Adres 4: Bertram 157,   Datum aanvang geldigheid = 2016-02-00
!-- Adres 3: Spui 43,       datum aanvang geldigheid = 2016-01-00   datum einde geldigheid = 2016-02-00
!-- Adres 2: Neude 11,      Datum aanvang geldigheid = 2016-00-00   Datum einde geldigheid = 2016-01-00
!-- Adres 1: Bartoklaan 11, Datum aanvang geldigheid = 2010-12-31   Datum einde geldigheid = 2016-00-00


Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie, autorisatie/GeefDetailsPersoon_AfnemerIndicaties_Afnemer
Given persoonsbeelden uit specials:specials/Anne_met_Historie_gedeeltelijk_onb_dat_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'geefDetailsPersoon'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|986096969
|peilmomentMaterieelResultaat|2016-01-31

Then heeft het antwoordbericht verwerking Geslaagd

Then heeft het antwoordbericht 2 groep 'adres'
Then is er voor xpath //brp:naamOpenbareRuimte[text()='Spui'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:naamOpenbareRuimte[text()='Bartoklaan'] een node aanwezig in het antwoord bericht

Then is het antwoordbericht gelijk aan expected_R2224/05.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R
!-- R2550 Actieverval is leeg, dus niet in bericht
Then is er voor xpath //brp:actieVerval geen node aanwezig in het antwoord bericht

Scenario:   6. GeefDetailsPersoon service met historie vorm Leeg en peilmomentMaterieelResultaat gevuld  / peilmomentFormeelResultaat  = LEEG, PL bevat deels onbekende datum
            LT: R2224_LT15
            Historievorm: Leeg
            peilmomentMaterieelResultaat: 2016-02-01
            peilmomentFormeelResultaat: Leeg
            Verwacht resultaat:
            - Spui en Bertram in het bericht
!-- Geldig voorkomens van adres op peilmoment materieel resultaat 2016-02-01
!-- Adres 4: Bertram 157,   Datum aanvang geldigheid = 2016-02-00
!-- Adres 3: Spui 43,       datum aanvang geldigheid = 2016-01-00   datum einde geldigheid = 2016-02-00
!-- Adres 2: Neude 11,      Datum aanvang geldigheid = 2016-00-00   Datum einde geldigheid = 2016-01-00
!-- Adres 1: Bartoklaan 11, Datum aanvang geldigheid = 2010-12-31   Datum einde geldigheid = 2016-00-00

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie, autorisatie/GeefDetailsPersoon_AfnemerIndicaties_Afnemer
Given persoonsbeelden uit specials:specials/Anne_met_Historie_gedeeltelijk_onb_dat_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'geefDetailsPersoon'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|986096969
|peilmomentMaterieelResultaat|2016-02-01

Then heeft het antwoordbericht verwerking Geslaagd

Then heeft het antwoordbericht 3 groep 'adres'
Then is er voor xpath //brp:naamOpenbareRuimte[text()='Spui'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:naamOpenbareRuimte[text()='Bertram'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:naamOpenbareRuimte[text()='Bartoklaan'] een node aanwezig in het antwoord bericht

Then is het antwoordbericht gelijk aan expected_R2224/06.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R
!-- R2550 Actieverval is leeg, dus niet in bericht
Then is er voor xpath //brp:actieVerval geen node aanwezig in het antwoord bericht

Scenario:   7. GeefDetailsPersoon service met historie vorm Leeg, PL bevat geheel onbekende datum
            LT: R2224_LT16
            Historievorm: Leeg
            peilmomentMaterieelResultaat: 2005-01-01
            peilmomentFormeelResultaat: Leeg
            Verwacht resultaat: Enkel de actuele voorkomens bij de persoon worden geleverd.

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit oranje:DELTAVERS11/DELTAVERS11C10T110_xls

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|950878601
|peilmomentMaterieelResultaat|2005-01-01

Then heeft het antwoordbericht verwerking Geslaagd

!-- R2224_LT07 Voorkomens met onbekende datum aanvang geldigheid worden niet gefilterd door historie filter
Then is er voor xpath //brp:derdeHeeftGezag/brp:datumAanvangGeldigheid[text()='0000'] een node aanwezig in het antwoord bericht

!-- R2224_LT07 Voorkomens met onbekende datum einde geldigheid worden niet gefilterd door historie filter
Then is er voor xpath //brp:ouderlijkGezag/brp:datumEindeGeldigheid[text()='0000'] een node aanwezig in het antwoord bericht

Then is het antwoordbericht gelijk aan expected_R2224/07.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario: 8.   Historievorm = Leeg, Peilmoment materieel resultaat is Leeg, Peilmoment formeel resultaat VANDAAG
                LT: R2224_LT17
            Historievorm: Geen
            peilmomentMaterieelResultaat: LEEG
            peilmomentFormeelResultaat: VANDAAG
            Verwacht resultaat: Datum aanvang huwelijk is 2016-05-10, tijdstip registratie is huwelijk < peilmomentFormeelResultaat en tijdstip verval = leeg dus in bericht

Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding_Haarlem

Given persoonsbeelden uit BIJHOUDING:VHNL04C10T10/Personen_Libby_Thatcher_(Ingeschrevene-I/dbstate003

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van doelbinding Haarlem'
|zendendePartijNaam|'Gemeente Haarlem'
|bsn|422531881
|peilmomentFormeelResultaat|VANDAAG

Then heeft het antwoordbericht verwerking Geslaagd

Then is er voor xpath //brp:huwelijk een node aanwezig in het antwoord bericht

Then is het antwoordbericht gelijk aan expected_R2224/08.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario: 9.   Historievorm = Leeg, Peilmoment materieel resultaat is Leeg, Peilmoment formeel resultaat 2016-10-16T23:59:59.223Z
                LT: R2224_LT18
            Historievorm: Leeg
            peilmomentFormeelResultaat: 2016-10-16T23:59:59.223Z
            Verwacht resultaat:
            - Datum aanvang huwelijk is 2016-05-10,
            - tijdstip registratie van het huwelijk ligt na het peilmomentformeelresultaat dus NIET in bericht (2016-12-01)

Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding_Haarlem

Given persoonsbeelden uit BIJHOUDING:VHNL04C10T10/Personen_Libby_Thatcher_(Ingeschrevene-I/dbstate003

Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|422531881|'2014-12-31 T23:59:00Z'

Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|159247913|'2014-12-31 T23:59:00Z'

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van doelbinding Haarlem'
|zendendePartijNaam|'Gemeente Haarlem'
|bsn|422531881
|peilmomentFormeelResultaat|2016-10-16T23:59:59.223Z

Then heeft het antwoordbericht verwerking Geslaagd

Then is er voor xpath //brp:huwelijk geen node aanwezig in het antwoord bericht

Then is het antwoordbericht gelijk aan expected_R2224/09.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R
