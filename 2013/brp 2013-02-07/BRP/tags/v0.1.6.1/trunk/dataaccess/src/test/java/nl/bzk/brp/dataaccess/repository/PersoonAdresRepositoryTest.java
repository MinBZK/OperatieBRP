/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nl.bzk.brp.dataaccess.AbstractRepositoryTestCase;
import nl.bzk.brp.dataaccess.exceptie.OnbekendeReferentieExceptie;
import nl.bzk.brp.dataaccess.repository.historie.PersoonAdresHistorieRepository;
import nl.bzk.brp.model.attribuuttype.Adresregel;
import nl.bzk.brp.model.attribuuttype.Adresseerbaarobject;
import nl.bzk.brp.model.attribuuttype.AfgekorteNaamOpenbareRuimte;
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.attribuuttype.Datum;
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
import nl.bzk.brp.model.groep.impl.usr.PersoonAdresStandaardGroepMdl;
import nl.bzk.brp.model.objecttype.impl.usr.PersoonAdresMdl;
import nl.bzk.brp.model.objecttype.impl.usr.PersoonMdl;
import nl.bzk.brp.model.objecttype.interfaces.usr.PersoonAdres;
import nl.bzk.brp.model.objecttype.statisch.AangeverAdreshouding;
import nl.bzk.brp.model.objecttype.statisch.AangeverAdreshoudingIdentiteit;
import nl.bzk.brp.model.objecttype.statisch.AdellijkeTitel;
import nl.bzk.brp.model.objecttype.statisch.FunctieAdres;
import nl.bzk.brp.model.objecttype.statisch.Land;
import nl.bzk.brp.model.objecttype.statisch.Nationaliteit;
import nl.bzk.brp.model.objecttype.statisch.Partij;
import nl.bzk.brp.model.objecttype.statisch.Plaats;
import nl.bzk.brp.model.objecttype.statisch.Predikaat;
import nl.bzk.brp.model.objecttype.statisch.RedenWijzigingAdres;
import nl.bzk.brp.model.objecttype.statisch.StatusHistorie;
import nl.bzk.brp.model.operationeel.kern.HisPersoonAdres;
import nl.bzk.brp.model.operationeel.kern.PersistentActie;
import nl.bzk.brp.model.operationeel.kern.PersistentPersoonAdres;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;


public class PersoonAdresRepositoryTest extends AbstractRepositoryTestCase {

    @Inject
    private PersoonAdresRepository persoonAdresRepository;

    @Inject
    private PersoonAdresMdlRepository persoonAdresMdlRepository;

    @PersistenceContext
    private EntityManager          em;

    @Inject
    private PersoonAdresHistorieRepository persoonAdresHistorieRepository;

    @Inject
    private PartijRepository partijRepository;

    @Inject
    private ReferentieDataMdlRepository referentieDataMdlRepository;

    @Test
    public void testOpslaanNieuwPersoonAdres() {
        final int datumAanvangGeldigheid = 20120228;
        PersistentActie actie = maakActie();

        // Voer de functie uit om de nieuwe adres op te slaan
        PersistentPersoonAdres adres = persoonAdresRepository.opslaanNieuwPersoonAdres(
                maakNieuwAdres(), datumAanvangGeldigheid, null, new Date());
        persoonAdresHistorieRepository.opslaanHistorie(adres, actie, datumAanvangGeldigheid, null);

        // Haal de adres history op
        final String adresHistorieSql =
            "SELECT adres FROM HisPersoonAdres adres " + "WHERE adres.persoonAdres.persoon.id = :persoonId "
                + "ORDER BY adres.datumAanvangGeldigheid DESC, adres.datumTijdRegistratie DESC";
        @SuppressWarnings("unchecked")
        List<HisPersoonAdres> adresHistorie =
            em.createQuery(adresHistorieSql).setParameter("persoonId", 1001L).getResultList();

        // Verwacht wordt dat er 5 items zijn in de history, 3 in de C-laag en 2 in de D-laag. Dit daar er al drie in
        // de database zaten voor mutatie/opslag nieuw adres.
        Assert.assertEquals(5, adresHistorie.size());

        // A-laag
        PersistentPersoonAdres huidigAdres = adresHistorie.get(0).getPersoonAdres();
        Assert.assertNotNull(huidigAdres);
        // Verwacht wordt dat elke history item gekoppeld is aan het huidige adres in de A-laag
        Assert.assertEquals(huidigAdres, adresHistorie.get(1).getPersoonAdres());
        Assert.assertEquals(huidigAdres, adresHistorie.get(2).getPersoonAdres());
        Assert.assertEquals(huidigAdres, adresHistorie.get(3).getPersoonAdres());
        Assert.assertEquals(huidigAdres, adresHistorie.get(4).getPersoonAdres());

        // Controlleer huidige adres in de A-laag
        controleerHuidigAdresInALaag(huidigAdres);

        // C-laag - Nieuwe adres
        HisPersoonAdres nieuwHisAdres = adresHistorie.get(0);
        // Niet ingevulde tijd verval geeft aan dat de history item in laag C zit
        Assert.assertNull(nieuwHisAdres.getDatumTijdVerval());
        Assert.assertEquals(datumAanvangGeldigheid, nieuwHisAdres.getDatumAanvangGeldigheid().intValue());
        Assert.assertNull(nieuwHisAdres.getDatumEindeGeldigheid());
        Assert.assertEquals(20120229, nieuwHisAdres.getDatumAanvangAdreshouding().intValue());
        Assert.assertEquals("Damweg", nieuwHisAdres.getNaamOpenbareRuimte());
        Assert.assertEquals("Damwg", nieuwHisAdres.getAfgekorteNaamOpenbareRuimte());
        Assert.assertEquals("2", nieuwHisAdres.getHuisnummer());
        Assert.assertEquals("b", nieuwHisAdres.getHuisletter());
        Assert.assertEquals("III", nieuwHisAdres.getHuisnummertoevoeging());
        Assert.assertEquals("3063NB", nieuwHisAdres.getPostcode());
        Assert.assertEquals("Kijkduin", nieuwHisAdres.getWoonplaats().getNaam());
        Assert.assertEquals("1753", nieuwHisAdres.getAdresseerbaarObject());
        Assert.assertEquals("1815", nieuwHisAdres.getIdentificatiecodeNummeraanduiding());
        Assert.assertEquals(nl.bzk.brp.model.gedeeld.RedenWijzigingAdres.AMBTSHALVE, nieuwHisAdres.getRedenWijzigingAdres());
        Assert.assertEquals(nl.bzk.brp.model.gedeeld.AangeverAdreshoudingIdentiteit.GEZAGHOUDER, nieuwHisAdres.getAangeverAdreshouding());
        Assert.assertEquals(nieuwHisAdres.getActieInhoud().getId(), actie.getId());
        Assert.assertNull(nieuwHisAdres.getActieVerval());
        Assert.assertNull(nieuwHisAdres.getActieAanpassingGeldigheid());

        // C-laag - Oude (ingekorte) adres
        HisPersoonAdres oudHisAdres = adresHistorie.get(1);
        // Niet ingevulde tijd verval geeft aan dat de history item in laag C zit
        Assert.assertNull(oudHisAdres.getDatumTijdVerval());
        Assert.assertEquals(20120101, oudHisAdres.getDatumAanvangGeldigheid().intValue());
        Assert.assertEquals(20120228, oudHisAdres.getDatumEindeGeldigheid().intValue());
        Assert.assertEquals(20120101, oudHisAdres.getDatumAanvangAdreshouding().intValue());
        Assert.assertEquals("Damstraat", oudHisAdres.getNaamOpenbareRuimte());
        Assert.assertEquals("1", oudHisAdres.getHuisnummer());
        Assert.assertEquals(oudHisAdres.getActieAanpassingGeldigheid().getId(), actie.getId());
        Assert.assertNull(oudHisAdres.getActieVerval());

        // D-laag - Historie van oude adres
        HisPersoonAdres oudHisAdresDLaag = adresHistorie.get(2);
        // Ingevulde tijd verval geeft aan dat de history item in laag D zit
        Assert.assertNotNull(oudHisAdresDLaag.getDatumTijdVerval());
        Assert.assertEquals(20120101, oudHisAdresDLaag.getDatumAanvangGeldigheid().intValue());
        Assert.assertNull(oudHisAdresDLaag.getDatumEindeGeldigheid());
        Assert.assertEquals(20120101, oudHisAdresDLaag.getDatumAanvangAdreshouding().intValue());
        Assert.assertEquals("Damstraat", oudHisAdresDLaag.getNaamOpenbareRuimte());
        Assert.assertEquals("1", oudHisAdresDLaag.getHuisnummer());
        Assert.assertEquals(oudHisAdresDLaag.getActieVerval().getId(), actie.getId());
    }

    @Test
    public void testOpslaanNieuwPersoonAdresMinimaalAdresObject() {
        nl.bzk.brp.model.logisch.PersoonAdres adres = new nl.bzk.brp.model.logisch.PersoonAdres();
        nl.bzk.brp.model.logisch.Persoon persoon = new nl.bzk.brp.model.logisch.Persoon();
        nl.bzk.brp.model.logisch.groep.PersoonIdentificatienummers persoonIdentificatienummers = new nl.bzk.brp.model.logisch.groep.PersoonIdentificatienummers();
        persoonIdentificatienummers.setBurgerservicenummer("100001001");
        persoon.setIdentificatienummers(persoonIdentificatienummers);
        adres.setPersoon(persoon);

        final int datumAanvangGeldigheid = 20111111;
        final Integer datumEindeGeldigheid = null;

        // Voer de functie uit om de nieuwe adres op te slaan
        persoonAdresRepository.opslaanNieuwPersoonAdres(adres, datumAanvangGeldigheid, datumEindeGeldigheid,
            new Date());
    }


    @Test
    public void testTerugwerkendeKrachtVerhuizing() {
        final int datumAanvangGeldigheid = 20111111;
        final int datumEindeGeldigheid = 20120128;

        PersistentActie actie = maakActie();

        // Voer de functie uit om de nieuwe adres op te slaan
        PersistentPersoonAdres adres = persoonAdresRepository.opslaanNieuwPersoonAdres(
                maakNieuwAdres(), datumAanvangGeldigheid, datumEindeGeldigheid, new Date());

        //Persisteer historie
        persoonAdresHistorieRepository.opslaanHistorie(adres, actie, datumAanvangGeldigheid, datumEindeGeldigheid);

        // Haal de adres history op
        final String adresHistorieSql =
            "SELECT adres FROM HisPersoonAdres adres " + "WHERE adres.persoonAdres.persoon.id = :persoonId "
                + "ORDER BY adres.datumAanvangGeldigheid DESC, adres.datumTijdRegistratie DESC";
        List<HisPersoonAdres> adresHistorie =
            em.createQuery(adresHistorieSql, HisPersoonAdres.class).setParameter("persoonId", 1001L).getResultList();
        // Verwacht wordt dat er 6 items zijn in de history, 3 in de C-laag en 3 in de D-laag. Dit daar er al drie in
        // de database zaten voor mutatie/opslag nieuw adres.
        Assert.assertEquals(6, adresHistorie.size());
        // A-laag
        PersistentPersoonAdres huidigAdres = adresHistorie.get(0).getPersoonAdres();
        Assert.assertNotNull(huidigAdres);
        // Verwacht wordt dat elke history item gekoppeld is aan het huidige adres in de A-laag
        Assert.assertEquals(huidigAdres, adresHistorie.get(1).getPersoonAdres());
        Assert.assertEquals(huidigAdres, adresHistorie.get(2).getPersoonAdres());
        Assert.assertEquals(huidigAdres, adresHistorie.get(3).getPersoonAdres());
        Assert.assertEquals(huidigAdres, adresHistorie.get(4).getPersoonAdres());
        Assert.assertEquals(huidigAdres, adresHistorie.get(5).getPersoonAdres());
        // Controlleer huidige adres in de A-laag - Deze zou niet gewijzigd moeten zijn!
        Assert.assertEquals(20120101, huidigAdres.getDatumAanvangAdreshouding().intValue());
        Assert.assertEquals("Damstraat", huidigAdres.getNaamOpenbareRuimte());
        Assert.assertEquals("Damstr", huidigAdres.getAfgekorteNaamOpenbareRuimte());
        Assert.assertEquals("1", huidigAdres.getHuisnummer());
        Assert.assertEquals("a", huidigAdres.getHuisletter());
        Assert.assertEquals("II", huidigAdres.getHuisnummertoevoeging());
        Assert.assertEquals("3984NX", huidigAdres.getPostcode());
        Assert.assertEquals("1492", huidigAdres.getAdresseerbaarObject());
        Assert.assertEquals("1581", huidigAdres.getIdentificatiecodeNummeraanduiding());

        // TODO Tim: Hier moeten nog tests op inhoud, want dit gaat nog niet helemaal goed.
    }

    @Test(expected = OnbekendeReferentieExceptie.class)
    public void testOpslaanNieuwPersoonAdresMetNietBestaandeGemeenteCode() {
        final int datumAanvangGeldigheid = 20120228;
        nl.bzk.brp.model.logisch.PersoonAdres adres = maakNieuwAdres();
        adres.getGemeente().setGemeentecode("000");
        persoonAdresRepository.opslaanNieuwPersoonAdres(adres, datumAanvangGeldigheid, null, new Date());
    }

    @Test(expected = OnbekendeReferentieExceptie.class)
    public void testOpslaanNieuwPersoonAdresMetNietBestaandePlaatsCode() {
        final int datumAanvangGeldigheid = 20120228;
        nl.bzk.brp.model.logisch.PersoonAdres adres = maakNieuwAdres();
        adres.getWoonplaats().setWoonplaatscode("000");
        persoonAdresRepository.opslaanNieuwPersoonAdres(adres, datumAanvangGeldigheid, null, new Date());
    }

    @Test(expected = OnbekendeReferentieExceptie.class)
    public void testOpslaanNieuwPersoonAdresMetNietBestaandeLandCode() {
        final int datumAanvangGeldigheid = 20120228;
        nl.bzk.brp.model.logisch.PersoonAdres adres = maakNieuwAdres();
        adres.getLand().setLandcode("000");
        persoonAdresRepository.opslaanNieuwPersoonAdres(adres, datumAanvangGeldigheid, null, new Date());
    }

    @Test
    public void testIsIemandIngeschrevenOpAdresNietIngeschreven() {
        Assert.assertFalse(persoonAdresRepository.isIemandIngeschrevenOpAdres(maakNieuwAdres()));
    }

    private PersoonAdresMdl creerTestAdres() {
        PersoonAdresMdl adres = new PersoonAdresMdl();
        PersoonAdresStandaardGroepMdl paGegevens = new PersoonAdresStandaardGroepMdl();
        ReflectionTestUtils.setField(adres, "gegevens", paGegevens);

        ReflectionTestUtils.setField(paGegevens, "soort", FunctieAdres.WOONADRES);
        ReflectionTestUtils.setField(paGegevens, "datumAanvangAdreshouding", new Datum(20120229));
        ReflectionTestUtils.setField(paGegevens, "naamOpenbareRuimte", new NaamOpenbareRuimte("Damstraat"));
        ReflectionTestUtils.setField(paGegevens, "afgekorteNaamOpenbareRuimte", new AfgekorteNaamOpenbareRuimte("Damstr"));
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
        ReflectionTestUtils.setField(paGegevens, "statusHistorie", StatusHistorie.A);
        ReflectionTestUtils.setField(paGegevens, "gemeente",
                referentieDataMdlRepository.vindGemeenteOpCode(new GemeenteCode("0034")));
        return adres;
    }

    private PersoonAdresMdl maakNieuwAdres2() {
        PersoonAdresMdl adres = new PersoonAdresMdl();
        PersoonAdresStandaardGroepMdl paGegevens = new PersoonAdresStandaardGroepMdl();
        ReflectionTestUtils.setField(adres, "gegevens", paGegevens);
        ReflectionTestUtils.setField(adres, "persoon", em.find(PersoonMdl.class, 1001L));
        ReflectionTestUtils.setField(paGegevens, "soort", FunctieAdres.WOONADRES);

        ReflectionTestUtils.setField(paGegevens, "soort", FunctieAdres.WOONADRES);
        ReflectionTestUtils.setField(paGegevens, "datumAanvangAdreshouding", new Datum(20120229));
        ReflectionTestUtils.setField(paGegevens, "naamOpenbareRuimte", new NaamOpenbareRuimte("Damweg"));
        ReflectionTestUtils.setField(paGegevens, "afgekorteNaamOpenbareRuimte", new AfgekorteNaamOpenbareRuimte("Damwg"));
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
        ReflectionTestUtils.setField(paGegevens, "statusHistorie", StatusHistorie.A);
        ReflectionTestUtils.setField(paGegevens, "gemeente",
                referentieDataMdlRepository.vindGemeenteOpCode(new GemeenteCode("0034")));
        ReflectionTestUtils.setField(paGegevens, "redenwijziging",
                referentieDataMdlRepository.vindRedenWijzingAdresOpCode(new RedenWijzigingAdresCode("A")));
        ReflectionTestUtils.setField(paGegevens, "aangeverAdreshouding", AangeverAdreshoudingIdentiteit.GEZAGHOUDER);
//                referentieDataMdlRepository.vindAangeverAdreshoudingOpCode(new AangeverAdreshoudingCode("G")));
        return adres;
    }

    @Test
    public void testIsIemandIngeschrevenOpAdres() {
        PersoonAdresMdl adres = creerTestAdres();
        Assert.assertTrue(persoonAdresMdlRepository.isIemandIngeschrevenOpAdres(adres));
    }


    /** Alleen huisnummer ontbreekt voor de rest dezelfde adres gegevens */
    @Test
    public void testIsIemandIngeschrevenOpAdresZonderHuisnummer() {
        PersoonAdresMdl adres = creerTestAdres();
        // Geen huisnummer
        ReflectionTestUtils.setField(adres.getGegevens(), "huisnummer", null);

        Assert.assertTrue(persoonAdresMdlRepository.isIemandIngeschrevenOpAdres(adres));
    }

    /** Alleen huisletter ontbreekt voor de rest dezelfde adres gegevens */
    @Test
    public void testIsIemandIngeschrevenOpAdresZonderHuisletter() {
        PersoonAdresMdl adres = creerTestAdres();
        // Geen huisletter
        ReflectionTestUtils.setField(adres.getGegevens(), "huisletter", null);

        Assert.assertTrue(persoonAdresMdlRepository.isIemandIngeschrevenOpAdres(adres));
    }

    /** Alleen huisnummertoevoeging ontbreekt voor de rest dezelfde adres gegevens */
    @Test
    public void testIsIemandIngeschrevenOpAdresZonderHuisnummerToevoeging() {
        PersoonAdresMdl adres = creerTestAdres();
        // zonder huisnummertoevoeging
        ReflectionTestUtils.setField(adres.getGegevens(), "huisnummertoevoeging", null);

        Assert.assertTrue(persoonAdresMdlRepository.isIemandIngeschrevenOpAdres(adres));
    }

    @Test
    public void testIsIemandIngeschrevenOpAdresZonderHuisnummerOntbreektHuisletterVerschillend() {
        PersoonAdresMdl adres = creerTestAdres();
        // Geen huisnummer
        ReflectionTestUtils.setField(adres.getGegevens(), "huisnummer", null);
        // Andere huisletter
        ReflectionTestUtils.setField(adres.getGegevens(), "huisletter", new Huisletter("b"));

        Assert.assertFalse(persoonAdresMdlRepository.isIemandIngeschrevenOpAdres(adres));
    }

    @Test
    public void testIsIemandIngeschrevenOpAdresZonderGenesteObjecten() {
        PersoonAdresMdl adres = maakNieuwAdres2();

        ReflectionTestUtils.setField(adres.getGegevens(), "gemeente", null);
        ReflectionTestUtils.setField(adres.getGegevens(), "woonplaats", null);
        Assert.assertFalse(persoonAdresMdlRepository.isIemandIngeschrevenOpAdres(adres));
    }

    @Test
    public void testNieuwAdresVoorPersoonZonderAdres() {
        final int datumAanvangGeldigheid = 20120228;
        PersistentActie actie = maakActie();
        nl.bzk.brp.model.logisch.PersoonAdres adres = maakNieuwAdres();
        adres.getPersoon().getIdentificatienummers().setBurgerservicenummer("345678912");
        // Voer de functie uit om de nieuwe adres op te slaan
        PersistentPersoonAdres adres1 = persoonAdresRepository.opslaanNieuwPersoonAdres(
                adres, datumAanvangGeldigheid, null, new Date());

        //Sla historie op
        persoonAdresHistorieRepository.opslaanHistorie(adres1, actie, datumAanvangGeldigheid, null);

        // Haal de adres history op
        final String adresHistorieSql =
            "SELECT adres FROM HisPersoonAdres adres " + "WHERE adres.persoonAdres.persoon.id = :persoonId "
                + "ORDER BY adres.datumAanvangGeldigheid DESC, adres.datumTijdRegistratie DESC";
        @SuppressWarnings("unchecked")
        List<HisPersoonAdres> adresHistorie =
            em.createQuery(adresHistorieSql).setParameter("persoonId", 3L).getResultList();
        // Verwacht wordt dat er 1 item is in de history
        Assert.assertEquals(1, adresHistorie.size());
        // A-laag
        PersistentPersoonAdres huidigAdres = adresHistorie.get(0).getPersoonAdres();
        Assert.assertNotNull(huidigAdres);
        // Controlleer huidige adres in de A-laag
        controleerHuidigAdresInALaag(huidigAdres);
        // C-laag - Nieuwe adres
        HisPersoonAdres nieuwHisAdres = adresHistorie.get(0);
        // Niet ingevulde tijd verval geeft aan dat de history item in laag C zit
        Assert.assertNull(nieuwHisAdres.getDatumTijdVerval());
        Assert.assertEquals(datumAanvangGeldigheid, nieuwHisAdres.getDatumAanvangGeldigheid().intValue());
        Assert.assertNull(nieuwHisAdres.getDatumEindeGeldigheid());
        Assert.assertEquals(20120229, nieuwHisAdres.getDatumAanvangAdreshouding().intValue());
        Assert.assertEquals("Damweg", nieuwHisAdres.getNaamOpenbareRuimte());
        Assert.assertEquals("Damwg", nieuwHisAdres.getAfgekorteNaamOpenbareRuimte());
        Assert.assertEquals("2", nieuwHisAdres.getHuisnummer());
        Assert.assertEquals("b", nieuwHisAdres.getHuisletter());
        Assert.assertEquals("III", nieuwHisAdres.getHuisnummertoevoeging());
        Assert.assertEquals("3063NB", nieuwHisAdres.getPostcode());
        Assert.assertEquals("Kijkduin", nieuwHisAdres.getWoonplaats().getNaam());
        Assert.assertEquals("1753", nieuwHisAdres.getAdresseerbaarObject());
        Assert.assertEquals("1815", nieuwHisAdres.getIdentificatiecodeNummeraanduiding());
        Assert.assertEquals(nieuwHisAdres.getActieInhoud().getId(), actie.getId());
    }

    @Test
    public void testVindHuidigAdresVoorPersoon() {
        PersoonAdresMdl adres = persoonAdresMdlRepository.vindHuidigWoonAdresVoorPersoon(
                new Burgerservicenummer("123456789"));
        Assert.assertNotNull(adres);
        Assert.assertEquals(1, adres.getId().intValue());
    }

    /**
     * Controleert het huidige adres uit de A-laag.
     *
     * @param huidigAdres het huidige adres uit de A-laag, na uitvoer van de verhuizing.
     */
    private void controleerHuidigAdresInALaag(final PersistentPersoonAdres huidigAdres) {
        Assert.assertEquals(20120229, huidigAdres.getDatumAanvangAdreshouding().intValue());
        Assert.assertEquals("Damweg", huidigAdres.getNaamOpenbareRuimte());
        Assert.assertEquals("Damwg", huidigAdres.getAfgekorteNaamOpenbareRuimte());
        Assert.assertEquals("2", huidigAdres.getHuisnummer());
        Assert.assertEquals("b", huidigAdres.getHuisletter());
        Assert.assertEquals("III", huidigAdres.getHuisnummertoevoeging());
        Assert.assertEquals("3063NB", huidigAdres.getPostcode());
        Assert.assertEquals("Kijkduin", huidigAdres.getWoonplaats().getNaam());
        Assert.assertEquals("1753", huidigAdres.getAdresseerbaarObject());
        Assert.assertEquals("1815", huidigAdres.getIdentificatiecodeNummeraanduiding());
    }

    private nl.bzk.brp.model.logisch.PersoonAdres maakNieuwAdres() {
        nl.bzk.brp.model.logisch.PersoonAdres adres = new nl.bzk.brp.model.logisch.PersoonAdres();
        nl.bzk.brp.model.logisch.Persoon persoon = new nl.bzk.brp.model.logisch.Persoon();
        nl.bzk.brp.model.logisch.groep.PersoonIdentificatienummers persoonIdentificatienummers = new nl.bzk.brp.model.logisch.groep.PersoonIdentificatienummers();
        persoonIdentificatienummers.setBurgerservicenummer("100001001");
        persoon.setIdentificatienummers(persoonIdentificatienummers);
        adres.setPersoon(persoon);
        adres.setDatumAanvangAdreshouding(20120229);
        adres.setNaamOpenbareRuimte("Damweg");
        adres.setAfgekorteNaamOpenbareRuimte("Damwg");
        adres.setHuisnummer("2");
        adres.setHuisletter("b");
        adres.setHuisnummertoevoeging("III");
        adres.setPostcode("3063NB");
        nl.bzk.brp.model.gedeeld.Plaats woonplaats = new nl.bzk.brp.model.gedeeld.Plaats();
        woonplaats.setWoonplaatscode("1352");
        adres.setWoonplaats(woonplaats);
        adres.setAdresseerbaarObject("1753");
        adres.setIdentificatiecodeNummeraanduiding("1815");
        nl.bzk.brp.model.gedeeld.Land land = new nl.bzk.brp.model.gedeeld.Land();
        land.setId(2);
        land.setLandcode("6030");
        adres.setLand(land);
        adres.setPersoonAdresStatusHis("A");
        nl.bzk.brp.model.gedeeld.Partij gemeente = new nl.bzk.brp.model.gedeeld.Partij();
        gemeente.setGemeentecode("0034");
        adres.setGemeente(gemeente);
        adres.setRedenWijziging(nl.bzk.brp.model.gedeeld.RedenWijzigingAdres.AMBTSHALVE);
        adres.setAangeverAdreshouding(nl.bzk.brp.model.gedeeld.AangeverAdreshoudingIdentiteit.GEZAGHOUDER);
        return adres;
    }

//    @Test
//    public void testZoekAdresMetVolledigFilter() throws Exception {
//        AdresSelectieFilter filter = new AdresSelectieFilter();
//        filter.setAdresSoorten(FunctieAdres.BRIEFADRES, FunctieAdres.WOONADRES);
//        filter.setHuisletter("h");
//        filter.setHuisnummer("123");
//        filter.setHuisnummertoevoeging("t123");
//        filter.setIdentificatiecodeNummeraanduiding("t123");
//        filter.setNaamOpenbareRuimte("t123");
//        filter.setPeilDatum(464333);
//        filter.setPostcode("Postcode");
//        filter.setWoonplaatscodes("1234", "3445", "2222");
//        persoonAdresRepository.zoekBinnelandseAdressen(filter);
//
//    }

    private PersistentActie maakActie() {
        PersistentActie actie = new PersistentActie();
        actie.setTijdstipRegistratie(new Date(System.currentTimeMillis() - 1));
        actie.setSoort(nl.bzk.brp.model.gedeeld.SoortActie.REGISTRATIE_NATIONALITEIT);
        actie.setPartij(
                partijRepository.findOne(4)
        );
        em.persist(actie);
        return actie;
    }

    @Test
    public void haalPersoonAdresOp() {
        PersoonAdres pa = em.find(PersoonAdresMdl.class, 10001L);

        Assert.assertEquals(new Burgerservicenummer("135867277"), pa.getPersoon().getIdentificatieNummers().getBurgerServiceNummer());
        Assert.assertEquals("Hoofd instelling", pa.getGegevens().getAangeverAdresHouding().getNaam());
        Assert.assertEquals("1492",             pa.getGegevens().getAdresseerbaarObject().getWaarde());
        Assert.assertEquals("Dorpstr",          pa.getGegevens().getAfgekorteNaamOpenbareRuimte().getWaarde());
        Assert.assertEquals("Regel 1",          pa.getGegevens().getBuitenlandsAdresRegel1().getWaarde());
        Assert.assertEquals("Regel 2",          pa.getGegevens().getBuitenlandsAdresRegel2().getWaarde());
        Assert.assertEquals("Regel 3",          pa.getGegevens().getBuitenlandsAdresRegel3().getWaarde());
        Assert.assertEquals("Regel 4",          pa.getGegevens().getBuitenlandsAdresRegel4().getWaarde());
        Assert.assertEquals("Regel 5",          pa.getGegevens().getBuitenlandsAdresRegel5().getWaarde());
        Assert.assertEquals("Regel 6",          pa.getGegevens().getBuitenlandsAdresRegel6().getWaarde());
        Assert.assertEquals(new Integer(20120101), pa.getGegevens().getDatumAanvangAdreshouding().getWaarde());
        Assert.assertEquals(new Integer(12020508), pa.getGegevens().getDatumVertrekUitNederland().getWaarde());
        Assert.assertEquals("Almere",           pa.getGegevens().getGemeente().getNaam().getWaarde());
        Assert.assertEquals("GemDeel",          pa.getGegevens().getGemeentedeel().getWaarde());
        Assert.assertEquals("a",                pa.getGegevens().getHuisletter().getWaarde());
        Assert.assertEquals("512",              pa.getGegevens().getHuisnummer().getWaarde());
        Assert.assertEquals("IV",               pa.getGegevens().getHuisnummertoevoeging().getWaarde());
        Assert.assertEquals("Nederland",        pa.getGegevens().getLand().getNaam().getWaarde());
        Assert.assertEquals("1581",             pa.getGegevens().getIdentificatiecodeNummeraanduiding().getWaarde());
        Assert.assertEquals("Omschrijving",     pa.getGegevens().getLocatieOmschrijving().getWaarde());
        Assert.assertEquals("ta",               pa.getGegevens().getLocatietovAdres().getWaarde());
        Assert.assertEquals("Dorpstraat",       pa.getGegevens().getNaamOpenbareRuimte().getWaarde());
        Assert.assertEquals("7812PK",           pa.getGegevens().getPostcode().getWaarde());
        Assert.assertEquals("Aangifte door persoon", pa.getGegevens().getRedenWijziging().getNaam().getWaarde());
        Assert.assertEquals("Woonadres",        pa.getGegevens().getSoort().getNaam());
        Assert.assertEquals("0034",         pa.getGegevens().getWoonplaats().getCode().getWaarde());
        Assert.assertEquals("Almeres",      pa.getGegevens().getWoonplaats().getNaam().getWaarde());

        Assert.assertNull(pa.getGegevens().getStatusHistorie());
    }

    @Test
    public void slaPersoonAdresOp() {
        PersoonMdl persoon = em.find(PersoonMdl.class, 1L);
        RedenWijzigingAdres rdnWijzig = em.find(RedenWijzigingAdres.class, 1);
        Land land = em.find(Land.class, 2L);
        Plaats woonplaats = em.find(Plaats.class, 1L);
        Partij gemeente = em.find(Partij.class, 3L);

        PersoonAdresMdl pa = new PersoonAdresMdl();
        PersoonAdresStandaardGroepMdl paGegevens = new PersoonAdresStandaardGroepMdl();
        ReflectionTestUtils.setField(pa, "gegevens", paGegevens);
        ReflectionTestUtils.setField(pa, "persoon", persoon);

        ReflectionTestUtils.setField(paGegevens, "soort", FunctieAdres.WOONADRES);
        ReflectionTestUtils.setField(paGegevens, "datumAanvangAdreshouding", new Datum(20120802));
        ReflectionTestUtils.setField(paGegevens, "redenwijziging", rdnWijzig);
        ReflectionTestUtils.setField(paGegevens, "gemeente", gemeente);
        ReflectionTestUtils.setField(paGegevens, "woonplaats", woonplaats);
        ReflectionTestUtils.setField(paGegevens, "land", land);
        ReflectionTestUtils.setField(paGegevens, "aangeverAdreshouding", AangeverAdreshoudingIdentiteit.HOOFDINSTELLING);
        ReflectionTestUtils.setField(paGegevens, "adresseerbaarObject", new Adresseerbaarobject("AdreerbaarObject x"));
        ReflectionTestUtils.setField(paGegevens, "afgekorteNaamOpenbareRuimte", new AfgekorteNaamOpenbareRuimte("Afgekorte NOR"));
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
        ReflectionTestUtils.setField(paGegevens, "statusHistorie", StatusHistorie.A);

        em.persist(pa);
        Assert.assertTrue("Fout gegaan met wegschrijven, null Id", (pa.getId() != null));
        PersoonAdresMdl paNew = em.find(PersoonAdresMdl.class, pa.getId());
        pa = paNew;
        Assert.assertEquals("Hoofd instelling", pa.getGegevens().getAangeverAdresHouding().getNaam());
        Assert.assertEquals("AdreerbaarObject x", pa.getGegevens().getAdresseerbaarObject().getWaarde());
        Assert.assertEquals("Afgekorte NOR",    pa.getGegevens().getAfgekorteNaamOpenbareRuimte().getWaarde());
        Assert.assertEquals("Regel 1",          pa.getGegevens().getBuitenlandsAdresRegel1().getWaarde());
        Assert.assertEquals("Regel 2",          pa.getGegevens().getBuitenlandsAdresRegel2().getWaarde());
        Assert.assertEquals("Regel 3",          pa.getGegevens().getBuitenlandsAdresRegel3().getWaarde());
        Assert.assertEquals("Regel 4",          pa.getGegevens().getBuitenlandsAdresRegel4().getWaarde());
        Assert.assertEquals("Regel 5",          pa.getGegevens().getBuitenlandsAdresRegel5().getWaarde());
        Assert.assertEquals("Regel 6",          pa.getGegevens().getBuitenlandsAdresRegel6().getWaarde());
        Assert.assertEquals(new Integer(20120101), pa.getGegevens().getDatumAanvangAdreshouding().getWaarde());
        Assert.assertEquals(new Integer(12020508), pa.getGegevens().getDatumVertrekUitNederland().getWaarde());
        Assert.assertEquals("Almere",           pa.getGegevens().getGemeente().getNaam().getWaarde());
        Assert.assertEquals("GemDeel",          pa.getGegevens().getGemeentedeel().getWaarde());
        Assert.assertEquals("a",                pa.getGegevens().getHuisletter().getWaarde());
        Assert.assertEquals("512",              pa.getGegevens().getHuisnummer().getWaarde());
        Assert.assertEquals("IV",               pa.getGegevens().getHuisnummertoevoeging().getWaarde());
        Assert.assertEquals("Nederland",        pa.getGegevens().getLand().getNaam().getWaarde());
        Assert.assertEquals("1581",             pa.getGegevens().getIdentificatiecodeNummeraanduiding().getWaarde());
        Assert.assertEquals("Omschrijving",     pa.getGegevens().getLocatieOmschrijving().getWaarde());
        Assert.assertEquals("ta",               pa.getGegevens().getLocatietovAdres().getWaarde());
        Assert.assertEquals("Dorpstraat",       pa.getGegevens().getNaamOpenbareRuimte().getWaarde());
        Assert.assertEquals("7812PK",           pa.getGegevens().getPostcode().getWaarde());
        Assert.assertEquals("Aangifte door persoon", pa.getGegevens().getRedenWijziging().getNaam().getWaarde());
        Assert.assertEquals("Woonadres",        pa.getGegevens().getSoort().getNaam());
        Assert.assertEquals("0034",         pa.getGegevens().getWoonplaats().getCode().getWaarde());
        Assert.assertEquals("Almeres",      pa.getGegevens().getWoonplaats().getNaam().getWaarde());

        Assert.assertEquals("Actueel voorkomend", pa.getGegevens().getStatusHistorie().getNaam());

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
        Partij party = em.find(Partij.class, 1L);
        Assert.assertEquals("Regering en Staten-Generaal", party.getNaam().getWaarde());
        Plaats plaats = em.find(Plaats.class, 1L);
        Assert.assertEquals("Almeres", plaats.getNaam().getWaarde());
        Predikaat predikaat = em.find(Predikaat.class, 1);
        Assert.assertEquals("K", predikaat.getCode().getWaarde());
//
//        RedenBeeindigingRelatie redenBeeindigingRelatie = em.find(RedenBeeindigingRelatie.class, 3);
//        System.out.println(redenBeeindigingRelatie + ","  + redenBeeindigingRelatie.getCode());
        RedenWijzigingAdres redenWijzigingAdres = em.find(RedenWijzigingAdres.class, 1);
        Assert.assertEquals("Aangifte door persoon", redenWijzigingAdres.getNaam().getWaarde());
    }

    @Test
    public void testVindHuidigWoonAdresVoorPersoon() {
        PersistentPersoonAdres adres = persoonAdresRepository.vindHuidigWoonAdresVoorPersoon("123456789");
        Assert.assertNotNull(adres);
        Assert.assertEquals(1, adres.getId().longValue());
    }

}
