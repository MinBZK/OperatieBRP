Meta:
@status             Klaar
@sleutelwoorden     Expressietaal

Narrative:
Testen van de domein functies in de expressie taal:
GEWIJZIGD


Scenario:    01 GEWIJZIGD WAAR expressie
             LT:
             Expressie:   GEWIJZIGD(oud, nieuw, [Persoon.Voornaam.Naam])

Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC10T10_xls
Given leveringsautorisatie uit autorisatie/functie_GEWIJZGD_WAAR

When voor persoon 854820425 wordt de laatste handeling geleverd
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Expressietaal is ontvangen en wordt bekeken


Scenario:    02 GEWIJZIGD ONWAAR expressie
             LT:
             Expressie:   GEWIJZIGD(oud, nieuw, [Persoon.BuitenlandsPersoonsnummer.Nummer])

Given persoonsbeelden uit oranje:DELTAVERS01a/DELTAVERS01aC10T10_xls
Given leveringsautorisatie uit autorisatie/functie_GEWIJZGD_ONWAAR

When voor persoon 854820425 wordt de laatste handeling geleverd
Then is er geen synchronisatiebericht gevonden
