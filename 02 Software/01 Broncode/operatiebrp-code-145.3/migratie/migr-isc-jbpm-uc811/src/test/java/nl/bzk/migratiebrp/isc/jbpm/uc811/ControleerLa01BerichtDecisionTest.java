/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc811;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.La01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Lq01Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.register.Partij;
import nl.bzk.migratiebrp.bericht.model.sync.register.PartijRegisterImpl;
import nl.bzk.migratiebrp.bericht.model.sync.register.Rol;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import nl.bzk.migratiebrp.isc.jbpm.common.TestPartijService;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.InMemoryBerichtenDao;
import nl.bzk.migratiebrp.register.client.PartijService;
import org.junit.Before;
import org.junit.Test;

public class ControleerLa01BerichtDecisionTest {

    private final BerichtenDao berichtenDao = new InMemoryBerichtenDao();
    private ControleerLa01BerichtDecision subject;

    private Lq01Bericht lq01;
    private Lq01Bericht lq01Rni;
    private La01Bericht la01;
    private La01Bericht la01Rni;

    @Before
    public void setup() {
        Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        builder.persoonStapel(Lo3StapelHelper.lo3Stapel(
                Lo3StapelHelper.lo3Cat(Lo3StapelHelper.lo3Persoon("1234567890", null, null, null, null, null, null), Lo3CategorieEnum.CATEGORIE_01)));
        builder.verblijfplaatsStapel(Lo3StapelHelper.lo3Stapel(
                Lo3StapelHelper.lo3Cat(Lo3StapelHelper.lo3Verblijfplaats("0626", null, null, null, null, null, null), null, new Lo3Historie(), null)));
        final Lo3Persoonslijst lo3Persoonslijst = builder.build();

        builder = new Lo3PersoonslijstBuilder();
        builder.persoonStapel(Lo3StapelHelper.lo3Stapel(
                Lo3StapelHelper.lo3Cat(Lo3StapelHelper.lo3Persoon("1234567890", null, null, null, null, null, null), Lo3CategorieEnum.CATEGORIE_01)));
        builder.verblijfplaatsStapel(Lo3StapelHelper.lo3Stapel(
                Lo3StapelHelper.lo3Cat(Lo3StapelHelper.lo3Verblijfplaats("1999", null, null, null, null, null, null), null, new Lo3Historie(), null)));
        final Lo3Persoonslijst lo3PersoonslijstRni = builder.build();

        lq01 = new Lq01Bericht();
        lq01.setANummer("1234567890");
        lq01.setDoelPartijCode("0626");
        lq01.setBronPartijCode("199902");

        lq01Rni = new Lq01Bericht();
        lq01Rni.setANummer("1234567890");
        lq01Rni.setDoelPartijCode("199901");
        lq01Rni.setBronPartijCode("199902");

        la01 = new La01Bericht();
        la01.setBronPartijCode("062601");
        la01.setDoelPartijCode("199902");
        la01.setCorrelationId(lq01.getMessageId());
        la01.setLo3Persoonslijst(lo3Persoonslijst);

        la01Rni = new La01Bericht();
        la01Rni.setBronPartijCode("199901");
        la01Rni.setDoelPartijCode("199902");
        la01Rni.setCorrelationId(lq01.getMessageId());
        la01Rni.setLo3Persoonslijst(lo3PersoonslijstRni);

        final List<Partij> partijen = new ArrayList<>();
        partijen.add(new Partij("062601", "0626", null, Arrays.asList(Rol.BIJHOUDINGSORGAAN_COLLEGE, Rol.AFNEMER)));
        partijen.add(new Partij("199901", null, null, Arrays.asList(Rol.BIJHOUDINGSORGAAN_COLLEGE, Rol.AFNEMER)));

        final PartijService partijService = new TestPartijService(new PartijRegisterImpl(partijen));

        subject = new ControleerLa01BerichtDecision(berichtenDao, partijService);
    }

    @Test
    public void testGeldig() {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("lq01Bericht", berichtenDao.bewaarBericht(lq01));
        parameters.put("la01Bericht", berichtenDao.bewaarBericht(la01));

        assertEquals("La01", subject.execute(parameters));
    }

    @Test
    public void testGeldigRni() {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("lq01Bericht", berichtenDao.bewaarBericht(lq01Rni));
        parameters.put("la01Bericht", berichtenDao.bewaarBericht(la01Rni));

        assertEquals("La01", subject.execute(parameters));
    }

    @Test
    public void testGeenLq01() {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("la01Bericht", berichtenDao.bewaarBericht(la01));

        assertEquals("3f. Fout", subject.execute(parameters));
    }

    @Test
    public void testGeenAnummer() {
        lq01.setANummer(null);

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("lq01Bericht", berichtenDao.bewaarBericht(lq01));
        parameters.put("la01Bericht", berichtenDao.bewaarBericht(la01));

        assertEquals("3f. Fout", subject.execute(parameters));
    }

    @Test
    public void testLeegAnummer() {
        lq01.setANummer("");

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("lq01Bericht", berichtenDao.bewaarBericht(lq01));
        parameters.put("la01Bericht", berichtenDao.bewaarBericht(la01));

        assertEquals("3f. Fout", subject.execute(parameters));
    }

    @Test
    public void testGeenLa01() {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("lq01Bericht", berichtenDao.bewaarBericht(lq01));

        assertEquals("3f. Fout", subject.execute(parameters));
    }

    @Test
    public void testGeenPersoonslijst() {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("lq01Bericht", berichtenDao.bewaarBericht(lq01));

        assertEquals("3f. Fout", subject.execute(parameters));
    }

    @Test
    public void testGeenOriginator() {
        la01.setBronPartijCode(null);

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("lq01Bericht", berichtenDao.bewaarBericht(lq01));
        parameters.put("la01Bericht", berichtenDao.bewaarBericht(la01));

        assertEquals("3f. Fout", subject.execute(parameters));
    }

    @Test
    public void testLegeOriginator() {
        la01.setBronPartijCode("");

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("lq01Bericht", berichtenDao.bewaarBericht(lq01));
        parameters.put("la01Bericht", berichtenDao.bewaarBericht(la01));

        assertEquals("3f. Fout", subject.execute(parameters));
    }
}
