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

Scenario:   01 Na Autorisatiefilters blijft er een Inhoudelijke groep over (verwerkingssoort <> Identificatie)
                LT: R1989_LT01
                270433417 valt binnen de doelbinding, en verhuisd.
                Verwacht resultaat: Persoon wel in mutatiebericht opgenomen. Er zijn na autorisatiefiltering nog groepen met verwerkingssoort <> indentificatie,
                dus wordt er een mutatie bericht geleverd

!-- autorisatie op adres, afgeleidadministratief en de identficerende groepen
Given leveringsautorisatie uit autorisatie/R1989_1
Given persoonsbeelden uit specials:MaakBericht/R1989_DELTAVERS08C10T10_xls
When voor persoon 270433417 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Abo geen popbep doelbinding Haarlem is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/R1989_expected_scenario_1.xml voor expressie //brp:lvg_synVerwerkPersoon

Then hebben verwerkingssoorten in voorkomens de volgende waardes:
| groep                 | nummer | verwerkingssoort |
| identificatienummers  | 1      | Identificatie    |
| samengesteldeNaam     | 1      | Identificatie    |
| geboorte              | 1      | Identificatie    |
| geslachtsaanduiding   | 1      | Identificatie    |
| adres                 | 1      | Toevoeging       |
| adres                 | 2      | Wijziging        |
| adres                 | 3      | Verval           |


Scenario:   02 Na Autorisatiefilters op adres blijft geen Inhoudelijke groep over (verwerkingssoort = Identificatie)
            LT: R1989_LT02
            UC_Kenny wordt geboren in doelbinding van leveringsautorisatie die geen autorisatie heeft op adres
            Verwacht resultaat: Mutatiebericht met vulling:
            - Identificerende groepen
            - Bijhouding

!-- zelfde autorisatie, maar dan geen autorisatie op adres
Given leveringsautorisatie uit autorisatie/R1989_2
Given persoonsbeelden uit specials:MaakBericht/R1989_DELTAVERS08C10T10_xls
When voor persoon 270433417 wordt de laatste handeling geleverd
Then is er geen synchronisatiebericht gevonden