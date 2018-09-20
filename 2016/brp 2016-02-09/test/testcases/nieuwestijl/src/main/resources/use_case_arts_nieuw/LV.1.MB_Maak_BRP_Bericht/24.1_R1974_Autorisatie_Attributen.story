Meta:
@auteur             kedon
@status             Klaar
@usecase            LV.1.MB
@regels             R1974
@sleutelwoorden     Maak BRP bericht

Narrative:

Bij de levering van een inhoudelijke groep ('Inhoudelijke groep' (R1540)) voor een Dienst mogen alleen die attributen in het resultaat
worden opgenomen waarvoor geldt dat:
Er voor de afnemer een koppeling Dienstbundel \ Groep \ Attribuut (Element, waarin de Element.Soort = Attribuut) bestaat, waarvan
Dienstbundel \ Groep \ Attribuut.Attribuut het betreffende Attribuut aanwijst.

Noot: bepaalde ('identificerende') attributen zullen altijd geleverd worden (zie 'Identificerende groep' (R1542)) .
Dit wordt gerealiseerd door de Attributen op de juiste wijze te kenmerken.
Dit betreft een beheervoorschrift: in Element zal de Beheerder bij Element.Autorisatie vast moeten leggen dat
dit gegeven "Verplicht" of "Aanbevolen" te leveren is.



Scenario: 1.1   Voor attribuut 1 Koppeling Dienstbundel\Groep\Attribuut en Verwijzing Dienstbundel\Groep\Attribuut naar attribuut aanwezig
                Voor attribuut 2 Koppeling Dienstbundel\Groep\Attribuut NIET aanwezig (postcode)
                Logisch testgeval R1974_02
                Verwacht resultaat: postcode niet in a-synchroon bericht

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding_Haarlem

Given de personen 299054457, 743274313, 228708977 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 228708977 en anr 2010486354 zonder extra gebeurtenissen

Given de database is aangepast met: update autaut.dienstbundelgroepattr set attr = 13020 where id = (select id from autaut.dienstbundelgroepattr where attr = 3281 and dienstbundelgroep = 105)
Given de cache is herladen

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van doelbinding Haarlem' en partij 'Gemeente Haarlem'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand 24.2_R1974_Synchroniseer_Persoon.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding Haarlem is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep   | nummer | attribuut                  | aanwezig  |
| adres   | 1      | postcode                   | nee       |

Scenario: 1.2   Terugzetten database

Given de database is aangepast met: update autaut.dienstbundelgroepattr set attr = 3281 where id = (select id from autaut.dienstbundelgroepattr where attr = 13020 and dienstbundelgroep = 105)
Given de cache is herladen


Scenario: 2.1   Voor attribuut 1 Koppeling Dienstbundel\Groep\Attribuut en Verwijzing Dienstbundel\Groep\Attribuut naar attribuut aanwezig
                Voor attribuut 2 (BSN) Koppeling Dienstbundel\Groep\Attribuut en Verwijzing Dienstbundel\Groep\Attribuut naar attribuut NIET aanwezig
                Logisch testgeval R1974_03
                Verwacht resultaat: postcode niet in a-synchroon bericht

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding_Haarlem

Given de personen 299054457, 743274313, 228708977 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 228708977 en anr 2010486354 zonder extra gebeurtenissen

Given de database is aangepast met: update kern.element set srt = 2 where id in (3018, 3676, 3281)
Given de cache is herladen

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van doelbinding Haarlem' en partij 'Gemeente Haarlem'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand 24.2_R1974_Synchroniseer_Persoon.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding Haarlem is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep   | nummer | attribuut                  | aanwezig  |
| adres   | 1      | postcode                   | nee       |

Scenario: 2.2   Terugzetten database

Given de database is aangepast met: update kern.element set srt = 3 where id in (3018, 3676, 3281)
Given de cache is herladen

Scenario: R1974_04   Autorisatie voor identificerende groep niet aanwezig, attribuut wordt wel geleverd
                Identificerende groepen: Van de (Hoofd)persoon en gerelateerden met betrokkenheid Partner en Ouder:
                                         Persoon.Identificatienummers
                                         Persoon.Samengestelde naam
                                         Persoon.Geboorte
                                         Persoon.Geslachtsaanduiding

                                         Van gerelateerden met betrokkenheid Kind:
                                         Persoon.Identificatienummers
                                         Persoon.Samengestelde naam
                                         Persoon.Geboorte
                Logisch testgeval R1974_04
                Verwacht resultaat: attributen die via de element tabel als verplicht leveren zijn gekenmerkt worden geleverd, ook als er geen autorisatie voor het attribuut aanwezig is
                Bevinding:
Meta:
@status Bug

Given leveringsautorisatie uit /levering_autorisaties/R1974_altijd_leveren_identificerende_groepen
Given de personen 661691081, 634915113, 743274313, 205416937, 906868233, 877968585, 555163209 zijn verwijderd
Given de standaardpersoon UC_Huisman met bsn 661691081 en anr 4285481746 zonder extra gebeurtenissen
Given de database is aangepast met: update kern.element set autorisatie=5 where naam='Persoon.Adres.Postcode'
Given de cache is herladen
Given de persoon beschrijvingen:
UC_Huisman = Persoon.metBsn(661691081)
nieuweGebeurtenissenVoor(UC_Huisman) {
   verhuizing(partij: 'Gemeente Delft', aanvang: 20120510, registratieDatum: 20120510) {
           naarGemeente 'Delft',
               straat: 'Aalscholverring', nummer: 30, postcode: '2623PD', woonplaats: "Delft"
       }
}
slaOp(UC_Huisman)

When voor persoon 606417801 wordt de laatste handeling geleverd

Given verzoek voor leveringsautorisatie 'R1974 altijd leveren identificerende groepen' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand 24.3_R1974_Synchroniseer_Persoon.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie R1974 altijd leveren identificerende groepen is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep   | nummer | attribuut                  | aanwezig  |
| adres   | 1      | postcode                   | ja        |

Given de database is aangepast met: update kern.element set autorisatie=3 where naam='Persoon.Adres.Postcode'

