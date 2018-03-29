Meta:
@status             Klaar
@usecase            BV.0.GD
@sleutelwoorden     Geef Details Persoon
@regels             R2274

Narrative:
Indien in het verzoekbericht de parameter Bericht.Verantwoording de waarde "Nee" heeft

Dan bevat het bericht geen actieverwijzingen (actieInhoud, actieAanpassingGeldigheid en actieVerval)
tenzij anders is bepaald door R1545 - Verplicht leveren van ABO-partij en rechtsgrond..

Toelichting: dit leidt er toe dat er geen verantwoordingsinformatie in het bericht wordt opgenomen,
tenzij dat wettelijk verplicht te leveren is.


Scenario:   1.  Partijrol = afnemer, adminisratieve handeling door partij = afnemer, Bericht verantwoording JA
                LT: R2274_LT01
                Verwacht Resultaat
                - Verantwoording in bericht
                - ActieInhoud en actieAanpassingGeldigheid bij gewijzigde adressen

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Anne_met_Historie_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|590984809

Then heeft het antwoordbericht verwerking Geslaagd

!-- R2274_LT01
Then is er voor xpath //brp:adres/brp:actieInhoud een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:adres/brp:actieAanpassingGeldigheid een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:relatie/brp:actieVerval een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:adres/brp:tijdstipRegistratie een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:administratieveHandelingen een node aanwezig in het antwoord bericht

Scenario:   2.  Partijrol = afnemer, adminisratieve handeling door partij = afnemer, Bericht verantwoording NEE
                LT: R2274_LT02
                Verwacht Resultaat
                - Verantwoording NIET in bericht
                - GEEN ActieInhoud en actieAanpassingGeldigheid bij gewijzigde adressen

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Anne_met_Historie_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|590984809
|verantwoording|Geen

Then heeft het antwoordbericht verwerking Geslaagd

!-- R2274_LT02
Then is er voor xpath //brp:actieInhoud geen node aanwezig in het antwoord bericht
Then is er voor xpath //brp:actieAanpassingGeldigheid geen node aanwezig in het antwoord bericht
Then is er voor xpath //brp:actieVerval geen node aanwezig in het antwoord bericht
Then is er voor xpath //brp:administratieveHandelingen geen node aanwezig in het antwoord bericht
Then is er voor xpath //brp:adres/brp:tijdstipRegistratie een node aanwezig in het antwoord bericht


Scenario:   3.  Partijrol = afnemer, adminisratieve handeling door partij = 3 (Bijhoudingsorgaan Minister), Bericht verantwoording JA
                LT: R2274_LT03
                Uitwerking:
                - Onderzoek gestart door administratieve handeling
                Verwacht resultaat:
                - Verantwoordingsgegevens in bericht
                - Actieverwijzing in bericht

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit oranje:DELTARNI/DELTARNI_INITVULLING_C10T30_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|995347529

Then heeft het antwoordbericht verwerking Geslaagd

!-- R2274_LT03 Verantwoordingsgegevens opgenomen in bericht
Then is in antwoordbericht de aanwezigheid van 'actieInhoud' in 'afgeleidAdministratief' nummer 1 ja

!-- R2274_LT03 / R1545 Actieverwijzing opnemen in bericht
Then is in antwoordbericht de aanwezigheid van 'actie' in 'bijgehoudenActies' nummer 1 ja

Scenario:   4.  Partijrol = afnemer, adminisratieve handeling door partij = 3 (Bijhoudingsorgaan Minister), Bericht verantwoording NEE
                LT: R2274_LT04
                Uitwerking:
                - Onderzoek gestart door administratieve handeling
                Verwacht resultaat:
                - Verantwoordingsgegevens in bericht
                - Actieverwijzing in bericht


Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit oranje:DELTARNI/DELTARNI_INITVULLING_C10T30_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|995347529
|verantwoording|Geen

Then heeft het antwoordbericht verwerking Geslaagd

!-- R2274_LT04 Verantwoordingsgegevens opgenomen in bericht
Then is in antwoordbericht de aanwezigheid van 'actieInhoud' in 'afgeleidAdministratief' nummer 1 ja

!-- R2274_LT04 / R1545 Actieverwijzing opnemen in bericht
Then is in antwoordbericht de aanwezigheid van 'actie' in 'bijgehoudenActies' nummer 1 ja