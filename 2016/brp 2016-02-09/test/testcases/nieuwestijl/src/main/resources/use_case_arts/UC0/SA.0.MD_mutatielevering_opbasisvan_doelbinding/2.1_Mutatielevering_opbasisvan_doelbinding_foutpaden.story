Meta:
@epic               Verbeteren testtooling
@auteur             jowil
@status             Klaar
@usecase            SA.0.MD
@regels             R1316,R1333,R1343,R1348,R1989,R1990,R1993,R1994,R2000,R2001,R2002
@sleutelwoorden     Mutatielevering o.b.v. doelbinding

Narrative:
Foutsituaties voor mutatielevering op basis van doelbinding.

Scenario:   6.  Als de persoon niet binnen de doelbinding van een afnemer valt, dan wordt er geen bericht geleverd.
                Doelbinding is hier onwaar.
                Logisch testgeval: R1333_04
                Verwacht resultaat: Geen bericht

Given relevante abonnementen postcode gebied Bennebroek 2120 - 2121
Given de personen 299054457, 743274313, 552232105 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 552232105 en anr 9313242898 zonder extra gebeurtenissen

Given administratieve handeling van type verhuizingIntergemeentelijk , met de acties registratieAdres
And testdata uit bestand 2.2_Mutatielevering_opbasisvan_doelbinding_verhuizing5.yml
When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When het mutatiebericht voor abonnement postcode gebied Bennebroek 2120 - 2121 is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden

Scenario:   7.  Als de persoon niet binnen de doelbinding van een afnemer valt, dan wordt er geen bericht geleverd.
                Doelbinding is hier NULL.
                Logisch testgeval: R1333_05
                Verwacht resultaat: Geen bericht

Given relevante abonnementen Abo op basis van datum overlijden
Given de personen 299054457, 743274313, 552232105 zijn verwijderd
Given de standaardpersoon UC_Kenny met bsn 552232105 en anr 9313242898 met extra gebeurtenissen:
overlijden() {
    op 20140300 te "'s-Gravenhage" gemeente "'s-Gravenhage"
    }

When voor persoon 552232105 wordt de laatste handeling geleverd
When het mutatiebericht voor abonnement Abo op basis van datum overlijden is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden

Scenario:   8. Als het resultaat populatiebeperking van WAAR naar ONWAAR gaat, dan wordt GEEN vulbericht aangemaakt
            Logisch testgeval: R1348_L04
            Verwacht resultaat: Geen bericht

Given relevante abonnementen Abo op basis van postcode en geboortedatum persoon 1
Given de database is gereset voor de personen 393908586
And administratieve handeling van type verhuizingBinnengemeentelijk , met de acties registratieAdres
And testdata uit bestand 2.3_Mutatielevering_opbasisvan_doelbinding_verhuizing7.yml
When het bericht wordt verstuurd

Then heeft het antwoordbericht verwerking Geslaagd

When het mutatiebericht voor abonnement Abo op basis van postcode en geboortedatum persoon 1 is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden

Scenario:   9. Als het resultaat populatiebeperking van WAAR naar NULL gaat, dan wordt GEEN vulbericht aangemaakt
            Logisch testgeval:  R1348_L05
            Verwacht resultaat: Geen bericht

Given relevante abonnementen Abo op basis van postcode en geboortedatum persoon 3
Given de database is gereset voor de personen 393908586
And administratieve handeling van type verhuizingBinnengemeentelijk , met de acties registratieAdres
And testdata uit bestand Mutatielevering_opbasisvan_doelbinding_verhuizing7.yml
When het bericht wordt verstuurd

Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Abo op basis van postcode en geboortedatum persoon 3 is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden
