Meta:
@status             Klaar
@sleutelwoorden     Expressietaal

Narrative:
In deze story zijn scenario's opgenomen om de expressietaal af te testen tav verantwoording m.b.t. functie AH

Scenario:   1. Expressietaal testcases/Expressie_Taal_testen/autorisatie/Expressietaal_AH_1
            LT:
            Expressie refereert aan bestaande attrwaarde (partijcode) uit de administratieve handeling
            Expressie: AH(Persoon.Identificatienummers.ActieInhoud, [AdministratieveHandeling.PartijCode]) E= 199902
            Verwacht resultaat: levering volledig bericht.

Given leveringsautorisatie uit autorisatie/Expressietaal_AH_1
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC10T130_xls
When voor persoon 995347529 wordt de laatste handeling geleverd
Then is er 1 bericht geleverd


Scenario:   2. Expressietaal testcases/Expressie_Taal_testen/autorisatie/Expressietaal_AH_2
            LT:
            Expressie refereert aan NIET-bestaande attrwaarde (partijcode) uit de administratieve handeling
            Expressie: AH(Persoon.Identificatienummers.ActieInhoud, [AdministratieveHandeling.PartijCode]) E= 123456
            Verwacht resultaat: GEEN bericht.

Given leveringsautorisatie uit autorisatie/Expressietaal_AH_2
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC10T130_xls
When voor persoon 995347529 wordt de laatste handeling geleverd
Then is er geen synchronisatiebericht gevonden


Scenario:   3. Expressietaal testcases/Expressie_Taal_testen/autorisatie/Expressietaal_AH_3
            LT:
            Expressie refereert aan bestaande attrwaarde (datum ontlening) uit bijgehouden acties binnen de administratieve handeling
            Expressie: AH(Persoon.Inschrijving.ActieInhoud, [Actie.SoortNaam]) E= "Conversie GBA"
            Verwacht resultaat: levering volledig bericht.

Given leveringsautorisatie uit autorisatie/Expressietaal_AH_3
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC10T130_xls
When voor persoon 995347529 wordt de laatste handeling geleverd
Then is er 1 bericht geleverd


Scenario:   4. Expressietaal testcases/Expressie_Taal_testen/autorisatie/Expressietaal_AH_4
            LT:
            Expressie refereert aan NIET-bestaande attrwaarde (datum ontlening) uit bijgehouden acties binnen de administratieve handeling
            Expressie: AH(Persoon.Inschrijving.ActieInhoud, [Actie.SoortNaam]) E= "Inschrijving Geboorte"
            Verwacht resultaat: GEEN bericht.

Given leveringsautorisatie uit autorisatie/Expressietaal_AH_4
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC10T130_xls
When voor persoon 995347529 wordt de laatste handeling geleverd
Then is er geen synchronisatiebericht gevonden


Scenario:   5. Expressietaal testcases/Expressie_Taal_testen/autorisatie/Expressietaal_AH_5
            LT:
            Expressie refereert aan bestaande attrwaarde (rechtsgrondomschrijving) uit actiebron binnen de administratieve handeling
            Expressie: AH(Persoon.Identificatienummer.ActieInhoud, [ActieBron.Rechtsgrondomschrijving]) E= "Omschrijving verdrag"
            Verwacht resultaat: levering volledig bericht.

Given leveringsautorisatie uit autorisatie/Expressietaal_AH_5
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC10T130_xls
When voor persoon 995347529 wordt de laatste handeling geleverd
Then is er 1 bericht geleverd


Scenario:   6. Expressietaal testcases/Expressie_Taal_testen/autorisatie/Expressietaal_AH_6
            LT:
            Expressie refereert aan bestaande attrwaarde (rechtsgrondomschrijving) uit actiebron binnen de administratieve handeling
            Expressie: AH(Persoon.Identificatienummer.ActieInhoud, [ActieBron.Rechtsgrondomschrijving]) E= "Niet bestaand"
            Verwacht resultaat: GEEN bericht

Given leveringsautorisatie uit autorisatie/Expressietaal_AH_6
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC10T130_xls
When voor persoon 995347529 wordt de laatste handeling geleverd
Then is er geen synchronisatiebericht gevonden


Scenario:   7. Expressietaal testcases/Expressie_Taal_testen/autorisatie/Expressietaal_AH_7
            LT:
            Expressie refereert aan bestaande attrwaarde (aktenummer) uit document uit bijgehouden acties binnen de administratieve handeling
            Expressie: AH(Persoon.Overlijden.ActieInhoud, [Document.Aktenummer]) E= "20A1234"
            Verwacht resultaat: levering volledig bericht.

Given leveringsautorisatie uit autorisatie/Expressietaal_AH_7
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC10T130_xls
When voor persoon 995347529 wordt de laatste handeling geleverd
Then is er 1 bericht geleverd


Scenario:   8. Expressietaal testcases/Expressie_Taal_testen/autorisatie/Expressietaal_AH_8
            LT:
            Expressie refereert aan bestaande attrwaarde (soortnaam) uit document uit bijgehouden acties binnen de administratieve handeling
            Expressie:  AH(Persoon.Overlijden.ActieInhoud, [Document.SoortNaam]) E= "Niet bestaand"
            Verwacht resultaat: GEEN bericht

Given leveringsautorisatie uit autorisatie/Expressietaal_AH_8
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC10T130_xls
When voor persoon 995347529 wordt de laatste handeling geleverd
Then is er geen synchronisatiebericht gevonden


Scenario:   9. Expressietaal testcases/Expressie_Taal_testen/autorisatie/Expressietaal_AH_9
            LT:
            Expressie refereert aan attrwaarde uit niet bestaande actie in niet bestaande groep
            Expressie: AH(Persoon.Verblijfsrecht.ActieInhoud, [Actie.SoortNaam]) E= "7"
            Verwacht resultaat: GEEN bericht

Given leveringsautorisatie uit autorisatie/Expressietaal_AH_9
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC10T130_xls
When voor persoon 995347529 wordt de laatste handeling geleverd
Then is er geen synchronisatiebericht gevonden



Scenario:   10. Expressietaal testcases/Expressie_Taal_testen/autorisatie/Expressietaal_ACTIE_8
            LT:
            Expressie refereert aan attrwaarde uit bestaande vervallen actie in bestaande groep, dit levert geen resultaat want expressie gaat
            per definitie over actuele records.
            Expressie: AH(Persoon.AfgeleidAdministratief.ActieVerval, [Actie.SoortNaam]) E= "7"
            Verwacht resultaat: GEEN bericht.

Given leveringsautorisatie uit autorisatie/Expressietaal_AH_10
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC10T130_xls
When voor persoon 995347529 wordt de laatste handeling geleverd
Then is er geen synchronisatiebericht gevonden


Scenario:   11. Expressietaal testcases/Expressie_Taal_testen/autorisatie/Expressietaal_ACTIE_9
            LT:
            Expressie refereert aan attrwaarde uit bestaande aanpassing geldigheid actie in bestaande groep, dit levert geen resultaat want expressie gaat
            per definitie over actuele records.
            Expressie: AH(Persoon.Bijhouding.ActieAanpassingGeldigheid, [Actie.SoortNaam]) E= "7"
            Verwacht resultaat: GEEN bericht.

Given leveringsautorisatie uit autorisatie/Expressietaal_AH_11
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC10T130_xls
When voor persoon 995347529 wordt de laatste handeling geleverd
Then is er geen synchronisatiebericht gevonden
