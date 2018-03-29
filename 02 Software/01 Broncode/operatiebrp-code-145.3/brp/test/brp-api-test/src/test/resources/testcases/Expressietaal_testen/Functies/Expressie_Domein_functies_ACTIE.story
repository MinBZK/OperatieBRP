Meta:
@status             Klaar
@sleutelwoorden     Expressietaal

Narrative:
In deze story zijn scenario's opgenomen om de expressietaal af te testen tav verantwoording m.b.t. functie ACTIE

Scenario:   1. Expressietaal testcases/Expressie_Taal_testen/autorisatie/Expressietaal_ACTIE_1
            LT:
            Expressie refereert aan bestaande attrwaarde (soortnaam) uit een bijgehouden actie uit de administratieve handeling
            Expressie: ACTIE(Persoon.Identificatienummers.ActieInhoud, [Actie.SoortNaam]) E= "7"
            Verwacht resultaat: levering volledig bericht.

Given leveringsautorisatie uit autorisatie/Expressietaal_ACTIE_1
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC10T130_xls
When voor persoon 995347529 wordt de laatste handeling geleverd
Then is er 1 bericht geleverd


Scenario:   2. Expressietaal testcases/Expressie_Taal_testen/autorisatie/Expressietaal_ACTIE_2
            LT:
            Expressie refereert aan NIET-bestaande attrwaarde (soortnaam) uit een bijgehouden actie uit de administratieve handeling
            Expressie: ACTIE(Persoon.Identificatienummers.ActieInhoud, [Actie.SoortNaam]) E= "1"
            Verwacht resultaat: GEEN bericht

Given leveringsautorisatie uit autorisatie/Expressietaal_ACTIE_2
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC10T130_xls
When voor persoon 995347529 wordt de laatste handeling geleverd
Then is er geen synchronisatiebericht gevonden


Scenario:   3. Expressietaal testcases/Expressie_Taal_testen/autorisatie/Expressietaal_ACTIE_3
            LT:
            Expressie refereert aan bestaande attrwaarde (rechtsgrondomschrijving) uit actiebron binnen de administratieve handeling
            Expressie: ACTIE(Persoon.Identificatienummers.ActieInhoud, [ActieBron.Rechtsgrondomschrijving]) E= "Omschrijving verdrag"
            Verwacht resultaat: levering volledig bericht.

Given leveringsautorisatie uit autorisatie/Expressietaal_ACTIE_3
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC10T130_xls
When voor persoon 995347529 wordt de laatste handeling geleverd
Then is er 1 bericht geleverd


Scenario:   4. Expressietaal testcases/Expressie_Taal_testen/autorisatie/Expressietaal_ACTIE_4
            LT:
            Expressie refereert aan NIET-bestaande attrwaarde (rechtsgrondomschrijving) uit actiebron binnen de administratieve handeling
            Expressie: ACTIE(Persoon.Identificatienummers.ActieInhoud, [ActieBron.Rechtsgrondomschrijving]) E= "Niet bestaand"
            Verwacht resultaat: GEEN bericht.

Given leveringsautorisatie uit autorisatie/Expressietaal_ACTIE_4
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC10T130_xls
When voor persoon 995347529 wordt de laatste handeling geleverd
Then is er geen synchronisatiebericht gevonden


Scenario:   5. Expressietaal testcases/Expressie_Taal_testen/autorisatie/Expressietaal_ACTIE_5
            LT:
            Expressie refereert aan bestaande attrwaarde (aktenummer) uit document uit bijgehouden acties binnen de administratieve handeling
            Expressie: ACTIE(Persoon.Overlijden.ActieInhoud, [Document.Aktenummer]) E= "20A1234"
            Verwacht resultaat: levering volledig bericht.

Given leveringsautorisatie uit autorisatie/Expressietaal_ACTIE_5
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC10T130_xls
When voor persoon 995347529 wordt de laatste handeling geleverd
Then is er 1 bericht geleverd


Scenario:   6. Expressietaal testcases/Expressie_Taal_testen/autorisatie/Expressietaal_ACTIE_6
            LT:
            Expressie refereert aan bestaande attrwaarde (soortnaam) uit document uit bijgehouden acties binnen de administratieve handeling
            Expressie: ACTIE(Persoon.Overlijden.ActieInhoud, [Document.SoortNaam]) E= "Niet bestaand"
            Verwacht resultaat: GEEN bericht.

Given leveringsautorisatie uit autorisatie/Expressietaal_ACTIE_6
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC10T130_xls
When voor persoon 995347529 wordt de laatste handeling geleverd
Then is er geen synchronisatiebericht gevonden


Scenario:   7. Expressietaal testcases/Expressie_Taal_testen/autorisatie/Expressietaal_ACTIE_7
            LT:
            Expressie refereert aan attrwaarde uit niet bestaande actie in niet bestaande groep
            Expressie: ACTIE(Persoon.Verblijfsrecht.ActieInhoud, [Actie.SoortNaam]) E= "7"
            Verwacht resultaat: GEEN bericht.

Given leveringsautorisatie uit autorisatie/Expressietaal_ACTIE_7
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC10T130_xls
When voor persoon 995347529 wordt de laatste handeling geleverd
Then is er geen synchronisatiebericht gevonden


Scenario:   8. Expressietaal testcases/Expressie_Taal_testen/autorisatie/Expressietaal_ACTIE_8
            LT:
            Expressie refereert aan attrwaarde uit bestaande vervallen actie in bestaande groep, dit levert geen resultaat want expressie gaat
            per definitie over actuele records.
            Expressie: ACTIE(Persoon.AfgeleidAdministratief.ActieVerval, [Actie.SoortNaam]) E= "7"
            Verwacht resultaat: GEEN bericht.

Given leveringsautorisatie uit autorisatie/Expressietaal_ACTIE_8
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC10T130_xls
When voor persoon 995347529 wordt de laatste handeling geleverd
Then is er geen synchronisatiebericht gevonden


Scenario:   9. Expressietaal testcases/Expressie_Taal_testen/autorisatie/Expressietaal_ACTIE_9
            LT:
            Expressie refereert aan attrwaarde uit bestaande aanpassing geldigheid actie in bestaande groep, dit levert geen resultaat want expressie gaat
            per definitie over actuele records.
            Expressie: ACTIE(Persoon.Bijhouding.ActieAanpassingGeldigheid, [Actie.SoortNaam]) E= "7"
            Verwacht resultaat: GEEN bericht.

Given leveringsautorisatie uit autorisatie/Expressietaal_ACTIE_9
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC10T130_xls
When voor persoon 995347529 wordt de laatste handeling geleverd
Then is er geen synchronisatiebericht gevonden
