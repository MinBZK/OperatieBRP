Meta:

@status             Klaar
@usecase            LV.0.MB
@regels             R1568
@sleutelwoorden     Maak BRP bericht

Narrative:
Bij het opstellen van een Leverinsgsbericht waarin meerdere personen zijn opgenomen geldt het volgende:
De volgorde van hoofdpersonen in het leveringsbericht wordt bepaald door het attribuut Persoon.Sorteervolgorde
in de groep Persoon.Afgeleid administratief. In het bericht worden de Personen in oplopende Persoon.Sorteervolgorde opgenomen.
Bij gelijke Persoon.Sorteervolgorde vindt sortering plaats op oplopende technische sleutel (ID) van het object Persoon.

Scenario: 1.    Sorteervolgorde is gelijk, dus dient sortering plaats te vinden op basis van ID van de personen
                LT: R1568_LT01
                Verwacht resultaat:
                - Sortering op oplopende technische sleutel (ID)

Given leveringsautorisatie uit autorisatie/geen_pop_bep_levering_op_basis_van_doelbinding_Haarlem

Given persoonsbeelden uit BIJHOUDING:VHNL04C10T10/Personen_Libby_Thatcher_(Ingeschrevene-I/dbstate003
When voor persoon 422531881 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding Haarlem is ontvangen en wordt bekeken

Then is het synchronisatiebericht gelijk aan expecteds/R1568_expected_scenario_1.xml voor expressie //brp:lvg_synVerwerkPersoon