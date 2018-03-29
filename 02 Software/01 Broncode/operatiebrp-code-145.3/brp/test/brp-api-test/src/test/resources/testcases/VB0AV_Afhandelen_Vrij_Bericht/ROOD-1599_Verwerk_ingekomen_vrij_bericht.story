Meta:
@status             Klaar
@usecase            VB.0.AV
@sleutelwoorden     Verwerk ingekomen Vrij Bericht


Narrative:

Wanneer de ontvangende partij partijcode = 199903 (BRP Voorziening), dan wordt het vrije bericht in de database geplaatsd.

Scenario: 1.    Ontvangende partij is BRP voorziening
                Verwacht resulaat:
                - Geslaagd
                - Bericht in database

Given verzoek verstuur vrij bericht:
|key|value
|zendendePartijNaam|'PartijVrijBerichtDatumIngangGisterenDatumEindeLeeg'
|zenderVrijBericht|'PartijVrijBerichtDatumIngangGisterenDatumEindeLeeg'
|ontvangerVrijBericht|'BRP Voorziening'
|ondertekenaar|'PartijVrijBerichtDatumIngangGisterenDatumEindeLeeg'
|transporteur|'PartijVrijBerichtDatumIngangGisterenDatumEindeLeeg'
|soortNaam|'Beheermelding'
|inhoud|'Test verwerking ingekomen bericht voor BRP voorziening Partij'

Then heeft het antwoordbericht verwerking Geslaagd

Then is er een vrij bericht opgeslagen met zendende partij 850011 en soortnaam gelijk aan Beheermelding en inhoud gelijk aan Test verwerking ingekomen bericht voor BRP voorziening Partij

Then is er geen vrij bericht verzonden

Scenario: 2.    Ontvangende partij <> BRP voorziening
                Verwacht resulaat:
                - Geslaagd
                - Bericht niet in database

Given verzoek verstuur vrij bericht:
|key|value
|zendendePartijNaam|'PartijVrijBerichtDatumIngangGisterenDatumEindeLeeg'
|zenderVrijBericht|'PartijVrijBerichtDatumIngangGisterenDatumEindeLeeg'
|ontvangerVrijBericht|'PartijVrijBerichtDatumIngangVandaagDatumEindeMorgen'
|ondertekenaar|'PartijVrijBerichtDatumIngangGisterenDatumEindeLeeg'
|transporteur|'PartijVrijBerichtDatumIngangGisterenDatumEindeLeeg'
|soortNaam|'Beheermelding'
|inhoud|'Test verwerking ingekomen bericht voor BRP voorziening Partij'

Then heeft het antwoordbericht verwerking Geslaagd

Then is een vrij bericht voor partij 850012 verstuurd naar afleverpunt http://ergens

