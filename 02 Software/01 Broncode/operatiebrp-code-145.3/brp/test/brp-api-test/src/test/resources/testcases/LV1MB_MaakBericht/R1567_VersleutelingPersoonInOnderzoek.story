Meta:
@status             Klaar
@usecase            LV.0.MB
@regels             R1567
@sleutelwoorden     Maak BRP bericht

Narrative:
R1565 - Geleverde objecten bevatten in het bericht een ObjectSleutel beschrijft de mogelijke versleuteling van de ObjectSleutel
in berichten. Wanneer binnen hetzelfde bericht verwezen wordt naar een object waarvan de ID versleuteld is, dan dient de verwijzing
dezelfde versleutelde waarde te gebruiken.
(Concreet: als er in een bericht een Gegeven in Onderzoek voorkomt met een ObjectSleutel van een Persoon, dan dient dezelfde
versleutelde waarde zowel in het persoonsdeel als in het onderzoeksdeel gebruikt te worden; het moet niet nodig zijn om de waarde
te ontsleutelen om te kunnen achterhalen wat er in onderzoek staat


Scenario: 1     UC Kenny valt in de doelbinding en Onderzoek wordt gestart. Controle op objectsleutel Onderzoek
                LT: R1567_LT01
                Issue: R1567_LT01 ROOD-1027
                Verwacht resultaat: Objectsleutel persoon (versleuteld) = objectsleutel gegeven onderzoek (versleuteld)

Meta:
@status Bug
!-- Regel niet testbaar, omdat er via de gba bijhoudingen geen Persoon-objecten in onderzoek gezet kunnen worden.

Given persoonsbeelden uit specials:MaakBericht/xyz
Given leveringsautorisatie uit autorisatie/levering_op_basis_van_doelbinding

When voor persoon 595891305 wordt de laatste handeling geleverd

When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie levering op basis van doelbinding is ontvangen en wordt bekeken

Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep              | nummer | attribuut            | aanwezig |
| onderzoeken        | 1      | onderzoek            | ja       |
| gegevenInOnderzoek | 1      | objectSleutelGegeven | ja       |

!-- Controleer dat de objectsleutel bij het gegeven in onderzoek gelijk is aan de objectsleutel van het object in het persooonsdeel
!-- In dit geval verwijst het gegeven in onderzoek naar het object persoon en dus moet deze, net als de objectsleutel van de persoon, versleuteld zijn.
Then is er voor xpath //brp:persoon[@brp:objectSleutel=//brp:objectSleutelGegeven/text()] een node aanwezig in het levering bericht
