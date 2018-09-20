Meta:
@sprintnummer       78
@epic               Change 2015003: Geg.model relaties & betrokkenh.
@auteur             luwid
@jiraIssue          TEAMBRP-2708, TEAMBRP-3287
@status             Klaar
@regels             VR00122, R1320


Narrative:          Als afnemer
                    wil ik Verwerkingssoort ontvangen op objecten in MutatieBerichten,
                    zodat ik die berichten eenvoudiger kan interpreteren/verwerken in mijn eigen systeem.
                    R1320	VR00122	Bepalen verwerkingssoort van objecten

!-- Door implementatie van TEAMBRP-3287 zijn sommige expecteds aangepast: bij huwelijk en scheiding wordt de verwerkingssoort van gerelateerde persoon
!-- 'Identificatie'


Scenario:   1. MutatieBericht n.a.v. het in huwelijk treden van Sophia en Pablo
            Verwacht:
            -   Een verwerkingssoort bij alle objecten binnen Persoon
                -   Persoon (de bijgehouden hoofdpersoon): Wijziging
                -   Partner (eigen betrokkenheid): Toevoeging
                -   Huwelijk: Toevoeging
                -   Partner (gerelateerde betrokkenheid): Toevoeging
                -   Persoon (de betrokken persoon): Identificatie
            -   Een verwerkingssoort op de Administratieve Handeling zelf
                -   Administratieve Handeling (boven in bericht): Toevoeging

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given de personen 181888440, 283972567 zijn verwijderd
And de standaardpersoon Sophia met bsn 283972567 en anr 2065939858 zonder extra gebeurtenissen

When voor persoon 283972567 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then hebben verwerkingssoorten in voorkomens de volgende waardes:
| groep             | nummer    | verwerkingssoort  |
| synchronisatie    | 1         | Toevoeging        |
| persoon           | 1         | Wijziging         |
| partner           | 1         | Toevoeging        |
| huwelijk          | 1         | Toevoeging        |
| partner           | 2         | Toevoeging        |
| persoon           | 2         | Identificatie     |


Scenario:   2. MutatieBericht voor Pablo n.a.v. een naamswijziging bij Sophia
            Verwacht:
            -   Een verwerkingssoort bij alle objecten binnen Persoon
                -   Persoon (de bijgehouden hoofdpersoon): Wijziging
                -   Partner (eigen betrokkenheid): Referentie
                -   Huwelijk: Referentie
                -   Partner (gerelateerde betrokkenheid): Referentie
                -   Persoon (gerelateerde persoon): Wijziging
            -   Een verwerkingssoort op de Administratieve Handeling zelf
                -   Administratieve Handeling (boven in bericht): Toevoeging

!-- Dus let op! Naammswijziging voor Sophia. Aangezien Sophia en Pablo getrouwd zijn wordt er ook een mutatiebericht
!-- verstuurd voor Pablo
!-- Derhalve wordt de laatste handeling van Pablo opgehaald

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given de personen 181888440, 600290220 zijn verwijderd
And de standaardpersoon Sophia met bsn 600290220 en anr 6046147858 met extra gebeurtenissen:
naamswijziging(aanvang: 20150613, registratieDatum: 20150613) {
    geslachtsnaam(1) wordt stam:'Zonnig', voorvoegsel:'het'
}

When voor persoon 181888440 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then hebben verwerkingssoorten in voorkomens de volgende waardes:
| groep             | nummer    | verwerkingssoort  |
| synchronisatie    | 1         | Toevoeging        |
| persoon           | 1         | Wijziging         |
| partner           | 1         | Referentie        |
| huwelijk          | 1         | Referentie        |
| partner           | 2         | Referentie        |
| persoon           | 2         | Wijziging         |


Scenario:   3. MutatieBericht n.a.v. het beëindigen van het huwelijk tussen Sophia en Pablo
            Verwacht:
            -   Een verwerkingssoort bij alle objecten binnen Persoon
                -   Persoon (de bijgehouden hoofdpersoon): Wijziging
                -   Partner (eigen betrokkenheid): Referentie
                -   Huwelijk: Wijziging
                -   Partner (gerelateerde betrokkenheid): Referentie
                -   Persoon (gerelateerde persoon): Identificatie
            -   Een verwerkingssoort op de Administratieve Handeling zelf
                -   Administratieve Handeling (boven in bericht): Toevoeging

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given de personen 181888440, 400378279 zijn verwijderd
And de standaardpersoon Sophia met bsn 400378279 en anr 4735918610 met extra gebeurtenissen:
scheiding(aanvang: 20150909, registratieDatum: 20150909) {
    van echtgenoot
    op(20150000).te('Groningen').gemeente('Groningen')
}

When voor persoon 400378279 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then hebben verwerkingssoorten in voorkomens de volgende waardes:
| groep          | nummer | verwerkingssoort |
| synchronisatie | 1      | Toevoeging       |
| persoon        | 1      | Wijziging        |
| partner        | 1      | Referentie       |
| huwelijk       | 1      | Wijziging        |
| partner        | 2      | Referentie       |
| persoon        | 2      | Identificatie    |


Scenario:   4. MutatieBericht n.a.v. het nietig verklaren van het huwelijk tussen Sophia en Pablo
            Verwacht:
            -   Een verwerkingssoort bij alle objecten binnen Persoon
                -   Persoon (de bijgehouden hoofdpersoon): Wijziging
                -   Partner (eigen betrokkenheid): Referentie
                -   Huwelijk: Verval
                -   Partner (gerelateerde betrokkenheid): Referentie
                -   Persoon (gerelateerde persoon): Identificatie
            -   Een verwerkingssoort op de Administratieve Handeling zelf
                -   Administratieve Handeling (boven in bericht): Toevoeging

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given de personen 181888440, 300136006 zijn verwijderd
And de standaardpersoon Sophia met bsn 300136006 en anr 3231762194 met extra gebeurtenissen:
nietigVerklaringHuwelijk(aanvang: 20151010, registratieDatum: 20151010) {
}

When voor persoon 300136006 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then hebben verwerkingssoorten in voorkomens de volgende waardes:
| groep             | nummer    | verwerkingssoort  |
| synchronisatie    | 1         | Toevoeging        |
| persoon           | 1         | Wijziging         |
| partner           | 1         | Verval            |
| huwelijk          | 1         | Verval            |
| partner           | 2         | Verval            |
| persoon           | 2         | Identificatie     |


Scenario:   5. MutatieBericht n.a.v. binnenGemeentelijke verhuizing door Sophia en Pablo
            Verwacht:
            -   Een verwerkingssoort bij alle objecten binnen Persoon
                -   Persoon (de bijgehouden hoofdpersoon): Wijziging
            -   Een verwerkingssoort op de Administratieve Handeling zelf
                -   Administratieve Handeling (boven in bericht): Toevoeging
                -   Objecttype PersoonAdres komt drie keer voor (binnen de container Adressen) met verwerkingssoorten
                    -   Toevoeging
                    -   Wijziging
                    -   Verval

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given de personen 181888440, 500385701 zijn verwijderd
And de standaardpersoon Sophia met bsn 500385701 en anr 5645904146 met extra gebeurtenissen:
verhuizing(aanvang: 20151111, registratieDatum: 20151111) {
    binnenGemeente straat: 'Dorpstraat', nummer: 15, postcode: '2000AA', woonplaats: "Haarlem"
}

When voor persoon 500385701 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then hebben verwerkingssoorten in voorkomens de volgende waardes:
| groep             | nummer    | verwerkingssoort  |
| synchronisatie    | 1         | Toevoeging        |
| persoon           | 1         | Wijziging         |
| adres             | 1         | Toevoeging        |
| adres             | 2         | Wijziging         |
| adres             | 3         | Verval            |
