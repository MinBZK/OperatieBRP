Meta:
@auteur             kedon
@status             Klaar
@usecase            LV.1.MB
@regels             R134966
@sleutelwoorden     Maak BRP bericht

Narrative:
Een beëindigde Inhoudelijke groep (DatumEindeGeldigheid is gevuld met een waarde,
eventueel volledig onbekend) mag alleen worden opgenomen in het te leveren resultaat
(Persoonslijst of Mutatielevering) als er bij de Dienst waarvoor geleverd wordt een
corresponderend voorkomen bestaat van Dienstbundel \ Groep met Dienstbundel \ Groep.Materiële historie? = 'Ja'.


Scenario: 1 beëindigde Inhoudelijke groep (adres) met DatumEindeGeldigheid gevuld en een corresponderend voorkomen van
            dienstbundel groep, met materiele historie = ja
            Logisch testgeval:  R1349_01, R1349_05
            Verwacht Resultaat: beëindigde Inhoudelijke groep wordt opgenomen in het te leveren bericht, dus 3 groepen adres

Given leveringsautorisatie uit /levering_autorisaties/Populatiebeperking_levering_op_basis_van_doelbinding_Haarlem
Given de personen 299054457, 743274313, 228708977 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 228708977 en anr 2010486354 zonder extra gebeurtenissen

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Haarlem.txt
Given bijhoudingsverzoek voor partij 'Gemeente Haarlem'
Given administratieve handeling van type verhuizingIntergemeentelijk , met de acties registratieAdres
And testdata uit bestand 5.2_R1349_verhuizing_binnen_Haarlem.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het mutatiebericht voor leveringsautorisatie Populatiebeperking levering op basis van doelbinding Haarlem is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Then heeft het bericht 3 groep 'adres'

Scenario: 2 beëindigde Inhoudelijke groep (adres) met DatumEindeGeldigheid volledig onbekend en een corresponderend voorkomen van
            dienstbundel groep, met materiele historie = ja
            Logisch testgeval:  R1349_02
            Verwacht Resultaat: beëindigde Inhoudelijke groep wordt opgenomen in het te leveren bericht, dus 3 groepen adres

Given de database is aangepast met: update kern.his_persadres set dateindegel = 00000000 where persadres = (select id from kern.persadres where pers = (select id from kern.pers where bsn = 228708977 ))
Given de cache is herladen

Given leveringsautorisatie uit /levering_autorisaties/Populatiebeperking_levering_op_basis_van_doelbinding_Haarlem
Given verzoek voor leveringsautorisatie 'Populatiebeperking levering op basis van doelbinding Haarlem' en partij 'Gemeente Haarlem'

Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand 5.3_R1349_Synchroniseer_Persoon.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Populatiebeperking levering op basis van doelbinding Haarlem is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Then heeft het bericht 3 groep 'adres'

Scenario: 3 beëindigde Inhoudelijke groep (adres) met DatumEindeGeldigheid gevuld en een corresponderend voorkomen van
            dienstbundel groep, met materiele historie = nee
            Logisch testgeval:  R1349_03
            Verwacht Resultaat: beëindigde Inhoudelijke groep wordt opgenomen in het te leveren bericht, dus 2 groepen adres

Given leveringsautorisatie uit /levering_autorisaties/Populatiebeperking_levering_op_basis_van_doelbinding_Haarlem_Geen_Materiele_historie
Given de personen 299054457, 743274313, 228708977 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 228708977 en anr 2010486354 zonder extra gebeurtenissen

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Haarlem.txt
Given bijhoudingsverzoek voor partij 'Gemeente Haarlem'
Given administratieve handeling van type verhuizingIntergemeentelijk , met de acties registratieAdres
And testdata uit bestand 5.2_R1349_verhuizing_binnen_Haarlem.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het mutatiebericht voor leveringsautorisatie Popbep levering obv doelbinding Haarlem geen materiele historie is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Then heeft het bericht 2 groep 'adres'

Scenario: 4 beëindigde Inhoudelijke groep (adres) met DatumEindeGeldigheid volledig onbekend en een corresponderend voorkomen van
            dienstbundel groep, met materiele historie = nee
            Logisch testgeval:  R1349_04
            Verwacht Resultaat: beëindigde Inhoudelijke groep wordt opgenomen in het te leveren bericht, dus 2 groepen adres

Given de database is aangepast met: update kern.his_persadres set dateindegel = 00000000 where persadres = (select id from kern.persadres where pers = (select id from kern.pers where bsn = 228708977 ))
Given de cache is herladen

Given leveringsautorisatie uit /levering_autorisaties/Populatiebeperking_levering_op_basis_van_doelbinding_Haarlem_Geen_Materiele_historie
Given verzoek voor leveringsautorisatie 'Popbep levering obv doelbinding Haarlem geen materiele historie' en partij 'Gemeente Haarlem'

Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand 5.3_R1349_Synchroniseer_Persoon.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Popbep levering obv doelbinding Haarlem geen materiele historie is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Then heeft het bericht 2 groep 'adres'

Scenario: 5 beëindigde Inhoudelijke groep (adres) met DatumEindeGeldigheid gedeeltelijk onbekend en een corresponderend voorkomen van
            dienstbundel groep, met materiele historie = JA
            Logisch testgeval:  R1349_06
            Verwacht Resultaat: beëindigde Inhoudelijke groep wordt opgenomen in het te leveren bericht, dus 3 groepen adres

Given de database is aangepast met: update kern.his_persadres set dateindegel = 20150000 where persadres = (select id from kern.persadres where pers = (select id from kern.pers where bsn = 228708977 ))
Given de cache is herladen

Given leveringsautorisatie uit /levering_autorisaties/Populatiebeperking_levering_op_basis_van_doelbinding_Haarlem
Given verzoek voor leveringsautorisatie 'Populatiebeperking levering op basis van doelbinding Haarlem' en partij 'Gemeente Haarlem'

Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand 5.3_R1349_Synchroniseer_Persoon.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Populatiebeperking levering op basis van doelbinding Haarlem is ontvangen en wordt bekeken
Then is het bericht xsd-valide
Then heeft het bericht 3 groep 'adres'