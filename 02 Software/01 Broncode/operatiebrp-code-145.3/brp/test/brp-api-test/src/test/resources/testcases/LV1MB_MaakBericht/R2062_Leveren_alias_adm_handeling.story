Meta:
@status                 Klaar
@sleutelwoorden         Leveren alias administratieve handeling

Narrative:
Indien Leveringsautorisatie.Alias soort administratieve handeling leveren? de waarde "Ja" heeft
dan moet voor ieder bericht die voor deze Leveringsautorisatie geleverd wordt
de Soort administratieve handeling geleverd worden die refereert aan Soort administratieve handeling.Alias.
Is Soort administratieve handeling.Alias niet gevuld, dan moet de Soort administratieve handeling geleverd worden.

Scenario: 1. Administratieve handeling van type Geboorte in nederland
          LT: R2062_LT02

Given leveringsautorisatie uit  autorisatie/R2062_autorisatieAlias
Given persoonsbeelden uit BIJHOUDING:GBNL04C10T10/Registratie_geborene_in_Nederland_met_OU/dbstate003
When voor persoon 954614537 wordt de laatste handeling geleverd

!-- Mutatiebericht met alias 'Geboorte' voor adm handeling 'Geboorte in Nederland'
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatieallesAlias is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/R2062_mutatiebericht_ouder.xml voor expressie //brp:lvg_synVerwerkPersoon

!-- Volledigbericht met alias 'Geboorte' voor adm handeling 'Geboorte in Nederland'
When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatieallesAlias is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expecteds/R2062_volledigbericht_kind.xml voor expressie //brp:lvg_synVerwerkPersoon

!-- Bevraging met alias 'Geboorte' voor adm handeling 'Geboorte in Nederland'
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'autorisatieallesAlias'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|306706921

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan expecteds/R2062_geefdetails.xml voor expressie //lvg_bvgGeefDetailsPersoon_R

Scenario: 2. Mutatie bericht voor leveringsautorisatie waarvoor geldt indaliassrtadmhndleveren = TRUE
               LT: R2062_LT03
               Verwacht Resultaat: Mutatie bericht met meerdere administratieve handelingen
               - Administratieve handlingen waarvoor geen alias aanwezig zijn worden geleverd met de administratieve handeling naam

Given leveringsautorisatie uit  autorisatie/R2062_autorisatieAlias, autorisatie/R2062_Attendering
Given persoonsbeelden uit BIJHOUDING:VHNL04C10T10/Personen_Libby_Thatcher_(Ingeschrevene-I/dbstate003
When voor persoon 159247913 wordt de laatste handeling geleverd

When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatieallesAlias is ontvangen en wordt bekeken
!-- Controle dat de administratieve handeling.Soortnaam geleverd wordt en niet de alias als alias leeg is
Then is er voor xpath //brp:administratieveHandeling/brp:soortNaam[text()='Voltrekking huwelijk in Nederland'] een node aanwezig in het levering bericht
Then is er voor xpath //brp:synchronisatie/brp:soortNaam[text()='Voltrekking huwelijk in Nederland'] een node aanwezig in het levering bericht

When het volledigbericht voor partij Gemeente Utrecht en leveringsautorisatie Attendering met alias is ontvangen en wordt bekeken
!-- Controle dat de administratieve handeling.Soortnaam geleverd wordt en niet de alias als alias leeg is
Then is er voor xpath //brp:administratieveHandeling/brp:soortNaam[text()='Voltrekking huwelijk in Nederland'] een node aanwezig in het levering bericht
Then is er voor xpath //brp:synchronisatie/brp:soortNaam[text()='Voltrekking huwelijk in Nederland'] een node aanwezig in het levering bericht

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'autorisatieallesAlias'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|159247913

Then heeft het antwoordbericht verwerking Geslaagd

