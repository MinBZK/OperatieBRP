Meta:
@sprintnummer           78
@epic                   PDT & aanpassen SW Leveren tbv productiedata 3.1
@status                 Klaar
@sleutelwoorden         GeconverteerdeDataTest

Narrative: Leveren categorie 13 (Kiesrecht) mutatieberichten

Scenario: 01 DELTAVERS13C10T10	Delta in CAT13 Kiesrecht inhoudelijke wijziging (europees kiesrecht)
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS13/DELTAVERS13C10T10_xls
When voor persoon 194681129 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie13/Scenario1.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 02 DELTAVERS13C10T20	Delta in CAT13 Kiesrecht inhoudelijke wijziging (nederlands kiesrecht)
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS13/DELTAVERS13C10T20_xls
When voor persoon 629228425 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie13/Scenario2.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 03 DELTAVERS13C10T30	Van uitsluiting kiesrecht naar EU verkiezing
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS13/DELTAVERS13C10T30_xls
When voor persoon 629228425 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie13/Scenario3.xml voor expressie //brp:lvg_synVerwerkPersoon

Scenario: 04 DELTAVERS13C10T40	Van EU verkiezing naar uitsluiting kiesrecht
Given leveringsautorisatie uit autorisatie/autorisatiealles
Given persoonsbeelden uit oranje:DELTAVERS13/DELTAVERS13C10T40_xls
When voor persoon 629228425 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie autorisatiealles is ontvangen en wordt bekeken
Then is het synchronisatiebericht gelijk aan expected/categorie13/Scenario4.xml voor expressie //brp:lvg_synVerwerkPersoon
