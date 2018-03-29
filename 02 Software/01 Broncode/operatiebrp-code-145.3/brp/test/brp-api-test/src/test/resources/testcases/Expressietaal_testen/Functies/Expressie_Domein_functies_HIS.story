Meta:
@status             Klaar
@sleutelwoorden     Expressietaal

Narrative:
Testen van de domein functies in de expressie taal:
HISF, HISM, HISM_LAATSTE

HISF: filtert alle voorkomens met een verval er uit, filter ook de voorkomens met een indicatietbvmutlev=TRUE
HISM: Levert de niet vervallen voorkomens op, is enkel van toepassing op voorkomens met materiele historie

Scenario:   02 expressie met een HISM_LAATSTE op postcode
            LT:
            expressie in de autorisatie is HISM_LAATSTE op postcode 2511BL
            Laatste historische record van postcode is 2511BL dus leveren.
            Verwacht resultaat:
            Levering laatste handeling voor Anne_met_Historie.

Given leveringsautorisatie uit autorisatie/HISM_LAATSTE_PC_2511BL
Given persoonsbeelden uit specials:specials/Anne_met_Historie_xls

When voor persoon 590984809 wordt de laatste handeling geleverd
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie HISM LAATSTE is ontvangen en wordt bekeken


Scenario:   03 expressie met een HIS_LAATSTE op postcode welke niet gelijk is aan het laatste historische record
            LT:
            expressie in de autorisatie is HIS_LAATSTE op postcode 3512AE
            Laatste historische record van Anne_met_Historie postcode is 2511BL dus NIET leveren.
            Verwacht resultaat:
            Geen bericht.

Given leveringsautorisatie uit autorisatie/HISM_LAATSTE_PC_3512AE
Given persoonsbeelden uit specials:specials/Anne_met_Historie_xls
When voor persoon 590984809 wordt de laatste handeling geleverd
Then is er geen synchronisatiebericht gevonden


Scenario:   04 expressie met een HISM_LAATSTE E<>NULL
            LT:
            expressie op groep Persoon.Nationaliteit.Standaard, vergelijk op RedenVerliesCode E<> NULL
            Blob heeft een RedenVerliesCode in HisLaatste, persoon zou geleverd moeten worden.
            Verwacht resultaat:
            Persoon geleverd.

Given leveringsautorisatie uit autorisatie/HISM_LAATSTE_E_ongelijk_aan_NULL
Given persoonsbeelden uit oranje:DELTAVERS04a/DELTAVERS04aC10T40_xls
When voor persoon 963363529 wordt de laatste handeling geleverd
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie HISM LAATSTE E ongelijk aan NULL is ontvangen en wordt bekeken

Scenario:   05 expressie met een HISM_LAATSTE E<>NULL op RedenVerliesCode
            LT:
            expressie op groep Persoon.Nationaliteit.Standaard, vergelijk op RedenVerliesCode <> NULL
            Blob heeft geen laaste met RedenVerliesCode
            Verwacht resultaat:
            Geen bericht geleverd.

Given leveringsautorisatie uit autorisatie/HISM_LAATSTE_E_ongelijk_aan_NULL
Given persoonsbeelden uit specials:specials/Anne_met_Historie_xls
When voor persoon 590984809 wordt de laatste handeling geleverd
Then is er geen synchronisatiebericht gevonden

!-- HISF & HISF FUNCTIES


Scenario: 06    Expressie voor eerste postcode van Anne_met_historie op materiele historie
                LT:
                EXPR: HISM(Persoon.Adres.Postcode) E= "3512AE"
                Verwacht resultaat:
                - postcode ooit waar geweest, dus bericht geleverd

!-- Postcode geschiedenis Anne met historie:
!-- ACTUEEL:    1422RZ
!-- LAATSTE:    2511BL
!-- DAARVOOR:   3512AE

Given leveringsautorisatie uit autorisatie/HISM__PC_3512AE
Given persoonsbeelden uit specials:specials/Anne_met_Historie_xls

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'HISM'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|590984809

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 07    Expressie voor tweede postcode van Anne_met_historie op materiele historie
                LT:
                EXPR: HISM(Persoon.Adres.Postcode) E= "2511BL"
                Verwacht resultaat:
                - postcode ooit waar geweest, dus bericht geleverd

!-- Postcode geschiedenis Anne met historie:
!-- ACTUEEL:    1422RZ
!-- LAATSTE:    2511BL
!-- DAARVOOR:   3512AE

Given leveringsautorisatie uit autorisatie/HISM__PC_2511BL
Given persoonsbeelden uit specials:specials/Anne_met_Historie_xls

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'HISM'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|590984809

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 08    Expressie voor ACTUELE postcode van Anne_met_historie op materiele historie
                LT:
                EXPR: HISM(Persoon.Adres.Postcode) E= "1422RZ"
                Verwacht resultaat:
                - postcode NU waar geweest, dus GEEN bericht geleverd

!-- Postcode geschiedenis Anne met historie:
!-- ACTUEEL:    1422RZ
!-- LAATSTE:    2511BL
!-- DAARVOOR:   3512AE

Given leveringsautorisatie uit autorisatie/HISM__PC_1422RZ
Given persoonsbeelden uit specials:specials/Anne_met_Historie_xls

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'HISM'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|590984809

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                                  |
| R1403     | Met het opgegeven identificerend gegeven is geen persoon gevonden binnen uw autorisatie. |

Scenario: 09    Expressie voor postcode (5454AA) waar Anne_met_historie nooit gewoont heeft op materiele historie
                LT:
                EXPR: HISM(Persoon.Adres.Postcode) E= "5454AA"
                Verwacht resultaat:
                - postcode nooit waar geweest, dus GEEN bericht geleverd

!-- Postcode geschiedenis Anne met historie:
!-- ACTUEEL:    1422RZ
!-- LAATSTE:    2511BL
!-- DAARVOOR:   3512AE

Given leveringsautorisatie uit autorisatie/HISM__PC_5454AA
Given persoonsbeelden uit specials:specials/Anne_met_Historie_xls

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'HISM'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|590984809

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                                  |
| R1403     | Met het opgegeven identificerend gegeven is geen persoon gevonden binnen uw autorisatie. |


Scenario: 10    Expressie voor eerste postcode van Anne_met_historie op formele historie
                LT:
                EXPR: HISF(Persoon.Adres.Postcode) E= "3512AE"
                Verwacht resultaat:
                - postcode ooit waar geweest, dus bericht geleverd

!-- Postcode geschiedenis Anne met historie:
!-- ACTUEEL:    1422RZ
!-- LAATSTE:    2511BL
!-- DAARVOOR:   3512AE

Given leveringsautorisatie uit autorisatie/HISF__PC_3512AE
Given persoonsbeelden uit specials:specials/Anne_met_Historie_xls

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'HISF'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|590984809

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 11    Expressie voor tweede postcode van Anne_met_historie op formele historie
                LT:
                EXPR: HISF(Persoon.Adres.Postcode) E= "2511BL"
                Verwacht resultaat:
                - postcode ooit waar geweest, dus bericht geleverd

!-- Postcode geschiedenis Anne met historie:
!-- ACTUEEL:    1422RZ
!-- LAATSTE:    2511BL
!-- DAARVOOR:   3512AE

Given leveringsautorisatie uit autorisatie/HISF__PC_2511BL
Given persoonsbeelden uit specials:specials/Anne_met_Historie_xls

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'HISF'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|590984809

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 12    Expressie voor ACTUELE postcode van Anne_met_historie op formele historie
                LT:
                EXPR: HISF(Persoon.Adres.Postcode) E= "1422RZ"
                Verwacht resultaat:
                - postcode NU waar geweest, dus GEEN bericht geleverd

!-- Postcode geschiedenis Anne met historie:
!-- ACTUEEL:    1422RZ
!-- LAATSTE:    2511BL
!-- DAARVOOR:   3512AE

Given leveringsautorisatie uit autorisatie/HISF__PC_1422RZ
Given persoonsbeelden uit specials:specials/Anne_met_Historie_xls

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'HISF'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|590984809

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                                  |
| R1403     | Met het opgegeven identificerend gegeven is geen persoon gevonden binnen uw autorisatie. |

Scenario: 13    Expressie voor postcode (5454AA) waar Anne_met_historie nooit gewoont heeft op formele historie
                LT:
                EXPR: HISF(Persoon.Adres.Postcode) E= "5454AA"
                Verwacht resultaat:
                - postcode nooit waar geweest, dus GEEN bericht geleverd

!-- Postcode geschiedenis Anne met historie:
!-- ACTUEEL:    1422RZ
!-- LAATSTE:    2511BL
!-- DAARVOOR:   3512AE

Given leveringsautorisatie uit autorisatie/HISF__PC_5454AA
Given persoonsbeelden uit specials:specials/Anne_met_Historie_xls

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'HISF'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|590984809

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                                  |
| R1403     | Met het opgegeven identificerend gegeven is geen persoon gevonden binnen uw autorisatie. |

Scenario: 14 Zoeken op vervallen geregistreerdpartnerschap met HISF
             LT:
             EXPR : MAP(HISF (GeregistreerdPartnerschap.Standaard), r,r.RedenEindeCode) E= NULL

Given leveringsautorisatie uit autorisatie/HISF_vervallen_huwelijk
Given persoonsbeelden uit BIJHOUDING:EGNL01C70T30/R2040_Beeindiging_van_een_geregistreerd_/dbstate002

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'HISF'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|594645001

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 15 Zoeken op vervallen geregistreerdpartnerschap met HISM
              LT:
              EXPR : MAP(HISM (GeregistreerdPartnerschap.Standaard), r,r.RedenEindeCode) E= NULL

Given leveringsautorisatie uit autorisatie/HISM_vervallen_huwelijk
Given persoonsbeelden uit BIJHOUDING:EGNL01C70T30/R2040_Beeindiging_van_een_geregistreerd_/dbstate002

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'HISM'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|594645001

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                                  |
| R1403     | Met het opgegeven identificerend gegeven is geen persoon gevonden binnen uw autorisatie. |

Scenario: 16 Meerdere voorkomens van groepen
              LT:
              EXPR: HISF(Persoon.Nationaliteit.RedenVerkrijgingCode) E=022
              Persoon heeft actueel voorkomen van nationaliteit en vervallen voorkomen van nationaliteit
              Persoon heeft meerdere actuele en vervallen voorkomens van afnemerindicatie
              Historie patroon = formeel

Given leveringsautorisatie uit autorisatie/HISF_Nationaliteit
Given persoonsbeelden uit oranje:DELTAVERS04a/DELTAVERS04aC90T10g_xls

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'HISF'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|963363529

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 16.1 Meerdere voorkomens van groepen (op vervallen record)
              LT:
              EXPR: KV(FILTER(HISF(Persoon.Nationaliteit.Standaard), r, r.RedenVerkrijgingCode = 021))
              Persoon heeft actueel voorkomen van nationaliteit en vervallen voorkomen van nationaliteit
              Persoon heeft meerdere actuele en vervallen voorkomens van afnemerindicatie
              Historie patroon = formeel
              - gezocht wordt op alle record, records met nadere aanduiding verval mogen hier niet uitgefilterd worden
                record met RedenVerkrijgingCode = 21 is alleen een vervallen record.

Given leveringsautorisatie uit autorisatie/HISF_Nationaliteit_op_nadere_aanduiding_verval
Given persoonsbeelden uit oranje:DELTAVERS04a/DELTAVERS04aC90T10g_xls

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'HISF nadere aanduiding verval'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|963363529

Then heeft het antwoordbericht verwerking Geslaagd


Scenario: 17  Meerdere voorkomens van groepen
              LT:
              EXPR: HISM(Persoon.Nationaliteit.RedenVerkrijgingCode) E=022
              Persoon heeft actueel voorkomen van nationaliteit en vervallen voorkomen van nationaliteit
              Historie patroon = materieel / formeel

Given leveringsautorisatie uit autorisatie/HISM_Nationaliteit
Given persoonsbeelden uit oranje:DELTAVERS04a/DELTAVERS04aC90T10g_xls

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'HISM'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|963363529

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 18  Meerdere voorkomens van groep nationaliteit
              LT:
              EXPR: HISM(Persoon.Nationaliteit.RedenVerkrijgingCode) E=020
              Persoon heeft 2 materieel beeindigde voorkomens van nationaliteit waarvan 1 met redenbeeindigingcode = 20
              Historie patroon = materieel / formeel

Given leveringsautorisatie uit autorisatie/HISM_Nationaliteit_2
Given persoonsbeelden uit oranje:DELTAVERS04a/DELTAVERS04aC60T30_xls

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'HISM'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|963363529

Then heeft het antwoordbericht verwerking Geslaagd


