Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R1690 Kind moet Nederlander worden als het een 3e generatie kind betreft

Scenario: Ouder is op de geboorte van het kind geen Ingezetene meer
          LT: GBNL01C30T40

Given alle personen zijn verwijderd

!-- Initiële vulling: Ingezeten grootouders
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL01C30T40-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL01C30T40-002.xls

!-- Initiële vulling: Vader met opschortreden E
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL01C30T40-003.xls

!-- Geboorte: Ingezeten moeder met BSNs van grootouders
When voer een bijhouding uit GBNL01C30T40a.xml namens partij 'Gemeente BRP 1'

!-- Wijzig naderebijhaard op kern.his_persbijhouding van pers moeder naar Emigratie zodat ze niet-ingezetene wordt op de geboortedatum van haar toekomstige kind.
Given de database is aangepast met: update kern.his_persbijhouding
                                    set    bijhaard=2,
				           naderebijhaard=3,
                                           dataanvgel=20170101
				    where  pers in (select id from kern.pers where voornamen='Tina')

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
			            where  persadres in (select id from kern.persadres where pers in (select id from kern.pers where voornamen='Tina'));

!-- Geboorte kind met BSNs van niet-ingezeten moeder en vader
When voer een bijhouding uit GBNL01C30T40b.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GBNL/expected/GBNL01C30T40.xml voor expressie //brp:bhg_afsRegistreerGeboorte_R
