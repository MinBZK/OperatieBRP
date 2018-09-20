Meta:
@sprintnummer           91
@epic                   Archiveer bericht
@auteur                 miuij
@status                 Klaar
@regels                 R1270

Narrative:
Als beheerder wil ik bij een uitgaan mutatie bericht dat alle personen worden gearchiveerd in de ber.pers tabel (excl betrokkenen)


Scenario:   Personen in een mutatiebericht worden gearchiveerd in de berpers tabel excl betrokkenen
            Logische testgevallen LV.1.AV Afhandelen Verzoek LV.1.AB: R1270_L10
            Verwacht resultaat:
            1. Inkomend bericht wordt gearchiveerd (geen expliciete check)
            2. Peronen in het uitgaande bericht worden gearchiveerd in de ber.pers tabel (excl betrokkenen)


Given leveringsautorisatie uit /levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding_Haarlem
Given de database is aangepast met: delete from ber.ber
Given de database is aangepast met: delete from ber.berpers
Given de cache is herladen
Given de personen 727750537,212208937,814591139,875271467,646257353,564779209 zijn verwijderd
Given de standaardpersoon Matilda met bsn 646257353 en anr 4765349650 zonder extra gebeurtenissen
Given de standaardpersoon Gregory met bsn 564779209 en anr 7960380178 zonder extra gebeurtenissen

Given bijhoudingautorisatie uit /bijhoudingsautorisaties/Gemeente_Alkmaar.txt
Given bijhoudingsverzoek voor partij 'Gemeente Alkmaar'
And administratieve handeling van type voltrekkingHuwelijkInNederland , met de acties registratieAanvangHuwelijkGeregistreerdPartnerschap, registratieNaamgebruik
And testdata uit bestand R1270_huwelijk.yml

When het bericht wordt verstuurd
Then heeft het antwoordbericht verwerking Geslaagd

When voor persoon 564779209 wordt de laatste handeling geleverd
When het mutatiebericht voor leveringsautorisatie Geen pop.bep. levering op basis van doelbinding Haarlem is ontvangen en wordt bekeken
Then is het bericht xsd-valide

Then in ber select kern.pers.bsn from ber.berpers left join ber.ber on (berpers.ber = ber.id) left join kern.pers on(berpers.pers= pers.id) where ber.id = (select ber.id from ber.ber where ber.ontvangendepartij='396' and ber.srt='23' limit 1) and berpers.pers = (select id from kern.pers where bsn='564779209') de volgende gegevens:
| veld      | waarde    |
| bsn       | 564779209 |

Then in ber select kern.pers.bsn from ber.berpers left join ber.ber on (berpers.ber = ber.id) left join kern.pers on(berpers.pers= pers.id) where ber.id = (select ber.id from ber.ber where ber.ontvangendepartij='396' and ber.srt='23' limit 1) and berpers.pers = (select id from kern.pers where bsn='646257353') de volgende gegevens:
| bsn       | 646257353 |




