/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.bevraging.gba.ws.vertaler;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.brp.domain.algemeen.Melding;
import nl.bzk.brp.levering.lo3.conversie.regels.RegelcodeVertaler;
import org.junit.Before;
import org.junit.Test;

public class GbaWebserviceRegelcodeVertalerTest {

    private RegelcodeVertaler<AntwoordBerichtResultaat> subject;

    @Before
    public void setup() {
        subject = new GbaWebserviceRegelcodeVertaler();
    }

    @Test
    public void geenRegel() {
        assertEquals(Optional.of(AntwoordBerichtResultaat.OK), subject.bepaalFoutcode(Collections.emptyList()));
    }

    @Test
    public void enkeleRegel() {
        assertEquals(Optional.of(AntwoordBerichtResultaat.TECHNISCHE_FOUT_018), subject.bepaalFoutcode(Collections.singletonList(new Melding(Regel.R2290))));
    }

    @Test
    public void meerdereRegels() {
        assertEquals(Optional.of(AntwoordBerichtResultaat.TECHNISCHE_FOUT_032),
                subject.bepaalFoutcode(Arrays.asList(new Melding(Regel.R2289), new Melding(Regel.R2290))));
    }

    @Test(expected = IllegalArgumentException.class)
    public void geenConversieAanwezig() {
        subject.bepaalFoutcode(Collections.singletonList(new Melding(Regel.R2491)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void geenFoutcodeAanwezig() {
        subject.bepaalFoutcode(Collections.singletonList(new Melding(Regel.R1406)));
    }
}
