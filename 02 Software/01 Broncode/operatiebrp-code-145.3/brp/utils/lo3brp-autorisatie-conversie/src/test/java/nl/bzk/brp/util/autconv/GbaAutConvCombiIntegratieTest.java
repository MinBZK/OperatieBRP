/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.autconv;

import java.io.IOException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelGroepAttribuut;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAfnemerindicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.algemeenbrp.test.dal.data.Data;
import org.junit.Assert;
import org.junit.Test;

@Data(resources = {"classpath:dbunit-alleen-gba-combi-autorisatie.xml", "classpath:dbunit-cleanup.xml"})
public class GbaAutConvCombiIntegratieTest extends AbstractIntegratietest {

    private static final String AFLEVERPUNT_BRP = "mijnuri";

    static {
        System.setProperty("afleverpunt", AFLEVERPUNT_BRP);
    }

    @PersistenceContext(unitName = "nl.bzk.brp.master")
    private EntityManager em;

    @Test
    public void test() throws IOException {
        getConverteerder().converteer();

        final List<Dienstbundel> dienstbundelList = em.createQuery("SELECT d FROM Dienstbundel d", Dienstbundel.class).getResultList();
        Assert.assertEquals(12, dienstbundelList.size());
        Assert.assertEquals(6, dienstbundelList.stream().filter(dienstbundel -> dienstbundel.getNaam().contains("BRP")).count());

        final List<Dienst> dienstList = em.createQuery("SELECT d FROM Dienst d", Dienst.class).getResultList();
        Assert.assertEquals(24, dienstList.size());
        Assert.assertEquals(12, dienstList.stream().filter(dienst -> dienst.getDienstbundel().getNaam().contains("BRP")).count());

        final List<DienstbundelGroepAttribuut> attribuutList =
                em.createQuery("SELECT d FROM DienstbundelGroepAttribuut d", DienstbundelGroepAttribuut.class).getResultList();
        Assert.assertEquals(66, attribuutList.size());

        final List<Leveringsautorisatie> leveringsautorisatieList =
                em.createQuery("SELECT d FROM Leveringsautorisatie d", Leveringsautorisatie.class).getResultList();
        Assert.assertEquals(6, leveringsautorisatieList.size());
        Assert.assertEquals(3, leveringsautorisatieList.stream().filter(leveringsautorisatie -> leveringsautorisatie.getStelsel() == Stelsel.BRP).count());

        final List<ToegangLeveringsAutorisatie> toegangleveringsautorisatieList =
                em.createQuery("SELECT d FROM ToegangLeveringsAutorisatie d", ToegangLeveringsAutorisatie.class).getResultList();
        Assert.assertEquals(6, toegangleveringsautorisatieList.size());
        Assert.assertEquals(3, toegangleveringsautorisatieList.stream().filter(tla -> tla.getLeveringsautorisatie().getStelsel() == Stelsel.BRP).count());

        final List<PersoonAfnemerindicatie> afnemerindicatieList =
                em.createQuery("SELECT d FROM PersoonAfnemerindicatie d", PersoonAfnemerindicatie.class).getResultList();
        Assert.assertEquals(12, afnemerindicatieList.size());
        Assert.assertEquals(6, afnemerindicatieList.stream().filter(a -> a.getLeveringsautorisatie().getStelsel() == Stelsel.BRP).count());
    }
}
