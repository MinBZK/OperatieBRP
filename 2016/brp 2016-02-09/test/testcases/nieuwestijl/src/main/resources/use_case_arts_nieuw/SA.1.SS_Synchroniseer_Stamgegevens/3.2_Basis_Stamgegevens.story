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

Scenario: 3.2.1 Valideer waardes in antwoord op een synchronisatie stamgegeven verzoek
                Telt het aantal elementen in de Element tabel
                Logische testgevallen Use Case: R1331_02, R1332_03, R1979_03
                Verwacht resultaat: Identiek aan scenario 2

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


Scenario: 3.2.2 Tel waardes in antwoord op een synchronisatie stamgegeven verzoek
                Logische testgevallen Use Case : R1331_02, R1332_03, R1979_03
                Verwacht resultaat: 1100 elementen

Given leveringsautorisatie uit /levering_autorisaties/synchronisatie_stamgegeven
Given verzoek voor leveringsautorisatie 'SynchronisatieStamgegeven' en partij 'Gemeente Alkmaar'
Given verzoek van bericht lvg_synGeefSynchronisatieStamgegeven
And testdata uit bestand 2.2_synchronisatie_stamgegeven_basis.yml
And extra waardes:
| SLEUTEL                        | WAARDE
| parameters.stamgegeven         | ElementTabel

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

And heeft het antwoordbericht 1100 groepen 'element'