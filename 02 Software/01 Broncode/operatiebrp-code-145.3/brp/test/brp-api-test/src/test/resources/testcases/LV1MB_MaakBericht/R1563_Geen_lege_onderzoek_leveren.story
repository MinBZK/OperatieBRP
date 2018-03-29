Meta:

@status             Klaar
@usecase            LV.0.MB
@regels             R1563
@sleutelwoorden     Maak BRP bericht

Narrative:
In het te leveren resultaat mogen geen lege Onderzoeken voorkomen. Dat wil zeggen: als er bij een Onderzoek geen enkel te leveren Gegeven in onderzoek meer aanwezig is, dan dient de hele groep uit het resultaat verwijderd te worden.
Als er geen enkele Groep Onderzoek meer resteert, dan zal het Onderzoeksdeel binnen het bericht niet meer aanwezig zijn.

Zie ook 'Onderzoeksgroep' (R1543) voor de definitie van de "Onderzoeksgroep" en de daarin gehanteerde begrippen.


Scenario: 1.     Onderzoek gestart op  huisnumer (onderdeel van groep adres)
                 LT: R1563_LT01, R1563_LT02
                 Verwacht resultaat:
                 - Onderzoek geleverd voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding Haarlem
                 - Onderzoek NIET geleverd voor leveringsautorisatie Geen autorisatie op attributen van adres


Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding_Haarlem, autorisatie/Geen_autorisatie_op_attributen_adres
Given persoonsbeelden uit specials:specials/Anne_Bakker_xls
When voor persoon 595891305 wordt de laatste handeling geleverd

!-- Controle op R1563_LT01
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding Haarlem is ontvangen en wordt bekeken
Then heeft het bericht 2 groepen 'onderzoek'
Then hebben attributen in voorkomens de volgende waardes:
| groep                  | nummer | attribuut           | verwachteWaarde           |
| gegevenInOnderzoek     | 1      | elementNaam         | Persoon.Adres.Huisnummer  |
!-- Controle op R1563_LT02
When het volledigbericht voor leveringsautorisatie Geen autorisatie op attributen van adres is ontvangen en wordt bekeken
Then heeft het bericht 0 groepen 'onderzoek'

