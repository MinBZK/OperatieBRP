Meta:
@status             Klaar
@sleutelwoorden     Verstrekkingsbeperking
@usecase            BV.0.GD

Narrative:

Wanneer gegevens van een persoon worden geleverd én de 'Persoon heeft een actuele(1) verstrekkingsbeperking'
(zie 'Persoon heeft verstrekkingsbeperking' (R1341)) dan bevat het uitgaande bericht een Melding voor die Persoon met de
Melding.Soort "Waarschuwing" (ongeacht aan welke Partij wordt geleverd).

(Toelichting: dit betreft het ten alle tijden meeleveren van 'de aantekening' dat er sprake is van een verstrekkingsbeperking,
conform art 3.10 van de Wet BRP )

(1) Deze waarschuwing is gebaseerd op de actuele status en staat los van de gegevens die geleverd moeten worden.
Dus als er een historische levering plaatsvindt, zal toch uitgegaan worden van de actuele status "Verstrekkingsbeperking" voor
de te leveren waarschuwing.

Scenario: 1.a   Geef details persoon, volledige verstrekkingsbeperking
                LT: R1339_LT05, R1340_LT01
                Verwacht Resultaat:
                - Melding voor die Persoon met de Melding.Soort Waarschuwing

Given leveringsautorisatie uit autorisatie/Bevraging_Verstrekkingsbeperking

Given persoonsbeelden uit specials:VerstrekkingsBeperking/PersoonKrijgtVerstrekkingsBeperking_xls

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Bevraging verstrekkingsbeperking'
|zendendePartijNaam|'KUC033-PartijVerstrekkingsbeperking'
|bsn|270433417

Then heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Fout'
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                                                                            |
| R1339    | Bij deze persoon geldt een verstrekkingsbeperking waardoor deze dienst niet geleverd kan worden.   |

Scenario: 1.b   Synchoniseer Persoon, volledige verstrekkingsbeperking
                LT: R1339_LT01
                Verwacht Resultaat:
                - Melding voor die Persoon met de Melding.Soort Waarschuwing

Given leveringsautorisatie uit autorisatie/Bevraging_Verstrekkingsbeperking

Given persoonsbeelden uit specials:VerstrekkingsBeperking/PersoonKrijgtVerstrekkingsBeperking_xls

Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'Bevraging verstrekkingsbeperking'
|zendendePartijNaam|'KUC033-PartijVerstrekkingsbeperking'
|bsn|270433417

Then heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Fout'
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                                                                            |
| R1339    | Bij deze persoon geldt een verstrekkingsbeperking waardoor deze dienst niet geleverd kan worden.   |

Scenario: 1.c   Geef medebewoners, volledige verstrekkingsbeperking
                LT: R1339_LT10
                Verwacht Resultaat:
                - Melding voor die Persoon met de Melding.Soort Waarschuwing

Given leveringsautorisatie uit autorisatie/Bevraging_Verstrekkingsbeperking

Given persoonsbeelden uit specials:VerstrekkingsBeperking/PersoonKrijgtVerstrekkingsBeperking_xls

Given verzoek geef medebewoners van persoon:
|key|value
|leveringsautorisatieNaam|'Bevraging verstrekkingsbeperking'
|zendendePartijNaam|'KUC033-PartijVerstrekkingsbeperking'
|burgerservicenummer|270433417

Then heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Fout'
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                                                                            |
| R1339    | Bij deze persoon geldt een verstrekkingsbeperking waardoor deze dienst niet geleverd kan worden.   |

Scenario: 1.d   Zoek Persoon, volledige verstrekkingsbeperking
                LT: R1983_LT11
                Verwacht Resultaat:
                - Melding voor die Persoon met de Melding.Soort Waarschuwing

Given leveringsautorisatie uit autorisatie/Bevraging_Verstrekkingsbeperking

Given persoonsbeelden uit specials:VerstrekkingsBeperking/PersoonKrijgtVerstrekkingsBeperking_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Bevraging verstrekkingsbeperking'
|zendendePartijNaam|'KUC033-PartijVerstrekkingsbeperking'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=270433417

Then is er voor xpath //brp:burgerservicenummer[text()='270433417'] geen node aanwezig in het antwoord bericht

Scenario: 1.e   Zoek Persoon op adres, volledige verstrekkingsbeperking
                LT: R1983_LT14
                Verwacht Resultaat:
                - Melding voor die Persoon met de Melding.Soort Waarschuwing

Given leveringsautorisatie uit autorisatie/Bevraging_Verstrekkingsbeperking

Given persoonsbeelden uit specials:VerstrekkingsBeperking/PersoonKrijgtVerstrekkingsBeperking_xls

Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'Bevraging verstrekkingsbeperking'
|zendendePartijNaam|'KUC033-PartijVerstrekkingsbeperking'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Postcode,Waarde=2000AA

Then is er voor xpath //brp:burgerservicenummer[text()='270433417'] geen node aanwezig in het antwoord bericht

Scenario: 2.a   Geef details persoon, verstrekkingsbeperking op partij
                LT: R1983_LT09, R1340_LT02
                Verwacht Resultaat:
                - Melding voor die Persoon met de Melding.Soort Waarschuwing
                Uitwerking:
                - verstrekkingsbeperking geplaatst op partij Stichting Interkerkelijke Ledenadministratie
                - Bevraging door partij Stichting Interkerkelijke Ledenadministratie

Given leveringsautorisatie uit autorisatie/Bevraging_verstrekkingsbeperking_op_partij

Given persoonsbeelden uit BIJHOUDING:VZIG04C20T20/Specifieke_verstrekkingsbeperking_Partij/dbstate002

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Bevraging verstrekkingsbeperking'
|zendendePartijNaam|'Stichting Interkerkelijke Ledenadministratie'
|bsn|771168585

Then heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Fout'
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                                                                            |
| R1339    | Bij deze persoon geldt een verstrekkingsbeperking waardoor deze dienst niet geleverd kan worden.   |

Scenario: 2.b   Synchoniseer Persoon, verstrekkingsbeperking op partij
                LT: R1339_LT01
                Verwacht Resultaat:
                - Melding voor die Persoon met de Melding.Soort Waarschuwing
                Uitwerking:
                - verstrekkingsbeperking geplaatst op partij Stichting Interkerkelijke Ledenadministratie
                - Bevraging door partij Stichting Interkerkelijke Ledenadministratie

Given leveringsautorisatie uit autorisatie/Bevraging_verstrekkingsbeperking_op_partij

Given persoonsbeelden uit BIJHOUDING:VZIG04C20T20/Specifieke_verstrekkingsbeperking_Partij/dbstate002

Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'Bevraging verstrekkingsbeperking'
|zendendePartijNaam|'Stichting Interkerkelijke Ledenadministratie'
|bsn|771168585

Then heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Fout'
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                                                                            |
| R1339    | Bij deze persoon geldt een verstrekkingsbeperking waardoor deze dienst niet geleverd kan worden.   |

Scenario: 2.c   Geef medebewoners, verstrekkingsbeperking op partij
                LT: R1339_LT10
                Verwacht Resultaat:
                - Melding voor die Persoon met de Melding.Soort Waarschuwing
                Uitwerking:
                - verstrekkingsbeperking geplaatst op partij Stichting Interkerkelijke Ledenadministratie
                - Bevraging door partij Stichting Interkerkelijke Ledenadministratie

Given leveringsautorisatie uit autorisatie/Bevraging_verstrekkingsbeperking_op_partij

Given persoonsbeelden uit BIJHOUDING:VZIG04C20T20/Specifieke_verstrekkingsbeperking_Partij/dbstate002

Given verzoek geef medebewoners van persoon:
|key|value
|leveringsautorisatieNaam|'Bevraging verstrekkingsbeperking'
|zendendePartijNaam|'Stichting Interkerkelijke Ledenadministratie'
|burgerservicenummer|771168585

Then heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Fout'
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                                                                            |
| R1339    | Bij deze persoon geldt een verstrekkingsbeperking waardoor deze dienst niet geleverd kan worden.   |

Scenario: 2.d   Zoek Persoon, verstrekkingsbeperking op partij
                LT: R1983_LT12
                Verwacht Resultaat:
                - Melding voor die Persoon met de Melding.Soort Waarschuwing
                Uitwerking:
                - verstrekkingsbeperking geplaatst op partij Stichting Interkerkelijke Ledenadministratie
                - Bevraging door partij Stichting Interkerkelijke Ledenadministratie

Given leveringsautorisatie uit autorisatie/Bevraging_verstrekkingsbeperking_op_partij

Given persoonsbeelden uit BIJHOUDING:VZIG04C20T20/Specifieke_verstrekkingsbeperking_Partij/dbstate002

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Bevraging verstrekkingsbeperking'
|zendendePartijNaam|'Stichting Interkerkelijke Ledenadministratie'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=771168585

Then heeft het antwoordbericht verwerking Geslaagd
Then is er voor xpath //brp:burgerservicenummer[text()='771168585'] geen node aanwezig in het antwoord bericht


Scenario: 2.e   Zoek Persoon op adres, verstrekkingsbeperking op partij
                LT: R1983_LT14
                Verwacht Resultaat:
                - Melding voor die Persoon met de Melding.Soort Waarschuwing
                Uitwerking:
                - verstrekkingsbeperking geplaatst op partij Stichting Interkerkelijke Ledenadministratie
                - Bevraging door partij Stichting Interkerkelijke Ledenadministratie

Given leveringsautorisatie uit autorisatie/Bevraging_verstrekkingsbeperking_op_partij

Given persoonsbeelden uit BIJHOUDING:VZIG04C20T20/Specifieke_verstrekkingsbeperking_Partij/dbstate002

Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'Bevraging verstrekkingsbeperking'
|zendendePartijNaam|'Stichting Interkerkelijke Ledenadministratie'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Postcode,Waarde=2000AA

Then heeft het antwoordbericht verwerking Geslaagd
Then is er voor xpath //brp:burgerservicenummer[text()='771168585'] geen node aanwezig in het antwoord bericht

Scenario: 3.a   Geef details persoon, verstrekkingsbeperking op andere partij
                LT: R1340_LT03
                Verwacht Resultaat:
                - Melding voor die Persoon met de Melding.Soort Waarschuwing
                Uitwerking:
                - verstrekkingsbeperking geplaatst op partij Stichting Interkerkelijke Ledenadministratie
                - Bevraging door partij KUC033-PartijVerstrekkingsbeperking

Given leveringsautorisatie uit autorisatie/Bevraging_verstrekkingsbeperking_op_partij_andere_partij

Given persoonsbeelden uit BIJHOUDING:VZIG04C20T20/Specifieke_verstrekkingsbeperking_Partij/dbstate002

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Bevraging verstrekkingsbeperking'
|zendendePartijNaam|'KUC033-PartijVerstrekkingsbeperking'
|bsn|771168585

Then heeft in het antwoordbericht 'soortNaam' in 'melding' de waarde 'Waarschuwing'
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                        |
| R1340    | De persoon heeft een verstrekkingsbeperking.   |

Scenario: 3.b   Synchoniseer Persoon, verstrekkingsbeperking op andere partij
                LT: R1340_LT03
                Verwacht Resultaat:
                - Melding voor die Persoon met de Melding.Soort Waarschuwing
                Uitwerking:
                - verstrekkingsbeperking geplaatst op partij Stichting Interkerkelijke Ledenadministratie
                - Bevraging door partij KUC033-PartijVerstrekkingsbeperking

Given leveringsautorisatie uit autorisatie/Bevraging_verstrekkingsbeperking_op_partij_andere_partij

Given persoonsbeelden uit BIJHOUDING:VZIG04C20T20/Specifieke_verstrekkingsbeperking_Partij/dbstate002

Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'Bevraging verstrekkingsbeperking'
|zendendePartijNaam|'KUC033-PartijVerstrekkingsbeperking'
|bsn|771168585

When het volledigbericht voor leveringsautorisatie Bevraging verstrekkingsbeperking is ontvangen en wordt bekeken
Then hebben attributen in voorkomens de volgende waardes:
| groep         | nummer | attribuut     | verwachteWaarde                              |
| melding       | 1      | soortNaam     | Waarschuwing                                 |
| melding	    | 1      | regelCode 	 | R1340                                        |
| melding	    | 1      | melding    	 | De persoon heeft een verstrekkingsbeperking. |                                                                                                  |


Scenario: 3.c   Geef medebewoners, verstrekkingsbeperking op andere partij
                LT: R1340_LT03
                Verwacht Resultaat:
                - Melding voor die Persoon met de Melding.Soort Waarschuwing
                Uitwerking:
                - verstrekkingsbeperking geplaatst op partij Stichting Interkerkelijke Ledenadministratie
                - Bevraging door partij KUC033-PartijVerstrekkingsbeperking

!-- Peilmoment nodig omdat er anders geen herleidbaar adres gevonden wordt
Given leveringsautorisatie uit autorisatie/Bevraging_verstrekkingsbeperking_op_partij_andere_partij

Given persoonsbeelden uit BIJHOUDING:VZIG04C20T20/Specifieke_verstrekkingsbeperking_Partij/dbstate002

Given verzoek geef medebewoners van persoon:
|key|value
|leveringsautorisatieNaam|'Bevraging verstrekkingsbeperking'
|zendendePartijNaam|'KUC033-PartijVerstrekkingsbeperking'
|administratienummer      | '3047059745'
|peilmomentMaterieel  |'2014-01-01'

Then heeft in het antwoordbericht 'soortNaam' in 'melding' de waarde 'Waarschuwing'
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                        |
| R1340    | De persoon heeft een verstrekkingsbeperking.   |

Scenario: 3.d   Zoek Persoon, verstrekkingsbeperking op andere partij
                LT: R1340_LT08
                Verwacht Resultaat:
                - Melding voor die Persoon met de Melding.Soort Waarschuwing
                - Persoon gevonden
                Uitwerking:
                - verstrekkingsbeperking geplaatst op partij Stichting Interkerkelijke Ledenadministratie
                - Bevraging door partij KUC033-PartijVerstrekkingsbeperking

Given leveringsautorisatie uit autorisatie/Bevraging_verstrekkingsbeperking_op_partij_andere_partij

Given persoonsbeelden uit BIJHOUDING:VZIG04C20T20/Specifieke_verstrekkingsbeperking_Partij/dbstate002

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Bevraging verstrekkingsbeperking'
|zendendePartijNaam|'KUC033-PartijVerstrekkingsbeperking'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=771168585

Then heeft het antwoordbericht verwerking Geslaagd
Then heeft in het antwoordbericht 'soortNaam' in 'melding' de waarde 'Waarschuwing'
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                        |
| R1340    | De persoon heeft een verstrekkingsbeperking.   |
Then is er voor xpath //brp:burgerservicenummer[text()='771168585'] een node aanwezig in het antwoord bericht

Scenario: 3.e   Zoek Persoon op adres, verstrekkingsbeperking op andere partij
                LT: R1340_LT03
                Verwacht Resultaat:
                - Melding voor die Persoon met de Melding.Soort Waarschuwing
                - Persoon gevonden
                Uitwerking:
                - verstrekkingsbeperking geplaatst op partij Stichting Interkerkelijke Ledenadministratie
                - Bevraging door partij KUC033-PartijVerstrekkingsbeperking

Given leveringsautorisatie uit autorisatie/Bevraging_verstrekkingsbeperking_op_partij_andere_partij

Given persoonsbeelden uit BIJHOUDING:VZIG04C20T20/Specifieke_verstrekkingsbeperking_Partij/dbstate002

Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'Bevraging verstrekkingsbeperking'
|zendendePartijNaam|'KUC033-PartijVerstrekkingsbeperking'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Postcode,Waarde=2000AA

Then heeft het antwoordbericht verwerking Geslaagd
Then heeft in het antwoordbericht 'soortNaam' in 'melding' de waarde 'Waarschuwing'
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                        |
| R1340    | De persoon heeft een verstrekkingsbeperking.   |
Then is er voor xpath //brp:burgerservicenummer[text()='771168585'] een node aanwezig in het antwoord bericht


Scenario: 4.a   Geef details persoon, volledige verstrekkingsbeperking, peilmoment voor verstrekkingsbeperking vanaf 2016
                LT: R1340_LT04
                Verwacht Resultaat:
                - Melding voor die Persoon met de Melding.Soort Waarschuwing
                Uitwerking:
                - Bij verstrekkingsbeperkingen wordt gekeken naar het actuele beeld

Given leveringsautorisatie uit autorisatie/Bevraging_Verstrekkingsbeperking

Given persoonsbeelden uit specials:VerstrekkingsBeperking/PersoonKrijgtVerstrekkingsBeperking_xls

Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|270433417|1999-12-31 T23:59:00Z

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Bevraging verstrekkingsbeperking'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|270433417
|historievorm|MaterieelFormeel
|peilmomentMaterieelResultaat|'2011-01-01'
|peilmomentFormeelResultaat|'2011-01-01T23:59:00'

Then heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                        |
| R1340    | De persoon heeft een verstrekkingsbeperking.   |

Scenario: 4.b   Geef medebewoners, volledige verstrekkingsbeperking, peilmoment voor verstrekkingsbeperking vanaf 2016
                LT: R1340_LT04
                Verwacht Resultaat:
                - Melding voor die Persoon met de Melding.Soort Waarschuwing
                Uitwerking:
                - Bij verstrekkingsbeperkingen wordt gekeken naar het actuele beeld

Given leveringsautorisatie uit autorisatie/Bevraging_Verstrekkingsbeperking

Given persoonsbeelden uit specials:VerstrekkingsBeperking/PersoonKrijgtVerstrekkingsBeperking_xls

Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|270433417|1999-12-31 T23:59:00Z

Given verzoek geef medebewoners van persoon:
|key|value
|leveringsautorisatieNaam|'Bevraging verstrekkingsbeperking'
|zendendePartijNaam|'Gemeente Utrecht'
|peilmomentMaterieel|'2015-12-31'|
|burgerservicenummer|270433417


Then heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                        |
| R1340    | De persoon heeft een verstrekkingsbeperking.   |

Scenario: 4.c   Zoek Persoon, volledige verstrekkingsbeperking, peilmoment voor verstrekkingsbeperking vanaf 2016
                LT: R1340_LT09
                Verwacht Resultaat:
                - Persoon NIET gevonden
                Uitwerking:
                - Bij verstrekkingsbeperkingen wordt gekeken naar het actuele beeld

Given leveringsautorisatie uit autorisatie/Bevraging_Verstrekkingsbeperking

Given persoonsbeelden uit specials:VerstrekkingsBeperking/PersoonKrijgtVerstrekkingsBeperking_xls

Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|270433417|1999-12-31 T23:59:00Z

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Bevraging verstrekkingsbeperking'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=270433417
|peilmomentMaterieel|'2010-02-28'

Then heeft het antwoordbericht verwerking Geslaagd
Then heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                        |
| R1340    | De persoon heeft een verstrekkingsbeperking.   |

Scenario: 4.d   Zoek Persoon op adres, volledige verstrekkingsbeperking, peilmoment voor verstrekkingsbeperking vanaf 2016
                LT: R1340_LT09
                Verwacht Resultaat:
                - Persoon NIET gevonden
                Uitwerking:
                - Bij verstrekkingsbeperkingen wordt gekeken naar het actuele beeld

Given leveringsautorisatie uit autorisatie/Bevraging_Verstrekkingsbeperking

Given persoonsbeelden uit specials:VerstrekkingsBeperking/PersoonKrijgtVerstrekkingsBeperking_xls

Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|270433417|1999-12-31 T23:59:00Z

Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'Bevraging verstrekkingsbeperking'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Postcode,Waarde=2000AA
|peilmomentMaterieel|'2010-02-28'

Then heeft het antwoordbericht verwerking Geslaagd
Then heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                        |
| R1340    | De persoon heeft een verstrekkingsbeperking.   |

Scenario: 5.a   Geef details persoon, volledige verstrekkingsbeperking die vervallen is
                LT: R1340_LT05
                Verwacht Resultaat:
                - GEEN Melding voor die Persoon met de Melding.Soort Waarschuwing
                Uitwerking:
                - Bij verstrekkingsbeperkingen wordt gekeken naar het actuele beeld

Given leveringsautorisatie uit autorisatie/Bevraging_Verstrekkingsbeperking

Given persoonsbeelden uit specials:VerstrekkingsBeperking/PersoonVerstrekkingsBeperkingVervalt_xls

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Bevraging verstrekkingsbeperking'
|zendendePartijNaam|'KUC033-PartijVerstrekkingsbeperking'
|bsn|270433417

Then heeft het antwoordbericht verwerking Geslaagd
Then is in antwoordbericht de aanwezigheid van 'soortNaam' in 'melding' nummer 1 nee

Scenario: 5.a1  Geef details persoon met hisotirsch peilmoment, volledige verstrekkingsbeperking die vervallen is
                LT: R1340_LT10
                Verwacht Resultaat:
                - GEEN Melding voor die Persoon met de Melding.Soort Waarschuwing
                Uitwerking:
                - Bij verstrekkingsbeperkingen wordt gekeken naar het actuele beeld

Given leveringsautorisatie uit autorisatie/Bevraging_Verstrekkingsbeperking

Given persoonsbeelden uit specials:VerstrekkingsBeperking/PersoonVerstrekkingsBeperkingVervalt_xls

Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|270433417|1999-12-31 T23:59:00Z

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Bevraging verstrekkingsbeperking'
|zendendePartijNaam|'KUC033-PartijVerstrekkingsbeperking'
|bsn|270433417
|historievorm|MaterieelFormeel
|peilmomentMaterieelResultaat|'2011-01-01'
|peilmomentFormeelResultaat|'2011-01-01T23:59:00'

Then heeft het antwoordbericht verwerking Geslaagd
Then is in antwoordbericht de aanwezigheid van 'soortNaam' in 'melding' nummer 1 nee

Scenario: 5.b   Synchoniseer Persoon, volledige verstrekkingsbeperking die vervallen is
                LT: R1340_LT05
                Verwacht Resultaat:
                - GEEN Melding voor die Persoon met de Melding.Soort Waarschuwing
                Uitwerking:
                - Bij verstrekkingsbeperkingen wordt gekeken naar het actuele beeld

Given leveringsautorisatie uit autorisatie/Bevraging_Verstrekkingsbeperking

Given persoonsbeelden uit specials:VerstrekkingsBeperking/PersoonVerstrekkingsBeperkingVervalt_xls

Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'Bevraging verstrekkingsbeperking'
|zendendePartijNaam|'KUC033-PartijVerstrekkingsbeperking'
|bsn|270433417

Then heeft het antwoordbericht verwerking Geslaagd
Then is in antwoordbericht de aanwezigheid van 'soortNaam' in 'melding' nummer 1 nee

Scenario: 5.c   Geef medebewoners, volledige verstrekkingsbeperking die vervallen is
                LT: R1340_LT05
                Verwacht Resultaat:
                - GEEN Melding voor die Persoon met de Melding.Soort Waarschuwing
                Uitwerking:
                - Bij verstrekkingsbeperkingen wordt gekeken naar het actuele beeld

Given leveringsautorisatie uit autorisatie/Bevraging_Verstrekkingsbeperking

Given persoonsbeelden uit specials:VerstrekkingsBeperking/PersoonVerstrekkingsBeperkingVervalt_xls

Given verzoek geef medebewoners van persoon:
|key|value
|leveringsautorisatieNaam|'Bevraging verstrekkingsbeperking'
|zendendePartijNaam|'KUC033-PartijVerstrekkingsbeperking'
|burgerservicenummer|270433417

Then heeft het antwoordbericht verwerking Geslaagd
Then is in antwoordbericht de aanwezigheid van 'soortNaam' in 'melding' nummer 1 nee

Scenario: 5.d   Zoek Persoon, volledige verstrekkingsbeperking die vervallen is
                LT: R1340_LT05
                Verwacht Resultaat:
                - Geslaagd
                - GEEN Melding voor die Persoon met de Melding.Soort Waarschuwing
                Uitwerking:
                - Bij verstrekkingsbeperkingen wordt gekeken naar het actuele beeld

Given leveringsautorisatie uit autorisatie/Bevraging_Verstrekkingsbeperking

Given persoonsbeelden uit specials:VerstrekkingsBeperking/PersoonVerstrekkingsBeperkingVervalt_xls

Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Bevraging verstrekkingsbeperking'
|zendendePartijNaam|'KUC033-PartijVerstrekkingsbeperking'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=270433417

Then heeft het antwoordbericht verwerking Geslaagd
Then is in antwoordbericht de aanwezigheid van 'soortNaam' in 'melding' nummer 1 nee
Then is er voor xpath //brp:burgerservicenummer[text()='270433417'] een node aanwezig in het antwoord bericht

Scenario: 5.e   Zoek Persoon op adres, volledige verstrekkingsbeperking die vervallen is
                LT: R1340_LT05
                Verwacht Resultaat:
                - Geslaagd
                - GEEN Melding voor die Persoon met de Melding.Soort Waarschuwing
                Uitwerking:
                - Bij verstrekkingsbeperkingen wordt gekeken naar het actuele beeld

Given leveringsautorisatie uit autorisatie/Bevraging_Verstrekkingsbeperking

Given persoonsbeelden uit specials:VerstrekkingsBeperking/PersoonVerstrekkingsBeperkingVervalt_xls

Given verzoek zoek persoon op adres:
|key|value
|leveringsautorisatieNaam|'Bevraging verstrekkingsbeperking'
|zendendePartijNaam|'KUC033-PartijVerstrekkingsbeperking'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Adres.Postcode,Waarde=2000AA

Then heeft het antwoordbericht verwerking Geslaagd
Then is in antwoordbericht de aanwezigheid van 'soortNaam' in 'melding' nummer 1 nee
Then is er voor xpath //brp:burgerservicenummer[text()='270433417'] een node aanwezig in het antwoord bericht


Scenario: 6.    Mutatielevering obv afnemerindicatie, volledige verstrekkingsbeperking
                LT: R1340_LT11
                Verwacht Resultaat:
                - Geen bericht

Given leveringsautorisatie uit autorisatie/AfnemerIndicatie_Verstrekkingsbeperking

Given persoonsbeelden uit specials:VerstrekkingsBeperking/PersoonMutatieMetActueleVerstrekkingsbeperking_xls

Given afnemerindicatie op de persoon :
|bsn|leveringsautorisatieNaam|partijNaam|dienstId|tsReg|tsReg|datumEindeVolgen
|270433417|levering op basis van afnemerindicatie verstrekkingsbeperking|'KUC033-PartijVerstrekkingsbeperking'|30|2015-07-28 T16:11:21Z|2016-12-12 T16:11:21Z

When voor persoon 270433417 wordt de laatste handeling geleverd

!-- Controle R2589
Then is er geen synchronisatiebericht gevonden


Scenario: 8.    Mutatielevering obv afnemerindicatie, verstrekkingsbeperking op andere partij
                LT: R1340_LT12
                Verwacht Resultaat:
                - Mutatiebericht met melding dat er een verstrekkingsbeperking voor deze persoon is
                Uitwerking:
                - verstrekkingsbeperking geplaatst op partij Stichting Interkerkelijke Ledenadministratie
                - Mutatielevering voor partij KUC033-PartijVerstrekkingsbeperking

Given leveringsautorisatie uit autorisatie/AfnemerIndicatie_Verstrekkingsbeperking_op_andere_partij

Given persoonsbeelden uit BIJHOUDING:VZIG04C20T20/Specifieke_verstrekkingsbeperking_Partij/dbstate002

Given afnemerindicatie op de persoon :
|bsn|leveringsautorisatieNaam|partijNaam|dienstId|tsReg|tsReg|datumEindeVolgen
|771168585|levering op basis van afnemerindicatie verstrekkingsbeperking|'KUC033-PartijVerstrekkingsbeperking'|30|2015-07-28 T16:11:21Z|2016-12-12 T16:11:21Z

When voor persoon 771168585 wordt de laatste handeling geleverd

!-- Controle R2589
When het mutatiebericht voor leveringsautorisatie levering op basis van afnemerindicatie verstrekkingsbeperking is ontvangen en wordt bekeken
Then hebben attributen in voorkomens de volgende waardes:
| groep         | nummer | attribuut     | verwachteWaarde                              |
| melding       | 1      | soortNaam     | Waarschuwing                                 |
| melding	    | 1      | regelCode 	 | R1340                                        |
| melding	    | 1      | melding    	 | De persoon heeft een verstrekkingsbeperking.  |                                                                                                  |
