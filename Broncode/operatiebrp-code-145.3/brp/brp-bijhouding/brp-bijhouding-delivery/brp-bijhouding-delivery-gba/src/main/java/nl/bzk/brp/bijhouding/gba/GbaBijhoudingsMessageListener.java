/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.gba;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.UUID;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.Validator;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingAntwoordBericht;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingVerzoekBericht;
import nl.bzk.brp.bijhouding.bericht.parser.BijhoudingVerzoekBerichtParser;
import nl.bzk.brp.bijhouding.bericht.parser.ParseException;
import nl.bzk.brp.bijhouding.bericht.validation.SchemaValidationHelper;
import nl.bzk.brp.bijhouding.bericht.writer.BijhoudingAntwoordBerichtWriter;
import nl.bzk.brp.bijhouding.bericht.writer.WriteException;
import nl.bzk.brp.bijhouding.business.BijhoudingService;
import org.springframework.jms.core.JmsTemplate;
import org.xml.sax.SAXException;

/**
 * MessageListener voor het afhandelen van GBA bijhoudingsvoorstellen.
 */
public final class GbaBijhoudingsMessageListener implements MessageListener {

    private static final String BERICHT_REFERENTIE = "iscBerichtReferentie";
    private static final String CORRELATIE_REFERENTIE = "iscCorrelatieReferentie";

    private final BijhoudingService bijhoudingService;
    private final JmsTemplate gbaBijhoudingAntwoordTemplate;

    /**
     * Constructor voor de message listener.
     * @param bijhoudingService de bijhouding service
     * @param gbaBijhoudingAntwoordTemplate JMS template
     */
    @Inject
    public GbaBijhoudingsMessageListener(final BijhoudingService bijhoudingService, final JmsTemplate gbaBijhoudingAntwoordTemplate) {
        this.bijhoudingService = bijhoudingService;
        this.gbaBijhoudingAntwoordTemplate = gbaBijhoudingAntwoordTemplate;
    }

    @Override
    public void onMessage(final Message message) {
        antwoord(bijhoudingService.verwerkGbaBericht(leesBericht(message)), leesBerichtReferentie(message));
    }

    private void antwoord(final BijhoudingAntwoordBericht antwoordBericht, final String berichtReferentie) {
        gbaBijhoudingAntwoordTemplate.send(session -> {
            final Message result = session.createTextMessage(write(antwoordBericht));
            result.setStringProperty(BERICHT_REFERENTIE, UUID.randomUUID().toString());
            result.setStringProperty(CORRELATIE_REFERENTIE, berichtReferentie);
            return result;
        });
    }

    private static BijhoudingVerzoekBericht leesBericht(final Message message) {
        try {
            return parse(((TextMessage) message).getText());
        } catch (final JMSException | ClassCastException e) {
            throw new VerzendOntvangstException("Kan bericht niet lezen.", e);
        }
    }

    private static String leesBerichtReferentie(final Message message) {
        try {
            return message.getStringProperty(BERICHT_REFERENTIE);
        } catch (final JMSException e) {
            throw new VerzendOntvangstException("Kan berichtreferentie niet lezen", e);
        }
    }

    private static BijhoudingVerzoekBericht parse(final String bericht) {
        valideer(bericht);
        try {
            final BijhoudingVerzoekBericht result = new BijhoudingVerzoekBerichtParser().parse(new ByteArrayInputStream(bericht.getBytes("UTF-8")));
            result.setXml(bericht);
            return result;
        } catch (final ParseException | UnsupportedEncodingException e) {
            throw new IllegalArgumentException("Fout bij het lezen van het verzoek bericht.", e);
        }
    }

    private static void valideer(final String bericht) {
        try {
            final Schema schema = SchemaValidationHelper.getIscSchema();
            final Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new ByteArrayInputStream(bericht.getBytes("UTF-8"))));
        } catch (final SAXException | IOException e) {
            throw new IllegalArgumentException("Fout bij het valideren van het verzoek bericht.", e);
        }
    }

    private static String write(final BijhoudingAntwoordBericht antwoordBericht) {
        try {
            final StringWriter stringWriter = new StringWriter();
            new BijhoudingAntwoordBerichtWriter().write(antwoordBericht, stringWriter);
            return stringWriter.toString();
        } catch (final WriteException e) {
            throw new IllegalArgumentException("Fout bij het maken van het antwoord bericht.", e);
        }
    }

    private static final class VerzendOntvangstException extends RuntimeException {
        private VerzendOntvangstException(final String message, final Throwable cause) {
            super(message, cause);
        }
    }
}
