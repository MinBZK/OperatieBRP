Meta:
@status             Klaar
@usecase            LV.0.MB
@regels             R1565
@sleutelwoorden     Maak BRP bericht

Narrative:
Voor elk Object dat wordt opgenomen in een bericht geldt dat de bijbehorende ObjectSleutel moet worden meegeleverd.
Dit betreft altijd de ID van het A-laag voorkomen van het betreffende object.

Als het ObjectType 'Persoon' is, geldt bovendien dat de ObjectSleutel versleuteld volgens R1834 -
Objectsleutel in bericht is versleutelde ID van persoon in het bericht moet worden opgenomen.

!-- omdat de objectsleutel in dit geval verwijst naar de persoon, is de object sleutel versleuteld.
!-- De regel voor deze controle staat nog op concept

Scenario: 1.1   UC_Kenny wordt geboren in de doelbinding. Controle op objecten voor objectsleutel
                LT: R1565_LT01, R1565_LT03
                Verwacht resultaat: Alle objecten krijgen objectsleutel mee

Given persoonsbeelden uit specials:MaakBericht/R1565_Anne_Bakker_xls
Given leveringsautorisatie uit autorisatie/Abo_geen_popbep_doelbinding_Haarlem
Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'Abo geen popbep doelbinding Haarlem'
|zendendePartijNaam|'Gemeente Haarlem'
|bsn|595891305

When het volledigbericht voor leveringsautorisatie Abo geen popbep doelbinding Haarlem is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/R1565_expected_scenario_01.xml voor expressie //brp:lvg_synVerwerkPersoon

!-- R1565_LT03 Controleer dat objectsleutel van de persoon gemaskeerd is
Then is er voor xpath //brp:persoon[string-length(@brp:objectSleutel) > 20] een node aanwezig in het levering bericht


!-- Controleer dat de objecten een attribuut objectSleutel bevatten
Then is er voor xpath //brp:adres[@brp:objectSleutel] een node aanwezig in het levering bericht
Then is er voor xpath //brp:voornaam[@brp:objectSleutel] een node aanwezig in het levering bericht
Then is er voor xpath //brp:geslachtsnaamcomponent[@brp:objectSleutel] een node aanwezig in het levering bericht
Then is er voor xpath //brp:nationaliteit[@brp:objectSleutel] een node aanwezig in het levering bericht
Then is er voor xpath //brp:derdeHeeftGezag[@brp:objectSleutel] een node aanwezig in het levering bericht
Then is er voor xpath //brp:onderCuratele[@brp:objectSleutel] een node aanwezig in het levering bericht
Then is er voor xpath //brp:reisdocument[@brp:objectSleutel] een node aanwezig in het levering bericht
Then is er voor xpath //brp:onderzoek[@brp:objectSleutel] een node aanwezig in het levering bericht
Then is er voor xpath //brp:gegevenInOnderzoek[@brp:objectSleutel] een node aanwezig in het levering bericht
Then is er voor xpath //brp:kind[@brp:objectSleutel] een node aanwezig in het levering bericht
Then is er voor xpath //brp:familierechtelijkeBetrekking[@brp:objectSleutel] een node aanwezig in het levering bericht
Then is er voor xpath //brp:ouder[@brp:objectSleutel] een node aanwezig in het levering bericht
Then is er voor xpath //brp:ouder/brp:persoon[@brp:objectSleutel] een node aanwezig in het levering bericht
Then is er voor xpath //brp:kind[@brp:objectSleutel] een node aanwezig in het levering bericht
Then is er voor xpath //brp:kind/brp:persoon[@brp:objectSleutel] een node aanwezig in het levering bericht
Then is er voor xpath //brp:partner[@brp:objectSleutel] een node aanwezig in het levering bericht
Then is er voor xpath //brp:partner/brp:persoon[@brp:objectSleutel] een node aanwezig in het levering bericht
Then is er voor xpath //brp:administratieveHandeling[@brp:objectSleutel] een node aanwezig in het levering bericht
Then is er voor xpath //brp:bron[@brp:objectSleutel] een node aanwezig in het levering bericht
Then is er voor xpath //brp:bronnen/brp:bron[@brp:objectSleutel] een node aanwezig in het levering bericht
Then is er voor xpath //brp:document[@brp:objectSleutel] een node aanwezig in het levering bericht
Then is er voor xpath //brp:actie[@brp:objectSleutel] een node aanwezig in het levering bericht

Scenario: 1.2       Het object persoon wordt in onderzoek gezet. Controle op onderzoekobject voor objectsleutel
                    LT: R1565_LT02
                    Verwacht resultaat: Gegeven in onderzoek heeft een attribuut 'objectSleutel'

Given persoonsbeelden uit specials:MaakBericht/R1565_Anne_Bakker_Onderzoek_Aanvang_xls
Given leveringsautorisatie uit autorisatie/Abo_geen_popbep_doelbinding_Haarlem

When voor persoon 595891305 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Abo geen popbep doelbinding Haarlem is ontvangen en wordt bekeken

Then hebben attributen in voorkomens de volgende waardes:
| groep              | nummer | attribuut    | verwachteWaarde          |
| synchronisatie     | 1      | soortNaam    | GBA - Bijhouding actueel |
| synchronisatie     | 1      | partijCode   | 199902                   |
| onderzoek          | 2      | datumAanvang | 2015-12-31               |
| gegevenInOnderzoek | 1      | elementNaam  | Persoon.Adres.Huisnummer |
| actie              | 1      | soortNaam    | Conversie GBA            |

!-- Controleer dat de objecten een attribuut objectSleutel bevatten
Then is er voor xpath //brp:onderzoek[@brp:objectSleutel] een node aanwezig in het levering bericht
Then is er voor xpath //brp:gegevenInOnderzoek[@brp:objectSleutel] een node aanwezig in het levering bericht
