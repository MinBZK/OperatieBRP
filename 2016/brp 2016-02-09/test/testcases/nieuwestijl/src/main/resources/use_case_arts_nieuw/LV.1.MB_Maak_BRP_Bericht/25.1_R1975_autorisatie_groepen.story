Meta:
@auteur             aapos
@status             Klaar
@usecase            LV.1.MB
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

Scenario: R1975_01     Autorisatie op groep Aanwezig
                Logisch testgeval R1975_01
                Verwacht resultaat: Groep is aanwezig in leveringsbericht

Given leveringsautorisatie uit /levering_autorisaties/R1975_autorisatie_op_groep

Given de personen 299054457, 743274313, 228708977 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 228708977 en anr 2010486354 zonder extra gebeurtenissen

Given verzoek voor leveringsautorisatie 'R1975 autorisatie op groep' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand 24.2_R1974_Synchroniseer_Persoon.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie R1975 autorisatie op groep is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Then heeft het bericht 1 groep 'adres'
Then heeft het bericht 1 groep 'persoon'
Then heeft het bericht 1 groep 'samengesteldeNaam'
Then heeft het bericht 1 groep 'geboorte'
Then heeft het bericht 1 groep 'geslachtsaanduiding'
Then heeft het bericht 1 groep 'identificatienummers'


Scenario: R1975_02   Autorisatie op groep niet aanwezig
                Logisch testgeval R1975_02
                Verwacht resultaat: groep komt niet voor in bericht


Given de personen 299054457, 743274313, 228708977 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 228708977 en anr 2010486354 zonder extra gebeurtenissen

Given verzoek voor leveringsautorisatie 'R1975 autorisatie op groep' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand 24.2_R1974_Synchroniseer_Persoon.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie R1975 autorisatie op groep is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Then heeft het bericht 0 groep 'afgeleidAdministratief'
Then heeft het bericht 0 groep 'inschrijving'
Then heeft het bericht 0 groep 'bijhouding'
Then heeft het bericht 0 groep 'geslachtsnaamcomponenten'
Then heeft het bericht 0 groep 'nationaliteiten'
Then heeft het bericht 0 groep 'betrokkenheden'
Then heeft het bericht 0 groep 'administratieveHandelingen'

