/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.regels;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.brp.domain.algemeen.Melding;
import org.junit.Before;
import org.junit.Test;

public class RegelcodeVertalerTest {

    private RegelcodeVertaler subject;

    @Before
    public void setup() {
        subject = new GbaRegelcodeVertaler();
    }

    @Test
    public void enkeleRegel() {
        assertEquals(Optional.of('X').map(Object::toString), subject.bepaalFoutcode(Collections.singletonList(new Melding(Regel.R2290))).map(Object::toString));
    }

    @Test
    public void meerdereRegels() {
        assertEquals(Optional.of('U').map(Object::toString),
                subject.bepaalFoutcode(Arrays.asList(new Melding(Regel.R2289), new Melding(Regel.R2290))).map(Object::toString));
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
