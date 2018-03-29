Meta:
@status             Klaar
@sleutelwoorden     Selectie
@regels             R1546, R1548, R1353

Narrative:

Een vervallen groep (TijdstipVerval en ActieVerval zijn gevuld met een waarde) wordt alleen in het te leveren bericht opgenomen indien:

Het niet een MutatieBericht betreft

EN

er autorisatie bestaat voor de formele historie van de betreffende groep
(de groep komt voor in Dienstbundel \ Groep met Dienstbundel \ Groep.Formele historie? = 'Ja' bij de Dienst waarvoor geleverd wordt).



Scenario:   1.      Selectie met formele historie
                    LT: R1546_LT07
                    Verwacht resultaat:
                    - TijdstipVerval en ActieVerval zijn gevuld met een waarde voor vervallen groepen

Given leveringsautorisatie uit aut/SelectieMetFormeleHistorie
Given een selectierun met de volgende selectie taken:
|id | datplanning | status               | dienstSleutel                                                 |
|1  | vandaag     | Uitvoerbaar              | SelectieMetFormeleHistorie |

Given personen uit specials:specials/Anne_met_Historie_xls
When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar
Then resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag' met '1' personen

!-- Voor R1548 ook controle (middels volledige expected) dat tijdstipregistratie gevuld wordt bij aanwezigheid formele historie
!-- Voor R1548 ook controle (middels volledige expected) dat voorkomens van een groep waarin IndMuteLev de waarde 'Ja' heeft
!-- NIET in het selectieresultaat zitten
And zijn de volgende resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag':
|volgnummer    |pad
|1             |expecteds/R1546_Scenario1_Met_vervallen_groepen.xml

Scenario:   2.      Selectie zonder formele historie
                    LT: R1546_LT08
                    Verwacht resultaat:
                    - Geen vervallen groepen
                    - Geen TijdstipVerval en ActieVerval in bericht

Given leveringsautorisatie uit aut/SelectieZonderFormeleHistorie
Given een selectierun met de volgende selectie taken:
|id | datplanning | status               | dienstSleutel                                                 |
|1  | vandaag     | Uitvoerbaar              | SelectieZonderFormeleHistorie |

Given personen uit specials:specials/Anne_met_Historie_xls
When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar
Then resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag' met '1' personen

!-- Voor R1548 ook controle (middels volledige expected) dat tijdstipregistratie NIET gevuld wordt bij afnwezigheid formele historie
And zijn de volgende resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag':
|volgnummer    |pad
|1             |expecteds/R1546_Scenario2_Zonder_Vervallen_Groepen.xml
