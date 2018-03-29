Meta:
@status                 Klaar
@sleutelwoorden         xml verzoek berichten

Narrative: verzoek berichten als xml

Scenario: 1. Synchroniseer persoon met XML verzoek
             LT:

Meta:
@status skip

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/autorisatie.txt
Given verzoek synchroniseer persoon met xml verzoek/synchronisatie_persoon.xml transporteur 00000001002220647000 ondertekenaar 00000001002220647000
Then heeft het antwoordbericht verwerking Geslaagd


Scenario: 2. Geef details persoon met xml
             LT:

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/autorisatie.txt
Given verzoek geef details persoon met xml verzoek/geefdetails_goed.xml transporteur 00000001002220647000 ondertekenaar 00000001002220647000


Scenario: 3. Geef details persoon met xml peilmomentMaterieelResultaat ongeldig
             LT:

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/autorisatie.txt
Given verzoek geef details persoon met xml verzoek/geefdetails_peilmomentMaterieelResultaat_ongeldig.xml transporteur 00000001002220647000 ondertekenaar 00000001002220647000

Scenario: 5. Plaats afnemerindicatie met xml datum aanvang materiele periode ongeldig
             LT:

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/autorisatie.txt
Given verzoek plaats afnemerindicatie met xml verzoek/plaats_afnemerindicatie_datum_aanvang_materiele_periode_ongeldig.xml transporteur 00000001002220647000 ondertekenaar 00000001002220647000

Scenario: 6. Plaats afnemerindicatie met xml datum einde volgen ongeldig
             LT:

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/autorisatie.txt
Given verzoek plaats afnemerindicatie met xml verzoek/plaats_afnemerindicatie_datum_einde_volgen_ongeldig.xml transporteur 00000001002220647000 ondertekenaar 00000001002220647000

