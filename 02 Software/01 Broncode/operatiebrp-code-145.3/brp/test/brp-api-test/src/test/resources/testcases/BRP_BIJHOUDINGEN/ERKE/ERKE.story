Meta:
@status                 Klaar
@sleutelwoorden         ERKE04C10

Narrative:
Als BRP wil ik valideren dat een bijhouding van het type Erkenning
correct geleverd wordt aan afnemers van het BRP.
ERKE04C10	Erkenning: Hoofdactie Registratie ouder
ERKE04C20	Erkenning: Nevenactie Registratie geslachtsnaam voornaam
ERKE04C30	Erkenning: Nevenactie Registratie nationaliteit
ERKE04C40	Erkenning: Nevenactie Beëindiging nationaliteit

NB: De testscenario's zijn opgesteld vanuit het perspectief van het geboren kind, in de mutatie berichten komen echter meerdere personen mee
Omdat er ook wijzigingen plaatsvinden bij de ouder van het kind.

Scenario: 1. Administratieve handeling van type Erkenning waarbij de alle betrokken partijen een verschillende gemeente heeft
          LT: ERKE04C10T10
          ERKE04C10T10 - Erkenning waarbij de alle betrokken partijen een verschillende gemeente heeft

Given leveringsautorisatie uit  autorisatie/autorisatiealles,
                                autorisatie/Attendering_Mutatielevering_afnemerindicatie

Given persoonsbeelden uit BIJHOUDING:ERKE04C10T10/Erkenning_waarbij_de_alle_betrokken_part/dbstate003
When voor persoon 264484009 wordt de laatste handeling geleverd

When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/mutatie-bericht-scenario1.xml voor expressie //brp:lvg_synVerwerkPersoon

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering met plaatsing en expressies is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/volledig-bericht-scenario1.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 2. Administratieve handeling van type De geboortegemeente van het kind dient een bijhoudingsvoorstel in inzake erkenning
          LT: ERKE04C10T20
          ERKE04C10T20 - De geboortegemeente van het kind dient een bijhoudingsvoorstel in inzake erkenning

Given leveringsautorisatie uit  autorisatie/autorisatiealles,
                                autorisatie/Attendering_Mutatielevering_afnemerindicatie

Given persoonsbeelden uit BIJHOUDING:ERKE04C10T20/De_geboortegemeente_van_het_kind_dient_e/dbstate003
When voor persoon 743264137 wordt de laatste handeling geleverd

When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/mutatie-bericht-scenario2.xml voor expressie //brp:lvg_synVerwerkPersoon

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering met plaatsing en expressies is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/volledig-bericht-scenario2.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 3. Administratieve handeling van type De woongemeente van het kind dient een bijhoudingsvoorstel in inzake erkenning
          LT: ERKE04C10T30
          ERKE04C10T30 - De woongemeente van het kind dient een bijhoudingsvoorstel in inzake erkenning

Given leveringsautorisatie uit  autorisatie/autorisatiealles,
                                autorisatie/Attendering_Mutatielevering_afnemerindicatie

Given persoonsbeelden uit BIJHOUDING:ERKE04C10T30/De_woongemeente_van_het_kind_dient_een_b/dbstate003
When voor persoon 537979049 wordt de laatste handeling geleverd

When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/mutatie-bericht-scenario3.xml voor expressie //brp:lvg_synVerwerkPersoon

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering met plaatsing en expressies is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/volledig-bericht-scenario3.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 4. Administratieve handeling van type Erkenning zorgt ervoor dat Bijzondere verblijfsrechtelijke positie wordt verkregen
          LT: ERKE04C10T40
          ERKE04C10T40 - Erkenning zorgt ervoor dat Bijzondere verblijfsrechtelijke positie wordt verkregen

Given leveringsautorisatie uit  autorisatie/autorisatiealles,
                                autorisatie/Attendering_Mutatielevering_afnemerindicatie

Given persoonsbeelden uit BIJHOUDING:ERKE04C10T40/Erkenning_zorgt_ervoor_dat_Bijzondere_ve/dbstate003
When voor persoon 972317065 wordt de laatste handeling geleverd

When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/mutatie-bericht-scenario4.xml voor expressie //brp:lvg_synVerwerkPersoon

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering met plaatsing en expressies is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/volledig-bericht-scenario4.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 5. Administratieve handeling van type Erkenning zorgt ervoor dat Bijzondere verblijfsrechtelijke positie niet kan worden verkregen vanwege NL nationaliteit
          LT: ERKE04C10T50
          ERKE04C10T50 - Erkenning zorgt ervoor dat Bijzondere verblijfsrechtelijke positie niet kan worden verkregen vanwege NL nationaliteit

Given leveringsautorisatie uit  autorisatie/autorisatiealles,
                                autorisatie/Attendering_Mutatielevering_afnemerindicatie

Given persoonsbeelden uit BIJHOUDING:ERKE04C10T50/Erkenning_zorgt_ervoor_dat_Bijzondere_ve/dbstate003
When voor persoon 946438857 wordt de laatste handeling geleverd

When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/mutatie-bericht-scenario5.xml voor expressie //brp:lvg_synVerwerkPersoon

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering met plaatsing en expressies is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/volledig-bericht-scenario5.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 6. Administratieve handeling van type Erkenning waarbij de ouder erkenner een pseudo-persoon is
          LT: ERKE04C10T60
          ERKE04C10T60 - Erkenning waarbij de ouder erkenner een pseudo-persoon is

Given leveringsautorisatie uit  autorisatie/autorisatiealles,
                                autorisatie/Attendering_Mutatielevering_afnemerindicatie

Given persoonsbeelden uit BIJHOUDING:ERKE04C10T60/Erkenning_waarbij_de_ouder_of_erkenner_e/dbstate002
When voor persoon 237394121 wordt de laatste handeling geleverd

When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/mutatie-bericht-scenario6.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 7. Administratieve handeling van type Erkenning zorgt ervoor dat de voornamen wordt opgenomen door namenreeks
          LT: ERKE04C20T10
          ERKE04C20T10 - Erkenning zorgt ervoor dat de voornamen wordt opgenomen door namenreeks

Given leveringsautorisatie uit  autorisatie/autorisatiealles,
                                autorisatie/Attendering_Mutatielevering_afnemerindicatie

Given persoonsbeelden uit BIJHOUDING:ERKE04C20T10/Erkenning_zorgt_ervoor_dat_de_voornamen_/dbstate003
When voor persoon 142856721 wordt de laatste handeling geleverd

When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/mutatie-bericht-scenario7.xml voor expressie //brp:lvg_synVerwerkPersoon

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering met plaatsing en expressies is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/volledig-bericht-scenario7.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 8. Administratieve handeling van type Erkenning zorgt ervoor dat Bijzondere verblijfsrechtelijke positie wordt vervallen
          LT: ERKE04C30T10
          ERKE04C30 - Erkenning Nevenactie Registratie nationaliteit
          ERKE04C30T10 - Erkenning zorgt ervoor dat Bijzondere verblijfsrechtelijke positie wordt vervallen

Given leveringsautorisatie uit  autorisatie/autorisatiealles,
                                autorisatie/Attendering_Mutatielevering_afnemerindicatie

Given persoonsbeelden uit BIJHOUDING:ERKE04C30T10/Erkenning_zorgt_ervoor_dat_Bijzondere_ve/dbstate003
When voor persoon 232699513 wordt de laatste handeling geleverd

When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/mutatie-bericht-scenario8.xml voor expressie //brp:lvg_synVerwerkPersoon

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering met plaatsing en expressies is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/volledig-bericht-scenario8.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 9. Administratieve handeling van type Erkenning zorgt ervoor dat verblijfsrecht vervalt
          LT: ERKE04C30T20
          ERKE04C30T20 - Erkenning zorgt ervoor dat verblijfsrecht vervalt

Given leveringsautorisatie uit  autorisatie/autorisatiealles,
                                autorisatie/Attendering_Mutatielevering_afnemerindicatie

Given persoonsbeelden uit BIJHOUDING:ERKE04C30T20/Erkenning_zorgt_ervoor_dat_verblijfsrech/dbstate003
When voor persoon 427105225 wordt de laatste handeling geleverd

When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/mutatie-bericht-scenario9.xml voor expressie //brp:lvg_synVerwerkPersoon

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering met plaatsing en expressies is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/volledig-bericht-scenario9.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 10. Administratieve handeling van type Beëindiging nationaliteit via Erkenning
          LT: ERKE04C40T10
          ERKE04C40T10 - Beëindiging nationaliteit via Erkenning

Given leveringsautorisatie uit  autorisatie/autorisatiealles,
                                autorisatie/Attendering_Mutatielevering_afnemerindicatie

Given persoonsbeelden uit BIJHOUDING:ERKE04C40T10/Beëindiging_nationaliteit_via__Erkenning/dbstate003
When voor persoon 518089769 wordt de laatste handeling geleverd

!-- NB Zowel de gewijzigde ouder gegevens als de gewijzigde kind gegevens zijn aanwezig in het mutatie bericht
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/mutatie-bericht-scenario10.xml voor expressie //brp:lvg_synVerwerkPersoon

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering met plaatsing en expressies is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/volledig-bericht-scenario10.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 11. Administratieve handeling van type Meerdere nationaliteiten waarbij de nationaliteit bij geboorte wordt beëindigd en kind de NL nationaliteit krijgt
          LT: ERKE04C40T20
          ERKE04C40T20 - Meerdere nationaliteiten waarbij de nationaliteit bij geboorte wordt beëindigd en kind de NL nationaliteit krijgt

Given leveringsautorisatie uit  autorisatie/autorisatiealles,
                                autorisatie/Attendering_Mutatielevering_afnemerindicatie

Given persoonsbeelden uit BIJHOUDING:ERKE04C40T20/Meerdere_nationaliteiten_waarbij_de_nati/dbstate003
When voor persoon 443684169 wordt de laatste handeling geleverd

When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/mutatie-bericht-scenario11.xml voor expressie //brp:lvg_synVerwerkPersoon

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering met plaatsing en expressies is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/volledig-bericht-scenario11.xml voor expressie //brp:lvg_synVerwerkPersoon
