Meta:
@status             Klaar
@regels             R1333
@sleutelwoorden     Mutatielevering o.b.v. doelbinding

Narrative:
Oud nieuw bepaling

Indien er door de onderhanden Administratieve handeling één of meer personen 'Gewijzigd binnen doelbinding'
of 'Uit doelbinding gegaan' zijn bij de betreffende Toegang leveringsautorisatie en Dienst, dan dient voor die combinatie :
Een VolledigBericht aangemaakt en verstrekt te worden waarin deze Persoonslijsten zijn opgenomen,
als de Administratieve handeling.Soort gelijk is aan de waarde GBA - Bijhouding overig

Scenario:   1. Verhuizing via GBA bijhouding binnen doelbinding
            LT: R1333_LT02
            Verwacht resultaat: Volledig bericht
            Uitwerking:
            Administratieve handeling P, direct voorafgaande aan Q, evalueerde op WAAR door verhuizing binnen de doelbinding.

Given leveringsautorisatie uit autorisatie/postcode_gebied_2000-5000

Given persoonsbeelden uit specials:specials/Anne_Bakker_GBA_Bijhouding_Verhuizing_xls

When voor persoon 595891305 wordt de laatste handeling geleverd
!-- Volledig bericht GBA bijhouding overig
When het volledigbericht voor leveringsautorisatie postcode gebied 2000 - 5000 is ontvangen en wordt bekeken
Then hebben attributen in voorkomens de volgende waardes:
| groep                    | nummer | attribuut | verwachteWaarde         |
| administratieveHandeling | 1      | soortNaam | GBA - Bijhouding overig |