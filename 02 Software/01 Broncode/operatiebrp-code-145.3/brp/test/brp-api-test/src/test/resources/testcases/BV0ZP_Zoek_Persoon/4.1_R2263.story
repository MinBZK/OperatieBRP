Meta:
@status             Klaar
@usecase            BV.0.ZP
@sleutelwoorden     Zoek Persoon
@regels             R2263

Narrative:
Zoekresultaatberichten bevatten alleen de verantwoordingsgegevens die wettelijk verplicht geleverd moeten worden
(zie R1545 - Verplicht leveren van ABO-partij en rechtsgrond.), ongeacht de autorisatie die is vastgelegd in Dienstbundel \ Groep.

Dit betekent dat er geen actieverwijzingen worden opgenomen in de groepen,
tenzij R1545 - Verplicht leveren van ABO-partij en rechtsgrond. dat anders bepaalt.

Scenario:   1.  Partijrol = Bijhoudingsorgaan College, administarieve handeling door partij = afnemer, Leveringsautorisatie met verantwoording
                LT: R2263_LT01
                Uitwerking:
                - Verhuizing naar Den Haag op 01-01-2010
                - Verhuizing naar Uithoorn op 01-01-2015
                Verwacht resultaat:
                - Geen actieverwijzingen in bericht voor adres


Given leveringsautorisatie uit autorisatie/Zoek_Persoon
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC10T110_xls
Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=659797641

Then heeft het antwoordbericht verwerking Geslaagd

Then is in antwoordbericht de aanwezigheid van 'actieInhoud' in 'afgeleidAdministratief' nummer 1 nee
Then is in antwoordbericht de aanwezigheid van 'actie' in 'bijgehoudenActies' nummer 1 nee

Scenario:   2.  Partijrol = Bijhoudingsorgaan College, administarieve handeling door partij = afnemer, Leveringsautorisatie ZONDER verantwoording
                LT: R2263_LT02
                Uitwerking:
                - Verhuizing naar Utrecht  op 31-12-2015
                - Verhuizing naar Den Haag op 01-01-2016
                - Verhuizing naar Uithoorn op 02-01-2016
                Verwacht resultaat:
                - Geen actieverwijzingen in bericht voor adres

Given leveringsautorisatie uit /levering_autorisaties_nieuw/R2263/Zoek_Persoon_Zonder_Verantwoording
Given persoonsbeelden uit oranje:DELTAOND/DELTAONDC10T110_xls
Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon Zonder Verantwoording'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=590984809

Then heeft het antwoordbericht verwerking Geslaagd
Then is in antwoordbericht de aanwezigheid van 'actieInhoud' in 'afgeleidAdministratief' nummer 1 nee
Then is in antwoordbericht de aanwezigheid van 'actie' in 'bijgehoudenActies' nummer 1 nee

Scenario:   3.  Partijrol = bestuursorgaan Minister, administarieve handeling door partij = 3 (Bijhoudingsorgaan Minister), Leveringsautorisatie met verantwoording
                LT: R2263_LT03
                Uitwerking:
                - Onderzoek gestart door administratieve handeling
                Verwacht resultaat:
                - Verantwoordingsgegevens in bericht
                - Actieverwijzing in bericht
!-- TODO administratieve hnd v partij Minister niet beschikbaar in gba blobs

Meta:
@status NietTestbaar
!-- niet testbaar vanwege conversie

Given leveringsautorisatie uit /levering_autorisaties_nieuw/R2266/Zoek_Persoon
Given persoonsbeelden uit LV1MB/Deel2/13_R1549/6/1/dbstate002
Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=590984809

Then heeft het antwoordbericht verwerking Geslaagd

!-- R2263_LT03 Verantwoordingsgegevens opgenomen in bericht
Then is in antwoordbericht de aanwezigheid van 'actieInhoud' in 'afgeleidAdministratief' nummer 1 ja

!-- R2263_LT03 / R1545 Actieverwijzing opnemen in bericht
Then is in antwoordbericht de aanwezigheid van 'actie' in 'bijgehoudenActies' nummer 1 ja

Scenario:   4.  Partijrol = afnemer, administarieve handeling door partij = 3 (Bijhoudingsorgaan Minister), Leveringsautorisatie met verantwoording
                LT: R2263_LT04
                Uitwerking:
                - Onderzoek gestart door administratieve handeling
                Verwacht resultaat:
                - Verantwoordingsgegevens in bericht
                - Actieverwijzing in bericht
!-- TODO administratieve hnd v partij Minister niet beschikbaar in gba blobs
Meta:
@status NietTestbaar
!-- niet testbaar vanwege conversie

Given leveringsautorisatie uit /levering_autorisaties_nieuw/R2263/Zoek_Persoon_Zonder_Verantwoording
Given persoonsbeelden uit LV1MB/Deel2/13_R1549/6/1/dbstate002
Given verzoek zoek persoon:
|key|value
|leveringsautorisatieNaam|'Zoek Persoon Zonder Verantwoording'
|zendendePartijNaam|'Gemeente Utrecht'
|zoekcriteria|ZoekOptie=Exact,ElementNaam=Persoon.Identificatienummers.Burgerservicenummer,Waarde=228708977

Then heeft het antwoordbericht verwerking Geslaagd

!-- R2263_LT04 Verantwoordingsgegevens opgenomen in bericht
Then is in antwoordbericht de aanwezigheid van 'actieInhoud' in 'afgeleidAdministratief' nummer 1 ja

!-- R2263_LT04 / R1545 Actieverwijzing opnemen in bericht
Then is in antwoordbericht de aanwezigheid van 'actie' in 'bijgehoudenActies' nummer 1 ja