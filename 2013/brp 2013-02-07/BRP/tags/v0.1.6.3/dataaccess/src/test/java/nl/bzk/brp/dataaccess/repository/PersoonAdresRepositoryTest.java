/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nl.bzk.brp.dataaccess.AbstractRepositoryTestCase;
import nl.bzk.brp.dataaccess.repository.historie.PersoonAdresMdlHistorieRepository;
import nl.bzk.brp.model.attribuuttype.Adresregel;
import nl.bzk.brp.model.attribuuttype.Adresseerbaarobject;
import nl.bzk.brp.model.attribuuttype.AfgekorteNaamOpenbareRuimte;
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.DatumTijd;
import nl.bzk.brp.model.attribuuttype.GemeenteCode;
import nl.bzk.brp.model.attribuuttype.Gemeentedeel;
import nl.bzk.brp.model.attribuuttype.Huisletter;
import nl.bzk.brp.model.attribuuttype.Huisnummer;
import nl.bzk.brp.model.attribuuttype.Huisnummertoevoeging;
import nl.bzk.brp.model.attribuuttype.IdentificatiecodeNummerAanduiding;
import nl.bzk.brp.model.attribuuttype.LandCode;
import nl.bzk.brp.model.attribuuttype.LocatieOmschrijving;
import nl.bzk.brp.model.attribuuttype.LocatieTovAdres;
import nl.bzk.brp.model.attribuuttype.NaamOpenbareRuimte;
import nl.bzk.brp.model.attribuuttype.PlaatsCode;
import nl.bzk.brp.model.attribuuttype.Postcode;
import nl.bzk.brp.model.attribuuttype.RedenWijzigingAdresCode;
import nl.bzk.brp.model.groep.bericht.PersoonAdresStandaardGroepBericht;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonAdresHisModel;
import nl.bzk.brp.model.objecttype.bericht.ActieBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonAdresBericht;
import nl.bzk.brp.model.objecttype.operationeel.ActieModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonAdresModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.AangeverAdreshouding;
import nl.bzk.brp.model.objecttype.operationeel.statisch.AangeverAdreshoudingIdentiteit;
import nl.bzk.brp.model.objecttype.operationeel.statisch.AdellijkeTitel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.FunctieAdres;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Land;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Nationaliteit;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Partij;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Plaats;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Predikaat;
import nl.bzk.brp.model.objecttype.operationeel.statisch.RedenWijzigingAdres;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortActie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.StatusHistorie;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;


public class PersoonAdresRepositoryTest extends AbstractRepositoryTestCase {

    @Inject
    private PersoonAdresMdlRepository         persoonAdresMdlRepository;

    @PersistenceContext
    private EntityManager                     em;

    @Inject
    private PersoonAdresMdlHistorieRepository persoonAdresMdlHistorieRepository;

    @Inject
    private ReferentieDataMdlRepository       referentieDataMdlRepository;

    private final String                      adresHistorieSql =
                                                                   "SELECT adres FROM PersoonAdresHisModel adres WHERE adres.persoonAdres.persoon.id = :persoonId "
                                                                       + "ORDER BY adres.historie.datumAanvangGeldigheid DESC, adres.historie.datumTijdRegistratie DESC";

    @Test
    public void testOpslaanNieuwPersoonAdres() {
        final Datum datumAanvangGeldigheid = new Datum(20120228);
        ActieModel actie = maakActie();

        // Voer de functie uit om de nieuwe adres op te slaan
        PersoonAdresModel adres =
            persoonAdresMdlRepository.opslaanNieuwPersoonAdres(maakNieuwAdres(), datumAanvangGeldigheid, null,
                    new Date());

        persoonAdresMdlHistorieRepository.persisteerHistorie(adres, actie, datumAanvangGeldigheid, null);

        // Haal de adres history op
        @SuppressWarnings("unchecked")
        List<PersoonAdresHisModel> adresHistorie =
            em.createQuery(adresHistorieSql).setParameter("persoonId", 1001L).getResultList();

        // Verwacht wordt dat er 5 items zijn in de history, 3 in de C-laag en 2 in de D-laag. Dit daar er al drie in
        // de database zaten voor mutatie/opslag nieuw adres.
        Assert.assertEquals(5, adresHistorie.size());

        // A-laag
        PersoonAdresModel huidigAdres = adresHistorie.get(0).getPersoonAdres();
        Assert.assertNotNull(huidigAdres);
        // Verwacht wordt dat elke history item gekoppeld is aan het huidige adres in de A-laag
        Assert.assertEquals(huidigAdres, adresHistorie.get(1).getPersoonAdres());
        Assert.assertEquals(huidigAdres, adresHistorie.get(2).getPersoonAdres());
        Assert.assertEquals(huidigAdres, adresHistorie.get(3).getPersoonAdres());
        Assert.assertEquals(huidigAdres, adresHistorie.get(4).getPersoonAdres());

        // Controlleer huidige adres in de A-laag
        controleerHuidigAdresInALaag(huidigAdres);

        // C-laag - Nieuwe adres
        PersoonAdresHisModel nieuwHisAdres = adresHistorie.get(0);
        // Niet ingevulde tijd verval geeft aan dat de history item in laag C zit
        Assert.assertNull(nieuwHisAdres.getDatumTijdVerval());
        Assert.assertEquals(datumAanvangGeldigheid.getWaarde().intValue(), nieuwHisAdres.getDatumAanvangGeldigheid()
                .getWaarde().intValue());
        Assert.assertNull(nieuwHisAdres.getDatumEindeGeldigheid());
        Assert.assertEquals(20120229, nieuwHisAdres.getDatumAanvangAdreshouding().getWaarde().intValue());
        Assert.assertEquals("Damweg", nieuwHisAdres.getNaamOpenbareRuimte().getWaarde());
        Assert.assertEquals("Damwg", nieuwHisAdres.getAfgekorteNaamOpenbareRuimte().getWaarde());
        Assert.assertEquals("2", nieuwHisAdres.getHuisnummer().getWaarde());
        Assert.assertEquals("b", nieuwHisAdres.getHuisletter().getWaarde());
        Assert.assertEquals("III", nieuwHisAdres.getHuisnummertoevoeging().getWaarde());
        Assert.assertEquals("3063NB", nieuwHisAdres.getPostcode().getWaarde());
        Assert.assertEquals("Kijkduin", nieuwHisAdres.getWoonplaats().getNaam().getWaarde());
        Assert.assertEquals("1753", nieuwHisAdres.getAdresseerbaarObject().getWaarde());
        Assert.assertEquals("1815", nieuwHisAdres.getIdentificatiecodeNummeraanduiding().getWaarde());
        Assert.assertEquals("Ambtshalve", nieuwHisAdres.getRedenWijziging().getNaam().getWaarde());
        Assert.assertEquals(AangeverAdreshoudingIdentiteit.GEZAGHOUDER, nieuwHisAdres.getAangeverAdresHouding());
        Assert.assertEquals(nieuwHisAdres.getActieInhoud().getId(), actie.getId());
        Assert.assertNull(nieuwHisAdres.getActieVerval());
        Assert.assertNull(nieuwHisAdres.getActieAanpassingGeldigheid());

        // C-laag - Oude (ingekorte) adres
        PersoonAdresHisModel oudHisAdres = adresHistorie.get(1);
        // Niet ingevulde tijd verval geeft aan dat de history item in laag C zit
        Assert.assertNull(oudHisAdres.getDatumTijdVerval());
        Assert.assertEquals(20120101, oudHisAdres.getDatumAanvangGeldigheid().getWaarde().intValue());
        Assert.assertEquals(20120228, oudHisAdres.getDatumEindeGeldigheid().getWaarde().intValue());
        Assert.assertEquals(20120101, oudHisAdres.getDatumAanvangAdreshouding().getWaarde().intValue());
        Assert.assertEquals("Damstraat", oudHisAdres.getNaamOpenbareRuimte().getWaarde());
        Assert.assertEquals("1", oudHisAdres.getHuisnummer().getWaarde());
        Assert.assertEquals(oudHisAdres.getActieAanpassingGeldigheid().getId(), actie.getId());
        Assert.assertNull(oudHisAdres.getActieVerval());

        // D-laag - Historie van oude adres
        PersoonAdresHisModel oudHisAdresDLaag = adresHistorie.get(2);
        // Ingevulde tijd verval geeft aan dat de history item in laag D zit
        Assert.assertNotNull(oudHisAdresDLaag.getDatumTijdVerval());
        Assert.assertEquals(20120101, oudHisAdresDLaag.getDatumAanvangGeldigheid().getWaarde().intValue());
        Assert.assertNull(oudHisAdresDLaag.getDatumEindeGeldigheid());
        Assert.assertEquals(20120101, oudHisAdresDLaag.getDatumAanvangAdreshouding().getWaarde().intValue());
        Assert.assertEquals("Damstraat", oudHisAdresDLaag.getNaamOpenbareRuimte().getWaarde());
        Assert.assertEquals("1", oudHisAdresDLaag.getHuisnummer().getWaarde());
        Assert.assertEquals(oudHisAdresDLaag.getActieVerval().getId(), actie.getId());
    }

    @Test
    public void testOpslaanNieuwPersoonAdresMinimaalAdresObject() {
        PersoonModel persoon = em.find(PersoonModel.class, 1001L);

        PersoonAdresBericht nieuwAdres = new PersoonAdresBericht();
        nieuwAdres.setGegevens(new PersoonAdresStandaardGroepBericht());

        PersoonAdresModel adres = new PersoonAdresModel(nieuwAdres, persoon);

        final Datum datumAanvangGeldigheid = new Datum(20111111);
        final Datum datumEindeGeldigheid = null;

        // Voer de functie uit om de nieuwe adres op te slaan
        persoonAdresMdlRepository.opslaanNieuwPersoonAdres(adres, datumAanvangGeldigheid, datumEindeGeldigheid,
                new Date());
    }

    @Test
    public void testTerugwerkendeKrachtVerhuizing() {
        final Datum datumAanvangGeldigheid = new Datum(20111111);
        final Datum datumEindeGeldigheid = new Datum(20120128);

        ActieModel actie = maakActie();

        // Voer de functie uit om de nieuwe adres op te slaan
        PersoonAdresModel adres =
            persoonAdresMdlRepository.opslaanNieuwPersoonAdres(maakNieuwAdres(), datumAanvangGeldigheid,
                    datumEindeGeldigheid, new Date());

        // Persisteer historie
        persoonAdresMdlHistorieRepository.persisteerHistorie(adres, actie, datumAanvangGeldigheid, datumEindeGeldigheid);

        // Haal de adres history op
        List<PersoonAdresHisModel> adresHistorie =
            em.createQuery(adresHistorieSql, PersoonAdresHisModel.class).setParameter("persoonId", 1001L)
                    .getResultList();
        // Verwacht wordt dat er 6 items zijn in de history, 3 in de C-laag en 3 in de D-laag. Dit daar er al drie in
        // de database zaten voor mutatie/opslag nieuw adres.
        Assert.assertEquals(6, adresHistorie.size());
        // A-laag
        PersoonAdresModel huidigAdres = adresHistorie.get(0).getPersoonAdres();
        Assert.assertNotNull(huidigAdres);
        // Verwacht wordt dat elke history item gekoppeld is aan het huidige adres in de A-laag
        Assert.assertEquals(huidigAdres, adresHistorie.get(1).getPersoonAdres());
        Assert.assertEquals(huidigAdres, adresHistorie.get(2).getPersoonAdres());
        Assert.assertEquals(huidigAdres, adresHistorie.get(3).getPersoonAdres());
        Assert.assertEquals(huidigAdres, adresHistorie.get(4).getPersoonAdres());
        Assert.assertEquals(huidigAdres, adresHistorie.get(5).getPersoonAdres());
        // Controlleer huidige adres in de A-laag - Deze zou niet gewijzigd moeten zijn!
        Assert.assertEquals(20120101, huidigAdres.getGegevens().getDatumAanvangAdreshouding().getWaarde().intValue());
        Assert.assertEquals("Damstraat", huidigAdres.getGegevens().getNaamOpenbareRuimte().getWaarde());
        Assert.assertEquals("Damstr", huidigAdres.getGegevens().getAfgekorteNaamOpenbareRuimte().getWaarde());
        Assert.assertEquals("1", huidigAdres.getGegevens().getHuisnummer().getWaarde());
        Assert.assertEquals("a", huidigAdres.getGegevens().getHuisletter().getWaarde());
        Assert.assertEquals("II", huidigAdres.getGegevens().getHuisnummertoevoeging().getWaarde());
        Assert.assertEquals("3984NX", huidigAdres.getGegevens().getPostcode().getWaarde());
        Assert.assertEquals("1492", huidigAdres.getGegevens().getAdresseerbaarObject().getWaarde());
        Assert.assertEquals("1581", huidigAdres.getGegevens().getIdentificatiecodeNummeraanduiding().getWaarde());

        // TODO Tim: Hier moeten nog tests op inhoud, want dit gaat nog niet helemaal goed.
    }

    @Test
    public void testIsIemandIngeschrevenOpAdresNietIngeschreven() {
        Assert.assertFalse(persoonAdresMdlRepository.isIemandIngeschrevenOpAdres(maakNieuwAdres()));
    }

    private PersoonAdresModel creerTestAdres() {
        PersoonAdresBericht adres = new PersoonAdresBericht();
        PersoonAdresStandaardGroepBericht paGegevens = new PersoonAdresStandaardGroepBericht();
        ReflectionTestUtils.setField(adres, "gegevens", paGegevens);

        ReflectionTestUtils.setField(paGegevens, "soort", FunctieAdres.WOONADRES);
        ReflectionTestUtils.setField(paGegevens, "datumAanvangAdreshouding", new Datum(20120229));
        ReflectionTestUtils.setField(paGegevens, "naamOpenbareRuimte", new NaamOpenbareRuimte("Damstraat"));
        ReflectionTestUtils.setField(paGegevens, "afgekorteNaamOpenbareRuimte", new AfgekorteNaamOpenbareRuimte(
                "Damstr"));
        ReflectionTestUtils.setField(paGegevens, "huisnummer", new Huisnummer("1"));
        ReflectionTestUtils.setField(paGegevens, "huisletter", new Huisletter("a"));
        ReflectionTestUtils.setField(paGegevens, "huisnummertoevoeging", new Huisnummertoevoeging("II"));
        ReflectionTestUtils.setField(paGegevens, "postcode", new Postcode("3984NX"));
        ReflectionTestUtils.setField(paGegevens, "woonplaats",
                referentieDataMdlRepository.vindWoonplaatsOpCode(new PlaatsCode("0034")));
        ReflectionTestUtils.setField(paGegevens, "adresseerbaarObject", new Adresseerbaarobject("1492"));
        ReflectionTestUtils.setField(paGegevens, "identificatiecodeNummeraanduiding",
                new IdentificatiecodeNummerAanduiding("1581"));
        ReflectionTestUtils.setField(paGegevens, "land",
                referentieDataMdlRepository.vindLandOpCode(new LandCode("6030")));

        ReflectionTestUtils.setField(paGegevens, "gemeente",
                referentieDataMdlRepository.vindGemeenteOpCode(new GemeenteCode("0034")));
        PersoonAdresModel persoonAdresMdl = new PersoonAdresModel(adres, null);
        ReflectionTestUtils.setField(persoonAdresMdl, "statusHistorie", StatusHistorie.A);
        return persoonAdresMdl;
    }

    private PersoonAdresModel maakNieuwAdres2(final PersoonModel persoonMdl) {
        PersoonAdresBericht adres = new PersoonAdresBericht();
        PersoonAdresStandaardGroepBericht paGegevens = new PersoonAdresStandaardGroepBericht();
        ReflectionTestUtils.setField(adres, "gegevens", paGegevens);
        ReflectionTestUtils.setField(paGegevens, "soort", FunctieAdres.WOONADRES);

        ReflectionTestUtils.setField(paGegevens, "soort", FunctieAdres.WOONADRES);
        ReflectionTestUtils.setField(paGegevens, "datumAanvangAdreshouding", new Datum(20120229));
        ReflectionTestUtils.setField(paGegevens, "naamOpenbareRuimte", new NaamOpenbareRuimte("Damweg"));
        ReflectionTestUtils.setField(paGegevens, "afgekorteNaamOpenbareRuimte",
                new AfgekorteNaamOpenbareRuimte("Damwg"));
        ReflectionTestUtils.setField(paGegevens, "huisnummer", new Huisnummer("2"));
        ReflectionTestUtils.setField(paGegevens, "huisletter", new Huisletter("b"));
        ReflectionTestUtils.setField(paGegevens, "huisnummertoevoeging", new Huisnummertoevoeging("III"));
        ReflectionTestUtils.setField(paGegevens, "postcode", new Postcode("3063NB"));
        ReflectionTestUtils.setField(paGegevens, "woonplaats",
                referentieDataMdlRepository.vindWoonplaatsOpCode(new PlaatsCode("1352")));
        ReflectionTestUtils.setField(paGegevens, "adresseerbaarObject", new Adresseerbaarobject("1753"));
        ReflectionTestUtils.setField(paGegevens, "identificatiecodeNummeraanduiding",
                new IdentificatiecodeNummerAanduiding("1815"));
        ReflectionTestUtils.setField(paGegevens, "land",
                referentieDataMdlRepository.vindLandOpCode(new LandCode("6030")));

        ReflectionTestUtils.setField(paGegevens, "gemeente",
                referentieDataMdlRepository.vindGemeenteOpCode(new GemeenteCode("0034")));
        ReflectionTestUtils.setField(paGegevens, "redenwijziging",
                referentieDataMdlRepository.vindRedenWijzingAdresOpCode(new RedenWijzigingAdresCode("A")));
        ReflectionTestUtils.setField(paGegevens, "aangeverAdreshouding", AangeverAdreshoudingIdentiteit.GEZAGHOUDER);
        // referentieDataMdlRepository.vindAangeverAdreshoudingOpCode(new AangeverAdreshoudingCode("G")));
        PersoonAdresModel persoonAdresMdl = new PersoonAdresModel(adres, persoonMdl);
        ReflectionTestUtils.setField(persoonAdresMdl, "statusHistorie", StatusHistorie.A);
        return persoonAdresMdl;
    }

    @Test
    public void testIsIemandIngeschrevenOpAdres() {
        PersoonAdresModel adres = creerTestAdres();
        Assert.assertTrue(persoonAdresMdlRepository.isIemandIngeschrevenOpAdres(adres));
    }

    /** Alleen huisnummer ontbreekt voor de rest dezelfde adres gegevens */
    @Test
    public void testIsIemandIngeschrevenOpAdresZonderHuisnummer() {
        PersoonAdresModel adres = creerTestAdres();
        // Geen huisnummer
        ReflectionTestUtils.setField(adres.getGegevens(), "huisnummer", null);

        Assert.assertTrue(persoonAdresMdlRepository.isIemandIngeschrevenOpAdres(adres));
    }

    /** Alleen huisletter ontbreekt voor de rest dezelfde adres gegevens */
    @Test
    public void testIsIemandIngeschrevenOpAdresZonderHuisletter() {
        PersoonAdresModel adres = creerTestAdres();
        // Geen huisletter
        ReflectionTestUtils.setField(adres.getGegevens(), "huisletter", null);

        Assert.assertTrue(persoonAdresMdlRepository.isIemandIngeschrevenOpAdres(adres));
    }

    /** Alleen huisnummertoevoeging ontbreekt voor de rest dezelfde adres gegevens */
    @Test
    public void testIsIemandIngeschrevenOpAdresZonderHuisnummerToevoeging() {
        PersoonAdresModel adres = creerTestAdres();
        // zonder huisnummertoevoeging
        ReflectionTestUtils.setField(adres.getGegevens(), "huisnummertoevoeging", null);

        Assert.assertTrue(persoonAdresMdlRepository.isIemandIngeschrevenOpAdres(adres));
    }

    @Test
    public void testIsIemandIngeschrevenOpAdresZonderHuisnummerOntbreektHuisletterVerschillend() {
        PersoonAdresModel adres = creerTestAdres();
        // Geen huisnummer
        ReflectionTestUtils.setField(adres.getGegevens(), "huisnummer", null);
        // Andere huisletter
        ReflectionTestUtils.setField(adres.getGegevens(), "huisletter", new Huisletter("b"));

        Assert.assertFalse(persoonAdresMdlRepository.isIemandIngeschrevenOpAdres(adres));
    }

    @Test
    public void testIsIemandIngeschrevenOpAdresZonderGenesteObjecten() {
        PersoonAdresModel adres = maakNieuwAdres2(em.find(PersoonModel.class, 1001L));
        ReflectionTestUtils.setField(adres.getGegevens(), "gemeente", null);
        ReflectionTestUtils.setField(adres.getGegevens(), "woonplaats", null);
        Assert.assertFalse(persoonAdresMdlRepository.isIemandIngeschrevenOpAdres(adres));
    }

    @Test
    public void testNieuwAdresVoorPersoonZonderAdres() {
        final Datum datumAanvangGeldigheid = new Datum(20120228);
        ActieModel actie = maakActie();
        PersoonAdresModel adres = maakNieuwAdres();
        ReflectionTestUtils.setField(adres, "persoon", em.find(PersoonModel.class, 3L));

        // Voer de functie uit om de nieuwe adres op te slaan
        PersoonAdresModel adres1 =
            persoonAdresMdlRepository.opslaanNieuwPersoonAdres(adres, datumAanvangGeldigheid, null, new Date());

        // Sla historie op
        persoonAdresMdlHistorieRepository.persisteerHistorie(adres1, actie, datumAanvangGeldigheid, null);

        // Haal de adres history op
        @SuppressWarnings("unchecked")
        List<PersoonAdresHisModel> adresHistorie =
            em.createQuery(adresHistorieSql).setParameter("persoonId", 3L).getResultList();
        // Verwacht wordt dat er 1 item is in de history
        Assert.assertEquals(1, adresHistorie.size());
        // A-laag
        PersoonAdresModel huidigAdres = adresHistorie.get(0).getPersoonAdres();
        Assert.assertNotNull(huidigAdres);
        // Controlleer huidige adres in de A-laag
        controleerHuidigAdresInALaag(huidigAdres);
        // C-laag - Nieuwe adres
        PersoonAdresHisModel nieuwHisAdres = adresHistorie.get(0);
        // Niet ingevulde tijd verval geeft aan dat de history item in laag C zit
        Assert.assertNull(nieuwHisAdres.getDatumTijdVerval());
        Assert.assertEquals(datumAanvangGeldigheid.getWaarde(), nieuwHisAdres.getDatumAanvangGeldigheid().getWaarde());
        Assert.assertNull(nieuwHisAdres.getDatumEindeGeldigheid());
        Assert.assertEquals(20120229, nieuwHisAdres.getDatumAanvangAdreshouding().getWaarde().intValue());
        Assert.assertEquals("Damweg", nieuwHisAdres.getNaamOpenbareRuimte().getWaarde());
        Assert.assertEquals("Damwg", nieuwHisAdres.getAfgekorteNaamOpenbareRuimte().getWaarde());
        Assert.assertEquals("2", nieuwHisAdres.getHuisnummer().getWaarde());
        Assert.assertEquals("b", nieuwHisAdres.getHuisletter().getWaarde());
        Assert.assertEquals("III", nieuwHisAdres.getHuisnummertoevoeging().getWaarde());
        Assert.assertEquals("3063NB", nieuwHisAdres.getPostcode().getWaarde());
        Assert.assertEquals("Kijkduin", nieuwHisAdres.getWoonplaats().getNaam().getWaarde());
        Assert.assertEquals("1753", nieuwHisAdres.getAdresseerbaarObject().getWaarde());
        Assert.assertEquals("1815", nieuwHisAdres.getIdentificatiecodeNummeraanduiding().getWaarde());
        Assert.assertEquals(nieuwHisAdres.getActieInhoud().getId(), actie.getId());
    }

    @Test
    public void testVindHuidigAdresVoorPersoon() {
        PersoonAdresModel adres =
            persoonAdresMdlRepository.vindHuidigWoonAdresVoorPersoon(new Burgerservicenummer("123456789"));
        Assert.assertNotNull(adres);
        Assert.assertEquals(1, adres.getId().intValue());
    }

    /**
     * Controleert het huidige adres uit de A-laag.
     *
     * @param huidigAdres het huidige adres uit de A-laag, na uitvoer van de verhuizing.
     */
    private void controleerHuidigAdresInALaag(final PersoonAdresModel huidigAdres) {
        Assert.assertEquals(20120229, huidigAdres.getGegevens().getDatumAanvangAdreshouding().getWaarde().intValue());
        Assert.assertEquals("Damweg", huidigAdres.getGegevens().getNaamOpenbareRuimte().getWaarde());
        Assert.assertEquals("Damwg", huidigAdres.getGegevens().getAfgekorteNaamOpenbareRuimte().getWaarde());
        Assert.assertEquals("2", huidigAdres.getGegevens().getHuisnummer().getWaarde());
        Assert.assertEquals("b", huidigAdres.getGegevens().getHuisletter().getWaarde());
        Assert.assertEquals("III", huidigAdres.getGegevens().getHuisnummertoevoeging().getWaarde());
        Assert.assertEquals("3063NB", huidigAdres.getGegevens().getPostcode().getWaarde());
        Assert.assertEquals("Kijkduin", huidigAdres.getGegevens().getWoonplaats().getNaam().getWaarde());
        Assert.assertEquals("1753", huidigAdres.getGegevens().getAdresseerbaarObject().getWaarde());
        Assert.assertEquals("1815", huidigAdres.getGegevens().getIdentificatiecodeNummeraanduiding().getWaarde());
    }

    private PersoonAdresModel maakNieuwAdres() {
        PersoonModel persoon = em.find(PersoonModel.class, 1001L);

        PersoonAdresStandaardGroepBericht gegevens = new PersoonAdresStandaardGroepBericht();
        gegevens.setDatumAanvangAdreshouding(new Datum(20120229));
        gegevens.setNaamOpenbareRuimte(new NaamOpenbareRuimte("Damweg"));
        gegevens.setAfgekorteNaamOpenbareRuimte(new AfgekorteNaamOpenbareRuimte("Damwg"));
        gegevens.setHuisnummer(new Huisnummer("2"));
        gegevens.setHuisletter(new Huisletter("b"));
        gegevens.setHuisnummertoevoeging(new Huisnummertoevoeging("III"));
        gegevens.setPostcode(new Postcode("3063NB"));
        gegevens.setWoonplaats(em.find(Plaats.class, 1302L));
        gegevens.setAdresseerbaarObject(new Adresseerbaarobject("1753"));
        gegevens.setIdentificatiecodeNummeraanduiding(new IdentificatiecodeNummerAanduiding("1815"));
        gegevens.setLand(em.find(Land.class, 2L));
        gegevens.setGemeente(em.find(Partij.class, 3));
        gegevens.setRedenwijziging(em.find(RedenWijzigingAdres.class, 2));
        gegevens.setAangeverAdreshouding(AangeverAdreshoudingIdentiteit.GEZAGHOUDER);

        PersoonAdresBericht nieuwAdres = new PersoonAdresBericht();
        nieuwAdres.setGegevens(gegevens);

        PersoonAdresModel adres = new PersoonAdresModel(nieuwAdres, persoon);

        return adres;
    }

    // @Test
    // public void testZoekAdresMetVolledigFilter() throws Exception {
    // AdresSelectieFilter filter = new AdresSelectieFilter();
    // filter.setAdresSoorten(FunctieAdres.BRIEFADRES, FunctieAdres.WOONADRES);
    // filter.setHuisletter("h");
    // filter.setHuisnummer("123");
    // filter.setHuisnummertoevoeging("t123");
    // filter.setIdentificatiecodeNummeraanduiding("t123");
    // filter.setNaamOpenbareRuimte("t123");
    // filter.setPeilDatum(464333);
    // filter.setPostcode("Postcode");
    // filter.setWoonplaatscodes("1234", "3445", "2222");
    // persoonAdresRepository.zoekBinnelandseAdressen(filter);
    //
    // }

    private ActieModel maakActie() {
        ActieBericht actieBericht = new ActieBericht();

        actieBericht.setTijdstipRegistratie(new DatumTijd(new Timestamp(System.currentTimeMillis() - 1)));
        actieBericht.setSoort(SoortActie.REGISTRATIE_NATIONALITEIT);
        actieBericht.setPartij(em.find(Partij.class, 4));

        ActieModel actie = new ActieModel(actieBericht);
        em.persist(actie);

        return actie;
    }

    @Test
    public void haalPersoonAdresOp() {
        PersoonAdresModel pa = em.find(PersoonAdresModel.class, 10001L);

        Assert.assertEquals(new Burgerservicenummer("135867277"), pa.getPersoon().getIdentificatieNummers()
                .getBurgerServiceNummer());
        Assert.assertEquals("Hoofd instelling", pa.getGegevens().getAangeverAdresHouding().getNaam());
        Assert.assertEquals("1492", pa.getGegevens().getAdresseerbaarObject().getWaarde());
        Assert.assertEquals("Dorpstr", pa.getGegevens().getAfgekorteNaamOpenbareRuimte().getWaarde());
        Assert.assertEquals("Regel 1", pa.getGegevens().getBuitenlandsAdresRegel1().getWaarde());
        Assert.assertEquals("Regel 2", pa.getGegevens().getBuitenlandsAdresRegel2().getWaarde());
        Assert.assertEquals("Regel 3", pa.getGegevens().getBuitenlandsAdresRegel3().getWaarde());
        Assert.assertEquals("Regel 4", pa.getGegevens().getBuitenlandsAdresRegel4().getWaarde());
        Assert.assertEquals("Regel 5", pa.getGegevens().getBuitenlandsAdresRegel5().getWaarde());
        Assert.assertEquals("Regel 6", pa.getGegevens().getBuitenlandsAdresRegel6().getWaarde());
        Assert.assertEquals(new Integer(20120101), pa.getGegevens().getDatumAanvangAdreshouding().getWaarde());
        Assert.assertEquals(new Integer(12020508), pa.getGegevens().getDatumVertrekUitNederland().getWaarde());
        Assert.assertEquals("Almere", pa.getGegevens().getGemeente().getNaam().getWaarde());
        Assert.assertEquals("GemDeel", pa.getGegevens().getGemeentedeel().getWaarde());
        Assert.assertEquals("a", pa.getGegevens().getHuisletter().getWaarde());
        Assert.assertEquals("512", pa.getGegevens().getHuisnummer().getWaarde());
        Assert.assertEquals("IV", pa.getGegevens().getHuisnummertoevoeging().getWaarde());
        Assert.assertEquals("Nederland", pa.getGegevens().getLand().getNaam().getWaarde());
        Assert.assertEquals("1581", pa.getGegevens().getIdentificatiecodeNummeraanduiding().getWaarde());
        Assert.assertEquals("Omschrijving", pa.getGegevens().getLocatieOmschrijving().getWaarde());
        Assert.assertEquals("ta", pa.getGegevens().getLocatietovAdres().getWaarde());
        Assert.assertEquals("Dorpstraat", pa.getGegevens().getNaamOpenbareRuimte().getWaarde());
        Assert.assertEquals("7812PK", pa.getGegevens().getPostcode().getWaarde());
        Assert.assertEquals("Aangifte door persoon", pa.getGegevens().getRedenWijziging().getNaam().getWaarde());
        Assert.assertEquals("Woonadres", pa.getGegevens().getSoort().getNaam());
        Assert.assertEquals("0034", pa.getGegevens().getWoonplaats().getCode().getWaarde());
        Assert.assertEquals("Almeres", pa.getGegevens().getWoonplaats().getNaam().getWaarde());

        Assert.assertEquals(StatusHistorie.A, pa.getStatusHistorie());
    }

    @Test
    public void slaPersoonAdresOp() {
        PersoonModel persoon = em.find(PersoonModel.class, 1L);
        RedenWijzigingAdres rdnWijzig = em.find(RedenWijzigingAdres.class, 1);
        Land land = em.find(Land.class, 2L);
        Plaats woonplaats = em.find(Plaats.class, 1L);
        Partij gemeente = em.find(Partij.class, 3);

        PersoonAdresBericht pa = new PersoonAdresBericht();
        PersoonAdresStandaardGroepBericht paGegevens = new PersoonAdresStandaardGroepBericht();
        ReflectionTestUtils.setField(pa, "gegevens", paGegevens);

        ReflectionTestUtils.setField(paGegevens, "soort", FunctieAdres.WOONADRES);
        ReflectionTestUtils.setField(paGegevens, "datumAanvangAdreshouding", new Datum(20120802));
        ReflectionTestUtils.setField(paGegevens, "redenwijziging", rdnWijzig);
        ReflectionTestUtils.setField(paGegevens, "gemeente", gemeente);
        ReflectionTestUtils.setField(paGegevens, "woonplaats", woonplaats);
        ReflectionTestUtils.setField(paGegevens, "land", land);
        ReflectionTestUtils
                .setField(paGegevens, "aangeverAdreshouding", AangeverAdreshoudingIdentiteit.HOOFDINSTELLING);
        ReflectionTestUtils.setField(paGegevens, "adresseerbaarObject", new Adresseerbaarobject("AdreerbaarObject x"));
        ReflectionTestUtils.setField(paGegevens, "afgekorteNaamOpenbareRuimte", new AfgekorteNaamOpenbareRuimte(
                "Afgekorte NOR"));
        ReflectionTestUtils.setField(paGegevens, "buitenlandsAdresRegel1", new Adresregel("Regel 1"));
        ReflectionTestUtils.setField(paGegevens, "buitenlandsAdresRegel2", new Adresregel("Regel 2"));
        ReflectionTestUtils.setField(paGegevens, "buitenlandsAdresRegel3", new Adresregel("Regel 3"));
        ReflectionTestUtils.setField(paGegevens, "buitenlandsAdresRegel4", new Adresregel("Regel 4"));
        ReflectionTestUtils.setField(paGegevens, "buitenlandsAdresRegel5", new Adresregel("Regel 5"));
        ReflectionTestUtils.setField(paGegevens, "buitenlandsAdresRegel6", new Adresregel("Regel 6"));

        ReflectionTestUtils.setField(paGegevens, "datumAanvangAdreshouding", new Datum(20120101));
        ReflectionTestUtils.setField(paGegevens, "datumVertrekUitNederland", new Datum(12020508));

        ReflectionTestUtils.setField(paGegevens, "gemeentedeel", new Gemeentedeel("GemDeel"));
        ReflectionTestUtils.setField(paGegevens, "huisletter", new Huisletter("a"));
        ReflectionTestUtils.setField(paGegevens, "huisnummer", new Huisnummer("512"));
        ReflectionTestUtils.setField(paGegevens, "huisnummertoevoeging", new Huisnummertoevoeging("IV"));
        ReflectionTestUtils.setField(paGegevens, "identificatiecodeNummeraanduiding",
                new IdentificatiecodeNummerAanduiding("1581"));
        ReflectionTestUtils.setField(paGegevens, "locatieOmschrijving", new LocatieOmschrijving("Omschrijving"));
        ReflectionTestUtils.setField(paGegevens, "locatietovAdres", new LocatieTovAdres("ta"));
        ReflectionTestUtils.setField(paGegevens, "naamOpenbareRuimte", new NaamOpenbareRuimte("Dorpstraat"));
        ReflectionTestUtils.setField(paGegevens, "postcode", new Postcode("7812PK"));

        PersoonAdresModel persoonAdresMdl = new PersoonAdresModel(pa, persoon);
        ReflectionTestUtils.setField(persoonAdresMdl, "statusHistorie", StatusHistorie.A);

        em.persist(persoonAdresMdl);
        Assert.assertTrue("Fout gegaan met wegschrijven, null Id", (persoonAdresMdl.getId() != null));
        persoonAdresMdl = em.find(PersoonAdresModel.class, persoonAdresMdl.getId());
        Assert.assertEquals("Hoofd instelling", pa.getGegevens().getAangeverAdresHouding().getNaam());
        Assert.assertEquals("AdreerbaarObject x", pa.getGegevens().getAdresseerbaarObject().getWaarde());
        Assert.assertEquals("Afgekorte NOR", pa.getGegevens().getAfgekorteNaamOpenbareRuimte().getWaarde());
        Assert.assertEquals("Regel 1", pa.getGegevens().getBuitenlandsAdresRegel1().getWaarde());
        Assert.assertEquals("Regel 2", pa.getGegevens().getBuitenlandsAdresRegel2().getWaarde());
        Assert.assertEquals("Regel 3", pa.getGegevens().getBuitenlandsAdresRegel3().getWaarde());
        Assert.assertEquals("Regel 4", pa.getGegevens().getBuitenlandsAdresRegel4().getWaarde());
        Assert.assertEquals("Regel 5", pa.getGegevens().getBuitenlandsAdresRegel5().getWaarde());
        Assert.assertEquals("Regel 6", pa.getGegevens().getBuitenlandsAdresRegel6().getWaarde());
        Assert.assertEquals(new Integer(20120101), pa.getGegevens().getDatumAanvangAdreshouding().getWaarde());
        Assert.assertEquals(new Integer(12020508), pa.getGegevens().getDatumVertrekUitNederland().getWaarde());
        Assert.assertEquals("Almere", pa.getGegevens().getGemeente().getNaam().getWaarde());
        Assert.assertEquals("GemDeel", pa.getGegevens().getGemeentedeel().getWaarde());
        Assert.assertEquals("a", pa.getGegevens().getHuisletter().getWaarde());
        Assert.assertEquals("512", pa.getGegevens().getHuisnummer().getWaarde());
        Assert.assertEquals("IV", pa.getGegevens().getHuisnummertoevoeging().getWaarde());
        Assert.assertEquals("Nederland", pa.getGegevens().getLand().getNaam().getWaarde());
        Assert.assertEquals("1581", pa.getGegevens().getIdentificatiecodeNummeraanduiding().getWaarde());
        Assert.assertEquals("Omschrijving", pa.getGegevens().getLocatieOmschrijving().getWaarde());
        Assert.assertEquals("ta", pa.getGegevens().getLocatietovAdres().getWaarde());
        Assert.assertEquals("Dorpstraat", pa.getGegevens().getNaamOpenbareRuimte().getWaarde());
        Assert.assertEquals("7812PK", pa.getGegevens().getPostcode().getWaarde());
        Assert.assertEquals("Aangifte door persoon", pa.getGegevens().getRedenWijziging().getNaam().getWaarde());
        Assert.assertEquals("Woonadres", pa.getGegevens().getSoort().getNaam());
        Assert.assertEquals("0034", pa.getGegevens().getWoonplaats().getCode().getWaarde());
        Assert.assertEquals("Almeres", pa.getGegevens().getWoonplaats().getNaam().getWaarde());

        Assert.assertEquals("Actueel voorkomend", persoonAdresMdl.getStatusHistorie().getNaam());
    }

    @Test
    public void haalStatischeObjectenOp() {
        Land land = em.find(Land.class, 4L);
        Assert.assertEquals("Frankrijk", land.getNaam().getWaarde());
        AdellijkeTitel adellijkeTitel = em.find(AdellijkeTitel.class, 1);
        Assert.assertEquals("B", adellijkeTitel.getAdellijkeTitelCode().getWaarde());
        AangeverAdreshouding aangeverAdreshouding = em.find(AangeverAdreshouding.class, 1);
        Assert.assertEquals("Gezaghouder", aangeverAdreshouding.getNaam().getWaarde());
        Nationaliteit nationaliteit = em.find(Nationaliteit.class, 1L);
        Assert.assertEquals("Onbekend", nationaliteit.getNaam().getWaarde());
        Partij party = em.find(Partij.class, 1);
        Assert.assertEquals("Regering en Staten-Generaal", party.getNaam().getWaarde());
        Plaats plaats = em.find(Plaats.class, 1L);
        Assert.assertEquals("Almeres", plaats.getNaam().getWaarde());
        Predikaat predikaat = em.find(Predikaat.class, 1);
        Assert.assertEquals("K", predikaat.getCode().getWaarde());
        //
        // RedenBeeindigingRelatie redenBeeindigingRelatie = em.find(RedenBeeindigingRelatie.class, 3);
        // System.out.println(redenBeeindigingRelatie + "," + redenBeeindigingRelatie.getCode());
        RedenWijzigingAdres redenWijzigingAdres = em.find(RedenWijzigingAdres.class, 1);
        Assert.assertEquals("Aangifte door persoon", redenWijzigingAdres.getNaam().getWaarde());
    }

    @Test
    public void testVindHuidigWoonAdresVoorPersoon() {
        PersoonAdresModel adres =
            persoonAdresMdlRepository.vindHuidigWoonAdresVoorPersoon(new Burgerservicenummer("123456789"));
        Assert.assertNotNull(adres);
        Assert.assertEquals(1, adres.getId().longValue());
    }

}
