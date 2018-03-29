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

Scenario: 1.    Kruimeltje wordt geboren met Geboortedatum 1997/11/00 en komt nieuw in de doelbinding
                LT: R1262_LT15, R1264_LT11, R2056_LT11, R2130_LT09, R1334_LT01, R1335_LT04, R1983_LT01, R1991_LT01, R1993_LT01, R2060_LT01
                van leveringsautorisatie attendering_populatiebeperking_op_basis_van_geboortedatum met populatiebeperking Persoon.Geboorte.Datum >= 1997/03/01
                waardoor de Populatiebeperking evalueert naar WAAR, het Attenderingscriterium = WAAR.

                LV.1.AL Autorisatie Levering
                Dienst Attendering = geldig
                LT: R1262_LT15
                Verwacht resultaat; Autorisatie succesvol doorlopen, geen expliciete controles

                Dienst attendering is niet geblokkeerd
                LT: R1264_LT11
                Verwacht resultaat; Autorisatie succesvol doorlopen, geen expliciete controles

                De dienstbundel is geldig en niet geblokkeerd
                LT: R2056_LT11
                Verwacht resultaat; Autorisatie succesvol doorlopen, geen expliciete controles

                Leveringsautorisatie bevat dienst:
                LT: R2130_LT09
                Verwacht resultaat; Autorisatie succesvol doorlopen, geen expliciete controles

                SA.0.AT Attendering
                Dienst datum ingang < systeemdatum,
                LT: R1334_LT01
                Verwacht resultaat: Aanmaken volledig bericht

                Dienst attendering, geen verstrekkingsbeperking
                LT: R1983_LT01
                Verwacht resultaat: Verstrek volledig bericht

                Stelsel BRP:
                LT: R1991_LT01, R1993_LT01
                Verwacht resultaat: Levering via BRP stelsel

                Afleverpunt aanwezig:
                LT: R2060_LT01
                Verwacht resultaat: Lever volledig bericht

                SA.0.AA Attendering met plaatsen afnemerindicatie
                LT: R1335_LT04
                Effect afnemerindicatie = Leeg

Given leveringsautorisatie uit autorisatie/attendering_populatiebeperking_op_basis_van_geboortedatum
Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC20T280_xls
When voor persoon 854820425 wordt de laatste handeling geleverd

!-- R1334_LT01, R1983_LT01, R2060_LT01
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering populatiebeperking op basis van geboortedatum is ontvangen en wordt bekeken
