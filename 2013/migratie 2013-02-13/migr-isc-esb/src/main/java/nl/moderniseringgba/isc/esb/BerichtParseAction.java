/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb;

import nl.moderniseringgba.isc.esb.message.Bericht;
import nl.moderniseringgba.isc.esb.message.BerichtFactory;
import nl.moderniseringgba.isc.esb.message.brp.BrpBerichtFactory;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3BerichtFactory;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3HeaderVeld;
import nl.moderniseringgba.isc.esb.message.mvi.MviBerichtFactory;
import nl.moderniseringgba.isc.esb.message.sync.SyncBerichtFactory;
import nl.moderniseringgba.isc.jbpm.berichten.BerichtenDao;
import nl.moderniseringgba.isc.jbpm.berichten.JbpmBerichtenDao;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.jboss.soa.esb.actions.ActionLifecycleException;
import org.jboss.soa.esb.actions.ActionPipelineProcessor;
import org.jboss.soa.esb.actions.ActionProcessingException;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;
import org.jboss.soa.esb.message.Properties;

/**
 * ESB Action die verantwoordelijk is voor het omzetten van de STRING message naar een OBJECT message.
 */
public final class BerichtParseAction implements ActionPipelineProcessor {

    private static final Logger LOG = LoggerFactory.getLogger();

    private final ConfigTree configTree;

    private BerichtenDao berichtenDao = new JbpmBerichtenDao();
    private BerichtFactory berichtFactory;

    /**
     * Constructor (verplicht voor Jboss ESB).
     * 
     * @param configTree
     *            configuratie
     */
    public BerichtParseAction(final ConfigTree configTree) {
        this.configTree = configTree;
    }

    public void setBerichtenDao(final BerichtenDao berichtenDao) {
        this.berichtenDao = berichtenDao;
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    @Override
    public Message process(final Message message) throws ActionProcessingException {
        LOG.info("process: {}", message.getBody());

        final String berichtAlsString = (String) message.getBody().get();
        final Bericht bericht = berichtFactory.getBericht(berichtAlsString);

        // Zet berichttype in bericht audit.
        final Properties properties = message.getProperties();
        final Long berichtId = (Long) properties.getProperty(EsbConstants.PROPERTY_BERICHT);
        berichtenDao.updateNaam(berichtId, bericht.getBerichtType());

        // Set messageId en correlationId op bericht
        final String messageId = (String) properties.getProperty(EsbConstants.PROPERTY_MESSAGE_ID);
        final String correlatieId = (String) properties.getProperty(EsbConstants.PROPERTY_CORRELATIE_ID);

        bericht.setMessageId(messageId);
        bericht.setCorrelationId(correlatieId);

        // LO3 specifieke afhandeling
        if (bericht instanceof Lo3Bericht) {
            final String bronGemeente = (String) properties.getProperty(EsbConstants.PROPERTY_BRON_GEMEENTE);
            final String doelGemeente = (String) properties.getProperty(EsbConstants.PROPERTY_DOEL_GEMEENTE);
            berichtenDao.updateGemeenten(berichtId, bronGemeente, doelGemeente);

            final Lo3Bericht lo3Bericht = (Lo3Bericht) bericht;

            lo3Bericht.setBronGemeente(bronGemeente);
            lo3Bericht.setDoelGemeente(doelGemeente);

            final String herhalingHeader = lo3Bericht.getHeader(Lo3HeaderVeld.HERHALING);
            final Integer herhaling =
                    herhalingHeader == null || "".equals(herhalingHeader) ? null : Integer.valueOf(herhalingHeader);
            berichtenDao.updateHerhaling(berichtId, herhaling);
        }

        message.getBody().add(bericht);
        return message;
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    @Override
    public void initialise() throws ActionLifecycleException {
        final String kanaal = configTree.getAttribute("kanaal");

        if ("VOSPG".equals(kanaal)) {
            berichtFactory = new Lo3BerichtFactory();
        } else if ("BRP".equals(kanaal)) {
            berichtFactory = BrpBerichtFactory.SINGLETON;
        } else if ("SYNC".equals(kanaal)) {
            berichtFactory = SyncBerichtFactory.SINGLETON;
        } else if ("MVI".equals(kanaal)) {
            berichtFactory = new MviBerichtFactory();
        } else {
            throw new ActionLifecycleException("Kanaal '" + kanaal + "' onbekend.");
        }
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
