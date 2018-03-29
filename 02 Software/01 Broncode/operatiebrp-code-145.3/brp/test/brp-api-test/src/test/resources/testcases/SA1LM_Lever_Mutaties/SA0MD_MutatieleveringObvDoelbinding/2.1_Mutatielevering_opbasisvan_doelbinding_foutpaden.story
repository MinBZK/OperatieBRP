Meta:
@status             Klaar
@usecase            SA.0.MD
@regels             R2016, R2129, R1316, R1333, R1343, R1348, R1989, R1990, R1991, R1993, R2057, R2060, R2062
@sleutelwoorden     Mutatielevering o.b.v. doelbinding, Lever Mutaties

Narrative:
Foutsituaties voor mutatielevering op basis van doelbinding.

Scenario:   1. Als het resultaat populatiebeperking van ONWAAR (geboorte) naar ONWAAR (verhuizing) gaat, dan wordt GEEN vulbericht aangemaakt
            LT: R1348_LT09, R1316_LT03
            Persoonsbeeld oud   = ONWAAR
            Persoonsbeeld nieuw = ONWAAR
            BSN omgezet vanuit sql file naar standaard persoon oude bsn 393908586
            Verwacht resultaat: Geen bericht

Given leveringsautorisatie uit /levering_autorisaties_nieuw/Mutatielevering_obv_Doelbinding/Doelbinding_met_pop_bep_2_huwelijken

Given persoonsbeelden uit BIJHOUDING:VHNL04C10T10/Personen_Libby_Thatcher_(Ingeschrevene-I/dbstate003

When voor persoon 422531881 wordt de laatste handeling geleverd

Then is er geen synchronisatiebericht gevonden

Scenario: 2.    Persoon wordt geboren met Geboortedatum 1997/11/00 en komt nieuw in de doelbinding leveringsautorisatie
                LT: R2057_LT29
                maar de dienst mutatielevering op basis van doelbinding is niet geldig
                Verwacht resultaat: GEEN bericht
                Uitwerking
                Dienst mutatielevering op basis van doelbinding
                Dienst.Datum ingang                 > systeemdatum
                Dienst.Datum einde                  > systeemdatum
                Dienstbundel.Datum ingang           < systeemdatum
                Dienstbundel.Datum einde            > systeemdatum
                Toegang Leveringsautorisatie ingang < systeemdatum
                Toegang Leveringsautorisatie einde  > systeemdatum
                Leveringsautorisatie.Datum ingang   < systeemdatum
                Leveringsautorisatie.Datum einde    > systeemdatum

Given leveringsautorisatie uit /levering_autorisaties_nieuw/geen_pop_bep_levering_op_basis_van_doelbinding_dienst_ingang_toekomst
!-- Persoon nieuw in de doelbinding
Given persoonsbeelden uit specials:specials/Jan_xls

When voor persoon 606417801 wordt de laatste handeling geleverd

Then is er geen synchronisatiebericht gevonden