/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc309;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.bzk.migratiebrp.bericht.model.lo3.impl.Tb02Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Tf21Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.generated.FoutredenType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwerkToevalligeGebeurtenisAntwoordBericht;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.InMemoryBerichtenDao;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

/**
 *
 */
public class MaakTf21AntwoordBerichtActionTest {

    private final Tb02Factory tb02Factory = new Tb02Factory();
    private MaakTf21AntwoordBerichtAction subject;
    private BerichtenDao berichtenDao;

    @Before
    public void setUp() throws Exception {
        subject = new MaakTf21AntwoordBerichtAction();
        berichtenDao = new InMemoryBerichtenDao();
        ReflectionTestUtils.setField(subject, "berichtenDao", berichtenDao);
    }

    @Test
    public void testMaakTf21Bericht() throws Exception {
        final Tb02Bericht bericht = tb02Factory.maakTb02Bericht(Tb02Factory.Soort.SLUITING);
        bericht.setDoelGemeente("2222");
        bericht.setMessageId("12345");
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("input", berichtenDao.bewaarBericht(bericht));

        final VerwerkToevalligeGebeurtenisAntwoordBericht antwoord = new VerwerkToevalligeGebeurtenisAntwoordBericht();
        antwoord.setStatus(StatusType.FOUT);
        antwoord.setFoutreden(FoutredenType.B);
        parameters.put("verwerkToevalligeGebeurtenisAntwoordBericht", berichtenDao.bewaarBericht(antwoord));

        final Map<String, Object> result = subject.execute(parameters);
        assertFalse("Resultaat mag niet leeg zijn", result.isEmpty());
        final Tf21Bericht tf21Bericht = (Tf21Bericht) berichtenDao.leesBericht((Long) result.get("tf21Bericht"));
        assertEquals(
            "Doelgemeente dient gelijk te zijn aan de brongemeente van het tb02 bericht",
            bericht.getBronGemeente(),
            tf21Bericht.getDoelGemeente());
        assertEquals(
                "Brongemeente dient gelijk te zijn aan de doelgemeente van het tb02 bericht",
                bericht.getDoelGemeente(),
                tf21Bericht.getBronGemeente());
        assertEquals("Het correlatieId moet gelijk zijn aan het messageId van het tb02 bericht", bericht.getMessageId(), tf21Bericht.getCorrelationId());

        final Method formatInhoud = tf21Bericht.getClass().getDeclaredMethod("formatInhoud");
        formatInhoud.setAccessible(true);
        List<Lo3CategorieWaarde> waarden = (List<Lo3CategorieWaarde>) formatInhoud.invoke(tf21Bericht);
        assertEquals("Aantal categoriewaarden moet 2 zijn bij sluiting", 2, waarden.size());
    }
}
