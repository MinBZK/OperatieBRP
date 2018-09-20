/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc202;

import java.util.HashMap;
import java.util.Map;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.bericht.model.lo3.format.Lo3PersoonslijstFormatter;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Lg01Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.BlokkeringInfoVerzoekBericht;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Long;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.InMemoryBerichtenDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class MaakBlokkeringInfoActionTest {

    private static final Lo3PersoonslijstFormatter LO3_FORMATTER = new Lo3PersoonslijstFormatter();

    private MaakBlokkeringInfoAction subject;
    private BerichtenDao berichtenDao;

    @Before
    public void setup() {
        subject = new MaakBlokkeringInfoAction();
        berichtenDao = new InMemoryBerichtenDao();
        ReflectionTestUtils.setField(subject, "berichtenDao", berichtenDao);
    }

    @Test
    public void test() {
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        builder.persoonStapel(Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
            Lo3StapelHelper.lo3Persoon(1234567892L, "Jaap", "Pietersen", 19770101, "0512", "6030", "M"),
            Lo3StapelHelper.lo3Akt(1),
            Lo3StapelHelper.lo3His(20040101),
            new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 0))));
        final Lg01Bericht lg01Bericht = new Lg01Bericht();

        final Lo3Persoonslijst lo3Pl = builder.build();

        // String formattedPl =
        lg01Bericht.setCategorieen(LO3_FORMATTER.format(lo3Pl));
        lg01Bericht.setHeader(Lo3HeaderVeld.DATUM_TIJD, "00000000000000000");

        final Lo3PersoonInhoud persoon = lo3Pl.getPersoonStapel().getLaatsteElement().getInhoud();
        lg01Bericht.setHeader(Lo3HeaderVeld.A_NUMMER, asString(persoon.getANummer()));
        lg01Bericht.setHeader(Lo3HeaderVeld.OUD_A_NUMMER, asString(persoon.getVorigANummer()));

        lg01Bericht.setMessageId("messageId");
        lg01Bericht.setDoelGemeente("0599");

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", berichtenDao.bewaarBericht(lg01Bericht));

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals(1, result.size());

        final BlokkeringInfoVerzoekBericht blokkeringInfoBericht =
                (BlokkeringInfoVerzoekBericht) berichtenDao.leesBericht((Long) result.get("blokkeringInfoBericht"));
        Assert.assertNotNull(blokkeringInfoBericht);
        Assert.assertEquals("1234567892", blokkeringInfoBericht.getANummer());
    }

    protected static String asString(final Lo3Long value) {
        if (value == null || !value.isInhoudelijkGevuld()) {
            return null;
        } else {
            return String.valueOf(Lo3Long.unwrap(value));
        }
    }
}
