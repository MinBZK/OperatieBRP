Meta:
@status             Klaar
@usecase            BV.0.GD
@sleutelwoorden     Geef Details Persoon
@regels             R2226

Narrative:
R2226
Indien in het bericht
Bericht.Historievorm de waarde "MaterieelFormeel" heeft
OF
Bericht.Historievorm is leeg (of komt niet voor in het verzoekbericht)
dan worden alle voorkomens geleverd die:
in het systeem geregistreerd waren tot en met Bericht.Peilmoment formeel resultaat
EN
Geldig (R2129) waren op enige periode tot en met Bericht.Peilmoment materieel resultaat.
Indien één of beide peilmomenten leeg zijn (of niet voorkomen in het bericht) dan wordt 'Systeemdatum' (R2016) als peilmoment(en) gebruikt.
Indien er sprake is van Datum (deels) onbekend (R1273) dan moet de R1283 - Vergelijken (partiële) datums uitgevoerd worden.
Extra note:
-         Voorkomens met historiepatroon ‘Geen’ voldoen als TsReg <= Peilmoment Formeel Resultaat
-         Voorkomens met historiepatroon ‘Formeel’ voldoen als TsReg <= Peilmoment Formeel Resultaat
-         Voorkomens met historiepatroon ‘Materieel’ voldoen als (TsReg <= Peilmoment Formeel Resultaat) EN (DAG <= Peilmoment Materieel Resultaat )
!-- Extra scenario's voor BRP bijhouding (Intergemeentelijke verhuizing)

Scenario: 28.1  Intergemeentelijke verhuizing per 2016-01-01, (tijdstipRegistratie: 2017-03-26), eerst Voorschoten vanaf 2011-03-16
                LT: R2226_LT24
                Verwacht resultaat:
                - Geen peilmomenten, dus beide adressen in bericht

Given leveringsautorisatie uit  autorisatie/autorisatiealles
Given persoonsbeelden uit BIJHOUDING:VZIG04C10T30/Verhuizing_waarbij_aangever_waarde__G__h/dbstate002
Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|465713257|2009-12-30 T23:59:00Z

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'autorisatiealles'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|465713257

Then heeft het antwoordbericht verwerking Geslaagd

!-- Oude Vervallen adres (Verval), Oude Gewijzigde adres (Gewijzigd), Nieuwe Adres (Toegevoegd)
Then heeft het antwoordbericht 3 groepen 'adres'

Scenario: 28.2  Intergemeentelijke verhuizing per 2016-01-01, (tijdstipRegistratie: 2017-03-26), eerst Voorschoten vanaf 2011-03-16
                LT: R2226_LT16
                Verwacht resultaat:
                - Beide peilmomenten voor de verhuizing, dus maar 1 adres (wel 2 voorkomens)

Given leveringsautorisatie uit  autorisatie/autorisatiealles
Given persoonsbeelden uit BIJHOUDING:VZIG04C10T30/Verhuizing_waarbij_aangever_waarde__G__h/dbstate002
Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|465713257|2009-12-30 T23:59:00Z

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'autorisatiealles'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|465713257
|peilmomentMaterieelResultaat|'2015-12-31'
|peilmomentFormeelResultaat|'2015-12-31T23:59:00'

Then heeft het antwoordbericht verwerking Geslaagd

!-- Oude Vervallen adres (Verval), Oude Gewijzigde adres (Gewijzigd)
Then heeft het antwoordbericht 2 groepen 'adres'

Scenario: 28.3  Intergemeentelijke verhuizing per 2016-01-01, (tijdstipRegistratie: 2017-03-26), eerst Voorschoten vanaf 2011-03-16
                LT: R2226_LT15
                Verwacht resultaat:
                - peilmomentMaterieelResultaat na de verhuizing, maar
                - peilmomentFormeelResultaat voor de verhuzing, dus
                - net als scenario 28.2, dus maar 1 adres (wel 2 voorkomens)

Given leveringsautorisatie uit  autorisatie/autorisatiealles
Given persoonsbeelden uit BIJHOUDING:VZIG04C10T30/Verhuizing_waarbij_aangever_waarde__G__h/dbstate002
Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|465713257|2009-12-30 T23:59:00Z

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'autorisatiealles'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|465713257
|peilmomentMaterieelResultaat|'2016-01-02'
|peilmomentFormeelResultaat|'2015-12-31T23:59:00'
|historievorm|MaterieelFormeel

Then heeft het antwoordbericht verwerking Geslaagd

!-- Oude Vervallen adres (Verval), Oude Gewijzigde adres (Gewijzigd)
Then heeft het antwoordbericht 2 groepen 'adres'

Scenario: 28.4  Intergemeentelijke verhuizing per 2016-01-01, (tijdstipRegistratie: 2017-03-26), eerst Voorschoten vanaf 2011-03-16
                LT: R2226_LT17
                Verwacht resultaat:
                - peilmomentFormeelResultaat na de verhuizing, maar
                - peilmomentMaterieelResultaat voor de verhuzing, dus
                - net als scenario 28.2, dus maar 1 adres (wel 2 voorkomens)

Given leveringsautorisatie uit  autorisatie/autorisatiealles
Given persoonsbeelden uit BIJHOUDING:VZIG04C10T30/Verhuizing_waarbij_aangever_waarde__G__h/dbstate002
Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|465713257|2009-12-30 T23:59:00Z

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'autorisatiealles'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|465713257
|peilmomentMaterieelResultaat|'2015-12-31'
|peilmomentFormeelResultaat|'2016-01-02T23:59:00'

Then heeft het antwoordbericht verwerking Geslaagd

!-- Oude Vervallen adres (Verval), Oude Gewijzigde adres (Gewijzigd)
Then heeft het antwoordbericht 2 groepen 'adres'

Scenario: 28.5  Intergemeentelijke verhuizing per 2016-01-01, (tijdstipRegistratie: 2017-03-26), eerst Voorschoten vanaf 2011-03-16
                LT: R2226_LT24
                Verwacht resultaat:
                - peilmomenten beide na verhuizing, maar
                - voor tijdstipregistratie nieuwe adres
                - Dus alleen oude adres, 2x in bericht

Given leveringsautorisatie uit  autorisatie/autorisatiealles
Given persoonsbeelden uit BIJHOUDING:VZIG04C10T30/Verhuizing_waarbij_aangever_waarde__G__h/dbstate002
Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|465713257|2009-12-30 T23:59:00Z

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'autorisatiealles'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|465713257
|peilmomentMaterieelResultaat|'2016-01-02'
|peilmomentFormeelResultaat|'2016-01-02T23:59:00'
|historievorm|MaterieelFormeel

Then heeft het antwoordbericht verwerking Geslaagd

!-- Oude Vervallen adres (Verval), Oude Gewijzigde adres (Gewijzigd)
!-- Nieuwe Adres (Toegevoegd), niet in bericht want tijdstipregistratie pas in 2017
Then heeft het antwoordbericht 2 groepen 'adres'

Scenario: 28.6  Intergemeentelijke verhuizing per 2016-01-01, (tijdstipRegistratie: 2017-03-26), eerst Voorschoten vanaf 2011-03-16
                LT: R2226_LT24
                Verwacht resultaat:
                - peilmomenten beide voor datum ingang eerste adres, dus
                - 0 groepen adres

Given leveringsautorisatie uit  autorisatie/autorisatiealles
Given persoonsbeelden uit BIJHOUDING:VZIG04C10T30/Verhuizing_waarbij_aangever_waarde__G__h/dbstate002
Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|465713257|2009-12-30 T23:59:00Z

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'autorisatiealles'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|465713257
|peilmomentMaterieelResultaat|'2010-01-02'
|peilmomentFormeelResultaat|'2010-01-02T23:59:00'

Then heeft het antwoordbericht verwerking Geslaagd
Then heeft het antwoordbericht 0 groepen 'adres'

Scenario: 28.7  Intergemeentelijke verhuizing per 2016-01-01, (tijdstipRegistratie: 2017-03-26), eerst Voorschoten vanaf 2011-03-16
                LT: R2226_LT17
                Verwacht resultaat:
                - peilmomentMaterieelResultaat voor datum ingang eerste adres, dus
                - 0 groepen adres

Given leveringsautorisatie uit  autorisatie/autorisatiealles
Given persoonsbeelden uit BIJHOUDING:VZIG04C10T30/Verhuizing_waarbij_aangever_waarde__G__h/dbstate002
Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|465713257|2009-12-30 T23:59:00Z

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'autorisatiealles'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|465713257
|peilmomentMaterieelResultaat|'2010-01-02'
|peilmomentFormeelResultaat|'2013-01-02T23:59:00'

Then heeft het antwoordbericht verwerking Geslaagd
Then heeft het antwoordbericht 0 groepen 'adres'

Scenario: 28.8  Intergemeentelijke verhuizing per 2016-01-01, (tijdstipRegistratie: 2017-03-26), eerst Voorschoten vanaf 2011-03-16
                LT: R2226_LT15
                Verwacht resultaat:
                - peilmomentFormeelResultaat voor datum ingang eerste adres, dus
                - 0 groepen adres

Given leveringsautorisatie uit  autorisatie/autorisatiealles
Given persoonsbeelden uit BIJHOUDING:VZIG04C10T30/Verhuizing_waarbij_aangever_waarde__G__h/dbstate002
Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|465713257|2009-12-30 T23:59:00Z

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'autorisatiealles'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|465713257
|peilmomentMaterieelResultaat|'2013-01-02'
|peilmomentFormeelResultaat|'2010-01-02T23:59:00'

Then heeft het antwoordbericht verwerking Geslaagd
Then heeft het antwoordbericht 0 groepen 'adres'
