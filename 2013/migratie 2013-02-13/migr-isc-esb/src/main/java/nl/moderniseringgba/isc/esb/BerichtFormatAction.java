/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb;

import nl.moderniseringgba.isc.esb.message.Bericht;
import nl.moderniseringgba.isc.esb.message.BerichtInhoudException;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3HeaderVeld;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.jboss.soa.esb.actions.ActionLifecycleException;
import org.jboss.soa.esb.actions.ActionPipelineProcessor;
import org.jboss.soa.esb.actions.ActionProcessingException;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Body;
import org.jboss.soa.esb.message.Message;
import org.jboss.soa.esb.message.Properties;

/**
 * ESB Action die verantwoordelijk is voor het omzetten van de OBJECT message naar een STRING message.
 */
public final class BerichtFormatAction implements ActionPipelineProcessor {

    private static final Logger LOG = LoggerFactory.getLogger();

    /**
     * Constructor (verplicht voor Jboss ESB).
     * 
     * @param configTree
     *            configuratie
     */
    public BerichtFormatAction(final ConfigTree configTree) {
        // Geen actie
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    @Override
    public Message process(final Message message) throws ActionProcessingException {
        LOG.debug("process: {}", message);

        final Properties properties = message.getProperties();
        final Body body = message.getBody();

        // Bericht
        final Bericht bericht = (Bericht) body.get();

        // Zet message id en correlatie id
        if (bericht.getMessageId() == null) {
            throw new IllegalArgumentException("Uitgaand bericht heeft geen ID.");
        }

        properties.setProperty(EsbConstants.PROPERTY_MESSAGE_ID, bericht.getMessageId());

        if (bericht.getCorrelationId() != null) {
            properties.setProperty(EsbConstants.PROPERTY_CORRELATIE_ID, bericht.getCorrelationId());
        }

        // Zet naam (voor audit)
        properties.setProperty(EsbConstants.PROPERTY_NAAM, bericht.getBerichtType());

        // Bron en doel gemeente op LO3 bericht
        if (bericht instanceof Lo3Bericht) {
            final Lo3Bericht lo3Bericht = (Lo3Bericht) bericht;

            // Bron gemeente
            final String bronGemeente = lo3Bericht.getBronGemeente();
            if (bronGemeente != null) {
                properties.setProperty(EsbConstants.PROPERTY_BRON_GEMEENTE, bronGemeente);
            }

            // Doel gemeente
            final String doelGemeente = lo3Bericht.getDoelGemeente();
            if (doelGemeente != null) {
                properties.setProperty(EsbConstants.PROPERTY_DOEL_GEMEENTE, doelGemeente);
            }

            // herhaling (voor audit)
            final String herhalingHeader = lo3Bericht.getHeader(Lo3HeaderVeld.HERHALING);
            if (herhalingHeader != null && !"".equals(herhalingHeader)) {
                properties.setProperty(EsbConstants.PROPERTY_HERHALING, Integer.valueOf(herhalingHeader));
            }
        }

        // Format
        String berichtAlsString;
        try {
            berichtAlsString = bericht.format();
        } catch (final BerichtInhoudException e) {
            throw new IllegalArgumentException("Uitgaand bericht format probleem.", e);
        }

        body.add(berichtAlsString);
        return message;
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    @Override
    public void initialise() throws ActionLifecycleException {
        // Geen actie
    }

    @Override
    public void destroy() throws ActionLifecycleException {
        // Geen actie
    }

    @Override
    public void processException(final Message arg0, final Throwable arg1) {
        // Geen actie
    }

    @Override
    public void processSuccess(final Message arg0) {
        // Geen actie
    }

}
