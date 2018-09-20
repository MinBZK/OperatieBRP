Meta:
@epic               Verbeteren testtooling
@auteur             kedon
@status             Klaar
@usecase            SA.0.SP
@regels             R1339,R1342,R1344,R1345,R1346,R1347,R1403,R1538,R1539,R1978,R1982,R2002,R2016,R2059,R2062
@sleutelwoorden     Synchronisatie persoon


Narrative:
Synchroniseer persoon:
Als afnemer wil ik een afnemerverzoek tot het synchroniseren van een persoon afhandelen,
met dienst 'Mutatielevering op basis van doelbinding' of 'Mutatielevering op basis van afnemerindicatie',
zodat de afnemer voor de opgegeven Persoon een Volledig bericht ontvangt
Foutpaden


Scenario:   5.   Verzoek synchronisatie persoon, met dienst mutatielevering op basis van doelbinding en
                Populatiebeperking dienst mutatielevering op basis van doelbinding evalueert op ONWAAR
            Logische testgevallen Use Case: R1344_03, R1345_01, R1347_01, R1978_02, R1982_02
            Verwacht resultaat: Response bericht
                Met vulling:
                -  Verwerking = Foutief
                -  Foutmelding = De opgegeven persoon valt niet te synchroniseren binnen de opgegeven leveringsautorisatie.


Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given de personen 803697417, 968282441, 412670409 zijn verwijderd
Given de standaardpersoon UC_Timmy met bsn 412670409 en anr 7157893906 zonder extra gebeurtenissen
Given de database is aangepast met: update autaut.levsautorisatie set populatiebeperking='ONWAAR' where naam = 'Geen pop.bep. levering op basis van doelbinding'
Given de cache is herladen


Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van doelbinding' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand 1.2_Synchroniseer_Persoon_1.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief
Then heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Fout'
Then heeft in het antwoordbericht 'melding' in 'melding' de waarde 'De opgegeven persoon valt niet te synchroniseren binnen de opgegeven leveringsautorisatie.'

Scenario:   6.   Verzoek synchronisatie persoon, met dienst mutatielevering op basis van doelbinding en
                Populatiebeperking dienst mutatielevering op basis van doelbinding evalueert op NULL
            Logische testgevallen Use Case: R1344_04, R1345_01, R1347_01, R1978_02, R1982_02
            Verwacht resultaat: Response bericht
                Met vulling:
                -  Verwerking = Geslaagd
                -  Foutmelding = De opgegeven persoon valt niet te synchroniseren binnen de opgegeven leveringsautorisatie.
            Bevinding: zie TEAMBRP 4026

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given de personen 803697417, 968282441, 412670409 zijn verwijderd
Given de standaardpersoon UC_Timmy met bsn 412670409 en anr 7157893906 zonder extra gebeurtenissen
Given de database is aangepast met: update autaut.levsautorisatie set populatiebeperking=NULL where naam = 'Geen pop.bep. levering op basis van doelbinding'
Given de cache is herladen

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van doelbinding' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand 1.2_Synchroniseer_Persoon_1.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

Scenario:   7.   Verzoek synchronisatie persoon, met dienst mutatielevering op basis van doelbinding en
                DatumIngang dienst mutatielevering op basis van doelbinding > systeemdatum
            Logische testgevallen Use Case: R1344_05, R1345_01, R1347_03, R1978_02, R1982_02
            Verwacht resultaat: Response bericht
                Met vulling:
                -  Verwerking = Foutief
                -  Foutmelding = Er is geen dienst mutatielevering aanwezig binnen de opgegeven leveringsautorisatie.

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given de personen 803697417, 968282441, 412670409 zijn verwijderd
Given de standaardpersoon UC_Timmy met bsn 412670409 en anr 7157893906 zonder extra gebeurtenissen
Given de database is aangepast met: update autaut.dienst set datingang='${vandaagsql(0,0,1)}' where srt=1
Given de cache is herladen

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van doelbinding' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand 1.2_Synchroniseer_Persoon_1.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief
Then heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Fout'

And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                                                                    |
| BRLV0041 | Er is geen dienst mutatielevering aanwezig binnen de opgegeven leveringsautorisatie.       |


Scenario:   8.   Verzoek synchronisatie persoon, met dienst mutatielevering op basis van doelbinding en
                DatumEinde volgen dienst mutatielevering op basis van doelbinding < systeemdatum
            Logische testgevallen Use Case: R1344_06, R1345_01, R1347_04, R1978_02, R1982_02
            Verwacht resultaat: Response bericht
                Met vulling:
                -  Verwerking = Foutief
                -  Foutmelding = De dienst van de gevraagde soort binnen het opgegeven leveringsautorisatie moet geldig zijn op de systeemdatum.

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given de personen 803697417, 968282441, 412670409 zijn verwijderd
Given de standaardpersoon UC_Timmy met bsn 412670409 en anr 7157893906 zonder extra gebeurtenissen
Given de database is aangepast met: update autaut.dienst set dateinde='${vandaagsql(-1,0,0)}' where srt=1
Given de cache is herladen

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van doelbinding' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand 1.2_Synchroniseer_Persoon_1.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief
Then heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Fout'

And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                                                                    |
| BRLV0041 | Er is geen dienst mutatielevering aanwezig binnen de opgegeven leveringsautorisatie.       |

Scenario:   9.   Nadere.populatiebeperking dienst Mutatielevering op basis van doelbinding = onwaar
            Logische testgevallen Use Case: R1344_01, R1345_03, R1347_01, R1978_02, R1982_02
            Verwacht resultaat: Response bericht
                Met vulling:
                -  Verwerking = Foutief
                -  Foutmelding = De opgegeven persoon valt niet te synchroniseren binnen de opgegeven leveringsautorisatie.

Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given de personen 803697417, 968282441, 412670409 zijn verwijderd
Given de standaardpersoon UC_Timmy met bsn 412670409 en anr 7157893906 zonder extra gebeurtenissen
Given de database is aangepast met: update autaut.levsautorisatie set populatiebeperking='ONWAAR' where naam = 'Geen pop.bep. levering op basis van doelbinding'
Given de cache is herladen

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van doelbinding' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand 1.2_Synchroniseer_Persoon_1.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief
Then heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Fout'
Then heeft in het antwoordbericht 'melding' in 'melding' de waarde 'De opgegeven persoon valt niet te synchroniseren binnen de opgegeven leveringsautorisatie.'


Scenario:   10.1   Verzoek synchronisatie persoon, met dienst mutatielevering op basis van afnemerindicatie en
                DatumEinde volgen dienst mutatielevering op basis van afnemerindicatie < systeemdatum
            Logische testgevallen Use Case: R1345_02, R1346_02, R1347_05, R1978_02, R1982_02
            Verwacht resultaat: Response bericht
                Met vulling:
                -  Verwerking = Geslaagd

Given leveringsautorisatie uit /levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given de personen 803697417, 968282441, 412670409 zijn verwijderd
Given de standaardpersoon UC_Timmy met bsn 412670409 en anr 7157893906 zonder extra gebeurtenissen

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synRegistreerAfnemerindicatie
And testdata uit bestand 1.4_Plaats_Afnemerindicatie.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
And heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Geen'

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is het bericht xsd-valide


Scenario:   10.2   Verzoek synchronisatie persoon, met dienst mutatielevering op basis van afnemerindicatie en
                DatumEinde volgen dienst mutatielevering op basis van afnemerindicatie < systeemdatum
            Logische testgevallen Use Case: R1345_02, R1346_02, R1347_05, R1978_02, R1982_02
            Verwacht resultaat: Response bericht
                Met vulling:
                -  Verwerking = Foutief
                -  Foutmelding = Er is geen dienst mutatielevering aanwezig binnen de opgegeven leveringsautorisatie.

Given de database is aangepast met: update autaut.dienst set dateinde='${vandaagsql(0,0,-1)}' where srt=9
Given de cache is herladen
Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van afnemerindicatie' en partij 'Gemeente Olst'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand 1.3_Synchroniseer_Persoon_2.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief
Then heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Fout'
And heeft het antwoordbericht de meldingen:
| CODE  | MELDING                            |
| R1262 | De gevraagde dienst is niet geldig |


Scenario:   11. Afnemer vraagt gegevens op van een persoon, deze persoon heeft een verstrekkingsbeperking voor de betreffende afnemer.
            Logisch testgevallen: R1339_01, R1342_02
            Verwacht resultaat: 1. Synchroon responsebericht
                                Met vulling:
                                -  Verwerking = foutief
                                -  Melding: Bij deze persoon geldt een verstrekkingsbeperking waardoor deze dienst niet geleverd kan worden.
                                2. Er wordt niet geleverd voor deze persoon
            Bevinding: JIRA ISSUE TEAMBRP-4500 ondanks de verstrekkingsbeperking wordt er toch geleverd


Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding
Given de database is aangepast met: update kern.partij set indverstrbeperkingmogelijk = 'TRUE' where naam='Gemeente Utrecht'
Given de cache is herladen
Given de personen 803697417, 968282441, 412670409 zijn verwijderd
Given de standaardpersoon UC_Timmy met bsn 412670409 en anr 7157893906  met extra gebeurtenissen:
verstrekkingsbeperking() {
    registratieBeperkingen( partij: 34401 )
}

Given verzoek voor leveringsautorisatie 'Geen pop.bep. levering op basis van doelbinding' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand 1.2_Synchroniseer_Persoon_1.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Foutief
Then heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Fout'
And heeft het antwoordbericht de meldingen:
| CODE     | MELDING                                                                                    |
| BRLV0031 | De persoon heeft een verstrekkingsbeperking waardoor deze dienst niet geleverd kan worden. |

When het volledigbericht voor leveringsautorisatie Geen pop.bep. levering op basis van afnemerindicatie is ontvangen en wordt bekeken
Then is er geen synchronisatiebericht gevonden