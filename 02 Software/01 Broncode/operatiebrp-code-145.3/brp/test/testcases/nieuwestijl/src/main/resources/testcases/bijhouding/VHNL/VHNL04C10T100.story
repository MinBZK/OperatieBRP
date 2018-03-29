Meta:
@status                 Klaar
@sleutelwoorden         Geslaagd
@usecase                UCS-BY.HG

Narrative:
Registratie aanvang huwelijk in NL tussen I-I en I-I (Happy Flow)

Scenario:   Prevalidatie Personen Libby Thatcher (Ingeschrevene-Ingezetene) en Piet Jansen (Ingeschrevene-Ingezetene) gaan trouwen, controleer relatie, betrokkenheid, afgeleid administratief,
            LT: VHNL04C10T100



Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL/Libby.xls
Given enkel initiele vulling uit bestand /LO3PL/Piet.xls

When voer een bijhouding uit VHNL04C10T100.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding/VHNL/expected/VHNL04C10T100.xml voor expressie //brp:bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R

Then is in de database de persoon met bsn 159247913 niet als PARTNER betrokken bij een HUWELIJK
Then is in de database de persoon met bsn 422531881 niet als PARTNER betrokken bij een HUWELIJK

Then staat er 0 notificatiebericht voor bijhouders op de queue


