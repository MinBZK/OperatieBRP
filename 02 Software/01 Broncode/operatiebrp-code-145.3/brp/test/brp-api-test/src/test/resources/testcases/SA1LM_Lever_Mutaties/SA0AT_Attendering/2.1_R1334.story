Meta:
@status             Klaar
@usecase            SA.0.AT
@regels             R1334, R1983, R1991, R1993, R1994, R2016, R2057, R2060, R2062, R2129
@sleutelwoorden     Attendering, Lever mutaties

Narrative:
        Aanmaken volledigbericht.
        Indien door een administratieve handeling één of meerdere personen in de doelgroep van een abonnement
        met een geldige dienst Attendering van de afnemer vallen en er wordt voor die persoon of personen voldaan aan het attenderingscriterium,
        dan wordt een volledig bericht voor die persoon of personen aangemaakt.

        Algemeen: Kruimeltje wordt geboren met Geboortedatum 1997/11/00 en komt nieuw in de doelbinding
        van leveringsautorisatie attendering_populatiebeperking_op_basis_van_geboortedatum met populatiebeperking Persoon.Geboorte.Datum >= 1997/03/01
        waardoor de Populatiebeperking evalueert naar WAAR, het Attenderingscriterium = WAAR.

R1334:
Indien:
Eén of meer Persoon zijn bijgehouden door een Administratieve handeling
EN
Er een geldige Toegang leveringsautorisatie bestaat met een geldige Dienst van Soort dienst Attendering
EN
De expressie Totale populatiebeperking (R2059) over een bijgehouden Persoon evalueert als 'WAAR'
EN
De expressie Dienst.Attenderingscriterium over de nieuwe R1557 - Reconstructie actueel persoonsbeeld tbv expressie-evaluatie versie van die Persoon evalueert als 'WAAR'

Dan:
Wordt een Volledig bericht aangemaakt waarin de Persoon die voldoen aan de bovenstaande voorwaarden zijn opgenomen.


Scenario: 1.    Geboortedatum 1997/11/20 Populatiebeperking evalueert WAAR, Attenderingscriterium = WAAR, datum ingang abo = systeemdatum.
                LT: R1334_LT01
                Verwacht resultaat: Leveringsbericht
                    Met vulling:
                    -  Soort bericht = Volledigbericht
                    -  Persoon = De betreffende Persoon uit het bericht

Given leveringsautorisatie uit autorisatie/attendering_populatiebeperking_op_basis_van_geboortedatum_attendering_dienst_ingang_vandaag
Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC20T280_xls
When voor persoon 854820425 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Attendering populatiebeperking op basis van geboortedatum is ontvangen en wordt bekeken

Scenario: 2.    Geboortedatum 1997/02/28 Populatiebeperking evalueert ONWAAR, Attenderingscriterium = WAAR.
                LT: R1334_LT03
                Verwacht resultaat: GEEN volledig bericht

Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC10T100_xls
Given leveringsautorisatie uit autorisatie/attendering_populatiebeperking_op_basis_van_geboortedatum
When voor persoon 319343017 wordt de laatste handeling geleverd
Then is er geen synchronisatiebericht gevonden

Scenario: 3.    Geboortedatum 1966/01/00 Populatiebeperking evalueert NULL, Attenderingscriterium = WAAR.
                LT: R1334_LT04
                Verwacht resultaat:
                - GEEN attendering, en dus GEEN volledig bericht

Given persoonsbeelden uit specials:specials/Libby-gebdat-deels-onbekend1_xls
Given leveringsautorisatie uit autorisatie/attendering_populatiebeperking_op_basis_van_geboortedatum1966
When voor persoon 422531881 wordt de laatste handeling geleverd
Then is er geen synchronisatiebericht gevonden


Scenario: 4.  Populatiebeperking is waar, attenderingcriteria op aanvang huwelijk is onwaar.
                LT: R1334_LT05
                Verwacht resultaat: GEEN volledig bericht

Given leveringsautorisatie uit autorisatie/attendering_huwelijkaanvang_populatiebeperking_waar
Given persoonsbeelden uit BIJHOUDING:VHNL04C10T10/Personen_Libby_Thatcher_(Ingeschrevene-I/dbstate001
When voor persoon 422531881 wordt de laatste handeling geleverd
Then is er geen synchronisatiebericht gevonden

Scenario: 5.    Geboortedatum 20100811 Populatiebeperking evalueert WAAR, Attenderingscriterium = NULL (leeg).
                LT: R1334_LT06
                Verwacht resultaat: Geen bericht

Given leveringsautorisatie uit autorisatie/attendering_populatiebeperking_op_basis_van_geboortedatum_attenderingscriterium_null
Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC20T280_xls
When voor persoon 854820425 wordt de laatste handeling geleverd
Then is er geen synchronisatiebericht gevonden

Scenario: 6.    Geboortedatum 1997/11/20 Populatiebeperking evalueert WAAR, Attenderingscriterium = WAAR, datum einde abo = systeemdatum.
                LT: R1334_LT07
                Verwacht resultaat: GEEN volledig bericht

Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC20T280_xls
Given leveringsautorisatie uit autorisatie/attendering_populatiebeperking_op_basis_van_geboortedatum_attendering_dienst_einde_vandaag
When voor persoon 854820425 wordt de laatste handeling geleverd
Then is er geen synchronisatiebericht gevonden

Scenario: 7.    Geboortedatum 1997/11/20 Populatiebeperking evalueert WAAR, Attenderingscriterium = WAAR, datum ingang abo > systeemdatum
                LT: R1334_LT08
                Verwacht resultaat: GEEN volledig bericht

Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC20T280_xls
Given leveringsautorisatie uit autorisatie/attendering_populatiebeperking_op_basis_van_geboortedatum_attendering_dienst_ingang_morgen
When voor persoon 854820425 wordt de laatste handeling geleverd
Then is er geen synchronisatiebericht gevonden

Scenario: 8.    Persoonsbeeld nieuw populatie beperking = NULL, attenderingscriterium is ONWAAR
                LT: R1334_LT11
                Verwacht resultaat: GEEN volledig bericht

Given persoonsbeelden uit specials:specials/Libby-gebdat-deels-onbekend1_xls
Given leveringsautorisatie uit autorisatie/attendering_populatiebeperking_op_null_criterium_onwaar
When voor persoon 422531881 wordt de laatste handeling geleverd
Then is er geen synchronisatiebericht gevonden


Scenario: 9.    Persoonsbeeld nieuw populatie beperking = ONWAAR, attenderingscriterium is NULL
                LT: R1334_LT12
                Verwacht resultaat: GEEN volledig bericht

Given persoonsbeelden uit specials:specials/Libby-gebdat-deels-onbekend1_xls
Given leveringsautorisatie uit autorisatie/attendering_populatiebeperking_op_onwaar_criterium_null
When voor persoon 422531881 wordt de laatste handeling geleverd
Then is er geen synchronisatiebericht gevonden