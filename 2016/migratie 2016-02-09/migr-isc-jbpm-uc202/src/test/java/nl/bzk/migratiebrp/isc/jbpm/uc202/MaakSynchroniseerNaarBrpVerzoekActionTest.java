/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc202;

import java.util.HashMap;
import java.util.Map;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Lg01Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpVerzoekBericht;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.InMemoryBerichtenDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class MaakSynchroniseerNaarBrpVerzoekActionTest {

    private MaakSynchroniseerNaarBrpVerzoekAction subject;
    private BerichtenDao berichtenDao;

    @Before
    public void setup() {
        subject = new MaakSynchroniseerNaarBrpVerzoekAction();
        berichtenDao = new InMemoryBerichtenDao();
        ReflectionTestUtils.setField(subject, "berichtenDao", berichtenDao);
    }

    @Test
    public void testToevoegen() {
        final Lg01Bericht lg01Bericht = new Lg01Bericht();
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        builder.persoonStapel(Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(Lo3StapelHelper.lo3Persoon(
            1234567892L,
            123456789,
            "Jaap",
            null,
            null,
            "Pietersen",
            19770101,
            "0512",
            "6030",
            "M",
            1231231232L,
            4564564564L,
            "E"), Lo3StapelHelper.lo3Akt(1), Lo3StapelHelper.lo3His(20040101), new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 0))));
        final Lo3Persoonslijst pl = builder.build();
        lg01Bericht.setLo3Persoonslijst(pl);

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", berichtenDao.bewaarBericht(lg01Bericht));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals(1, result.size());

        final SynchroniseerNaarBrpVerzoekBericht synchroniseerNaarBrpVerzoekBericht =
                (SynchroniseerNaarBrpVerzoekBericht) berichtenDao.leesBericht((Long) result.get("synchroniseerNaarBrpVerzoekBericht"));
        Assert.assertNotNull(synchroniseerNaarBrpVerzoekBericht);
        Assert.assertEquals(pl, synchroniseerNaarBrpVerzoekBericht.getLo3Persoonslijst());
    }

    @Test
    public void testANummerWijziging() {
        final Lg01Bericht lg01Bericht = new Lg01Bericht();
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        builder.persoonStapel(Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(Lo3StapelHelper.lo3Persoon(
            1234567892L,
            123456789,
            "Jaap",
            null,
            null,
            "Pietersen",
            19770101,
            "0512",
            "6030",
            "M",
            1231231232L,
            4564564564L,
            "E"), Lo3StapelHelper.lo3Akt(1), Lo3StapelHelper.lo3His(20040101), new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 0))));
        final Lo3Persoonslijst pl = builder.build();
        lg01Bericht.setLo3Persoonslijst(pl);
        lg01Bericht.setHeader(Lo3HeaderVeld.OUD_A_NUMMER, "3456843580");

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", berichtenDao.bewaarBericht(lg01Bericht));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals(1, result.size());

        final SynchroniseerNaarBrpVerzoekBericht synchroniseerNaarBrpVerzoekBericht =
                (SynchroniseerNaarBrpVerzoekBericht) berichtenDao.leesBericht((Long) result.get("synchroniseerNaarBrpVerzoekBericht"));
        Assert.assertNotNull(synchroniseerNaarBrpVerzoekBericht);
        Assert.assertEquals(pl, synchroniseerNaarBrpVerzoekBericht.getLo3Persoonslijst());
        Assert.assertTrue(synchroniseerNaarBrpVerzoekBericht.isAnummerWijziging());
    }

    @Test
    public void testANummerWijzigingLeegOudANummer() {
        final Lg01Bericht lg01Bericht = new Lg01Bericht();
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        builder.persoonStapel(Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(Lo3StapelHelper.lo3Persoon(
            1234567892L,
            123456789,
            "Jaap",
            null,
            null,
            "Pietersen",
            19770101,
            "0512",
            "6030",
            "M",
            1231231232L,
            4564564564L,
            "E"), Lo3StapelHelper.lo3Akt(1), Lo3StapelHelper.lo3His(20040101), new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 0))));
        final Lo3Persoonslijst pl = builder.build();
        lg01Bericht.setLo3Persoonslijst(pl);
        lg01Bericht.setHeader(Lo3HeaderVeld.OUD_A_NUMMER, "0000000000");

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", berichtenDao.bewaarBericht(lg01Bericht));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals(1, result.size());

        final SynchroniseerNaarBrpVerzoekBericht synchroniseerNaarBrpVerzoekBericht =
                (SynchroniseerNaarBrpVerzoekBericht) berichtenDao.leesBericht((Long) result.get("synchroniseerNaarBrpVerzoekBericht"));
        Assert.assertNotNull(synchroniseerNaarBrpVerzoekBericht);
        Assert.assertEquals(pl, synchroniseerNaarBrpVerzoekBericht.getLo3Persoonslijst());
        Assert.assertFalse(synchroniseerNaarBrpVerzoekBericht.isAnummerWijziging());
    }

    @Test
    public void testBeheerderKeuzeNieuw() {
        final Lg01Bericht lg01Bericht = new Lg01Bericht();
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        builder.persoonStapel(Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(Lo3StapelHelper.lo3Persoon(
            1234567892L,
            123456789,
            "Jaap",
            null,
            null,
            "Pietersen",
            19770101,
            "0512",
            "6030",
            "M",
            1231231232L,
            4564564564L,
            "E"), Lo3StapelHelper.lo3Akt(1), Lo3StapelHelper.lo3His(20040101), new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 0))));
        final Lo3Persoonslijst pl = builder.build();
        lg01Bericht.setLo3Persoonslijst(pl);

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", berichtenDao.bewaarBericht(lg01Bericht));
        parameters.put(VerwerkBeheerderkeuzeAction.VARIABLE_INDICATIE_NIEUW, true);

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals(1, result.size());

        final SynchroniseerNaarBrpVerzoekBericht synchroniseerNaarBrpVerzoekBericht =
                (SynchroniseerNaarBrpVerzoekBericht) berichtenDao.leesBericht((Long) result.get("synchroniseerNaarBrpVerzoekBericht"));
        Assert.assertNotNull(synchroniseerNaarBrpVerzoekBericht);
        Assert.assertEquals(pl, synchroniseerNaarBrpVerzoekBericht.getLo3Persoonslijst());
        Assert.assertTrue(synchroniseerNaarBrpVerzoekBericht.getOpnemenAlsNieuwePl());
    }

    @Test
    public void testBeheerderKeuzeVervang() {
        final Lg01Bericht lg01Bericht = new Lg01Bericht();
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        builder.persoonStapel(Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(Lo3StapelHelper.lo3Persoon(
            1234567892L,
            123456789,
            "Jaap",
            null,
            null,
            "Pietersen",
            19770101,
            "0512",
            "6030",
            "M",
            1231231232L,
            4564564564L,
            "E"), Lo3StapelHelper.lo3Akt(1), Lo3StapelHelper.lo3His(20040101), new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 0))));
        final Lo3Persoonslijst pl = builder.build();
        lg01Bericht.setLo3Persoonslijst(pl);

        final Long anummerTeVervangenPl = Long.valueOf("8641321645");

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", berichtenDao.bewaarBericht(lg01Bericht));
        parameters.put(VerwerkBeheerderkeuzeAction.VARIABLE_INDICATIE_VERVANG, anummerTeVervangenPl);

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals(1, result.size());

        final SynchroniseerNaarBrpVerzoekBericht synchroniseerNaarBrpVerzoekBericht =
                (SynchroniseerNaarBrpVerzoekBericht) berichtenDao.leesBericht((Long) result.get("synchroniseerNaarBrpVerzoekBericht"));
        Assert.assertNotNull(synchroniseerNaarBrpVerzoekBericht);
        Assert.assertEquals(pl, synchroniseerNaarBrpVerzoekBericht.getLo3Persoonslijst());
        Assert.assertEquals(anummerTeVervangenPl, synchroniseerNaarBrpVerzoekBericht.getANummerTeVervangenPl());
    }

}
