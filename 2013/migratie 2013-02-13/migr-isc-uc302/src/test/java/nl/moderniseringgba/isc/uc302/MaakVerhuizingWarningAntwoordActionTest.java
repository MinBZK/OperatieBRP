/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc302;

import java.util.HashMap;
import java.util.Map;

import nl.moderniseringgba.isc.esb.message.brp.generated.StatusType;
import nl.moderniseringgba.isc.esb.message.brp.impl.VerhuizingAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.brp.impl.VerhuizingVerzoekBericht;

import org.junit.Assert;
import org.junit.Test;

public class MaakVerhuizingWarningAntwoordActionTest {
    private final MaakVerhuizingWarningAntwoordAction subject = new MaakVerhuizingWarningAntwoordAction();

    @Test
    public void test() {
        final VerhuizingVerzoekBericht input = new VerhuizingVerzoekBericht();
        input.setANummer("8172387435");

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("input", input);

        final Map<String, Object> result = subject.execute(parameters);
        Assert.assertEquals(1, result.size());

        final VerhuizingAntwoordBericht verhuizingAntwoord =
                (VerhuizingAntwoordBericht) result.get("verhuizingAntwoord");
        Assert.assertEquals(StatusType.WAARSCHUWING, verhuizingAntwoord.getStatus());
    }
}
