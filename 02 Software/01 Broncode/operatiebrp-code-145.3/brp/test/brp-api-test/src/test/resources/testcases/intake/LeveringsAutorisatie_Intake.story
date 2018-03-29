Meta:
@status                 Klaar
@sleutelwoorden         Intaketest, Levsautorisatie

Narrative:
Intake test om de mutatie levering aan meerdere afnemers te valideren

1 Gemeente Utrecht
2 Gemeente Haarlem
3 Gemeente Utrecht

Scenario:   1. Mutatie Levering aan meerdere leveringsautorisaties
            LT:
            Verwacht resultaat:
            1. 9 mutatieleveringen voor 9 verschillende gemeenten
            2. Totaal aantal berichten niet meer dan 9.


Given leveringsautorisatie uit autorisatie/LevsAutorisatie1, autorisatie/LevsAutorisatie2, autorisatie/LevsAutorisatie3
Given persoonsbeelden uit oranje:DELTAVERS08/DELTAVERS08C10T10_xls

When voor persoon 270433417 wordt de laatste handeling geleverd

When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie LevsAutorisatie1 is ontvangen en wordt bekeken
When het mutatiebericht voor partij Gemeente Haarlem en leveringsautorisatie LevsAutorisatie2 is ontvangen en wordt bekeken
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie LevsAutorisatie3 is ontvangen en wordt bekeken


Then zijn er 3 berichten geleverd
