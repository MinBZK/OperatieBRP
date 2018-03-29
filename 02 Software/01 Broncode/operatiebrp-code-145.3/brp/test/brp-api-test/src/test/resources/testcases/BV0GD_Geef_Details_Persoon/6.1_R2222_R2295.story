Meta:

@status             Klaar
@usecase            BV.0.GD
@sleutelwoorden     Geef Details Persoon
@regels             R2222

Narrative:
Een peilmoment formeel mag, indien gevuld, niet na 'Systeemdatum' (R2016) liggen.

Scenario: 1.    Bericht.Peilmoment formeel resultaat < systeemdatum
                LT: R2222_LT01
                Verwacht resultaat:
                - Geef details persoon succesvol verwerkt
                - synchroon antwoord bericht geef details persoon geleverd


Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls
Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|606417801|2014-12-31 T23:59:00Z

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|historievorm|Geen
|peilmomentMaterieelResultaat|GISTEREN
|peilmomentFormeelResultaat |GISTEREN

Then heeft het antwoordbericht verwerking Geslaagd


Scenario: 2.    Bericht.Peilmoment formeel resultaat = systeemdatum
                LT: R2222_LT02
                Verwacht resultaat:
                - Geef details persoon succesvol verwerkt
                - synchroon antwoord bericht geef details persoon geleverd

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|historievorm|Geen
|peilmomentMaterieelResultaat|GISTEREN
|peilmomentFormeelResultaat |VANDAAG

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 3.    Bericht.Peilmoment formeel resultaat = LEEG
                LT: R2222_LT05
                Verwacht resultaat:
                - Geef details persoon succesvol verwerkt
                - synchroon antwoord bericht geef details persoon geleverd

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 4.    Bericht.Peilmoment formeel resultaat > systeemdatum
                LT: R2222_LT04
                Verwacht resultaat:
                - Foutmelding
                - Peildatum mag niet in de toekomst liggen.

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|historievorm|Geen
|peilmomentMaterieelResultaat|GISTEREN
|peilmomentFormeelResultaat |MORGEN

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                                                    |
| R2222     | Peildatum mag niet in de toekomst liggen.


Scenario: 5.    Bericht.Peilmoment materieel resultaat > systeemdatum
                LT: R2295_LT07
                Verwacht resultaat:
                - Foutmelding
                - Peilmoment materieel mag niet in de toekomst liggen.

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Jan_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
|historievorm|Geen
|peilmomentMaterieelResultaat|MORGEN

Then heeft het antwoordbericht verwerking Foutief
And heeft het antwoordbericht de meldingen:
| CODE      | MELDING                                                                                                    |
| R2295     | Peilmoment materieel mag niet in de toekomst liggen.