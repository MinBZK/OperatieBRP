Meta:
@status                 Klaar
@sleutelwoorden         geboorteInNederlandMetErkenning
@auteur                 dihoe

Narrative:
Afstamming, geboorte in Nederland met erkenning met de acties registratie geboorte, registratie identificatienummers,
registratie ouder en registratie naamgebruik

Scenario: 1. Geboorte in Nederland, ouders zijn niet getrouwd, vader erkent kind

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Tiel.txt
Given bijhoudingsverzoek voor partij 'Gemeente Tiel'

Given de personen 826933129,526521673,958587401,631512457,141901317,728733705,517582569 zijn verwijderd
Given de standaardpersoon Sandy met bsn 958587401 en anr 8086167314 zonder extra gebeurtenissen
Given de standaardpersoon Danny met bsn 728733705 en anr 4210970130 zonder extra gebeurtenissen

Given administratieve handeling van type geboorteInNederlandMetErkenning , met de acties registratieGeboorte, registratieIdentificatienummers, registratieOuder, registratieNaamgebruik
And testdata uit bestand geboorte_in_nederland_met_erkenning_01.yml
And extra waardes:
| SLEUTEL                                                                                                                                                                  | WAARDE            |
| geboorteInNederlandMetErkenning.acties.registratieGeboorte.datumAanvangGeldigheid                                                                                        | ${vandaag(0,0,0)} |
| geboorteInNederlandMetErkenning.acties.registratieGeboorte.familierechtelijkeBetrekking.betrokkenheden.kind.persoon.samengesteldeNaam.indicatieNamenreeks                | N                 |
| geboorteInNederlandMetErkenning.acties.registratieGeboorte.familierechtelijkeBetrekking.betrokkenheden.kind.persoon.geboorte.datum                                       | ${vandaag(0,0,0)} |
| geboorteInNederlandMetErkenning.acties.registratieGeboorte.familierechtelijkeBetrekking.betrokkenheden.kind.persoon.geboorte.gemeenteCode                                | 0310              |
| geboorteInNederlandMetErkenning.acties.registratieGeboorte.familierechtelijkeBetrekking.betrokkenheden.kind.persoon.geslachtsaanduiding.code                             | V                 |
| geboorteInNederlandMetErkenning.acties.registratieGeboorte.familierechtelijkeBetrekking.betrokkenheden.kind.persoon.(voornamen)[0].naam                                  | Elizabeth         |
| geboorteInNederlandMetErkenning.acties.registratieGeboorte.familierechtelijkeBetrekking.betrokkenheden.kind.persoon.geslachtsnaamcomponenten.geslachtsnaamcomponent.stam | Zuko              |
| geboorteInNederlandMetErkenning.acties.registratieGeboorte.familierechtelijkeBetrekking.betrokkenheden.ouder.persoon.objectSleutel                                       | 958587401         |
| geboorteInNederlandMetErkenning.acties.registratieGeboorte.familierechtelijkeBetrekking.betrokkenheden.ouder.ouderschap.indicatieOuderUitWieKindIsGeboren                | J                 |

And testdata uit bestand geboorte_in_nederland_met_erkenning_02.yml
And extra waardes:
| SLEUTEL                                                                                                                                                                  | WAARDE            |
| geboorteInNederlandMetErkenning.acties.registratieIdentificatienummers.datumAanvangGeldigheid                           | ${vandaag(0,0,0)} |
| geboorteInNederlandMetErkenning.acties.registratieIdentificatienummers.persoon.identificatienummers.burgerservicenummer | 517582569         |
| geboorteInNederlandMetErkenning.acties.registratieIdentificatienummers.persoon.identificatienummers.administratienummer | 7906738450        |

And testdata uit bestand geboorte_in_nederland_met_erkenning_03.yml
And extra waardes:
| SLEUTEL                                                                                                                         | WAARDE            |
| geboorteInNederlandMetErkenning.acties.registratieOuder.datumAanvangGeldigheid                                                  | ${vandaag(0,0,0)} |
| geboorteInNederlandMetErkenning.acties.registratieOuder.familierechtelijkeBetrekking.betrokkenheden.ouder.persoon.objectSleutel | 728733705         |

And testdata uit bestand geboorte_in_nederland_met_erkenning_04.yml
And extra waardes:
| SLEUTEL                                                                                             | WAARDE            |
| geboorteInNederlandMetErkenning.acties.registratieNaamgebruik.datumAanvangGeldigheid                | ${vandaag(0,0,0)} |
| geboorteInNederlandMetErkenning.acties.registratieNaamgebruik.persoon.naamgebruik.indicatieAfgeleid | J                 |

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

Then in kern select voornamen, geslnaamstam from kern.pers where bsn = 517582569 de volgende gegevens:
| veld         | waarde    |
| voornamen    | Elizabeth |
| geslnaamstam | Zuko      |

