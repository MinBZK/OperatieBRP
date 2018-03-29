Meta:
@status             Klaar
@sleutelwoorden     Verstrekkingsbeperking

Narrative:
Als operatieBRP wil ik dat de leveringsdiensten correcte resultaten geven in het geval er bij de persoon een verstrekkingsbeperking is vastgelegd

NB: Voor partijen waarvoor geldt dat IndicatieVerstrekkingsbeperkingMogelijk = TRUE mag de dienst 'mutatie levering obv doelbinding'
    NIET zijn opgenomen in de leveringsautorisatie.


Scenario: 1.    Verwijderen van de afnemerindicatie bij actuele vollledige verstrekkingsbeperking
                LT: R1983_LT32
                Verwacht Resultaat: Verwerking geslaagd; de afnemerindicatie is succesvol verwijderd ondanks de verstrekkingsbeperking

Given leveringsautorisatie uit autorisatie/autorisatiealles_afnemerindicatie, autorisatie/autorisatiealles_afnemerindicatie_verstrekkingsBeperking

Given persoonsbeelden uit specials:VerstrekkingsBeperking/PersoonKrijgtVerstrekkingsBeperking_xls
Given afnemerindicatie op de persoon :
| bsn       | leveringsautorisatieNaam                                 | partijNaam                            | dienstId | tsReg                 | datumEindeVolgen |
| 270433417 | autorisatiealles_afnemerindicatie                        | 'Gemeente Utrecht'                    | 1        | 2016-07-28 T16:11:21Z |                  |
| 270433417 | autorisatiealles_afnemerindicatie_verstrekkingsBeperking | 'KUC033-PartijVerstrekkingsbeperking' | 10       | 2016-07-28 T16:11:21Z |                  |


Given verzoek verwijder afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'autorisatiealles_afnemerindicatie_verstrekkingsBeperking'
|zendendePartijNaam|'KUC033-PartijVerstrekkingsbeperking'
|bsn|270433417


Then heeft het antwoordbericht verwerking Geslaagd


Scenario: 2. Mutatie bericht nav vervallen verstrekkingsbeperking bij persoon waarvoor geldt dat Persoon.Indicatie.VolledigeVerstrekkingsbeperking.Waarde = true
             LT: R1342_LT05, R1340_LT06
             Verwacht Resultaat: Mutatie levering met waarschuwing en identificerende groepen


Given leveringsautorisatie uit autorisatie/autorisatiealles_afnemerindicatie, autorisatie/autorisatiealles_afnemerindicatie_verstrekkingsBeperking

Given persoonsbeelden uit specials:VerstrekkingsBeperking/PersoonMutatieTypeVerstrekkingsBeperkingVervalt_xls

Given afnemerindicatie op de persoon :
| bsn       | leveringsautorisatieNaam                                 | partijNaam                            | dienstId | tsReg                 | datumEindeVolgen |
| 270433417 | autorisatiealles_afnemerindicatie                        | 'Gemeente Utrecht'                    | 1        | 2016-07-28 T16:11:21Z |                  |
| 270433417 | autorisatiealles_afnemerindicatie_verstrekkingsBeperking | 'KUC033-PartijVerstrekkingsbeperking' | 10       | 2016-07-28 T16:11:21Z |                  |

When voor persoon 270433417 wordt de laatste handeling geleverd

!-- Mutatie bericht voor partij verstrekkingsbeperking = FALSE
!-- Geen waarschuwing in bericht
When het mutatiebericht voor leveringsautorisatie autorisatiealles_afnemerindicatie is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/levering_scenario_3A.xml voor expressie //brp:lvg_synVerwerkPersoon

!-- Mutatie bericht voor partij verstrekkingsbeperking = TRUE
!-- Geen waarschuwing in bericht
When het mutatiebericht voor leveringsautorisatie autorisatiealles_afnemerindicatie_verstrekkingsBeperking is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/levering_scenario_3B.xml voor expressie //brp:lvg_synVerwerkPersoon

Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'autorisatiealles_afnemerindicatie_verstrekkingsBeperking'
|zendendePartijNaam|'KUC033-PartijVerstrekkingsbeperking'
|bsn|270433417

Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie autorisatiealles_afnemerindicatie_verstrekkingsBeperking is ontvangen en wordt bekeken

Scenario:   3.  Zoek persoon, met meerdere personen, waarvan sommige een verstrekkingsbeperking hebben
                LT: R1340_LT08
                Verwacht resultaat:
                - Correcte verwijzingen ID in bericht

Given leveringsautorisatie uit autorisatie/Zoek_Persoon_Gemeente_Utrecht

Given personen uit specials:specials/Jan_xls, specials:specials/ElisaBeth_in_doelbinding_xls, specials:specials/Jan_MetActueleVerstrekkingsbeperking_xls, specials:specials/Kim_MetActueleVerstrekkingsbeperking_xls, specials:specials/Rob_MetActueleVerstrekkingsbeperking_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek persoon met verstrekkingsbeperkingen'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Geboorte.Datum,Waarde=1966-08-21

Then heeft het antwoordbericht verwerking Geslaagd

Then heeft het antwoordbericht 3 meldingen voor regel 'R1340' die betrekking hebben op personen met bsn '261952857,446478313,632357289'