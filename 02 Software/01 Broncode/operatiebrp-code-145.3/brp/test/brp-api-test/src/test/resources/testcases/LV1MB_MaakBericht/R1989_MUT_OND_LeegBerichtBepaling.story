Meta:
@status                 Klaar
@usecase                LV.0.MB
@regels                 R1989
@sleutelwoorden         Maak BRP bericht

Narrative:
Een Persoon wordt NIET opgenomen in een mutatiebericht als voor een Leveringsautorisatie na toepassing van de Autorisatiefilters en
alle overige filters voor levering geldt dat:
Voor deze Persoon geen enkele 'Inhoudelijke groep' ('Inhoudelijke groep' (R1540)) anders dan Persoon.Afgeleid administratief of
Definitie 'Onderzoeksgroep' ('Onderzoeksgroep' (R1543)) in het bericht overblijft met een VerwerkingsSoort <> 'Identificatie'.

(Noot 1: De groep 'Afgeleid administratief' dus negeren bij de bepaling of er iets gewijzigd is)

(Noot 2: deze regel zal waarschijnlijk ooit nog aangepast moeten worden voor handeling 'sterker brondocument' en/of het leveren
van mutaties op Verantwoording)

Scenario:   01 Na Autorisatiefilters blijft heeft alleen een onderzoeksgroep een verwerkingssoort <> Identificatie.Het bericht wordt daarom niet bepaald als "leeg"
               LT: R1989_LT03
               Verwacht resultaat: Mutatiebericht

Given leveringsautorisatie uit autorisatie/R1989_MUT_OND_1
Given persoonsbeelden uit specials:MaakBericht/R1989_MUT_OND_bsn_xls
When voor persoon 595891305 wordt de laatste handeling geleverd

When het mutatiebericht voor leveringsautorisatie x is ontvangen en wordt bekeken
Then hebben verwerkingssoorten in voorkomens de volgende waardes:
| groep                | nummer | verwerkingssoort |
| identificatienummers | 1      | Identificatie    |
| samengesteldeNaam    | 1      | Identificatie    |
| geboorte             | 1      | Identificatie    |
| geslachtsaanduiding  | 1      | Identificatie    |
| onderzoek            | 1      | Toevoeging       |

Then is het synchronisatiebericht gelijk aan expecteds/R1989_MUT_OND_expected_scenario_1.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario:   02 Zonder autorisatie op onderzoek heeft alles verwerkingssoort Identificatie
               LT: R1989_LT04
               Verwacht resultaat: Persoon niet in mutatiebericht opgenomen. Er is na autorisatiefiltering GEEN groep Onderzoek,
               dus wordt er geen mutatie bericht geleverd

Given leveringsautorisatie uit autorisatie/R1989_MUT_OND_2
Given persoonsbeelden uit specials:MaakBericht/R1989_MUT_OND_bsn_xls
When voor persoon 595891305 wordt de laatste handeling geleverd
Then is er geen synchronisatiebericht gevonden
