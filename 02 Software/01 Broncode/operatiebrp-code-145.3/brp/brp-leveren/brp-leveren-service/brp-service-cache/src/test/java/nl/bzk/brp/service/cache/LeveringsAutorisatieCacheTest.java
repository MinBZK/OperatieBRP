/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.cache;

import com.google.common.collect.Iterables;
import java.util.ArrayList;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;

/**
 * LeveringsAutorisatieCacheTest
 */
@RunWith(MockitoJUnitRunner.class)
public class LeveringsAutorisatieCacheTest {

    @Mock
    private BrpCache brpCache;

    @Mock
    private ApplicationContext applicationContext;

    @Mock
    private LeveringsAutorisatieCacheHelper levAutCacheHelper;

    @InjectMocks
    private LeveringsAutorisatieCacheImpl leveringAutorisatieCache;


    @Before
    public void beforeTest() {
        final List<Partij> allePartijen = new ArrayList<>();
        final Partij partij1 = new Partij("partij1", "000001");
        partij1.setId((short) 1);
        final PartijRol partijRol1 = new PartijRol(partij1, Rol.AFNEMER);
        partijRol1.setId(1);
        partij1.addPartijRol(partijRol1);
        allePartijen.add(partij1);

        final Partij ondertekenaar = new Partij("ondertekenaar", "000002");
        ondertekenaar.setId((short) 2);
        final PartijRol partijRol2 = new PartijRol(ondertekenaar, Rol.AFNEMER);
        partijRol2.setId(2);
        ondertekenaar.addPartijRol(partijRol2);
        allePartijen.add(ondertekenaar);

        final Partij transporteur = new Partij("transporteur", "000003");
        transporteur.setId((short) 3);
        final PartijRol partijRol3 = new PartijRol(transporteur, Rol.AFNEMER);
        partijRol3.setId(3);
        transporteur.addPartijRol(partijRol3);
        allePartijen.add(transporteur);

        final PartijCacheImpl.Data partijData = new PartijCacheImpl.Data(allePartijen);
        final List<ToegangLeveringsAutorisatie> toegangLeveringAutorisaties = new ArrayList<>();

        final Leveringsautorisatie leveringsAutorisatie1 = new Leveringsautorisatie(Stelsel.BRP, false);
        leveringsAutorisatie1.setPopulatiebeperking("1 = 3");
        leveringsAutorisatie1.setId(1);
        final ToegangLeveringsAutorisatie toegangLeveringsAutorisatie1 = new ToegangLeveringsAutorisatie(partijRol1, leveringsAutorisatie1);
        toegangLeveringsAutorisatie1.setAfleverpunt("localhost");
        toegangLeveringsAutorisatie1.setId(1);
        toegangLeveringsAutorisatie1.setOndertekenaar(ondertekenaar);
        toegangLeveringsAutorisatie1.setNaderePopulatiebeperking("1=1");
        toegangLeveringAutorisaties.add(toegangLeveringsAutorisatie1);

        final Dienstbundel dienstbundel1 = new Dienstbundel(leveringsAutorisatie1);
        dienstbundel1.setId(1);
        final Dienst dienst1 = new Dienst(dienstbundel1, SoortDienst.ATTENDERING);
        dienst1.setId(11);
        dienst1.setAttenderingscriterium("1 = 6");
        dienstbundel1.addDienstSet(dienst1);
        dienstbundel1.setNaderePopulatiebeperking("1 = 2");
        final Dienst dienst2 = new Dienst(dienstbundel1, SoortDienst.GEEF_DETAILS_PERSOON);
        dienst2.setId(12);
        //ongeldige diensten worden uitgesloten, maar toegang blijft wel behouden omdat er een andere geldige dienst is
        dienst2.setAttenderingscriterium("()");
        dienstbundel1.addDienstSet(dienst2);
        leveringsAutorisatie1.addDienstbundelSet(dienstbundel1);
        //ongeldige dienstbundel wordt uitgesloten als geheel
        final Dienstbundel ongeldigeDienstbundel = new Dienstbundel(leveringsAutorisatie1);
        ongeldigeDienstbundel.setId(2);
        ongeldigeDienstbundel.setNaderePopulatiebeperking("()");
        final Dienst dienst3 = new Dienst(ongeldigeDienstbundel, SoortDienst.GEEF_DETAILS_PERSOON);
        dienst3.setId(13);
        ongeldigeDienstbundel.addDienstSet(dienst3);
        leveringsAutorisatie1.addDienstbundelSet(ongeldigeDienstbundel);

        //ongeldig
        final ToegangLeveringsAutorisatie toegangLeveringsAutorisatie2 = new ToegangLeveringsAutorisatie(partijRol1, leveringsAutorisatie1);
        toegangLeveringsAutorisatie2.setAfleverpunt("localhost");
        toegangLeveringsAutorisatie2.setId(2);
        toegangLeveringsAutorisatie2.setOndertekenaar(ondertekenaar);
        //ongeldige toegangleveringsautorisaties worden uitgsloten
        toegangLeveringsAutorisatie2.setNaderePopulatiebeperking("()");
        toegangLeveringAutorisaties.add(toegangLeveringsAutorisatie2);

        //ongeldig
        final Leveringsautorisatie leveringsAutorisatie2 = new Leveringsautorisatie(Stelsel.BRP, false);
        leveringsAutorisatie2.setId(1);
        //toegangleveringsautorisaties met leveringsAutorisatie worden uitgsloten
        leveringsAutorisatie2.setPopulatiebeperking("()");
        final ToegangLeveringsAutorisatie toegangLeveringsAutorisatie3 = new ToegangLeveringsAutorisatie(partijRol1, leveringsAutorisatie2);
        toegangLeveringsAutorisatie3.setAfleverpunt("localhost");
        toegangLeveringsAutorisatie3.setId(3);
        toegangLeveringsAutorisatie3.setOndertekenaar(ondertekenaar);
        toegangLeveringsAutorisatie3.setNaderePopulatiebeperking("WAAR");
        toegangLeveringAutorisaties.add(toegangLeveringsAutorisatie3);

        Mockito.when(levAutCacheHelper.ophalenAlleToegangLeveringsautorisaties(partijData)).thenReturn(toegangLeveringAutorisaties);

        final CacheEntry cacheEntry = leveringAutorisatieCache.herlaad(partijData);
        Mockito.when(brpCache.getCache(LeveringsAutorisatieCacheImpl.CACHE_NAAM)).thenReturn(cacheEntry.getData());
    }

    @Test
    public void testGeefAlleToegangleveringsautorisaties() {
        final List<ToegangLeveringsAutorisatie> toegangLeveringAutorisaties = leveringAutorisatieCache.geefAlleToegangleveringsautorisaties();
        Assert.assertEquals(1, toegangLeveringAutorisaties.size());
    }

    @Test
    public void testGeefToegangLeveringsautorisatie() {
        final ToegangLeveringsAutorisatie toegangLeveringsAutorisatie = leveringAutorisatieCache.geefToegangLeveringsautorisatie(1);
        Assert.assertNotNull(toegangLeveringsAutorisatie);
    }

    @Test
    public void testWegfilterenDienstBundel() {
        final ToegangLeveringsAutorisatie toegangLeveringsAutorisatie = leveringAutorisatieCache.geefToegangLeveringsautorisatie(1);
        Assert.assertNotNull(toegangLeveringsAutorisatie);
        final boolean dienstbundelAanwezig = Iterables.any(toegangLeveringsAutorisatie.getLeveringsautorisatie().getDienstbundelSet(),
                db -> db.getId() == 2);
        Assert.assertFalse(dienstbundelAanwezig);
        final Dienst dienst = leveringAutorisatieCache.geefDienst(13);
        Assert.assertNull(dienst);
    }

    @Test
    public void testGeefToegangleveringautorisatiesVoorGeautoriseerdePartij() {
        final List<ToegangLeveringsAutorisatie> toegangLeveringAutorisaties = leveringAutorisatieCache
                .geefToegangleveringautorisatiesVoorGeautoriseerdePartij("000001");
        Assert.assertNotNull(toegangLeveringAutorisaties);
        Assert.assertEquals(1, toegangLeveringAutorisaties.size());
        final ToegangLeveringsAutorisatie toegangLeveringsAutorisatie = toegangLeveringAutorisaties.get(0);
        final Dienst dienst = leveringAutorisatieCache.geefDienst(11);
        Assert.assertEquals("1 = 3 EN (1 = 2 EN 1 = 1)", leveringAutorisatieCache.geefPopulatiebeperking(toegangLeveringsAutorisatie, dienst).toString());
    }

    @Test
    public void testGeefPopulatieBeperking() {
        final List<ToegangLeveringsAutorisatie> toegangLeveringAutorisaties = leveringAutorisatieCache
                .geefToegangleveringautorisatiesVoorGeautoriseerdePartij("000001");
        final ToegangLeveringsAutorisatie toegangLeveringsAutorisatie = toegangLeveringAutorisaties.get(0);
        final Dienst dienst = leveringAutorisatieCache.geefDienst(11);
        Assert.assertEquals("1 = 3 EN (1 = 2 EN 1 = 1)", leveringAutorisatieCache.geefPopulatiebeperking(toegangLeveringsAutorisatie, dienst).toString());
    }

    @Test
    public void testGeefAttenderingsCriterium() {
        final Dienst dienst = leveringAutorisatieCache.geefDienst(11);
        Assert.assertEquals("1 = 6", leveringAutorisatieCache.geefAttenderingExpressie(dienst).toString());
    }

    @Test
    public void testGeefToegangleveringsautorisatieLeveringsautorisatieIdEnPartijCode() {
        final ToegangLeveringsAutorisatie toegangLeveringsAutorisatie = leveringAutorisatieCache.geefToegangLeveringsautorisatie(1, "000001");
        Assert.assertNotNull(toegangLeveringsAutorisatie);
    }

    @Test
    public void testGeefLeveringsautorisatie() {
        final Leveringsautorisatie leveringsautorisatie = leveringAutorisatieCache.geefLeveringsautorisatie(1);
        Assert.assertNotNull(leveringsautorisatie);
    }

    @Test
    public void testGeefDienst() {
        final Dienst dienst = leveringAutorisatieCache.geefDienst(11);
        Assert.assertNotNull(dienst);
    }

    @Test
    public void testGeefOngeldigeDienst() {
        final Dienst dienst = leveringAutorisatieCache.geefDienst(12);
        Assert.assertNull(dienst);
    }
}
