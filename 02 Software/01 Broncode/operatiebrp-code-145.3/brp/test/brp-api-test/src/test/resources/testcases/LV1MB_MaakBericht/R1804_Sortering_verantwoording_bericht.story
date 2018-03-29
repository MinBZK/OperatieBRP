Meta:
@status                 Klaar
@usecase                LV.0.MB
@regels                 R1804

Narrative:
Bij een levering wdt verantwoordingsinformatie gesorteerd
De gewenste sortering voor verantwoordingsinformatie is als volgt:
Administratieve handeling zijn aflopend gesorteerd op TijdstipRegistratie en daarbinnen oplopend op Id (dus de meest recente handeling bovenaan in het bericht)
Actie \ Bron (binnen die handelingen) en Actie\Bronnen zijn als volgt gesorteerd: Eerst Administratieve handeling \ Bron.Document, oplopend gesorteerd op Id, daarna Administratieve handeling \ Bron.Rechtsgrond, oplopend gesorteerd op Id, daarna Administratieve handeling \ Bron.Rechtsgrondomschrijving, gesorteerd op alfabetische volgorde van die omschrijving.
Actie zijn aflopend gesorteerd op TijdstipRegistratie en daarbinnen oplopend op Id.


Scenario:   1. Sortering bronnen, acties en rechtsgrond binnen administratieve handeling
            LT: R1804_LT01
            Voltrekking huwelijk met vervolgens een erkenning, naamswijziging en een verhuizing
            Verwacht resultaat:
            Sortering administratievhandelingen op tijdstip registratie aflopend = Huwelijk, GBA initiele vulling
            Sortering bron.documenten gesorteerd op id oplopend
            Sortering brond.rechtsgrond gesorteerd op id oplopend
            Sortering bron.rechtsgrondomschrijving gesorteerd op alfabetische volgorde
            Sortering Acties op tijdstip Registratie aflopend daarna op id


Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding
Given persoonsbeelden uit BIJHOUDING:VHNL06C10T10/De_actuele_Persoon.Samengestelde_naam_wo/dbstate003
Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van doelbinding'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|558376617
Then heeft het antwoordbericht verwerking Geslaagd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/R1804_expected_scenario_1.xml voor expressie //brp:lvg_synVerwerkPersoon

!-- Controleer de sortering van de administratieve handelingen aflopend op datum tijdstip registratie
Then hebben attributen in voorkomens de volgende waardes:
| groep                    | nummer | attribuut | verwachteWaarde                   |
| administratieveHandeling | 1      | soortNaam | Voltrekking huwelijk in Nederland |
| administratieveHandeling | 2      | soortNaam | GBA - Initiële vulling            |

!-- Controleer de sortering van bron documenten (oplopend) op db ID binnen een administratieve handeling (tsreg is gelijk voor beide documenten)

Then heeft het bericht voor xpath number(//brp:bron[1]/brp:document/@brp:objectSleutel) < number(//brp:bron[2]/brp:document/@brp:objectSleutel) de waarde true

!-- Controleer de sortering van actie  oplopend op ID (omdat tsreg gelijk is voor beide acties)
Then hebben attributen in voorkomens de volgende waardes:
| groep | nummer | attribuut | verwachteWaarde              |
| actie | 1      | soortNaam | Registratie aanvang huwelijk |
| actie | 2      | soortNaam | Registratie naamgebruik      |

!-- Controleer de sortering van acties met zelfde tsreg oplopend gesorteerd op ID
Then heeft het bericht voor xpath number(//brp:administratieveHandeling[2]/brp:bijgehoudenActies/brp:actie[1]/@brp:objectSleutel) < number(//brp:administratieveHandeling[2]/brp:bijgehoudenActies/brp:actie[2]/@brp:objectSleutel) de waarde true
