Meta:
@status                 Klaar
@sleutelwoorden         geboorteInNederland
@auteur                 dihoe

Narrative:
Afstamming, geboorte in Nederland met de acties registratie geboorte, registratie identificatienummers,
registratie naamgebruik en registratie nationaliteit

Scenario: 1. Geboorte in Nederland, Sandy = moeder, Danny = vader, Martin = kind

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Tiel.txt
Given bijhoudingsverzoek voor partij 'Gemeente Tiel'

Given de personen 826933129,526521673,292824713,631512457,141901317,667381065,904394633 zijn verwijderd
Given de standaardpersoon Sandy met bsn 292824713 en anr 7292954386 zonder extra gebeurtenissen
Given de standaardpersoon Danny met bsn 667381065 en anr 9601207058 zonder extra gebeurtenissen
Given de persoon beschrijvingen:
def Sandy = Persoon.uitDatabase(bsn: 292824713)
def Danny = Persoon.uitDatabase(bsn: 667381065)
Persoon.nieuweGebeurtenissenVoor(Sandy) {
    huwelijk(aanvang: 20151212, registratieDatum: 20151212) {
          op 20150605 te 'Rotterdam' gemeente 'Rotterdam'
          met Danny
    }
}
slaOp(Sandy)

Given administratieve handeling van type geboorteInNederland , met de acties registratieGeboorte, registratieIdentificatienummers, registratieNaamgebruik, registratieNationaliteit
And testdata uit bestand geboorte_in_nederland_01.yml
And extra waardes:
| SLEUTEL                                                                                                                                                      | WAARDE            |
| geboorteInNederland.acties.registratieGeboorte.datumAanvangGeldigheid                                                                                        | ${vandaag(0,0,0)} |
| geboorteInNederland.acties.registratieGeboorte.familierechtelijkeBetrekking.betrokkenheden.kind.persoon.samengesteldeNaam.indicatieNamenreeks                | N                 |
| geboorteInNederland.acties.registratieGeboorte.familierechtelijkeBetrekking.betrokkenheden.kind.persoon.geboorte.datum                                       | ${vandaag(0,0,0)} |
| geboorteInNederland.acties.registratieGeboorte.familierechtelijkeBetrekking.betrokkenheden.kind.persoon.geboorte.gemeenteCode                                | 0310              |
| geboorteInNederland.acties.registratieGeboorte.familierechtelijkeBetrekking.betrokkenheden.kind.persoon.geslachtsaanduiding.code                             | M                 |
| geboorteInNederland.acties.registratieGeboorte.familierechtelijkeBetrekking.betrokkenheden.kind.persoon.(voornamen)[0].naam                                  | Martin            |
| geboorteInNederland.acties.registratieGeboorte.familierechtelijkeBetrekking.betrokkenheden.kind.persoon.geslachtsnaamcomponenten.geslachtsnaamcomponent.stam | Zuko              |
| geboorteInNederland.acties.registratieGeboorte.familierechtelijkeBetrekking.betrokkenheden.(ouders)[0].persoon.objectSleutel                                 | 292824713         |
| geboorteInNederland.acties.registratieGeboorte.familierechtelijkeBetrekking.betrokkenheden.(ouders)[0].ouderschap.indicatieOuderUitWieKindIsGeboren          | J                 |
| geboorteInNederland.acties.registratieGeboorte.familierechtelijkeBetrekking.betrokkenheden.(ouders)[1].persoon.objectSleutel                                 | 667381065         |


And testdata uit bestand geboorte_in_nederland_02.yml
And extra waardes:
| SLEUTEL                                                                                                     | WAARDE            |
| geboorteInNederland.acties.registratieIdentificatienummers.datumAanvangGeldigheid                           | ${vandaag(0,0,0)} |
| geboorteInNederland.acties.registratieIdentificatienummers.persoon.identificatienummers.burgerservicenummer | 904394633         |
| geboorteInNederland.acties.registratieIdentificatienummers.persoon.identificatienummers.administratienummer | 2303789458        |

And testdata uit bestand geboorte_in_nederland_03.yml
And extra waardes:
| SLEUTEL                                                                                 | WAARDE            |
| geboorteInNederland.acties.registratieNaamgebruik.datumAanvangGeldigheid                | ${vandaag(0,0,0)} |
| geboorteInNederland.acties.registratieNaamgebruik.persoon.naamgebruik.indicatieAfgeleid | J                 |

And testdata uit bestand geboorte_in_nederland_04.yml
And extra waardes:
| SLEUTEL                                                                                                        | WAARDE            |
| geboorteInNederland.acties.registratieNationaliteit.datumAanvangGeldigheid                                     | ${vandaag(0,0,0)} |
| geboorteInNederland.acties.registratieNationaliteit.persoon.nationaliteiten.nationaliteit.nationaliteitCode    | 0001              |
| geboorteInNederland.acties.registratieNationaliteit.persoon.nationaliteiten.nationaliteit.redenVerkrijgingCode | 017               |

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

Then in kern select voornamen, geslnaamstam, voornamennaamgebruik from kern.pers where bsn = 904394633 de volgende gegevens:
| veld                 | waarde |
| voornamen            | Martin |
| geslnaamstam         | Zuko   |
| voornamennaamgebruik | Martin |
