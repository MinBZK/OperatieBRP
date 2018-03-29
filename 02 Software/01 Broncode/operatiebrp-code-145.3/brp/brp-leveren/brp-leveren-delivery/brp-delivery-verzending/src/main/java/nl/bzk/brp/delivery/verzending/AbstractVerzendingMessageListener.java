/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.verzending;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;

/**
 * Message listener interface voor verzending. Deze class wordt
 */
abstract class AbstractVerzendingMessageListener implements MessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Override
    public void onMessage(final Message message) {
        BrpNu.set(DatumUtil.nuAlsZonedDateTime());
        LOGGER.debug("onMessage");
        if (!(message instanceof TextMessage)) {
            LOGGER.error("Onverwacht berichttype. {} Dit bericht wordt genegeerd!", message);
            return;
        }

        String text;
        try {
            text = ((TextMessage) message).getText();
            LOGGER.debug("Binnenkomend {}: {}", getInkomendBerichtLogMsg(), text);
        } catch (JMSException e) {
            throw new VerzendExceptie(String.format("Deserialisatiefout : Het ontvangen van een JMS bericht met een %s is mislukt", getInkomendBerichtLogMsg()),
                    e);
        }

        try {
            if (message.getJMSRedelivered()) {
                LOGGER.info("Redelivery {}: messageID={}",
                        getInkomendBerichtLogMsg(), message.getJMSMessageID());
            }
        } catch (JMSException e) {
            throw new VerzendExceptie("Kan JMS property niet lezen", e);
        }

        verzendBericht(text);
    }

    /**
     * Verzend (gedeserialiseerd) bericht.
     * @param message message text
     */
    abstract void verzendBericht(final String message);

    /**
     * Geeft soort bericht voor logging doeleinden.
     * @return soort bericht
     */
    abstract String getInkomendBerichtLogMsg();

}
