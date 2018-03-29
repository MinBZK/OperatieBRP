Meta:
@status             Klaar
@sleutelwoorden     Selectie met verwijderen afnemerindicatie
@regels             R2540

Narrative:
Indien Dienst.Soort selectie gelijk is aan 'Selectiedienst standaard'
OF
Dienst.Soort selectie gelijk is aan 'Selectiedienst met plaatsing afnemerindicatie'
DAN
Selecteer de Persoon(en) die voldoen aan de Totale populatiebeperking (R2059) en het Dienst.Selectiecriterium

Indien Dienst.Soort selectie gelijk is aan 'Selectiedienst met verwijdering afnemerindicatie'
DAN
Selecteer de Persoon(en) die voldoen aan het Dienst.Selectiecriterium

Jan
- Geboortedatum 1960-08-21
- Postcode      2252EB

Scenario:   1.      Selectie met verwijderen afnemerindicatie, Jan voldoet aan totale populatiebeperking
                    LT: R2540_LT11
                    Verwacht resultaat:
                    - afnemerindicatie verwijderd

Given leveringsautorisatie uit autorisatie/R2540/Selectie_Totale_Populatiebeperking_WAAR
Given een selectierun met de volgende selectie taken:
|id |datplanning 	|status      | dienstSleutel
|1  |vandaag 	    |Uitvoerbaar     | Selectie_Totale_Populatiebeperking_WAAR

Given persoonsbeelden uit specials:specials/Jan_xls

Given afnemerindicatie op de persoon :
|bsn|leveringsautorisatieNaam|partijNaam|dienstId|tsReg|tsReg|datumEindeVolgen
|606417801|Selectie pop bep|'Gemeente Utrecht'|30|2015-07-28 T16:11:21Z|2016-12-12 T16:11:21Z

When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar

Then is er voor persoon met bsn 606417801 en leveringautorisatie Selectie pop bep en partij Gemeente Utrecht de afnemerindicatie verwijderd

Scenario:   2.      Selectie met verwijderen afnemerindicatie, nadere selectiecriterium onwaar
                    LT: R2540_LT12
                    Verwacht resultaat:
                    - Afnemerindicatie NIET verwijderd

!-- NadereSelcriterium                  : Persoon.Geboorte.Datum E= 1959/08/21
Given leveringsautorisatie uit autorisatie/R2540/Selectie_Nadere_selectiecriterium_ONWAAR
Given een selectierun met de volgende selectie taken:
|id |datplanning 	|status      | dienstSleutel
|1  |vandaag 	    |Uitvoerbaar     | Selectie_Nadere_selectiecriterium_ONWAAR

Given persoonsbeelden uit specials:specials/Jan_xls

Given afnemerindicatie op de persoon :
|bsn|leveringsautorisatieNaam|partijNaam|dienstId|tsReg|tsReg|datumEindeVolgen
|606417801|Selectie nadere selectiecriterium onwaar|'Gemeente Utrecht'|30|2015-07-28 T16:11:21Z|2016-12-12 T16:11:21Z

When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar

Then is er voor persoon met bsn 606417801 en leveringautorisatie Selectie nadere selectiecriterium onwaar en partij Gemeente Utrecht de afnemerindicatie niet verwijderd

Scenario:   3.      Selectie met verwijderen afnemerindicatie, dienstbundel nadere populatiebeperking onwaar
                    LT: R2540_LT13
                    Verwacht resultaat:
                    - afnemerindicatie verwijderd

!-- Nadere Populatie Beperking	        : Persoon.Geboorte.Datum > 1965/12/10
Given leveringsautorisatie uit autorisatie/R2540/Selectie_Dienstbundel_Nadere_Populatiebeperking_ONWAAR
Given een selectierun met de volgende selectie taken:
|id |datplanning 	|status      | dienstSleutel
|1  |vandaag 	    |Uitvoerbaar     | Selectie_Dienstbundel_Nadere_Populatiebeperking_ONWAAR

Given persoonsbeelden uit specials:specials/Jan_xls

Given afnemerindicatie op de persoon :
|bsn|leveringsautorisatieNaam|partijNaam|dienstId|tsReg|tsReg|datumEindeVolgen
|606417801|Dienstbundel nadere pop bep onwaar|'Gemeente Utrecht'|30|2015-07-28 T16:11:21Z|2016-12-12 T16:11:21Z

When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar

Then is er voor persoon met bsn 606417801 en leveringautorisatie Dienstbundel nadere pop bep onwaar en partij Gemeente Utrecht de afnemerindicatie verwijderd

Scenario:   4.      Selectie met verwijderen afnemerindicatie, Toegang leveringsautorisatie nadere populatiebeperking onwaar
                    LT: R2540_LT14
                    Verwacht resultaat:
                    - afnemerindicatie verwijderd

!-- Nadere Populatie Beperking	        : Persoon.Geboorte.Datum > 1961/12/10
Given leveringsautorisatie uit autorisatie/R2540/Selectie_Toegang_Leveringsautorisatie_Bop_Bep_Onwaar
Given een selectierun met de volgende selectie taken:
|id |datplanning 	|status      | dienstSleutel
|1  |vandaag 	        |Uitvoerbaar     | Selectie_Toegang_Leveringsautorisatie_Bop_Bep_Onwaar

Given persoonsbeelden uit specials:specials/Jan_xls

Given afnemerindicatie op de persoon :
|bsn|leveringsautorisatieNaam|partijNaam|dienstId|tsReg|tsReg|datumEindeVolgen
|606417801|Selectie Toegang Leveringsautorisatie Bop Bep Onwaar|'Gemeente Utrecht'|30|2015-07-28 T16:11:21Z|2016-12-12 T16:11:21Z

When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar

Then is er voor persoon met bsn 606417801 en leveringautorisatie Selectie Toegang Leveringsautorisatie Bop Bep Onwaar en partij Gemeente Utrecht de afnemerindicatie verwijderd

Scenario:   5.      Selectie met verwijderen afnemerindicatie, leveringsautorisatie populatiebeperking onwaar
                    LT: R2540_LT15
                    Verwacht resultaat:
                    - afnemerindicatie verwijderd

!-- Populatie Beperking			        : AANTAL(FILTER(Adressen, a, a.Postcode >= "2270AA" EN a.Postcode <= "2280ZZ")) > 0
Given leveringsautorisatie uit autorisatie/R2540/Selectie_Leveringsautorisatie_pop_bep_Onwaar
Given een selectierun met de volgende selectie taken:
|id |datplanning 	|status      | dienstSleutel
|1  |vandaag 	        |Uitvoerbaar     | Selectie_Leveringsautorisatie_pop_bep_Onwaar

Given persoonsbeelden uit specials:specials/Jan_xls

Given afnemerindicatie op de persoon :
|bsn|leveringsautorisatieNaam|partijNaam|dienstId|tsReg|tsReg|datumEindeVolgen
|606417801|Selectie Leveringsautorisatie pop bep Onwaar|'Gemeente Utrecht'|30|2015-07-28 T16:11:21Z|2016-12-12 T16:11:21Z

When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar

Then is er voor persoon met bsn 606417801 en leveringautorisatie Selectie Leveringsautorisatie pop bep Onwaar en partij Gemeente Utrecht de afnemerindicatie verwijderd
