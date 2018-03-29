Meta:
@status             Klaar
@usecase            LV.0.MB
@regels             R1975
@sleutelwoorden     Maak BRP bericht

Narrative:
Het te leveren resultaat bevat alleen inhoudelijke groepen waarvoor enige autorisatie bestaat in de Dienstbundel.
Dit betreft attribuut-autorisatie en autorisatie op materiële-, formele- en verantwoordingsaspecten.

De attribuut-autorisatie staat beschreven in R1974 - Alleen attributen waarvoor autorisatie bestaat worden geleverd.

Toelichting:
Dit kan geïnterpreteerd worden als: er is een voorkomen van Dienstbundel \ Groep waar tenminste één indicator op 'Ja' staat of
waarbij tenminste één voorkomen van Dienstbundel \ Groep \ Attribuut bestaat.


NB: Het gaat er dus niet om of er attributen daadwerkelijk voorkomen in het bericht maar of ze gezien de autorisatie voor kunnen komen.
Bijvoorbeeld: er is autorisatie voor A-nummer en de te leveren persoon heeft een groep identificatienummers met A-nummer afwezig en BSN gevuld.
Dan is er volgens deze definitie autorisatie voor de groep Identificatienummers en wordt deze groep geleverd, hoewel A-nummer niet aanwezig zal
zijn in het bericht.

Scenario: 1.    Autorisatie op groep aanwezig, min 1 attribuut geautoriseerd, Materiele historie,formele historie en verantwoording aanwezig
                LT: R1975_LT01
                Verwacht resultaat: Groep geautoriseerd en in bericht

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/R1975_autorisatie_op_groep
Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'R1975 autorisatie op groep'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
Then heeft het antwoordbericht verwerking Geslaagd
When het volledigbericht voor leveringsautorisatie R1975 autorisatie op groep is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan /testcases/LV1MB_MaakBericht/expecteds/R1975_autorisatie_groepen_1.xml voor expressie //brp:lvg_synVerwerkPersoon
Then heeft het bericht 1 groep 'adres'
Then heeft het bericht 1 groep 'persoon'
Then heeft het bericht 1 groep 'samengesteldeNaam'
Then heeft het bericht 1 groep 'geboorte'
Then heeft het bericht 1 groep 'geslachtsaanduiding'
Then heeft het bericht 1 groep 'identificatienummers'

Scenario: 2.    Autorisatie op groep niet aanwezig, geen attribuut geautoriseerd, Materiele historie,formele historie en verantwoording niet aanwezig
                LT: R1975_LT03
                Verwacht resultaat: Groep niet geautoriseerd, komt niet voor in bericht

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/R1975_autorisatie_op_groep
Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'R1975 autorisatie op groep'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|606417801
Then heeft het antwoordbericht verwerking Geslaagd
When het volledigbericht voor leveringsautorisatie R1975 autorisatie op groep is ontvangen en wordt bekeken
Then heeft het bericht 0 groep 'afgeleidAdministratief'
Then heeft het bericht 0 groep 'inschrijving'

Then heeft het bericht 0 groep 'bijhouding'
Then heeft het bericht 0 groep 'geslachtsnaamcomponenten'
Then heeft het bericht 0 groep 'nationaliteiten'
Then heeft het bericht 0 groep 'betrokkenheden'
Then heeft het bericht 0 groep 'administratieveHandelingen'


Scenario: 3.    De groep is wel geautoriseerd. Er is geen enkel attribuut binnen de groep geautoriseerd en de groep heeft geen formele / materiele historie en geen verantwoording
                LT: R1975_LT02
                Verwacht resultaat:
                1. Volledigbericht Groep wordt niet geleverd aan afnemer omdat na autorisatie filtering er een 'lege groep' overblijft
                2. Mutatie bericht Groep wordt wel geleverd omdat de nadereAanduidingVerval verplicht geleverd wordt indien de groep geleverd wordt

Given persoonsbeelden uit specials:specials/Jan_xls
Given leveringsautorisatie uit autorisatie/Geen_autorisatie_op_attributen_adres_Geen_historie
Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'Geen autorisatie op attributen van adres'
|zendendePartijNaam|'Gemeente Haarlem'
|bsn|606417801

Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Geen autorisatie op attributen van adres is ontvangen en wordt bekeken
Then heeft het bericht 0 groepen 'adressen'
Then heeft het bericht 0 groepen 'adres'
Then is het synchronisatiebericht gelijk aan /testcases/LV1MB_MaakBericht/expecteds/R1975_autorisatie_groepen_volledigbericht_3.xml voor expressie //brp:lvg_synVerwerkPersoon

!-- Verhuizing naar ander adres
Given persoonsbeelden uit oranje:DELTAVERS08/DELTAVERS08C30T30_xls
When voor persoon 941099593 wordt de laatste handeling geleverd

When het mutatiebericht voor leveringsautorisatie Geen autorisatie op attributen van adres is ontvangen en wordt bekeken
Then heeft het bericht 1 groepen 'adressen'
Then heeft het bericht 1 groepen 'adres'
Then is het synchronisatiebericht gelijk aan /testcases/LV1MB_MaakBericht/expecteds/R1975_autorisatie_groepen_mutatiebericht_3.xml voor expressie //brp:lvg_synVerwerkPersoon