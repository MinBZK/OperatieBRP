Meta:
@sprintnummer           78
@epic                   PDT & aanpassen SW Leveren tbv productiedata 3.1
@status                 Klaar
@sleutelwoorden         GeconverteerdeDataTest, cat4


Narrative: Leveren categorie 4 (nationaliteit) mutatieberichten


Scenario: 01 DELTAVERS04aC10T10 Beeindiging NL nationaliteit + Verkrijging vreemde nationaliteit
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS04a/DELTAVERS04aC10T10_xls
When voor persoon 963363529 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie04a_1/01.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 02 DELTAVERS04aC10T20 Beeindiging vreemde nationaliteit + Verkrijging NL nationaliteit
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS04a/DELTAVERS04aC10T20_xls
When voor persoon 800570121 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie04a_1/02.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 03 DELTAVERS04aC10T30 Beeindiging onbekende nationaliteit + Verkrijging NL nationaliteit
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS04a/DELTAVERS04aC10T30_xls
When voor persoon 519395529 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie04a_1/03.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 04 DELTAVERS04aC10T40 Beeindiging NL nationaliteit + Beeindiging vreemde nationaliteit
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS04a/DELTAVERS04aC10T40_xls
When voor persoon 963363529 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie04a_1/04.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 05 DELTAVERS04aC10T50 Beeindiging NL nationaliteit + Beeindiging vreemde nationaliteit (na eerdere verkrijging en beeindiging)
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS04a/DELTAVERS04aC10T50_xls
When voor persoon 963363529 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie04a_1/05.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 06 DELTAVERS04aC10T60 Beeindiging staatloos + Verkrijging NL nationaliteit
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS04a/DELTAVERS04aC10T60_xls
When voor persoon 164278989 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie04a_1/06.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 07 DELTAVERS04aC10T70 Beeindiging bijzonder NL + Verkrijging NL nationaliteit
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS04a/DELTAVERS04aC10T70_xls
When voor persoon 850275209 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie04a_1/07.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 08 DELTAVERS04aC10T80 Beeindiging vastgesteld niet NL
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS04a/DELTAVERS04aC10T80_xls
When voor persoon 639113801 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie04a_1/08.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 09 DELTAVERS04aC10T90 Beeindiging NL met PROBAS indicatie (wordt in praktijk wellicht niet zo geregistreerd)
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS04a/DELTAVERS04aC10T90_xls
When voor persoon 434328297 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie04a_1/09.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 10 DELTAVERS04aC10T100 Beeindiging nationaliteit met in historie 2x vervallen nationaliteit
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS04a/DELTAVERS04aC10T100_xls
When voor persoon 963363529 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie04a_1/10.xml voor expressie //brp:lvg_synVerwerkPersoon



