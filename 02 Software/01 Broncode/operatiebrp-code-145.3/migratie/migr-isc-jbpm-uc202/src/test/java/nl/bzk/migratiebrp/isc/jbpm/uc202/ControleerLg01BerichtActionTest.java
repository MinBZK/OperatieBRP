/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc202;

import edu.emory.mathcs.backport.java.util.Collections;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.BerichtSyntaxException;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Lg01Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.register.Partij;
import nl.bzk.migratiebrp.bericht.model.sync.register.PartijRegisterImpl;
import nl.bzk.migratiebrp.bericht.model.sync.register.Rol;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import nl.bzk.migratiebrp.isc.jbpm.common.TestPartijService;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.InMemoryBerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringActionHandler;
import nl.bzk.migratiebrp.register.client.PartijService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ControleerLg01BerichtActionTest {

    private ControleerLg01BerichtAction subject;
    private BerichtenDao berichtenDao;

    @Before
    public void setup() {
        berichtenDao = new InMemoryBerichtenDao();

        final List<Partij> partijen = new ArrayList<>();
        partijen.add(new Partij("059901", "0599", null, Arrays.asList(Rol.BIJHOUDINGSORGAAN_COLLEGE, Rol.AFNEMER)));
        partijen.add(new Partij("042901", "0429", null, Arrays.asList(Rol.BIJHOUDINGSORGAAN_COLLEGE, Rol.AFNEMER)));
        partijen.add(new Partij("069901", "0699", intToDate(2009_01_01), Arrays.asList(Rol.BIJHOUDINGSORGAAN_COLLEGE, Rol.AFNEMER)));
        partijen.add(new Partij("071701", "0717", null, Arrays.asList(Rol.BIJHOUDINGSORGAAN_COLLEGE, Rol.AFNEMER)));
        partijen.add(new Partij("199902", null, intToDate(2009_01_01), Collections.emptyList()));

        final PartijService partijService = new TestPartijService(new PartijRegisterImpl(partijen));

        subject = new ControleerLg01BerichtAction(partijService, berichtenDao);

    }

    private static Date intToDate(final int date) {
        try {
            return new SimpleDateFormat("yyyyMMdd").parse(Integer.toString(date));
        } catch (final ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Test
    public void testOk() {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", berichtenDao.bewaarBericht(maakLg01("123456789", "123456789", null, null, "059901", "0599")));

        final Map<String, Object> result = subject.execute(parameters);

        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void testOriginatorNull() {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", berichtenDao.bewaarBericht(maakLg01("123456789", "123456789", null, null, null, "0599")));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals("1b. Fout", result.get(SpringActionHandler.TRANSITION_RESULT));
        // Assert.assertEquals(ControleerLg01BerichtAction.PF_03,
        // result.get(ControleerLg01BerichtAction.VARIABLE_FOUTTYPE));
        // Assert.assertNotNull(result.get(ControleerLg01BerichtAction.VARIABLE_TOELICHTING));
    }

    @Test
    public void testOriginatorOnbekend() {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", berichtenDao.bewaarBericht(maakLg01("123456789", "123456789", null, null, "079901", "0799")));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals("1b. Fout", result.get(SpringActionHandler.TRANSITION_RESULT));
        // Assert.assertEquals(ControleerLg01BerichtAction.PF_03,
        // result.get(ControleerLg01BerichtAction.VARIABLE_FOUTTYPE));
        // Assert.assertNotNull(result.get(ControleerLg01BerichtAction.VARIABLE_TOELICHTING));
    }

    @Test
    public void testOriginatorBrp() {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", berichtenDao.bewaarBericht(maakLg01("123456789", "123456789", null, null, "069901", "0699")));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals("1b. Fout", result.get(SpringActionHandler.TRANSITION_RESULT));
        // Assert.assertEquals(ControleerLg01BerichtAction.PF_03,
        // result.get(ControleerLg01BerichtAction.VARIABLE_FOUTTYPE));
        // Assert.assertNotNull(result.get(ControleerLg01BerichtAction.VARIABLE_TOELICHTING));
    }

    @Test
    public void testANummerLeeg() {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", berichtenDao.bewaarBericht(maakLg01(null, "123456789", null, null, "059901", "0599")));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals("1b. Fout", result.get(SpringActionHandler.TRANSITION_RESULT));
    }

    @Test
    public void testANummerOngelijk() {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", berichtenDao.bewaarBericht(maakLg01("9878654321", "123456789", null, null, "059901", "0599")));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals("1b. Fout", result.get(SpringActionHandler.TRANSITION_RESULT));
    }

    @Test
    public void testOriginatorOngelijk() {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", berichtenDao.bewaarBericht(maakLg01("123456789", "123456789", null, null, "042901", "0599")));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals(null, result.get(SpringActionHandler.TRANSITION_RESULT));
    }

    private Lg01Bericht maakLg01(
            final String aNummerKop,
            final String aNummerInhoud,
            final String vorigANummerKop,
            final String vorigANummerInhoud,
            final String partijBericht,
            final String gemeenteInhoud) {

        final Lg01Bericht lg01 = new Lg01Bericht();
        lg01.setHeader(Lo3HeaderVeld.A_NUMMER, aNummerKop);
        lg01.setHeader(Lo3HeaderVeld.OUD_A_NUMMER, vorigANummerKop);
        lg01.setLo3Persoonslijst(maakPersoonslijst(aNummerInhoud, vorigANummerInhoud, gemeenteInhoud));
        lg01.setBronPartijCode(partijBericht);
        lg01.setDoelPartijCode("199902");

        final String formattedLg01 = lg01.format();

        try {
            lg01.parse(formattedLg01);
        } catch (
                BerichtSyntaxException
                        | BerichtInhoudException e) {
            e.printStackTrace();
            return null;
        }

        return lg01;
    }

    private Lo3Persoonslijst maakPersoonslijst(final String aNummerInhoud, final String vorigANummerInhoud, final String gemeenteInhoud) {

        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        // @formatter:off
        builder.persoonStapel(Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(Lo3StapelHelper.lo3Persoon(aNummerInhoud,
                null,
                "Jan",
                null,
                null,
                "Jansen",
                1970_01_01,
                "0518",
                "6030",
                "M",
                vorigANummerInhoud,
                null,
                "E"),
                Lo3StapelHelper.lo3Akt(1),
                Lo3StapelHelper.lo3His(1970_01_01),
                new Lo3Herkomst(Lo3CategorieEnum.PERSOON, 0, 0))));

        builder.verblijfplaatsStapel(Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(Lo3StapelHelper.lo3Verblijfplaats(gemeenteInhoud,
                1970_01_01,
                1970_01_01,
                "Straat",
                15,
                "9876AA",
                "I"),
                null,
                Lo3StapelHelper.lo3His(1970_01_01),
                new Lo3Herkomst(Lo3CategorieEnum.VERBLIJFPLAATS, 0, 0))));
        // @formatter:on

        return builder.build();
    }

}
