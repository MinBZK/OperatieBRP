/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.selectie.publicatie;

import java.util.function.Supplier;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.service.algemeen.BrpServiceRuntimeException;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsOperations;
import org.springframework.jms.core.ProducerCallback;

/**
 *
 */
final class PublicatieHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private PublicatieHelper() {}

    static void publiceer(JmsOperations jmsOperations,
                           ProducerCallback<Void> producerCallback,
                           Supplier<String> errorMessage) {

        try {
            jmsOperations.execute(producerCallback);
        } catch (final JmsException e) {
            LOGGER.error(errorMessage.get(), e);
            throw new BrpServiceRuntimeException(e);
        }
    }
}
