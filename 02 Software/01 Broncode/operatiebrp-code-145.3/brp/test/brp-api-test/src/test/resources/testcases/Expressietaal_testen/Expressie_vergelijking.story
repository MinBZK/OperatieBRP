Meta:
@status Klaar

Narrative:
De testen zijn gemaakt om te controleren dat de expressie van logische of vergelijking goed gaat.

!-- De volgende scenario's testen de of vergelijking middels expressie: persoon.nationaliteit.code EIN {1, 2}

Scenario: 1. Anne Bakker nationaliteit.code 1 en nationaliteit.code 67, expressie Persoon.Nationaliteit.NationaliteitCode EIN {0001,0067}
             LT:

Given leveringsautorisatie uit autorisatie/Expressietaal_of_vergelijking_nationaliteit_beide_correct

Given persoonsbeelden uit specials:specials/Anne_Bakker_xls
When voor persoon 595891305 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie nadere populatiebeperking op nationaliteit beide correct is ontvangen en wordt bekeken
Then is er 1 bericht geleverd

Scenario: 2. Anne Bakker nationaliteit.code 1 en nationaliteit.code 67, expressie Persoon.Nationaliteit.NationaliteitCode EIN {0001,0068}
             LT:

Given leveringsautorisatie uit autorisatie/Expressietaal_of_vergelijking_nationaliteit_1_correct

Given persoonsbeelden uit specials:specials/Anne_Bakker_xls
When voor persoon 595891305 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie nadere populatiebeperking op nationaliteit 1 correct is ontvangen en wordt bekeken
Then is er 1 bericht geleverd

Scenario: 3. Anne Bakker nationaliteit.code 1 en nationaliteit.code 67, expressie Persoon.Nationaliteit.NationaliteitCode EIN {0002,0068}
             LT:

Given leveringsautorisatie uit autorisatie/Expressietaal_of_vergelijking_nationaliteit_niet_correct

Given persoonsbeelden uit specials:specials/Anne_Bakker_xls
When voor persoon 595891305 wordt de laatste handeling geleverd

Then is er geen synchronisatiebericht gevonden

!-- De volgende scenario's testen de EN vergelijking middels expressie (persoon.nationaliteit.code E= 1) EN (persoon.nationaliteit.code E= 2)

Scenario: 4. Anne Bakker nationaliteit.code 1 en nationaliteit.code 67, expressie (Persoon.Nationaliteit.NationaliteitCode E= 0001) EN (Persoon.Nationaliteit.NationaliteitCode E= 0067)
             LT:

Given leveringsautorisatie uit autorisatie/Expressietaal_EN_vergelijking_nationaliteit_beide_correct

Given persoonsbeelden uit specials:specials/Anne_Bakker_xls
When voor persoon 595891305 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie nadere populatiebeperking op nationaliteit beide correct is ontvangen en wordt bekeken
Then is er 1 bericht geleverd

Scenario: 5. Anne Bakker nationaliteit.code 1 en nationaliteit.code 67, expressie (Persoon.Nationaliteit.NationaliteitCode E= 0001) EN (Persoon.Nationaliteit.NationaliteitCode E= 0068)
             LT:

Given leveringsautorisatie uit autorisatie/Expressietaal_EN_vergelijking_nationaliteit_1_correct

Given persoonsbeelden uit specials:specials/Anne_Bakker_xls
When voor persoon 595891305 wordt de laatste handeling geleverd

Then is er geen synchronisatiebericht gevonden

Scenario: 6. Anne Bakker nationaliteit.code 1 en nationaliteit.code 67, expressie (Persoon.Nationaliteit.NationaliteitCode E= 0002) EN (Persoon.Nationaliteit.NationaliteitCode E= 0068)
             LT:

Given leveringsautorisatie uit autorisatie/Expressietaal_EN_vergelijking_nationaliteit_niet_correct

Given persoonsbeelden uit specials:specials/Anne_Bakker_xls
When voor persoon 595891305 wordt de laatste handeling geleverd

Then is er geen synchronisatiebericht gevonden

!-- De volgende scenario's testen de EN vergelijking middels expressie: persoon.nationaliteit.code AIN {1, 2}

Scenario: 7. Anne Bakker nationaliteit.code 1 en nationaliteit.code 67, expressie Persoon.Nationaliteit.NationaliteitCode AIN {0001,0067}
             LT:

Given leveringsautorisatie uit autorisatie/Expressietaal_EN_vergelijking_nationaliteit_beide_correct_AIN

Given persoonsbeelden uit specials:specials/Anne_Bakker_xls
When voor persoon 595891305 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie nadere populatiebeperking op nationaliteit beide correct AIN is ontvangen en wordt bekeken
Then is er 1 bericht geleverd

Scenario: 8. Anne Bakker nationaliteit.code 1 en nationaliteit.code 67, expressie Persoon.Nationaliteit.NationaliteitCode AIN {0001,0068}
             LT:

Given leveringsautorisatie uit autorisatie/Expressietaal_EN_vergelijking_nationaliteit_1_correct_AIN

Given persoonsbeelden uit specials:specials/Anne_Bakker_xls
When voor persoon 595891305 wordt de laatste handeling geleverd
Then is er geen synchronisatiebericht gevonden


Scenario: 9. Anne Bakker nationaliteit.code 1 en nationaliteit.code 67, expressie Persoon.Nationaliteit.NationaliteitCode AIN {0002,0068}
             LT:

Given leveringsautorisatie uit autorisatie/Expressietaal_EN_vergelijking_nationaliteit_niet_correct_AIN
Given persoonsbeelden uit specials:specials/Anne_Bakker_xls
When voor persoon 595891305 wordt de laatste handeling geleverd
Then is er geen synchronisatiebericht gevonden


Scenario: 10. Anne Bakker nationaliteit.code 1 en nationaliteit.code 67, expressie Persoon.Nationaliteit.NationaliteitCode groter of gelijk 001
             LT:

Given leveringsautorisatie uit autorisatie/Expressietaal_op_getal_groter_of_gelijk
Given persoonsbeelden uit specials:specials/Anne_Bakker_xls
When voor persoon 595891305 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie nadere populatiebeperking op getal groter of gelijk is ontvangen en wordt bekeken
Then is er 1 bericht geleverd


Scenario: 11. Anne Bakker nationaliteit.code 1 en nationaliteit.code 67, expressie Persoon.Nationaliteit.NationaliteitCode kleiner of gelijk 68
             LT:

Given leveringsautorisatie uit autorisatie/Expressietaal_op_getal_kleiner_of_gelijk
Given persoonsbeelden uit specials:specials/Anne_Bakker_xls
When voor persoon 595891305 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie nadere populatiebeperking op getal kleiner of gelijk is ontvangen en wordt bekeken
Then is er 1 bericht geleverd


Scenario: 12. Anne Bakker nationaliteit.code 1 en nationaliteit.code 67, expressie Persoon.Nationaliteit.NationaliteitCode ongelijk {0002}
             LT:

Given leveringsautorisatie uit autorisatie/Expressietaal_op_getal_ongelijk
Given persoonsbeelden uit specials:specials/Anne_Bakker_xls
When voor persoon 595891305 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie nadere populatiebeperking op getal ongelijk is ontvangen en wordt bekeken
Then is er 1 bericht geleverd


Scenario: 13. Persoon.Adres.Postcode E< {"2252EB"}
              LT:

Given leveringsautorisatie uit autorisatie/Expressietaal_Vergelijking_string_kleiner_dan
Given persoonsbeelden uit specials:specials/Jan_xls
When voor persoon 606417801 wordt de laatste handeling geleverd
Then is er geen synchronisatiebericht gevonden

Scenario: 14. Persoon.Adres.Postcode E<= {"2252EB"}
              LT:

Given leveringsautorisatie uit autorisatie/Expressietaal_Vergelijking_string_kleiner_of_gelijk
Given persoonsbeelden uit specials:specials/Jan_xls
When voor persoon 606417801 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie nadere populatiebeperking op string kleiner of gelijk is ontvangen en wordt bekeken
Then is er 1 bericht geleverd


Scenario: 15. Persoon.Adres.Postcode E>= {"2252EB"}
              LT:

Given leveringsautorisatie uit autorisatie/Expressietaal_Vergelijking_string_groter_of_gelijk
Given persoonsbeelden uit specials:specials/Jan_xls
When voor persoon 606417801 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie nadere populatiebeperking op string groter of gelijk is ontvangen en wordt bekeken
Then is er 1 bericht geleverd

Scenario: 16. Persoon.Adres.Postcode E> {"2252EB"}
              LT:

Given leveringsautorisatie uit autorisatie/Expressietaal_Vergelijking_string_groter_dan
Given persoonsbeelden uit specials:specials/Jan_xls
When voor persoon 606417801 wordt de laatste handeling geleverd
Then is er geen synchronisatiebericht gevonden


Scenario:  17. Lijst van voornamen van de kinderen van de persoon
           LT:
           Expressie: MAP(GerelateerdeKind.Persoon, k, k.SamengesteldeNaam.Voornamen) <= {"Co","Sanne"}
           Verwacht resultaat: WAAR

Given persoonsbeelden uit specials:VolledigBericht/Anne_Bakker_Volledig_xls
Given leveringsautorisatie uit autorisatie/Expressietaal_op_lijst_kleiner_of_gelijk

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'nadere populatiebeperking op lijst kleiner of gelijk'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|595891305
Then heeft het antwoordbericht verwerking Geslaagd


Scenario:  18. Lijst van voornamen van de kinderen van de persoon
           LT:
           Expressie: MAP(GerelateerdeKind.Persoon, k, k.SamengesteldeNaam.Voornamen) < {"Co","Sanne"}
           Verwacht resultaat: ONWAAR

Given persoonsbeelden uit specials:VolledigBericht/Anne_Bakker_Volledig_xls
Given leveringsautorisatie uit autorisatie/Expressietaal_op_lijst_kleiner_dan

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'nadere populatiebeperking op lijst kleiner dan'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|595891305
Then heeft het antwoordbericht verwerking Foutief

Scenario:  19. Lijst van voornamen van de kinderen van de persoon
           LT:
           Expressie: MAP(GerelateerdeKind.Persoon, k, k.SamengesteldeNaam.Voornamen) >= {"Co","Sanne"}
           Verwacht resultaat: WAAR

Given persoonsbeelden uit specials:VolledigBericht/Anne_Bakker_Volledig_xls
Given leveringsautorisatie uit autorisatie/Expressietaal_op_lijst_groter_of_gelijk

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'nadere populatiebeperking op lijst groter of gelijk'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|595891305
Then heeft het antwoordbericht verwerking Geslaagd

Scenario:  20. Lijst van voornamen van de kinderen van de persoon
           LT:
           Expressie: MAP(GerelateerdeKind.Persoon, k, k.SamengesteldeNaam.Voornamen) > {"Co","Sanne"}
           Verwacht resultaat: ONWAAR

Given persoonsbeelden uit specials:VolledigBericht/Anne_Bakker_Volledig_xls
Given leveringsautorisatie uit autorisatie/Expressietaal_op_lijst_groter_dan

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'nadere populatiebeperking op lijst groter dan'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|595891305
Then heeft het antwoordbericht verwerking Foutief

Scenario:  21. Lijst van voornamen van de kinderen van de persoon
           LT:
           Expressie: MAP(GerelateerdeKind.Persoon, k, k.SamengesteldeNaam.Voornamen) <> {"Co","Sanne"}
           Verwacht resultaat: ONWAAR

Given persoonsbeelden uit specials:VolledigBericht/Anne_Bakker_Volledig_xls
Given leveringsautorisatie uit autorisatie/Expressietaal_op_lijst_ongelijk_aan

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'nadere populatiebeperking op lijst ongelijk aan'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|595891305
Then heeft het antwoordbericht verwerking Foutief

Scenario:  22. Lijst van voornamen van de kinderen van de persoon
           LT:
           Expressie: MAP(GerelateerdeKind.Persoon, k, k.SamengesteldeNaam.Voornamen) = {"Co","Sanne"}
           Verwacht resultaat: WAAR

Given persoonsbeelden uit specials:VolledigBericht/Anne_Bakker_Volledig_xls
Given leveringsautorisatie uit autorisatie/Expressietaal_op_lijst_gelijk_aan

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'nadere populatiebeperking op lijst gelijk aan'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|595891305
Then heeft het antwoordbericht verwerking Geslaagd

Scenario:  23. Lijst van voornamen van de kinderen van de persoon
           LT:
           Expressie: MAP(GerelateerdeKind.Persoon, k, k.SamengesteldeNaam.Voornamen) =% {"Co","Sanne"}
           Verwacht resultaat: NULL  (wildcard)

Given persoonsbeelden uit specials:VolledigBericht/Anne_Bakker_Volledig_xls
Given leveringsautorisatie uit autorisatie/Expressietaal_op_lijst_gelijk_aan_wildcard

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'nadere populatiebeperking op lijst gelijk aan wildcard'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|595891305
Then heeft het antwoordbericht verwerking Foutief


Scenario:  24. Persoon.Indicatie.OnderCuratele.Waarde
           LT:
           Expressie: MAP(HISF (GeregistreerdPartnerschap.Standaard), r,r.RedenEindeCode) >= NULL
           Verwacht resultaat: waar (is null)

Given persoonsbeelden uit specials:VolledigBericht/Anne_Bakker_Volledig_xls
Given leveringsautorisatie uit autorisatie/Expressietaal_op_groter_of_gelijk_aan_null

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'nadere populatiebeperking op groter of gelijk aan null'
|zendendePartijNaam|'Gemeente Haarlem'
|bsn|595891305
Then heeft het antwoordbericht verwerking Geslaagd


Scenario:  25. Persoon.Indicatie.OnderCuratele.Waarde
           LT:
           Expressie: MAP(HISF (GeregistreerdPartnerschap.Standaard), r,r.RedenEindeCode) > NULL
           Verwacht resultaat: onwaar (is null)

Given persoonsbeelden uit specials:VolledigBericht/Anne_Bakker_Volledig_xls
Given leveringsautorisatie uit autorisatie/Expressietaal_op_groter_dan_null

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'nadere populatiebeperking op groter dan null'
|zendendePartijNaam|'Gemeente Haarlem'
|bsn|595891305
Then heeft het antwoordbericht verwerking Foutief

Scenario:  26. Persoon.Indicatie.OnderCuratele.Waarde
           LT:
           Expressie: MAP(HISF (GeregistreerdPartnerschap.Standaard), r,r.RedenEindeCode) <= NULL
           Verwacht resultaat: waar (is null)

Given persoonsbeelden uit specials:VolledigBericht/Anne_Bakker_Volledig_xls
Given leveringsautorisatie uit autorisatie/Expressietaal_op_kleiner_of_gelijk_aan_null

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'nadere populatiebeperking op kleiner of gelijk aan null'
|zendendePartijNaam|'Gemeente Haarlem'
|bsn|595891305
Then heeft het antwoordbericht verwerking Geslaagd

Scenario:  27. Persoon.Indicatie.OnderCuratele.Waarde
           LT:
           Expressie: MAP(HISF (GeregistreerdPartnerschap.Standaard), r,r.RedenEindeCode) < NULL
           Verwacht resultaat: onwaar (is null)

Given persoonsbeelden uit specials:VolledigBericht/Anne_Bakker_Volledig_xls
Given leveringsautorisatie uit autorisatie/Expressietaal_op_kleiner_dan_null

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'nadere populatiebeperking op kleiner dan null'
|zendendePartijNaam|'Gemeente Haarlem'
|bsn|595891305
Then heeft het antwoordbericht verwerking Foutief

Scenario:  28. Persoon.Indicatie.OnderCuratele.Waarde
           LT:
           Expressie: MAP(HISF (GeregistreerdPartnerschap.Standaard), r,r.RedenEindeCode) =% NULL
           Verwacht resultaat: onwaar (wildcard is null)

Given persoonsbeelden uit specials:VolledigBericht/Anne_Bakker_Volledig_xls
Given leveringsautorisatie uit autorisatie/Expressietaal_op_gelijk_aan_null_wildcard

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'nadere populatiebeperking op gelijk aan null wildcard'
|zendendePartijNaam|'Gemeente Haarlem'
|bsn|595891305
Then heeft het antwoordbericht verwerking Foutief