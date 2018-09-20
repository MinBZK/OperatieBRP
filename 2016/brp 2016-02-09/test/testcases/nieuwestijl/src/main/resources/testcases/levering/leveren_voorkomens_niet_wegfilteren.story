Meta:
@sprintnummer       75
@epic               Change 2015003: Gegevensmodel relaties en betrokkenheden (fund.issue)
@auteur             devel
@jiraIssue          TEAMBRP-2975
@status             Klaar
@regels             VR00120. R1975


Narrative:  Als afnemer wil ik dat groepen niet worden weggefilterd als ik historie of verantwoording mag zien,
            zodat ik ook formele/materiele/verantwoordingsaspecten krijg over lege groepen.

            R1975	VR00120	Alleen groepen waarvoor autorisatie bestaat worden geleverd

Scenario:   1. Standaardgroep van Fam. Rechtelijke betrekking tonen indien formele/materiele en of verantwoordingsaspecten voor deze groep getoond worden.
                De standaardgroep van Fam. Rechtelijke betrekking heeft geen attributen. Autorisatie via attributen om de groep te tonen gaat hier dus
                niet op.

Given de gehele database is gereset
Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding, /levering_autorisaties/Abo_zonder_abonnementgroepen
Given de standaardpersoon Vanilla met bsn 809783174 en anr 7825265938 zonder extra gebeurtenissen

When voor persoon 809783174 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding is ontvangen en wordt bekeken
Then is de aanwezigheid van 'tijdstipRegistratie' in 'relatie' nummer 0 WAAR
Then is de aanwezigheid van 'actieInhoud' in 'relatie' nummer 0 WAAR

When het volledigbericht voor leveringsautorisatie Abo zonder abonnementgroepen is ontvangen en wordt bekeken
Then is de aanwezigheid van 'tijdstipRegistratie' in 'relatie' nummer 0 ONWAAR
Then is de aanwezigheid van 'actieInhoud' in 'relatie' nummer 0 WAAR

Scenario:   2. Groep Adres.Standaard (historievorm materilee en formeel) tonen indien afnemer is geautoriseerd op formele/materiele en of
verantwoordingsaspecten voor deze groep.

Given leveringsautorisatie uit /levering_autorisaties/mutatielevering_obv_doelbinding/Autorisatie_op_verantwoordingsinfo_geen_autorisatie_op_attributen
When voor persoon 809783174 wordt de laatste handeling geleverd

When het volledigbericht voor leveringsautorisatie Autorisatie op verantwoordingsinfo geen autorisatie op attributen is ontvangen en wordt bekeken
Then is de aanwezigheid van 'tijdstipRegistratie' in 'adres' nummer 0 WAAR
Then is de aanwezigheid van 'actieInhoud' in 'adres' nummer 0 WAAR
Then is de aanwezigheid van 'datumAanvangGeldigheid' in 'adres' nummer 0 WAAR



