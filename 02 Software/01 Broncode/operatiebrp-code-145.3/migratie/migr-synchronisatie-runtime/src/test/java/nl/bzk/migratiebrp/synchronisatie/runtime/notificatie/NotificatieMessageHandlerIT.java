/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.notificatie;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import nl.bzk.migratiebrp.bericht.model.JMSConstants;
import nl.bzk.migratiebrp.synchronisatie.runtime.AbstractIT;
import nl.bzk.migratiebrp.synchronisatie.runtime.Main;
import org.junit.Assert;
import org.junit.Test;

/**
 */
public class NotificatieMessageHandlerIT extends AbstractIT {

    public NotificatieMessageHandlerIT() {
        super(Main.Modus.SYNCHRONISATIE);
    }

    @Test
    public void ontvangNotificatie() throws JMSException {
        putBrpMessage("GbaNotificaties", "Testbericht", "ref-123");
        Message bericht = expectMessage("notificatie");
        if (bericht instanceof TextMessage) {
            Assert.assertEquals("Testbericht", ((TextMessage) bericht).getText());
            Assert.assertEquals("ref-123", bericht.getStringProperty(JMSConstants.BERICHT_REFERENTIE));
        } else {
            Assert.fail();
        }
    }
}