Meta:
@sprintnummer               69
@epic                       Attendering
@auteur                     dadij
@jiraIssue                  TEAMBRP-2496
@status                     Klaar
@regels                     VR00062

Narrative:
        Afnemer heeft diensten 'Attendering'.
        Het abonnement Attendering populatiebeperking op basis van geboortedatum heeft de populatiebeperking persoon.geboorte.datum >= 1997/03/15 en
        attenderingscriterium WAAR.
        De expressie op de populatiebeperking zal WAAR evalueren voor een persoon met de gedeeltelijk onbekende geboortedatum 1997/11/00.
        De expressie op de populatiebeperking zal NULL evalueren voor een persoon met de gedeeltelijk onbekende geboortedatum 1997/03/00.

Scenario: 1. Geboortedatum 1997/11/00 Populatiebeperking evalueert WAAR, er dient een bericht geleverd te worden.

Given leveringsautorisatie uit /levering_autorisaties/attendering_populatiebeperking_op_basis_van_geboortedatum
Given de personen 420075768 zijn verwijderd
Given de standaardpersoon Vanilla met bsn 420075768 en anr 1054894566 met extra gebeurtenissen:
verbeteringGeboorteakte(partij: 54101, aanvang: 20101010, toelichting:'onbekend en WAAR', registratieDatum: 20101010){
    op '1997/11/00' te 'Delft' gemeente 503
}

When voor persoon 420075768 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Attendering populatiebeperking op basis van geboortedatum is ontvangen en wordt bekeken
Then is het bericht xsd-valide


Scenario: 2. Geboortedatum 1997/03/00 Populatiebeperking evalueert NULL, er dient geen bericht geleverd te worden.

Given leveringsautorisatie uit /levering_autorisaties/attendering_populatiebeperking_op_basis_van_geboortedatum
Given de personen 420075768 zijn verwijderd
Given de standaardpersoon Vanilla met bsn 420075768 en anr 1054894566 met extra gebeurtenissen:
verbeteringGeboorteakte(partij: 54101, aanvang: 20101010, toelichting:'onbekend en NULL', registratieDatum: 20101010){
    op '1997/03/00' te 'Delft' gemeente 503
}

When voor persoon 420075768 wordt de laatste handeling geleverd
Then wacht tot alle berichten zijn ontvangen

When volledigbericht voor leveringsautorisatie Attendering populatiebeperking op basis van geboortedatum wordt bekeken
Then is er geen synchronisatiebericht gevonden

When mutatiebericht voor leveringsautorisatie Attendering populatiebeperking op basis van geboortedatum wordt bekeken
Then is er geen synchronisatiebericht gevonden
