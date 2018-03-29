Meta:

@status             Klaar
@usecase            LV.0.MB
@regels             R1974
@sleutelwoorden     Maak BRP bericht

Narrative:
Bij de levering van een 'Inhoudelijke groep' (R1540) voor een Dienst mag een Attribuut alleen in het bericht
voorkomen als er een corresponderend voorkomen van Dienstbundel \ Groep \ Attribuut bestaat.

Scenario: 1.1   Attribuut autorisatie op leverings autorisatie Geen pop.bep. levering op basis van doelbinding Haarlem, GEEN Attribuut autorisatie op leverings autorisatie Geen pop.bep. levering op basis van doelbinding Haarlem niet geautoriseerd
                LT: R1974_LT01, R1974_LT02
                Verwacht resultaat:
                - attribuut huisnummer  aanwezig in bericht voor leverings autorisatie Geen pop.bep. levering op basis van doelbinding Haarlem
                - attribuut huisnummer niet aanwezig in bericht voor leverings autorisatie Geen pop.bep. levering op basis van doelbinding Haarlem niet geautoriseerd


Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_obv_afnemerindicatie_huisnummer_niet_geautoriseerd, autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding_Haarlem


Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek plaats afnemerindicatie:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering obv afnemerindicatie huisnummer niet geautoriseerd'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801

Then heeft het antwoordbericht verwerking Geslaagd

!-- LT R1974_LT02
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering obv afnemerindicatie huisnummer niet geautoriseerd is ontvangen en wordt bekeken
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep   | nummer | attribuut                  | aanwezig  |
| adres   | 1      | huisnummer                 | nee       |

!-- Given de standaardpersoon UC_Kenny met bsn 228708977 en anr 2010486354 zonder extra gebeurtenissen
!-- Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering obv afnemerindicatie huisnummer niet geautoriseerd' en partij 'Gemeente Utrecht'
!-- Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
!-- And testdata uit bestand 30.2_R1974_afnemerIndicatie_plaats.yml

Given persoonsbeelden uit specials:specials/Jan_xls

Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van doelbinding Haarlem'
|zendendePartijNaam|'Gemeente Haarlem'
|bsn|606417801

Then heeft het antwoordbericht verwerking Geslaagd

!-- LT R1974_LT01
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding Haarlem is ontvangen en wordt bekeken
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep   | nummer | attribuut                  | aanwezig  |
| adres   | 1      | huisnummer                 | ja        |


