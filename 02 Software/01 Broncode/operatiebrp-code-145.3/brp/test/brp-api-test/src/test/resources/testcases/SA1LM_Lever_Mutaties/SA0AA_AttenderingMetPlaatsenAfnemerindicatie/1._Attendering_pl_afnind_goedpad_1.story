Meta:
@status             Klaar
@usecase            SA.0.AA
@regels             R1334, R1335, R1983, R1991, R1993, R1994, R2016, R2057, R2060, R2062, R2129
@sleutelwoorden     Attendering met plaatsen afnemerindicatie, Lever mutaties

Narrative:
        Aanmaken volledigbericht.
        Indien door een administratieve handeling één of meerdere personen in de doelgroep van een abonnement
        met een geldige dienst Attendering van de afnemer vallen en er wordt voor die persoon of personen voldaan aan het attenderingscriterium,
        dan wordt een volledig bericht voor die persoon of personen aangemaakt.

        Algemeen: Persoon wordt geboren met Geboortedatum 2000/01/01 en komt nieuw in de doelbinding
        van leveringsautorisatie attendering_populatiebeperking_op_basis_van_geboortedatum met populatiebeperking Persoon.Geboorte.Datum >= 1997/03/01
        waardoor de Populatiebeperking evalueert naar WAAR, het Attenderingscriterium = WAAR.

Scenario: 1.    Persoon wordt geboren met Geboortedatum 2000/01/01 en komt nieuw in de doelbinding
                LT: R1335_LT01, R1983_LT04, R2057_LT27, R1352_LT01, R1336_LT02
                van leveringsautorisatie attendering_met_plaatsen_afnemerindicatie met populatiebeperking Persoon.Geboorte.Datum >= 1997/03/01
                waardoor de Populatiebeperking evalueert naar WAAR, het Attenderingscriterium = WAAR.
                Identiek aan goedpad attendering, daarom alleen controle op R1335 plaatsen afnemerindicatie en R2057, R1983, R1352
                SA.0.AA - Attendering met plaaten afnemerindicatie
                Dienst attendering, dienst plaatsen afnemerindicatie. voldoet aan attenderingscriterim
                LT: R1335_LT01
                Verwacht resultaat:  Volledig bericht & Afnemerindicatie geplaatst
                Database gevuld met vulling:
                - Persoon \ Afnemerindicatie.Persoon = 270433417
                - Persoon \ Afnemerindicatie.Afnemer = Gemeente Utrecht (348)
                - Persoon \ Afnemerindicatie.Leveringsautorisatie = Attendering met plaatsen afnemerindicatie (1)
                - Persoon \ Afnemerindicatie.Datum aanvang materiële periode = LEEG
                - Persoon \ Afnemerindicatie.Datum einde volgen= LEEG

                Dienst attendering met plaatsen afnemerindicatie, geen verstrekkingsbeperking
                LT: R1983_LT04
                Verwacht resultaat: bericht wordt geleverd

                Dienst:			    Ingang < systeemdatum	EN Einde = Leeg
                Dienstbundel:		Ingang < systeemdatum	EN Einde = Leeg
                Toegang. Lev:		Ingang < systeemdatum	EN Einde = Leeg
                Lev. Autorisatie:	Ingang < systeemdatum	EN Einde = Leeg
                LT: R2057_LT27
                Verwacht resultaat:
                Plaatsen afnemeridicatie en Volledig bericht

Given leveringsautorisatie uit autorisatie/attendering_met_plaatsen_afnemerindicatie
Given persoonsbeelden uit oranje:DELTAVERS08/DELTAVERS08C80T10e_xls

When voor persoon 270433417 wordt de laatste handeling geleverd

!-- R1983_LT04
When het volledigbericht voor leveringsautorisatie Attendering met Plaatsing afnemerindicatie is ontvangen en wordt bekeken

!-- R1335_LT01, R2057_LT27, R1352_LT01
Then is er voor persoon met bsn 270433417 en leveringautorisatie Attendering met Plaatsing afnemerindicatie en partij Gemeente Utrecht een afnemerindicatie geplaatst
