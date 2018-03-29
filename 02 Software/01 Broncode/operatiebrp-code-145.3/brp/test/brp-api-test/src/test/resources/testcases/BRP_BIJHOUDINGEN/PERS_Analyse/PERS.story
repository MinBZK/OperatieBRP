Meta:
@status                 Klaar
@sleutelwoorden         PERS

Narrative:
Analyse om brp persoonsbeelden te vergelijken met gba persoonsbeelden

Scenario: 10 Huwelijk tussen BRP persoon en een BRP pseudo
          LT: PERS01C10T10
          Registratie geregistreerd partnerschap met neven actie registratie geslachtsnaam.


Given leveringsautorisatie uit  autorisatie/autorisatiealles
Given persoonsbeelden uit BIJHOUDING:PERS01C10T10/Huwelijk_tussen_BRP_persoon_en_een_BRP_p/dbstate005
When voor persoon 306706921 wordt de laatste handeling geleverd
!-- Ophalen van de mutatie leveringen nav het geregistreerd partnerschap
When het mutatiebericht voor partij Gemeente Utrecht en leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'autorisatiealles'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|306706921

Then heeft het antwoordbericht verwerking Geslaagd


Scenario: 20 Huwelijk tussen GBA_persoon en GBA_pseud
             LT: PERS01C10T20
Given leveringsautorisatie uit  autorisatie/autorisatiealles
Given persoonsbeelden uit BIJHOUDING:PERS01C10T20/Huwelijk_tussen_GBA_persoon_en_GBA_pseud/dbstate002
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'autorisatiealles'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|306706921

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 30 Huwelijk tussen BRP persoon en een BRP pseudo en daarna ontbinding (historie)
             LT: PERS01C10T30
Given leveringsautorisatie uit  autorisatie/autorisatiealles
Given persoonsbeelden uit BIJHOUDING:PERS01C10T30/Huwelijk_tussen_BRP_persoon_en_een_BRP_p/dbstate005
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'autorisatiealles'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|803995209

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 40 Huwelijk tussen GBA persoon en GBA pseudo en ontbinding ervan
             LT: PERS01C10T40

Given leveringsautorisatie uit  autorisatie/autorisatiealles
Given persoonsbeelden uit BIJHOUDING:PERS01C10T40/Huwelijk_tussen_GBA_persoon_en_GBA_pseud/dbstate002
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'autorisatiealles'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|803995209

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 50 Geboorte BRP persoon met 2 ouders
             LT: PERS01C20T10


Given leveringsautorisatie uit  autorisatie/autorisatiealles
Given persoonsbeelden uit BIJHOUDING:PERS01C20T10/Geboorte_BRP_persoon_met_2_ouders_______/dbstate003
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'autorisatiealles'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|137473849

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 60 Geboorte GBA persoon met 2 ouders
             LT: PERS01C20T20

Given leveringsautorisatie uit  autorisatie/autorisatiealles
Given persoonsbeelden uit BIJHOUDING:PERS01C20T20/Geboorte_GBA_persoon_met_2_ouders_______/dbstate002
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'autorisatiealles'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|137473849

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 70 Verhuizing BRP persoon intergemeentelijk
             LT: PERS01C30T10

Given leveringsautorisatie uit  autorisatie/autorisatiealles
Given persoonsbeelden uit BIJHOUDING:PERS01C30T10/Verhuizing_BRP_persoon_intergemeentelijk/dbstate003
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'autorisatiealles'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|494468105

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 80 Verhuizing GBA persoon intergemeentelijk
             LT: PERS01C30T20

Given leveringsautorisatie uit  autorisatie/autorisatiealles
Given persoonsbeelden uit BIJHOUDING:PERS01C30T20/Verhuizing_GBA_persoon_intergemeentelijk/dbstate002
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'autorisatiealles'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|494468105

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 90 Ontrelateren bij Ontbinding huwelijk in Nederland
             LT: PERS01C40T10

Given leveringsautorisatie uit  autorisatie/autorisatiealles
Given persoonsbeelden uit BIJHOUDING:PERS01C40T10/2._Ontrelateren_bij_Ontbinding_huwelijk_/dbstate007
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'autorisatiealles'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|663844745

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 95 Ontrelateren bij Ontbinding huwelijk in Nederland
             LT: PERS01C40T10

Given leveringsautorisatie uit  autorisatie/autorisatiealles
Given persoonsbeelden uit BIJHOUDING:PERS01C40T10/2._Ontrelateren_bij_Ontbinding_huwelijk_/dbstate007

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'autorisatiealles'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|944441993

Then heeft het antwoordbericht verwerking Geslaagd

Scenario: 100 Huwelijk tussen GBA persoon en GBA pseudo en ontbinding ervan
              LT: PERS01C40T20

Given leveringsautorisatie uit  autorisatie/autorisatiealles
Given persoonsbeelden uit BIJHOUDING:PERS01C40T20/Huwelijk_tussen_GBA_persoon_en_GBA_pseud/dbstate002
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'autorisatiealles'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|663844745

Then heeft het antwoordbericht verwerking Geslaagd