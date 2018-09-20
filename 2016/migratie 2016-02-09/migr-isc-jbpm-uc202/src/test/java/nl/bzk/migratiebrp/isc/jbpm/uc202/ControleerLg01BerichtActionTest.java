/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc202;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.BerichtSyntaxException;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Lg01Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.register.Gemeente;
import nl.bzk.migratiebrp.bericht.model.sync.register.GemeenteRegisterImpl;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import nl.bzk.migratiebrp.isc.jbpm.common.TestGemeenteService;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.InMemoryBerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringActionHandler;
import nl.bzk.migratiebrp.register.client.GemeenteService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class ControleerLg01BerichtActionTest {

    private ControleerLg01BerichtAction subject;
    private BerichtenDao berichtenDao;
    private GemeenteService gemeenteService;

    @Before
    public void setup() {
        subject = new ControleerLg01BerichtAction();
        berichtenDao = new InMemoryBerichtenDao();
        ReflectionTestUtils.setField(subject, "berichtenDao", berichtenDao);

        final List<Gemeente> gemeenten = new ArrayList<>();
        gemeenten.add(new Gemeente("0599", "580599", null));
        gemeenten.add(new Gemeente("0429", "580429", null));
        gemeenten.add(new Gemeente("0699", "580699", intToDate(20090101)));
        gemeenten.add(new Gemeente("0717", "580717", null));
        gemeenteService = new TestGemeenteService(new GemeenteRegisterImpl(gemeenten));

        ReflectionTestUtils.setField(subject, "gemeenteRegisterService", gemeenteService);

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
        parameters.put("input", berichtenDao.bewaarBericht(maakLg01(123456789L, 123456789L, null, null, "0599", "0599")));

        final Map<String, Object> result = subject.execute(parameters);

        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void testOriginatorNull() {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", berichtenDao.bewaarBericht(maakLg01(123456789L, 123456789L, null, null, null, "0599")));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals("2b. Fout", result.get(SpringActionHandler.TRANSITION_RESULT));
        // Assert.assertEquals(ControleerLg01BerichtAction.PF_03,
        // result.get(ControleerLg01BerichtAction.VARIABLE_FOUTTYPE));
        // Assert.assertNotNull(result.get(ControleerLg01BerichtAction.VARIABLE_TOELICHTING));
    }

    @Test
    public void testOriginatorOnbekend() {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", berichtenDao.bewaarBericht(maakLg01(123456789L, 123456789L, null, null, "0799", "0799")));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals("2b. Fout", result.get(SpringActionHandler.TRANSITION_RESULT));
        // Assert.assertEquals(ControleerLg01BerichtAction.PF_03,
        // result.get(ControleerLg01BerichtAction.VARIABLE_FOUTTYPE));
        // Assert.assertNotNull(result.get(ControleerLg01BerichtAction.VARIABLE_TOELICHTING));
    }

    @Test
    public void testOriginatorBrp() {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", berichtenDao.bewaarBericht(maakLg01(123456789L, 123456789L, null, null, "0699", "0699")));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals("2b. Fout", result.get(SpringActionHandler.TRANSITION_RESULT));
        // Assert.assertEquals(ControleerLg01BerichtAction.PF_03,
        // result.get(ControleerLg01BerichtAction.VARIABLE_FOUTTYPE));
        // Assert.assertNotNull(result.get(ControleerLg01BerichtAction.VARIABLE_TOELICHTING));
    }

    @Test
    public void testANummerLeeg() {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", berichtenDao.bewaarBericht(maakLg01(null, 123456789L, null, null, "0599", "0599")));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals("2b. Fout", result.get(SpringActionHandler.TRANSITION_RESULT));
    }

    @Test
    public void testANummerOngelijk() {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", berichtenDao.bewaarBericht(maakLg01(9878654321L, 123456789L, null, null, "0599", "0599")));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals("2b. Fout", result.get(SpringActionHandler.TRANSITION_RESULT));
    }

    @Test
    public void testVorigANummerNull() {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", berichtenDao.bewaarBericht(maakLg01(123456789L, 123456789L, 123123123L, null, "0599", "0599")));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals("2b. Fout", result.get(SpringActionHandler.TRANSITION_RESULT));
    }

    @Test
    public void testVorigANummerOngelijk() {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", berichtenDao.bewaarBericht(maakLg01(123456789L, 123456789L, 123123123L, 456456456L, "0599", "0599")));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals("2b. Fout", result.get(SpringActionHandler.TRANSITION_RESULT));
    }

    @Test
    public void testOriginatorOngelijk() {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", berichtenDao.bewaarBericht(maakLg01(123456789L, 123456789L, null, null, "0699", "0599")));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals("2b. Fout", result.get(SpringActionHandler.TRANSITION_RESULT));
    }

    private Lg01Bericht maakLg01(
        final Long aNummerKop,
        final Long aNummerInhoud,
        final Long vorigANummerKop,
        final Long vorigANummerInhoud,
        final String gemeenteKop,
        final String gemeenteInhoud)
    {

        final Lg01Bericht lg01 = new Lg01Bericht();
        lg01.setHeader(Lo3HeaderVeld.A_NUMMER, aNummerKop == null ? null : aNummerKop.toString());
        lg01.setHeader(Lo3HeaderVeld.OUD_A_NUMMER, vorigANummerKop == null ? null : vorigANummerKop.toString());
        lg01.setLo3Persoonslijst(maakPersoonslijst(aNummerInhoud, vorigANummerInhoud, gemeenteInhoud));
        lg01.setBronGemeente(gemeenteKop);
        lg01.setDoelGemeente("3000200");

        final String formattedLg01 = lg01.format();

        try {
            lg01.parse(formattedLg01);
        } catch (
            BerichtSyntaxException
            | BerichtInhoudException e)
        {
            e.printStackTrace();
            return null;
        }

        return lg01;
    }

    private Lo3Persoonslijst maakPersoonslijst(final Long aNummerInhoud, final Long vorigANummerInhoud, final String gemeenteInhoud) {

        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        // @formatter:off
        builder.persoonStapel(Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(Lo3StapelHelper.lo3Persoon(aNummerInhoud,
                                                                                                          null,
                                                                                                          "Jan",
                                                                                                          null,
                                                                                                          null,
                                                                                                          "Jansen",
                                                                                                          19700101,
                                                                                                          "0518",
                                                                                                          "6030",
                                                                                                          "M",
                                                                                                          vorigANummerInhoud,
                                                                                                          null,
                                                                                                          "E"),
                                                                               Lo3StapelHelper.lo3Akt(1),
                                                                               Lo3StapelHelper.lo3His(19700101),
                                                                               new Lo3Herkomst(Lo3CategorieEnum.PERSOON, 0, 0))));

        builder.verblijfplaatsStapel(Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(Lo3StapelHelper.lo3Verblijfplaats(gemeenteInhoud,
                                                                                                                        1970101,
                                                                                                                        1970101,
                                                                                                                        "Straat",
                                                                                                                        15,
                                                                                                                        "9876AA",
                                                                                                                        "I"),
                                                                                      null,
                                                                                      Lo3StapelHelper.lo3His(19700101),
                                                                                      new Lo3Herkomst(Lo3CategorieEnum.VERBLIJFPLAATS, 0, 0))));
        // @formatter:on

        return builder.build();
    }

}
