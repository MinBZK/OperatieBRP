/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;

import nl.bzk.migratiebrp.voisc.spd.exception.SpdProtocolException;

import org.junit.Test;

public class DeliveryReportTest {

    private DeliveryReport subject() throws SpdProtocolException {
        return DeliveryReport.fromOperationItems("1611031510Z123456789001RecipieMsgdelitimeReas");
    }

    @Test
    public void fromOperationItems() throws SpdProtocolException {
        assertThat("should have correct type", subject(), instanceOf(DeliveryReport.class));
    }

    @Test
    public void fromOperationItemsEmpty() throws SpdProtocolException {
        DeliveryReport record = DeliveryReport.fromOperationItems("1611031510Z123456789001Recipie           Reas");
        assertEquals("1611031510Z123456789001Recipie           Reas", record.toSpdString());
    }

    @Test
    public void toSpdString() throws SpdProtocolException {
        assertEquals("1611031510Z123456789001Recipie           Reas", subject().toSpdString());
    }

    @Test
    public void length() throws SpdProtocolException {
        assertEquals(48, subject().length());
    }

    @Test
    public void lengthField() throws SpdProtocolException {
        assertEquals("00048", subject().lengthField());
    }

    @Test
    public void operationCode() throws SpdProtocolException {
        assertEquals(260, subject().operationCode());
    }
}
