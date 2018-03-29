Meta:
@status             Klaar
@usecase            SA.0.MA
@regels             R1343, R2586, R2589
@sleutelwoorden     Mutatielevering o.b.v. afnemerindicatie, Lever mutaties

Narrative:
R1343:
Indien R1338 - Verwerkingslogica dienst Mutatielevering op basis van afnemerindicatie heeft vastgesteld dat voor de onderhanden Administratieve handeling een MutatieBericht of VolledigBericht moet worden aangemaakt en verstrekt, dan wordt indien:

Bij R2551 - Reconstructie persoonsbeeld 'oud' ten behoeve van expressies is GEEN sprake van 'Persoon heeft een verstrekkingsbeperking voor Partij' (R1342)
EN
Bij R2550 - Reconstructie persoonsbeeld 'nieuw' ten behoeve van expressies is WEL sprake van 'Persoon heeft een verstrekkingsbeperking voor Partij' (R1342)

eenmalig alleen een MutatieBericht aangemaakt en verstrekt met melding van de verstrekkingsbeperking.

Toelichting:
Deze melding wordt slechts eenmalig verstrekt, om geen nadere informatie te lekken naar de Afnemer. Hiervoor dient de bepaling of de verstrekkingsbeperking in de onderliggende bijhouding is ontstaan.

R2586:
Indien sprake is van R1343 - Mutatielevering met melding verstrekkingsbeperking dan moet het MutatieBericht voor die Persoon alleen bestaan uit:

De identificerende elementen (zie 'Identificerende groep' (R1542)) en

De melding dat bij deze Persoon een verstrekkingsbeperking is opgenomen voor de Persoon \ Verstrekkingsbeperking.Partij.

Scenario: 1.1   Mutatielevering op basis van afnemerindicatie voor persoon waar volledige verstrekkingsbeperking wordt geplaatst
                LT: R1343_LT01, R2586_LT01, R2589_LT02, R1983_LT29, R2589_LT01
                Verwacht resultaat: Mutatielevering met vulling
                - Code: R2586
                - Melding: Bij deze persoon is een verstrekkingsbeperking vastgelegd, mutatielevering is gestopt.
                - Alleen identificerende groepen

Given leveringsautorisatie uit autorisatie/AfnemerIndicatie_Verstrekkingsbeperking
Given persoonsbeelden uit specials:VerstrekkingsBeperking/PersoonKrijgtVerstrekkingsBeperking_xls


Given afnemerindicatie op de persoon :
|bsn|leveringsautorisatieNaam|partijNaam|dienstId|tsReg|tsReg|datumEindeVolgen
|270433417|levering op basis van afnemerindicatie verstrekkingsbeperking|'KUC033-PartijVerstrekkingsbeperking'|30|2015-07-28 T16:11:21Z|2016-12-12 T16:11:21Z

When voor persoon 270433417 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie levering op basis van afnemerindicatie verstrekkingsbeperking is ontvangen en wordt bekeken

!-- check op alleen identificerende groepen
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep              | nummer | attribuut                   | aanwezig |
| persoon            | 1      | identificatienummers        | ja       |
| persoon            | 1      | samengesteldeNaam           | ja       |
| persoon            | 1      | geboorte                    | ja       |
| persoon            | 1      | geslachtsaanduiding         | ja       |
| persoon            | 1      | voornamen                   | nee      |
| persoon            | 1      | geslachtsnaamcomponenten    | nee      |
| persoon            | 1      | adressen                    | nee      |

!-- Check op melding R2586
Then hebben attributen in voorkomens de volgende waardes:
| groep 	            | nummer  	| attribuut             | verwachteWaarde |
| melding        	    | 1         | regelCode        	    | R2586           |
| melding        	    | 1         | soortNaam      	    | Waarschuwing    |
| melding        	    | 1         | melding         	    | Bij deze persoon is een verstrekkingsbeperking vastgelegd, mutatielevering is gestopt. |

Then is het synchronisatiebericht gelijk aan Expecteds/Mutatie_obv_afnemerindicatie_met_Verstrekkingsbeperking.xml voor expressie //brp:lvg_synVerwerkPersoon
Given persoonsbeelden uit specials:VerstrekkingsBeperking/PersoonMutatieMetActueleVerstrekkingsbeperking_xls
When voor persoon 270433417 wordt de laatste handeling geleverd

!-- Controle R2589_LT01
Then is er geen synchronisatiebericht gevonden

Scenario: 1.2   Mutatielevering op basis van afnemerindicatie voor persoon waar verstrekkingsbeperking op partij wordt geplaatst
                LT: R1343_LT01, R2586_LT01, R2589_LT02, R1983_LT29, R1983_LT30
                Verwacht resultaat: Mutatielevering met vulling
                - Code: R2586
                - Melding: Bij deze persoon is een verstrekkingsbeperking vastgelegd, mutatielevering is gestopt.
                - Alleen identificerende groepen


Given leveringsautorisatie uit autorisatie/AfnemerIndicatie_Verstrekkingsbeperking_op_partij

Given persoonsbeelden uit BIJHOUDING:VZIG04C20T20/Specifieke_verstrekkingsbeperking_Partij/dbstate002

Given afnemerindicatie op de persoon :
|bsn|leveringsautorisatieNaam|partijNaam|dienstId|tsReg|tsReg|datumEindeVolgen
|771168585|levering op basis van afnemerindicatie verstrekkingsbeperking|'Stichting Interkerkelijke Ledenadministratie'|30|2015-07-28 T16:11:21Z|2016-12-12 T16:11:21Z

When voor persoon 771168585 wordt de laatste handeling geleverd

When het mutatiebericht voor leveringsautorisatie levering op basis van afnemerindicatie verstrekkingsbeperking is ontvangen en wordt bekeken

Then is het synchronisatiebericht gelijk aan Expecteds/Verstrekkingsbeperking_op_partij.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 2.        Mutatielevering op basis van afnemerindicatie voor persoon waar volledige verstrekkingsbeperking wordt geplaatst, alleen autorisatie op identificerende gegevens identificatienummer en geboorte
                    Verwacht resultaat:
                    - Alleen identificatienummer en geboorte groepen in bericht
                    - Melding R2586

Given leveringsautorisatie uit autorisatie/AfnemerIndicatie_Verstrekkingsbeperking_Alleen_Autorisatie_Geboorte_en_Identificatienummers
Given persoonsbeelden uit specials:VerstrekkingsBeperking/PersoonKrijgtVerstrekkingsBeperking_xls

Given afnemerindicatie op de persoon :
|bsn|leveringsautorisatieNaam|partijNaam|dienstId|tsReg|tsReg|datumEindeVolgen
|270433417|afnemerindicatie verstrekkingsbeperking autorisatie geboorte en identificatienummers|'KUC033-PartijVerstrekkingsbeperking'|30|2015-07-28 T16:11:21Z|2016-12-12 T16:11:21Z

When voor persoon 270433417 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie afnemerindicatie verstrekkingsbeperking autorisatie geboorte en identificatienummers is ontvangen en wordt bekeken

!-- check op alleen identificerende groepen die geautorisaeerd zijn
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep              | nummer | attribuut                   | aanwezig |
| persoon            | 1      | identificatienummers        | ja       |
| persoon            | 1      | samengesteldeNaam           | nee      |
| persoon            | 1      | geboorte                    | ja       |
| persoon            | 1      | geslachtsaanduiding         | nee      |

!-- Check op melding R2586
Then hebben attributen in voorkomens de volgende waardes:
| groep 	            | nummer  	| attribuut             | verwachteWaarde |
| melding        	    | 1         | regelCode        	    | R2586           |
| melding        	    | 1         | soortNaam      	    | Waarschuwing    |
| melding        	    | 1         | melding         	    | Bij deze persoon is een verstrekkingsbeperking vastgelegd, mutatielevering is gestopt. |


Scenario: 3.    Mutatielevering op basis van afnemerindicatie voor persoon waar volledige verstrekkingsbeperking wordt geplaatst, geen autorisatie op alle identificerende gegevens.
                Verwacht resultaat:
                - Geen groepen in bericht, dus geen bericht

Given leveringsautorisatie uit autorisatie/AfnemerIndicatie_Verstrekkingsbeperking_Geen_Autorisatie_groepen
Given persoonsbeelden uit specials:VerstrekkingsBeperking/PersoonKrijgtVerstrekkingsBeperking_xls

Given afnemerindicatie op de persoon :
|bsn|leveringsautorisatieNaam|partijNaam|dienstId|tsReg|tsReg|datumEindeVolgen
|270433417|afnemerindicatie verstrekkingsbeperking geen autorisatie groepen|'KUC033-PartijVerstrekkingsbeperking'|30|2015-07-28 T16:11:21Z|2016-12-12 T16:11:21Z

When voor persoon 270433417 wordt de laatste handeling geleverd
Then is er geen synchronisatiebericht gevonden