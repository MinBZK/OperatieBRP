Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R1690 Kind moet Nederlander worden als het een 3e generatie kind betreft

Scenario: Grootouder is geen ingezetene meer op de geboorte van het kind
          LT: GBNL01C30T60

Given alle personen zijn verwijderd

!-- Initiële vulling: grootouders
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL01C30T60-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL01C30T60-002.xls

!-- Initiële vulling: Ingezeten vader
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL01C30T60-003.xls

!-- Geboorte: Ingezeten moeder met BSNs van grootouders
When voer een bijhouding uit GBNL01C30T60a.xml namens partij 'Gemeente BRP 1'

!-- Wijzig naderebijhaard op kern.his_persbijhouding van grootouders naar Emigratie zodat ze niet-ingezetenen zijn op de geboorte van het kind.
Given de database is aangepast met: update kern.his_persbijhouding
                                    set    bijhaard=2,
				           naderebijhaard=3,
					   dataanvgel=20170101
				    where  pers in (select id from kern.pers where voornamen='Libby' or voornamen='Jan')

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
			            where  persadres in (select id from kern.persadres where pers in (select id from kern.pers where voornamen='Libby' or voornamen='Jan'));

!-- Geboorte kind met BSNs van moeder en vader die op hun beurt inmiddels niet-ingezeten ouders hebben.
When voer een bijhouding uit GBNL01C30T60b.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GBNL/expected/GBNL01C30T60.xml voor expressie //brp:bhg_afsRegistreerGeboorte_R
