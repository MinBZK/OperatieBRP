Meta:
@status             Klaar
@usecase            LV.0.MB
@regels             R1980
@sleutelwoorden     Maak BRP bericht

Narrative:
Een BRP bericht bevat geen lege containers.

Dat wil zeggen dat als een bericht een container zou bevatten die geen enkel object bevat,
deze container niet moet worden aangemaakt (dan wel moet worden weggefilterd uit het resultaat)

Scenario:   1. Jan heeft geen afnemerindicatie
            LT: R1980_LT03
                Verwacht resultaat: volledig bericht
                - lege container afnemerindicaties wordt niet getoond

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/Abo_geen_popbep_doelbinding_Haarlem

Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'Abo geen popbep doelbinding Haarlem'
|zendendePartijNaam|'Gemeente Haarlem'
|bsn|606417801

Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Abo geen popbep doelbinding Haarlem is ontvangen en wordt bekeken
!-- Voorbeeld als lege container. Deze regel wordt eigenlijk beter afgetest in alle testen aar volledige expectds geplaatst worden.
Then heeft het bericht 0 groepen 'afnemerindicaties'

Then is het synchronisatiebericht gelijk aan expecteds/R1980_expected_scenario_1.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario:   2. Controle op lege containers voor mutatiebericht
            LT: R1980_LT04
            Verwacht resultaat: Mutatiebericht
            - Geen LEGE container afnemerindicatie

Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding_Haarlem

Given persoonsbeelden uit BIJHOUDING:VHNL04C10T10/Personen_Libby_Thatcher_(Ingeschrevene-I/dbstate003
When voor persoon 422531881 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding Haarlem is ontvangen en wordt bekeken
!-- Voorbeeld als lege container. Deze regel wordt eigenlijk beter afgetest in alle testen waar volledige expectds geplaatst worden.
Then heeft het bericht 0 groepen 'afnemerindicaties'

Then is het synchronisatiebericht gelijk aan expecteds/R1980_expected_scenario_2.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario:   3. Verhuizing van hr letter naar hr letter leeg
            LT:
            Extra test om te kijken of lege attributen geleverd worden

Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding,
Given persoonsbeelden uit specials:MaakBericht/R1980_Elisabeth_verhuizing_hrtoevoeging_vervalt_xls
When voor persoon 270433417 wordt de laatste handeling geleverd
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/R1980_expected_scenario_3.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario:  4. Geef Details - Lege Persoon container als ivm autorisatie geen te leveren attributen meer overblijven
              LT: R1980_LT03

Given leveringsautorisatie uit autorisatie/R1980_LeegPersoonObject
Given persoonsbeelden uit BIJHOUDING:VHNL06C10T10/De_actuele_Persoon.Samengestelde_naam_wo/dbstate003
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'LegeSelectie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|156960849

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                                                                                  |
| R1403 | Met het opgegeven identificerend gegeven is geen persoon gevonden binnen uw autorisatie. |

Scenario:  5. Plaats afnemerindicatie - Lege Persoon container als ivm autorisatie geen te leveren attributen meer overblijven
              LT: R1980_LT03

Given leveringsautorisatie uit autorisatie/R1980_LeegPersoonObject
Given persoonsbeelden uit BIJHOUDING:VHNL06C10T10/De_actuele_Persoon.Samengestelde_naam_wo/dbstate003

Given verzoek plaats afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'LegeSelectie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|156960849

Then heeft het antwoordbericht verwerking Geslaagd

Then is er geen synchronisatiebericht gevonden

Scenario:  6. Synchroniseer Persoon - Lege Persoon container als ivm autorisatie geen te leveren attributen meer overblijven
              LT: R1980_LT03

Given leveringsautorisatie uit autorisatie/R1980_LeegPersoonObject
Given persoonsbeelden uit BIJHOUDING:VHNL06C10T10/De_actuele_Persoon.Samengestelde_naam_wo/dbstate003
Given afnemerindicatie op de persoon :
| bsn       | leveringsautorisatieNaam | partijNaam         | dienstId | tsReg                 |
| 156960849 | LegeSelectie             | 'Gemeente Utrecht' | 4        | 2016-07-28 T16:11:21Z |

Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'LegeSelectie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|156960849

Then heeft het antwoordbericht verwerking Geslaagd

Then is er geen synchronisatiebericht gevonden

Scenario:  7. Selectie - Lege Persoon container als ivm autorisatie geen te leveren attributen meer overblijven
              LT: R1980_LT03

Given leveringsautorisatie uit autorisatie/R1980_LeegPersoonObject
Given persoonsbeelden uit BIJHOUDING:VHNL06C10T10/De_actuele_Persoon.Samengestelde_naam_wo/dbstate003

Given een selectierun met de volgende selectie taken:
| id | datplanning | status      | dienstSleutel                                 |
| 1  | vandaag     | Uitvoerbaar | selectietoegang                               |

When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar
Then resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag' met '0' personen
