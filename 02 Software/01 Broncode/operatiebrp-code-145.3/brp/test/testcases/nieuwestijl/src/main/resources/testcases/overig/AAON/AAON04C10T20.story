Meta:
@status                 Klaar
@usecase                BY.0.ONZ

Narrative:
Aanvang onderzoek gegevens persoon

Scenario: Persoon krijgt onderzoek op verschillende objecten en voorkomens
          LT: AAON04C10T20

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-AAON/AAON04C10T10-Libby.xls

Then heeft $HIS_PERSOON_ADRES$ de waarde van de volgende query: select max(id) from kern.his_persadres
Then heeft $PERSOON_ADRES_OBJ$ de waarde van de volgende query: select max(id) from kern.persadres
Then heeft $HIS_PERSOON_BIJHOUDING$ de waarde van de volgende query: select max(id) from kern.his_persbijhouding
Then heeft $HIS_PERSOON_BLPERSNR$ de waarde van de volgende query: select max(id) from kern.his_persblpersnr
Then heeft $PERSOON_BLPERSNR_OBJ$ de waarde van de volgende query: select max(id) from kern.persblpersnr
Then heeft $HIS_PERSOON_EUVERKIEZ$ de waarde van de volgende query: select max(id) from kern.his_persdeelneuverkiezingen
Then heeft $HIS_PERSOON_GEBOORTE$ de waarde van de volgende query: select max(pg.id) from kern.his_persgeboorte pg join kern.pers p on p.id = pg.pers where p.bsn = '422531881'
Then heeft $HIS_PERSOON_GESLACHT$ de waarde van de volgende query: select max(pg.id) from kern.his_persgeslachtsaand pg join kern.pers p on p.id = pg.pers where p.bsn = '422531881'
Then heeft $HIS_PERSOON_GESLNAAMCOMP$ de waarde van de volgende query: select max(id) from kern.his_persgeslnaamcomp
Then heeft $PERSOON_GESLNAAMCOMP_OBJ$ de waarde van de volgende query: select max(id) from kern.persgeslnaamcomp
Then heeft $HIS_PERSOON_IDS$ de waarde van de volgende query: select max(pg.id) from kern.his_persids pg join kern.pers p on p.id = pg.pers where p.bsn = '422531881'
Then heeft $PERSOON_IDENTITEIT_OBJ$ de waarde van de volgende query: select max(id) from kern.pers where voornamen = 'Libby'
Then heeft $HIS_PERSOON_INDICATIE_DERDE$ de waarde van de volgende query: select max(hpi.id) from kern.his_persindicatie hpi join kern.persindicatie pi on pi.id = hpi.persindicatie join kern.srtindicatie si on si.id = pi.srt where si.naam = 'Derde heeft gezag?'
Then heeft $PERSOON_INDICATIE_DERDE_OBJ$ de waarde van de volgende query: select max(pi.id) from kern.persindicatie pi join kern.srtindicatie si on si.id = pi.srt where si.naam = 'Derde heeft gezag?'
Then heeft $HIS_PERSOON_INDICATIE_VBP$ de waarde van de volgende query: select max(hpi.id) from kern.his_persindicatie hpi join kern.persindicatie pi on pi.id = hpi.persindicatie join kern.srtindicatie si on si.id = pi.srt where si.naam = 'Bijzondere verblijfsrechtelijke positie?'
Then heeft $PERSOON_INDICATIE_VBP_OBJ$ de waarde van de volgende query: select max(pi.id) from kern.persindicatie pi join kern.srtindicatie si on si.id = pi.srt where si.naam = 'Bijzondere verblijfsrechtelijke positie?'
Then heeft $PERSOON_INDICATIE_OBJ$ de waarde van de volgende query: select max(id) from kern.persindicatie
Then heeft $HIS_PERSOON_INDICATIE$ de waarde van de volgende query: select max(id) from kern.his_persindicatie
Then heeft $HIS_PERSOON_INDICATIE_CURATELE$ de waarde van de volgende query: select max(hpi.id) from kern.his_persindicatie hpi join kern.persindicatie pi on pi.id = hpi.persindicatie join kern.srtindicatie si on si.id = pi.srt where si.naam = 'Onder curatele?'
Then heeft $PERSOON_INDICATIE_CURATELE_OBJ$ de waarde van de volgende query: select max(pi.id) from kern.persindicatie pi join kern.srtindicatie si on si.id = pi.srt where si.naam = 'Onder curatele?'
Then heeft $PERSOON_INDICATIE_ONVERWERKT_OBJ$ de waarde van de volgende query: select max(pi.id) from kern.persindicatie pi join kern.srtindicatie si on si.id = pi.srt where si.naam = 'Onverwerkt document aanwezig?'
Then heeft $HIS_PERSOON_INDICATIE_ONVERWERKT$ de waarde van de volgende query: select max(hpi.id) from kern.his_persindicatie hpi join kern.persindicatie pi on pi.id = hpi.persindicatie join kern.srtindicatie si on si.id = pi.srt where si.naam = 'Onverwerkt document aanwezig?'
Then heeft $PERSOON_INDICATIE_BEPERKING_OBJ$ de waarde van de volgende query: select max(pi.id) from kern.persindicatie pi join kern.srtindicatie si on si.id = pi.srt where si.naam = 'Volledige verstrekkingsbeperking?'
Then heeft $HIS_PERSOON_INDICATIE_BEPERKING$ de waarde van de volgende query: select max(hpi.id) from kern.his_persindicatie hpi join kern.persindicatie pi on pi.id = hpi.persindicatie join kern.srtindicatie si on si.id = pi.srt where si.naam = 'Volledige verstrekkingsbeperking?'
Then heeft $HIS_PERSOON_INSCHRIJVING$ de waarde van de volgende query: select max(id) from kern.his_persinschr
Then heeft $HIS_PERSOON_MIGRATIE$ de waarde van de volgende query: select max(id) from kern.his_persmigratie
Then heeft $HIS_PERSOON_NAAMGEBRUIK$ de waarde van de volgende query: select max(id) from kern.his_persnaamgebruik
Then heeft $HIS_PERSOON_NATIONALITEIT$ de waarde van de volgende query: select max(id) from kern.his_persnation
Then heeft $PERSOON_NATIONALITEIT_OBJ$ de waarde van de volgende query: select max(id) from kern.persnation
Then heeft $HIS_PERSOON_NRVERWIJZING$ de waarde van de volgende query: select max(id) from kern.his_persnrverwijzing
Then heeft $HIS_PERSOON_OUDERSCHAP$ de waarde van de volgende query: select max(id) from kern.his_ouderouderschap where dataanvgel = '20110914'
Then heeft $HIS_PERSOON_OVERLIJDEN$ de waarde van de volgende query: select max(id) from kern.his_persoverlijden
Then heeft $HIS_PERSOON_PK$ de waarde van de volgende query: select max(id) from kern.his_perspk
Then heeft $HIS_PERSOON_REISDOC$ de waarde van de volgende query: select max(id) from kern.his_persreisdoc
Then heeft $PERSOON_REISDOC_OBJ$ de waarde van de volgende query: select max(id) from kern.persreisdoc
Then heeft $HIS_PERSOON_SGNAAM$ de waarde van de volgende query: select max(pg.id) from kern.his_perssamengesteldenaam pg join kern.pers p on p.id = pg.pers where p.bsn = '422531881'
Then heeft $HIS_PERSOON_KIES$ de waarde van de volgende query: select max(id) from kern.his_persuitslkiesr
Then heeft $HIS_PERSOON_VBR$ de waarde van de volgende query: select max(id) from kern.his_persverblijfsr
Then heeft $HIS_PERSOON_VERIFICATIE$ de waarde van de volgende query: select max(id) from kern.his_persverificatie
Then heeft $PERSOON_VERIFICATIE_OBJ$ de waarde van de volgende query: select max(id) from kern.persverificatie
Then heeft $HIS_PERSOON_VOORNAAM$ de waarde van de volgende query: select max(id) from kern.his_persvoornaam
Then heeft $PERSOON_VOORNAAM_OBJ$ de waarde van de volgende query: select max(id) from kern.persvoornaam
When voer een bijhouding uit AAON04C10T20.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

Then in kern heeft select o.dataanv, o.oms, so.naam, e.naam as element from kern.onderzoek o
                      join kern.pers p on o.pers = p.id
                      join kern.statusonderzoek so on so.id = o.status
                      join kern.gegeveninonderzoek gio on gio.onderzoek = o.id
                      join kern.element e on e.id = gio.element
                      where p.bsn = '422531881' order by e.naam
de volgende gegevens:
| veld       | waarde                                           |
| dataanv    | 20170601                                         |
| oms        | Onderzoek naar gegevens van persoonslijst        |
| naam       | In uitvoering                                    |
| element    | Persoon.Adres.Identiteit                         |
----
| dataanv    | 20170601                                         |
| oms        | Onderzoek naar gegevens van persoonslijst        |
| naam       | In uitvoering                                    |
| element    | Persoon.Adres.Standaard                          |
----
| dataanv    | 20170601                                         |
| oms        | Onderzoek naar gegevens van persoonslijst        |
| naam       | In uitvoering                                    |
| element    | Persoon.Bijhouding                               |
----
| dataanv    | 20170601                                         |
| oms        | Onderzoek naar gegevens van persoonslijst        |
| naam       | In uitvoering                                    |
| element    | Persoon.BuitenlandsPersoonsnummer.Identiteit     |
----
| dataanv    | 20170601                                         |
| oms        | Onderzoek naar gegevens van persoonslijst        |
| naam       | In uitvoering                                    |
| element    | Persoon.BuitenlandsPersoonsnummer.Standaard      |
----
| dataanv    | 20170601                                         |
| oms        | Onderzoek naar gegevens van persoonslijst        |
| naam       | In uitvoering                                    |
| element    | Persoon.DeelnameEUVerkiezingen                   |
----
| dataanv    | 20170601                                         |
| oms        | Onderzoek naar gegevens van persoonslijst        |
| naam       | In uitvoering                                    |
| element    | Persoon.Geboorte                                 |
----
| dataanv    | 20170601                                         |
| oms        | Onderzoek naar gegevens van persoonslijst        |
| naam       | In uitvoering                                    |
| element    | Persoon.Geslachtsaanduiding                      |
----
| dataanv    | 20170601                                         |
| oms        | Onderzoek naar gegevens van persoonslijst        |
| naam       | In uitvoering                                    |
| element    | Persoon.Geslachtsnaamcomponent.Identiteit        |
----
| dataanv    | 20170601                                         |
| oms        | Onderzoek naar gegevens van persoonslijst        |
| naam       | In uitvoering                                    |
| element    | Persoon.Geslachtsnaamcomponent.Standaard         |
----
| dataanv    | 20170601                                         |
| oms        | Onderzoek naar gegevens van persoonslijst        |
| naam       | In uitvoering                                    |
| element    | Persoon.Identificatienummers                     |
----
| dataanv    | 20170601                                         |
| oms        | Onderzoek naar gegevens van persoonslijst        |
| naam       | In uitvoering                                    |
| element    | Persoon.Identiteit                               |
----
| dataanv    | 20170601                                         |
| oms        | Onderzoek naar gegevens van persoonslijst        |
| naam       | In uitvoering                                    |
| element    | Persoon.Indicatie.BehandeldAlsNederlander.Standaard |
----
| dataanv    | 20170601                                         |
| oms        | Onderzoek naar gegevens van persoonslijst        |
| naam       | In uitvoering                                    |
| element    | Persoon.Indicatie.BijzondereVerblijfsrechtelijkePositie.Identiteit |
----
| dataanv    | 20170601                                         |
| oms        | Onderzoek naar gegevens van persoonslijst        |
| naam       | In uitvoering                                    |
| element    | Persoon.Indicatie.BijzondereVerblijfsrechtelijkePositie.Standaard |
----
| dataanv    | 20170601                                         |
| oms        | Onderzoek naar gegevens van persoonslijst        |
| naam       | In uitvoering                                    |
| element    | Persoon.Indicatie.DerdeHeeftGezag.Identiteit     |
----
| dataanv    | 20170601                                         |
| oms        | Onderzoek naar gegevens van persoonslijst        |
| naam       | In uitvoering                                    |
| element    | Persoon.Indicatie.DerdeHeeftGezag.Standaard      |
----
| dataanv    | 20170601                                         |
| oms        | Onderzoek naar gegevens van persoonslijst        |
| naam       | In uitvoering                                    |
| element    | Persoon.Indicatie.Identiteit                     |
----
| dataanv    | 20170601                                         |
| oms        | Onderzoek naar gegevens van persoonslijst        |
| naam       | In uitvoering                                    |
| element    | Persoon.Indicatie.OnderCuratele.Identiteit       |
----
| dataanv    | 20170601                                         |
| oms        | Onderzoek naar gegevens van persoonslijst        |
| naam       | In uitvoering                                    |
| element    | Persoon.Indicatie.OnderCuratele.Standaard        |
----
| dataanv    | 20170601                                         |
| oms        | Onderzoek naar gegevens van persoonslijst        |
| naam       | In uitvoering                                    |
| element    | Persoon.Indicatie.OnverwerktDocumentAanwezig.Identiteit|
----
| dataanv    | 20170601                                         |
| oms        | Onderzoek naar gegevens van persoonslijst        |
| naam       | In uitvoering                                    |
| element    | Persoon.Indicatie.OnverwerktDocumentAanwezig.Standaard|
----
| dataanv    | 20170601                                         |
| oms        | Onderzoek naar gegevens van persoonslijst        |
| naam       | In uitvoering                                    |
| element    | Persoon.Indicatie.SignaleringMetBetrekkingTotVerstrekkenReisdocument.Identiteit|
----
| dataanv    | 20170601                                         |
| oms        | Onderzoek naar gegevens van persoonslijst        |
| naam       | In uitvoering                                    |
| element    | Persoon.Indicatie.SignaleringMetBetrekkingTotVerstrekkenReisdocument.Standaard|
----
| dataanv    | 20170601                                         |
| oms        | Onderzoek naar gegevens van persoonslijst        |
| naam       | In uitvoering                                    |
| element    | Persoon.Indicatie.Staatloos.Identiteit           |
----
| dataanv    | 20170601                                         |
| oms        | Onderzoek naar gegevens van persoonslijst        |
| naam       | In uitvoering                                    |
| element    | Persoon.Indicatie.Staatloos.Standaard            |
----
| dataanv    | 20170601                                         |
| oms        | Onderzoek naar gegevens van persoonslijst        |
| naam       | In uitvoering                                    |
| element    | Persoon.Indicatie.Standaard                      |
----
| dataanv    | 20170601                                         |
| oms        | Onderzoek naar gegevens van persoonslijst        |
| naam       | In uitvoering                                    |
| element    | Persoon.Indicatie.VastgesteldNietNederlander.Identiteit|
----
| dataanv    | 20170601                                         |
| oms        | Onderzoek naar gegevens van persoonslijst        |
| naam       | In uitvoering                                    |
| element    | Persoon.Indicatie.VastgesteldNietNederlander.Standaard|
----
| dataanv    | 20170601                                         |
| oms        | Onderzoek naar gegevens van persoonslijst        |
| naam       | In uitvoering                                    |
| element    | Persoon.Indicatie.VolledigeVerstrekkingsbeperking.Identiteit|
----
| dataanv    | 20170601                                         |
| oms        | Onderzoek naar gegevens van persoonslijst        |
| naam       | In uitvoering                                    |
| element    | Persoon.Indicatie.VolledigeVerstrekkingsbeperking.Standaard|
----
| dataanv    | 20170601                                         |
| oms        | Onderzoek naar gegevens van persoonslijst        |
| naam       | In uitvoering                                    |
| element    | Persoon.Inschrijving                             |
----
| dataanv    | 20170601                                         |
| oms        | Onderzoek naar gegevens van persoonslijst        |
| naam       | In uitvoering                                    |
| element    | Persoon.Kind.Identiteit                          |
----
| dataanv    | 20170601                                         |
| oms        | Onderzoek naar gegevens van persoonslijst        |
| naam       | In uitvoering                                    |
| element    | Persoon.Migratie                                 |
----
| dataanv    | 20170601                                         |
| oms        | Onderzoek naar gegevens van persoonslijst        |
| naam       | In uitvoering                                    |
| element    | Persoon.Naamgebruik                              |
----
| dataanv    | 20170601                                         |
| oms        | Onderzoek naar gegevens van persoonslijst        |
| naam       | In uitvoering                                    |
| element    | Persoon.Nationaliteit.Identiteit                 |
----
| dataanv    | 20170601                                         |
| oms        | Onderzoek naar gegevens van persoonslijst        |
| naam       | In uitvoering                                    |
| element    | Persoon.Nationaliteit.Standaard                  |
----
| dataanv    | 20170601                                         |
| oms        | Onderzoek naar gegevens van persoonslijst        |
| naam       | In uitvoering                                    |
| element    | Persoon.Nummerverwijzing                         |
----
| dataanv    | 20170601                                         |
| oms        | Onderzoek naar gegevens van persoonslijst        |
| naam       | In uitvoering                                    |
| element    | Persoon.Ouder.Identiteit                         |
----
| dataanv    | 20170601                                         |
| oms        | Onderzoek naar gegevens van persoonslijst        |
| naam       | In uitvoering                                    |
| element    | Persoon.Ouder.Ouderschap                         |
----
| dataanv    | 20170601                                         |
| oms        | Onderzoek naar gegevens van persoonslijst        |
| naam       | In uitvoering                                    |
| element    | Persoon.Overlijden                               |
----
| dataanv    | 20170601                                         |
| oms        | Onderzoek naar gegevens van persoonslijst        |
| naam       | In uitvoering                                    |
| element    | Persoon.Partner.Identiteit                       |
----
| dataanv    | 20170601                                         |
| oms        | Onderzoek naar gegevens van persoonslijst        |
| naam       | In uitvoering                                    |
| element    | Persoon.Persoonskaart                            |
----
| dataanv    | 20170601                                         |
| oms        | Onderzoek naar gegevens van persoonslijst        |
| naam       | In uitvoering                                    |
| element    | Persoon.Reisdocument.Identiteit                  |
----
| dataanv    | 20170601                                         |
| oms        | Onderzoek naar gegevens van persoonslijst        |
| naam       | In uitvoering                                    |
| element    | Persoon.Reisdocument.Standaard                   |
----
| dataanv    | 20170601                                         |
| oms        | Onderzoek naar gegevens van persoonslijst        |
| naam       | In uitvoering                                    |
| element    | Persoon.SamengesteldeNaam                        |
----
| dataanv    | 20170601                                         |
| oms        | Onderzoek naar gegevens van persoonslijst        |
| naam       | In uitvoering                                    |
| element    | Persoon.UitsluitingKiesrecht                     |
----
| dataanv    | 20170601                                         |
| oms        | Onderzoek naar gegevens van persoonslijst        |
| naam       | In uitvoering                                    |
| element    | Persoon.Verblijfsrecht                           |
----
| dataanv    | 20170601                                         |
| oms        | Onderzoek naar gegevens van persoonslijst        |
| naam       | In uitvoering                                    |
| element    | Persoon.Verificatie.Identiteit                   |
----
| dataanv    | 20170601                                         |
| oms        | Onderzoek naar gegevens van persoonslijst        |
| naam       | In uitvoering                                    |
| element    | Persoon.Verificatie.Standaard                    |
----
| dataanv    | 20170601                                         |
| oms        | Onderzoek naar gegevens van persoonslijst        |
| naam       | In uitvoering                                    |
| element    | Persoon.Versie                                   |
----
| dataanv    | 20170601                                         |
| oms        | Onderzoek naar gegevens van persoonslijst        |
| naam       | In uitvoering                                    |
| element    | Persoon.Verstrekkingsbeperking.Identiteit        |
----
| dataanv    | 20170601                                         |
| oms        | Onderzoek naar gegevens van persoonslijst        |
| naam       | In uitvoering                                    |
| element    | Persoon.Voornaam.Identiteit                      |
----
| dataanv    | 20170601                                         |
| oms        | Onderzoek naar gegevens van persoonslijst        |
| naam       | In uitvoering                                    |
| element    | Persoon.Voornaam.Standaard                       |
