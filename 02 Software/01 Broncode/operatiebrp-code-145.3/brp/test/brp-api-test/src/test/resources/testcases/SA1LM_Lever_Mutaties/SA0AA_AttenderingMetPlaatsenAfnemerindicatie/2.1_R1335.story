Meta:
@status             Klaar
@usecase            SA.0.AA
@regels             R1334, R1335, R1983, R1991, R1993, R1994, R2016, R2057, R2060, R2062, R2129
@sleutelwoorden     Attendering met plaatsen afnemerindicatie, Lever mutaties

Narrative:
Voor de verwerking van een Dienst met Soort dienst Attendering en waarbij Dienst.Effect afnemerindicaties gelijk is aan "Plaatsing":

Indien deze Dienst een leveringsbericht aanmaakt met één of meer Personen, dan maakt deze Dienst per geleverde Persoon tevens een nieuwe Persoon \ Afnemerindicatie aan,
als die nog niet bestaat:

Doe voor elke Persoon in het bericht:

Ga na of er al een geldig voorkomen van Persoon \ Afnemerindicatie bestaat voor deze Persoon en deze Leveringsautorisatie.

Indien dat niet het geval is, vul dan de volgende rubrieken ter plaatsing van de afnemerindicatie:
Persoon \ Afnemerindicatie.Persoon = De betreffende Persoon
Persoon \ Afnemerindicatie.Afnemer = De Partij waarvoor de Dienst (in combinatie met de Toegang leveringsautorisatie) wordt geleverd
Persoon \ Afnemerindicatie.Leveringsautorisatie = De Leveringsautorisatie waarbinnen de Dienst wordt geleverd
Persoon \ Afnemerindicatie.Datum aanvang materiële periode = 'leeg'
Persoon \ Afnemerindicatie.Datum einde volgen= 'leeg'}

Scenario: 1.    Dienst attendering, dienst effect afnemerindicatie: verwijder,
                LT: R1335_LT03
                voldoet aan attenderingscriterium, aantal personen: 1, afnemerindicatie aanwezig: Nee
                Verwacht resultaat:
                - Volledig bericht op basis van attenderingscriterium
                - Geen afnemerindicatie geplaatst, want geen effect plaatsing

Given leveringsautorisatie uit autorisatie/attendering_met_verwijdering_afnemerindicatie
Given persoonsbeelden uit oranje:DELTAVERS08/DELTAVERS08C80T10e_xls
When voor persoon 270433417 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Attendering met verwijdering afnemerindicatie is ontvangen en wordt bekeken
Then is er voor persoon met bsn 270433417 en leveringautorisatie Attendering met verwijdering afnemerindicatie en partij Gemeente Utrecht geen afnemerindicatie geplaatst

Scenario: 2.    Er worden 2 personen aangemaakt, deze personen trouwen,
                LT: R1335_LT05
                waardoor ze beiden binnen het attenderingscriterium van de leverautorisatie komen te vallen.
                Verwacht resultaat: 2 volledige berichten en 2 afnemerindicaties geplaatst

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie_attendering
Given persoonsbeelden uit BIJHOUDING:VHNL04C10T10/Personen_Libby_Thatcher_(Ingeschrevene-I/dbstate003
When voor persoon 422531881 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie attendering is ontvangen en wordt bekeken
Then is er voor persoon met bsn 422531881 en leveringautorisatie Geen pop.bep. levering op basis van afnemerindicatie attendering en partij Gemeente Utrecht een afnemerindicatie geplaatst

Then is er voor persoon met bsn 159247913 en leveringautorisatie Geen pop.bep. levering op basis van afnemerindicatie attendering en partij Gemeente Utrecht een afnemerindicatie geplaatst

Scenario: 3.    422531881 (Libby) trouwt met 159247913 (Piet), waardoor ze beiden binnen het attenderingscriterium van de leverautorisatie komen te vallen.
                LT: R1335_LT06, R1352_LT02
                Verwacht resultaat:
                - Mutatiebericht voor 434587977 en geen afnemerindicatie geplaatst (want die is er al)
                - Volledig bericht voor 159247913 en plaatsen afnemerindicatie

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie_attendering
Given persoonsbeelden uit BIJHOUDING:VHNL04C10T10/Personen_Libby_Thatcher_(Ingeschrevene-I/dbstate003

Given afnemerindicatie op de persoon :
| bsn       | leveringsautorisatieNaam                                              | partijNaam      | datumEindeVolgen | tsReg                 | dienstId |
| 422531881 | 'Geen pop.bep. levering op basis van afnemerindicatie attendering'    | 'Gemeente Utrecht' |                  | 2010-01-01 T00:00:00Z | 1        |


When voor persoon 422531881 wordt de laatste handeling geleverd

!-- Controle op Libby
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie attendering is ontvangen en wordt bekeken
Then hebben attributen in voorkomens de volgende waardes:
| groep                  | nummer | attribuut               | verwachteWaarde   |
| identificatienummers   | 1      | burgerservicenummer     | 422531881         |
Then is er voor persoon met bsn 422531881 en leveringautorisatie Geen pop.bep. levering op basis van afnemerindicatie attendering en partij Gemeente Utrecht geen afnemerindicatie geplaatst

!-- Controle op Piet
When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie attendering is ontvangen en wordt bekeken
Then hebben attributen in voorkomens de volgende waardes:
| groep                  | nummer | attribuut               | verwachteWaarde   |
| identificatienummers   | 1      | burgerservicenummer     | 159247913         |
Then is er voor persoon met bsn 159247913 en leveringautorisatie Geen pop.bep. levering op basis van afnemerindicatie attendering en partij Gemeente Utrecht een afnemerindicatie geplaatst

