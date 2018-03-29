/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.gba.domain.vrijbericht;


import org.junit.Assert;
import org.junit.Test;

public class VrijBerichtQueueTest {

    @Test
    public void test() {
        Assert.assertEquals(2, VrijBerichtQueue.values().length);
        Assert.assertEquals("GbaVrijeBerichten", VrijBerichtQueue.VERZOEK.getQueueNaam());
        Assert.assertEquals("GbaVrijeBerichtenAntwoorden", VrijBerichtQueue.ANTWOORD.getQueueNaam());
    }
}
