Meta:
@status     Klaar

Narrative:
Deze test is ontstaan na I&T bevinding INTEST-2803,
waaruit bleek dat gegevenInOnderzoek geleverd wordt in berichten, wanneer er geen autorisatie is voor de afnemer middels
zijn leveringsautorisatie

Scenario:   1.  Geen autorisatie op onderzoek, wel op in onderzoek staand gegeven (adres).
                LT: R1976_LT02
                Verwacht resultaat: Onderzoek wordt niet geleverd in het bericht

Given leveringsautorisatie uit autorisatie/Geen_autorisatie_voor_onderzoek2
Given persoonsbeelden uit specials:MaakBericht/R1319_ElisaBeth_aanvang_meerdere_onderzoeken_xls
Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'Geen autorisatie voor onderzoek groep2'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|270433417

Then heeft het antwoordbericht verwerking Geslaagd

When het volledigbericht voor leveringsautorisatie Geen autorisatie voor onderzoek groep2 is ontvangen en wordt bekeken
Then heeft het bericht 0 groepen 'onderzoek'
Then heeft het bericht 0 groepen 'gegevensInOnderzoek'