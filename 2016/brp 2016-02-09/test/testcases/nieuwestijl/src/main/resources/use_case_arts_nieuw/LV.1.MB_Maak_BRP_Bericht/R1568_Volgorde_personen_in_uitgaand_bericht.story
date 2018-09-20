Meta:
@auteur             aapos
@status             Klaar
@usecase            LV.1.MB
@regels             R1568
@sleutelwoorden     Maak BRP bericht

Narrative:
Bij het opstellen van een Leverinsgsbericht waarin meerdere personen zijn opgenomen geldt het volgende:

De volgorde van hoofdpersonen in het leveringsbericht wordt bepaald door het attribuut Persoon.Sorteervolgorde in de groep
Persoon.Afgeleid administratief. In het bericht worden de Personen in oplopende Persoon.Sorteervolgorde opgenomen.
Bij gelijke Persoon.Sorteervolgorde vindt sortering plaats op oplopende technische sleutel (ID) van het object Persoon.

1. Bijhouding op kind
2. Afnemer ontvangt mutatie bericht van bijhouding met als 1ste persoon het kind en vervolgens de ouders
3. in de tabel kern.his_persafgeleidadministrati is in kolom 'sorteervolgorde' opgenomen in welke volgorde de personen geleverd moeten worden (persoon met sorteerdvolgorde '1' komt als eerste persoon in het mutatie bericht)

Scenario: R1568_L01 Sorteervolgorde (gedeeltelijk) gelijk.
            Er worden 3 hoofdpersonen geleverd, waarbij de laatste 2 personen een gelijke sorteer volgorde hebben
            Logisch testgeval: R1568_01, R1568_03
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given de personen 253525305, 634915113, 743274313, 205416937, 906868233, 877968585, 555163209 zijn verwijderd
Given de standaardpersoon UC_Huisman met bsn 253525305 en anr 1836939858 zonder extra gebeurtenissen

Given de persoon beschrijvingen:
UC_Benjamin = Persoon.metBsn(555163209)
nieuweGebeurtenissenVoor(UC_Benjamin) {
    naamswijziging(aanvang: 20150203, registratieDatum: 20150203) {
              geslachtsnaam(1) wordt stam:'Zonnig', voorvoegsel:'het'
        }
}
slaOp(UC_Benjamin)

Given de cache is herladen
When voor persoon 555163209 wordt de laatste handeling geleverd

When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then heeft het bericht 5 groep 'persoon'

Then hebben de attributen in de groepen de volgende waardes:
| groep                | attribuut           | verwachteWaardes                                      |
| identificatienummers | burgerservicenummer | 555163209, 253525305, 555163209, 877968585, 555163209 |

Then hebben attributen in voorkomens de volgende waardes:
| groep                | nummer | attribuut           | verwachteWaarde |
| identificatienummers | 1      | burgerservicenummer | 555163209       |
| identificatienummers | 2      | burgerservicenummer | 253525305       |
| identificatienummers | 4      | burgerservicenummer | 877968585       |

Scenario: R1568_L02 Ongelijke sorteer volgorde
                Logisch testgeval: R1568_02
Narrative: als de sorteerd volgorde in de tabel kern.his_persafgeleidadministrati wordt gewijzigd dan is de volgorde van de hoofdpersonen in het mutatie bericht ook gewijzigd

Given de database is aangepast met: update kern.his_persafgeleidadministrati set sorteervolgorde=1 where pers=(select id from kern.pers where bsn=877968585)
Given de database is aangepast met: update kern.his_persafgeleidadministrati set sorteervolgorde=2 where pers=(select id from kern.pers where bsn=253525305)
Given de database is aangepast met: update kern.his_persafgeleidadministrati set sorteervolgorde=3 where pers=(select id from kern.pers where bsn=555163209)
Given de cache is herladen

When voor persoon 555163209 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then hebben de attributen in de groepen de volgende waardes:
| groep                | attribuut           | verwachteWaardes                                      |
| identificatienummers | burgerservicenummer | 877968585, 555163209, 253525305, 555163209, 555163209 |

Then hebben attributen in voorkomens de volgende waardes:
| groep                | nummer | attribuut           | verwachteWaarde |
| identificatienummers | 1      | burgerservicenummer | 877968585       |
| identificatienummers | 3      | burgerservicenummer | 253525305       |
| identificatienummers | 4      | burgerservicenummer | 555163209       |