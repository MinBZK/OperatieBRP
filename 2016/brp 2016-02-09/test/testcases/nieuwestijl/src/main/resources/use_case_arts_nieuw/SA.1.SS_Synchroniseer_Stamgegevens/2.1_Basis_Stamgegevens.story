Meta:
@epic               Verbeteren testtooling
@auteur             kedon
@status             Klaar
@usecase            SA.0.SS, SA.1.SS, SA.0.AV, LV.1.AV
@regels             R1331,R1332,R1979
@sleutelwoorden     Synchronisatie stamgegeven, Synchroniseer stamgegeven, Afhandeling verzoek, Afhandelen verzoek


Narrative:
De nieuwe Element tabel zal worden gebruikt om verwijzingen te communiceren naar afnemers. Bijvoorbeeld om aan te geven welke gegevens
in onderzoek staan (waaronder ook onderzoek naar ontbrekende gegevens, die dus zelf niet in het bericht staan). Mogelijk gaan afnemers
in de toekomst dit ook gebruiken om zelf te verwijzen (bijvoorbeeld voor terugmelding of scoping van bevraging). Het is dus noodzakelijk
dat de afnemers de nieuwe Elementtabel kennen zodat ze die verwijzingen kunnen interpreteren.

Echter de nieuwe Element tabel bevat ook veel inhoud die voor de afnemers niet relevant is. Dat betreft zowel kolommen (o.a. technische
verwijzingen naar de database) als rijen (gegevens die geen onderdeel van een levering kunnen zijn). Verwerkingsregel VR00111 beschrijft
de juiste inperking en sortering bij het leveren van de inhoud van de nieuwe Element tabel aan Afnemers. Daarnaast is in BRLV0024 de
opsomming van potentieel te leveren stamtabellen uitgebreid met de tabel Element.

Alleen een aantal willekeurige elementen worden gecontroleerd aangezien het wat ver gaat om 1500 elementen te gaan controleren.

Scenario: 2.1 Valideer waardes in antwoord op een synchronisatie stamgegeven verzoek
               Logische testgevallen LV.1.AV Afhandelen Verzoek LV.1.AB:   R1268_07, R1269_05
               Logische testgevallen LV.1.AV Afhandelen Verzoek LV.1.XV:   Geen testgevallen
               Logische testgevallen LV.1.AV Afhandelen Verzoek LV.1.AU:   R1257_01, R1258_03, R2052_01
               Logische testgevallen LV.1.AV Afhandelen Verzoek LV.1.AL:   R1260_01, R1261_03, R1262_03, R1263_01, R1264_01, R2053_01, R2055_01, R2056_01
               Logische testgevallen SA.1.SS Synchronisser Stamgegevens:   R1331_02, R1332_02, R1979_02
               Logische testgevallen LV.1.AV Afhandelen Verzoek LV.1.MR:   R1266_01, R1410_01
               Logische testgevallen LV.1.AV Afhandelen Verzoek LV.1.VB:   R1985_01, R1991_01
               Logische testgevallen LV.1.AV Afhandelen Verzoek LV.1.AB:   R1268_08, R1270_07
             Verwacht resultaat: responseberichten
                             Met vulling:
                             - Tabelinhoud per stamgegeven

Given leveringsautorisatie uit /levering_autorisaties/synchronisatie_stamgegeven
Given verzoek voor leveringsautorisatie 'SynchronisatieStamgegeven' en partij 'Gemeente Alkmaar'
Given verzoek van bericht lvg_synGeefSynchronisatieStamgegeven
And testdata uit bestand 2.2_synchronisatie_stamgegeven_basis.yml
And extra waardes:
| SLEUTEL                        | WAARDE
| parameters.stamgegeven         | <stamgegeven>

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
And heeft in het antwoordbericht '<attribuut>' in '<groep>' de waardes '<waarde>'

Examples:
| <stamgegeven>                          | groep                                  | attribuut | waarde
| BijhoudingsaardTabel                   | bijhoudingsaardTabel                   | naam      | Ingezetene, Niet-ingezetene, Onbekend
| BijhoudingsresultaatTabel              | bijhoudingsresultaat                   | naam      | Verwerkt, Uitgesteld, Direct, Conform plan
| CategorieAdministratieveHandelingTabel | categorieAdministratieveHandelingTabel | naam      | Actualisering, Correctie, Synchronisatie, GBA - Initiele vulling, GBA - Synchronisatie
| EffectAfnemerindicatiesTabel           | effectAfnemerindicaties                | naam      | Plaatsing, Verwijdering
| FunctieAdresTabel                      | functieAdres                           | naam      | Woonadres, Briefadres
| GeslachtsaanduidingTabel               | geslachtsaanduiding                    | code      | M, V, O
| NaamgebruikTabel                       | naamgebruik                            | code      | E, P, V, N
| PredicaatTabel                         | predicaat                              | code      | K, H, J
| RedenWijzigingVerblijfTabel            | redenWijzigingVerblijf                 | code      | P, A, M, B, I, ?
| RolTabel                               | rol                                    | naam      | Afnemer, Bijhoudingsorgaan College, Bijhoudingsorgaan Minister
| SoortMeldingTabel                      | soortMelding                           | naam      | Geen, Informatie, Waarschuwing, Deblokkeerbaar, Fout
| SoortMigratieTabel                     | soortMigratie                          | naam      | Immigratie, Emigratie
| SoortPartijOnderzoekTabel              | soortPartijOnderzoek                   | naam      | Eigenaar, Gevoegde
| SoortPersoonTabel                      | soortPersoon                           | naam      | Ingeschrevene, Niet-ingeschrevene, Onbekend
| SoortPersoonOnderzoekTabel             | soortPersoonOnderzoek                  | naam      | Direct, Indirect
| SoortSynchronisatieTabel               | soortSynchronisatie                    | naam      | Mutatiebericht, Volledigbericht
| StatusOnderzoekTabel                   | statusOnderzoek                        | naam      | In uitvoering, Gestaakt, Afgesloten
| VerwerkingsresultaatTabel              | verwerkingsresultaat                   | naam      | Geslaagd, Foutief
| VerwerkingswijzeTabel                  | verwerkingswijze                       | naam      | Bijhouding, Prevalidatie
| SoortDienstTabel                       | soortDienst                            | naam      | Mutatielevering op basis van doelbinding, Mutatielevering op basis van afnemerindicatie, Plaatsen afnemerindicatie, Attendering, Zoek persoon, Zoek persoon op adresgegevens, Geef medebewoners van persoon, Geef details persoon, Synchronisatie persoon, Synchronisatie stamgegeven, Mutatielevering stamgegeven, Selectie, Geef details persoon bulk, Geef synchroniciteitsgegevens persoon, Geef identificerende gegevens persoon bulk, Geef details terugmelding, Opvragen aantal personen op adres, Aanmelding gerede twijfel, Intrekking terugmelding, Verwijderen afnemerindicatie


Scenario: 2.2   Tel waardes in antwoord op een synchronisatie stamgegeven verzoek
                Logische testgevallen Use Case: R1331_02, R1332_02, R1979_02
                 Verwacht resultaat: 1101 Elementen

Given verzoek voor leveringsautorisatie 'SynchronisatieStamgegeven' en partij 'Gemeente Alkmaar'
Given verzoek van bericht lvg_synGeefSynchronisatieStamgegeven
And testdata uit bestand 2.2_synchronisatie_stamgegeven_basis.yml
And extra waardes:
| SLEUTEL                        | WAARDE
| parameters.stamgegeven         | ElementTabel

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

And heeft het antwoordbericht 1101 groepen 'element'

Scenario: 2.3   Elementtabel

Given verzoek voor leveringsautorisatie 'SynchronisatieStamgegeven' en partij 'Gemeente Alkmaar'
Given verzoek van bericht lvg_synGeefSynchronisatieStamgegeven
And testdata uit bestand 1.2_synchronisatie_stamgegevens_element.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then is in antwoordbericht de aanwezigheid van 'naam' in 'element' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'soortNaam' in 'element' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'datumAanvangGeldigheid' in 'element' nummer 1 ja

Scenario: 2.4   aanduidingInhoudingVermissingReisdocumentTabel

Given verzoek voor leveringsautorisatie 'SynchronisatieStamgegeven' en partij 'Gemeente Alkmaar'
Given verzoek van bericht lvg_synGeefSynchronisatieStamgegeven
And testdata uit bestand 2.3_synchronisatie_stamgegeven_aanduidingInhoudingVermissingReisdocument.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then is in antwoordbericht de aanwezigheid van 'code' in 'aanduidingInhoudingVermissingReisdocument' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'naam' in 'aanduidingInhoudingVermissingReisdocument' nummer 1 ja

Scenario: 2.5   aanduiding verblijfsrechtTabel

Given verzoek voor leveringsautorisatie 'SynchronisatieStamgegeven' en partij 'Gemeente Alkmaar'
Given verzoek van bericht lvg_synGeefSynchronisatieStamgegeven
And testdata uit bestand 2.4_AanduidingVerblijfsrechtTabel.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then is in antwoordbericht de aanwezigheid van 'code' in 'aanduidingVerblijfsrecht' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'omschrijving' in 'aanduidingVerblijfsrecht' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'datumAanvangGeldigheid' in 'aanduidingVerblijfsrecht' nummer 1 ja

Scenario: 2.6   Aangever Tabel

Given verzoek voor leveringsautorisatie 'SynchronisatieStamgegeven' en partij 'Gemeente Alkmaar'
Given verzoek van bericht lvg_synGeefSynchronisatieStamgegeven
And testdata uit bestand 2.5_AangeverTabel.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then is in antwoordbericht de aanwezigheid van 'naam' in 'aangever' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'code' in 'aangever' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'omschrijving' in 'aangever' nummer 1 ja

Scenario: 2.7   Adellijke titel Tabel

Given verzoek voor leveringsautorisatie 'SynchronisatieStamgegeven' en partij 'Gemeente Alkmaar'
Given verzoek van bericht lvg_synGeefSynchronisatieStamgegeven
And testdata uit bestand 2.6_AdellijkeTitelTabel.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then is in antwoordbericht de aanwezigheid van 'code' in 'adellijkeTitel' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'naamMannelijk' in 'adellijkeTitel' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'naamVrouwelijk' in 'adellijkeTitel' nummer 1 ja

Scenario: 2.8   Autoriteittype van afgifte reisdocument Tabel
                Bevinding: JIRA ISSUE TEAMBRP-4563 Datum Aanvang Geldigheid ontbreekt in responsebericht
Meta:
@status     Bug

Given verzoek voor leveringsautorisatie 'SynchronisatieStamgegeven' en partij 'Gemeente Alkmaar'
Given verzoek van bericht lvg_synGeefSynchronisatieStamgegeven
And testdata uit bestand 2.7_Autoriteittype_van_afgifte_reisdocument_Tabel.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then is in antwoordbericht de aanwezigheid van 'code' in 'autoriteittypeVanAfgifteReisdocument' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'naam' in 'autoriteittypeVanAfgifteReisdocument' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'datumAanvangGeldigheid' in 'autoriteittypeVanAfgifteReisdocument' nummer 1 ja

Scenario: 2.9  Bijhoudingsaard Tabel

Given verzoek voor leveringsautorisatie 'SynchronisatieStamgegeven' en partij 'Gemeente Alkmaar'
Given verzoek van bericht lvg_synGeefSynchronisatieStamgegeven
And testdata uit bestand 2.8_BijhoudingsaardTabel.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then is in antwoordbericht de aanwezigheid van 'naam' in 'bijhoudingsaard' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'code' in 'bijhoudingsaard' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'omschrijving' in 'bijhoudingsaard' nummer 1 ja

Scenario: 2.10  Bijhoudingsresultaat Tabel

Given verzoek voor leveringsautorisatie 'SynchronisatieStamgegeven' en partij 'Gemeente Alkmaar'
Given verzoek van bericht lvg_synGeefSynchronisatieStamgegeven
And testdata uit bestand 2.9_BijhoudingsresultaatTabel.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then is in antwoordbericht de aanwezigheid van 'naam' in 'bijhoudingsresultaat' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'omschrijving' in 'bijhoudingsresultaat' nummer 1 ja

Scenario: 2.11  Bijhoudingsresultaat Tabel

Given verzoek voor leveringsautorisatie 'SynchronisatieStamgegeven' en partij 'Gemeente Alkmaar'
Given verzoek van bericht lvg_synGeefSynchronisatieStamgegeven
And testdata uit bestand 2.10_Categorie_administratieve_handeling_Tabel.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then is in antwoordbericht de aanwezigheid van 'naam' in 'categorieAdministratieveHandeling' nummer 1 ja


Scenario: 2.14  Effect afnemerindicaties Tabel

Given verzoek voor leveringsautorisatie 'SynchronisatieStamgegeven' en partij 'Gemeente Alkmaar'
Given verzoek van bericht lvg_synGeefSynchronisatieStamgegeven
And testdata uit bestand 2.13_Effect_afnemerindicaties_Tabel.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then is in antwoordbericht de aanwezigheid van 'naam' in 'effectAfnemerindicaties' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'omschrijving' in 'effectAfnemerindicaties' nummer 1 ja

Scenario: 2.15 Functie adres Tabel

Given verzoek voor leveringsautorisatie 'SynchronisatieStamgegeven' en partij 'Gemeente Alkmaar'
Given verzoek van bericht lvg_synGeefSynchronisatieStamgegeven
And testdata uit bestand 2.14_Functie_Adres_Tabel.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then is in antwoordbericht de aanwezigheid van 'naam' in 'functieAdres' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'code' in 'functieAdres' nummer 1 ja

Scenario: 2.16  Gemeente Tabel
                Bevinding: JIRA ISSUE TEAMBRP-4563 Datum Aanvang Geldigheid ontbreekt in responsebericht
Meta:
@status     Bug

Given verzoek voor leveringsautorisatie 'SynchronisatieStamgegeven' en partij 'Gemeente Alkmaar'
Given verzoek van bericht lvg_synGeefSynchronisatieStamgegeven
And testdata uit bestand 2.15_Gemeente_Tabel.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then is in antwoordbericht de aanwezigheid van 'naam' in 'gemeente' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'code' in 'gemeente' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'partijCode' in 'gemeente' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'datumAanvangGeldigheid' in 'gemeente' nummer 1 ja

Scenario: 2.17  Geslachtsaanduiding Tabel
                Bevinding: JIRA ISSUE TEAMBRP-4563 omschrijving ontbreekt in het responsebericht

Meta:
@status     Bug

Given verzoek voor leveringsautorisatie 'SynchronisatieStamgegeven' en partij 'Gemeente Alkmaar'
Given verzoek van bericht lvg_synGeefSynchronisatieStamgegeven
And testdata uit bestand 2.16_Geslachtsaanduiding_Tabel.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then is in antwoordbericht de aanwezigheid van 'naam' in 'geslachtsaanduiding' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'code' in 'geslachtsaanduiding' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'omschrijving' in 'geslachtsaanduiding' nummer 1 ja

Scenario: 2.18 Land/Gebied Tabel
                Bevinding: JIRA ISSUE TEAMBRP-4563 Datum Aanvang Geldigheid ontbreekt in responsebericht
Meta:
@status     Bug

Given verzoek voor leveringsautorisatie 'SynchronisatieStamgegeven' en partij 'Gemeente Alkmaar'
Given verzoek van bericht lvg_synGeefSynchronisatieStamgegeven
And testdata uit bestand 2.17_Land_Gebied_Tabel.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then is in antwoordbericht de aanwezigheid van 'naam' in 'landGebied' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'code' in 'landGebied' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'datumAanvangGeldigheid' in 'landGebied' nummer 1 ja

Scenario: 2.19 Naamgebruik Tabel

Given verzoek voor leveringsautorisatie 'SynchronisatieStamgegeven' en partij 'Gemeente Alkmaar'
Given verzoek van bericht lvg_synGeefSynchronisatieStamgegeven
And testdata uit bestand 2.18_Naamgebruik_Tabel.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then is in antwoordbericht de aanwezigheid van 'naam' in 'naamgebruik' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'code' in 'naamgebruik' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'omschrijving' in 'naamgebruik' nummer 1 ja

Scenario: 2.20 Nadere Bijhoudingsaard Tabel

Given verzoek voor leveringsautorisatie 'SynchronisatieStamgegeven' en partij 'Gemeente Alkmaar'
Given verzoek van bericht lvg_synGeefSynchronisatieStamgegeven
And testdata uit bestand 2.19_Nadere_Bijhoudingsaard_Tabel.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then is in antwoordbericht de aanwezigheid van 'naam' in 'nadereBijhoudingsaard' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'code' in 'nadereBijhoudingsaard' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'omschrijving' in 'nadereBijhoudingsaard' nummer 1 ja

Scenario: 2.21 Nationaliteit Tabel
            Bevinding: JIRA ISSUE TEAMBRP-4563 Datum Aanvang Geldigheid ontbreekt in responsebericht
Meta:
@status     Bug

Given verzoek voor leveringsautorisatie 'SynchronisatieStamgegeven' en partij 'Gemeente Alkmaar'
Given verzoek van bericht lvg_synGeefSynchronisatieStamgegeven
And testdata uit bestand 2.20_Nationaliteit_Tabel.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then is in antwoordbericht de aanwezigheid van 'naam' in 'nationaliteit' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'code' in 'nationaliteit' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'datumAanvangGeldigheid' in 'nationaliteit' nummer 1 ja

Scenario: 2.22 Partij Tabel
         Bevinding: JIRA ISSUE TEAMBRP-4563 In het responsebericht staat niet datumAanvangGeldigheid, maar datumAanvang

Meta:
@status     Bug

Given verzoek voor leveringsautorisatie 'SynchronisatieStamgegeven' en partij 'Gemeente Alkmaar'
Given verzoek van bericht lvg_synGeefSynchronisatieStamgegeven
And testdata uit bestand 2.21_Partij_Tabel.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then is in antwoordbericht de aanwezigheid van 'naam' in 'partij' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'soortNaam' in 'partij' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'code' in 'partij' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'datumAanvangGeldigheid' in 'partij' nummer 1 ja

Scenario: 2.23 Plaats Tabel

Given verzoek voor leveringsautorisatie 'SynchronisatieStamgegeven' en partij 'Gemeente Alkmaar'
Given verzoek van bericht lvg_synGeefSynchronisatieStamgegeven
And testdata uit bestand 2.22_Plaats_Tabel.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then is in antwoordbericht de aanwezigheid van 'naam' in 'plaats' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'code' in 'plaats' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'datumAanvangGeldigheid' in 'plaats' nummer 1 ja

Scenario: 2.24 Predicaat Tabel

Given verzoek voor leveringsautorisatie 'SynchronisatieStamgegeven' en partij 'Gemeente Alkmaar'
Given verzoek van bericht lvg_synGeefSynchronisatieStamgegeven
And testdata uit bestand 2.23_Predicaat_Tabel.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then is in antwoordbericht de aanwezigheid van 'code' in 'predicaat' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'naamMannelijk' in 'predicaat' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'naamVrouwelijk' in 'predicaat' nummer 1 ja

Scenario: 2.25 Rechtsgrond Tabel
            Bevinding: JIRA ISSUE TEAMBRP-4563 de rechtsgrond Tabel geeft niks terug in het responsebericht
Meta:
@status     Bug

Given verzoek voor leveringsautorisatie 'SynchronisatieStamgegeven' en partij 'Gemeente Alkmaar'
Given verzoek van bericht lvg_synGeefSynchronisatieStamgegeven
And testdata uit bestand 2.24_Rechtsgrond_Tabel.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then is in antwoordbericht de aanwezigheid van 'naam' in 'rechtsgrond' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'soortNaam' in 'rechtsgrond' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'omschrijving' in 'rechtsgrond' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'datumAanvangGeldigheid' in 'rechtsgrond' nummer 1 ja

Scenario: 2.26 Reden Einde Relatie Tabel

Given verzoek voor leveringsautorisatie 'SynchronisatieStamgegeven' en partij 'Gemeente Alkmaar'
Given verzoek van bericht lvg_synGeefSynchronisatieStamgegeven
And testdata uit bestand 2.25_Reden_Einde_Relatie_Tabel.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then is in antwoordbericht de aanwezigheid van 'code' in 'redenEindeRelatie' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'omschrijving' in 'redenEindeRelatie' nummer 1 ja

Scenario: 2.27 Reden Verkrijging Nederlandse Nationaliteit Tabel

Given verzoek voor leveringsautorisatie 'SynchronisatieStamgegeven' en partij 'Gemeente Alkmaar'
Given verzoek van bericht lvg_synGeefSynchronisatieStamgegeven
And testdata uit bestand 2.26_Reden_Verkrijging_Nederlandse_Nationaliteit_Tabel.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then is in antwoordbericht de aanwezigheid van 'code' in 'redenVerkrijgingNLNationaliteit' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'omschrijving' in 'redenVerkrijgingNLNationaliteit' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'datumAanvangGeldigheid' in 'redenVerkrijgingNLNationaliteit' nummer 1 ja

Scenario: 2.28 Reden Verlies Nederlandse Nationaliteit Tabel

Given verzoek voor leveringsautorisatie 'SynchronisatieStamgegeven' en partij 'Gemeente Alkmaar'
Given verzoek van bericht lvg_synGeefSynchronisatieStamgegeven
And testdata uit bestand 2.27_Reden_Verlies_Nederlandse_Nationaliteit_Tabel.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then is in antwoordbericht de aanwezigheid van 'code' in 'redenVerliesNLNationaliteit' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'omschrijving' in 'redenVerliesNLNationaliteit' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'datumAanvangGeldigheid' in 'redenVerliesNLNationaliteit' nummer 1 ja

Scenario: 2.29 Reden Wijziging Verblijf Tabel

Given verzoek voor leveringsautorisatie 'SynchronisatieStamgegeven' en partij 'Gemeente Alkmaar'
Given verzoek van bericht lvg_synGeefSynchronisatieStamgegeven
And testdata uit bestand 2.28_Reden_Wijziging_Verblijf_Tabel.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then is in antwoordbericht de aanwezigheid van 'code' in 'redenWijzigingVerblijf' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'naam' in 'redenWijzigingVerblijf' nummer 1 ja

Scenario: 2.30 Rol Tabel

Given verzoek voor leveringsautorisatie 'SynchronisatieStamgegeven' en partij 'Gemeente Alkmaar'
Given verzoek van bericht lvg_synGeefSynchronisatieStamgegeven
And testdata uit bestand 2.29_Rol_Tabel.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then is in antwoordbericht de aanwezigheid van 'naam' in 'rol' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'datumAanvangGeldigheid' in 'rol' nummer 1 ja

Scenario: 2.31 Soort administratieve handeling Tabel

Given verzoek voor leveringsautorisatie 'SynchronisatieStamgegeven' en partij 'Gemeente Alkmaar'
Given verzoek van bericht lvg_synGeefSynchronisatieStamgegeven
And testdata uit bestand 2.30_Soort_Administratieve_Handeling_Tabel.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then is in antwoordbericht de aanwezigheid van 'naam' in 'soortAdministratieveHandeling' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'code' in 'soortAdministratieveHandeling' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'categorie' in 'soortAdministratieveHandeling' nummer 1 ja

Scenario: 2.32 Soort document Tabel

Given verzoek voor leveringsautorisatie 'SynchronisatieStamgegeven' en partij 'Gemeente Alkmaar'
Given verzoek van bericht lvg_synGeefSynchronisatieStamgegeven
And testdata uit bestand 2.31_Soort_Document_Tabel.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then is in antwoordbericht de aanwezigheid van 'naam' in 'soortDocument' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'omschrijving' in 'soortDocument' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'rangorde' in 'soortDocument' nummer 1 ja

Scenario: 2.33 Soort melding Tabel

Given verzoek voor leveringsautorisatie 'SynchronisatieStamgegeven' en partij 'Gemeente Alkmaar'
Given verzoek van bericht lvg_synGeefSynchronisatieStamgegeven
And testdata uit bestand 2.32_Soort_Melding_Tabel.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then is in antwoordbericht de aanwezigheid van 'naam' in 'soortMelding' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'omschrijving' in 'soortMelding' nummer 1 ja

Scenario: 2.34 Soort migratie Tabel

Given verzoek voor leveringsautorisatie 'SynchronisatieStamgegeven' en partij 'Gemeente Alkmaar'
Given verzoek van bericht lvg_synGeefSynchronisatieStamgegeven
And testdata uit bestand 2.33_Soort_Migratie_Tabel.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then is in antwoordbericht de aanwezigheid van 'naam' in 'soortMigratie' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'code' in 'soortMigratie' nummer 1 ja

Scenario: 2.35 Soort Nederlands Reisdocument Tabel

Given verzoek voor leveringsautorisatie 'SynchronisatieStamgegeven' en partij 'Gemeente Alkmaar'
Given verzoek van bericht lvg_synGeefSynchronisatieStamgegeven
And testdata uit bestand 2.34_Soort_Nederlands_Reisdocument_Tabel.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then is in antwoordbericht de aanwezigheid van 'code' in 'soortNederlandsReisdocument' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'omschrijving' in 'soortNederlandsReisdocument' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'datumAanvangGeldigheid' in 'soortNederlandsReisdocument' nummer 1 ja

Scenario: 2.36 Soort partij Tabel
            Bevinding: JIRA ISSUE TEAMBRP-4563 Datum Aanvang Geldigheid ontbreekt in responsebericht

Meta:
@status             Bug

Given verzoek voor leveringsautorisatie 'SynchronisatieStamgegeven' en partij 'Gemeente Alkmaar'
Given verzoek van bericht lvg_synGeefSynchronisatieStamgegeven
And testdata uit bestand 2.35_Soort_Partij_Tabel.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then is in antwoordbericht de aanwezigheid van 'code' in 'soortPartij' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'datumAanvangGeldigheid' in 'soortPartij' nummer 1 ja

Scenario: 2.37 Soort partij onderzoek Tabel

Given verzoek voor leveringsautorisatie 'SynchronisatieStamgegeven' en partij 'Gemeente Alkmaar'
Given verzoek van bericht lvg_synGeefSynchronisatieStamgegeven
And testdata uit bestand 2.36_Soort_Partij_Onderzoek_Tabel.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then is in antwoordbericht de aanwezigheid van 'naam' in 'soortPartijOnderzoek' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'omschrijving' in 'soortPartijOnderzoek' nummer 1 ja

Scenario: 2.38 Soort persoon Tabel
            Bevinding: JIRA ISSUE TEAMBRP-4563 Datum Aanvang Geldigheid ontbreekt in responsebericht

Meta:
@status             Bug

Given verzoek voor leveringsautorisatie 'SynchronisatieStamgegeven' en partij 'Gemeente Alkmaar'
Given verzoek van bericht lvg_synGeefSynchronisatieStamgegeven
And testdata uit bestand 2.37_Soort_Persoon_Tabel.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then is in antwoordbericht de aanwezigheid van 'code' in 'soortPersoon' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'naam' in 'soortPersoon' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'omschrijving' in 'soortPersoon' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'datumAanvangGeldigheid' in 'soortPersoon' nummer 1 ja

Scenario: 2.39 Soort persoon onderzoek Tabel

Given verzoek voor leveringsautorisatie 'SynchronisatieStamgegeven' en partij 'Gemeente Alkmaar'
Given verzoek van bericht lvg_synGeefSynchronisatieStamgegeven
And testdata uit bestand 2.38_Soort_Persoon_Onderzoek_Tabel.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then is in antwoordbericht de aanwezigheid van 'naam' in 'soortPersoonOnderzoek' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'omschrijving' in 'soortPersoonOnderzoek' nummer 1 ja

Scenario: 2.40 Soort rechtsgrond Tabel
            Bevinding: JIRA ISSUE TEAMBRP-4563 naam ontbreekt in responsebericht

Meta:
@status             Bug

Given verzoek voor leveringsautorisatie 'SynchronisatieStamgegeven' en partij 'Gemeente Alkmaar'
Given verzoek van bericht lvg_synGeefSynchronisatieStamgegeven
And testdata uit bestand 2.39_Soort_Rechtsgrond_Tabel.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then is in antwoordbericht de aanwezigheid van 'naam' in 'soortRechtsgrond' nummer 1 ja

Scenario: 2.41 Soort synchronisatie Tabel

Given verzoek voor leveringsautorisatie 'SynchronisatieStamgegeven' en partij 'Gemeente Alkmaar'
Given verzoek van bericht lvg_synGeefSynchronisatieStamgegeven
And testdata uit bestand 2.40_Soort_Synchronisatie_Tabel.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then is in antwoordbericht de aanwezigheid van 'naam' in 'soortSynchronisatie' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'omschrijving' in 'soortSynchronisatie' nummer 1 ja


Scenario: 2.42 Status Onderzoek Tabel

Given verzoek voor leveringsautorisatie 'SynchronisatieStamgegeven' en partij 'Gemeente Alkmaar'
Given verzoek van bericht lvg_synGeefSynchronisatieStamgegeven
And testdata uit bestand 2.41_Status_Onderzoek_Tabel.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then is in antwoordbericht de aanwezigheid van 'naam' in 'statusOnderzoek' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'omschrijving' in 'statusOnderzoek' nummer 1 ja

Scenario: 2.43 Status Terugmelding Tabel

Given verzoek voor leveringsautorisatie 'SynchronisatieStamgegeven' en partij 'Gemeente Alkmaar'
Given verzoek van bericht lvg_synGeefSynchronisatieStamgegeven
And testdata uit bestand 2.42_Status_Terugmelding_Tabel.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then is in antwoordbericht de aanwezigheid van 'naam' in 'statusTerugmelding' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'omschrijving' in 'statusTerugmelding' nummer 1 ja

Scenario: 2.44 Verwerkingsresultaat Tabel

Given verzoek voor leveringsautorisatie 'SynchronisatieStamgegeven' en partij 'Gemeente Alkmaar'
Given verzoek van bericht lvg_synGeefSynchronisatieStamgegeven
And testdata uit bestand 2.43_Verwerkingsresultaat_Tabel.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then is in antwoordbericht de aanwezigheid van 'naam' in 'verwerkingsresultaat' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'omschrijving' in 'verwerkingsresultaat' nummer 1 ja

Scenario: 2.45 Verwerkingswijze Tabel

Given verzoek voor leveringsautorisatie 'SynchronisatieStamgegeven' en partij 'Gemeente Alkmaar'
Given verzoek van bericht lvg_synGeefSynchronisatieStamgegeven
And testdata uit bestand 2.44_Verwerkingswijze_Tabel.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then is in antwoordbericht de aanwezigheid van 'naam' in 'verwerkingswijze' nummer 1 ja
Then is in antwoordbericht de aanwezigheid van 'omschrijving' in 'verwerkingswijze' nummer 1 ja

Scenario: 2.46 Voorvoegsel Tabel

Given verzoek voor leveringsautorisatie 'SynchronisatieStamgegeven' en partij 'Gemeente Alkmaar'
Given verzoek van bericht lvg_synGeefSynchronisatieStamgegeven
And testdata uit bestand 2.45_Voorvoegsel_Tabel.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then is in antwoordbericht de aanwezigheid van 'voorvoegsel' in 'voorvoegsel' nummer 1 ja


Scenario: 2.47 SoortDienst Tabel

Given verzoek voor leveringsautorisatie 'SynchronisatieStamgegeven' en partij 'Gemeente Alkmaar'
Given verzoek van bericht lvg_synGeefSynchronisatieStamgegeven
And testdata uit bestand 2.47_SoortDienst_Tabel.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
Then is in antwoordbericht de aanwezigheid van 'naam' in 'soortDienst' nummer 1 ja