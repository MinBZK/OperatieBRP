Meta:
@status             Klaar
@usecase            LV.0.MB
@regels             R1561
@sleutelwoorden     Maak BRP bericht


Narrative:
Een bericht bevat alleen voorkomens van Gegeven in onderzoek waarbij:
Gegeven in onderzoek verwijst naar een gegeven dat ook aanwezig is in het bericht:
(Gegeven in onderzoek.Object sleutel gegeven en/of Gegeven in onderzoek.Voorkomen sleutel gegeven verwijst naar een object of groep die (na filtering op autorisatie en historie) in het bericht aanwezig is)
OF
Het een onderzoek betreft naar een ontbrekend gegeven:
(Gegeven in onderzoek.Object sleutel gegeven is leeg
EN
Gegeven in onderzoek.Voorkomen sleutel gegeven is leeg).

Scenario: 1.     Onderzoek gestart op  huisnumer (onderdeel van groep adres)
                 LT: R1561_LT01, R1561_LT06
                 Verwacht resultaat:
                 - Onderzoek geleverd voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding Haarlem
                 - Onderzoek NIET geleverd voor leveringsautorisatie Geen autorisatie op attributen van adres


Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding_Haarlem, autorisatie/Geen_autorisatie_op_attributen_adres
Given persoonsbeelden uit specials:specials/Anne_Bakker_xls
When voor persoon 595891305 wordt de laatste handeling geleverd

!-- Controle op R1561_LT01
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding Haarlem is ontvangen en wordt bekeken
Then heeft het bericht 2 groepen 'onderzoek'
Then hebben attributen in voorkomens de volgende waardes:
| groep                  | nummer | attribuut           | verwachteWaarde           |
| gegevenInOnderzoek     | 1      | elementNaam         | Persoon.Adres.Huisnummer  |
!-- Controle op R1561_LT06
When het volledigbericht voor leveringsautorisatie Geen autorisatie op attributen van adres is ontvangen en wordt bekeken
Then heeft het bericht 0 groepen 'onderzoek'

Scenario: 2.        Datum aanvang materiele periode is 2016-01-01
                    LT: R1561_LT05
                    Verwacht resultaat:
                    - Onderzoek NIET in bericht
                    Uitwerking:
                    - Onderzoek op adres Uithoorn (huidige adres) van datum aanvang = 2010-01-01 en datum einde = 2015-01-01

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:MaakBericht/R1544_Anne_Bakker_xls
Given afnemerindicatie op de persoon :
| bsn       | leveringsautorisatieNaam                                  | partijNaam      | datumEindeVolgen | datumAanvangMaterielePeriode | tsReg                 | dienstId |
| 595891305 | 'Geen pop.bep. levering op basis van afnemerindicatie'    | 'Gemeente Utrecht' |                  | 2016-01-01                   | 2016-01-01 T00:00:00Z | 1        |


Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|595891305

Then heeft het antwoordbericht verwerking Geslaagd
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken

Then heeft het bericht 0 groepen 'onderzoek'