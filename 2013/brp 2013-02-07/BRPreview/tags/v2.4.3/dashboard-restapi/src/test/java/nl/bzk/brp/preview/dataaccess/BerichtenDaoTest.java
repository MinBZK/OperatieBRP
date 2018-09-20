/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.dataaccess;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import nl.bzk.brp.preview.integratie.AbstractIntegratieTest;
import nl.bzk.brp.preview.model.Bericht;
import nl.bzk.brp.preview.model.OndersteundeBijhoudingsTypes;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;


public class BerichtenDaoTest extends AbstractIntegratieTest {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private BerichtenDao               berichtenDao;

    @Test
    public void testOpslaan() {

        Bericht bericht = new Bericht();
        bericht.setPartij("Leiden");
        bericht.setBericht("qwe");
        bericht.setBerichtDetails("asd");
        bericht.setAantalMeldingen(2);
        Calendar verzondenOp = Calendar.getInstance();
        verzondenOp.clear();
        verzondenOp.set(2004, Calendar.OCTOBER, 19, 10, 23, 54);
        bericht.setVerzondenOp(verzondenOp);
        bericht.setBurgerZakenModule("zxc");
        bericht.setSoortBijhouding(OndersteundeBijhoudingsTypes.GEBOORTE);
        bericht.setPrevalidatie(true);
        List<Integer> burgerservicenummers = new ArrayList<Integer>();
        burgerservicenummers.add(39);
        burgerservicenummers.add(65);
        bericht.setBurgerservicenummers(burgerservicenummers);

        // test
        berichtenDao.opslaan(bericht);

        String sql =
            "select count(0) from dashboard.berichten where 1 = 1"
                + " and partij = 'Leiden'" + " and bericht = 'qwe'"
                + " and berichtdetails = 'asd'" + " and aantalmeldingen = 2"
                + " and tsverzonden = TIMESTAMP '2004-10-19 10:23:54'" + " and bzm = 'zxc'"
                + " and soortactie = 'GEBOORTE'" + " and indprevalidatie = true";
        long count = jdbcTemplate.queryForLong(sql, (Map<String, ?>) null);

        Assert.assertEquals(1, count);

        sql = sql.replaceFirst("count\\(0\\)", "id");
        String sqlBsn = String.format("select count(0) from dashboard.bericht_bsn where bericht = (%s)", sql);
        long countBsn = jdbcTemplate.queryForLong(sqlBsn, (Map<String, ?>) null);

        Assert.assertEquals(2, countBsn);

    }

    @Test
    public void testGetAlleBerichten() {

        // test
        List<Bericht> berichtenLijst = berichtenDao.getAlleBerichten();

        Bericht[] berichten = berichtenLijst.toArray(new Bericht[berichtenLijst.size()]);

        Assert.assertEquals("Utrecht", berichten[0].getPartij());
        Assert.assertEquals("Utrecht heeft een verhuizing geprevalideerd.", berichten[0].getBericht());
        Assert.assertEquals("Johan (BSN 111222333) heeft per 1 mei 2012 als adres:"
            + " Lange Vijverberg 11 (Den Haag).", berichten[0].getBerichtDetails());
        Assert.assertEquals(1, berichten[0].getAantalMeldingen());
        Assert.assertEquals(2012, berichten[0].getVerzondenOp().get(Calendar.YEAR));
        Assert.assertEquals(Calendar.MAY, berichten[0].getVerzondenOp().get(Calendar.MONTH));
        Assert.assertEquals(15, berichten[0].getVerzondenOp().get(Calendar.DATE));
        Assert.assertEquals(10, berichten[0].getVerzondenOp().get(Calendar.HOUR));
        Assert.assertEquals(9, berichten[0].getVerzondenOp().get(Calendar.MINUTE));
        Assert.assertEquals(8, berichten[0].getVerzondenOp().get(Calendar.SECOND));
        Assert.assertEquals("CentricBZM", berichten[0].getBurgerZakenModule());
        Assert.assertEquals(OndersteundeBijhoudingsTypes.VERHUIZING, berichten[0].getSoortBijhouding());
        Assert.assertTrue(berichten[0].isPrevalidatie());
        Assert.assertEquals("Amsterdam", berichten[1].getPartij());
        Assert.assertEquals("Rotterdam", berichten[2].getPartij());
    }

    @Test
    public void testGetBerichtenOpBsn() {

        // test
        List<Bericht> berichten = berichtenDao.getBerichtenOpBsn(1201);

        Assert.assertEquals(2, berichten.size());
        Assert.assertEquals("Amsterdam", berichten.get(0).getPartij());
        Assert.assertEquals("Rotterdam", berichten.get(1).getPartij());
    }


    @Test
    public void testGetBerichtenVanaf() {

        Calendar vanaf = Calendar.getInstance();
        vanaf.set(2012, Calendar.MAY, 15, 10, 9, 7);

        // test
        List<Bericht> berichten = berichtenDao.getBerichtenVanaf(vanaf);

        Assert.assertEquals(1, berichten.size());
        Assert.assertEquals("Utrecht", berichten.get(0).getPartij());
    }


    @Test
    public void shouldGetBerichtenVoorPartij() {
        String partij = "Utrecht";

        List<Bericht> berichten = berichtenDao.getBerichtenVoorPartij(partij);

        Assert.assertEquals("Utrecht", berichten.get(0).getPartij());
    }


    @Test
    public void shouldGetBerichtenVoorPartijVanaf() {
        String partij = "Utrecht";
        Calendar vanaf = Calendar.getInstance();
        vanaf.set(2012, Calendar.MAY, 15, 10, 9, 7);

        List<Bericht> berichten = berichtenDao.getBerichtenVoorPartijVanaf(partij, vanaf);

        Assert.assertEquals("Utrecht", berichten.get(0).getPartij());

    }

}
