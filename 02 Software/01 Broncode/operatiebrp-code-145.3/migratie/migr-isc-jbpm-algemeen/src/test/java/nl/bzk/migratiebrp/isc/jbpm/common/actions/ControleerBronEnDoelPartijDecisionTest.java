/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.common.actions;

import edu.emory.mathcs.backport.java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import nl.bzk.migratiebrp.bericht.model.sync.register.Partij;
import org.junit.Assert;
import org.junit.Test;

public class ControleerBronEnDoelPartijDecisionTest {

    private final ControleerBronEnDoelPartijCodeDecision subject = new ControleerBronEnDoelPartijCodeDecision();

    @Test
    public void testOk() {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("bron", getBrpGemeente());
        parameters.put("doel", getGbaGemeente());

        final String result = subject.execute(parameters);

        Assert.assertEquals(null, result);
    }

    @Test
    public void testNokBronNull() {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("bron", null);
        parameters.put("doel", getGbaGemeente());

        final String result = subject.execute(parameters);

        Assert.assertEquals("Fout", result);
    }

    @Test
    public void testNokBronGba() {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("bron", getGbaGemeente());
        parameters.put("doel", getGbaGemeente());

        final String result = subject.execute(parameters);

        Assert.assertEquals("Fout", result);
    }

    @Test
    public void testNokDoelNull() {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("bron", getBrpGemeente());
        parameters.put("doel", null);

        final String result = subject.execute(parameters);

        Assert.assertEquals("Fout", result);
    }

    @Test
    public void testNokDoelBrp() {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("bron", getBrpGemeente());
        parameters.put("doel", getBrpGemeente());

        final String result = subject.execute(parameters);

        Assert.assertEquals("Fout", result);
    }

    /**
     * Geef de waarde van gba gemeente.
     * @return gba gemeente
     */
    private Partij getGbaGemeente() {
        return new Partij("0001", "580001", null, Collections.emptyList());
    }

    /**
     * Geef de waarde van brp gemeente.
     * @return brp gemeente
     */
    private Partij getBrpGemeente() {
        return new Partij("0001", "580001", new Date(System.currentTimeMillis() - 10000000000L), Collections.emptyList());
    }
}
