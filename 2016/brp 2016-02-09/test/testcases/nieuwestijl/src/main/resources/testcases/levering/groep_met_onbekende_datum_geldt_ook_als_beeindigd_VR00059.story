Meta:
@sprintnummer           72
@auteur                 rohax
@jiraIssue              TEAMBRP-1982
@status                 Klaar
@regels                 VR00059, R1349

Narrative:
   VR00059 stelt dat een beëindigde groep alleen geleverd mag worden als de afnemer geautoriseerd is voor materiële historie op dit groep.

   De aanscherping voor deze story betreft een einddatum die volledig onbekend is: '00000000'. Ook een groep met een volledig
   onbekende einddatum wordt geacht 'beëindigd' te zijn en wordt dus alleen geleverd bij autorisatie voor materiële historie.

   Om dit te onderzoeken wordt een volledig bericht gevraagd waarbij de adres waarde de datum einde geldigheid krijgt die gevraagd is

   Beeindigde groep alleen leveren bij autorisatie op materiele historie

Scenario: 1 Als de afnemer geen autorisatie heeft voor materiële historie op een groep, en de DatumEindeGeldigheid is leeg (NULL), dan wordt dat voorkomen wel geleverd.

Given de gehele database is gereset
Given leveringsautorisatie uit /levering_autorisaties/mutatielevering_obv_doelbinding/VR00059
And de database is aangepast met: update kern.his_persadres set dateindegel = null where persadres=40

Given verzoek voor leveringsautorisatie 'VR00059' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand groep_met_onbekende_datum_geldt_ook_als_beeindigd_VR00059.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
When het volledigbericht voor leveringsautorisatie VR00059 is ontvangen en wordt bekeken
Then heeft het bericht 1 groep 'adres'


Scenario: 2 Als de afnemer geen autorisatie heeft voor materiële historie op een groep, en de DatumEindeGeldigheid is volledig onbekend ('00000000'), dan wordt dat voorkomen NIET geleverd.

Given de gehele database is gereset
Given leveringsautorisatie uit /levering_autorisaties/mutatielevering_obv_doelbinding/VR00059
And de database is aangepast met: update kern.his_persadres set dateindegel = 0 where persadres=40

Given verzoek voor leveringsautorisatie 'VR00059' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand groep_met_onbekende_datum_geldt_ook_als_beeindigd_VR00059.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
When het volledigbericht voor leveringsautorisatie VR00059 is ontvangen en wordt bekeken
Then heeft het bericht 0 groepen 'adres'


Scenario: 3 ls de afnemer geen autorisatie heeft voor materiële historie op een groep, en de DatumEindeGeldigheid is gedeeltelijk onbekend (b.v. '20120600'), dan wordt dat voorkomen NIET geleverd.

Given de gehele database is gereset
Given leveringsautorisatie uit /levering_autorisaties/mutatielevering_obv_doelbinding/VR00059
And de database is aangepast met: update kern.his_persadres set dateindegel = 20120600 where persadres=40

Given verzoek voor leveringsautorisatie 'VR00059' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand groep_met_onbekende_datum_geldt_ook_als_beeindigd_VR00059.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
When het volledigbericht voor leveringsautorisatie VR00059 is ontvangen en wordt bekeken
Then heeft het bericht 0 groepen 'adres'


Scenario: 4 Als de afnemer geen autorisatie heeft voor materiële historie op een groep, en de DatumEindeGeldigheid is gevuld en bekend (b.v. '20120615'), dan wordt dat voorkomen NIET geleverd

Given de gehele database is gereset
Given leveringsautorisatie uit /levering_autorisaties/mutatielevering_obv_doelbinding/VR00059
And de database is aangepast met: update kern.his_persadres set dateindegel = 20120615 where persadres=40

Given verzoek voor leveringsautorisatie 'VR00059' en partij 'Gemeente Utrecht'
Given verzoek van bericht lvg_synGeefSynchronisatiePersoon
And testdata uit bestand groep_met_onbekende_datum_geldt_ook_als_beeindigd_VR00059.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd
When het volledigbericht voor leveringsautorisatie VR00059 is ontvangen en wordt bekeken
Then heeft het bericht 0 groepen 'adres'
