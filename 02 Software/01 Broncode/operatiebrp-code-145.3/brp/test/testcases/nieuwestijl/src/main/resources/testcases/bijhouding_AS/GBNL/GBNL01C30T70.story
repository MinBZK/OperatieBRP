Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R1690 Kind moet Nederlander worden als het een 3e generatie kind betreft

Scenario: Kind wordt geen Nederlander en 2 ouders zijn ingezetenen op kindgeboorte en 1 grootouder is ingezetene op oudergeboorte
          LT: GBNL01C30T70

Given alle personen zijn verwijderd

!-- Initiële vulling: Ingezeten grootouders
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL01C30T70-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL01C30T70-002.xls

!-- Initiële vulling: Ingezeten vader
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL01C30T70-003.xls

!-- Geboorte: Ingezeten moeder met BSNs van grootouders
When voer een bijhouding uit GBNL01C30T70a.xml namens partij 'Gemeente BRP 1'

!-- Wijzig naderebijhaard op kern.his_persbijhouding van 1 grootouder naar Emigratie op geboorte van de ouders zodat ze niet-ingezetene meer is op oudergeboorte.
Given de database is aangepast met: update kern.his_persbijhouding
                                    set    bijhaard=2,
				           naderebijhaard=3,
				           dataanvgel=20160101
				    where  pers in (select id from kern.pers where voornamen='Libby')

!-- Wijzig het adres naar een buitenlands adres voor RNI
Given de database is aangepast met: update kern.his_persadres
                                    set    aangadresh=3,
					   dataanvadresh=null,
					   identcodeadresseerbaarobject=null,
					   identcodenraand=null,
					   gem=null,
					   nor=null,
					   afgekortenor=null,
					   huisnr=null,
					   postcode=null,
					   wplnaam=null,
					   bladresregel1='Street 1',
					   bladresregel2='city1',
					   bladresregel3='city2',
					   landgebied=68
			            where  persadres in (select id from kern.persadres where pers in (select id from kern.pers where voornamen='Libby'));

!-- Wijzig kern.his_persbijhouding van de andere grootouder zodat hij ingezetene is op oudergeboorte.
Given de database is aangepast met: update kern.his_persbijhouding
                                  set    dataanvgel=20160101
				  where  pers in (select id from kern.pers where voornamen='Jan')

!-- Wijzig het ingezeten zijn van de ouders naar de datum van de kindgeboorte
Given de database is aangepast met: update kern.his_persbijhouding
				    set    dataanvgel=20170101
				    where  pers in (select id from kern.pers where voornamen='Tina' or voornamen='Henk')

!-- Geboorte niet-Nederlands kind met BSNs van moeder en vader die op hun beurt 1 niet-ingezeten ouder heeft en 1 ingezeten ouder.
When voer een bijhouding uit GBNL01C30T70b.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GBNL/expected/GBNL01C30T70.xml voor expressie //brp:bhg_afsRegistreerGeboorte_R