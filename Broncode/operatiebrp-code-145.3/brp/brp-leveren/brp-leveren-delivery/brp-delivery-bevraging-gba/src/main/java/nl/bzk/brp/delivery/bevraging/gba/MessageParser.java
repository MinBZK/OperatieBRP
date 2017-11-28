/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.bevraging.gba;

import java.util.UUID;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import nl.bzk.algemeenbrp.util.common.serialisatie.JsonStringSerializer;
import nl.bzk.algemeenbrp.util.common.serialisatie.SerialisatieExceptie;
import org.springframework.jms.support.JmsUtils;

class MessageParser {
    private static final String BERICHT_REFERENTIE = "iscBerichtReferentie";
    private static final String CORRELATIE_REFERENTIE = "iscCorrelatieReferentie";

    private final JsonStringSerializer serializer = new JsonStringSerializer();
    private final Message message;

    MessageParser(final Message message) {
        this.message = message;
    }

    String getBerichtReferentie() {
        try {
            return message.getStringProperty(BERICHT_REFERENTIE);
        } catch (final JMSException e) {
            throw new AdhocVraagException("Kan berichtreferentie niet lezen", e);
        }
    }

    <T> T parseVerzoek(final Class<T> clazz) {
        try {
            return serializer.deserialiseerVanuitString(getBericht(), clazz);
        } catch (final SerialisatieExceptie e) {
            throw new AdhocVraagException("Kan bericht niet lezen.", e);
        }
    }

    <T> Message composeAntwoord(final T antwoord, final Session session) {
        try {
            final Message result = session.createTextMessage(serializer.serialiseerNaarString(antwoord));
            result.setStringProperty(BERICHT_REFERENTIE, UUID.randomUUID().toString());
            result.setStringProperty(CORRELATIE_REFERENTIE, getBerichtReferentie());
            return result;
        } catch (JMSException e) {
            throw JmsUtils.convertJmsAccessException(e);
        }
    }

    private String getBericht() {
        try {
            return ((TextMessage) message).getText();
        } catch (final JMSException | ClassCastException e) {
            throw new AdhocVraagException("Kan bericht niet lezen.", e);
        }
    }

    /**
     * Exceptie bij ad hoc vragen.
     */
    static final class AdhocVraagException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        /**
         * Constructor.
         * @param message melding van exceptie
         * @param cause grondreden voor deze exceptie
         */
        AdhocVraagException(final String message, final Throwable cause) {
            super(message, cause);
        }
    }
}
