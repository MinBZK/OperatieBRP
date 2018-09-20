/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc301;

import java.util.HashMap;
import java.util.Map;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.If01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Ii01Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.LeesUitBrpAntwoordBericht;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.InMemoryBerichtenDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class MaakIf01BerichtGMOActionTest {

    private MaakIf01BerichtGMOAction subject;
    private BerichtenDao berichtenDao;

    @Before
    public void setup() {
        subject = new MaakIf01BerichtGMOAction();
        berichtenDao = new InMemoryBerichtenDao();
        ReflectionTestUtils.setField(subject, "berichtenDao", berichtenDao);
    }

    @Test
    public void testOverlijden() {
        final Ii01Bericht ii01Bericht = new Ii01Bericht();
        ii01Bericht.setBronGemeente("1234");
        ii01Bericht.setDoelGemeente("5678");
        ii01Bericht.set(Lo3CategorieEnum.PERSOON, Lo3ElementEnum.ANUMMER, "1234567891");

        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        builder.persoonStapel(Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
            Lo3StapelHelper.lo3Persoon(1234567892L, "Jan", "Jaapersten", 19700101, "2134", "6030", "M"),
            Lo3StapelHelper.lo3Akt(1),
            Lo3StapelHelper.lo3His(19700101),
            new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 0))));
        builder.inschrijvingStapel(Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
            Lo3StapelHelper.lo3Inschrijving(null, null, "O", 19700101, "0053", 5, 1, 19700101124500000L, true),
            null,
            Lo3Historie.NULL_HISTORIE,
            new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_07, 0, 0))));
        final LeesUitBrpAntwoordBericht queryResponse = new LeesUitBrpAntwoordBericht("dummy_correlation_id", builder.build());

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", berichtenDao.bewaarBericht(ii01Bericht));
        parameters.put("leesUitBrpAntwoordBericht", berichtenDao.bewaarBericht(queryResponse));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals(1, result.size());

        final If01Bericht if01Bericht = (If01Bericht) berichtenDao.leesBericht((Long) result.get("if01Bericht"));
        Assert.assertNotNull(if01Bericht);
        Assert.assertEquals("1234", if01Bericht.getDoelGemeente());
        Assert.assertEquals("5678", if01Bericht.getBronGemeente());
        Assert.assertEquals(ii01Bericht.getCategorieen(), if01Bericht.getCategorieen());
        Assert.assertEquals("O", if01Bericht.getHeader(Lo3HeaderVeld.FOUTREDEN));
        Assert.assertEquals("1234567892", if01Bericht.getHeader(Lo3HeaderVeld.A_NUMMER));
    }

    @Test
    public void testEmigratie() {
        final Ii01Bericht ii01Bericht = new Ii01Bericht();
        ii01Bericht.setBronGemeente("1234");
        ii01Bericht.setDoelGemeente("5678");
        ii01Bericht.set(Lo3CategorieEnum.PERSOON, Lo3ElementEnum.ANUMMER, "1234567891");

        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        builder.persoonStapel(Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
            Lo3StapelHelper.lo3Persoon(1234567892L, "Jan", "Jaapersten", 19700101, "2134", "6030", "M"),
            Lo3StapelHelper.lo3Akt(1),
            Lo3StapelHelper.lo3His(19700101),
            new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 0))));
        builder.inschrijvingStapel(Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
            Lo3StapelHelper.lo3Inschrijving(null, null, "E", 19700101, "0053", 5, 1, 19700101124500000L, true),
            null,
            Lo3Historie.NULL_HISTORIE,
            new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_07, 0, 0))));
        final LeesUitBrpAntwoordBericht queryResponse = new LeesUitBrpAntwoordBericht("dummy_correlation_id", builder.build());

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", berichtenDao.bewaarBericht(ii01Bericht));
        parameters.put("leesUitBrpAntwoordBericht", berichtenDao.bewaarBericht(queryResponse));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals(1, result.size());

        final If01Bericht if01Bericht = (If01Bericht) berichtenDao.leesBericht((Long) result.get("if01Bericht"));
        Assert.assertNotNull(if01Bericht);
        Assert.assertEquals("1234", if01Bericht.getDoelGemeente());
        Assert.assertEquals("5678", if01Bericht.getBronGemeente());
        Assert.assertEquals(ii01Bericht.getCategorieen(), if01Bericht.getCategorieen());
        Assert.assertEquals("E", if01Bericht.getHeader(Lo3HeaderVeld.FOUTREDEN));
        Assert.assertEquals("1234567892", if01Bericht.getHeader(Lo3HeaderVeld.A_NUMMER));
    }

    @Test
    public void testFout() {
        final Ii01Bericht ii01Bericht = new Ii01Bericht();
        ii01Bericht.setBronGemeente("1234");
        ii01Bericht.setDoelGemeente("5678");
        ii01Bericht.set(Lo3CategorieEnum.PERSOON, Lo3ElementEnum.ANUMMER, "1234567891");

        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        builder.persoonStapel(Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
            Lo3StapelHelper.lo3Persoon(1234567892L, "Jan", "Jaapersten", 19700101, "2134", "6030", "M"),
            Lo3StapelHelper.lo3Akt(1),
            Lo3StapelHelper.lo3His(19700101),
            new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 0))));
        builder.inschrijvingStapel(Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
            Lo3StapelHelper.lo3Inschrijving(null, null, "F", 19700101, "0053", 5, 1, 19700101124500000L, true),
            null,
            Lo3Historie.NULL_HISTORIE,
            new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_07, 0, 0))));
        final LeesUitBrpAntwoordBericht queryResponse = new LeesUitBrpAntwoordBericht("dummy_correlation_id", builder.build());

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", berichtenDao.bewaarBericht(ii01Bericht));
        parameters.put("leesUitBrpAntwoordBericht", berichtenDao.bewaarBericht(queryResponse));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals(1, result.size());

        final If01Bericht if01Bericht = (If01Bericht) berichtenDao.leesBericht((Long) result.get("if01Bericht"));
        Assert.assertNotNull(if01Bericht);
        Assert.assertEquals("1234", if01Bericht.getDoelGemeente());
        Assert.assertEquals("5678", if01Bericht.getBronGemeente());
        Assert.assertEquals(ii01Bericht.getCategorieen(), if01Bericht.getCategorieen());
        Assert.assertEquals("G", if01Bericht.getHeader(Lo3HeaderVeld.FOUTREDEN));
        Assert.assertEquals("0000000000", if01Bericht.getHeader(Lo3HeaderVeld.A_NUMMER));
    }

    @Test
    public void testRni() {
        final Ii01Bericht ii01Bericht = new Ii01Bericht();
        ii01Bericht.setBronGemeente("1234");
        ii01Bericht.setDoelGemeente("5678");
        ii01Bericht.set(Lo3CategorieEnum.PERSOON, Lo3ElementEnum.ANUMMER, "1234567891");

        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        builder.persoonStapel(Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
            Lo3StapelHelper.lo3Persoon(1234567892L, "Jan", "Jaapersten", 19700101, "2134", "6030", "M"),
            Lo3StapelHelper.lo3Akt(1),
            Lo3StapelHelper.lo3His(19700101),
            new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 0))));
        builder.inschrijvingStapel(Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
            Lo3StapelHelper.lo3Inschrijving(null, null, "R", 19700101, "0053", 5, 1, 19700101124500000L, true),
            null,
            Lo3Historie.NULL_HISTORIE,
            new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_07, 0, 0))));
        final LeesUitBrpAntwoordBericht queryResponse = new LeesUitBrpAntwoordBericht("dummy_correlation_id", builder.build());

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", berichtenDao.bewaarBericht(ii01Bericht));
        parameters.put("leesUitBrpAntwoordBericht", berichtenDao.bewaarBericht(queryResponse));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals(1, result.size());

        final If01Bericht if01Bericht = (If01Bericht) berichtenDao.leesBericht((Long) result.get("if01Bericht"));
        Assert.assertNotNull(if01Bericht);
        Assert.assertEquals("1234", if01Bericht.getDoelGemeente());
        Assert.assertEquals("5678", if01Bericht.getBronGemeente());
        Assert.assertEquals(ii01Bericht.getCategorieen(), if01Bericht.getCategorieen());
        Assert.assertEquals("G", if01Bericht.getHeader(Lo3HeaderVeld.FOUTREDEN));
        Assert.assertEquals("0000000000", if01Bericht.getHeader(Lo3HeaderVeld.A_NUMMER));
    }

    @Test
    public void testMinistrieelBesluit() {
        final Ii01Bericht ii01Bericht = new Ii01Bericht();
        ii01Bericht.setBronGemeente("1234");
        ii01Bericht.setDoelGemeente("5678");
        ii01Bericht.set(Lo3CategorieEnum.PERSOON, Lo3ElementEnum.ANUMMER, "1234567891");

        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        builder.persoonStapel(Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
            Lo3StapelHelper.lo3Persoon(1234567892L, "Jan", "Jaapersten", 19700101, "2134", "6030", "M"),
            Lo3StapelHelper.lo3Akt(1),
            Lo3StapelHelper.lo3His(19700101),
            new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 0))));
        builder.inschrijvingStapel(Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
            Lo3StapelHelper.lo3Inschrijving(null, null, "M", 19700101, "0053", 5, 1, 19700101124500000L, true),
            null,
            Lo3Historie.NULL_HISTORIE,
            new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_07, 0, 0))));
        final LeesUitBrpAntwoordBericht queryResponse = new LeesUitBrpAntwoordBericht("dummy_correlation_id", builder.build());

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", berichtenDao.bewaarBericht(ii01Bericht));
        parameters.put("leesUitBrpAntwoordBericht", berichtenDao.bewaarBericht(queryResponse));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals(1, result.size());

        final If01Bericht if01Bericht = (If01Bericht) berichtenDao.leesBericht((Long) result.get("if01Bericht"));
        Assert.assertNotNull(if01Bericht);
        Assert.assertEquals("1234", if01Bericht.getDoelGemeente());
        Assert.assertEquals("5678", if01Bericht.getBronGemeente());
        Assert.assertEquals(ii01Bericht.getCategorieen(), if01Bericht.getCategorieen());
        Assert.assertEquals("M", if01Bericht.getHeader(Lo3HeaderVeld.FOUTREDEN));
        Assert.assertEquals("1234567892", if01Bericht.getHeader(Lo3HeaderVeld.A_NUMMER));
    }
}
