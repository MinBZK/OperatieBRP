Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R1727 Geslachtsnaam Nederlands kind moet overeenkomen met die van eerder kind uit dezelfde relatie

Scenario: NL kind met ongelijke voorvoegsel t.o.v. NI stiefsibling met ingeschreven ouder waarbij ouwkig pseudo is
          LT: GBNL01C120T70

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL01C120T70-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL01C120T70-002.xls

!-- Geboorte toekomstige sibling
When voer een bijhouding uit GBNL01C120T70a.xml namens partij 'Gemeente BRP 1'
Then heeft het antwoordbericht verwerking Foutief

!-- Wijzig bijhoudingsaard en naderebijhaard op kern.his_persbijhouding van de sibling naar Emigratie zodat hij niet-ingezetene wordt.
Given de database is aangepast met: update kern.his_persbijhouding
                                    set    bijhaard=2,
				           naderebijhaard=3
			            where  pers in (select id from kern.pers where voornamen='Siblingnaam');

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
			            where  persadres in (select id from kern.persadres where pers in (select id from kern.pers where voornamen='Siblingnaam'));

!-- Geboorte kind
When voer een bijhouding uit GBNL01C120T70b.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GBNL/expected/GBNL01C120T70.xml voor expressie //brp:bhg_afsRegistreerGeboorte_R