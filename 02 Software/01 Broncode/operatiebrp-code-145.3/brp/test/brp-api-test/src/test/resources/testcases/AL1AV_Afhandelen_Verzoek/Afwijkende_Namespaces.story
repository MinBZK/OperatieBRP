Meta:
@status             Klaar
@usecase            BV.0.GD
@sleutelwoorden     Geef Details Persoon


Narrative:
Additionele test ter controle dat afwijkende namespaces correct afgehandeld worden i.g.v. bevraging.

Scenario: 1.    Geef detail persoon met afwijkende namespace declaratie
                LT:
                Verwacht Resultaat:
                - Verwerking geslaagd

Given leveringsautorisatie uit /levering_autorisaties_nieuw/R2244/oin_ongelijk_geldig_datumInganggisteren_datumEindemorgen
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek geef details persoon met xml GeefDetailsPersoon-request.xml transporteur 00000001001101857005 ondertekenaar 00000001001101857005

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 2.    Geef detail persoon met afwijkende namespace declaratie
                LT:
                Verwacht Resultaat:
                - Verwerking geslaagd

Given leveringsautorisatie uit /levering_autorisaties_nieuw/R2244/oin_ongelijk_geldig_datumInganggisteren_datumEindemorgen
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek geef details persoon met xml GeefDetailsPersoon-request-2.xml transporteur 00000001001101857005 ondertekenaar 00000001001101857005

Then heeft het antwoordbericht verwerking Geslaagd