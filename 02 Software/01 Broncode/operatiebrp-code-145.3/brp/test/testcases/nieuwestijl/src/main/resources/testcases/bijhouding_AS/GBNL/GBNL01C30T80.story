Meta:
@status                 Klaar
@usecase                UCS-BY.0.AS

Narrative: R1690 Kind moet Nederlander worden als het een 3e generatie kind betreft

Scenario: Kind wordt geen Nederlander en 1 ouder is ingezetene op kindgeboorte en 2 grootouders zijn ingezetenen op oudergeboorte
          LT: GBNL01C30T80

Given alle personen zijn verwijderd

!-- Initiële vulling: Ingezeten grootouders
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL01C30T80-001.xls
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL01C30T80-002.xls

!-- Initiële vulling: vader met opschorting reden E
Given enkel initiele vulling uit bestand /LO3PL-AS/LO3PL-GBNL/GBNL01C30T80-003.xls

!-- Geboorte: Ingezeten moeder met BSNs van grootouders
When voer een bijhouding uit GBNL01C30T80a.xml namens partij 'Gemeente BRP 1'

!-- Wijzig kern.his_persbijhouding zodat de grootouders ingezetenen zijn op oudergeboorte
Given de database is aangepast met: update kern.his_persbijhouding
				    set    dataanvgel=20160101
				    where  pers in (select id from kern.pers where voornamen='Libby' or voornamen='Jan')

!-- Wijzig kern.his_persbijhouding zodat 1 ouder ingezetene is op kindgeboorte en de andere ouder niet-ingezetene op kindgeboorte
Given de database is aangepast met: update kern.his_persbijhouding
                                    set    dataanvgel=20170101
				    where  pers in (select id from kern.pers where voornamen='Tina' or voornamen='Henk')

!-- Geboorte Niet-Nederlands kind met BSNs van een ingezeten OUWKIG en niet-ingezeten NOUWKIG.
When voer een bijhouding uit GBNL01C30T80b.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Foutief
Then is het antwoordbericht gelijk aan /testcases/bijhouding_AS/GBNL/expected/GBNL01C30T80.xml voor expressie //brp:bhg_afsRegistreerGeboorte_R
