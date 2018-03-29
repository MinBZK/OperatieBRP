Meta:
@status             Klaar
@usecase            LV.0.MB
@regels             R1562
@sleutelwoorden     Maak BRP bericht

Narrative:
Een bericht bevat alleen voorkomens van Gegeven in onderzoek waarbij:

Gegeven in onderzoek niet verwijst naar een specifiek attribuut
(Gegeven in onderzoek.Element bevat geen Element met Element.Soort = "Attribuut")

OF

Gegeven in onderzoek verwijst naar een geautoriseerd attribuut
(Gegeven in onderzoek.Element bevat een Element met Element.Soort = "Attribuut", waarbij dit
Element ook voorkomt in Dienstbundel \ Groep \ Attribuut.Attribuut bij de Dienst)


Toelichting:
Het volstaat om te filteren op ongeautoriseerde attributen.
Voor ongeautoriseerde groepen en objecten geldt dat deze niet in het bericht komen en de betreffende onderzoeken er
vervolgens door R1561 - Een bericht bevat alleen onderzoeken naar ontbrekende gegevens en naar groepen/objecten die
zelf aanwezig zijn in het bericht worden uitgefilterd. Voor onderzoeken naar ontbrekende gegevens geldt dat alleen
Bijhouders deze geleverd krijgen en die mogen alle onderzoeksgegevens geleverd krijgen.


Scenario: 01m  Element soort is attribuut en attribuut geautoriseerd
                LT: R1562_LT02
                Verwacht resultaat: Mutatiebericht geleverd met onderzoek op adres

Given persoonsbeelden uit specials:MaakBericht/R1562_Anne_Bakker_Onderzoek_Aanvang_xls
Given leveringsautorisatie uit autorisatie/R1562_0102
When voor persoon 595891305 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Abo geen popbep doelbinding Haarlem is ontvangen en wordt bekeken
Then heeft het bericht 2 groepen 'onderzoek'

Then is het synchronisatiebericht gelijk aan expecteds/R1562_01.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 01v  Element soort is attribuut en attribuut geautoriseerd
                LT: R1562_LT02
                Verwacht resultaat: Volledigbericht geleverd met onderzoek op adres

Given persoonsbeelden uit specials:MaakBericht/R1562_Anne_Bakker_Onderzoek_Aanvang_xls
Given leveringsautorisatie uit autorisatie/R1562_0102
Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'Abo geen popbep doelbinding Haarlem'
|zendendePartijNaam|'Gemeente Haarlem'
|bsn|595891305

Then heeft het antwoordbericht verwerking Geslaagd
And heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Geen'
When het volledigbericht voor leveringsautorisatie Abo geen popbep doelbinding Haarlem is ontvangen en wordt bekeken
Then heeft het bericht 2 groepen 'onderzoek'

Then is het synchronisatiebericht gelijk aan expecteds/R1562_02.xml voor expressie //brp:lvg_synVerwerkPersoon


Scenario: 02m   Element soort is attribuut en het attribuut is NIET geautoriseerd
                LT: R1562_LT03
                Verwacht resultaat:
                Mutatiebericht geleverd zonder onderzoek op adres

Given persoonsbeelden uit specials:MaakBericht/R1562_Anne_Bakker_Onderzoek_Aanvang_xls
Given leveringsautorisatie uit autorisatie/R1562_0304
When voor persoon 595891305 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Abo geen popbep doelbinding Haarlem is ontvangen en wordt bekeken
Then heeft het bericht 0 groepen 'onderzoek'

Then is het synchronisatiebericht gelijk aan expecteds/R1562_03.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 02v   Element soort is attribuut en het attribuut is NIET geautoriseerd
                LT: R1562_LT03
                Verwacht resultaat:
                Volledigbericht geleverd zonder onderzoek op adres

Given persoonsbeelden uit specials:MaakBericht/R1562_Anne_Bakker_Onderzoek_Aanvang_xls
Given leveringsautorisatie uit autorisatie/R1562_0304
Given verzoek synchroniseer persoon:
|key|value
|leveringsautorisatieNaam|'Abo geen popbep doelbinding Haarlem'
|zendendePartijNaam|'Gemeente Haarlem'
|bsn|595891305

Then heeft het antwoordbericht verwerking Geslaagd
And heeft in het antwoordbericht 'hoogsteMeldingsniveau' in 'resultaat' de waarde 'Geen'
When het volledigbericht voor leveringsautorisatie Abo geen popbep doelbinding Haarlem is ontvangen en wordt bekeken
Then heeft het bericht 0 groepen 'onderzoek'

Then is het synchronisatiebericht gelijk aan expecteds/R1562_04.xml voor expressie //brp:lvg_synVerwerkPersoon

